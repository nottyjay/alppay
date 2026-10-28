package com.aialphay.alppay.pay.mq;

import com.aialphay.alppay.components.mq.model.ResetAppConfigMQ;
import com.aialphay.alppay.service.impl.SysConfigService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 接收MQ消息 业务： 更新系统配置参数
 *
 * @Author nottyjay
 * @site https://www.aialphay.com
 * @date 2021/7/27 9:23
 */
@Slf4j
@Component
public class ResetAppConfigMQReceiver implements ResetAppConfigMQ.IMQReceiver {

  @Autowired private SysConfigService sysConfigService;

  @Override
  public void receive(ResetAppConfigMQ.MsgPayload payload) {

    log.info("成功接收更新系统配置的订阅通知, msg={}", payload);
    sysConfigService.initDBConfig(payload.getGroupKey());
    log.info("系统配置静态属性已重置");
  }
}
