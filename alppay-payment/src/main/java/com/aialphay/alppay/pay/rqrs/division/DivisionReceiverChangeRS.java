package com.aialphay.alppay.pay.rqrs.division;

import com.aialphay.alppay.core.entity.MchDivisionReceiver;
import com.aialphay.alppay.pay.rqrs.AbstractRS;
import java.math.BigDecimal;
import lombok.Data;
import org.springframework.beans.BeanUtils;

/*
 * 绑定账户 响应参数
 *
 * @Author nottyjay
 * @site https://www.aialphay.com
 * @date 2025-12-06 16:00:34
 */
@Data
public class DivisionReceiverChangeRS extends AbstractRS {

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

  /** 分账比例 */
  private BigDecimal divisionProfit;

  /** 支付渠道错误码 */
  private String errCode;

  /** 支付渠道错误信息 */
  private String errMsg;

  public static DivisionReceiverChangeRS buildByRecord(MchDivisionReceiver record) {

    if (record == null) {
      return null;
    }

    DivisionReceiverChangeRS result = new DivisionReceiverChangeRS();
    BeanUtils.copyProperties(record, result);

    return result;
  }
}
