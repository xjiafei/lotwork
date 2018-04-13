package com.winterframework.firefrog.game.service.ec.utils;

import java.util.Map;

public class ECNoticeUpdate {
	
	
	public static String echoECData(Map<String, String> request) { 
		String str = new String();
		StringBuffer sb = new StringBuffer(str);
		sb.append("customer=" + request.get("customer"))		
		.append("&recordId=" + request.get("recordId"))
		.append("&lottery=" + request.get("lottery"))		
		.append("&issue=" + request.get("issue"))		
		.append("&time=" + request.get("time"))	
		.append("&number=" + request.get("number"))
		.append("&correctTime=" + request.get("correctTime"));		
		return sb.toString();
	}
	
	
	
}
