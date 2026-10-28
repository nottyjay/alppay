package com.aialphay.alppay.pay.channel.alipay;

import com.aialphay.alppay.core.constants.CS;
import com.aialphay.alppay.core.entity.PayOrder;
import com.aialphay.alppay.pay.channel.AbstractPaymentService;
import com.aialphay.alppay.pay.model.MchAppConfigContext;
import com.aialphay.alppay.pay.rqrs.AbstractRS;
import com.aialphay.alppay.pay.rqrs.payorder.UnifiedOrderRQ;
import com.aialphay.alppay.pay.util.PaywayUtil;
import org.springframework.stereotype.Service;

/*
 * 支付接口： 支付宝官方
 * 支付方式： 自适应
 *
 * @Author nottyjay
 * @site https://www.aialphay.com
 * @date 2021/6/8 17:19
 */
@Service
public class AlipayPaymentService extends AbstractPaymentService {

  @Override
  public String getIfCode() {
    return CS.IF_CODE.ALIPAY;
  }

  @Override
  public boolean isSupport(String wayCode) {
    return true;
  }

  @Override
  public String preCheck(UnifiedOrderRQ rq, PayOrder payOrder) {
    return PaywayUtil.getRealPaywayService(this, payOrder.getWayCode()).preCheck(rq, payOrder);
  }

  @Override
  public AbstractRS pay(
      UnifiedOrderRQ rq, PayOrder payOrder, MchAppConfigContext mchAppConfigContext)
      throws Exception {
    return PaywayUtil.getRealPaywayService(this, payOrder.getWayCode())
        .pay(rq, payOrder, mchAppConfigContext);
  }
}
