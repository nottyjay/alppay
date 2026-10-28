package com.aialphay.alppay.pay.rqrs.payorder.payway;

import com.aialphay.alppay.core.constants.CS;
import com.aialphay.alppay.pay.rqrs.payorder.UnifiedOrderRQ;
import lombok.Data;

/*
 * 支付方式： YSF_JSAPI
 *
 * @Author nottyjay
 * @site https://www.aialphay.com
 * @date 2021/6/8 17:34
 */
@Data
public class YsfJsapiOrderRQ extends UnifiedOrderRQ {

  /** 构造函数 * */
  public YsfJsapiOrderRQ() {
    this.setWayCode(CS.PAY_WAY_CODE.YSF_JSAPI);
  }
}
