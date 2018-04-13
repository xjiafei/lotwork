package com.winterframework.firefrog.user.web.dto;

import java.io.Serializable;

/** 
* @ClassName: QueryUserFreezeRequestDTO 
* @Description: 查询冻结列表 
* @author david 
* @date 2013-5-7 上午11:14:50 
*  
*/
public class QueryFreezeUserRequestDTO implements Serializable {

	private static final long serialVersionUID = -1905020680742501824L;

	private String account;

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

}
