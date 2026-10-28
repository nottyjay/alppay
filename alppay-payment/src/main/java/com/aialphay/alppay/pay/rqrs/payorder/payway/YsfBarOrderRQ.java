package com.aialphay.alppay.pay.rqrs.payorder.payway;

import com.aialphay.alppay.core.constants.CS;
import com.aialphay.alppay.pay.rqrs.payorder.UnifiedOrderRQ;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/*
 * 支付方式： YSF_BAR
 *
 * @Author nottyjay
 * @site https://www.aialphay.com
 * @date 2021/6/8 17:34
 */
@Data
public class YsfBarOrderRQ extends UnifiedOrderRQ {

  /** 用户 支付条码 * */
  @NotBlank(message = "支付条码不能为空")
  private String authCode;

  /** 构造函数 * */
  public YsfBarOrderRQ() {
    this.setWayCode(CS.PAY_WAY_CODE.YSF_BAR);
  }
}
