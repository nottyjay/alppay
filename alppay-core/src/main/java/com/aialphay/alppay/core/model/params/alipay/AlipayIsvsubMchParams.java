package com.aialphay.alppay.core.model.params.alipay;

import com.aialphay.alppay.core.model.params.IsvsubMchParams;
import lombok.Data;

/*
 * 支付宝 特约商户参数定义
 *
 * @Author nottyjay
 * @site https://www.aialphay.com
 * @date 2021/6/8 16:33
 */
@Data
public class AlipayIsvsubMchParams extends IsvsubMchParams {

  private String appAuthToken;
}
