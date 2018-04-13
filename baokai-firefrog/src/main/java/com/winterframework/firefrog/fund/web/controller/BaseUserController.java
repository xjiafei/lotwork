package com.winterframework.firefrog.fund.web.controller;

import javax.annotation.Resource;

import com.winterframework.firefrog.user.service.IUserProfileService;

public class BaseUserController {
	
	@Resource(name = "userProfileServiceImpl")
	protected IUserProfileService userProfileService;
	public static String getAccountFromUserchain(String chain){
		String[] vals=chain.split("/");
		if(vals.length<=1){
			return chain;
		}else{
			return vals[vals.length-1];
		}
	}
}
