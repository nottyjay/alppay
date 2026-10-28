package com.aialphay.alppay.exception;

/**
 * Alppay异常抽象类
 *
 * @author nottyjay
 * @site https://www.aialphay.com
 * @date 2021-06-08 11:00
 */
public abstract class AlppayException extends Exception {

  private static final long serialVersionUID = 2566087783987900120L;

  private int statusCode;

  public AlppayException(String message) {
    super(message, null);
  }

  public AlppayException(String message, Throwable e) {
    super(message, e);
  }

  public AlppayException(String message, int statusCode, Throwable e) {
    super(message, e);
    this.statusCode = statusCode;
  }

  public int getStatusCode() {
    return statusCode;
  }

  @Override
  public String toString() {
    StringBuffer sb = new StringBuffer();
    sb.append(super.toString());
    return sb.toString();
  }
}
