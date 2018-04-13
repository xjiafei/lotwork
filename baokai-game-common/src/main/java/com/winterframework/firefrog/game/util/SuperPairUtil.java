package com.winterframework.firefrog.game.util;

import java.util.HashMap;
import java.util.Map;



/**
 * 超级对子工具类
 * @ClassName
 * @Description
 * @author ibm
 * 2016年1月18日
 */
public class SuperPairUtil { 
	private static final Map<Integer,Integer> matchMap=new HashMap<Integer,Integer>();
	private static final String prefix="KK";
	public static final String alias="超级2000";
	static{
		matchMap.put(44, 10);
		matchMap.put(45, 11);
		matchMap.put(46, 12);
		matchMap.put(47, 13);
		matchMap.put(48, 14);
		matchMap.put(49, 15);
		matchMap.put(50, 16);
		matchMap.put(51, 11); //秒秒彩超级2000_四星
		matchMap.put(52, 33); //秒秒彩超级2000_中三
	}
	public static boolean isSuperPair(Integer groupCode) {
		return (groupCode>=44 && groupCode<=52);
	}
	public static boolean isSuperPair(String betTypeCode) {
		Integer groupCode=Integer.valueOf(betTypeCode.substring(0, 2));
		return isSuperPair(groupCode);
	}
	public static boolean isSuperPairByGroupCodeName(String groupCodeName) {
		return groupCodeName.contains("_2000");
	}
	
	public static Integer getCommGroupCode(Integer groupCode){
		return matchMap.get(groupCode);
	}
	public static String getCommBetTypeCode(String betTypeCode){
		Integer groupCode=Integer.valueOf(betTypeCode.substring(0, 2));
		Integer commGroupCode=matchMap.get(groupCode);
		return (prefix+betTypeCode).replace(prefix+groupCode, commGroupCode+"");
	}
	
	public static void main(String[] args){
		System.out.println(getCommBetTypeCode("45_12_45"));
	}
}
