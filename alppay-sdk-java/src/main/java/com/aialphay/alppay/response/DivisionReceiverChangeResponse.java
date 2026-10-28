package com.aialphay.alppay.response;

import com.aialphay.alppay.model.DivisionReceiverChangeResModel;

/**
 * @auther nottyjay
 * @date 2025/12/6
 */
public class DivisionReceiverChangeResponse extends AlppayResponse {

  private static final Long serialVersionUID = 2637191198247207952L;

  public DivisionReceiverChangeResModel get() {
    if (getData() == null) return new DivisionReceiverChangeResModel();
    return getData().toJavaObject(DivisionReceiverChangeResModel.class);
  }

}
