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
        System.out.println(request.getHeader("x-forwarded-for"));
        String[] tt=ip.split(",");
     //   return tt[tt.length-1].trim(); 
        return tt[0].trim(); 
    } 
}
