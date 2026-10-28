package com.aialphay.alppay.pay.channel.icbcpay;

import com.aialphay.alppay.core.constants.CS;
import com.aialphay.alppay.core.entity.PayOrder;
import com.aialphay.alppay.pay.channel.IPayOrderReconService;
import com.aialphay.alppay.pay.model.IcbcWrapper;
import com.aialphay.alppay.pay.model.MchAppConfigContext;
import com.aialphay.alppay.pay.rqrs.msg.ChannelReconMsg;
import com.aialphay.alppay.pay.service.ConfigContextQueryService;
import com.icbc.api.request.CardbusinessAggregatepayB2cOnlineOrderqryRequestV1;
import com.icbc.api.response.CardbusinessAggregatepayB2cOnlineOrderqryResponseV1;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 工商银行 自定义对账功能实现类
 *
 * @auther nottyjay
 * @date 2026/1/25
 */
@Slf4j
@Service
public class IcbcPayOrderReconService implements IPayOrderReconService {
  @Autowired public ConfigContextQueryService configContextQueryService;

  @Override
  public String getIfCode() {
    return CS.IF_CODE.ICBC;
  }

  @Override
  public ChannelReconMsg reconciliation(PayOrder payOrder, MchAppConfigContext mchAppConfigContext)
      throws Exception {
    CardbusinessAggregatepayB2cOnlineOrderqryRequestV1 request =
        new CardbusinessAggregatepayB2cOnlineOrderqryRequestV1();
    request.setServiceUrl(
        "https://gw.open.icbc.com.cn/api/cardbusiness/aggregatepay/b2c/online/orderqry/V1");
    CardbusinessAggregatepayB2cOnlineOrderqryRequestV1
            .CardbusinessAggregatepayB2cOnlineOrderqryRequestV1Biz
        bizContent =
            new CardbusinessAggregatepayB2cOnlineOrderqryRequestV1
                .CardbusinessAggregatepayB2cOnlineOrderqryRequestV1Biz();
    bizContent.setDeal_flag("0");

    IcbcWrapper icbcWrapper = configContextQueryService.getIcbcWrapper(mchAppConfigContext);
    bizContent.setMer_id(icbcWrapper.getMchParams().getMerchantId());
    bizContent.setMer_prtcl_no(icbcWrapper.getMchParams().getMerchantId());
    bizContent.setIcbc_appid(icbcWrapper.getMchParams().getAppid());
    bizContent.setOut_trade_no(payOrder.getPayOrderId());
    request.setBizContent(bizContent);
    CardbusinessAggregatepayB2cOnlineOrderqryResponseV1 resp = icbcWrapper.execute(request);
    if (resp == null) {
      return ChannelReconMsg.unknown("查询渠道订单失败");
    }
    //    返回码，交易成功返回0，其他表示业务报错
    if (resp.getReturnCode() != 0) {
      return ChannelReconMsg.unknown("查询交易失败");
    }
    //    deal_flag=0: pay_status说明
    //    支付查询returncode=0，paystatus=0===表示这笔单订单状态为2-支付成功/3-部分退成功/4-全额退成功，
    //    支付查询returncode=0，paystatus=1===表示这笔单订单状态为5-支付失败/8-已关闭，
    //    支付查询returncode=0，paystatus=2===表示这笔单订单状态为7-支付中，
    //    支付查询returncode<>0，===表示查不到记录或者程序报错或者订单状态为其他。
    if ("0".equals(resp.getPay_status())) {
      return ChannelReconMsg.confirmSuccess(resp.getOrder_id(), "渠道确认支付成功", resp.getCard_no());
    } else if ("1".equals(resp.getPay_status())) {
      return ChannelReconMsg.confirmError(resp.getOrder_id(), "渠道确认支付失败/已关闭");
    } else if ("2".equals(resp.getPay_status())) {
      return ChannelReconMsg.waiting(resp.getOrder_id(), "订单已过期，渠道订单还在待支付，需要人工复核");
    } else {
      return ChannelReconMsg.unknown("渠道查询订单状态未知");
    }
  }
}
