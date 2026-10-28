package com.aialphay.alppay.pay.rqrs.msg;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/*
 * 上游渠道侧响应信息包装类
 *
 * @Author xiaocui
 * @date 2026/6/3 17:31
 */
@Slf4j
@Data
public class ChannelReconMsg {
  /** 渠道订单号 * */
  private String channelOrderId;

  /** 渠道用户ID * */
  private String channelUserId;

  /** 渠道订单状态 * */
  private ChannelOrderStatus channelOrderStatus;

  /** 对账返回信息 * */
  private String msg;

  public ChannelReconMsg() {}

  public ChannelReconMsg(
      ChannelOrderStatus channelOrderStatus,
      String channelOrderId,
      String msg,
      String channelUserId) {
    this.channelOrderId = channelOrderId;
    this.channelOrderStatus = channelOrderStatus;
    this.msg = msg;
  }

  public static ChannelReconMsg unknown(String msg) {
    return new ChannelReconMsg(ChannelOrderStatus.UNKNOWN, null, msg, null);
  }

  public static ChannelReconMsg confirmSuccess(
      String channelOrderId, String msg, String channelUserId) {
    return new ChannelReconMsg(ChannelOrderStatus.SUCCESS, channelOrderId, msg, channelUserId);
  }

  public static ChannelReconMsg confirmError(String channelOrderId, String msg) {
    return new ChannelReconMsg(ChannelOrderStatus.ERROR, channelOrderId, msg, null);
  }

  public static ChannelReconMsg waiting(String channelOrderId, String msg) {
    return new ChannelReconMsg(ChannelOrderStatus.WAITING, channelOrderId, msg, null);
  }

  // 渠道订单支付状态
  public enum ChannelOrderStatus {
    SUCCESS, // 支付成功
    ERROR, // 支付失败
    WAITING, // 待支付
    UNKNOWN, // 未知
  }
}
