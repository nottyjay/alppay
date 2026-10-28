package com.aialphay.alppay.core.service;

import com.aialphay.alppay.core.entity.PayOrder;

/***
 * 码牌相关逻辑
 *
 * @Author nottyjay
 * @site https://www.aialphay.com
 * @date 2024/11/4 15:08
 */
public interface IMchQrcodeManager {

  /**
   * 功能描述: 查询商户配置信息
   * @Return: com.aialphay.alppay.core.entity.PayOrder
   * @Author: nottyjay
   * @Date: 2024/11/4 15:14
   */
  PayOrder queryMchInfoByQrc(String id);
}
