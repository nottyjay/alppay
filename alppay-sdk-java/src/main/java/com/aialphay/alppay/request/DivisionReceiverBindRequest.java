package com.aialphay.alppay.request;

import com.aialphay.alppay.Alppay;
import com.aialphay.alppay.model.AlppayObject;
import com.aialphay.alppay.net.RequestOptions;
import com.aialphay.alppay.response.DivisionReceiverBindResponse;

/***
 * 分账绑定接口
 *
 * @author nottyjay
 * @site https://www.aialphay.com
 * @date 2021/8/25 10:34
 */
public class DivisionReceiverBindRequest implements AlppayRequest<DivisionReceiverBindResponse> {

  private String apiVersion = Alppay.VERSION;
  private String apiUri = "api/division/receiver/bind";
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
  public Class<DivisionReceiverBindResponse> getResponseClass() {
    return DivisionReceiverBindResponse.class;
  }
}
