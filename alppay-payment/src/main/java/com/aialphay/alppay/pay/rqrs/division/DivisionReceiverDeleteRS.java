package com.aialphay.alppay.pay.rqrs.division;

import com.aialphay.alppay.pay.rqrs.AbstractRS;
import lombok.Data;

/*
 * 绑定账户 响应参数
 *
 * @Author nottyjay
 * @site https://www.aialphay.com
 * @date 2025-12-06 16:00:34
 */
@Data
public class DivisionReceiverDeleteRS extends AbstractRS {

  /** 分账接收者ID */
  private Long receiverId;

  /** 支付渠道错误码 */
  private String errCode;

  /** 支付渠道错误信息 */
  private String errMsg;

  public static DivisionReceiverDeleteRS buildByRecord(Long receiverId) {

    if (receiverId == null) {
      return null;
    }

    DivisionReceiverDeleteRS result = new DivisionReceiverDeleteRS();
    result.setReceiverId(receiverId);
    return result;
  }
}
