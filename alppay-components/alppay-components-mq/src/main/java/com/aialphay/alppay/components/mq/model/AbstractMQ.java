package com.aialphay.alppay.components.mq.model;

import com.aialphay.alppay.components.mq.constant.MQSendTypeEnum;

/**
 * 定义MQ消息格式
 *
 * @Author nottyjay
 * @site https://www.aialphay.com
 * @date 2021/7/22 15:33
 */
public abstract class AbstractMQ {

  /** MQ名称 * */
  public abstract String getMQName();

  /** MQ 类型 * */
  public abstract MQSendTypeEnum getMQType();

  /** 构造MQ消息体 String类型 * */
  public abstract String toMessage();
}
