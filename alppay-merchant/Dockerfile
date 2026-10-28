#
#   alipay-merchant（商户平台） java程序镜像构建脚本   .Power by nottyjay
#

# ------   START   ------

# 基础镜像来自： https://hub.docker.com/ 官方OFFICIAL
FROM openjdk:17

MAINTAINER nottyjay

# 配置环境变量，支持中文。
ENV LANG=C.UTF-8

# 设置时区 东八区， 解决日志时间不正确的问题。
ENV TZ=Asia/Shanghai

# 对外映射的端口 ( 不明确EXPOSE 也不影响映射  )
EXPOSE 9218

# 挂载目录 "/alipayhomes/service/app/application.yml 这个文件不能写到VOLUME中， 否则将映射成为了目录导致启动异常。
VOLUME ["/alipayhomes/service/logs", "/alipayhomes/service/uploads"]

# 使用alipay用户启动。 ( 需要 RUN adduser等一系列操作 )
# USER alipay:alipay

# 创建目录
RUN mkdir /alipayhomes/service/app -p

# 安装
COPY ./target/alipay-merchant.jar /alipayhomes/service/app/alipay-merchant.jar

# 设置工作目录
WORKDIR /alipayhomes/service/app

CMD ["java", "-jar", "alipay-merchant.jar"]

# ------   END   ------




