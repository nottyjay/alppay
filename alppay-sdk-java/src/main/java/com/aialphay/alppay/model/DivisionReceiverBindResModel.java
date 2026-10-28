package com.aialphay.alppay.model;

import lombok.Data;

import java.math.BigDecimal;

/***
 * 分账账号的响应结果
 *
 * @author nottyjay
 * @site https://www.aialphay.com
 * @date 2021/8/25 10:38
 */
@Data
public class DivisionReceiverBindResModel extends AlppayObject {

  /** 分账接收者ID */
  private Long receiverId;

  /** 接收者账号别名 */
  private String receiverAlias;

  /** 组ID（便于商户接口使用） */
  private Long receiverGroupId;

  /** 商户号 */
  private String mchNo;

  /** 应用ID */
  private String appId;

  /** 支付接口代码 */
  private String ifCode;

  /** 分账接收账号类型: 0-个人(对私) 1-商户(对公) */
  private Byte accType;

  /** 分账接收账号 */
  private String accNo;

  /** 分账接收账号名称 */
  private String accName;

  /** 分账关系类型（参考微信）， 如： SERVICE_PROVIDER 服务商等 */
  private String relationType;

  /** 当选择自定义时，需要录入该字段。 否则为对应的名称 */
  private String relationTypeName;

  /** 渠道特殊信息 */
  private String channelExtInfo;

  /** 绑定成功时间 */
  private Long bindSuccessTime;

  /** 分账比例 */
  private BigDecimal divisionProfit;

  /** 分账状态 1-绑定成功, 0-绑定异常 */
  private Byte bindState;

  /** 支付渠道错误码 */
  private String errCode;

  /** 支付渠道错误信息 */
  private String errMsg;
}
