package com.aialphay.alppay.pay.channel;

import com.aialphay.alppay.core.entity.MchDivisionReceiver;
import com.aialphay.alppay.core.entity.PayOrder;
import com.aialphay.alppay.core.entity.PayOrderDivisionRecord;
import com.aialphay.alppay.pay.model.MchAppConfigContext;
import com.aialphay.alppay.pay.rqrs.msg.ChannelRetMsg;

import java.util.HashMap;
import java.util.List;

/**
 * 分账接口
 *
 * @Author nottyjay
 * @site https://www.aialphay.com
 * @date 2021/8/22 08:59
 */
public interface IDivisionService {

  /** 获取到接口code * */
  String getIfCode();

  /** 是否支持该分账 */
  boolean isSupport();

  /** 绑定关系 * */
  ChannelRetMsg bind(
      MchDivisionReceiver mchDivisionReceiver, MchAppConfigContext mchAppConfigContext);

  /** 单次分账 （无需调用完结接口，或自动解冻商户资金) * */
  ChannelRetMsg singleDivision(
      PayOrder payOrder,
      List<PayOrderDivisionRecord> recordList,
      MchAppConfigContext mchAppConfigContext);

  /** 查询分账结果 * */
  HashMap<Long, ChannelRetMsg> queryDivision(
      PayOrder payOrder,
      List<PayOrderDivisionRecord> recordList,
      MchAppConfigContext mchAppConfigContext);

  /** 删除分账关系 * */
  default ChannelRetMsg delete(
      MchDivisionReceiver mchDivisionReceiver, MchAppConfigContext mchAppConfigContext) {
    return null;
  }
}
