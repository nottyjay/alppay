package com.aialphay.alppay.service.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.aialphay.alppay.core.entity.SysEntitlement;
import org.apache.ibatis.annotations.Param;

/**
 * 系统权限表 Mapper 接口
 *
 * @author [mybatis plus generator]
 * @since 2020-06-13
 */
public interface SysEntitlementMapper extends BaseMapper<SysEntitlement> {

  Integer userHasLeftMenu(@Param("userId") Long userId, @Param("sysType") String sysType);
}
