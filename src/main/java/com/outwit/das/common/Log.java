package com.outwit.das.common;
import org.apache.log4j.Logger;
/**
 * 
 * @author 
 * @Date   2014-5-20
 *
 */
public class Log{
	
	//公共模块日志
	private static Logger common = Logger.getLogger("common");
	//会员日志
	private static Logger member =  Logger.getLogger("member");
	//权限日志
	private static Logger permission = Logger.getLogger("permission");
	//机票日志
	private static Logger ticket =  Logger.getLogger("ticket");
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
	public static Logger getTicket() {
		return ticket;
	}
	
}
