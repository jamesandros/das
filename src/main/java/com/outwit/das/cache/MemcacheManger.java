package com.outwit.das.cache;

import java.io.IOException;
import java.net.InetSocketAddress;

import com.outwit.das.common.Log;
import com.outwit.das.common.PropertiesConfig;

import net.spy.memcached.AddrUtil;
import net.spy.memcached.MemcachedClient;

/**
 * memcached 缓存客户端
 * @author coderyu
 *
 */
public class MemcacheManger {

	private static MemcachedClient memcachedClient = null;
	
	static{
		try {
			String url =PropertiesConfig.getCacheConfig().getProperty("cache").trim();
			if(url.contains("#")){
				String ip_port [] = url.split("#");
				memcachedClient = new MemcachedClient(new InetSocketAddress(ip_port[0], Integer.parseInt(ip_port[1])));
				Log.getCache().debug("链接缓存系统并初始化成功");
			}else{
				memcachedClient = new MemcachedClient(AddrUtil.getAddresses(url));
			}
		} catch (IOException e) {
			Log.getCache().error("链接缓存系统并初始化失败",e);
		}
	}
	
	public static MemcachedClient getMemcachedClient(){
		return memcachedClient;
	}
	
	/**
	 * 关闭缓存客户端的链接
	 */
	public static void disConnect() {  
        if (memcachedClient != null) {  
            try {  
            	memcachedClient.shutdown();  
            	Log.getCache().debug("Shutdown MemcachedManager...");  
            } catch (Exception e) {  
            	Log.getCache().error(e.getMessage(), e);  
            }  
        }  
    }     
	
	
	
}
