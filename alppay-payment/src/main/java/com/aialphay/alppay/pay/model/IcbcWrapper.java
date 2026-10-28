package com.aialphay.alppay.pay.model;

import cn.hutool.json.JSONUtil;
import com.aialphay.alppay.core.model.params.icbc.IcbcNormalMchParams;
import com.icbc.api.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 * 工行支付请求包装类
 *
 * @author nottyjay
 * @Date 2025/11/21 10:00
 */
@Slf4j
@Data
@AllArgsConstructor
public class IcbcWrapper {

  private DefaultIcbcClient client;
  private UiIcbcClient uiClient;
  private IcbcNormalMchParams mchParams;

  public static IcbcWrapper buildIcbcWrapper(IcbcNormalMchParams icbcNormalMchParams){
    DefaultIcbcClient client = null;
    client = new DefaultIcbcClient(icbcNormalMchParams.getAppid(), IcbcConstants.SIGN_TYPE_RSA2, icbcNormalMchParams.getPrivateKey(), icbcNormalMchParams.getApiGatewayPublicKey());
    UiIcbcClient uiClient = null;
    uiClient = new UiIcbcClient(icbcNormalMchParams.getAppid(), IcbcConstants.SIGN_TYPE_RSA2, icbcNormalMchParams.getPrivateKey(), IcbcConstants.CHARSET_UTF8);
    return new IcbcWrapper(client, uiClient, icbcNormalMchParams);
  }

  public <T extends IcbcResponse> T execute(AbstractIcbcRequest<T> request){
    try{
      return client.execute(request);
      } catch (IcbcApiException e){
      log.error("icbc execute error: {}", e.getMessage(), e);
      return null;
    }
  }
  /**
   * 执行UI请求
   * @param request 请求
   * @return 响应
   */
  public String executeUI(IcbcRequest<?> request){
    try {
      if(log.isDebugEnabled()){
        log.debug(JSONUtil.toJsonStr(request));
      }
      return uiClient.buildPostForm(request);
    } catch (IcbcApiException e) {
      log.error("icbc executeUI error: {}", e.getMessage(), e);
      return null;
    }
  }
}
