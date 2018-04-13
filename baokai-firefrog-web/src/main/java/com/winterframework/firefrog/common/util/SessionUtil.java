/**   
* @Title: SessionUtil.java 
* @Package com.winterframework.firefrog.common.util 
* @Description: TODO(用一句话描述该文件做什么) 
* @author 你的名字   
* @date 2014-1-22 下午2:56:41 
* @version V1.0   
*/
package com.winterframework.firefrog.common.util;

/** 
* @ClassName: SessionUtil 
* @Description: TODO(这里用一句话描述这个类的作用) 
* @author 你的名字 
* @date 2014-1-22 下午2:56:41 
*  
*/
public class SessionUtil {
	/** 
	* @Title: checkIpAndUserAgent 
	* @Description: check ip and user agent is same as their value when session id was created.
	* @param ip
	* @param userAgent
	* @param sessionId
	* @return
	*/
	public static boolean checkIpAndUserAgent(Long ip, String userAgent, String salt, String sessionId) {
		String encryptResult = MultMd5.dmd5(ip + userAgent, salt);
		if (sessionId.startsWith(encryptResult)) {
			return true;
		}
		return false;
	}
}
