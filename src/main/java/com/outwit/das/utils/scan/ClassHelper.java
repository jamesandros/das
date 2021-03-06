package com.outwit.das.utils.scan;

import java.lang.annotation.Annotation;
import java.util.List;

import com.outwit.das.utils.ConfigHelper;
import com.outwit.das.utils.InstanceFactory;

/**
 * 根据条件获取相关类
 * @author andros
 *
 * 2015年6月21日下午3:08:10
 */
public class ClassHelper {

    /**
     * 获取基础包名
     */
    private static String basePackage = ConfigHelper.getString("base_package");
    
    /**
     * 获取 ClassScanner
     */
    private static final ClassScanner classScanner = InstanceFactory.getClassScanner();
    
    /**
     * 获取基础包名中的所有类
     */
    public static List<Class<?>> getClassList() {
        return classScanner.getClassList(basePackage);
    }

    /**
     * 获取基础包名中指定父类或接口的相关类
     */
    public static List<Class<?>> getClassListBySuper(Class<?> superClass) {
        return classScanner.getClassListBySuper(basePackage, superClass);
    }

    /**
     * 获取基础包名中指定注解的相关类
     */
    public static List<Class<?>> getClassListByAnnotation(Class<? extends Annotation> annotationClass) {
        return classScanner.getClassListByAnnotation(basePackage, annotationClass);
    }
    /**
     * 设置其他的包
     * @param basePackage
     */
	public static void setBasePackage(String basePackage) {
		ClassHelper.basePackage = basePackage;
	}
    
}
