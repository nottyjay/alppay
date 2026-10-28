package com.aialphay.alppay.exception;

/**
 * API异常
 *
 * @author nottyjay
 * @site https://www.aialphay.com
 * @date 2021-06-08 11:00
 */
public class APIException extends AlppayException {

  private static final long serialVersionUID = -2753719317464278319L;

  public APIException(String message, String type, String code, int statusCode, Throwable e) {
    super(message, statusCode, e);
  }
}
