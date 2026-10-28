package com.aialphay.alppay.components.oss.constant;

import lombok.Getter;

/*
 * oss 服务枚举值
 * @Author nottyjay
 * @site https://www.aialphay.com
 * @date 2021/7/12 10:48
 */
@Getter
public enum OssServiceTypeEnum {
  LOCAL("local"), // 本地存储

  ALIYUN_OSS("aliyun-oss"); // 阿里云oss

  /** 名称 * */
  private String serviceName;

  OssServiceTypeEnum(String serviceName) {
    this.serviceName = serviceName;
  }
}
