package com.winterframework.firefrog.fund.service.impl.worth;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import com.winterframework.modules.security.MD5Encrypt;

public class UtilSign{
	
	public static String GetMd5str(Map<String,String> Parm){		
		if(Parm.containsKey("sign")){
			Parm.remove("sign");//不包含sign
		}
		List<String> SortStr = Ksort(Parm); //排序
		String Md5Str = CreateLinkstring(Parm,SortStr);
		return Md5Str;
	}
	
	/**
	 * 排序  (升序)
	 * @param Parm
	 * @return
	 */
	public static List<String> Ksort(Map<String,String> Parm){ 
		List<String> SMapKeyList = new ArrayList<String>(Parm.keySet()); 
		Collections.sort(SMapKeyList);
		return SMapKeyList;
	} 
	
	/**
	 * 判断值是否为空 FALSE 为不空  TRUE 为空
	 * @param Temp
	 * @return
	 */
	public static boolean StrEmpty(String Temp){
		if(null == Temp || Temp.isEmpty()){
			return true;
		}
		return false;
	}
	
	/**
	 * 拼接报文
	 * @param Parm
	 * @param SortStr
	 * @return
	 */
	public static String CreateLinkstring(Map<String,String> Parm,List<String> SortStr){
		String LinkStr = "";
		for(int i=0;i<SortStr.size();i++){
			if(!StrEmpty(Parm.get(SortStr.get(i).toString()))){
				LinkStr += SortStr.get(i) +"="+Parm.get(SortStr.get(i).toString());
				if((i+1)<SortStr.size()){
					LinkStr +="&";
				}
			}
		}
		return LinkStr;
	}
}