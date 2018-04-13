package com.winterframework.firefrog.game.web.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.winterframework.firefrog.game.web.util.CheckMobile;

/**
 * WAP 訊息攔截器。<br>
 * 當進入點為移動裝置(不論iOS、Android、Windows Mobile...)，request 塞入 isMobile = yes 的值。 
 * @author user
 */
public class WapInterceptor implements HandlerInterceptor {


	private Logger logger = Logger.getLogger(AclInterceptor.class);

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		boolean isMoblie = CheckMobile.check(request.getHeader("USER-AGENT"));
		if (isMoblie){
			request.setAttribute("IsMobile", "yes");
		}
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		
	//	logger.error("test post interecptroe");
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
	//	RequestContext.get().end();
	}

}
