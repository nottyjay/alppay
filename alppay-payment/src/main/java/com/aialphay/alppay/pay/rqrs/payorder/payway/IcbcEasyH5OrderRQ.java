package com.aialphay.alppay.pay.rqrs.payorder.payway;

import com.aialphay.alppay.core.constants.CS;
import com.aialphay.alppay.pay.rqrs.payorder.UnifiedOrderRQ;

/**
 * 工行线上POS工银E支付H5消费下单
 * @author nottyjay
 * @Date 2025/11/21 10:00
 */
public class IcbcEasyH5OrderRQ extends UnifiedOrderRQ {

  public IcbcEasyH5OrderRQ() {
    this.setWayCode(CS.PAY_WAY_CODE.ICBC_EASY_H5);
  }
}
