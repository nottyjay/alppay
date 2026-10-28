package com.aialphay.alppay.pay.rqrs.payorder.payway;

import com.aialphay.alppay.core.constants.CS;
import com.aialphay.alppay.pay.rqrs.payorder.CommonPayDataRQ;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/*
 * 支付方式： UPACP_BAR
 *
 * @Author nottyjay
 * @site https://www.aialphay.com
 * @date 2021/12/1 19:57
 */
@Data
public class UpBarOrderRQ extends CommonPayDataRQ {

  /** 用户 支付条码 * */
  @NotBlank(message = "支付条码不能为空")
  private String authCode;

  /** 构造函数 * */
  public UpBarOrderRQ() {
    this.setWayCode(CS.PAY_WAY_CODE.UP_BAR);
  }
}
