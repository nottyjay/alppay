package com.aialphay.alppay.pay.rqrs.payorder.payway;

import com.aialphay.alppay.core.constants.CS;
import com.aialphay.alppay.pay.rqrs.payorder.UnifiedOrderRQ;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/*
 * 支付方式： WX_BAR
 *
 * @Author nottyjay
 * @site https://www.aialphay.com
 * @date 2021/6/8 17:34
 */
@Data
public class WxBarOrderRQ extends UnifiedOrderRQ {

  /** 用户 支付条码 * */
  @NotBlank(message = "支付条码不能为空")
  private String authCode;

  /** 构造函数 * */
  public WxBarOrderRQ() {
    this.setWayCode(CS.PAY_WAY_CODE.WX_BAR); // 默认 wx_bar, 避免validate出现问题
  }
}
