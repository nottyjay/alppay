package com.aialphay.alppay.mch.service;

import com.aialphay.alppay.core.constants.CS;
import com.aialphay.alppay.core.service.ICodeSysTypeManager;
import org.springframework.stereotype.Component;

@Component
public class CodeSysTypeManager implements ICodeSysTypeManager {

  @Override
  public String getCodeSysName() {
    return CS.CODE_SYS_NAME_SET.alipay_MERCHANT;
  }
}
