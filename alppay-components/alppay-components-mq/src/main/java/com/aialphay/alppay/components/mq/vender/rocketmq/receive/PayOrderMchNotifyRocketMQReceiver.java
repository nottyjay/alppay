package com.aialphay.alppay.components.mq.vender.rocketmq.receive;

import com.aialphay.alppay.components.mq.constant.MQVenderCS;
import com.aialphay.alppay.components.mq.executor.MqThreadExecutor;
import com.aialphay.alppay.components.mq.model.PayOrderMchNotifyMQ;
import com.aialphay.alppay.components.mq.vender.IMQMsgReceiver;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * rocketMQ消息接收器：仅在vender=rocketMQ时 && 项目实现IMQReceiver接口时 进行实例化 业务： 支付订单商户通知
 *
 * @Author nottyjay
 * @site https://www.aialphay.com
 * @date 2021/7/22 17:06
 */
@Component
@ConditionalOnProperty(name = MQVenderCS.YML_VENDER_KEY, havingValue = MQVenderCS.ROCKET_MQ)
@ConditionalOnBean(PayOrderMchNotifyMQ.IMQReceiver.class)
@RocketMQMessageListener(
    topic = PayOrderMchNotifyMQ.MQ_NAME,
    consumerGroup = PayOrderMchNotifyMQ.MQ_NAME)
public class PayOrderMchNotifyRocketMQReceiver implements IMQMsgReceiver, RocketMQListener<String> {

  @Autowired private PayOrderMchNotifyMQ.IMQReceiver mqReceiver;

  /** 接收 【 queue 】 类型的消息 * */
  @Override
  public void receiveMsg(String msg) {
    mqReceiver.receive(PayOrderMchNotifyMQ.parse(msg));
  }

  @Override
  @Async(MqThreadExecutor.EXECUTOR_PAYORDER_MCH_NOTIFY)
  public void onMessage(String message) {
    this.receiveMsg(message);
  }
}
