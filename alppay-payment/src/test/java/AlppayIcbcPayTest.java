import cn.hutool.json.JSONUtil;
import com.aialphay.alppay.Alppay;
import com.aialphay.alppay.AlppayClient;
import com.aialphay.alppay.core.model.params.icbc.IcbcNormalMchParams;
import com.aialphay.alppay.exception.AlppayException;
import com.aialphay.alppay.model.PayOrderCreateReqModel;
import com.aialphay.alppay.pay.model.IcbcWrapper;
import com.aialphay.alppay.pay.model.MchAppConfigContext;
import com.aialphay.alppay.pay.rqrs.msg.ChannelRetMsg;
import com.aialphay.alppay.pay.service.ConfigContextQueryService;
import com.aialphay.alppay.request.PayOrderCreateRequest;
import com.aialphay.alppay.response.PayOrderCreateResponse;
import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONWriter;
import com.icbc.api.IcbcApiException;
import com.icbc.api.IcbcConstants;
import com.icbc.api.crypt.RSA;
import com.icbc.api.request.CardbusinessAggregatepayB2cOnlineOrderqryRequestV1;
import com.icbc.api.response.CardbusinessAggregatepayB2cOnlineOrderqryResponseV1;
import com.icbc.api.utils.IcbcSignature;
import com.icbc.api.utils.WebUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;

public class AlppayIcbcPayTest {

  static final Logger _log = LoggerFactory.getLogger(AlppayIcbcPayTest.class);

  public static void main(String[] args) throws IcbcApiException {
    //    payTest();
    //    verifySignTest();
    //    notifyResultTest();
    queryTest();
  }

  private static void payTest() {
    String appid = "69818e1de4b05a12b112eaee";
    String apiKey =
        "oXMis8A5rPsJUn6wSrCUc9Ttdn615XHj90pLxarwmLC51cUfz4ZG3XTDXakcNBHOwqLaHYCv6F13zj8I7X6z7REziLTWUxBwPekQKR6nsHc0CjuMaTpMtsZLSy5FwnQX";
    String apiBase = "https://payment.aiimr.com";
    String mchNo = "M1770098186";
    Alppay.appId = appid;
    Alppay.apiKey = apiKey;
    Alppay.setApiBase(apiBase);
    AlppayClient alppayClient =
        AlppayClient.getInstance(Alppay.appId, Alppay.apiKey, Alppay.getApiBase());

    String wayCode = "ICBC_EASY_H5"; // 支付方式
    PayOrderCreateRequest request = new PayOrderCreateRequest();
    PayOrderCreateReqModel model = new PayOrderCreateReqModel();
    model.setMchNo(mchNo); // 商户号
    model.setAppId(alppayClient.getAppId()); // 应用ID
    String orderNo = "mho" + new Date().getTime();
    model.setMchOrderNo(orderNo); // 商户订单号
    model.setWayCode(wayCode); // 支付方式
    model.setAmount(1l); // 金额，单位分
    model.setCurrency("CNY"); // 币种，目前只支持cny
    model.setClientIp("192.166.1.132"); // 发起支付请求客户端的IP地址
    model.setSubject("商品标题"); // 商品标题
    model.setBody("商品描述"); // 商品描述
    model.setNotifyUrl("https://www.aialphay.com"); // 异步通知地址
    model.setReturnUrl(""); // 前端跳转地址
    model.setChannelExtra(""); // 渠道扩展参数
    model.setExtParam(""); // 商户扩展参数,回调时原样返回

    request.setBizModel(model);
    try {
      PayOrderCreateResponse response = alppayClient.execute(request);
      _log.info("验签结果：{}", response.checkSign(Alppay.apiKey));
      // 下单成功
      if (response.isSuccess(Alppay.apiKey)) {
        String payOrderId = response.get().getPayOrderId();
        _log.info("payOrderId：{}", payOrderId);
        _log.info("mchOrderNo：{}", response.get().getMchOrderNo());
        _log.info("payData：{}", response.get().getPayData());
        _log.info("全部记录：{}", JSONUtil.toJsonStr(response));
      } else {
        _log.info("下单失败：{}", orderNo);
        _log.info("通道错误码：{}", response.get().getErrCode());
        _log.info("通道错误信息：{}", response.get().getErrMsg());
      }
    } catch (AlppayException e) {
      _log.error(e.getMessage());
    }
  }

