package com.aialphay.alppay.pay.service;

import com.aialphay.alppay.core.entity.PayOrder;
import com.aialphay.alppay.core.entity.PayOrderReconRecord;
import com.aialphay.alppay.core.utils.SpringBeansUtil;
import com.aialphay.alppay.pay.channel.IPayOrderReconService;
import com.aialphay.alppay.pay.model.MchAppConfigContext;
import com.aialphay.alppay.pay.rqrs.msg.ChannelReconMsg;
import com.aialphay.alppay.service.impl.PayOrderReconRecordService;
import com.aialphay.alppay.service.impl.PayOrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/*
 * 查询上游订单， &  对账服务实现类
 *
 * @Author xiaocui
 * @date 2026/6/3 17:40
 */

@Service
@Slf4j
public class PayOrderReconService {
  @Autowired private ConfigContextQueryService configContextQueryService;
  @Autowired private PayOrderService payOrderService;
  @Autowired private PayOrderProcessService payOrderProcessService;

  /** 订单对账 * */
  public void reconciliationOrder(PayOrder payOrder) {

    try {

      String payOrderId = payOrder.getPayOrderId();

      // 查询支付接口是否存在
      IPayOrderReconService reconService =
          SpringBeansUtil.getBean(
              payOrder.getIfCode() + "PayOrderReconService", IPayOrderReconService.class);

      // 支付通道接口实现不存在
      if (reconService == null) {
        log.error("{} interface not exists!", payOrder.getIfCode());
        return;
      }

      // 查询出商户应用的配置信息
      MchAppConfigContext mchAppConfigContext =
          configContextQueryService.queryMchInfoAndAppInfo(
              payOrder.getMchNo(), payOrder.getAppId());

      ChannelReconMsg channelReconMsg = reconService.reconciliation(payOrder, mchAppConfigContext);
      if (channelReconMsg == null) {
        log.error("channelRetMsg is null");
        return;
      }

      log.info("对账[{}]结果为：{}", payOrderId, channelReconMsg);

      // 渠道确认支付成功
      if (channelReconMsg.getChannelOrderStatus() == ChannelReconMsg.ChannelOrderStatus.SUCCESS) {
        // 当前订单为失败或关单，暂时不处理系统退款和撤销终态与渠道状态不一致情况
        if (payOrder.getState() == PayOrder.STATE_FAIL
            || payOrder.getState() == PayOrder.STATE_CLOSED) {
          // 更新订单状态为支付成功，并且更新已对账标识
          payOrderService.reconUpdateFailOrClose2Success(
              payOrderId,
              channelReconMsg.getChannelOrderId(),
              channelReconMsg.getChannelUserId(),
              genSysAuto(
                  channelReconMsg,
                  payOrderId,
                  "渠道确认支付成功，但系统订单状态为非【成功】状态，系统自动处理",
                  payOrder.getMchNo()));
          // 确认支付成功，通知下游商户
          payOrderProcessService.confirmSuccess(payOrder);
        } else { // 无异常
          payOrderService.updateOrderIsRecon(
              payOrderId, genNormal(channelReconMsg, payOrderId, payOrder.getMchNo()));
        }
      } else if (channelReconMsg.getChannelOrderStatus()
          == ChannelReconMsg.ChannelOrderStatus.ERROR) { // 渠道确认支付失败
        if (payOrder.getState() != PayOrder.STATE_FAIL
            && payOrder.getState() != PayOrder.STATE_CLOSED) {
          payOrderService.updateOrderIsRecon(
              payOrderId,
              genNeedManual(
                  channelReconMsg, payOrderId, "渠道确认支付失败，但系统订单状态为非【失败/关单】状态", payOrder.getMchNo()));
        } else { // 无异常
          payOrderService.updateOrderIsRecon(
              payOrderId, genNormal(channelReconMsg, payOrderId, payOrder.getMchNo()));
        }
      } else {
        payOrderService.updateOrderIsRecon(
            payOrderId, genNeedManual(channelReconMsg, payOrderId, "订单状态异常", payOrder.getMchNo()));
      }

    } catch (Exception e) { // 继续下一次迭代查询
      log.error("error payOrderId = {}", payOrder.getPayOrderId(), e);
    }
  }

  // 对账无异常
  public PayOrderReconRecord genNormal(ChannelReconMsg reconMsg, String orderId, String mchNo) {
    return new PayOrderReconRecord()
        .setChannelOrderNo(reconMsg.getChannelOrderId())
        .setOrderId(orderId)
        .setState(PayOrderReconRecord.RECONCILE_NORMAL)
        .setHandleStrategy(PayOrderReconRecord.HANDLE_NO_ACTION)
        .setCreatedAt(new Date())
        .setUpdatedAt(new Date())
        .setMchNo(mchNo)
        .setRemark("【" + reconMsg.getMsg() + "】对账结果无异常");
  }

  // 需要人工处理
  public PayOrderReconRecord genNeedManual(
      ChannelReconMsg reconMsg, String orderId, String msg, String mchNo) {
    return new PayOrderReconRecord()
        .setChannelOrderNo(reconMsg.getChannelOrderId())
        .setOrderId(orderId)
        .setCreatedAt(new Date())
        .setUpdatedAt(new Date())
        .setState(PayOrderReconRecord.RECONCILE_STATUS_ERROR)
        .setHandleStrategy(PayOrderReconRecord.HANDLE_MANUAL)
        .setMchNo(mchNo)
        .setRemark("【" + reconMsg.getMsg() + "】" + msg);
  }

  // 系统自动修正
  public PayOrderReconRecord genSysAuto(
      ChannelReconMsg reconMsg, String orderId, String msg, String mchNo) {
    return new PayOrderReconRecord()
        .setChannelOrderNo(reconMsg.getChannelOrderId())
        .setOrderId(orderId)
        .setCreatedAt(new Date())
        .setUpdatedAt(new Date())
        .setState(PayOrderReconRecord.RECONCILE_STATUS_ERROR)
        .setHandleStrategy(PayOrderReconRecord.HANDLE_SYS)
        .setMchNo(mchNo)
        .setRemark("【" + reconMsg.getMsg() + "】" + msg);
  }
}
