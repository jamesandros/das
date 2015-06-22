package com.outwit.das.utils;

import com.outwit.das.utils.scan.ClassTemplate;

/**
 * 用于获取子类的模板类
 * @author andros
 *
 * 2015年6月18日下午3:21:49
 */
public abstract class SupperClassTemplate extends ClassTemplate {

    protected final Class<?> superClass;

    protected SupperClassTemplate(String packageName, Class<?> superClass) {
        super(packageName);
        this.superClass = superClass;
    }
}
