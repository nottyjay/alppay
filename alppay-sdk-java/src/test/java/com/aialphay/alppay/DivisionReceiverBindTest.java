package com.aialphay.alppay;

import com.aialphay.alppay.exception.AlppayException;
import com.aialphay.alppay.model.DivisionReceiverBindReqModel;
import com.aialphay.alppay.request.DivisionReceiverBindRequest;
import com.aialphay.alppay.response.DivisionReceiverBindResponse;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledIfSystemProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@EnabledIfSystemProperty(named = "alppay.integration-tests", matches = "true")
class DivisionReceiverBindTest {

  static final Logger _log = LoggerFactory.getLogger(DivisionReceiverBindTest.class);

  @BeforeAll
  public static void initApiKey() {
    Alppay.setApiBase(AlppayTestData.getApiBase());
    Alppay.apiKey = AlppayTestData.getApiKey();
    Alppay.mchNo = AlppayTestData.getMchNo();
    Alppay.appId = AlppayTestData.getAppId();
  }

  @Test
  public void testDivisionReceiverBind() {
    AlppayClient alppayClient =
        AlppayClient.getInstance(Alppay.appId, Alppay.apiKey, Alppay.getApiBase());
    DivisionReceiverBindRequest request = new DivisionReceiverBindRequest();
    DivisionReceiverBindReqModel model = new DivisionReceiverBindReqModel();
    model.setMchNo(Alppay.mchNo); // 商户号
    model.setAppId(alppayClient.getAppId()); // 应用ID
    model.setIfCode("shengpay");
    model.setReceiverAlias("安惠");
    model.setReceiverGroupId(100003L);
    model.setAccType((byte) 1);
    model.setAccNo("32617592");
    model.setAccName("宽空间");
    model.setRelationType("SERVICE_PROVIDER");
    model.setRelationTypeName("服务商");
    model.setDivisionProfit("0.10");
    request.setBizModel(model);

    try {
      DivisionReceiverBindResponse response = alppayClient.execute(request);
      _log.info("验签结果：{}", response.checkSign(Alppay.apiKey));
      // 判断转账发起是否成功（并不代表转账成功）
      if (response.isSuccess(Alppay.apiKey)) {
        _log.info("accNo：{}， 绑定成功", response.get().getAccNo());
      } else {
        _log.info("绑定失败：accNo：{}", model.getAccNo());
        _log.info("通道错误码：{}", response.get().getErrCode());
        _log.info("通道错误信息：{}", response.get().getErrMsg());
      }
    } catch (AlppayException e) {
      _log.error(e.getMessage());
    }
  }
}
