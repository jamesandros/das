package com.outwit.das.utils;

import java.lang.annotation.Annotation;

/**
 * 用于获取注解类的模板类
 * @author andros
 *
 * 2015年6月18日下午3:07:36
 */
public abstract class AnnotationClassTemplate extends ClassTemplate {

    protected final Class<? extends Annotation> annotationClass;

    protected AnnotationClassTemplate(String packageName, Class<? extends Annotation> annotationClass) {
        super(packageName);
        this.annotationClass = annotationClass;
    }
}
