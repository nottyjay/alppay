# Java SDK 自动发布到 Maven Central

工作流：`.github/workflows/publish-sdk.yml`。

## 触发和构建范围

- 向 `nottyjay/alppay` GitHub 仓库推送新版本 tag（例如 `v1.0.1`），自动构建该 tag 指向的提交并发布 SDK。
- 普通分支 push 不触发发布；同一 tag 的发布任务串行执行。
- 使用 JDK 17 构建，SDK 输出 Java 8 字节码。
- 编译 SDK 主代码和测试代码，生成普通 JAR、sources JAR、Javadoc JAR、独立 POM、GPG 签名和校验文件。
- 支付、退款、转账等接口示例仍默认跳过，发布过程不会调用业务网关。
- 仅发布 `com.aialphay.alppay:alppay-sdk-java`。发布 POM 已展开父配置与依赖版本，使用方无需获取内部父 POM `alppay:Final`。
- 工作流等待 Central 确认发布完成，并保留构建产物 14 天。

此流程构建 SDK，不打包其他服务端模块。`alppay-payment` 依赖 `lib/icbc/` 中未纳入 Git 的银行 JAR，全项目 CI 需要先提供这些依赖的获取方式。

## 版本规则

版本取自 tag，格式为 `vX.Y.Z`，例如 `v1.0.1` 对应 Maven 版本 `1.0.1`。不符合格式的 `v*` tag 会在构建前失败，其他名称的 tag 不触发工作流。

流程通过 `-Disys.version=1.0.1` 为本次 SDK 构建设置版本，不修改根 POM；因此 tag 不必与根 POM 的开发版本一致。源码信息和许可证链接也会指向对应 tag。

Central 同一坐标和版本只能发布一次。重跑同一个 tag 仍使用同一个版本，已发布成功的版本不能重复发布或覆盖；发布下一版时创建新 tag。发布成功后的依赖坐标会显示在 Actions Summary 中。

## 发布操作

先将工作流及源码提交并推送到 GitHub，再为准备发布的提交创建 tag。下面以 `v1.0.1` 为例，实际操作时使用尚未发布的版本：

```bash
git switch master
git push github master
git tag -a v1.0.1 -m "Release Java SDK 1.0.1"
git push github v1.0.1
```

最后一步触发正式发布。仅向 Gitee 推送 tag 不会触发 GitHub Actions。请在首次推送 tag 前完成以下账号和 Secrets 配置。

## 需要准备的账号和密钥

### 1. GitHub 仓库

发布仓库为 [nottyjay/alppay](https://github.com/nottyjay/alppay)，SSH 地址为 `git@github.com:nottyjay/alppay.git`。本地已配置名为 `github` 的远程，同时保留 Gitee 的 `origin`。

在其他电脑首次配置时执行：

```bash
git remote add github git@github.com:nottyjay/alppay.git
```

工作流根据实际 GitHub 仓库自动填写发布 POM 的项目地址和源码地址。

### 2. Maven Central 账号与命名空间

登录 [Central Publisher Portal](https://central.sonatype.com/)，确认账号有 `com.aialphay.alppay` 的发布权限。通常需要验证 `com.aialphay` 命名空间对应的 `aialphay.com` 域名所有权；已有授权可直接使用。

在 Portal 生成发布 User Token。需要保存生成的 **username 和 password 两部分**，不是网页登录密码，也不是 GitHub Token。

### 3. GPG 签名密钥

可以使用已有的发布密钥。没有密钥时，在自己的电脑上生成带口令的签名密钥，并记录完整指纹：

```bash
gpg --quick-generate-key "Alppay Release <YOUR_EMAIL>" rsa3072 sign 2y
gpg --list-secret-keys --keyid-format LONG
```

将公钥发布到 Central 支持的密钥服务器，并导出对应的 ASCII 私钥，用于填写 GitHub Secret：

```bash
gpg --keyserver hkps://keyserver.ubuntu.com --send-keys YOUR_KEY_FINGERPRINT
gpg --armor --export-secret-keys YOUR_KEY_FINGERPRINT
```

私钥内容包括 `BEGIN PGP PRIVATE KEY BLOCK` 和 `END PGP PRIVATE KEY BLOCK`。将它直接保存到 GitHub Secrets，不要提交到仓库或粘贴到聊天中。

### 4. GitHub Actions Secrets

在 GitHub 仓库的 **Settings → Secrets and variables → Actions → New repository secret** 添加：

| Secret | 内容 |
| --- | --- |
| `MAVEN_CENTRAL_USERNAME` | Central Portal User Token 的 username |
| `MAVEN_CENTRAL_PASSWORD` | Central Portal User Token 的 password |
| `GPG_PRIVATE_KEY` | ASCII armored GPG 私钥全文 |
| `GPG_PASSPHRASE` | 上述私钥的解密口令 |

工作流使用 Maven GPG Plugin 的 Bouncy Castle signer，直接从环境变量读取私钥和口令，无需在 runner 手动安装密钥。缺少 Secrets 时会在发布步骤明确失败；已生成的构建产物仍可下载。

## 本地检查

在项目根目录检查发布构建，不需要 Central 凭据或签名私钥：

```bash
mvn -B -ntp -f alppay-sdk-java/pom.xml -Prelease -Dgpg.skip=true clean verify
```

这只验证编译、测试发现、源码包、Javadoc 和独立 POM，不会上传。

需要完整验证签名和发布 bundle 时，先在本机配置好 GPG 签名密钥，再使用仅打包模式：

```bash
mvn -B -ntp -f alppay-sdk-java/pom.xml -Prelease \
  -Disys.version=1.0.1 -Dsdk.scm.tag=v1.0.1 -Dcentral.skipUpload=true clean deploy
```

`central.skipUpload=true` 会完成本地安装、签名、校验文件和 bundle 生成，但不上传到 Central。文件位于 `alppay-sdk-java/target/central-publishing/central-bundle.zip`。

不要使用根目录的 `mvn -Prelease deploy` 或加 `-am` 来发布；发布命令仅选择 SDK，避免将内部模块一并部署。

## 排查发布失败

- `401/403`：检查 Token 是否过期，以及账号对命名空间的权限。
- 签名校验失败：确认公钥已发布、私钥和口令匹配、密钥未过期。
- 版本已存在：先确认该版本是否已经发布成功；下一次发布应创建新的 `vX.Y.Z` tag，重跑不会改变版本号。
- 发布等待超时：先查看 Central Portal 的 deployment 状态，上传成功后仍可能在处理中。

## 官方参考

- [Central Maven 发布插件](https://central.sonatype.org/publish/publish-portal-maven/)
- [Central 发布要求](https://central.sonatype.org/publish/requirements/)
- [GPG 签名与公钥发布](https://central.sonatype.org/publish/requirements/gpg/)
- [GitHub setup-java 发布配置](https://github.com/actions/setup-java/blob/main/docs/advanced-usage.md)
- [Maven Flatten Plugin](https://www.mojohaus.org/flatten-maven-plugin/flatten-mojo.html)
