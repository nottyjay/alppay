package com.aialphay.alppay.pay.rqrs.payorder.payway;

import com.aialphay.alppay.core.constants.CS;
import com.aialphay.alppay.pay.rqrs.payorder.UnifiedOrderRS;
import lombok.Data;

/*
 * 支付方式： ALI_APP
 *
 * @Author nottyjay
 * @site https://www.aialphay.com
 * @date 2021/6/8 17:34
 */
@Data
public class AliAppOrderRS extends UnifiedOrderRS {

  private String payData;

  @Override
  public String buildPayDataType() {
    return CS.PAY_DATA_TYPE.ALI_APP;
  }

  @Override
  public String buildPayData() {
    return payData;
  }
}
