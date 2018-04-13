package com.winterframework.firefrog.user.entity;

import java.io.Serializable;
import java.util.List;

import com.winterframework.firefrog.user.dao.vo.UserCustomerVo;


public class UserListResult implements Serializable {
	private static final long serialVersionUID = 1L;
	private List<UserCustomerVo> userCustomerList;
	
	public List<UserCustomerVo> getUserCustomerList() {
		return userCustomerList;
	}
	public void setUserCustomerList(List<UserCustomerVo> userCustomerList) {
		this.userCustomerList = userCustomerList;
	}
}
