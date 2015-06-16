package com.outwit.das.utils;

import java.util.List;



/**
 * 初始化 IOC 容器
 *
 * @author huangyong
 * @since 1.0
 */
public class IocHelper {
    /**
     * 查找实现类
     */
    public static Class<?> findImplementClass(Class<?> interfaceClass) {
        Class<?> implementClass = interfaceClass;
        List<Class<?>> implementClassList = ClassHelper.getClassListBySuper(interfaceClass);
        if (CollectionUtil.isNotEmpty(implementClassList)) {
            // 获取第一个实现类
            implementClass = implementClassList.get(0);
        }
        return implementClass;
    }
}