  private static void queryTest() {

    CardbusinessAggregatepayB2cOnlineOrderqryRequestV1 request =
        new CardbusinessAggregatepayB2cOnlineOrderqryRequestV1();
    request.setServiceUrl(
        "https://gw.open.icbc.com.cn/api/cardbusiness/aggregatepay/b2c/online/orderqry/V1");

    IcbcNormalMchParams icbcNormalMchParams = new IcbcNormalMchParams();
    icbcNormalMchParams.setAppid("11000000000000066903");
    icbcNormalMchParams.setMerchantId("330111990077");
    icbcNormalMchParams.setPrivateKey(
        "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCDkU0yA0MmwNpvPr1ihgcUKTfhbXXvgHOCw8BgFvqFs2ocq4KrdM+7aDZtkU0llJF6FiN58spJR3PgvELHJK5UlGEp3tbkuunCErZ06sYP0eWG/kztcTXz1Y3LpDJn8HUJLPt9oei9kN2D/SaW5xE4QsKJYUaRoqpIp9dQlKiVeD4We/UOb0mYdOkeNCzyiEC23dyNYnyR1qYl5wENEVFwGy/fgiAyRVkzPS7kTljz2iMpscTsFn8QTD5tk3g1YcVFq9roNygbKbEPsUP89ArrGiO+5Gz1Te63jV12VFOoCL9551reRwBvbDv2zyZ8XKpja5ofsPZvEL3yDW36j7xbAgMBAAECggEADlOlvNL3HoK0ppWF6PMgVTUx1RXm2Eg8C9rGGZCXAjUp0mpoxcPoFQfBmYfPh06Y0tsTTOW/2l/yAeBuQyI+643m0Ljn3Ktv5gbEoJGqNLHOFTee5+JClkVEdg7wwDnCfNfNoP4fCnl6bbOE3rzVtrMqXOxfU1+LvHKucyNlAjWYUHSQHTygrqut3xIXrXxYY8Fb0AH4WhSFDLooUA2eqTBjMAUPZJrLE1xnmeCZZt58Dz0Z2GLiwZVMJtYiNXZhvMfeRvypylvseJlBvnpg4cjOwef/Hb9BCR9JPMSVyzSXyDYHNBUnlv5BX9bR9jY3h6V8k42UyGdX7fRv3pdgGQKBgQC5cQxoiRgwxyqfg1S3u4hsG+icMQEV0K6hPDd61B6sWJIO6A67zmahsjA4gAu0znByha1Y8hGEGWEI8bp1yjqs4fFekBhopOcKbgk+0QQ1g8gRUenahxuqCjrazEhwmR2bafSxJQHGce19ytm7xb0XnHlG1wIHIhD/pq1rCDEGWQKBgQC1oKa3qf4emaPN7XOL1EwCTXKjqL3dxph0gGTkLnCVYwtZKc5ZOmCe2sRb6sFCUxT2jAyPy5MwgSY6TcKNT6vr/gKDtAIYHl3P4iVYK7wzdDWyHq3gg9BYgC8S8G77Hi+jhbRNfjZejmxYZlC30aV7ycMOWsZrOIwm7BFwu1Vp0wKBgQCPylHFyPAB+p1ZPJt3MCnTMjzGx9TAlqSiDzAxxMLX/oGan2O1SpEYNjPfy6HIG/7jPjYw7tqYJJmyNsXPSfT/Ut825OKYWa1wz+vJUodB+6Ipk+uR8AuKaQ5D0/0jmgZ0GM+MgFaFCpgNXzo/uE8sPL9pJ5TB9S9bHGIByUNCWQKBgDSCXJCuucQZAQ1w/73GQ69KusDr8PBkEQFleUWq02us9cPyXTDYusRSVQCJ7xOAt4fL13nfDg+w1t92Sa/W/8o7ZnizGwPkC5UlOUyzYDaz+y4ixnwpP5vGe1WLY1qAUJ+C8SySN5/ApzwrYFy0ZJZUL2s//StUbth12VjxH/F3AoGAUAI0F8CSoqqZpgPIHMc/+b/Wpo6DVPyaHJwfPP/s5WFjnDskrSXVWLEBPNij0GMgWWvsorUArWlUQv6Mig6cGXHF7IO2pvpq+yPS/Ty9Ro6bi3/2yfKadu7OgViJK+aSI5bFGh/zjQEBHpCOg2p40APK1Qgcyh8j539tvEFQahY=");
    icbcNormalMchParams.setApiGatewayPublicKey(
        "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCMpjaWjngB4E3ATh+G1DVAmQnIpiPEFAEDqRfNGAVvvH35yDetqewKi0l7OEceTMN1C6NPym3zStvSoQayjYV+eIcZERkx31KhtFu9clZKgRTyPjdKMIth/wBtPKjL/5+PYalLdomM4ONthrPgnkN4x4R0+D4+EBpXo8gNiAFsNwIDAQAB");
    IcbcWrapper icbcWrapper = IcbcWrapper.buildIcbcWrapper(icbcNormalMchParams);

    CardbusinessAggregatepayB2cOnlineOrderqryRequestV1
            .CardbusinessAggregatepayB2cOnlineOrderqryRequestV1Biz
        bizContent =
            new CardbusinessAggregatepayB2cOnlineOrderqryRequestV1
                .CardbusinessAggregatepayB2cOnlineOrderqryRequestV1Biz();
    //    String channelOrderNo = "330111990077000772606020141296";
    String orderId = "P2062187707145969666";
    bizContent.setOrder_id(null);
    bizContent.setDeal_flag("0");
    bizContent.setMer_id(icbcWrapper.getMchParams().getMerchantId());
    bizContent.setMer_prtcl_no(icbcWrapper.getMchParams().getMerchantId());
    bizContent.setIcbc_appid(icbcWrapper.getMchParams().getAppid());
    bizContent.setOut_trade_no(orderId);
    request.setBizContent(bizContent);
    CardbusinessAggregatepayB2cOnlineOrderqryResponseV1 resp = icbcWrapper.execute(request);
    String json = JSON.toJSONString(resp, JSONWriter.Feature.PrettyFormat);

    // 打印完整响应
    System.out.println("工行响应JSON：\n" + json);
    if (resp == null) {
      System.out.println("查询交易失败");
      return;
    }
    //    返回码，交易成功返回0，其他表示业务报错
    if (resp.getReturnCode() != 0) {
      System.out.println("查询交易失败");
      return;
    }
    //    deal_flag=0: pay_status说明
    //    支付查询returncode=0，paystatus=0===表示这笔单订单状态为2-支付成功/3-部分退成功/4-全额退成功，
    //    支付查询returncode=0，paystatus=1===表示这笔单订单状态为5-支付失败/8-已关闭，
    //    支付查询returncode=0，paystatus=2===表示这笔单订单状态为7-支付中，
    //    支付查询returncode<>0，===表示查不到记录或者程序报错或者订单状态为其他。
    if ("0".equals(resp.getPay_status())) {
      System.out.println("支付成功");
    } else if ("1".equals(resp.getPay_status())) {
      System.out.println("支付失败");
    } else if ("2".equals(resp.getPay_status())) {
      System.out.println("待支付");
    } else {
      System.out.println("结果未知");
    }
  }

