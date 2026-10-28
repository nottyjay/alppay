package com.aialphay.alppay.mch.ctrl;

import com.aialphay.alppay.core.constants.ApiCodeEnum;
import com.aialphay.alppay.core.constants.CS;
import com.aialphay.alppay.core.ctrls.AbstractCtrl;
import com.aialphay.alppay.core.entity.SysUser;
import com.aialphay.alppay.core.model.ApiRes;
import com.aialphay.alppay.mch.config.SystemYmlConfig;
import com.aialphay.alppay.service.impl.SysConfigService;
import com.aialphay.alppay.core.model.security.AlpUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * 通用ctrl类
 *
 * @Author nottyjay
 * @modify zhuxiao
 * @site https://www.aialphay.com
 * @date 2021-04-27 15:50
 */
public abstract class CommonCtrl extends AbstractCtrl {

  @Autowired protected SystemYmlConfig mainConfig;

  @Autowired protected SysConfigService sysConfigService;

  /** 获取当前用户ID */
  protected AlpUserDetails getCurrentUser() {

    return (AlpUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
  }

  /** 获取当前商户ID * */
  protected String getCurrentMchNo() {
    return getCurrentUser().getSysUser().getBelongInfoId();
  }

  /**
   * 获取当前用户登录IP
   *
   * @return
   */
  protected String getIp() {
    return getClientIp();
  }

  /**
   * 校验当前用户是否为超管
   *
   * @return
   */
  protected ApiRes checkIsAdmin() {
    SysUser sysUser = getCurrentUser().getSysUser();
    if (sysUser.getIsAdmin() != CS.YES) {
      return ApiRes.fail(ApiCodeEnum.SYS_PERMISSION_ERROR);
    } else {
      return null;
    }
  }
}
