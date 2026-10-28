package com.aialphay.alppay.core.model.params.xxpay;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.aialphay.alppay.core.model.params.NormalMchParams;
import com.aialphay.alppay.core.utils.StringKit;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

/*
 * 小新支付 普通商户参数定义
 *
 * @Author nottyjay
 * @site https://www.aialphay.com
 * @date 2021/9/10 21:51
 */
@Data
public class XxpayNormalMchParams extends NormalMchParams {

  /** 商户号 */
  private String mchId;

  /** 私钥 */
  private String key;

  /** 支付网关地址 */
  private String payUrl;

  @Override
  public String deSenData() {
    XxpayNormalMchParams mchParams = this;
    if (StringUtils.isNotBlank(this.key)) {
      mchParams.setKey(StringKit.str2Star(this.key, 4, 4, 6));
    }
    return ((JSONObject) JSON.toJSON(mchParams)).toJSONString();
  }
}
