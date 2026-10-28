package com.aialphay.alppay.pay.channel;

import com.aialphay.alppay.core.entity.PayOrder;
import com.aialphay.alppay.pay.model.MchAppConfigContext;
import com.aialphay.alppay.pay.rqrs.msg.ChannelRetMsg;

/**
 * 查单（渠道侧）接口定义
 *
 * @Author nottyjay
 * @site https://www.aialphay.com
 * @date 2021/5/19 15:16
 */
public interface IPayOrderQueryService {

  /** 获取到接口code * */
  String getIfCode();

  /** 查询订单 * */
  ChannelRetMsg query(PayOrder payOrder, MchAppConfigContext mchAppConfigContext) throws Exception;
}
