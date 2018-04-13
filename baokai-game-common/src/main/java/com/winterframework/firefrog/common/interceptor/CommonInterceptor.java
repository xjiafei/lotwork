package com.winterframework.firefrog.common.interceptor;

import java.nio.charset.Charset;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.http.MediaType;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.servlet.ModelAndView;

public class CommonInterceptor implements HandlerInterceptor {

	private final int APPLICATION_JSONP = 1;
	private final int APPLICATION_JSON = 2;
	private final int APPLICATION_DEF = 0;
	private Logger log = Logger.getLogger(CommonInterceptor.class);

	public CommonInterceptor() {
	}

	private String mappingURL;//利用正则映射到需要拦截的路径    

	public void setMappingURL(String mappingURL) {
		this.mappingURL = mappingURL;
	}

	/** 
	 * 在业务处理器处理请求之前被调用 
	 * 如果返回false 
	 *     从当前的拦截器往回执行所有拦截器的afterCompletion(),再退出拦截器链 
	 *  
	 * 如果返回true 
	 *    执行下一个拦截器,直到所有的拦截器都执行完毕 
	 *    再执行被拦截的Controller 
	 *    然后进入拦截器链, 
	 *    从最后一个拦截器往回执行所有的postHandle() 
	 *    接着再从最后一个拦截器往回执行所有的afterCompletion() 
	 */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		switch (isJsonp(request)) {
		case APPLICATION_JSONP: {
			Set<MediaType> result = new LinkedHashSet<MediaType>();

			//Set<MediaType> medias=(Set<MediaType>) request.getAttribute(HandlerMapping.PRODUCIBLE_MEDIA_TYPES_ATTRIBUTE);
			MediaType media = new MediaType("application", "javascript", Charset.forName("UTF-8"));
			String callback = request.getParameter("callback");
			result.add(media);
			request.setAttribute(HandlerMapping.PRODUCIBLE_MEDIA_TYPES_ATTRIBUTE, result);
			response.getOutputStream().write((callback + "(").getBytes());
			break;
		}
		case APPLICATION_JSON: {
			Set<MediaType> result = new LinkedHashSet<MediaType>();

			MediaType media = MediaType.APPLICATION_JSON;
			result.add(media);
			request.setAttribute(HandlerMapping.PRODUCIBLE_MEDIA_TYPES_ATTRIBUTE, result);
			break;
		}
		default:
			break;
		}
		return true;
	}

	/**
	 * 是否jsonp协议
	 */
	private final int isJsonp(HttpServletRequest request) {
		//text/javascript, application/javascript, application/ecmascript, application/x-ecmascript, */*; q=0.01 
		String contentType = request.getHeader("Content-Type");
		if (StringUtils.contains(contentType, "text/javascript")
				|| StringUtils.contains(contentType, "application/javascript")
				|| StringUtils.contains(contentType, "application/ecmascript")
				|| StringUtils.contains(contentType, "application/x-ecmascript")) {
			return APPLICATION_JSONP;
		}

		String[] strs = StringUtils.split(request.getRequestURI(), ".");
		String str = strs[strs.length - 1];
		if (StringUtils.equalsIgnoreCase(str, "jsonp")) {
			return APPLICATION_JSONP;
		} else if (StringUtils.equalsIgnoreCase(str, "json")) {
			return APPLICATION_JSON;
		}
		return APPLICATION_DEF;

	}

	//在业务处理器处理请求执行完成后,生成视图之前执行的动作   
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		log.info("==============执行顺序: 2、postHandle================");
	}

	/** 
	 * 在DispatcherServlet完全处理完请求后被调用  
	 *  
	 *   当有拦截器抛出异常时,会从当前拦截器往回执行所有的拦截器的afterCompletion() 
	 */
	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		log.info("==============执行顺序: 3、afterCompletion================");
	}

}