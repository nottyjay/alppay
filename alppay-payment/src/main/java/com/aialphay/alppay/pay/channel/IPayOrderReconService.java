package com.aialphay.alppay.pay.channel;

import com.aialphay.alppay.core.entity.PayOrder;
import com.aialphay.alppay.pay.model.MchAppConfigContext;
import com.aialphay.alppay.pay.rqrs.msg.ChannelReconMsg;

/**
 * 对账（渠道侧）接口定义
 *
 * @Author xiaocui
 * @date 2026/6/3 15:16
 */
public interface IPayOrderReconService {

    /** 获取到接口code * */
    String getIfCode();

    /** 渠道对账结果 * */
    ChannelReconMsg reconciliation(PayOrder payOrder, MchAppConfigContext mchAppConfigContext) throws Exception;
}
