package com.aialphay.alppay.service.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.aialphay.alppay.core.entity.PayOrder;
import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Param;

/**
 * 支付订单表 Mapper 接口
 *
 * @author [mybatis plus generator]
 * @since 2021-04-27
 */
public interface PayOrderMapper extends BaseMapper<PayOrder> {

  Map payCount(Map param);

  List<Map> payTypeCount(Map param);

  List<Map> selectOrderCount(Map param);

  /** 更新订单退款金额和次数 * */
  int updateRefundAmountAndCount(
      @Param("payOrderId") String payOrderId,
      @Param("currentRefundAmount") Long currentRefundAmount);
}
