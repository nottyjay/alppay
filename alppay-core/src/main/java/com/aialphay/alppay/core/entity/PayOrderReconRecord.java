package com.aialphay.alppay.core.entity;

import com.aialphay.alppay.core.model.BaseModel;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * 对账记录表
 *
 * @author [xiaocui]
 * @since 2026-06-03
 */
@Schema(description = "对账记录表")
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("t_pay_order_recon_record")
public class PayOrderReconRecord extends BaseModel implements Serializable {

  // 订单对账情况:0-无异常,1-状态不一致
  public static final byte RECONCILE_NORMAL = 0; // 无异常（对账成功）
  public static final byte RECONCILE_STATUS_ERROR = 1; // 渠道支付成功但系统订单状态不正确

  // 处理方案：0-无需处理,1-系统自动修正,2-需要人工处理
  public static final byte HANDLE_NO_ACTION = 0; // 无需处理
  public static final byte HANDLE_SYS = 1; // 系统自动修正
  public static final byte HANDLE_MANUAL = 2; // 需要人工处理

  /** 对账记录ID */
  @Schema(title = "reconId", description = "对账记录ID")
  @TableId(value = "recon_id", type = IdType.AUTO)
  private Long reconId;

  /** 订单ID */
  @Schema(title = "orderId", description = "订单ID")
  private String orderId;

  /** 渠道订单号 */
  @Schema(title = "channelOrderNo", description = "渠道订单号")
  private String channelOrderNo;

  /** 对账情况：0-无异常，1-金额不一致，2-状态不一致 */
  @Schema(title = "state", description = "对账情况：0-无异常，1-渠道支付成功但系统订单状态不正确")
  private Byte state;

  /** 处理方案：0-无需处理，1-系统自动修正，2-需要人工决策 */
  @Schema(title = "handleStrategy", description = "处理方案：0-无需处理，1-系统自动修正，2-需要人工决策")
  private Byte handleStrategy;

  /** 备注 */
  @Schema(title = "remark", description = "备注")
  private String remark;

  /** 创建时间 */
  @Schema(title = "createdAt", description = "创建时间")
  private Date createdAt;

  /** 更新时间 */
  @Schema(title = "updatedAt", description = "更新时间")
  private Date updatedAt;

  /** 商户号 */
  @Schema(title = "mchNo", description = "商户号")
  private String mchNo;

  // gw
  public static final LambdaQueryWrapper<PayOrderReconRecord> gw() {
    return new LambdaQueryWrapper<>();
  }
}
