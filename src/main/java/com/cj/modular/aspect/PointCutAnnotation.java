package com.cj.modular.aspect;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * <p>
 * 面向切面的注解
 * </p>
 *
 * @author Caoj
 * @version 2022-08-16 18:55
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.PACKAGE})
public @interface PointCutAnnotation {
}
