package com.winterframework.firefrog.session.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.winterframework.modules.web.util.RequestContext;

public abstract class AbstractAclInterceptor implements HandlerInterceptor {
	private static final Logger logger = LoggerFactory.getLogger(AbstractAclInterceptor.class);


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
		return RequestContext.get().session()!=null;
	}

	/** 
	 * 获取具体的对应跳转地址
	*/
	abstract String getRedirectAddr();
}
