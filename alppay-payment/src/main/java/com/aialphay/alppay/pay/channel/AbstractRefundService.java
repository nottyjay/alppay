package com.aialphay.alppay.pay.channel;

import com.aialphay.alppay.pay.service.ConfigContextQueryService;
import com.aialphay.alppay.pay.util.ChannelCertConfigKitBean;
import com.aialphay.alppay.service.impl.SysConfigService;
import org.springframework.beans.factory.annotation.Autowired;

/*
 * 退款接口抽象类
 *
 * @Author nottyjay
 * @site https://www.aialphay.com
 * @date 2021/6/17 9:37
 */
public abstract class AbstractRefundService implements IRefundService {

  @Autowired protected SysConfigService sysConfigService;
  @Autowired protected ChannelCertConfigKitBean channelCertConfigKitBean;
  @Autowired protected ConfigContextQueryService configContextQueryService;

  protected String getNotifyUrl() {
    return sysConfigService.getDBApplicationConfig().getPaySiteUrl()
        + "/api/refund/notify/"
        + getIfCode();
  }

  protected String getNotifyUrl(String refundOrderId) {
    return sysConfigService.getDBApplicationConfig().getPaySiteUrl()
        + "/api/refund/notify/"
        + getIfCode()
        + "/"
        + refundOrderId;
  }
}
