package com.aialphay.alppay.pay.rqrs.payorder.payway;

import com.aialphay.alppay.core.constants.CS;
import com.aialphay.alppay.pay.rqrs.payorder.CommonPayDataRQ;
import lombok.Data;

/*
 * 支付方式： WX_H5
 *
 * @Author nottyjay
 * @site https://www.aialphay.com
 * @date 2021/6/8 17:34
 */
@Data
public class WxH5OrderRQ extends CommonPayDataRQ {

  /** 构造函数 * */
  public WxH5OrderRQ() {
    this.setWayCode(CS.PAY_WAY_CODE.WX_H5);
  }
}
