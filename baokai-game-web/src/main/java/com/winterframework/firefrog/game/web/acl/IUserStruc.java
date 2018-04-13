package com.winterframework.firefrog.game.web.acl;

import java.io.Serializable;

import com.winterframework.modules.web.util.IUser;

public class IUserStruc extends AclUserStruc implements Serializable,IUser {

	private String userChain;
	
	
	public String getUserChain() {
		return userChain;
	}

	public void setUserChain(String userChain) {
		this.userChain = userChain;
	}

	@Override
	public Long getId() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getUserName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean IsBlocked() {
		// TODO Auto-generated method stub
		return false;
	}

}
