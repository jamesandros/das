package com.outwit.das.common;

import java.util.List;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

import com.outwit.das.annotation.Hessian;
import com.outwit.das.utils.CollectionUtil;
import com.outwit.das.utils.IocHelper;
import com.outwit.das.utils.StringUtil;
import com.outwit.das.utils.scan.ClassHelper;
public class DynamicCreateHessianBean implements ApplicationContextAware,ApplicationListener<ContextRefreshedEvent>{
	private ApplicationContext applicationContext;  
	public void onApplicationEvent(ContextRefreshedEvent event) {
		if(null == event.getApplicationContext().getParent()){
            //需要执行的逻辑代码，当spring容器初始化完成后就会执行该方法。  
			Log.getCommon().debug("开始创建hessianBean");
			//得到所有有hessian的注解
			List<Class<?>> hessianInterfaceList = ClassHelper.getClassListByAnnotation(Hessian.class);
			if (CollectionUtil.isNotEmpty(hessianInterfaceList)) {
				DefaultListableBeanFactory acf = (DefaultListableBeanFactory) applicationContext.getAutowireCapableBeanFactory();
				for (Class<?> hessianInterface : hessianInterfaceList) {
	            	Class<?> implClass = IocHelper.findImplementClass(hessianInterface);
	            	if(hessianInterface == implClass) {
	            		continue;
	            	}
	                String implName = StringUtil.firstToLower(implClass.getSimpleName());
	                //获取 Hessian URL
	                String url = hessianInterface.getAnnotation(Hessian.class).value();
	                if("".equals(url)){
	                	String backpage = hessianInterface.getPackage().getName();
	                	url = backpage.replace("com.outwit.das", "").replace(".service", "").replace('.','/');
	                }
	                String beanName = CommonConstant.HESSIAN_PREFIX + url + "/"+hessianInterface.getSimpleName();
	                if(!applicationContext.containsBeanDefinition(beanName)){
	                	BeanDefinitionBuilder userBeanDefinitionBuilder = BeanDefinitionBuilder.genericBeanDefinition(DaslHessianServiceExporter.class);
						userBeanDefinitionBuilder.addPropertyReference("service", implName);
						userBeanDefinitionBuilder.addPropertyValue("serviceInterface", hessianInterface.getName());
						acf.registerBeanDefinition(beanName, userBeanDefinitionBuilder.getBeanDefinition());
						Log.getCommon().info(beanName+"-"+userBeanDefinitionBuilder.getBeanDefinition());
	                }
	            }
	        }
		}
	}
	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		this.applicationContext = applicationContext;
	}
	
}
