package com.winterframework.firefrog.session.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.winterframework.firefrog.session.interceptor.vo.UserStrucResponse;
import com.winterframework.modules.spring.exetend.PropertyConfig;
import com.winterframework.modules.web.util.JsonMapper;
import com.winterframework.modules.web.util.RequestContext;

/**
 * 访问前台界面指定IP白名單的拦截器
 */
public class FrontUserWhiteListIpAclInterceptor extends AbstractAclInterceptor {
	static Logger logger = LoggerFactory
			.getLogger(FrontUserWhiteListIpAclInterceptor.class);
	@PropertyConfig(value = "url.front.login")
	protected String redirectAddr;

	public String getRedirectAddr() {
		return redirectAddr;
	}

	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		// 判断用户session是否有效
		if (RequestContext.get().session() == null
				|| RequestContext.get().session().getAttribute("info") == null) {
			
			Object test=request.getAttribute("frontContextPath");
			if(test!=null){
				redirectAddr=test+"/login/index/";
			}
			logger.error("session 过期，需要跳转到:" + redirectAddr);
			response.sendRedirect(redirectAddr);
			return false;
		}
		// //session安全教研
		String x_forward_for = getClientIpAddr(request);
		//String ua = request.getHeader("User-Agent");
		//Object secur = (RequestContext.get().session().getAttribute("secur"));
		//logger.debug("x_forward_for:"+x_forward_for+" ua:"+ua+ " secur:"+secur + " secur="+DigestUtils.md5Hex(ua + "_" + x_forward_for));
		//System.out.println("x_forward_for:"+x_forward_for+" ua:"+ua+ " secur:"+secur + " secur="+DigestUtils.md5Hex(ua + "_" + x_forward_for));
		// 判断用户权限
		final UserStrucResponse map = JsonMapper.nonAlwaysMapper()
				.fromMapToObject(
						RequestContext.get().session().getAttribute("info"),
						UserStrucResponse.class);
		String whiteListIP = map.getWhiteListIP();
		String accunt = map.getAccount();
		if(whiteListIP != null){//表示為 指定IP白名單帳號
			if(whiteListIP.indexOf(x_forward_for) <0 ){
				//表示 IP 不在 指定 IP 內需擋掉
				Object test=request.getAttribute("frontContextPath");
				if(test!=null){
					redirectAddr=test+"/login/logout/";
				}
				logger.error("指定IP白名單 IP 異常退出登錄 : 帳號= " + accunt + " RequestIP = " + x_forward_for + " 允許的IP: " + whiteListIP);
				response.sendRedirect(redirectAddr);
				return false;
			}
		}
		return true;
	}
	
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
