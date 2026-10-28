package com.aialphay.alppay.pay.rqrs.payorder.payway;

import com.aialphay.alppay.core.constants.CS;
import com.aialphay.alppay.pay.rqrs.payorder.UnifiedOrderRQ;
import lombok.Data;

/*
 * 支付方式： AUTO_BAR
 *
 * @Author nottyjay
 * @site https://www.aialphay.com
 * @date 2021/6/8 17:34
 */
@Data
public class AutoBarOrderRQ extends UnifiedOrderRQ {

  /** 条码值 * */
  private String authCode;

  /** 构造函数 * */
  public AutoBarOrderRQ() {
    this.setWayCode(CS.PAY_WAY_CODE.AUTO_BAR);
  }
}
