package com.outwit.das.common;
import java.util.List;
import java.util.Map;
/**
 * 判断对象、集合、数组是否为空，true表示不为空 false表示空
 * @author yangsheng
 * @Date   2014-5-22
 *
 */
public class ObjectIsNullUtil {

	/**
	 * 数组是否为空，true表示不为空 false表示空
	 * @param array
	 * @return 不为空返回true 空返回false
	 */
	public static <T> boolean arrayIsNotNull(T[] array){
		
		if(array!=null && array.length>0){
			return true;
		}
		return false;
	}

	
	/**
	 * 判读一个list集合是否为空
	 * @param list
	 * @return 不为空返回true 空返回false
	 */
    public static <T> boolean listIsNotNull(List<T> list){
    	if(list!=null && !list.isEmpty()){
    		return true;
    	}
    	return false;
    }
    /**
     * 判读一个map集合是否为空
     * @param map
     * @return 为空返回false 不为空返回true
     */
    public static <V,K> boolean mapIsNotNull(Map<V,K> map){
    	
    	if(map!=null && !map.isEmpty()){
    		return true;
    	}
    	return false;
    }
    
    public static boolean objIsNotNull(Object obj){
    	if(obj!=null && !obj.toString().isEmpty() && !"null".equals(obj.toString())){
    		return true;
    	}
    	return false;
    }
}
