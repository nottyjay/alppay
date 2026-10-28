package com.aialphay.alppay.core.exception;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.authentication.InternalAuthenticationServiceException;

/*
 * Spring Security 框架自定义异常类
 *
 * @Author nottyjay
 * @site https://www.aialphay.com
 * @date 2021/6/15 11:23
 */
@Getter
@Setter
public class AlppayAuthenticationException extends InternalAuthenticationServiceException {

  private BizException bizException;

  public AlppayAuthenticationException(String msg, Throwable cause) {
    super(msg, cause);
  }

  public AlppayAuthenticationException(String msg) {
    super(msg);
  }

  public static AlppayAuthenticationException build(String msg) {
    return build(new BizException(msg));
  }

  public static AlppayAuthenticationException build(BizException ex) {
    AlppayAuthenticationException result = new AlppayAuthenticationException(ex.getMessage());
    result.setBizException(ex);
    return result;
  }
}
