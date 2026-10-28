package com.aialphay.alppay.pay.rqrs.payorder.payway;

import com.aialphay.alppay.core.constants.CS;
import com.aialphay.alppay.pay.rqrs.payorder.UnifiedOrderRQ;
import lombok.Data;

import jakarta.validation.constraints.NotBlank;

/*
 * 支付方式： WX_JSAPI
 *
 * @Author nottyjay
 * @site https://www.aialphay.com
 * @date 2021/6/8 17:34
 */
@Data
public class WxJsapiOrderRQ extends UnifiedOrderRQ {

  /** 微信openid * */
  @NotBlank(message = "openid不能为空")
  private String openid;

  /** 标志是否为 subMchAppId的对应 openId， 0-否， 1-是， 默认否 * */
  private Byte isSubOpenId;

  /** 构造函数 * */
  public WxJsapiOrderRQ() {
    this.setWayCode(CS.PAY_WAY_CODE.WX_JSAPI);
  }

  @Override
  public String getChannelUserId() {
    return this.openid;
  }
}
