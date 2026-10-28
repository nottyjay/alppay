package com.aialphay.alppay.pay.rqrs.payorder.payway;

import com.aialphay.alppay.core.constants.CS;
import com.aialphay.alppay.pay.rqrs.payorder.CommonPayDataRQ;
import lombok.Data;

/**
 * 支付方式 订单码
 *
 * @author zhangheming
 * @site https://www.aialphay.com
 * @date 2024/6/12 17:20
 */
@Data
public class AliOcOrderRQ extends CommonPayDataRQ {

  public AliOcOrderRQ() {
    this.setWayCode(CS.PAY_WAY_CODE.ALI_OC);
  }
}
