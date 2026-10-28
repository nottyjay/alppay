package com.aialphay.alppay.pay.channel.ysfpay.payway;

import cn.hutool.core.date.DateUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.aialphay.alppay.core.entity.PayOrder;
import com.aialphay.alppay.core.exception.BizException;
import com.aialphay.alppay.pay.channel.ysfpay.YsfpayPaymentService;
import com.aialphay.alppay.pay.model.MchAppConfigContext;
import com.aialphay.alppay.pay.rqrs.AbstractRS;
import com.aialphay.alppay.pay.rqrs.msg.ChannelRetMsg;
import com.aialphay.alppay.pay.rqrs.payorder.UnifiedOrderRQ;
import com.aialphay.alppay.pay.rqrs.payorder.payway.AliJsapiOrderRQ;
import com.aialphay.alppay.pay.rqrs.payorder.payway.AliJsapiOrderRS;
import com.aialphay.alppay.pay.util.ApiResBuilder;
import java.util.Date;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

/*
 * 云闪付 支付宝 jsapi
 *
 * @Author nottyjay
 * @site https://www.aialphay.com
 * @date 2021/6/8 18:11
 */
@Service("ysfpayPaymentByAliJsapiService") // Service Name需保持全局唯一性
public class AliJsapi extends YsfpayPaymentService {

  @Override
  public String preCheck(UnifiedOrderRQ rq, PayOrder payOrder) {

    AliJsapiOrderRQ bizRQ = (AliJsapiOrderRQ) rq;
    if (StringUtils.isEmpty(bizRQ.getBuyerUserId())) {
      throw new BizException("[buyerUserId]不可为空");
    }
    return null;
  }

  @Override
  public AbstractRS pay(
      UnifiedOrderRQ rq, PayOrder payOrder, MchAppConfigContext mchAppConfigContext)
      throws Exception {
    String logPrefix = "【云闪付(alipayJs)jsapi支付】";
    JSONObject reqParams = new JSONObject();
    AliJsapiOrderRS res = ApiResBuilder.buildSuccess(AliJsapiOrderRS.class);
    ChannelRetMsg channelRetMsg = new ChannelRetMsg();
    res.setChannelRetMsg(channelRetMsg);

    // 请求参数赋值
    jsapiParamsSet(reqParams, payOrder, getNotifyUrl(), getReturnUrl());

    AliJsapiOrderRQ bizRQ = (AliJsapiOrderRQ) rq;
    // 云闪付扫一扫支付， 需要传入buyerUserId参数
    reqParams.put("userId", bizRQ.getBuyerUserId()); // buyerUserId

    // 客户端IP
    reqParams.put("customerIp", StringUtils.defaultIfEmpty(payOrder.getClientIp(), "127.0.0.1"));

    // 发送请求并返回订单状态
    JSONObject resJSON =
        packageParamAndReq(
            "/gateway/api/pay/unifiedorder", reqParams, logPrefix, mchAppConfigContext);
    // 请求 & 响应成功， 判断业务逻辑
    String respCode = resJSON.getString("respCode"); // 应答码
    String respMsg = resJSON.getString("respMsg"); // 应答信息
    try {

      // 00-交易成功， 02-用户支付中 , 12-交易重复， 需要发起查询处理    其他认为失败
      if ("00".equals(respCode)) {
        // 付款信息
        JSONObject payDataJSON = JSON.parseObject(resJSON.getString("payData"));
        String tradeNo = "";

        if (StringUtils.isNotBlank(payDataJSON.getString("tradeNo"))) {
          tradeNo = payDataJSON.getString("tradeNo");
        } else {
          String prepayId = payDataJSON.getString("prepayId");
          if (prepayId != null
              && prepayId.length() > 2
              && !prepayId.startsWith(DateUtil.format(new Date(), "yyyy"))) {
            tradeNo = prepayId.substring(2);
          } else {
            tradeNo = prepayId;
          }
        }
        res.setAlipayTradeNo(tradeNo);
        res.setPayData(payDataJSON.toJSONString());
        channelRetMsg.setChannelState(ChannelRetMsg.ChannelState.WAITING);
      }
    } catch (Exception e) {
      channelRetMsg.setChannelErrCode(respCode);
      channelRetMsg.setChannelErrMsg(respMsg);
    }
    return res;
  }
}
