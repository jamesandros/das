package com.outwit.das.common;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.outwit.das.utils.ObjectUtil;
import com.outwit.das.utils.SecurityEncode;


public class AlipayUtil {
	   /**
     * 支付宝消息验证地址
     */
	
	
    private static final String HTTPS_VERIFY_URL = "https://mapi.alipay.com/gateway.do?service=notify_verify&";
	/** 
     * 除去数组中的空值和签名参数
     * @param sArray 签名参数组
     * @return 去掉空值与签名参数后的新签名参数组
     */
    public static Map<String, String> paraFilter(Map<String, String> sArray) {

        Map<String, String> result = new HashMap<String, String>();

        if (sArray == null || sArray.size() <= 0) {
            return result;
        }

        for (String key : sArray.keySet()) {
            String value = sArray.get(key);
            if (value == null || value.equals("") || key.equalsIgnoreCase("sign")
                || key.equalsIgnoreCase("sign_type")) {
                continue;
            }
            result.put(key, value);
        }

        return result;
    }
	
	
    /** 
     * 把数组所有元素排序，并按照“参数=参数值”的模式用“&”字符拼接成字符串
     * @param params 需要排序并参与字符拼接的参数组
     * @return 拼接后字符串
     */
    public static String createLinkString(Map<String, String> params) {

        List<String> keys = new ArrayList<String>(params.keySet());
        Collections.sort(keys);

        String prestr = "";

        for (int i = 0; i < keys.size(); i++) {
            String key = keys.get(i);
            String value = params.get(key);

            if (i == keys.size() - 1) {//拼接时，不包括最后一个&字符
                prestr = prestr + key + "=" + value;
            } else {
                prestr = prestr + key + "=" + value + "&";
            }
        }

        return prestr;
    }
    
	/**
     * 生成签名结果
     * @param sPara 要签名的数组
     * @return 签名结果字符串
     */
	public static String buildRequestMysign(Map<String, String> sPara) {
    	String prestr = AlipayUtil.createLinkString(sPara); //把数组所有元素，按照“参数=参数值”的模式用“&”字符拼接成字符串
    	String mysign  = SecurityEncode.sign(prestr, PropertiesConfig.getPayconfig().getProperty("key"), PropertiesConfig.getPayconfig().getProperty("input_charset"));
        return mysign;
    }
	
    /**
     * 生成要请求给支付宝的参数数组
     * @param sParaTemp 请求前的参数数组
     * @return 要请求的参数数组
     */
    public static Map<String, String> buildRequestPara(Map<String, String> sParaTemp) {
        //除去数组中的空值和签名参数
        Map<String, String> sPara = AlipayUtil.paraFilter(sParaTemp);
        //生成签名结果
        String mysign = buildRequestMysign(sPara);
        //签名结果与签名方式加入请求提交参数组中
        sPara.put("sign", mysign);
        sPara.put("sign_type", PropertiesConfig.getPayconfig().getProperty("sign_type"));
        return sPara;
    }
	
    public static String createUrl(Map<String,String>params,String key) {
		String input_charset=params.get("_input_charset");
		String sign_type=PropertiesConfig.getPayconfig().getProperty("sign_type");
		StringBuilder parameter = new StringBuilder(params.remove("paygateway"));
		String sign = SecurityEncode.sign(AlipayUtil.getContent_public(params,key),input_charset);
		for (Iterator<String> iterator = params.keySet().iterator(); iterator.hasNext();) {
			String paraname = iterator.next();
			String paraval = params.get(paraname);
			parameter.append(paraname+"=");
			try {
				parameter.append(URLEncoder.encode(paraval,input_charset));
			} catch (UnsupportedEncodingException e) {
				Log.getCommon().error("创建支付路径出错",e);
			}
			parameter.append("&");
		}
		parameter.append("sign="+sign+"&sign_type="+sign_type);
		return parameter.toString();
	}
    
