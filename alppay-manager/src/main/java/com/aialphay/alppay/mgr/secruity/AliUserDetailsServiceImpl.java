package com.aialphay.alppay.mgr.secruity;

import com.aialphay.alppay.core.constants.CS;
import com.aialphay.alppay.core.entity.SysUser;
import com.aialphay.alppay.core.entity.SysUserAuth;
import com.aialphay.alppay.core.exception.AlppayAuthenticationException;
import com.aialphay.alppay.core.model.security.AlpUserDetails;
import com.aialphay.alppay.core.utils.RegKit;
import com.aialphay.alppay.service.impl.SysUserAuthService;
import com.aialphay.alppay.service.impl.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/*
 * 实现UserDetailsService 接口
 *
 * @Author nottyjay
 * @site https://www.aialphay.com
 * @date 2021/6/8 17:13
 */
@Service
public class AliUserDetailsServiceImpl implements UserDetailsService {

  @Autowired private SysUserService sysUserService;

  @Autowired private SysUserAuthService sysUserAuthService;

  /**
   * 此函数为： authenticationManager.authenticate(upToken) 内部调用 ; 需返回 用户信息载体 / 用户密码 。 用户角色+权限的封装集合
   * (暂时不查询， 在验证通过后再次查询，避免用户名密码输入有误导致查询资源浪费)
   *
   * <p>*
   */
  @Override
  public UserDetails loadUserByUsername(String loginUsernameStr) throws UsernameNotFoundException {

    // 登录方式， 默认为账号密码登录
    Byte identityType = CS.AUTH_TYPE.LOGIN_USER_NAME;
    if (RegKit.isMobile(loginUsernameStr)) {
      identityType = CS.AUTH_TYPE.TELPHONE; // 手机号登录
    }

    // 首先根据登录类型 + 用户名得到 信息
    SysUserAuth auth =
        sysUserAuthService.selectByLogin(loginUsernameStr, identityType, CS.SYS_TYPE.MGR);

    if (auth == null) { // 没有该用户信息
      throw AlppayAuthenticationException.build("用户名/密码错误！");
    }

    // 用户ID
    Long userId = auth.getUserId();

    SysUser sysUser = sysUserService.getById(userId);

    if (sysUser == null) {
      throw AlppayAuthenticationException.build("用户名/密码错误！");
    }

    if (CS.PUB_USABLE != sysUser.getState()) { // 状态不合法
      throw AlppayAuthenticationException.build("用户状态不可登录，请联系管理员！");
    }

    return new AlpUserDetails(sysUser, auth.getCredential());
  }
}
