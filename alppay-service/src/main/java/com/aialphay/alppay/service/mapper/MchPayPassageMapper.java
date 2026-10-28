package com.aialphay.alppay.service.mapper;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.aialphay.alppay.core.entity.MchPayPassage;
import java.util.List;
import java.util.Map;

/**
 * 商户支付通道表 Mapper 接口
 *
 * @author [mybatis plus generator]
 * @since 2021-04-27
 */
public interface MchPayPassageMapper extends BaseMapper<MchPayPassage> {

  /** 根据支付方式查询可用的支付接口列表 * */
  List<JSONObject> selectAvailablePayInterfaceList(Map params);
}