    public static String createUrl(String url,String input_charset){
    	StringBuilder parameter = new StringBuilder();
    	if(ObjectUtil.objIsNotNull(url)){
    		String params[] =  url.split("&");
        	for(String str : params){
        		String key_value  [] = str.split("=");
        		try {
					parameter.append(key_value[0]).append("=").append(URLEncoder.encode(key_value[1],input_charset)).append("&");
				} catch (UnsupportedEncodingException e) {
					Log.getCommon().error("创建支付路径出错",e);
				}
        	}
        	parameter.delete(parameter.length()-1, parameter.length());
    	}
    	return parameter.toString();
    }
    
    
    public static String getContent_public(Map<String, String> params, String privateKey){
        List<String> keys = new ArrayList<String>(params.keySet());
        Collections.sort(keys);
        String prestr = "";
        for(int i = 0; i < keys.size(); i++){
            String key = (String)keys.get(i);
            String value = (String)params.get(key);
            if(i == keys.size() - 1){
                prestr = (new StringBuilder(String.valueOf(prestr))).append(key).append("=").append(value).toString();
            } else{
                prestr = (new StringBuilder(String.valueOf(prestr))).append(key).append("=").append(value).append("&").toString();
            }
        }
        return (new StringBuilder(String.valueOf(prestr))).append(privateKey).toString();
    }
    
 

    /**
     * 验证消息是否是支付宝发出的合法消息
     * @return 验证结果
     */
    public static boolean verify(Map<String, String> params) {
        //判断responsetTxt是否为true，isSign是否为true, responsetTxt的结果不是true，与服务器设置问题、合作身份者ID、notify_id一分钟失效有关 ,isSign不是true，与安全校验码、请求时的参数格式（如：带自定义参数等）、编码格式有关
    	String responseTxt = "true";
		if(params.get("notify_id") != null) {
			String notify_id = params.get("notify_id");
			responseTxt = verifyResponse(notify_id);
		}
	    String sign = "";
	    if(params.get("sign") != null) {
	    	sign = params.get("sign");
	    }
	    boolean isSign = getSignVeryfy(params, sign);
        //写日志记录（若要调试，请取消下面两行注释）
	    String sWord = "responseTxt=" + responseTxt + "\n isSign=" + isSign + "\n 返回回来的参数：" + AlipayUtil.createLinkString(params);
	    Log.getCommon().debug(sWord);
        if (isSign && responseTxt.equals("true")) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 根据反馈回来的信息，生成签名结果
     * @param Params 通知返回来的参数数组
     * @param sign 比对的签名结果
     * @return 生成的签名结果
     */
	private static boolean getSignVeryfy(Map<String, String> Params, String sign) {
    	//过滤空值、sign与sign_type参数
    	Map<String, String> sParaNew = AlipayUtil.paraFilter(Params);
        //获取待签名字符串
        String preSignStr = AlipayUtil.createLinkString(sParaNew);
        //获得签名验证结果
        return SecurityEncode.verify(preSignStr, sign,PropertiesConfig.getPayconfig().getProperty("key"), PropertiesConfig.getPayconfig().getProperty("input_charset"));
       
    }

    /**
    * 获取远程服务器ATN结果,验证返回URL
    * @param notify_id 通知校验ID
    * @return 服务器ATN结果
    * 验证结果集：
    * invalid命令参数不对 出现这个错误，请检测返回处理中partner和key是否为空 
    * true 返回正确信息
    * false 请检查防火墙或者是服务器阻止端口问题以及验证时间是否超过一分钟
    */
    private static String verifyResponse(String notify_id) {
        //获取远程服务器ATN结果，验证是否是支付宝服务器发来的请求
        String partner = PropertiesConfig.getPayconfig().getProperty("partner");
        String veryfy_url = HTTPS_VERIFY_URL + "partner=" + partner + "&notify_id=" + notify_id;
        return checkUrl(veryfy_url);
    }

    /**
    * 获取远程服务器ATN结果
    * @param urlvalue 指定URL路径地址
    * @return 服务器ATN结果
    * 验证结果集：
    * invalid命令参数不对 出现这个错误，请检测返回处理中partner和key是否为空 
    * true 返回正确信息
    * false 请检查防火墙或者是服务器阻止端口问题以及验证时间是否超过一分钟
    */
    private static String checkUrl(String urlvalue) {
        String inputLine = "";
        try {
            URL url = new URL(urlvalue);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            BufferedReader in = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
            inputLine = in.readLine().toString();
        } catch (Exception e) {
           Log.getCommon().equals(e);
        }
        return inputLine;
    }
}
