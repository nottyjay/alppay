package com.aialphay.alppay.response;

import com.aialphay.alppay.model.RefundOrderQueryResModel;

/**
 * Alppay退款查单响应实现
 *
 * @author nottyjay
 * @site https://www.aialphay.com
 * @date 2021-06-18 12:00
 */
public class RefundOrderQueryResponse extends AlppayResponse {

  private static final long serialVersionUID = 7654172640802954221L;

  public RefundOrderQueryResModel get() {
    if (getData() == null) return new RefundOrderQueryResModel();
    return getData().toJavaObject(RefundOrderQueryResModel.class);
  }
}
