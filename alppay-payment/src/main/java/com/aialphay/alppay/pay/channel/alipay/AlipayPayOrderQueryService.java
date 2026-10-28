package com.aialphay.alppay.pay.channel.alipay;

import com.alipay.api.domain.AlipayTradeQueryModel;
import com.alipay.api.request.AlipayTradeQueryRequest;
import com.alipay.api.response.AlipayTradeQueryResponse;
import com.aialphay.alppay.core.constants.CS;
import com.aialphay.alppay.core.entity.PayOrder;
import com.aialphay.alppay.pay.channel.IPayOrderQueryService;
import com.aialphay.alppay.pay.model.MchAppConfigContext;
import com.aialphay.alppay.pay.rqrs.msg.ChannelRetMsg;
import com.aialphay.alppay.pay.service.ConfigContextQueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/*
 * 支付宝 查单接口实现类
 *
 * @Author nottyjay
 * @site https://www.aialphay.com
 * @date 2021/6/8 17:20
 */
@Service
public class AlipayPayOrderQueryService implements IPayOrderQueryService {

  @Autowired private ConfigContextQueryService configContextQueryService;

  @Override
  public String getIfCode() {
    return CS.IF_CODE.ALIPAY;
  }

  @Override
  public ChannelRetMsg query(PayOrder payOrder, MchAppConfigContext mchAppConfigContext) {

    AlipayTradeQueryRequest req = new AlipayTradeQueryRequest();

    // 商户订单号，商户网站订单系统中唯一订单号，必填
    AlipayTradeQueryModel model = new AlipayTradeQueryModel();
    model.setOutTradeNo(payOrder.getPayOrderId());
    req.setBizModel(model);

    // 通用字段
    AlipayKit.putApiIsvInfo(mchAppConfigContext, req, model);

    AlipayTradeQueryResponse resp =
        configContextQueryService.getAlipayClientWrapper(mchAppConfigContext).execute(req);
    String result = resp.getTradeStatus();

    if ("TRADE_SUCCESS".equals(result)) {
      return ChannelRetMsg.confirmSuccess(resp.getTradeNo()); // 支付成功
    } else if ("WAIT_BUYER_PAY".equals(result)) {
      return ChannelRetMsg.waiting(); // 支付中
    }
    return ChannelRetMsg.waiting(); // 支付中
  }
}
