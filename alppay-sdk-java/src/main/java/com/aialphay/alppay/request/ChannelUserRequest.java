package com.aialphay.alppay.request;

import com.aialphay.alppay.model.AlppayObject;
import com.aialphay.alppay.net.RequestOptions;
import com.aialphay.alppay.response.ChannelUserResponse;

/** 仅仅用于查询渠道用户信息，编译出具体 URL 并不会产生实际请求 */
public class ChannelUserRequest implements AlppayRequest<ChannelUserResponse> {

  private final String apiUri = "api/channelUserId/jump";
  private String apiVersion = "1.0";
  private RequestOptions options;
  private AlppayObject bizModel = null;

  public ChannelUserRequest() {}

  public String getApiUri() {
    return this.apiUri;
  }

  public String getApiVersion() {
    return this.apiVersion;
  }

  public void setApiVersion(String apiVersion) {
    this.apiVersion = apiVersion;
  }

  public RequestOptions getRequestOptions() {
    return this.options;
  }

  public void setRequestOptions(RequestOptions options) {
    this.options = options;
  }

  public AlppayObject getBizModel() {
    return this.bizModel;
  }

  public void setBizModel(AlppayObject bizModel) {
    this.bizModel = bizModel;
  }

  public Class<ChannelUserResponse> getResponseClass() {
    return ChannelUserResponse.class;
  }
}
