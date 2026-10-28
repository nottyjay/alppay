package com.aialphay.alppay.pay.rqrs.division;

import com.aialphay.alppay.pay.rqrs.AbstractMchAppRQ;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/*
 * 分账账号的绑定 请求参数
 *
 * @Author nottyjay
 * @site https://www.aialphay.com
 * @date 2025-12-06 16:00:34
 */
@Data
public class DivisionReceiverChangeRQ extends AbstractMchAppRQ {

  /** 分账接收者 ID */
  @NotBlank(message = "分账接收者 ID 不能为空")
  private Long receiverId;

  /** 接收者账号别名 * */
  private String receiverAlias;

  /** 组ID * */
  private Long receiverGroupId;

  /** 分账关系类型（参考微信）， 如： SERVICE_PROVIDER 服务商等 * */
  private String relationType;

  /** 当选择自定义时，需要录入该字段。 否则为对应的名称 * */
  private String relationTypeName;

  /** 渠道特殊信息 */
  private String channelExtInfo;

  /** 分账比例 * */
  private String divisionProfit;
}
