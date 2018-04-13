package com.winterframework.firefrog.interceptor;
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
        logger.info(RequestContext.getCurrUser().getUserName());
        request.setAttribute("userName",RequestContext.getCurrUser().getUserName());
        Integer vip = ((UserStrucResponse)RequestContext.get().getCurrUser()).getVipLvl();
        request.setAttribute("vip",vip);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
            throws Exception {
    }

}