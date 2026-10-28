package com.aialphay.alppay.service.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.aialphay.alppay.core.entity.RefundOrder;

/**
 * 退款订单表 Mapper 接口
 *
 * @author [mybatis plus generator]
 * @since 2021-04-27
 */
public interface RefundOrderMapper extends BaseMapper<RefundOrder> {

  /** 查询全部退成功金额 * */
  Long sumSuccessRefundAmount(String payOrderId);
}
