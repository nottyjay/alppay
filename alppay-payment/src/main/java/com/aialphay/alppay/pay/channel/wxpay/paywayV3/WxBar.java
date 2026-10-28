package com.aialphay.alppay.pay.channel.wxpay.paywayV3;

import com.aialphay.alppay.core.entity.PayOrder;
import com.aialphay.alppay.pay.channel.wxpay.WxpayPaymentService;
import com.aialphay.alppay.pay.model.MchAppConfigContext;
import com.aialphay.alppay.pay.rqrs.AbstractRS;
import com.aialphay.alppay.pay.rqrs.payorder.UnifiedOrderRQ;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/*
 * 微信 条码支付
 *
 * @Author nottyjay
 * @site https://www.aialphay.com
 * @date 2021/6/8 18:08
 */
@Service("wxpayPaymentByBarV3Service") // Service Name需保持全局唯一性
public class WxBar extends WxpayPaymentService {

  @Autowired private com.aialphay.alppay.pay.channel.wxpay.payway.WxBar wxBar;

  @Override
  public String preCheck(UnifiedOrderRQ rq, PayOrder payOrder) {
    return wxBar.preCheck(rq, payOrder);
  }

  @Override
  public AbstractRS pay(
      UnifiedOrderRQ rq, PayOrder payOrder, MchAppConfigContext mchAppConfigContext)
      throws Exception {
    return wxBar.pay(rq, payOrder, mchAppConfigContext);
  }
}
