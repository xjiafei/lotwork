package com.winterframework.firefrog.session.interceptor;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.winterframework.modules.web.util.RequestContext;
public class PathInterceptor implements HandlerInterceptor {
	private final static Logger logger = LoggerFactory.getLogger(PathInterceptor.class);
	
	private Map<String, String> pathMap;
	private String loginCookie;
	
	public String getLoginCookie() {
		return loginCookie;
	}

	public void setLoginCookie(String loginCookie) {
		this.loginCookie = loginCookie;
	}

	public Map<String, String> getPathMap() {
		return pathMap;
	}

	public void setPathMap(Map<String, String> pathMap) {
		this.pathMap = pathMap;
	}

	public PathInterceptor() { 
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
		//move get user function to AclInterceptor 
		try{
			RequestContext.begin(request.getSession().getServletContext(), request, response);
			resetDomain(request);
		}catch(Exception e){
			e.printStackTrace();
		}
//		RequestContext.get().saveUserInCookie(new IUser(){
//
//			@Override
//			public Long getId() {
//				// TODO Auto-generated method stub
//				return 100L;
//			}
//
//			@Override
//			public String getUserName() {
//				// TODO Auto-generated method stub
//				return "abc";
//			}
//
//			@Override
//			public boolean IsBlocked() {
//				// TODO Auto-generated method stub
//				return false;
//			}
//			
//		}, true);

		return true;
	}

	//在业务处理器处理请求执行完成后,生成视图之前执行的动作   
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		logger.debug("==============执行顺序: 2、postHandle================");
		Iterator<Entry<String, String>> it = pathMap.entrySet().iterator();
		while(it.hasNext()){
			Entry<String,String> en = it.next();
			request.setAttribute(en.getKey(), en.getValue());
		}
		
		resetDomain(request);
	}
	private void resetDomain(HttpServletRequest request){
		//为了多域名，不得不重设以上几个域名
		String host=request.getHeader("Host");
		if(StringUtils.isNotEmpty(host)){
			request.setAttribute("currentContextPath", "http://"+host);
			request.setAttribute("gamecenterPath", "http://"+host.replace("www", "admin").replace("www2", "admin"));
			request.setAttribute("frontGame", "http://"+host.replace("www2", "em").replace("www", "em").replace("pt", "em").replace("mk2", "em"));
			request.setAttribute("ptContextPath",  "http://"+host.replace("www2", "pt").replace("www", "pt").replace("em", "pt").replace("mk2", "pt"));
			request.setAttribute("mkContextPath",  "http://"+host.replace("www2", "mk2").replace("www", "mk2").replace("em", "mk2"));
	
			request.setAttribute("frontContextPath", "http://"+fromHosttoFrontHost(host));
			request.setAttribute("userContextPath", "http://"+fromHosttoFrontHost(host));
			request.setAttribute("adminContextPath",  "http://"+host.replace("www", "admin").replace("www2", "admin"));
			
		}
		
	}
	private String fromHosttoFrontHost(String host){
		if(host.contains("joy188")){
			return "www2.joy188.com:888";
		}
		return host.replace("admin","www").replace("em","www").replace("mk2","www");
	}
	/** 
	 * 在DispatcherServlet完全处理完请求后被调用  
	 *  
	 *   当有拦截器抛出异常时,会从当前拦截器往回执行所有的拦截器的afterCompletion() 
	 */
	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		// TODO Auto-generated method stub  end
		//System.out.println(RequestContext.getCurrUser().getUserName());
		//RequestContext.get().end();
		logger.debug("==============执行顺序: 3、afterCompletion================");
	}

}