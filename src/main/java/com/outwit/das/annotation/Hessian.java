package com.outwit.das.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.caucho.hessian.server.HessianServlet;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Hessian {
    String value() default ""; // Hessian URL
}
