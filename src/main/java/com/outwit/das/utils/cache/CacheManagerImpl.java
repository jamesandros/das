package com.outwit.das.utils.cache;
import java.util.concurrent.TimeUnit;

import com.outwit.das.common.CommonConstant;
import com.outwit.das.common.Log;

import net.spy.memcached.internal.GetFuture;
import net.spy.memcached.internal.OperationFuture;
/**
 * 
 * @author coderyu
 * @param <T>
 * @Date   2014-3-12
 *
 */
public class CacheManagerImpl implements CacheManager{
	
	
	/**
	 * 
	 * @param key
	 * @param o
	 * @param expire 单位秒
	 * @return
	 */
	public boolean add(String key, Object o, int expire){
		OperationFuture<Boolean> future= MemcacheManger.getMemcachedClient().add(key, expire, o);
		try {
			return future.get(CommonConstant.DEFAULT_TIMEOUT, TimeUnit.SECONDS);
		} catch (Exception e) {
			Log.getCache().error("保存对象"+key+"异常",e);
			return false;
		} 
	}
	
	
	/**
	 * 
	 * @param key
	 * @param o
	 * @param expire 单位秒
	 * @return
	 */
	public boolean set(String key, Object o, int expire){
		OperationFuture<Boolean> future= MemcacheManger.getMemcachedClient().set(key, expire, o);
		try {
			return future.get(CommonConstant.DEFAULT_TIMEOUT, TimeUnit.SECONDS);
		} catch (Exception e) {
			Log.getCache().error("set对象"+key+"异常",e);
			return false;
		}
	}
	
	/**
	 * 
	 * @param key
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public <T> T get(String key){
		Object object = null;  
        try {  
            object = (T) MemcacheManger.getMemcachedClient().get(key);  
        } catch (Exception e) {  
        	Log.getCache().error("获取对象"+key+"异常", e);  
        }  
        return (T)object;  
	}
	
	/**
	 * 
	 * @param key
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public <T> T asyncGet(String key){
		
		Object myObj=null;
		GetFuture<Object> f=MemcacheManger.getMemcachedClient().asyncGet(key);
		try {
		    myObj=f.get(CommonConstant.DEFAULT_TIMEOUT, TimeUnit.SECONDS);
		}catch (Exception e) {
			Log.getCache().error("异步获取对象"+key+"异常",e);
		} 
		return (T)myObj;
	}
	
	/**
	 * 
	 * @param key
	 * @param time 获取对象的超时时间
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public <T> T asyncGet(String key,long time){
		
		Object myObj=null;
		GetFuture<Object> f=MemcacheManger.getMemcachedClient().asyncGet(key);
		try {
		    myObj=f.get(time,TimeUnit.SECONDS);
		}catch (Exception e) {
			Log.getCache().error("异步获取对象"+key+"异常",e);
		}
		return (T)myObj;
	}

	/**
	 * 删除混存中指定的对象
	 * @param key
	 * @return
	 */
	public boolean del(String key) {
		OperationFuture<Boolean> future=MemcacheManger.getMemcachedClient().delete(key);
		try {
			return future.get(CommonConstant.DEFAULT_TIMEOUT, TimeUnit.SECONDS);
		} catch (Exception e) {
			Log.getCache().error("删除对象"+key+"异常",e);
			return false;
		}
	}
}
