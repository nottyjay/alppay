package com.aialphay.alppay;

import com.aialphay.alppay.exception.AlppayException;
import com.aialphay.alppay.model.RefundOrderCreateReqModel;
import com.aialphay.alppay.model.RefundOrderQueryReqModel;
import com.aialphay.alppay.request.RefundOrderCreateRequest;
import com.aialphay.alppay.request.RefundOrderQueryRequest;
import com.aialphay.alppay.response.RefundOrderCreateResponse;
import com.aialphay.alppay.response.RefundOrderQueryResponse;
import java.util.Date;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledIfSystemProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@EnabledIfSystemProperty(named = "alppay.integration-tests", matches = "true")
class RefundOrderTest {

    final static Logger _log = LoggerFactory.getLogger(RefundOrderTest.class);

    @BeforeAll
    public static void initApiKey() {
        Alppay.setApiBase(AlppayTestData.getApiBase());
        Alppay.apiKey = AlppayTestData.getApiKey();
        Alppay.mchNo = AlppayTestData.getMchNo();
        Alppay.appId = AlppayTestData.getAppId();
    }

    @Test
    public void testRefundOrderCreate() {
        AlppayClient alppayClient = AlppayClient.getInstance(Alppay.appId, Alppay.apiKey, Alppay.getApiBase());
        RefundOrderCreateRequest request = new RefundOrderCreateRequest();
        RefundOrderCreateReqModel model = new RefundOrderCreateReqModel();
        model.setMchNo(Alppay.mchNo);                       // 商户号
        model.setAppId(alppayClient.getAppId());            // 应用ID
        model.setMchOrderNo("");                            // 商户支付单号(与支付订单号二者传一)
        model.setPayOrderId("P202106181104177050002");      // 支付订单号(与商户支付单号二者传一)
        String refundOrderNo = "mho" + new Date().getTime();
        model.setMchRefundNo(refundOrderNo);                // 商户退款单号
        model.setRefundAmount(4l);                          // 退款金额，单位分
        model.setCurrency("cny");                           // 币种，目前只支持cny
        model.setClientIp("192.166.1.132");                 // 发起支付请求客户端的IP地址
        model.setRefundReason("退款测试");                    // 退款原因
        model.setNotifyUrl("https://www.aialphay.com");      // 异步通知地址
        model.setChannelExtra("");                          // 渠道扩展参数
        model.setExtParam("");                              // 商户扩展参数,回调时原样返回
        request.setBizModel(model);
        try {
            RefundOrderCreateResponse response = alppayClient.execute(request);
            _log.info("验签结果：{}", response.checkSign(Alppay.apiKey));
            // 判断退款发起是否成功（并不代表退款成功）
            if(response.isSuccess(Alppay.apiKey)) {
                String refundOrderId = response.get().getRefundOrderId();
                _log.info("refundOrderId：{}", refundOrderId);
                _log.info("mchRefundNo：{}", response.get().getMchRefundNo());
            }else {
                _log.info("下单失败：refundOrderNo={}, msg={}", refundOrderNo, response.getMsg());
                _log.info("通道错误码：{}", response.get().getErrCode());
                _log.info("通道错误信息：{}", response.get().getErrMsg());
            }
        } catch (AlppayException e) {
            _log.error(e.getMessage());
        }

    }

    @Test
    public void testRefundOrderQuery() {
        AlppayClient alppayClient = AlppayClient.getInstance(Alppay.appId, Alppay.apiKey, Alppay.getApiBase());
        RefundOrderQueryRequest request = new RefundOrderQueryRequest();
        RefundOrderQueryReqModel model = new RefundOrderQueryReqModel();
        model.setMchNo(Alppay.mchNo);                                             // 商户号
        model.setAppId(alppayClient.getAppId());                                  // 应用ID
        model.setRefundOrderId("P202106181105527690009");                         // 退款单号
        request.setBizModel(model);
        try {
            RefundOrderQueryResponse response = alppayClient.execute(request);
            _log.info("验签结果：{}", response.checkSign(Alppay.apiKey));
            if(response.isSuccess(Alppay.apiKey)) {
                _log.info("订单信息：{}", response);
                _log.info("退款状态：{}", response.get().getState());
                _log.info("退款金额：{}", response.get().getRefundAmount());
            }
        } catch (AlppayException e) {
            e.printStackTrace();
        }

    }



}
