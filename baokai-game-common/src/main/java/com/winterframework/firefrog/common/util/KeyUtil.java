package com.winterframework.firefrog.common.util;

import com.winterframework.modules.security.MD5Encrypt;

/** 
* @ClassName: KeyUtil 
* @Description:  
* @author 你的名字 
* @date 2013-7-3 下午6:11:04 
*  
*/
public class KeyUtil {

	public static String createKey(String config, String orderNum, String companyId) {
		return MD5Encrypt.encrypt(MD5Encrypt.encrypt(config) + orderNum + companyId);
	}
}
