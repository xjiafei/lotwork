package com.winterframework.firefrog.game.web.interceptor;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.core.type.TypeReference;
import com.winterframework.firefrog.common.httpjsonclient.IHttpJsonClient;
import com.winterframework.firefrog.common.redis.ConfigRedisRequestDTO;
import com.winterframework.firefrog.common.redis.ConfigRedisResponse;
import com.winterframework.firefrog.common.util.DataConverterUtil;
import com.winterframework.modules.spring.exetend.PropertyConfig;
import com.winterframework.modules.web.jsonresult.Response;
import com.winterframework.modules.web.util.RequestContext;

public class AclInterceptor implements HandlerInterceptor {

	@PropertyConfig(value = "url.connect.api.firefrog")
	private String urlPath;

	@Resource(name = "httpJsonClientImpl")
	private IHttpJsonClient httpClient;

	@PropertyConfig(value = "url.common.getRedisValue")
	private String redisValue;

	private Logger logger = Logger.getLogger(AclInterceptor.class);

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		RequestContext.begin(request.getSession().getServletContext(), request, response);
		logger.debug("do get admin acl.");
		ConfigRedisRequestDTO redisReq = new ConfigRedisRequestDTO();
		Long userId = RequestContext.getCurrUser().getId();
		redisReq.setModule("acl");
		redisReq.setKey(String.valueOf(userId));
		redisReq.setFunction("getAdminAcl");
		Response<ConfigRedisResponse> resp = httpClient.invokeHttp(urlPath + redisValue, redisReq,
				new TypeReference<Response<ConfigRedisResponse>>() {
				});
		String aclStr = resp.getBody().getResult().getVal();
		if (aclStr != null && !aclStr.equals("")) {
			List<String> aclList = (List<String>) DataConverterUtil.convertJson2Object(aclStr, ArrayList.class);
			request.setAttribute("aclList", aclList);
		}
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {

	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		RequestContext.get().end();
	}

}
