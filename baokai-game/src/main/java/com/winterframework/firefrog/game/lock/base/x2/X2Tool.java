package com.winterframework.firefrog.game.lock.base.x2;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.winterframework.firefrog.game.lock.base.LockTools;

public class X2Tool {
	/**
	 * 
	 * @param qOrh 前还是后
	 * @param dorz 但是还是组选
	 * @param abs
	 * @return
	 */
	public static Map<String,Integer> influence(boolean qOrh,boolean dorz,String...abs){
		Map<String,Integer>  lists=new HashMap<String,Integer>();
		int len=abs.length;
		for(int i=0;i<=9;i++){
			for(int j=0;j<len;j++){
				if(qOrh){
					LockTools.addPkg(lists,abs[j]+i);
					if(!dorz){
						LockTools.addPkg(lists,StringUtils.reverse(abs[j])+i);
					}
				}else{
					LockTools.addPkg(lists,i+abs[j]);
					if(!dorz){
						LockTools.addPkg(lists,i+StringUtils.reverse(abs[j]));
					}
				}
			}
		}
		return lists;
	}
	public static Map<String,Integer> influence(boolean qOrh,String...abs){
		return influence(qOrh,true,abs);
	}
	
}
