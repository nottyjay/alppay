package com.aialphay.alppay.pay.channel.icbcpay.payway;

import com.aialphay.alppay.core.entity.PayOrder;
import com.aialphay.alppay.pay.channel.icbcpay.IcbcpayPaymentService;
import com.aialphay.alppay.pay.model.IcbcWrapper;
import com.aialphay.alppay.pay.model.MchAppConfigContext;
import com.aialphay.alppay.pay.rqrs.AbstractRS;
import com.aialphay.alppay.pay.rqrs.msg.ChannelRetMsg;
import com.aialphay.alppay.pay.rqrs.payorder.UnifiedOrderRQ;
import com.aialphay.alppay.pay.rqrs.payorder.payway.IcbcEasyH5OrderRS;
import com.aialphay.alppay.pay.util.ApiResBuilder;
import com.icbc.api.request.CardbusinessEpayh5UiConsumptionRequestV1;
import com.icbc.api.request.CardbusinessEpayh5UiConsumptionRequestV1.CardbusinessEpayh5UiConsumptionRequestV1Biz;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import static cn.hutool.core.date.DatePattern.PURE_DATETIME_FORMAT;

@Slf4j
@Service("icbcpaymentByIcbcEasyH5Service")
public class IcbcEasyH5 extends IcbcpayPaymentService {

  @Override
  public String preCheck(UnifiedOrderRQ rq, PayOrder payOrder) {
    return null;
  }

  @Override
  public AbstractRS pay(UnifiedOrderRQ bizRQ, PayOrder payOrder, MchAppConfigContext mchAppConfigContext) throws Exception {
    CardbusinessEpayh5UiConsumptionRequestV1 request = new CardbusinessEpayh5UiConsumptionRequestV1();
    request.setServiceUrl("https://gw.open.icbc.com.cn/ui/cardbusiness/epayh5/ui/consumption/V1");
    CardbusinessEpayh5UiConsumptionRequestV1Biz bizContent = new CardbusinessEpayh5UiConsumptionRequestV1Biz();
    request.setBizContent(bizContent);
    bizContent.setOrder_date(PURE_DATETIME_FORMAT.format(payOrder.getCreatedAt()));
    bizContent.setOrder_id(payOrder.getPayOrderId());
    bizContent.setAmount(payOrder.getAmount().toString());
    bizContent.setInstallment_times("1");
    bizContent.setCur_type("001");
    bizContent.setMerURL(getNotifyUrl());
    bizContent.setReturn_url(getReturnUrlOnlyJump(payOrder.getPayOrderId()));
    bizContent.setH5_flag("1");
    bizContent.setOrder_interval("1800");
    IcbcWrapper icbcWrapper = configContextQueryService.getIcbcWrapper(mchAppConfigContext);
    bizContent.setMer_id(icbcWrapper.getMchParams().getMerchantId());
    bizContent.setMer_prtcl_no(icbcWrapper.getMchParams().getMerchantId());
    bizContent.setIcbc_appid(icbcWrapper.getMchParams().getAppid());
    String result = icbcWrapper.executeUI(request);
    IcbcEasyH5OrderRS res = ApiResBuilder.buildSuccess(IcbcEasyH5OrderRS.class);

    res.setFormContent(result);

    ChannelRetMsg channelRetMsg = new ChannelRetMsg();
    res.setChannelRetMsg(channelRetMsg);
    channelRetMsg.setChannelState(ChannelRetMsg.ChannelState.WAITING);
    return res;
  }
}
