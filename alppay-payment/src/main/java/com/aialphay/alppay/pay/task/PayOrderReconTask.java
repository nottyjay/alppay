package com.aialphay.alppay.pay.task;

import com.aialphay.alppay.core.entity.PayOrder;
import com.aialphay.alppay.pay.service.PayOrderReconService;
import com.aialphay.alppay.service.impl.PayOrderService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Date;

/*
 * 对账定时任务
 *
 * @Author xiaocui
 * @date 2026/6/3 10:47
 */
@Slf4j
@Component
public class PayOrderReconTask {
    private static final int QUERY_PAGE_SIZE = 100; // 每次查询数量

    @Autowired
    private PayOrderService payOrderService;
    @Autowired private PayOrderReconService reconService;

    @Scheduled(cron = "0 0/5 * * * ?")// 每5分钟执行一次
    public void start() {

        // 查询条件： 已经过期的订单 & 未进行对账处理 & 是终态订单
        LambdaQueryWrapper<PayOrder> lambdaQueryWrapper =
                PayOrder.gw()
                        .eq(PayOrder::getReconState, PayOrder.RECON_STATE_UN_CHECK)
                        .notIn(PayOrder::getState, Arrays.asList(PayOrder.STATE_INIT, PayOrder.STATE_ING))
                        .le(PayOrder::getExpiredTime, new Date());

        int currentPageIndex = 1; // 当前页码
        while (true) {

            try {
                IPage<PayOrder> payOrderIPage =
                        payOrderService.page(new Page(currentPageIndex, QUERY_PAGE_SIZE), lambdaQueryWrapper);

                if (payOrderIPage == null || payOrderIPage.getRecords().isEmpty()) { // 本次查询无结果, 不再继续查询;
                    break;
                }

                for (PayOrder payOrder : payOrderIPage.getRecords()) {
                    reconService.reconciliationOrder(payOrder);
                }

                // 已经到达页码最大量，无需再次查询
                if (payOrderIPage.getPages() <= currentPageIndex) {
                    break;
                }
                currentPageIndex++;

            } catch (Exception e) { // 出现异常，直接退出，避免死循环。
                log.error("error", e);
                break;
            }
        }
    }
}
