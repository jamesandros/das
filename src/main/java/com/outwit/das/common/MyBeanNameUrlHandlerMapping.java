package com.outwit.das.common;

import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.web.servlet.handler.BeanNameUrlHandlerMapping;

public class MyBeanNameUrlHandlerMapping extends BeanNameUrlHandlerMapping {
	protected String[] determineUrlsForHandler(String beanName) {
		Log.getCommon().debug("啦啦:"+beanName);
		return super.determineUrlsForHandler(beanName);
	}
}
