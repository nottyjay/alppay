package com.aialphay.alppay.pay.util;

import com.aialphay.alppay.pay.rqrs.AbstractRS;

/*
 * api响应结果构造器
 *
 * @Author nottyjay
 * @site https://www.aialphay.com
 * @date 2021/6/8 17:45
 */
public class ApiResBuilder {

  /** 构建自定义响应对象, 默认响应成功 * */
  public static <T extends AbstractRS> T buildSuccess(Class<? extends AbstractRS> T) {

    try {
      T result = (T) T.newInstance();
      return result;

    } catch (Exception e) {
      return null;
    }
  }
}
