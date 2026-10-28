package com.aialphay.alppay.pay.rqrs.payorder.payway;

import com.aialphay.alppay.core.constants.CS;
import com.aialphay.alppay.pay.rqrs.payorder.UnifiedOrderRQ;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/*
 * 支付方式： ALI_JSAPI
 *
 * @Author nottyjay
 * @site https://www.aialphay.com
 * @date 2021/6/8 17:34
 */
@Data
public class AliJsapiOrderRQ extends UnifiedOrderRQ {

  /** 支付宝用户ID * */
  @NotBlank(message = "用户ID不能为空")
  private String buyerUserId;

  /** 构造函数 * */
  public AliJsapiOrderRQ() {
    this.setWayCode(CS.PAY_WAY_CODE.ALI_JSAPI);
  }

  @Override
  public String getChannelUserId() {
    return this.buyerUserId;
  }
}
