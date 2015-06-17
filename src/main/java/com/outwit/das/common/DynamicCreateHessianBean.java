package com.outwit.das.common;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
public class DynamicCreateHessianBean implements ApplicationContextAware,ApplicationListener<ContextRefreshedEvent>{
	private ApplicationContext applicationContext;  
	public void onApplicationEvent(ContextRefreshedEvent event) {
		Log.getCommon().debug("已经加载进入了");
		if(!applicationContext.containsBeanDefinition("/hessian/myhessian/hessianok")){
			DefaultListableBeanFactory acf = (DefaultListableBeanFactory) applicationContext.getAutowireCapableBeanFactory(); 
			BeanDefinitionBuilder userBeanDefinitionBuilder = BeanDefinitionBuilder.genericBeanDefinition(DaslHessianServiceExporter.class);
			userBeanDefinitionBuilder.addPropertyReference("service", "userServiceImpl");
			userBeanDefinitionBuilder.addPropertyValue("serviceInterface", "com.outwit.das.permission.service.UserService");
			acf.registerBeanDefinition("/hessian/myhessian/hessianok", userBeanDefinitionBuilder.getBeanDefinition());	
			
			DaslHessianServiceExporter dsa  = (DaslHessianServiceExporter)applicationContext.getBean("/hessian/myhessian/hessianok");
			System.out.println(dsa.getService());
			System.out.println(dsa.getServiceInterface());
		}
	}
	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		this.applicationContext = applicationContext;
	}
	
}
