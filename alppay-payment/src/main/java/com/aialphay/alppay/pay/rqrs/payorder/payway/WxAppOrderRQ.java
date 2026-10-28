package com.aialphay.alppay.pay.rqrs.payorder.payway;

import com.aialphay.alppay.core.constants.CS;
import com.aialphay.alppay.pay.rqrs.payorder.UnifiedOrderRQ;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/*
 *  支付方式： WX_APP
 *
 * @author xiaoyu
 * @site https://www.aialphay.com
 * @date 2022/12/20 8:12
 */
@Data
public class WxAppOrderRQ extends UnifiedOrderRQ {

  /** 微信openid * */
  @NotBlank(message = "openid不能为空")
  private String openid;

  /** 构造函数 * */
  public WxAppOrderRQ() {
    this.setWayCode(CS.PAY_DATA_TYPE.WX_APP); // 默认 wayCode, 避免validate出现问题
  }

  @Override
  public String getChannelUserId() {
    return this.openid;
  }
}
