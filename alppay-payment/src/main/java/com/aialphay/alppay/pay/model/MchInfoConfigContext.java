package com.aialphay.alppay.pay.model;

import com.aialphay.alppay.core.entity.MchApp;
import com.aialphay.alppay.core.entity.MchInfo;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import lombok.Data;

/*
 * 商户配置信息
 * 放置到内存， 避免多次查询操作
 *
 * @Author nottyjay
 * @site https://www.aialphay.com
 * @date 2021/6/8 17:29
 */
@Data
public class MchInfoConfigContext {

  /** 商户信息缓存 */
  private String mchNo;

  private Byte mchType;
  private MchInfo mchInfo;
  private Map<String, MchApp> appMap = new ConcurrentHashMap<>();

  /** 重置商户APP * */
  public void putMchApp(MchApp mchApp) {
    appMap.put(mchApp.getAppId(), mchApp);
  }

  /** get商户APP * */
  public MchApp getMchApp(String appId) {
    return appMap.get(appId);
  }
}
