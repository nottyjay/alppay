package com.aialphay.alppay.pay.rqrs.payorder.payway;

import com.aialphay.alppay.core.constants.CS;
import com.aialphay.alppay.pay.rqrs.payorder.CommonPayDataRQ;
import lombok.Data;

/*
 * 支付方式： UPACP_APP
 *
 * @Author nottyjay
 * @site https://www.aialphay.com
 * @date 2021/12/1 19:57
 */
@Data
public class UpAppOrderRQ extends CommonPayDataRQ {

  /** 构造函数 * */
  public UpAppOrderRQ() {
    this.setWayCode(CS.PAY_WAY_CODE.UP_APP);
  }
}
