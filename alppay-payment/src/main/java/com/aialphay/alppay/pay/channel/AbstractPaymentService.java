package com.aialphay.alppay.pay.channel;

import com.aialphay.alppay.core.constants.CS;
import com.aialphay.alppay.core.entity.PayOrder;
import com.aialphay.alppay.pay.model.MchAppConfigContext;
import com.aialphay.alppay.pay.rqrs.payorder.UnifiedOrderRQ;
import com.aialphay.alppay.pay.service.ConfigContextQueryService;
import com.aialphay.alppay.pay.util.ChannelCertConfigKitBean;
import com.aialphay.alppay.service.impl.SysConfigService;
import org.springframework.beans.factory.annotation.Autowired;

/*
 * 支付接口抽象类
 *
 * @Author nottyjay
 * @site https://www.aialphay.com
 * @date 2021/6/8 17:18
 */
public abstract class AbstractPaymentService implements IPaymentService {

  @Autowired protected SysConfigService sysConfigService;
  @Autowired protected ChannelCertConfigKitBean channelCertConfigKitBean;
  @Autowired protected ConfigContextQueryService configContextQueryService;

  @Override
  public String customPayOrderId(
      UnifiedOrderRQ bizRQ, PayOrder payOrder, MchAppConfigContext mchAppConfigContext) {
    return null; // 使用系统默认支付订单号
  }

  /** 订单分账（一般用作 如微信订单将在下单处做标记） */
  protected boolean isDivisionOrder(PayOrder payOrder) {
    // 订单分账， 将冻结商户资金。
    if (payOrder.getDivisionMode() != null
        && (PayOrder.DIVISION_MODE_AUTO == payOrder.getDivisionMode()
            || PayOrder.DIVISION_MODE_MANUAL == payOrder.getDivisionMode())) {
      return true;
    }
    return false;
  }

  protected String getNotifyUrl() {
    return sysConfigService.getDBApplicationConfig().getPaySiteUrl()
        + "/api/pay/notify/"
        + getIfCode();
  }

  protected String getNotifyUrl(String payOrderId) {
    return sysConfigService.getDBApplicationConfig().getPaySiteUrl()
        + "/api/pay/notify/"
        + getIfCode()
        + "/"
        + payOrderId;
  }

  protected String getReturnUrl() {
    return sysConfigService.getDBApplicationConfig().getPaySiteUrl()
        + "/api/pay/return/"
        + getIfCode();
  }

  protected String getReturnUrl(String payOrderId) {
    return sysConfigService.getDBApplicationConfig().getPaySiteUrl()
        + "/api/pay/return/"
        + getIfCode()
        + "/"
        + payOrderId;
  }

  protected String getReturnUrlOnlyJump(String payOrderId) {
    return sysConfigService.getDBApplicationConfig().getPaySiteUrl()
        + "/api/pay/return/"
        + getIfCode()
        + "/"
        + CS.PAY_RETURNURL_FIX_ONLY_JUMP_PREFIX
        + payOrderId;
  }
}
