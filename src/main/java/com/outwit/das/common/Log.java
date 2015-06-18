package com.outwit.das.common;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;

import com.outwit.das.annotation.Hessian;
import com.outwit.das.utils.ClassHelper;
import com.outwit.das.utils.CollectionUtil;
import com.outwit.das.utils.IocHelper;
import com.outwit.das.utils.PropsUtil;

/**
 * 
 * @author andros
 *
 * 2015年6月17日上午10:57:40
 */
public class Log{
	
	//公共模块日志
	private static Logger common = Logger.getLogger("common");
	//会员日志
	private static Logger member =  Logger.getLogger("member");
	//权限日志
	private static Logger permission = Logger.getLogger("permission");
	//缓存日志日志
	private static Logger cache = Logger.getLogger("cache");
	
	public static Logger getCommon() {
		return common;
	}
	public static Logger getMember() {
		return member;
	}
	public static Logger getPermission() {
		return permission;
	}
	public static Logger getCache() {
		return cache;
	}
	public static void main(String[] args) {
//		List<Class<?>> hessianInterfaceList = ClassHelper.getClassListByAnnotation(Hessian.class);
//		if (CollectionUtil.isNotEmpty(hessianInterfaceList)) {
//			for (Class<?> hessianInterface : hessianInterfaceList) {
//                // 获取 Hessian URL
//            	System.out.println(hessianInterface.getName());
//            	String backpage = hessianInterface.getPackage().getName();
//            	System.out.println(backpage);
//            	backpage = backpage.replace("com.outwit.das", "").replace(".service", "").replace('.','/');
//            	System.out.println(backpage);
//                // 获取 Hessian 接口的实现类
//                Class<?> implClass = IocHelper.findImplementClass(hessianInterface);
//                String implName = implClass.getSimpleName();
//                implName = implName.substring(0, 1).toLowerCase()+implName.substring(1, implName.length());
//                System.out.println(implName);
//                
//            }
//        }
		
		System.out.println(PropsUtil.getConfigMap().get("hessian.username"));
	}
}
