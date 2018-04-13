/**   
* @Title: CookieUtil.java 
* @Package com.winterframework.firefrog.common.util 
* @Description: TODO(用一句话描述该文件做什么) 
* @author 你的名字   
* @date 2014-1-22 下午2:53:39 
* @version V1.0   
*/
package com.winterframework.firefrog.common.util;

import javax.servlet.http.Cookie;

/** 
* @ClassName: CookieUtil 
* @Description: TODO(这里用一句话描述这个类的作用) 
* @author page
* @date 2014-1-22 下午2:53:39 
*  
*/
public class CookieUtil {

	/** 
	* @Title: getCookie 
	* @Description: 取指定名字的cookie
	* @param cookies
	* @param cookieName
	* @return
	*/
	public static Cookie getCookie(Cookie[] cookies, String cookieName) {
		for (Cookie cookie : cookies) {
			if (cookie.getName().equalsIgnoreCase(cookieName)) {
				return cookie;
			}
		}
		
		return null;
	}

}
