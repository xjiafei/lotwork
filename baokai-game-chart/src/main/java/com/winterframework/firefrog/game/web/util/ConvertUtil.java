package com.winterframework.firefrog.game.web.util;


public class ConvertUtil {
	
	public static final String[] WEEK = {"周一","周二","周三","周四","周五","周六","周日"} ;
	
	public static final String COMMA = ",";

	public static String convertStr2Week(String str) throws Exception{
		
		StringBuffer buffer = new StringBuffer();
		if(str.indexOf(COMMA) > 0){
			
			String[] _weekStr = str.split(",");
			
			for(String s: _weekStr){
				buffer.append(WEEK[(Integer.parseInt(s) - 1)]);
				buffer.append(COMMA);
			}
			
			return buffer.substring(0, buffer.length() - 1) ; //去掉逗号
		}
		
		return "";
	}
	
	public static void main(String[] args) {
		String s1="";
		String s2="1";
		String[] str1=s1.split(",");
		String[] str2=s2.split(",");
		System.out.println(str1.length);
		System.out.println(str2.length);
		
	}
	
}
