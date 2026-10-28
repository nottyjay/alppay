package com.aialphay.alppay.pay.rqrs.division;

import com.aialphay.alppay.pay.rqrs.AbstractMchAppRQ;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * @auther nottyjay
 * @date 2025/12/6
 */
@Data
public class DivisionReceiverDeleteRQ extends AbstractMchAppRQ {

  /** 分账接收者 ID */
  @NotBlank(message = "分账接收者 ID 不能为空")
  private Long receiverId;
}
