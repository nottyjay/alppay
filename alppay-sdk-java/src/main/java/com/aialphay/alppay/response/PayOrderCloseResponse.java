package com.aialphay.alppay.response;

import com.aialphay.alppay.model.PayOrderCloseResModel;

/**
 * Alppay支付 关闭订单响应实现
 *
 * @author nottyjay
 * @site https://www.aialphay.com
 * @date 2022/1/25 9:56
 */
public class PayOrderCloseResponse extends AlppayResponse {

  private static final long serialVersionUID = 7654172640802954221L;

  public PayOrderCloseResModel get() {
    if (getData() == null) return new PayOrderCloseResModel();
    return getData().toJavaObject(PayOrderCloseResModel.class);
  }
}
