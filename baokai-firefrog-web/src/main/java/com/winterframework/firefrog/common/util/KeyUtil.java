/**   
* @Title: KeyUtil.java 
* @Package com.winterframework.firefrog.utils 
* @Description: TODO(用一句话描述该文件做什么) 
* @author 你的名字   
* @date 2013-7-3 下午6:11:04 
* @version V1.0   
*/
package com.winterframework.firefrog.common.util;

import com.winterframework.modules.security.MD5Encrypt;

/** 
* @ClassName: KeyUtil 
* @Description: TODO(这里用一句话描述这个类的作用) 
* @author 你的名字 
* @date 2013-7-3 下午6:11:04 
*  
*/
public class KeyUtil {

	public static String createKey(String config, String orderNum, String companyId) {
		return MD5Encrypt.encrypt(MD5Encrypt.encrypt(config) + orderNum + companyId);
	}
}
