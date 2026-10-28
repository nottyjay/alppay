package com.aialphay.alppay;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 数据结构列表属性注解
 *
 * @author nottyjay
 * @site https://www.aialphay.com
 * @date 2021-06-08 11:00
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(value = {ElementType.FIELD})
public @interface ApiListField {

  /** JSON列表属性映射名称 */
  String value() default "";
}
