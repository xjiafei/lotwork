package com.winterframework.firefrog.common.util;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

public class StringToMap {

	public static Map<String,String> toMap(String parameters){
		Map<String,String> map=new HashMap<String,String>();
		if(StringUtils.isEmpty(parameters)){
			//if empty,return null;
		}else{
		  String[] kvs=parameters.split("&",-1);
		  for(String kv:kvs){
			  String[] str=kv.split("=");
			  map.put(str[0],str[1]);
		  }
		}
		return map;
	}

}
