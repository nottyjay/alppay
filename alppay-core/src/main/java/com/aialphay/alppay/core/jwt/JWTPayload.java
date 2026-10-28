package com.aialphay.alppay.core.jwt;

import com.alibaba.fastjson.JSONObject;

import java.util.Map;

import com.aialphay.alppay.core.model.security.AlpUserDetails;
import lombok.Data;

/*
* JWT payload 载体
* 格式：
    {
        "sysUserId": "10001",
        "created": "1568250147846",
        "cacheKey": "KEYKEYKEYKEY",
    }
* @Author nottyjay
* @site https://www.aialphay.com
* @date 2021/6/8 18:01
*/
@Data
public class JWTPayload {

  private Long sysUserId; // 登录用户ID
  private Long created; // 创建时间, 格式：13位时间戳
  private String cacheKey; // redis保存的key

  protected JWTPayload() {}

  public JWTPayload(AlpUserDetails aliUserDetails) {

    this.setSysUserId(aliUserDetails.getSysUser().getSysUserId());
    this.setCreated(System.currentTimeMillis());
    this.setCacheKey(aliUserDetails.getCacheKey());
  }

  /** toMap * */
  public Map<String, Object> toMap() {
    JSONObject json = (JSONObject) JSONObject.toJSON(this);
    return json.toJavaObject(Map.class);
  }
}
