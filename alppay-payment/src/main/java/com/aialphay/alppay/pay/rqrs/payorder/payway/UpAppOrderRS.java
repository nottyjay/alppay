package com.aialphay.alppay.pay.rqrs.payorder.payway;

import com.aialphay.alppay.core.constants.CS;
import com.aialphay.alppay.pay.rqrs.payorder.CommonPayDataRS;
import lombok.Data;

/*
 * 支付方式： UPACP_APP
 *
 * @Author nottyjay
 * @site https://www.aialphay.com
 * @date 2021/12/1 19:57
 */
@Data
public class UpAppOrderRS extends CommonPayDataRS {

  private String payData;

  @Override
  public String buildPayDataType() {
    return CS.PAY_DATA_TYPE.YSF_APP;
  }

  @Override
  public String buildPayData() {
    return payData;
  }
}
