package com.aialphay.alppay.service.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.aialphay.alppay.core.entity.MchNotifyRecord;
import org.apache.ibatis.annotations.Param;

/**
 * 商户通知表 Mapper 接口
 *
 * @author [mybatis plus generator]
 * @since 2021-04-27
 */
public interface MchNotifyRecordMapper extends BaseMapper<MchNotifyRecord> {

  Integer updateNotifyResult(
      @Param("notifyId") Long notifyId,
      @Param("state") Byte state,
      @Param("resResult") String resResult);

  /*
   * 功能描述: 更改为通知中 & 增加允许重发通知次数
   * @param notifyId
   * @Author: nottyjay
   * @Date: 2021/6/21 17:38
   */
  Integer updateIngAndAddNotifyCountLimit(@Param("notifyId") Long notifyId);
}
