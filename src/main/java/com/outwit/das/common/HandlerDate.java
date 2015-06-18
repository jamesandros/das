package com.outwit.das.common;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;
import java.util.Random;

import org.apache.commons.lang3.RandomStringUtils;

import com.outwit.das.utils.ObjectUtil;

public class HandlerDate {

	public static String yyyyMMddHHmmss = "yyyyMMddHHmmss";

	public static String yyyyMMdd = "yyyy/MM/dd";

	public static String yyyy_MM_dd = "yyyy-MM-dd";

	public static String yyyy_MM_ddHHmmss = "yyyy-MM-dd HH:mm:ss";
	
	public static String yyyy_MM_ddHHmm = "yyyy-MM-dd HH:mm";

	public static String yyyyMM = "yyyy年MM月";

	public static String HHmmss = "HH:mm:ss";

	public static String HHmm = "HH:mm";

	private static long start = 0;

	/**
	 * 取得数据库主键 生成格式为yyyymmddhhmmss+k位随机数
	 * 
	 * 表示是取几位随机数，可以自己定
	 */

	private static List<String> randList = new ArrayList<String>();

	public static String getNowTime(String formatStyle) {

		try {
			SimpleDateFormat format = new SimpleDateFormat(formatStyle);
			return format.format(Calendar.getInstance().getTime());
		} catch (Exception e) {
			return null;
		}
	}

	public static Date stringConvertDate(String timeContent, String formatStyle) {
		SimpleDateFormat format = new SimpleDateFormat(formatStyle);
		try {
			return format.parse(timeContent);
		} catch (ParseException e) {
			return null;
		}
	}

