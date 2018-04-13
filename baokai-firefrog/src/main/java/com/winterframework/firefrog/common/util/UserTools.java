package com.winterframework.firefrog.common.util;

import com.winterframework.firefrog.acl.entity.AclUser;
import com.winterframework.firefrog.user.entity.User;
import com.winterframework.modules.web.jsonresult.Request;

public class UserTools {

	public static User getUserFromHead(Request<?> request){
		User applyUser = new User();
		applyUser.setId(request.getHead().getUserId());
		applyUser.setAccount(AccountTool.getRealAccount(request.getHead().getUserAccount()));
		return applyUser;
	}
	public static AclUser getBackUserFromHead(Request<?> request){
		AclUser applyUser = new AclUser();
		applyUser.setId(request.getHead().getUserId());
		applyUser.setAccount(AccountTool.getRealAccount(request.getHead().getUserAccount()));
		return applyUser;
	}
	public static AclUser getAdmin(){
		AclUser applyUser = new AclUser();
		applyUser.setId(-1L);
		applyUser.setAccount("admin");
		return applyUser;
	}
	
	

}
