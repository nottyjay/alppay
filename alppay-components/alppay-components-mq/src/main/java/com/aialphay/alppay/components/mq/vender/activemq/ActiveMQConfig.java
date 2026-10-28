package com.aialphay.alppay.components.mq.vender.activemq;

import com.aialphay.alppay.components.mq.constant.MQSendTypeEnum;
import com.aialphay.alppay.components.mq.constant.MQVenderCS;
import com.aialphay.alppay.components.mq.model.AbstractMQ;
import jakarta.jms.ConnectionFactory;
import jakarta.jms.Destination;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.apache.activemq.command.ActiveMQQueue;
import org.apache.activemq.command.ActiveMQTopic;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.config.JmsListenerContainerFactory;
import org.springframework.stereotype.Component;

/*
 * activeMQ的配置项
 *
 * @Author nottyjay
 * @site https://www.aialphay.com
 * @date 2021/7/23 16:51
 */
@Component
@ConditionalOnProperty(name = MQVenderCS.YML_VENDER_KEY, havingValue = MQVenderCS.ACTIVE_MQ)
public class ActiveMQConfig {

  public static final String TOPIC_LISTENER_CONTAINER = "jmsTopicListenerContainer";
  Map<String, Destination> map = new ConcurrentHashMap<>();

  public Destination getDestination(AbstractMQ mqModel) {

    if (map.get(mqModel.getMQName()) == null) {
      this.init(mqModel.getMQName(), mqModel.getMQType());
    }
    return map.get(mqModel.getMQName());
  }

  private synchronized void init(String mqName, MQSendTypeEnum mqSendTypeEnum) {

    if (mqSendTypeEnum == MQSendTypeEnum.QUEUE) {
      map.put(mqName, new ActiveMQQueue(mqName));
    } else {
      map.put(mqName, new ActiveMQTopic(mqName));
    }
  }

  /** 新增jmsListenerContainer, 用于接收topic类型的消息 * */
  @Bean
  public JmsListenerContainerFactory<?> jmsTopicListenerContainer(ConnectionFactory factory) {
    DefaultJmsListenerContainerFactory bean = new DefaultJmsListenerContainerFactory();
    bean.setPubSubDomain(true);
    bean.setConnectionFactory(factory);
    return bean;
  }
}
