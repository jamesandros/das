package com.outwit.das.utils;

import java.util.Iterator;
import java.util.List;



/**
 *  IOC 容器 
 * @author andros
 *
 * 2015年6月18日下午3:10:01
 */
public class IocHelper {
    /**
     * 查找实现类
     */
    public static Class<?> findImplementClass(Class<?> interfaceClass) {
        Class<?> implementClass = interfaceClass;
        List<Class<?>> implementClassList = ClassHelper.getClassListBySuper(interfaceClass);
        if (CollectionUtil.isNotEmpty(implementClassList)) {
           implementClass = implementClassList.get(0);
        }
        return implementClass;
    }
}
