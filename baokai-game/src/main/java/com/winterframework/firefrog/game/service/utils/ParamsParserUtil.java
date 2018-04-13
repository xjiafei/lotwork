package com.winterframework.firefrog.game.service.utils;

import java.util.HashMap;
import java.util.Map;

/**
 *ParamsParserUtil
 * @ClassName
 * @Description 自定义参数解析工具类
 * @author ibm
 * 2014年8月22日
 */
public class ParamsParserUtil { 
	
	public static Map parse(String params){
		return parse(params,";");
	}
	/**
	 * lotteryId:99102;issue_code:20140613303030
	 * @param params
	 * @return
	 */
	public static Map parse(String params,String seperator){ 
		if(params==null) return null;
		String spt=";";
		Map<String,Object> map=new HashMap<String,Object>();
		if(seperator!=null && seperator.length()>0){
			spt=seperator;
		}
		String[] paramsArr=params.split(spt); 
		for(String s:paramsArr){
			if(s.length()==0) continue;
			String[] ss=s.split(":"); 
			if(ss.length>1){
				map.put(ss[0], ss[1]);
			}else{
				map.put(ss[0], null);
			} 
		}
		return map;
	}
	

}
