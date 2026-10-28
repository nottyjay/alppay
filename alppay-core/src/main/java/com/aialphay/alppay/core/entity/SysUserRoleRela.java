package com.aialphay.alppay.core.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 操作员<->角色 关联表
 *
 * @author [mybatis plus generator]
 * @since 2021-04-23
 */
@Schema(description = "操作员<->角色 关联表")
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("t_sys_user_role_rela")
public class SysUserRoleRela implements Serializable {

  private static final long serialVersionUID = 1L;

  /** 用户ID */
  @Schema(title = "userId", description = "用户ID")
  private Long userId;

  /** 角色ID */
  @Schema(title = "roleId", description = "角色ID")
  private String roleId;

  // gw
  public static final LambdaQueryWrapper<SysUserRoleRela> gw() {
    return new LambdaQueryWrapper<>();
  }
}