  private static void verifySignTest() throws IcbcApiException {
    String apiGatewayPublicKey =
        "oXMis8A5rPsJUn6wSrCUc9Ttdn615XHj90pLxarwmLC51cUfz4ZG3XTDXakcNBHOwqLaHYCv6F13zj8I7X6z7REziLTWUxBwPekQKR6nsHc0CjuMaTpMtsZLSy5FwnQX";
    String requestURI = "/api/pay/notify/icbc";
    Map<String, String> params = new TreeMap<>(); // TreeMap已经保证了按键升序排序
    params.put("api", "/api/cardbusiness/aggregatepay/b2c/online/consumepurchase/V1");
    params.put("app_id", "11000000000000066903");
    String bizContent =
        "{\"access_type\":\"4\",\"attach\":\"\",\"bank_disc_amt\":\"0\",\"card_flag\":\"2\",\"card_kind\":\"11\",\"card_no\":\"6222****2329\",\"coupon_amt\":\"0\",\"cust_id\":\"\",\"decr_flag\":\"1\",\"ecoupon_amt\":\"0\",\"mer_disc_amt\":\"0\",\"mer_id\":\"330111990077\",\"msg_id\":\"050217175337856016100247258\",\"open_id\":\"\",\"order_id\":\"330111990077000732512030103117\",\"out_trade_no\":\"P1996056265521106945\",\"pay_time\":\"20251203111850\",\"pay_type\":\"2\",\"payment_amt\":\"1\",\"point_amt\":\"0\",\"return_code\":\"0\",\"return_msg\":\"success\",\"third_party_coupon_amt\":\"0\",\"third_party_discount_amt\":\"0\",\"third_trade_no\":\"\",\"total_amt\":\"1\",\"total_disc_amt\":\"0\"}";
    params.put("biz_content", bizContent);
    params.put("charset", "UTF-8");
    params.put("format", "json");
    params.put("from", "icbc-api");
    params.put("sign_type", "RSA");
    params.put("timestamp", "2025-12-03 11:18:50");
    String signContent = WebUtils.buildOrderedSignStr(requestURI, params);
    System.out.println("签名前原文：" + signContent);
    String sign =
        "eQA8BGy56YJETm3Z3Xsm3RoVYcU5mWt96E+Mz92lFtrbt4JVzGc2UCDSwhwGPFpsho/dfcuS6ZwMDo91sLRPIbIFXu5a1c0ZgrMPi/S+du9owmxm2c4duoZQ4KUP0MuTMqe06/H2xYqUwHxE8fsByKAfsGA/+16+pY7ICxhjjuw=";
    boolean passed =
        IcbcSignature.verify(
            signContent,
            IcbcConstants.SIGN_TYPE_RSA,
            apiGatewayPublicKey,
            IcbcConstants.CHARSET_UTF8,
            sign);
    System.out.printf("验签结果：%s", passed);
  }

