package com.aialphay.alppay.pay.ctrl.transfer;

import com.aialphay.alppay.core.entity.TransferOrder;
import com.aialphay.alppay.core.exception.BizException;
import com.aialphay.alppay.core.model.ApiRes;
import com.aialphay.alppay.pay.ctrl.ApiController;
import com.aialphay.alppay.pay.rqrs.transfer.QueryTransferOrderRQ;
import com.aialphay.alppay.pay.rqrs.transfer.QueryTransferOrderRS;
import com.aialphay.alppay.pay.service.ConfigContextQueryService;
import com.aialphay.alppay.service.impl.TransferOrderService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 商户转账单查询controller
 *
 * @Author nottyjay
 * @site https://www.aialphay.com
 * @date 2021/8/13 15:20
 */
@Slf4j
@RestController
public class QueryTransferOrderController extends ApiController {

  @Autowired private TransferOrderService transferOrderService;
  @Autowired private ConfigContextQueryService configContextQueryService;

  /** 查单接口 * */
  @RequestMapping("/api/transfer/query")
  public ApiRes queryTransferOrder() {

    // 获取参数 & 验签
    QueryTransferOrderRQ rq = getRQByWithMchSign(QueryTransferOrderRQ.class);

    if (StringUtils.isAllEmpty(rq.getMchOrderNo(), rq.getTransferId())) {
      throw new BizException("mchOrderNo 和 transferId不能同时为空");
    }

    TransferOrder refundOrder =
        transferOrderService.queryMchOrder(rq.getMchNo(), rq.getMchOrderNo(), rq.getTransferId());
    if (refundOrder == null) {
      throw new BizException("订单不存在");
    }

    QueryTransferOrderRS bizRes = QueryTransferOrderRS.buildByRecord(refundOrder);
    return ApiRes.okWithSign(
        bizRes, configContextQueryService.queryMchApp(rq.getMchNo(), rq.getAppId()).getAppSecret());
  }
}
