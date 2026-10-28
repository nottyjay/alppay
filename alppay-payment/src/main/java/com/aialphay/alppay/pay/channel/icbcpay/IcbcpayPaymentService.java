package com.aialphay.alppay.pay.channel.icbcpay;

import com.aialphay.alppay.core.constants.CS;
import com.aialphay.alppay.core.entity.PayOrder;
import com.aialphay.alppay.pay.channel.AbstractPaymentService;
import com.aialphay.alppay.pay.model.MchAppConfigContext;
import com.aialphay.alppay.pay.rqrs.AbstractRS;
import com.aialphay.alppay.pay.rqrs.payorder.UnifiedOrderRQ;
import com.aialphay.alppay.pay.service.ConfigContextQueryService;
import com.aialphay.alppay.pay.util.PaywayUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("icbcPaymentService")
@Slf4j
public class IcbcpayPaymentService extends AbstractPaymentService {

  @Autowired
  public ConfigContextQueryService configContextQueryService;

  @Override
  public String getIfCode() {
    return CS.IF_CODE.ICBC;
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
      UnifiedOrderRQ bizRQ, PayOrder payOrder, MchAppConfigContext mchAppConfigContext)
      throws Exception {
    return PaywayUtil.getRealPaywayService(this, payOrder.getWayCode())
        .pay(bizRQ, payOrder, mchAppConfigContext);
  }
}
