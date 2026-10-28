package com.aialphay.alppay.pay.ctrl.payorder.payway;

import com.aialphay.alppay.core.constants.CS;
import com.aialphay.alppay.core.model.ApiRes;
import com.aialphay.alppay.pay.ctrl.payorder.AbstractPayOrderController;
import com.aialphay.alppay.pay.rqrs.payorder.payway.AliBarOrderRQ;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/*
 * 支付宝 条码支付 controller
 *
 * @Author nottyjay
 * @site https://www.aialphay.com
 * @date 2021/6/8 17:25
 */
@Slf4j
@RestController
public class AliBarOrderController extends AbstractPayOrderController {

  /** 统一下单接口 * */
  @PostMapping("/api/pay/aliBarOrder")
  public ApiRes aliBarOrder() {

    // 获取参数 & 验证
    AliBarOrderRQ bizRQ = getRQByWithMchSign(AliBarOrderRQ.class);

    // 统一下单接口
    return unifiedOrder(CS.PAY_WAY_CODE.ALI_BAR, bizRQ);
  }
}
