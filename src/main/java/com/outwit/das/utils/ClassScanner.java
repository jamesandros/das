package com.outwit.das.utils;

import java.lang.annotation.Annotation;
import java.util.List;

/**
 * 类扫描器
 * @author andros
 *
 * 2015年6月33日下午3:08:17
 */
public interface ClassScanner {

    /**
     * 获取指定包名中的所有类
     */
    List<Class<?>> getClassList(String packageName);

    /**
     * 获取指定包名中指定注解的相关类
     */
    List<Class<?>> getClassListByAnnotation(String packageName, Class<? extends Annotation> annotationClass);

    /**
     * 获取指定包名中指定父类或接口的相关类
     */
    List<Class<?>> getClassListBySuper(String packageName, Class<?> superClass);
}
