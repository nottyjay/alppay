package com.aialphay.alppay.request;

import com.aialphay.alppay.Alppay;
import com.aialphay.alppay.model.AlppayObject;
import com.aialphay.alppay.net.RequestOptions;
import com.aialphay.alppay.response.RefundOrderCreateResponse;

/**
 * Alppay退款请求实现
 *
 * @author nottyjay
 * @site https://www.aialphay.com
 * @date 2021-06-18 09:00
 */
public class RefundOrderCreateRequest implements AlppayRequest<RefundOrderCreateResponse> {

  private String apiVersion = Alppay.VERSION;
  private String apiUri = "api/refund/refundOrder";
  private RequestOptions options;
  private AlppayObject bizModel = null;

  @Override
  public String getApiUri() {
    return this.apiUri;
  }

  @Override
  public String getApiVersion() {
    return this.apiVersion;
  }

  @Override
  public void setApiVersion(String apiVersion) {
    this.apiVersion = apiVersion;
  }

  @Override
  public RequestOptions getRequestOptions() {
    return this.options;
  }

  @Override
  public void setRequestOptions(RequestOptions options) {
    this.options = options;
  }

  @Override
  public AlppayObject getBizModel() {
    return this.bizModel;
  }

  @Override
  public void setBizModel(AlppayObject bizModel) {
    this.bizModel = bizModel;
  }

  @Override
  public Class<RefundOrderCreateResponse> getResponseClass() {
    return RefundOrderCreateResponse.class;
  }
}
