package com.winterframework.firefrog.fund.web.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class RedisKey {
	private static DateFormat df=new SimpleDateFormat("yyyyMMdd");
	public static String createDateKey(String module,String key,Long userId){
		return module + "_" + key +"_"+df.format(new Date())+ "_" + userId;
	}

}
