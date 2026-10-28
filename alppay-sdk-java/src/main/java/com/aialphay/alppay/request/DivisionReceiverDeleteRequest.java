package com.aialphay.alppay.request;

import com.aialphay.alppay.Alppay;
import com.aialphay.alppay.model.AlppayObject;
import com.aialphay.alppay.net.RequestOptions;
import com.aialphay.alppay.response.DivisionReceiverDeleteResponse;

/**
 * @auther nottyjay
 * @date 2025/12/6
 */
public class DivisionReceiverDeleteRequest implements AlppayRequest<DivisionReceiverDeleteResponse> {

  private String apiVersion = Alppay.VERSION;
  private String apiUri = "api/division/receiver/delete";
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
  public Class<DivisionReceiverDeleteResponse> getResponseClass() {
    return DivisionReceiverDeleteResponse.class;
  }
}
