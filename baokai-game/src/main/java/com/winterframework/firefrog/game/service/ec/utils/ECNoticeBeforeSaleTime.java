package com.winterframework.firefrog.game.service.ec.utils;

import java.util.Map;

public class ECNoticeBeforeSaleTime {
	
	public static String echoECData(Map<String, String> request) { 
		String str = new String();
		StringBuffer sb = new StringBuffer(str);
		sb.append("customer=" + request.get("customer"))		
		.append("&recordId=" + request.get("recordId"))
		.append("&lottery=" + request.get("lottery"))		
		.append("&issue=" + request.get("issue"))		
		.append("&time=" + request.get("time"))	
		.append("&logId=" + request.get("logId"))	
		.append("&errCode=" + request.get("errCode"))
		.append("&errMessage=" + request.get("errMessage"));		
		return sb.toString();
	}
	
}
