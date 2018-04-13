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
 * 访问前台界面的拦截器
 */
public class FrontUserAclInterceptor extends AbstractAclInterceptor {
	static Logger logger = LoggerFactory
			.getLogger(FrontUserAclInterceptor.class);
	@PropertyConfig(value = "url.front.login")
	protected String redirectAddr;

	public String getRedirectAddr() {
		return redirectAddr;
	}
	protected boolean supportAnonymity=false;
	

	public boolean isSupportAnonymity() {
		return supportAnonymity;
	}

	public void setSupportAnonymity(boolean supportAnonymity) {
		this.supportAnonymity = supportAnonymity;
	}





	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
	       
		// 判断用户session是否有效
		if (RequestContext.get().session() == null
				|| RequestContext.get().session().getAttribute("info") == null || JsonMapper.nonAlwaysMapper()
						.fromMapToObject(
								RequestContext.get().session().getAttribute("info"),
								UserStrucResponse.class).getId()<=0) {
			if(supportAnonymity){
				UserStrucResponse resp=new UserStrucResponse();
				resp.setId(Long.valueOf(0));
				resp.setIsAllZero(Long.valueOf(0));
				resp.setUserLvl(Integer.valueOf(-1));
				resp.setVipLvl(0);
				RequestContext.get().session().setAttribute("info",resp);
			}else{			
				Object test=request.getAttribute("frontContextPath");
				if(test!=null){
					redirectAddr=test+"/login/index/";
				}
				logger.error("session 过期，需要跳转到:" + redirectAddr);
				response.sendRedirect(redirectAddr);
				return false;
			}
		}
		// //session安全教研
		String x_forward_for = request.getHeader("X-Forwarded-For");
		String ua = request.getHeader("User-Agent");
		Object secur = (RequestContext.get().session().getAttribute("secur"));
		//System.out.println("x_forward_for:"+x_forward_for+" ua:"+ua+ " secur:"+secur + " secur="+DigestUtils.md5Hex(ua + "_" + x_forward_for));
		if (secur != null
				&& !StringUtils.equalsIgnoreCase(
						DigestUtils.md5Hex(ua + "_" + x_forward_for),
						(String) secur)) {
		    response.setStatus(406);
			return false;
		}
		// 判断用户权限
		final UserStrucResponse map = JsonMapper.nonAlwaysMapper()
				.fromMapToObject(
						RequestContext.get().session().getAttribute("info"),
						UserStrucResponse.class);
		if (map.getId() == null || map.getId()<=0) {
			if(supportAnonymity){
				map.setId(Long.valueOf(0));
				map.setIsAllZero(Long.valueOf(0));
				map.setUserLvl(Integer.valueOf(-1));
				map.setVipLvl(0);
				RequestContext.get().session().setAttribute("info",map);
			}else{
			logger.error("session里面没有用户id:" + redirectAddr);
			Object test=request.getAttribute("loginUrl");
			if(test!=null){
				redirectAddr=test+"/login/index/";
			}
			response.sendRedirect(redirectAddr);
			return false;}
		}

		RequestContext.setIUer(map);
		request.setAttribute("userlvl", map.getUserLvl());
		request.setAttribute("userId", map.getId());
		request.setAttribute("isAllZero", map.getIsAllZero());
		request.setAttribute("vip", map.getVipLvl());
		request.setAttribute("userName", RequestContext.getCurrUser()
				.getUserName());
		return true;
	}
}
