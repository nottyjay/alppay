package com.aialphay.alppay.pay.ctrl.refund;

import com.aialphay.alppay.core.entity.RefundOrder;
import com.aialphay.alppay.core.exception.BizException;
import com.aialphay.alppay.core.model.ApiRes;
import com.aialphay.alppay.pay.ctrl.ApiController;
import com.aialphay.alppay.pay.rqrs.refund.QueryRefundOrderRQ;
import com.aialphay.alppay.pay.rqrs.refund.QueryRefundOrderRS;
import com.aialphay.alppay.pay.service.ConfigContextQueryService;
import com.aialphay.alppay.service.impl.RefundOrderService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 商户退款单查询controller
 *
 * @Author nottyjay
 * @site https://www.aialphay.com
 * @date 2021/6/17 15:20
 */
@Slf4j
@RestController
public class QueryRefundOrderController extends ApiController {

  @Autowired private RefundOrderService refundOrderService;
  @Autowired private ConfigContextQueryService configContextQueryService;

  /** 查单接口 * */
  @RequestMapping("/api/refund/query")
  public ApiRes queryRefundOrder() {

    // 获取参数 & 验签
    QueryRefundOrderRQ rq = getRQByWithMchSign(QueryRefundOrderRQ.class);

    if (StringUtils.isAllEmpty(rq.getMchRefundNo(), rq.getRefundOrderId())) {
      throw new BizException("mchRefundNo 和 refundOrderId不能同时为空");
    }

    RefundOrder refundOrder =
        refundOrderService.queryMchOrder(rq.getMchNo(), rq.getMchRefundNo(), rq.getRefundOrderId());
    if (refundOrder == null) {
      throw new BizException("订单不存在");
    }

    QueryRefundOrderRS bizRes = QueryRefundOrderRS.buildByRefundOrder(refundOrder);
    return ApiRes.okWithSign(
        bizRes, configContextQueryService.queryMchApp(rq.getMchNo(), rq.getAppId()).getAppSecret());
  }
}
