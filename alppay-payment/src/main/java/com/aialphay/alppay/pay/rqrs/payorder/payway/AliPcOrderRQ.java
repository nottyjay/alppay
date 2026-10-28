package com.aialphay.alppay.pay.rqrs.payorder.payway;

import com.aialphay.alppay.core.constants.CS;
import com.aialphay.alppay.pay.rqrs.payorder.CommonPayDataRQ;
import lombok.Data;

/*
 * 支付方式： ALI_PC
 *
 * @Author nottyjay
 * @site https://www.aialphay.com
 * @date 2021/6/8 17:34
 */
@Data
public class AliPcOrderRQ extends CommonPayDataRQ {

  /** 构造函数 * */
  public AliPcOrderRQ() {
    this.setWayCode(CS.PAY_WAY_CODE.ALI_PC);
  }
}
