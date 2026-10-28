package com.aialphay.alppay.core.service;

import com.aialphay.alppay.core.model.DBApplicationConfig;

public interface ISysConfigService {

  /** 获取应用的配置参数 * */
  DBApplicationConfig getDBApplicationConfig();
}
