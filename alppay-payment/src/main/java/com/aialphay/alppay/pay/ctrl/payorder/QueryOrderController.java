package com.aialphay.alppay.pay.ctrl.payorder;

import com.aialphay.alppay.core.entity.PayOrder;
import com.aialphay.alppay.core.exception.BizException;
import com.aialphay.alppay.core.model.ApiRes;
import com.aialphay.alppay.pay.ctrl.ApiController;
import com.aialphay.alppay.pay.rqrs.payorder.QueryPayOrderRQ;
import com.aialphay.alppay.pay.rqrs.payorder.QueryPayOrderRS;
import com.aialphay.alppay.pay.service.ConfigContextQueryService;
import com.aialphay.alppay.service.impl.PayOrderService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/*
 * 商户查单controller
 *
 * @Author nottyjay
 * @site https://www.aialphay.com
 * @date 2021/6/8 17:26
 */
@Slf4j
@RestController
public class QueryOrderController extends ApiController {

  @Autowired private PayOrderService payOrderService;
  @Autowired private ConfigContextQueryService configContextQueryService;

  /** 查单接口 * */
  @RequestMapping("/api/pay/query")
  public ApiRes queryOrder() {

    // 获取参数 & 验签
    QueryPayOrderRQ rq = getRQByWithMchSign(QueryPayOrderRQ.class);

    if (StringUtils.isAllEmpty(rq.getMchOrderNo(), rq.getPayOrderId())) {
      throw new BizException("mchOrderNo 和 payOrderId不能同时为空");
    }

    PayOrder payOrder =
        payOrderService.queryMchOrder(rq.getMchNo(), rq.getPayOrderId(), rq.getMchOrderNo());
    if (payOrder == null) {
      throw new BizException("订单不存在");
    }

    QueryPayOrderRS bizRes = QueryPayOrderRS.buildByPayOrder(payOrder);
    return ApiRes.okWithSign(
        bizRes, configContextQueryService.queryMchApp(rq.getMchNo(), rq.getAppId()).getAppSecret());
  }
}
