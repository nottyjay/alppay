package com.aialphay.alppay.pay.channel;

import com.alibaba.fastjson.JSONObject;
import com.aialphay.alppay.pay.model.MchAppConfigContext;

/**
 * @Author nottyjay
 * @Description: 301方式获取渠道侧用户ID， 如微信openId 支付宝的userId等
 * @date 2021/5/2 15:10
 */
public interface IChannelUserService {

  /** 获取到接口code * */
  String getIfCode();

  /** 获取重定向地址 * */
  String buildUserRedirectUrl(String callbackUrlEncode, MchAppConfigContext mchAppConfigContext);

  /** 获取渠道用户ID * */
  String getChannelUserId(JSONObject reqParams, MchAppConfigContext mchAppConfigContext);
}
