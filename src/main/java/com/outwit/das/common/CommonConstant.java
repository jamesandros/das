package com.outwit.das.common;

/**
 * 全局公共常理定义
 * @author Administrator
 *
 */
public class CommonConstant {
	/**
	 * hessian api 调用用户名
	 */
	public static final String HESSIAN_USER="thkj";
	/**
	 * hessian api 调用密码
	 */
	public static final String HESSIAN_PASSWORD="thkj_888888";
	/**
	 * hessian 链接超时时间
	 */
	public static final long connectTimeout=60000;
	/**
	 * hessian 读取数据超时时间
	 */
	public static final  long readTimeout=60000;
	
	
	/**
	 * http.socket.timeout
	 */
	public static final int SO_TIMEOUT=60000;
	
	
	/**
	 * http.connection.timeout
	 */
	public static final int CONNECTION_TIMEOUT=60000;
	
	/**
	 * 超时时间，单位秒
	 */
	public static int DEFAULT_TIMEOUT = 5;  
	
	/** 
     * 缓存时效 1小时
     */  
    public static final int CACHE_EXP_HOUR = 3600; 
	  
	/** 
     * 缓存时效 1天 
     */  
    public static final int CACHE_EXP_DAY = 3600 * 24;  
  
    /** 
     * 缓存时效 1周 
     */  
    public static final int CACHE_EXP_WEEK = 3600 * 24 * 7;  
  
    /** 
     * 缓存时效 1月 
     */  
    public static final int CACHE_EXP_MONTH = 3600 * 24 * 30; 
    
    /**
     * 时间间隔一天
     */
    public static final long PERIOD_DAY = 24 * 60 * 60 * 1000;
    /**
     * 时间时隔1分钟
     */
    public static final long PERIOD_MIN = 60 * 1000;
    /** 
     * 缓存时效 永久 
     */  
    public static final int CACHE_EXP_FOREVER = 0;
    /**
     * 缓存常量名
     */
    public static final String MEM_CACHED = "mem_cached";
    /**
     * hessian接口前缀
     */
    public static final String  HESSIAN_PREFIX = "/hessian";

}
