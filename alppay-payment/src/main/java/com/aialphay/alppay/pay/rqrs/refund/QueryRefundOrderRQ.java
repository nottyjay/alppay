package com.aialphay.alppay.pay.rqrs.refund;

import com.aialphay.alppay.pay.rqrs.AbstractMchAppRQ;
import lombok.Data;

/*
 * 查询退款单请求参数对象
 *
 * @Author nottyjay
 * @site https://www.aialphay.com
 * @date 2021/6/17 14:07
 */
@Data
public class QueryRefundOrderRQ extends AbstractMchAppRQ {

  /** 商户退款单号 * */
  private String mchRefundNo;

  /** 支付系统退款订单号 * */
  private String refundOrderId;
}
