package com.aialphay.alppay.pay.rqrs.payorder;

import lombok.Data;

/*
 * 通用支付数据RQ
 *
 * @Author nottyjay
 * @site https://www.aialphay.com
 * @date 2021/6/8 17:31
 */
@Data
public class CommonPayDataRQ extends UnifiedOrderRQ {

  /** 请求参数： 支付数据包类型 * */
  private String payDataType;
}
