package com.aialphay.alppay.pay.channel;

import com.aialphay.alppay.core.entity.PayOrder;
import com.aialphay.alppay.pay.model.MchAppConfigContext;
import com.aialphay.alppay.pay.rqrs.msg.ChannelRetMsg;

/**
 * 关闭订单（渠道侧）接口定义
 *
 * @author xiaoyu
 * @site https://www.aialphay.com
 * @date 2022/1/24 17:23
 */
public interface IPayOrderCloseService {

  /** 获取到接口code * */
  String getIfCode();

  /** 查询订单 * */
  ChannelRetMsg close(PayOrder payOrder, MchAppConfigContext mchAppConfigContext) throws Exception;
}
