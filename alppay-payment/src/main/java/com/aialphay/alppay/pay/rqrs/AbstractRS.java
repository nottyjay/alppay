package com.aialphay.alppay.pay.rqrs;

import com.alibaba.fastjson.JSON;
import java.io.Serializable;
import lombok.Data;

/*
 * 接口抽象RS对象, 本身无需实例化
 *
 * @Author nottyjay
 * @site https://www.aialphay.com
 * @date 2021/6/8 17:39
 */
@Data
public abstract class AbstractRS implements Serializable {

  public String toJSONString() {
    return JSON.toJSONString(this);
  }
}
