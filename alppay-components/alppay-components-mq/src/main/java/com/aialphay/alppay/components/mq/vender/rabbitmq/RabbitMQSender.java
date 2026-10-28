package com.aialphay.alppay.components.mq.vender.rabbitmq;

import com.aialphay.alppay.components.mq.constant.MQSendTypeEnum;
import com.aialphay.alppay.components.mq.constant.MQVenderCS;
import com.aialphay.alppay.components.mq.model.AbstractMQ;
import com.aialphay.alppay.components.mq.vender.IMQSender;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

/**
 * rabbitMQ 消息发送器的实现
 *
 * @Author nottyjay
 * @site https://www.aialphay.com
 * @date 2021/7/23 16:52
 */
@Component
@ConditionalOnProperty(name = MQVenderCS.YML_VENDER_KEY, havingValue = MQVenderCS.RABBIT_MQ)
public class RabbitMQSender implements IMQSender {

  @Autowired private RabbitTemplate rabbitTemplate;

  @Override
  public void send(AbstractMQ mqModel) {

    if (mqModel.getMQType() == MQSendTypeEnum.QUEUE) {

      rabbitTemplate.convertAndSend(mqModel.getMQName(), mqModel.toMessage());
    } else {

      // fanout模式 的 routeKEY 没意义。
      this.rabbitTemplate.convertAndSend(
          RabbitMQConfig.FANOUT_EXCHANGE_NAME_PREFIX + mqModel.getMQName(),
          null,
          mqModel.toMessage());
    }
  }

  @Override
  public void send(AbstractMQ mqModel, int delay) {

    if (mqModel.getMQType() == MQSendTypeEnum.QUEUE) {

      rabbitTemplate.convertAndSend(
          RabbitMQConfig.DELAYED_EXCHANGE_NAME,
          mqModel.getMQName(),
          mqModel.toMessage(),
          messagePostProcessor -> {
            messagePostProcessor.getMessageProperties().setDelay(Math.toIntExact(delay * 1000));
            return messagePostProcessor;
          });
    } else {

      // fanout模式 的 routeKEY 没意义。  没有延迟属性
      this.rabbitTemplate.convertAndSend(
          RabbitMQConfig.FANOUT_EXCHANGE_NAME_PREFIX + mqModel.getMQName(),
          null,
          mqModel.toMessage());
    }
  }
}
