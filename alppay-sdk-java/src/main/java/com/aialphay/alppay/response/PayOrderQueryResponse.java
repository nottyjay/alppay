package com.aialphay.alppay.response;

import com.aialphay.alppay.model.PayOrderQueryResModel;

/**
 * Alppay支付查单响应实现
 *
 * @author nottyjay
 * @site https://www.aialphay.com
 * @date 2021-06-08 11:00
 */
public class PayOrderQueryResponse extends AlppayResponse {

  private static final long serialVersionUID = 7654172640802954221L;

  public PayOrderQueryResModel get() {
    if (getData() == null) return new PayOrderQueryResModel();
    return getData().toJavaObject(PayOrderQueryResModel.class);
  }
}
