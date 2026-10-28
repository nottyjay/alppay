package com.aialphay.alppay.pay.service;

import com.aialphay.alppay.core.exception.BizException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/*
 * 通用 Validator
 *
 * @Author nottyjay
 * @site https://www.aialphay.com
 * @date 2021/6/8 17:47
 */
@Service
public class ValidateService {

  @Autowired private Validator validator;

  public void validate(Object obj) {

    Set<ConstraintViolation<Object>> resultSet = validator.validate(obj);
    if (resultSet == null || resultSet.isEmpty()) {
      return;
    }
    resultSet.stream()
        .forEach(
            item -> {
              throw new BizException(item.getMessage());
            });
  }
}
