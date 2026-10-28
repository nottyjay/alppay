package com.aialphay.alppay.request;

import com.aialphay.alppay.model.AlppayObject;
import com.aialphay.alppay.net.RequestOptions;
import com.aialphay.alppay.response.AlppayResponse;

/**
 * Alppay请求接口
 *
 * @author nottyjay
 * @site https://www.aialphay.com
 * @date 2021-06-08 11:00
 */
public interface AlppayRequest<T extends AlppayResponse> {

  /**
   * 获取当前接口的路径
   *
   * @return
   */
  String getApiUri();

  /**
   * 获取当前接口的版本
   *
   * @return
   */
  String getApiVersion();

  /**
   * 设置当前接口的版本
   *
   * @return
   */
  void setApiVersion(String apiVersion);

  RequestOptions getRequestOptions();

  void setRequestOptions(RequestOptions options);

  AlppayObject getBizModel();

  void setBizModel(AlppayObject bizModel);

  Class<T> getResponseClass();
}
