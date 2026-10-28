package com.aialphay.alppay.response;

import com.aialphay.alppay.model.DivisionReceiverBindResModel;

/***
 * 分账账号的绑定响应实现
 *
 * @author nottyjay
 * @site https://www.aialphay.com
 * @date 2021/8/25 10:35
 */
public class DivisionReceiverBindResponse extends AlppayResponse {

  private static final long serialVersionUID = 7419683269497002904L;

  public DivisionReceiverBindResModel get() {
    if (getData() == null) return new DivisionReceiverBindResModel();
    return getData().toJavaObject(DivisionReceiverBindResModel.class);
  }

  @Override
  public boolean isSuccess(String apiKey) {
    if (super.isSuccess(apiKey)) {
      int state = get().getBindState();
      return state == 1;
    }
    return false;
  }
}