	public static String dateConvertString(Date date, String formatStyle) {
		try {
			SimpleDateFormat format = new SimpleDateFormat(formatStyle);
			return format.format(date);
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * 其他国家字符串日期格式转换成其他国家日期对象
	 * 
	 * @param strDate
	 *            原轨迹日期
	 * @return 标准日期格式
	 */
	public static Date StringConvertLocalDate(String strDate, Locale locale,
			String formatStyle) {
		SimpleDateFormat srcsdf = new SimpleDateFormat(formatStyle, locale);
		try {
			return srcsdf.parse(strDate);
		} catch (ParseException e) {
			return null;
		}
	}

	/**
	 * 时间前推或后推秒钟,其中JJ表示秒钟.
	 */
	public static String getPreTimesec(String sj1, int jj) {
		SimpleDateFormat format = new SimpleDateFormat(yyyy_MM_ddHHmmss);
		String mydate1 = "";
		try {
			Date date1 = format.parse(sj1);
			long Time = (date1.getTime() / 1000) + jj;
			date1.setTime(Time * 1000);
			mydate1 = format.format(date1);
		} catch (Exception e) {
		}
		return mydate1;
	}

	/**
	 * 将长时间格式字符串转换为时间 yyyy-MM-dd HH:mm:ss
	 * 
	 * @param strDate
	 * @return
	 */
	public static Date strToDateLong(String strDate) {
		SimpleDateFormat formatter = new SimpleDateFormat(yyyy_MM_ddHHmmss);
		ParsePosition pos = new ParsePosition(0);
		Date strtodate = formatter.parse(strDate, pos);
		return strtodate;
	}

	/**
	 * 生成订单号码
	 * 
	 * @return
	 */
	public static synchronized String getOrderNo() {
		if (start > 10000) {
			start = 0;
		}
		Random random = new Random();
		String orderNo = HandlerDate.getNowTime(HandlerDate.yyyyMMddHHmmss)
				+ start++ + random.nextInt(1);
		return orderNo;
	}

	/**
	 * 获取指定前缀的订单号码
	 * 
	 * @param prefix
	 * @return
	 */
	public static synchronized String getOrderNo(String prefix) {
		if (start > 10000) {
			start = 0;
		}
		Random random = new Random();
		String nowTime = HandlerDate.getNowTime(HandlerDate.yyyyMMddHHmmss)
				+ start++ + random.nextInt(9);
		return prefix + nowTime;
	}

	/**
	 * 格式化航班日期1033-->10:33
	 * 
	 * @param time
	 * @return
	 */
	public static String timeFormat(String time) {
		if (ObjectUtil.objIsNotNull(time)) {
			return time.substring(0, 2) + ":" + time.substring(2, 4);
		}
		return time;
	}

	/**
	 * 获取指定日期加一个月的日期
	 * 
	 * @param time
	 * @param month
	 * @return
	 */
	public static String getNextMonth(String time, int month) {
		Calendar dayc = new GregorianCalendar();
		;
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		;
		try {
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			Date daystart = df.parse(time);
			dayc.setTime(daystart);
			dayc.add(Calendar.MONTH, month);
		} catch (Exception e) {
		}
		return formatter.format(dayc.getTime());
	}

	/**
	 * 随机生成26个字母中的其中一个
	 * 
	 * @return
	 */
	public static String getOneLetter() {
		String str = "";
		for (int i = 0; i < 1; i++) {// 你想生成几个字符的，就把3改成几，如果改成１,那就生成一个随机字母．
			str = str + (char) (Math.random() * 26 + 'A');
		}
		return str;
	}

	public static void main(String[] args) {
		System.out.println(getOrderNo("PLE"));
	}
	/**
	 * 保证一次产生10000个随机数内无重复 平均一秒钟能产生250个随机数, 一次产生多订单慎用，会有内存溢出肯能
	 */
	public static String getNo(int k) {
		if (randList.size() > 10000) {
			randList.clear();
		}
		String rno = getNoNo(k);
		while (randList.contains(rno)) {

			rno = getNoNo(k);

		}
		randList.add(rno);
		return rno;
	}

	private static String getNoNo(int k) {
		try {
			Thread.sleep(1);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return getUserDate("yyyyMMddHHmmss")
				+ RandomStringUtils.randomNumeric(k);
	}

	/**
	 * 根据用户传入的时间表示格式，返回当前时间的格式 如yyyyMMdd，注意字母y不能大写。
	 * 
	 * @param sformat
	 *            yyyyMMddHHmmss
	 * @return
	 */
	public static String getUserDate(String sformat) {
		Date currentTime = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat(sformat);
		String dateString = formatter.format(currentTime);
		return dateString;
	}

	/**
	 * 取用户证件号码后6位作为会员登录的密码和支付密码
	 * 
	 * @param idcardString
	 * @return
	 */
	public static String strAutoComplement(String idcardString, int length,
			String complement) {
		if (idcardString.length() < length) {
			StringBuilder sbBuilder = new StringBuilder(idcardString);
			for (int i = idcardString.length(); i < length; i++) {
				sbBuilder.append(complement);
			}
			idcardString = sbBuilder.toString();
		}
		String srcLoginPwdString = idcardString.substring(idcardString.length()
				- length, idcardString.length());
		return srcLoginPwdString;
	}

	public static boolean objIsNotNull(Object obj) {
		if (obj != null && !obj.toString().isEmpty()) {
			return true;
		}
		return false;
	}

	/**
	 * 
	 * @param args
	 */
	public static Double getTwobitDecimal(Double doub) {
		DecimalFormat df = new DecimalFormat(".##");
		String str = df.format(doub);
		return Double.valueOf(str);
	}

	

//	public static void main(String args[]) {
//		int i = compareDate("2000-11-12 15:21:11", "1999-12-11 09:59");
//		System.out.println("i==" + i);
//	}

	public static int compareDate(String DATE1, String DATE2) {

		DateFormat df = new SimpleDateFormat(HandlerDate.yyyy_MM_ddHHmm);
		try {
			Date dt1 = df.parse(DATE1);
			Date dt2 = df.parse(DATE2);
			if (dt1.getTime() > dt2.getTime()) {
				System.out.println("dt1 在dt2后");
				return 1;
			} else if (dt1.getTime() < dt2.getTime()) {
				System.out.println("dt1在dt2前");
				return -1;
			} else {
				return 0;
			}
		} catch (Exception exception) {
			exception.printStackTrace();
		}
		return 0;
	}
}
