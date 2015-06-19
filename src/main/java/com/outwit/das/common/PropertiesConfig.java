package com.outwit.das.common;
import java.util.Properties;

import com.outwit.das.utils.PropsUtil;

/**
 * 
 * @author andros
 *
 * 2015年6月19日上午11:20:28
 */
public class PropertiesConfig {
	private  static final Properties config = PropsUtil.loadProps("config") ;
	private  static final Properties interfaceConfig =PropsUtil.loadProps("interface");
	private  static final Properties payConfig =PropsUtil.loadProps("payconfig");
	public static Properties getConfig() {
		return config;
	}
	public static Properties getInterfaceconfig() {
		return interfaceConfig;
	}
	public static Properties getPayconfig() {
		return payConfig;
	}
	
}
