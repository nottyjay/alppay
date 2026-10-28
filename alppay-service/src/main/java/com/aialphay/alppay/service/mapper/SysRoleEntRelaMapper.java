package com.aialphay.alppay.service.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.aialphay.alppay.core.entity.SysRoleEntRela;
import java.util.List;
import org.apache.ibatis.annotations.Param;

/**
 * 系统角色权限关联表 Mapper 接口
 *
 * @author [mybatis plus generator]
 * @since 2020-06-13
 */
public interface SysRoleEntRelaMapper extends BaseMapper<SysRoleEntRela> {

  List<String> selectEntIdsByUserId(@Param("userId") Long userId, @Param("sysType") String sysType);
}
