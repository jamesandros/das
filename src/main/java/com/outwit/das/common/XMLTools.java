package com.outwit.das.common;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;

import com.alibaba.druid.filter.config.ConfigTools;
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
	public static void main(String[] args) {
		String s;
		try {
			s = ConfigTools.decrypt("n2hzgWhO95sr/65UrJcqJok6PFg69l9k9btCkhx75fhCI2vCt5jRnfGD7915hxOScqsushXt9d6WrD76fz/R7w==");
			System.out.println(s);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
