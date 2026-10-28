package com.aialphay.alppay.core.model.params.icbc;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.aialphay.alppay.core.model.params.NormalMchParams;
import com.aialphay.alppay.core.utils.StringKit;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

@Data
public class IcbcNormalMchParams extends NormalMchParams {

  private String appid;
  private String merchantId;
  private String privateKey;
  private String apiGatewayPublicKey;

  @Override
  public String deSenData() {
    IcbcNormalMchParams mchParams = this;
    if (StringUtils.isNotBlank(this.privateKey)) {
      mchParams.setPrivateKey(StringKit.str2Star(this.privateKey, 4, 4, 6));
    }
    if (StringUtils.isNotBlank(this.apiGatewayPublicKey)) {
      mchParams.setApiGatewayPublicKey(StringKit.str2Star(this.apiGatewayPublicKey, 6, 6, 6));
    }
    return ((JSONObject) JSON.toJSON(mchParams)).toJSONString();
  }
}
