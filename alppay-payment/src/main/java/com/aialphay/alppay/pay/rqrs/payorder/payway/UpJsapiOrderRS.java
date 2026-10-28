package com.aialphay.alppay.pay.rqrs.payorder.payway;

import com.aialphay.alppay.core.constants.CS;
import com.aialphay.alppay.core.utils.JsonKit;
import com.aialphay.alppay.pay.rqrs.payorder.UnifiedOrderRS;
import lombok.Data;

/*
 * 支付方式： UP_JSAPI
 *
 * @Author nottyjay
 * @site https://www.aialphay.com
 * @date 2022/3/17 12:34
 */
@Data
public class UpJsapiOrderRS extends UnifiedOrderRS {

  /** 调起支付插件的云闪付订单号 * */
  private String redirectUrl;

  @Override
  public String buildPayDataType() {
    return CS.PAY_DATA_TYPE.YSF_APP;
  }

  @Override
  public String buildPayData() {
    return JsonKit.newJson("redirectUrl", redirectUrl).toString();
  }
}
