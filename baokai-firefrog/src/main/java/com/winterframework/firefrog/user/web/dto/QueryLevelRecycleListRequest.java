package com.winterframework.firefrog.user.web.dto;

import java.io.Serializable;

/** 
* @ClassName: QueryLevelRecycleListRequest 
* @Description: 查询一代回收用户列表 
* @author Andy 
* @date 2015-10-7 下午05:12:00 
*  
*/
public class QueryLevelRecycleListRequest implements Serializable {

	private static final long serialVersionUID = -1905020680742501824L;

	private String account;

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

}
