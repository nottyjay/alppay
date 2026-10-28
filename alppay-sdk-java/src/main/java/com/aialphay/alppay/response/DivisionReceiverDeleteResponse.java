package com.aialphay.alppay.response;

import com.aialphay.alppay.model.DivisionReceiverDeleteResModel;

/**
 * 分账账号的删除响应实现
 * @auther nottyjay
 * @date 2025/12/6
 */
public class DivisionReceiverDeleteResponse extends AlppayResponse {

  public DivisionReceiverDeleteResModel get() {
    if (getData() == null) return new DivisionReceiverDeleteResModel();
    return getData().toJavaObject(DivisionReceiverDeleteResModel.class);
  }

  @Override
  public boolean isSuccess(String apiKey) {
    return super.isSuccess(apiKey);
  }
}
