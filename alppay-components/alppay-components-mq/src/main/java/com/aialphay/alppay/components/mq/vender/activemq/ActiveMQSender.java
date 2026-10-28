package com.aialphay.alppay.components.mq.vender.activemq;

import com.aialphay.alppay.components.mq.constant.MQVenderCS;
import com.aialphay.alppay.components.mq.model.AbstractMQ;
import com.aialphay.alppay.components.mq.vender.IMQSender;
import jakarta.jms.TextMessage;
import org.apache.activemq.ScheduledMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

/**
 * activeMQ 消息发送器的实现
 *
 * @Author nottyjay
 * @site https://www.aialphay.com
 * @date 2021/7/23 16:52
 */
@Component
@ConditionalOnProperty(name = MQVenderCS.YML_VENDER_KEY, havingValue = MQVenderCS.ACTIVE_MQ)
public class ActiveMQSender implements IMQSender {

  @Autowired private ActiveMQConfig activeMQConfig;

  @Autowired private JmsTemplate jmsTemplate;

  @Override
  public void send(AbstractMQ mqModel) {
    jmsTemplate.convertAndSend(activeMQConfig.getDestination(mqModel), mqModel.toMessage());
  }

  @Override
  public void send(AbstractMQ mqModel, int delay) {
    jmsTemplate.send(
        activeMQConfig.getDestination(mqModel),
        session -> {
          TextMessage tm = session.createTextMessage(mqModel.toMessage());
          tm.setLongProperty(ScheduledMessage.AMQ_SCHEDULED_DELAY, delay * 1000);
          tm.setLongProperty(ScheduledMessage.AMQ_SCHEDULED_PERIOD, 1 * 1000);
          tm.setLongProperty(ScheduledMessage.AMQ_SCHEDULED_REPEAT, 1);
          return tm;
        });
  }
}
