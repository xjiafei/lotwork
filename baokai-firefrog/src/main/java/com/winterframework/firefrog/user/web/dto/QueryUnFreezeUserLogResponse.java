package com.winterframework.firefrog.user.web.dto;

import java.io.Serializable;
import java.util.List;

/**
 * 冻结日志信息列表
 * 
 * @author david
 * 
 */
public class QueryUnFreezeUserLogResponse implements Serializable {

	private static final long serialVersionUID = 4435089716310294683L;

	private List<QueryUnFreezeUserLogStruc> userList;

	public List<QueryUnFreezeUserLogStruc> getUserList() {
		return userList;
	}

	public void setUserList(List<QueryUnFreezeUserLogStruc> userList) {
		this.userList = userList;
	}

}
