package com.aialphay.alppay.pay.channel.icbcpay;

import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSONObject;
import com.aialphay.alppay.core.constants.CS;
import com.aialphay.alppay.core.entity.PayOrder;
import com.aialphay.alppay.core.exception.ResponseException;
import com.aialphay.alppay.core.model.params.icbc.IcbcNormalMchParams;
import com.aialphay.alppay.pay.channel.AbstractChannelNoticeService;
import com.aialphay.alppay.pay.model.MchAppConfigContext;
import com.aialphay.alppay.pay.rqrs.msg.ChannelRetMsg;
import com.icbc.api.IcbcConstants;
import com.icbc.api.utils.IcbcSignature;
import com.icbc.api.utils.WebUtils;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.MutablePair;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.TreeMap;

/**
 * 工行支付渠道通知服务
 */
@Service
@Slf4j
public class IcbcChannelNoticeService extends AbstractChannelNoticeService {

  @Override
  public String getIfCode() {
    return CS.IF_CODE.ICBC;
  }

  @Override
  public MutablePair<String, Object> parseParams(HttpServletRequest request, String urlOrderId, NoticeTypeEnum noticeTypeEnum) {

    try{
      JSONObject params = getReqParamJSON();
      if(log.isDebugEnabled()){
        log.debug(params.toString());
      }
      String payOrderId = params.getJSONObject("biz_content").getString("out_trade_no");
      return MutablePair.of(payOrderId, params);
    } catch (Exception e) {
      log.error("error", e);
      throw ResponseException.buildText("ERROR");
    }
  }

  @Override
  public ChannelRetMsg doNotice(HttpServletRequest request, Object params, PayOrder payOrder, MchAppConfigContext mchAppConfigContext, NoticeTypeEnum noticeTypeEnum) {
    IcbcNormalMchParams icbcNormalMchParams = (IcbcNormalMchParams) configContextQueryService.queryNormalMchParams(mchAppConfigContext.getMchNo(), mchAppConfigContext.getAppId(), getIfCode());
    String apiGatewayPublicKey = icbcNormalMchParams.getApiGatewayPublicKey();

    try{
      JSONObject requestParams = (JSONObject) params;
      String sign = requestParams.getString("sign");
      boolean passed = false;
      String requestURI = request.getRequestURI();
      Map<String, String> signParams = new TreeMap<>(); // TreeMap已经保证了按键升序排序
      signParams.put("api", requestParams.getString("api"));
      signParams.put("app_id", requestParams.getString("app_id"));
      signParams.put("biz_content", requestParams.getString("biz_content"));
      signParams.put("charset", requestParams.getString("charset"));
      signParams.put("format", requestParams.getString("format"));
      signParams.put("from", requestParams.getString("from"));
      signParams.put("sign_type", requestParams.getString("sign_type"));
      signParams.put("timestamp", requestParams.getString("timestamp"));
      String signContent = WebUtils.buildOrderedSignStr(requestURI, signParams);
      passed = IcbcSignature.verify(signContent, "RSA", apiGatewayPublicKey, IcbcConstants.CHARSET_UTF8, sign);
      if(!passed){
        throw ResponseException.buildText("ERROR");
      }
      JSONObject biz_content = requestParams.getJSONObject("biz_content");
      Map<String, Object> callParams = new TreeMap<>();
      callParams.put("return_code", 0);
      callParams.put("return_msg", "success");
      callParams.put("msg_id", biz_content.getString("msg_id"));
      Map<String, Object> resultMap = new LinkedHashMap<>();
      resultMap.put("response_biz_content", JSONUtil.toJsonStr(callParams));
      resultMap.put("sign_type", "RSA2");
      signContent = JSONUtil.toJsonStr(resultMap);
      sign = IcbcSignature.sign(signContent, IcbcConstants.SIGN_TYPE_RSA2, icbcNormalMchParams.getPrivateKey(), IcbcConstants.CHARSET_UTF8);
      resultMap.put("sign", sign);
      ResponseEntity okResopnse = jsonResp(resultMap);
      ChannelRetMsg result = new ChannelRetMsg();
//      上游渠道返回错误码，0：支付成功，其他：支付失败
      String returnCode = biz_content.getString("return_code");

      result.setChannelUserId(biz_content.getString("card_no"));
      result.setChannelOrderId(biz_content.getString("order_id"));
      result.setChannelErrCode(returnCode);

      if (!StringUtils.equals("0", returnCode)) {
//        确认失败
        result.setChannelState(ChannelRetMsg.ChannelState.CONFIRM_FAIL);
        result.setChannelErrMsg(biz_content.getString("return_msg"));
      } else {
//        确认支付成功，通知充值
        result.setChannelState(ChannelRetMsg.ChannelState.CONFIRM_SUCCESS);
      }
      result.setChannelOriginResponse(requestParams.toString());
      result.setResponseEntity(okResopnse);

      return result;
    } catch (Exception e) {
      log.error("error", e);
      throw ResponseException.buildText("ERROR");
    }
  }

}
