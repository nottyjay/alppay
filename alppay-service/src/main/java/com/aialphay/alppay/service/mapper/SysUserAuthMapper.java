package com.aialphay.alppay.service.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import com.aialphay.alppay.core.entity.SysUserAuth;

/**
 * 操作员认证表 Mapper 接口
 *
 * @author [mybatis plus generator]
 * @since 2020-06-13
 */
public interface SysUserAuthMapper extends BaseMapper<SysUserAuth> {

  SysUserAuth selectByLogin(
      @Param("identifier") String identifier,
      @Param("identityType") Byte identityType,
      @Param("sysType") String sysType);
}
