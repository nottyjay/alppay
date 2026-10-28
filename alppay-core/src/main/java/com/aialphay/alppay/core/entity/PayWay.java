package com.aialphay.alppay.core.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.aialphay.alppay.core.model.BaseModel;
import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 支付方式表
 *
 * @author [mybatis plus generator]
 * @since 2021-04-27
 */
@Schema(description = "支付方式表")
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("t_pay_way")
public class PayWay extends BaseModel implements Serializable {

  private static final long serialVersionUID = 1L;

  /** 支付方式代码 例如： wxpay_jsapi */
  @Schema(title = "wayCode", description = "支付方式代码  例如： wxpay_jsapi")
  @TableId
  private String wayCode;

  /** 支付方式名称 */
  @Schema(title = "wayName", description = "支付方式名称")
  private String wayName;

  /** 创建时间 */
  @Schema(title = "createdAt", description = "创建时间")
  private Date createdAt;

  /** 更新时间 */
  @Schema(title = "updatedAt", description = "更新时间")
  private Date updatedAt;

  public static final LambdaQueryWrapper<PayWay> gw() {
    return new LambdaQueryWrapper<>();
  }
}
