package com.outwit.das.common;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;

public class PayUtil {
	private static String REX = "^[0-9]+(.[0-9]{0,2})?$";

	/**
	 * 将Map对象转为TreeMap（key值有序）
	 * 
	 * @param map
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static TreeMap<String, Object> toTreeMap(Map<String, Object> map) {
		TreeMap tmap = new TreeMap();
		for (Object o : map.keySet().toArray()) {
			tmap.put(o, map.get(o));
		}
		return tmap;
	}

	/**
	 * 获得排序好的待签名字符串
	 * 
	 * @param notifyMap
	 * @return
	 */
	public static String getSignStr(Map<String, Object> notifyMap) {
		StringBuffer sb = new StringBuffer();
		for (Object o : notifyMap.keySet().toArray()) {
			Object value = notifyMap.get(o);
			if (!"sign".equals(o) && value != null
					&& StringUtils.isNotBlank(value.toString())) {
				sb.append(o);
				sb.append("=");
				sb.append(notifyMap.get(o));
				sb.append("&");
			}
		}
		return sb.substring(0, sb.length() - 1);
	}

	/**
	 * 将javabean转成Map,仅取特定的字段，且可以修改字段的命名
	 * 
	 * @param obj
	 *            javabean
	 * @param appointedMap
	 *            特定字段,value不为空时替代原名
	 * @return
	 */
	public static Map<String, Object> beanToMap(Object obj,
			Map<String, String> appointedMap) {
		Map<String, Object> map = null;
		PropertyDescriptor[] props = null;
		try {
			props = Introspector.getBeanInfo(obj.getClass(), Object.class)
					.getPropertyDescriptors();
		} catch (IntrospectionException e) {
		}
		if (props != null) {
			map = new HashMap<String, Object>();
			for (int i = 0; i < props.length; i++) {
				try {
					String name = props[i].getName();
					Object value = props[i].getReadMethod().invoke(obj);
					if (appointedMap != null
							&& appointedMap.keySet().size() > 0) {
						Set<String> key = appointedMap.keySet();
						if (!key.contains(name)) {
							continue;
						}
						if (StringUtils.isNotBlank(appointedMap.get(name))) {
							name = appointedMap.get(name);
						}
					}
					if (value == null || value.equals("null")) {
						value = "";
					}
					map.put(name, value);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return map;
	}

	/**
	 * Map转化成bean
	 * 
	 * @param type
	 * @param map
	 * @return
	 * @throws IntrospectionException
	 * @throws IllegalAccessException
	 * @throws InstantiationException
	 * @throws InvocationTargetException
	 */
	public static Object mapToBean(Class<?> type, Map<?, ?> map)
			throws IntrospectionException, IllegalAccessException,
			InstantiationException, InvocationTargetException {
		BeanInfo beanInfo = Introspector.getBeanInfo(type); // 获取类属性
		Object obj = type.newInstance(); // 创建 JavaBean 对象

		// 给 JavaBean 对象的属性赋值
		PropertyDescriptor[] propertyDescriptors = beanInfo
				.getPropertyDescriptors();
		for (int i = 0; i < propertyDescriptors.length; i++) {
			PropertyDescriptor descriptor = propertyDescriptors[i];
			String propertyName = descriptor.getName();
			if (map.containsKey(propertyName)) {
				// 下面一句可以 try 起来，这样当一个属性赋值失败的时候就不会影响其他属性赋值。
				Object value = map.get(propertyName);
				Object[] args = new Object[1];
				args[0] = value;
				descriptor.getWriteMethod().invoke(obj, args);
			}
		}
		return obj;
	}

	/**
	 * 获取URL 指定编码的参数
	 * 
	 * @param tMap
	 * @param charset
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public static String getURLParamStr(Map<String, Object> notifyMap) {
		StringBuffer sb = new StringBuffer();
		for (Entry<String, Object> obj : notifyMap.entrySet()) {
			sb.append(obj.getKey());
			sb.append("=");
			sb.append(obj.getValue());
			sb.append("&");
		}
		return sb.substring(0, sb.length() - 1);
	}

	/**
	 * 验证是否大于0小于100000000的且最多2位小数的数字
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isValidNum(String str) {
		Pattern pattern = Pattern.compile(REX);
		Matcher match = pattern.matcher(str);
		if (match.matches()) {
			float f = Float.parseFloat(str);
			return f > 0.00 && f <= 100000000.00;
		}
		return false;

	}

	/**
	 * 验证分润信息
	 * 
	 * @param profit
	 * @return 1:格式错误 2:分润会员不存在
	 * @throws SQLException
	 * @throws Exception
	 */
	public static boolean validProfit(String profit) {
		if (StringUtils.isNotBlank(profit)) {
			String[] pros = profit.split("\\|");
			for (String string : pros) {
				if (!string.contains("^")) {
					return false;
				} else {
					String isNum = string.split("\\^")[1];
					if (!isValidNum(isNum)) {
						return false;
					}
				}
			}
		}
		return true;
	}
}
