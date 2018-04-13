package com.winterframework.firefrog.game.web.interception;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.winterframework.firefrog.session.interceptor.vo.UserStrucResponse;
import com.winterframework.modules.web.util.RequestContext;
public class SessionInterceptor implements HandlerInterceptor {
	private final static Logger logger = LoggerFactory.getLogger(SessionInterceptor.class);
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		logger.debug("get username");
		Integer userLvl=1;
		try {
			//此处本地开发环境运行会抛出一个异常，属于正常现象，已经try catch处理，原因是我们没有登录功能，服务器上此处无bug
			UserStrucResponse usr = (UserStrucResponse)RequestContext.getCurrUser();
			userLvl=usr.getUserLvl();
		} catch (Exception e) {
			logger.error("获取用户信息异常",e);
		}
		request.setAttribute("userlvl",userLvl);
		request.setAttribute("userName",RequestContext.getCurrUser().getUserName());
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
	}

}