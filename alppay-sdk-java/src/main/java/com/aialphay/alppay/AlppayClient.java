package com.aialphay.alppay;

import com.alibaba.fastjson.JSONObject;
import com.aialphay.alppay.exception.AlppayException;
import com.aialphay.alppay.net.APIAlppayRequest;
import com.aialphay.alppay.net.APIResource;
import com.aialphay.alppay.net.RequestOptions;
import com.aialphay.alppay.request.AlppayRequest;
import com.aialphay.alppay.response.AlppayResponse;
import com.aialphay.alppay.util.JSONWriter;
import java.util.HashMap;
import java.util.Map;

/**
 * Alppay sdk客户端
 *
 * @author nottyjay
 * @site https://www.aialphay.com
 * @date 2021-06-08 11:00
 */
public class AlppayClient extends APIResource {

  private static final Map<String, AlppayClient> clientMap = new HashMap<String, AlppayClient>();

  private String appId;
  private String signType = Alppay.DEFAULT_SIGN_TYPE;
  private String apiKey = Alppay.apiKey;
  private String apiBase = Alppay.getApiBase();

  public AlppayClient(String apiBase, String signType, String apiKey) {
    this.apiBase = apiBase;
    this.signType = signType;
    this.apiKey = apiKey;
  }

  public AlppayClient(String apiBase, String apiKey) {
    this.apiBase = apiBase;
    this.apiKey = apiKey;
  }

  public AlppayClient(String apiKey) {
    this.apiKey = apiKey;
  }

  public AlppayClient() {}

  public static synchronized AlppayClient getInstance(String appId, String apiKey, String apiBase) {
    AlppayClient client = clientMap.get(appId);
    if (client != null) {
      return client;
    }
    client = new AlppayClient();
    clientMap.put(appId, client);
    client.appId = appId;
    client.apiKey = apiKey;
    client.apiBase = apiBase;
    return client;
  }

  public static synchronized AlppayClient getInstance(String appId, String apiKey) {
    AlppayClient client = clientMap.get(appId);
    if (client != null) {
      return client;
    }
    client = new AlppayClient();
    clientMap.put(appId, client);
    client.appId = appId;
    client.apiKey = apiKey;
    return client;
  }

  public static synchronized AlppayClient getInstance(String appId) {
    AlppayClient client = clientMap.get(appId);
    if (client != null) {
      return client;
    }
    client = new AlppayClient();
    clientMap.put(appId, client);
    client.appId = appId;
    return client;
  }

  public String getAppId() {
    return appId;
  }

  public String getSignType() {
    return signType;
  }

  public void setSignType(String signType) {
    this.signType = signType;
  }

  public String getApiKey() {
    return apiKey;
  }

  public void setApiKey(String apiKey) {
    this.apiKey = apiKey;
  }

  public String getApiBase() {
    return apiBase;
  }

  public void setApiBase(String apiBase) {
    this.apiBase = apiBase;
  }

  public <T extends AlppayResponse> T execute(AlppayRequest<T> request) throws AlppayException {

    // 支持用户自己设置RequestOptions
    if (request.getRequestOptions() == null) {
      RequestOptions options =
          RequestOptions.builder()
              .setVersion(request.getApiVersion())
              .setUri(request.getApiUri())
              .setAppId(this.appId)
              .setApiKey(this.apiKey)
              .build();
      request.setRequestOptions(options);
    }

    return execute(request, RequestMethod.POST, this.apiBase);
  }

  public String getRequestUrl(AlppayRequest request) throws AlppayException {
    // 支持用户自己设置RequestOptions
    if (request.getRequestOptions() == null) {
      RequestOptions options =
          RequestOptions.builder()
              .setVersion(request.getApiVersion())
              .setUri(request.getApiUri())
              .setAppId(this.appId)
              .setApiKey(this.apiKey)
              .build();
      request.setRequestOptions(options);
    }
    String jsonParam = new JSONWriter().write(request.getBizModel(), true);

    JSONObject params = JSONObject.parseObject(jsonParam);
    request.getRequestOptions();

    return APIAlppayRequest.buildURLWithSign(this.apiBase, params, request.getRequestOptions())
        .toString();
  }
}
