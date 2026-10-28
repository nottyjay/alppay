package com.aialphay.alppay;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.aialphay.alppay.exception.AlppayException;
import com.aialphay.alppay.model.PayOrderDivisionExecReqModel;
import com.aialphay.alppay.request.PayOrderDivisionExecRequest;
import com.aialphay.alppay.response.PayOrderDivisionExecResponse;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledIfSystemProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@EnabledIfSystemProperty(named = "alppay.integration-tests", matches = "true")
class PayOrderDivisionReceiverExecTest {

  static final Logger _log = LoggerFactory.getLogger(PayOrderDivisionReceiverExecTest.class);

  @BeforeAll
  public static void initApiKey() {
    Alppay.setApiBase(AlppayTestData.getApiBase());
    Alppay.apiKey = AlppayTestData.getApiKey();
    Alppay.mchNo = AlppayTestData.getMchNo();
    Alppay.appId = AlppayTestData.getAppId();
  }

  @Test
  public void testPayOrderDivisionExec() {
    AlppayClient alppayClient =
        AlppayClient.getInstance(Alppay.appId, Alppay.apiKey, Alppay.getApiBase());
    PayOrderDivisionExecRequest request = new PayOrderDivisionExecRequest();
    PayOrderDivisionExecReqModel model = new PayOrderDivisionExecReqModel();
    request.setBizModel(model);
    model.setMchNo(Alppay.mchNo); // 商户号
    model.setAppId(alppayClient.getAppId()); // 应用ID
    model.setPayOrderId("P147066alppayClient5");
    model.setUseSysAutoDivisionReceivers((byte) 0);

    JSONArray receviers = new JSONArray();
    receviers.add(
        JSONObject.parseObject(
            "{receiverId: '800004', receiverGroupId: '', divisionProfit: '0.1'}"));
    receviers.add(
        JSONObject.parseObject(
            "{receiverId: '800005', receiverGroupId: '', divisionProfit: '0.2'}"));

    model.setReceivers(receviers.toJSONString());

    try {
      PayOrderDivisionExecResponse response = alppayClient.execute(request);
      _log.info("验签结果：{}", response.checkSign(Alppay.apiKey));
      // 判断转账发起是否成功（并不代表转账成功）
      if (response.isSuccess(Alppay.apiKey)) {
        _log.info("渠道分账订单号：{}， 分账成功", response.get().getChannelBatchOrderId());
      } else {
        _log.info("分账失败：payOrderId：{}", model.getPayOrderId());
        _log.info("通道错误码：{}", response.get().getErrCode());
        _log.info("通道错误信息：{}", response.get().getErrMsg());
      }
    } catch (AlppayException e) {
      _log.error(e.getMessage());
    }
  }
}
