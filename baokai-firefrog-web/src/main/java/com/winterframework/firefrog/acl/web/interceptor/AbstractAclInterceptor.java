package com.winterframework.firefrog.acl.web.interceptor;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.winterframework.firefrog.common.http.IHttpJsonClient;
import com.winterframework.modules.web.util.RequestContext;

public abstract class AbstractAclInterceptor implements HandlerInterceptor {
	private static final Logger logger = Logger.getLogger(AbstractAclInterceptor.class);

	@Resource(name = "httpJsonClientImpl")
	protected IHttpJsonClient httpClient;

	protected String redirectAddr;

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		if (RequestContext.get() != null) {
			RequestContext.get().end();
		}
	}

	@Override
	public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, ModelAndView arg3)
			throws Exception {
	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object arg2) throws Exception {
		RequestContext.begin(request.getSession().getServletContext(), request, response);
		//如果取不到当前登录用户，则跳转到指定的界面   
		return true;
	}

	/** 
	 * 获取具体的对应跳转地址
	*/
	abstract String getRedirectAddr();
}
