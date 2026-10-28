package com.aialphay.alppay.core.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 系统角色权限关联表
 *
 * @author [mybatis plus generator]
 * @since 2021-04-23
 */
@Schema(description = "系统角色权限关联表")
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("t_sys_role_ent_rela")
public class SysRoleEntRela implements Serializable {

  private static final long serialVersionUID = 1L;

  /** 角色ID */
  @Schema(title = "roleId", description = "角色ID")
  private String roleId;

  /** 权限ID */
  @Schema(title = "entId", description = "权限ID")
  private String entId;

  // gw
  public static final LambdaQueryWrapper<SysRoleEntRela> gw() {
    return new LambdaQueryWrapper<>();
  }
}
