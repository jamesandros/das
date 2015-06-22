package com.outwit.das.utils;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.outwit.das.utils.scan.ClassScanner;
import com.outwit.das.utils.scan.DefaultClassScanner;

/**
 * 实例工厂
 * @author andros
 *
 * 2015年6月18日下午3:09:51
 */
public class InstanceFactory {

    private static final Map<String, Object> cache = new ConcurrentHashMap<String, Object>();

    /**
     * ClassScanner
     */
    private static final String CLASS_SCANNER = "outwit.customer.class_scanner";

    /**
     * 获取 ClassScanner
     */
    public static ClassScanner getClassScanner() {
        return getInstance(CLASS_SCANNER, DefaultClassScanner.class);
    }

    /**
     * 获取 DataSourceFactory
     */

    @SuppressWarnings("unchecked")
    public static <T> T getInstance(String cacheKey, Class<T> defaultImplClass) {
        // 若缓存中存在对应的实例，则返回该实例
        if (cache.containsKey(cacheKey)) {
            return (T) cache.get(cacheKey);
        }
        // 从配置文件中获取相应的接口实现类配置
        String implClassName = ConfigHelper.getString(cacheKey);
        // 若实现类配置不存在，则使用默认实现类
        if (StringUtil.isEmpty(implClassName)) {
            implClassName = defaultImplClass.getName();
        }
        // 通过反射创建该实现类对应的实例
        T instance = ObjectUtil.newInstance(implClassName);
        // 若该实例不为空，则将其放入缓存
        if (instance != null) {
            cache.put(cacheKey, instance);
        }
        // 返回该实例
        return instance;
    }
}
