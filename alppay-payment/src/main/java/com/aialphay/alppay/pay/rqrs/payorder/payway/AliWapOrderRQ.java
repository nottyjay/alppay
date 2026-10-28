package com.aialphay.alppay.pay.rqrs.payorder.payway;

import com.aialphay.alppay.core.constants.CS;
import com.aialphay.alppay.pay.rqrs.payorder.CommonPayDataRQ;
import lombok.Data;

/*
 * 支付方式： ALI_WAP
 *
 * @Author nottyjay
 * @site https://www.aialphay.com
 * @date 2021/6/8 17:34
 */
@Data
public class AliWapOrderRQ extends CommonPayDataRQ {

  /** 构造函数 * */
  public AliWapOrderRQ() {
    this.setWayCode(CS.PAY_WAY_CODE.ALI_WAP); // 默认 ALI_WAP, 避免validate出现问题
  }
}
