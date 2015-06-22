package com.outwit.das.utils.cache;


/**
 * 
 * @author coderyu
 * @Date   2014-3-12
 *
 */
public interface CacheManager{

	
	public boolean add(String key, Object o, int expire);
	
	public boolean set(String key, Object o, int expire);
	
	public <T> T get(String key);
	
	public <T> T asyncGet(String key);
	
	public <T> T asyncGet(String key,long time);
	
	public boolean del(String key);
}
