package com.aialphay.alppay.pay.task;

import com.aialphay.alppay.service.impl.PayOrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/*
 * 订单过期定时任务
 *
 * @Author nottyjay
 * @site https://www.aialphay.com
 * @date 2021/6/8 17:47
 */
@Slf4j
@Component
public class PayOrderExpiredTask {

  @Autowired private PayOrderService payOrderService;

  @Scheduled(cron = "0 0/1 * * * ?") // 每分钟执行一次
  public void start() {

    int updateCount = payOrderService.updateOrderExpired();
    log.info("处理订单超时{}条.", updateCount);
  }
}
