package com.outwit.das.common;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * 
 * @author coderyu
 * @Date   2014-5-25
 *
 */
public class PropertiesConfig {


	private  static final Properties hessianConfig  = new Properties(); //接口地址配置文件
	private  static final Properties cacheConfig =  new Properties();//缓存服务器地址
	private  static final Properties interfaceConfig = new Properties();//接口相关配置信息
	private  static final Properties payConfig = new Properties();//支付配置文件
	private  static final Properties taskConfig = new Properties();//任务配置文件
	static{
		InputStream in =null;
		try {
			in = PropertiesConfig.class.getResourceAsStream("/hessian.properties");
			if(in!=null){
				hessianConfig.load(in);
			}
			in =  PropertiesConfig.class.getResourceAsStream("/cache.properties");
			if(in!=null){
				cacheConfig.load(in);
			}
			in = PropertiesConfig.class.getResourceAsStream("/interface.properties");
			if(in!=null){
				interfaceConfig.load(in);
			}
			in = PropertiesConfig.class.getResourceAsStream("/payconfig.properties");
			if(in!=null){
				payConfig.load(in);
			}
			in = PropertiesConfig.class.getResourceAsStream("/task.properties");
			if(in!=null){
				taskConfig.load(in);
			}
		} catch (IOException e) {
			
		}finally{
			if(in!=null){
				try {
					in.close();
				} catch (IOException e) {
				}
			}
		}
	}
	
	public static Properties getHessianConfig(){
		return hessianConfig;
	}

	public static Properties getCacheConfig() {
		return cacheConfig;
	}

	public static Properties getInterfaceconfig() {
		return interfaceConfig;
	}

	public static Properties getPayconfig() {
		return payConfig;
	}

	public static Properties getTaskconfig() {
		return taskConfig;
	}
	
}
