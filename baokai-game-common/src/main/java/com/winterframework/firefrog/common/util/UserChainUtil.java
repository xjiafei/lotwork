package com.winterframework.firefrog.common.util;

import org.apache.commons.lang3.StringUtils;

import com.winterframework.modules.security.MD5Encrypt;

/** 
* @ClassName: KeyUtil 
* @Description:  
* @author 你的名字 
* @date 2013-7-3 下午6:11:04 
*  
*/
public class UserChainUtil {

	private static final String SEPERATOR="/";
	/**
	 * 获取上级用户
	 * @param account
	 * @param userChain
	 * @return
	 */
	public static String getSuper(String account,String userChain) {
		if(userChain==null || account==null) return null;
		String chain=userChain;
		chain=userChain.replace(SEPERATOR+account+SEPERATOR,"");
		String superAccount=chain.substring(chain.lastIndexOf(SEPERATOR)+1); 
		return StringUtils.isEmpty(superAccount)?null:superAccount;
	}
	
	public static void main(String[] args){
		System.out.print(getSuper("ydagent011","/ydagent011/"));
	}
}
