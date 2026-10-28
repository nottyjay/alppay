package com.aialphay.alppay.pay.channel.icbcpay.payway;

import cn.hutool.core.date.DateUtil;
import com.aialphay.alppay.core.entity.PayOrder;
import com.aialphay.alppay.core.utils.AmountUtil;
import com.aialphay.alppay.pay.channel.icbcpay.IcbcpayKit;
import com.aialphay.alppay.pay.channel.icbcpay.IcbcpayPaymentService;
import com.aialphay.alppay.pay.model.IcbcWrapper;
import com.aialphay.alppay.pay.model.MchAppConfigContext;
import com.aialphay.alppay.pay.rqrs.AbstractRS;
import com.aialphay.alppay.pay.rqrs.payorder.UnifiedOrderRQ;
import com.aialphay.alppay.pay.rqrs.payorder.payway.IcbcQrOrderRQ;
import com.icbc.api.request.QrcodeGenerateRequestV2;
import com.icbc.api.request.QrcodeGenerateRequestV2.QrcodeGenerateRequestV2Biz;
import com.icbc.api.response.QrcodeGenerateResponseV2;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service("icbcpaymentByIcbcQrService")
public class IcbcQr extends IcbcpayPaymentService {

  @Override
  public String preCheck(UnifiedOrderRQ rq, PayOrder payOrder) {
    return null;
  }

  @Override
  public AbstractRS pay(
      UnifiedOrderRQ bizRQ, PayOrder payOrder, MchAppConfigContext mchAppConfigContext)
      throws Exception {
    IcbcQrOrderRQ icbcQrOrderRQ = (IcbcQrOrderRQ) bizRQ;

    QrcodeGenerateRequestV2 request = new QrcodeGenerateRequestV2();
    QrcodeGenerateRequestV2Biz bizContent = new QrcodeGenerateRequestV2Biz();
    bizContent.setOutTradeNo(payOrder.getPayOrderId());
    bizContent.setOrderAmt(AmountUtil.convertCent2Dollar(payOrder.getAmount().toString()));
    bizContent.setPayExpire(String.valueOf(DateUtil.betweenMs(new Date(), payOrder.getExpiredTime()) / 1000));
    bizContent.setNotifyUrl(getNotifyUrl());
    bizContent.setNotifyFlag("1");
    bizContent.setTradeDate(DateUtil.formatDate(payOrder.getCreatedAt()));
    bizContent.setTradeTime(DateUtil.formatTime(payOrder.getCreatedAt()));
    bizContent.setTporderCreateIp(IcbcpayKit.getOrderCreateIp(payOrder));
    request.setBizContent(bizContent);

    IcbcWrapper icbcWrapper = configContextQueryService.getIcbcWrapper(mchAppConfigContext);
    QrcodeGenerateResponseV2 response = icbcWrapper.execute(request);

    return null;
  }
}
