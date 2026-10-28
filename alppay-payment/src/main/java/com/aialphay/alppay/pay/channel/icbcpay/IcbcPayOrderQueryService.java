package com.aialphay.alppay.pay.channel.icbcpay;

import com.aialphay.alppay.core.constants.CS;
import com.aialphay.alppay.core.entity.PayOrder;
import com.aialphay.alppay.pay.channel.IPayOrderQueryService;
import com.aialphay.alppay.pay.model.IcbcWrapper;
import com.aialphay.alppay.pay.model.MchAppConfigContext;
import com.aialphay.alppay.pay.rqrs.msg.ChannelRetMsg;
import com.aialphay.alppay.pay.service.ConfigContextQueryService;
import com.icbc.api.request.CardbusinessAggregatepayB2cOnlineOrderqryRequestV1;
import com.icbc.api.request.CardbusinessAggregatepayB2cOnlineOrderqryRequestV1.CardbusinessAggregatepayB2cOnlineOrderqryRequestV1Biz;
import com.icbc.api.response.CardbusinessAggregatepayB2cOnlineOrderqryResponseV1;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 工商银行 查单接口实现类
 * @auther nottyjay
 * @date 2026/1/25
 */
@Slf4j
@Service
public class IcbcPayOrderQueryService implements IPayOrderQueryService {

  @Autowired
  public ConfigContextQueryService configContextQueryService;

  @Override
  public String getIfCode() {
    return CS.IF_CODE.ICBC;
  }

  @Override
  public ChannelRetMsg query(PayOrder payOrder, MchAppConfigContext mchAppConfigContext) throws Exception {
    CardbusinessAggregatepayB2cOnlineOrderqryRequestV1 request = new CardbusinessAggregatepayB2cOnlineOrderqryRequestV1();
    request.setServiceUrl("https://gw.open.icbc.com.cn/api/cardbusiness/aggregatepay/b2c/online/orderqry/V1");
    CardbusinessAggregatepayB2cOnlineOrderqryRequestV1Biz bizContent = new CardbusinessAggregatepayB2cOnlineOrderqryRequestV1Biz();
//    bizContent.setOrder_id(payOrder.getChannelOrderNo());
    bizContent.setDeal_flag("0");

    IcbcWrapper icbcWrapper = configContextQueryService.getIcbcWrapper(mchAppConfigContext);
    bizContent.setMer_id(icbcWrapper.getMchParams().getMerchantId());
    bizContent.setMer_prtcl_no(icbcWrapper.getMchParams().getMerchantId());
    bizContent.setIcbc_appid(icbcWrapper.getMchParams().getAppid());
    bizContent.setOut_trade_no(payOrder.getPayOrderId());
    request.setBizContent(bizContent);
    CardbusinessAggregatepayB2cOnlineOrderqryResponseV1 resp = icbcWrapper.execute(request);
    if(resp == null) {
      return ChannelRetMsg.unknown();
    }
//    返回码，交易成功返回0，其他表示业务报错
    if (resp.getReturnCode() != 0) {
      return ChannelRetMsg.unknown();
    }
//    deal_flag=0: pay_status说明
//    支付查询returncode=0，paystatus=0===表示这笔单订单状态为2-支付成功/3-部分退成功/4-全额退成功，
//    支付查询returncode=0，paystatus=1===表示这笔单订单状态为5-支付失败/8-已关闭，
//    支付查询returncode=0，paystatus=2===表示这笔单订单状态为7-支付中，
//    支付查询returncode<>0，===表示查不到记录或者程序报错或者订单状态为其他。
    if("0".equals(resp.getPay_status())){
      return ChannelRetMsg.confirmSuccess(resp.getOrder_id());
    }else if("1".equals(resp.getPay_status())){
      return ChannelRetMsg.confirmFail(resp.getOrder_id());
    }else if("2".equals(resp.getPay_status())){
      return ChannelRetMsg.waiting();
    } else {
      return ChannelRetMsg.unknown();
    }
  }
}
