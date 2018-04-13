package com.winterframework.adbox.web.controller;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.winterframework.adbox.entity.AdUser;

public class LoginFilter implements  Filter {

	@Override
	public void destroy() {		
	}

	@Override
	public void doFilter(ServletRequest arg0, ServletResponse arg1,
			FilterChain arg2) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest)arg0;
		if(request.getServletPath().contains("login")||
				request.getServletPath().contains("/server")||request.getServletPath().contains("lg")||request.getServletPath().contains("easyui")||request.getServletPath().contains("loginError")
				||request.getServletPath().contains("unLogin")){
			arg2.doFilter(arg0, arg1);
		}else{
			AdUser adUser = (AdUser)request.getSession().getAttribute("loginUser");
			if(adUser!=null){
				arg2.doFilter(arg0, arg1);
			}else{
				HttpServletResponse res = (HttpServletResponse)arg1;
				String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/user/loginError";
				res.sendRedirect(basePath);
				return; 
			}
		}
		
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
	}

}
