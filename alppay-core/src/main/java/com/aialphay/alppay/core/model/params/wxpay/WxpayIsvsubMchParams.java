package com.aialphay.alppay.core.model.params.wxpay;

import com.aialphay.alppay.core.model.params.IsvsubMchParams;
import lombok.Data;

/*
 * 微信官方支付 配置参数
 *
 * @Author nottyjay
 * @site https://www.aialphay.com
 * @date 2021/6/8 18:02
 */
@Data
public class WxpayIsvsubMchParams extends IsvsubMchParams {

  /** 子商户ID * */
  private String subMchId;

  /** 子账户appID * */
  private String subMchAppId;
}
