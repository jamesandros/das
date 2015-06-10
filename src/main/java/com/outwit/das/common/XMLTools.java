package com.outwit.das.common;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
/**
 * 
 * @author coderyu
 * 日期  2014-12-3
 */
public class XMLTools {

	public static Document parseXMLByString(String xml){
		try {
			if(ObjectIsNullUtil.objIsNotNull(xml)){
				org.dom4j.Document document4j =  DocumentHelper.parseText(xml);
				return document4j;
			}
		} catch (Exception e) {
			Log.getCommon().error(e.getMessage(), e);
		}
		return null;
	}
	

}
