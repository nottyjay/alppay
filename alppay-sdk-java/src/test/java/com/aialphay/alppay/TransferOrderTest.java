package com.aialphay.alppay;

import com.aialphay.alppay.exception.AlppayException;
import com.aialphay.alppay.model.ChannelUserReqModel;
import com.aialphay.alppay.model.TransferOrderCreateReqModel;
import com.aialphay.alppay.model.TransferOrderQueryReqModel;
import com.aialphay.alppay.request.ChannelUserRequest;
import com.aialphay.alppay.request.TransferOrderCreateRequest;
import com.aialphay.alppay.request.TransferOrderQueryRequest;
import com.aialphay.alppay.response.TransferOrderCreateResponse;
import com.aialphay.alppay.response.TransferOrderQueryResponse;
import java.util.Date;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledIfSystemProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@EnabledIfSystemProperty(named = "alppay.integration-tests", matches = "true")
class TransferOrderTest {

    final static Logger _log = LoggerFactory.getLogger(TransferOrderTest.class);

    @BeforeAll
    public static void initApiKey() {
        Alppay.setApiBase(AlppayTestData.getApiBase());
        Alppay.apiKey = AlppayTestData.getApiKey();
        Alppay.mchNo = AlppayTestData.getMchNo();
        Alppay.appId = AlppayTestData.getAppId();
    }

    @Test
    public void testTransferOrderCreate() {
        AlppayClient alppayClient = AlppayClient.getInstance(Alppay.appId, Alppay.apiKey,
            Alppay.getApiBase());
        TransferOrderCreateRequest request = new TransferOrderCreateRequest();
        TransferOrderCreateReqModel model = new TransferOrderCreateReqModel();
        model.setMchNo(Alppay.mchNo);                       // 商户号
        model.setAppId(alppayClient.getAppId());            // 应用ID
        model.setMchOrderNo("mho" + new Date().getTime());                // 商户转账单号
        model.setAmount(1L);
        model.setCurrency("CNY");
        model.setIfCode("wxpay");
        model.setEntryType("WX_CASH");
        model.setAccountNo("a6BcIwtTvIqv1zXZohc61biryWok");
        model.setAccountName("");
        model.setTransferDesc("测试转账");
        model.setClientIp("192.166.1.132");                 // 发起转账请求客户端的IP地址
        request.setBizModel(model);

        try {
            TransferOrderCreateResponse response = alppayClient.execute(request);
            _log.info("验签结果：{}", response.checkSign(Alppay.apiKey));
            // 判断转账发起是否成功（并不代表转账成功）
            if (response.isSuccess(Alppay.apiKey)) {
                String transferId = response.get().getTransferId();
                _log.info("transferId：{}", transferId);
                _log.info("mchOrderNo：{}", response.get().getMchOrderNo());
            } else {
                _log.info("下单失败：mchOrderNo={}, msg={}", model.getMchOrderNo(), response.getMsg());
                _log.info("通道错误码：{}", response.get().getErrCode());
                _log.info("通道错误信息：{}", response.get().getErrMsg());
            }
        } catch (AlppayException e) {
            _log.error(e.getMessage());
        }

    }

    @Test
    public void testTransferOrderQuery() {
        AlppayClient alppayClient = AlppayClient.getInstance(Alppay.appId, Alppay.apiKey,
            Alppay.getApiBase());
        TransferOrderQueryRequest request = new TransferOrderQueryRequest();
        TransferOrderQueryReqModel model = new TransferOrderQueryReqModel();
        model.setMchNo(Alppay.mchNo);                                          // 商户号
        model.setAppId(alppayClient.getAppId());                               // 应用ID
        model.setTransferId("T202108121543441860003");                         // 转账单号
        request.setBizModel(model);
        try {
            TransferOrderQueryResponse response = alppayClient.execute(request);
            _log.info("验签结果：{}", response.checkSign(Alppay.apiKey));
            if (response.isSuccess(Alppay.apiKey)) {
                _log.info("订单信息：{}", response);
                _log.info("转账状态：{}", response.get().getState());
                _log.info("转账金额：{}", response.get().getAmount());
            }
        } catch (AlppayException e) {
            e.printStackTrace();
        }

    }


    @Test
    public void getChannelUserIdUrl() {
        AlppayClient alppayClient = AlppayClient.getInstance(Alppay.appId, Alppay.apiKey,
            Alppay.getApiBase());
        ChannelUserRequest request = new ChannelUserRequest();
        ChannelUserReqModel model = new ChannelUserReqModel();
        model.setAppId(alppayClient.getAppId());
        model.setMchNo(Alppay.mchNo);
        model.setRedirectUrl("https://httpdump.io/30cbe");
        model.setIfCode("AUTO");
        request.setBizModel(model);

        try {
            String url = alppayClient.getRequestUrl(request);
            _log.info("跳转 URL: {}", url);
        } catch (AlppayException e) {
            e.printStackTrace();
        }

    }
}
