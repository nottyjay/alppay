package com.aialphay.alppay.pay.channel.icbcpay;

import com.aialphay.alppay.core.entity.PayOrder;
import com.aialphay.alppay.pay.util.IPUtil;

public class IcbcpayKit {

  /**
   * 获取订单创建者的IP，若不存在则获取本机ip
   * @param payOrder
   * @return
   */
  public static String getOrderCreateIp(PayOrder payOrder) {
    if (payOrder == null && payOrder.getClientIp() == null) {
      return payOrder.getClientIp();
    }
    // 从本机网卡获取IP
    return IPUtil.getLocalIpAddress();
  }
}
