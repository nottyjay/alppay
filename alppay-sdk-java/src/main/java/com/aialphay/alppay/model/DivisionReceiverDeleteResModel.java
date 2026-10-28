package com.aialphay.alppay.model;

import lombok.Data;

/**
 * 分账账号的响应结果
 * @auther nottyjay
 * @date 2025/12/6
 */
@Data
public class DivisionReceiverDeleteResModel extends AlppayObject {

  /** 分账接收者 ID */
  private Long receiverId;

  /** 商户号 */
  private String mchNo;

  /** 应用ID */
  private String appId;

  /** 支付渠道错误码 */
  private String errCode;

  /** 支付渠道错误信息 */
  private String errMsg;
}
