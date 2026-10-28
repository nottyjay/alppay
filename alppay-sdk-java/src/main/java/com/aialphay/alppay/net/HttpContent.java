package com.aialphay.alppay.net;

import com.alibaba.fastjson.JSON;

import java.util.Map;

import static java.util.Objects.requireNonNull;

/**
 * Http请求内容
 *
 * @author nottyjay
 * @site https://www.aialphay.com
 * @date 2021-06-08 11:00
 */
public class HttpContent {

  byte[] byteArrayContent;

  String contentType;

  private HttpContent(byte[] byteArrayContent, String contentType) {
    this.byteArrayContent = byteArrayContent;
    this.contentType = contentType;
  }

  public static HttpContent buildJSONContent(Map<String, Object> params) {
    requireNonNull(params);

    return new HttpContent(
        createJSONString(params).getBytes(APIResource.CHARSET),
        String.format("application/json; charset=%s", APIResource.CHARSET));
  }

  private static String createJSONString(Map<String, Object> params) {
    return JSON.toJSONString(params);
  }

  public String stringContent() {
    return new String(this.byteArrayContent, APIResource.CHARSET);
  }
}
