package com.aialphay.alppay.pay.task;

import com.aialphay.alppay.service.impl.RefundOrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/*
 * 退款订单过期定时任务
 *
 * @Author nottyjay
 * @site https://www.aialphay.com
 * @date 2021/6/17 14:36
 */
@Slf4j
@Component
public class RefundOrderExpiredTask {

  @Autowired private RefundOrderService refundOrderService;

  @Scheduled(cron = "0 0/1 * * * ?") // 每分钟执行一次
  public void start() {

    int updateCount = refundOrderService.updateOrderExpired();
    log.info("处理退款订单超时{}条.", updateCount);
  }
}
