package com.aialphay.alppay.core.aop;

import java.lang.annotation.*;

/*
 * 方法级日志切面注解
 *
 * @Author nottyjay
 * @site https://www.aialphay.com
 * @date 2021/6/8 18:00
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface MethodLog {
  String remark() default "";
}
