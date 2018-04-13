package com.winterframework.firefrog.game.web.interceptor;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.core.type.TypeReference;
import com.winterframework.firefrog.common.httpjsonclient.IHttpJsonClient;
import com.winterframework.firefrog.game.web.acl.AclUserLoginRequest;
import com.winterframework.firefrog.game.web.acl.AclUserStruc;
import com.winterframework.modules.spring.exetend.PropertyConfig;
import com.winterframework.modules.web.jsonresult.Response;
import com.winterframework.modules.web.util.IUser;
import com.winterframework.modules.web.util.RequestContext;

/** 
* @ClassName: LoginCookieInterceptor 
* @Description: 登陆拦截器
* @author 你的名字 
* @date 2013-11-20 下午3:15:05 
*  
*/
public class LoginCookieInterceptor implements HandlerInterceptor {

	@Resource(name = "httpJsonClientImpl")
	private IHttpJsonClient httpClient;

	@PropertyConfig(value = "url.aclAdmin.adminLogin")
	private String adminLoginUrl;

	@PropertyConfig(value = "url.connect.api.firefrog")
	private String urlPath;

	private Logger logger = LoggerFactory.getLogger(LoginCookieInterceptor.class);

	/**
	* Title: afterCompletion
	* Description:
	* @param arg0
	* @param arg1
	* @param arg2
	* @param arg3
	* @throws Exception 
	* @see org.springframework.web.servlet.HandlerInterceptor#afterCompletion(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, java.lang.Object, java.lang.Exception) 
	*/
	@Override
	public void afterCompletion(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, Exception arg3)
			throws Exception {

	}

	/**
	* Title: postHandle
	* Description:
	* @param arg0
	* @param arg1
	* @param arg2
	* @param arg3
	* @throws Exception 
	* @see org.springframework.web.servlet.HandlerInterceptor#postHandle(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, java.lang.Object, org.springframework.web.servlet.ModelAndView) 
	*/
	@Override
	public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, ModelAndView arg3)
			throws Exception {

	}

	/**
	* Title: preHandle
	* Description:
	* @param arg0
	* @param arg1
	* @param arg2
	* @return
	* @throws Exception 
	* @see org.springframework.web.servlet.HandlerInterceptor#preHandle(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, java.lang.Object) 
	*/
	@Override
	public boolean preHandle(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2) throws Exception {
		Cookie[] cookies = arg0.getCookies();
		arg0.setAttribute("userId", "1373610");
		boolean isLogin = false;
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				if (cookie.getName().equalsIgnoreCase("_frcid")) {
					isLogin = true;
				}
			}
		}
		if (!isLogin) {
			String account = "gumu";
			String passwd = "123qwe";
			AclUserLoginRequest request = new AclUserLoginRequest();
			request.setAccount(account == null ? "123qwe" : account);
			//request.setAccount("test1");
			request.setPasswd(passwd == null ? "123qwe" : passwd);
			try {
//				final Response<AclUserStruc> resp = httpClient.invokeHttp(urlPath + adminLoginUrl, request,
//						new TypeReference<Response<AclUserStruc>>() {
//						});
//				if (resp.getBody().getResult() != null) {
					/*Cookie userIdCookie = new Cookie("userId", String.valueOf(resp.getBody().getResult().getId()));
					Cookie accountCookie = new Cookie("account",
							String.valueOf(resp.getBody().getResult().getAccount()));
					userIdCookie.setMaxAge(maxTime);
					accountCookie.setMaxAge(maxTime);
					arg1.addCookie(userIdCookie);
					arg1.addCookie(accountCookie);*/
//					RequestContext.begin(arg0.getSession().getServletContext(), arg0, arg1);
					RequestContext.get().saveUserInCookie(new IUser() {

						@Override
						public Long getId() {
							return 1373610L;
						}

						@Override
						public String getUserName() {
							return "gumu";
						}

						@Override
						public boolean IsBlocked() {
							return false;
						}

					}, true);

					String url = arg0.getRequestURL().toString();
					Map map = arg0.getParameterMap();
					Iterator it = map.entrySet().iterator();
					
					int i = 0;
					while (it.hasNext()) {
						Entry entry = (Entry) it.next();
						String[] value = (String[]) entry.getValue();
						if (i == 0) {
							url += "?" + entry.getKey() + "=" + value[0];
						} else {
							url += "&" + entry.getKey() + "=" + value[0];
						}
						i++;
					}

					arg1.sendRedirect(url);
//				}
			} catch (Exception e) {
				logger.error("login error", e);
			}
		}

		return true;
	}

}
