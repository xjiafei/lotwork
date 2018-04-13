package com.winterframework.firefrog.game.web.util;

import javax.servlet.http.HttpServletRequest;

public class IPUtil {
    public static String getClientIpAddr(HttpServletRequest request) { 
        String ip = request.getHeader("x-forwarded-for"); 
        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) { 
            ip = request.getHeader("Proxy-Client-IP"); 
        } 
        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) { 
            ip = request.getHeader("WL-Proxy-Client-IP"); 
        } 
        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) { 
            ip = request.getRemoteAddr(); 
        } 
        if(ip!=null && ip.length()>0){
        	
        }else{
        	return "";
        }
        String[] tt=ip.split(",");
        return tt[0].trim(); 
    } 
}