  private static void notifyResultTest() throws IcbcApiException {
    String privateKey =
        "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCDkU0yA0MmwNpvPr1ihgcUKTfhbXXvgHOCw8BgFvqFs2ocq4KrdM+7aDZtkU0llJF6FiN58spJR3PgvELHJK5UlGEp3tbkuunCErZ06sYP0eWG/kztcTXz1Y3LpDJn8HUJLPt9oei9kN2D/SaW5xE4QsKJYUaRoqpIp9dQlKiVeD4We/UOb0mYdOkeNCzyiEC23dyNYnyR1qYl5wENEVFwGy/fgiAyRVkzPS7kTljz2iMpscTsFn8QTD5tk3g1YcVFq9roNygbKbEPsUP89ArrGiO+5Gz1Te63jV12VFOoCL9551reRwBvbDv2zyZ8XKpja5ofsPZvEL3yDW36j7xbAgMBAAECggEADlOlvNL3HoK0ppWF6PMgVTUx1RXm2Eg8C9rGGZCXAjUp0mpoxcPoFQfBmYfPh06Y0tsTTOW/2l/yAeBuQyI+643m0Ljn3Ktv5gbEoJGqNLHOFTee5+JClkVEdg7wwDnCfNfNoP4fCnl6bbOE3rzVtrMqXOxfU1+LvHKucyNlAjWYUHSQHTygrqut3xIXrXxYY8Fb0AH4WhSFDLooUA2eqTBjMAUPZJrLE1xnmeCZZt58Dz0Z2GLiwZVMJtYiNXZhvMfeRvypylvseJlBvnpg4cjOwef/Hb9BCR9JPMSVyzSXyDYHNBUnlv5BX9bR9jY3h6V8k42UyGdX7fRv3pdgGQKBgQC5cQxoiRgwxyqfg1S3u4hsG+icMQEV0K6hPDd61B6sWJIO6A67zmahsjA4gAu0znByha1Y8hGEGWEI8bp1yjqs4fFekBhopOcKbgk+0QQ1g8gRUenahxuqCjrazEhwmR2bafSxJQHGce19ytm7xb0XnHlG1wIHIhD/pq1rCDEGWQKBgQC1oKa3qf4emaPN7XOL1EwCTXKjqL3dxph0gGTkLnCVYwtZKc5ZOmCe2sRb6sFCUxT2jAyPy5MwgSY6TcKNT6vr/gKDtAIYHl3P4iVYK7wzdDWyHq3gg9BYgC8S8G77Hi+jhbRNfjZejmxYZlC30aV7ycMOWsZrOIwm7BFwu1Vp0wKBgQCPylHFyPAB+p1ZPJt3MCnTMjzGx9TAlqSiDzAxxMLX/oGan2O1SpEYNjPfy6HIG/7jPjYw7tqYJJmyNsXPSfT/Ut825OKYWa1wz+vJUodB+6Ipk+uR8AuKaQ5D0/0jmgZ0GM+MgFaFCpgNXzo/uE8sPL9pJ5TB9S9bHGIByUNCWQKBgDSCXJCuucQZAQ1w/73GQ69KusDr8PBkEQFleUWq02us9cPyXTDYusRSVQCJ7xOAt4fL13nfDg+w1t92Sa/W/8o7ZnizGwPkC5UlOUyzYDaz+y4ixnwpP5vGe1WLY1qAUJ+C8SySN5/ApzwrYFy0ZJZUL2s//StUbth12VjxH/F3AoGAUAI0F8CSoqqZpgPIHMc/+b/Wpo6DVPyaHJwfPP/s5WFjnDskrSXVWLEBPNij0GMgWWvsorUArWlUQv6Mig6cGXHF7IO2pvpq+yPS/Ty9Ro6bi3/2yfKadu7OgViJK+aSI5bFGh/zjQEBHpCOg2p40APK1Qgcyh8j539tvEFQahY=";
    Map<String, Object> params = new TreeMap<>();
    params.put("return_code", 0);
    params.put("return_msg", "success");
    params.put("msg_id", "050217175337856016100247258");
    Map<String, Object> resultMap = new LinkedHashMap<>();
    resultMap.put("response_biz_content", JSONUtil.toJsonStr(params));
    resultMap.put("sign_type", "RSA2");
    String signContent = JSONUtil.toJsonStr(resultMap);
    System.out.println("签名前原文：" + signContent);
    String sign =
        IcbcSignature.sign(
            signContent, IcbcConstants.SIGN_TYPE_RSA2, privateKey, IcbcConstants.CHARSET_UTF8);
    resultMap.put("sign", sign);
    System.out.println("响应原文:" + JSONUtil.toJsonStr(resultMap));
  }
}
