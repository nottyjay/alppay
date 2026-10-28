package com.aialphay.alppay.components.oss.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * aliyun oss 的yml配置参数
 *
 * @Author nottyjay
 * @site https://www.aialphay.com
 * @date 2021/7/12 18:18
 */
@Data
@Component
@ConfigurationProperties(prefix = "isys.oss.aliyun-oss")
public class AliyunOssYmlConfig {

  private String endpoint;
  private String publicBucketName;
  private String privateBucketName;
  private String accessKeyId;
  private String accessKeySecret;
}
