package com.aialphay.alppay.pay.rqrs;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/*
 *
 * 通用RQ, 包含mchNo和appId 必填项
 *
 * @Author nottyjay
 * @site https://www.aialphay.com
 * @date 2021/6/16 10:30
 */
@Data
public class AbstractMchAppRQ extends AbstractRQ {

  /** 商户号 * */
  @NotBlank(message = "商户号不能为空")
  private String mchNo;

  /** 商户应用ID * */
  @NotBlank(message = "商户应用ID不能为空")
  private String appId;
}
