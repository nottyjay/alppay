package com.aialphay.alppay.pay.rqrs.transfer;

import com.aialphay.alppay.pay.rqrs.AbstractMchAppRQ;
import lombok.Data;

/*
 * 查询转账单请求参数对象
 *
 * @Author nottyjay
 * @site https://www.aialphay.com
 * @date 2021/6/17 14:07
 */
@Data
public class QueryTransferOrderRQ extends AbstractMchAppRQ {

  /** 商户转账单号 * */
  private String mchOrderNo;

  /** 支付系统转账单号 * */
  private String transferId;
}
