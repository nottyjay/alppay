package com.aialphay.alppay.components.mq.vender;

import com.aialphay.alppay.components.mq.model.AbstractMQ;

/**
 * MQ 消息发送器 接口定义
 *
 * @Author nottyjay
 * @site https://www.aialphay.com
 * @date 2021/7/21 16:09
 */
public interface IMQSender {

  /** 推送MQ消息， 实时 * */
  void send(AbstractMQ mqModel);

  /** 推送MQ消息， 延迟接收，单位：s * */
  void send(AbstractMQ mqModel, int delay);
}
