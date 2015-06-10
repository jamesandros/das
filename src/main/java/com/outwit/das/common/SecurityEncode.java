package com.outwit.das.common;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;
import java.util.Random;
import java.util.TreeMap;

import org.apache.commons.codec.digest.DigestUtils;


/**
 * md5加密
 * 
 */
public class SecurityEncode {

	private final static String[] hexDigits = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d",
			"e", "f" };

	/**
	 * 转换字节数组为16进制字串
	 * 
	 * @param b
	 *            字节数组
	 * @return 16进制字串
	 */

	public static String byteArrayToHexString(byte[] b) {
		StringBuffer resultSb = new StringBuffer();
		for (int i = 0; i < b.length; i++) {
			resultSb.append(byteToHexString(b[i]));
		}
		return resultSb.toString();
	}

	private static String byteToHexString(byte b) {
		int n = b;
		if (n < 0)
			n = 256 + n;
		int d1 = n / 16;
		int d2 = n % 16;
		return hexDigits[d1] + hexDigits[d2];
	}

	public static String MD5Encode(String origin,String input_charset) {
		String resultString = null;

		try {
			resultString = new String(origin.getBytes());
			MessageDigest md = MessageDigest.getInstance("MD5");
			resultString = byteArrayToHexString(md.digest(resultString.getBytes(input_charset)));
		} catch (Exception ex) {

		}
		return resultString;
	}
	
	public static String MD5Encode(String origin) {
		String resultString = null;

		try {
			resultString = new String(origin.getBytes());
			MessageDigest md = MessageDigest.getInstance("MD5");
			resultString = byteArrayToHexString(md.digest(resultString.getBytes()));
		} catch (Exception ex) {

		}
		return resultString;
	}
	
	public static String randomABCNum(int length){
		StringBuilder  randomBuilder = new StringBuilder();
		Random random =new Random();
		for(int i=0;i<length;i++){
			randomBuilder.append(hexDigits[random.nextInt(16)].toUpperCase());
		}
		return randomBuilder.toString();
	}
	/**
	 * 严格加密
	 * @param strSrc
	 * @param encName
	 * @return
	 */
	public static String SHA256Encode(String strSrc, String encName) {
        MessageDigest md = null;
        String strDes = null;

        byte[] bt = strSrc.getBytes();
        try {
            if (encName == null || encName.equals("")) {
                encName = "SHA-256";
            }
            md = MessageDigest.getInstance(encName);
            md.update(bt);
            strDes = bytes2Hex(md.digest()); // to HexString
        } catch (NoSuchAlgorithmException e) {
            return null;
        }
        return strDes;
    }

    public static String bytes2Hex(byte[] bts) {
        String des = "";
        String tmp = null;
        for (int i = 0; i < bts.length; i++) {
            tmp = (Integer.toHexString(bts[i] & 0xFF));
            if (tmp.length() == 1) {
                des += "0";
            }
            des += tmp;
        }
        return des;
    }
    
    
    /**
     * 签名字符串
     * @param text 需要签名的字符串
     * @param key 密钥
     * @param input_charset 编码格式
     * @return 签名结果
     */
    public static String sign(String text, String key, String input_charset) {
    	text = text + key;
        return DigestUtils.md5Hex(getContentBytes(text, input_charset));
    }
    
    public static String sign(String text, String input_charset) {
        return DigestUtils.md5Hex(getContentBytes(text, input_charset));
    }
    /**
     * 签名字符串
     * @param text 需要签名的字符串
     * @param sign 签名结果
     * @param key 密钥
     * @param input_charset 编码格式
     * @return 签名结果
     */
    public static boolean verify(String text, String sign, String key, String input_charset) {
    	text = text + key;
    	String mysign = DigestUtils.md5Hex(getContentBytes(text, input_charset));
    	if(mysign.equals(sign)) {
    		return true;
    	}
    	else {
    		return false;
    	}
    }

    /**
     * @param content
     * @param charset
     * @return
     * @throws SignatureException
     * @throws UnsupportedEncodingException 
     */
    private static byte[] getContentBytes(String content, String charset) {
        if (charset == null || "".equals(charset)) {
            return content.getBytes();
        }
        try {
            return content.getBytes(charset);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("MD5签名过程中出现错误,指定的编码集不对,您目前指定的编码集是:" + charset);
        }
    }
    /**
     * 混乱加密。
     * @param values
     * @return
     */
    public static String confusionSaltEncode(String values){
    	String str = SecurityEncode.MD5Encode(values);
		char[] toChar = str.toCharArray();
		String salt = String.valueOf(new char[]{toChar[0],toChar[8],toChar[6],toChar[7],toChar[3],toChar[18],toChar[15],toChar[5],toChar[1],toChar[5],toChar[3]});
		StringBuilder sb = new StringBuilder(salt);
		sb.append(values);
		sb.append(str);
		sb.reverse();
		String newStr = SecurityEncode.SHA256Encode(sb.toString(), null);
		return newStr.substring(values.length(), values.length()+30);
    }
}
