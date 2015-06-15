package com.outwit.das.hessian;

import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.outwit.das.utils.ClassHelper;

public class MM {
	public static void main(String[] args) {
		List<Class<?>> hessianInterfaceList = ClassHelper.getClassListByAnnotation(Hessian.class);
		Iterator<Class<?>> xx = hessianInterfaceList.iterator();
		System.out.println(hessianInterfaceList.size());
		while(xx.hasNext()){
			Class<?> clazz = xx.next();
			System.out.println(clazz);
			String backpage = clazz.getPackage().getName();
			backpage = backpage.replace("com.outwit.das", "").replace(".service", "");
			System.out.println(backpage = backpage.replace('.', '/'));
			
		};
		
		
		String str = "com.outwit.das.permission.service";
		
		
	
	}
}
