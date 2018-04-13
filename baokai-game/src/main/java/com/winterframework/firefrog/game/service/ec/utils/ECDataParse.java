package com.winterframework.firefrog.game.service.ec.utils;

import java.util.Map;

public class ECDataParse {
	
	public static String echoECData(Map<String, String> request,String strUU) { 
		String str = new String();
		StringBuffer sb = new StringBuffer(str);
		sb.append("customer=" + request.get("customer"))		
		.append("&recordId=" + request.get("recordId"))
		.append("&lottery=" + request.get("lottery"))		
		.append("&issue=" + request.get("issue"))		
		.append("&time=" + request.get("time"))	
		.append("&logId=" + strUU)
		.append("&number=" + request.get("number"))
		.append("&verifiedTime=" + request.get("verifiedTime"))
		.append("&earliestTime=" + request.get("earliestTime"))
		.append("&stopSaleTime=" + request.get("stopSaleTime"))
		.append("&drawingTime=" + request.get("drawingTime"));
		
		return sb.toString();
	}
	
	
	
}
