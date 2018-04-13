/**
 * Copyright (C) abba, 2010
 */
package com.winterframework.modules.web.util;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.util.ObjectUtils;

/**
 * 
 * @author abba(xuhengbiao@gmail.com) in 2010-6-8
 * @since 1.0
 */
public class RequestUtils {
	public static String getRemoteAddr(HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		return ip;

	}

	public static int getRemotePort(HttpServletRequest request) {
		String strPort = request.getHeader("remote_port");
		if (strPort != null) {
			return Integer.valueOf(strPort);
		}
		return request.getRemotePort();
	}

	public static void setCookie(HttpServletRequest request,
			HttpServletResponse response, String name, String value,
			int max_age, boolean all_sub_domain) {
		Cookie cookie = new Cookie(name, value);
		cookie.setPath("/");
		cookie.setMaxAge(max_age);
		response.addCookie(cookie);
	}

	public static void deleteCookie(HttpServletRequest request,
			HttpServletResponse response, String name, boolean all_sub_domain) {
		Cookie[] cookies = request.getCookies();
		if (!ObjectUtils.isEmpty(cookies)) {
			for (Cookie c : cookies) {
				c.setMaxAge(0);
				response.addCookie(c);
			}
		}
	}

	public static boolean isRobot(HttpServletRequest request) {
		return false;
	}
}
