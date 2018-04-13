package com.winterframework.firefrog.user.web.dto;

import java.io.Serializable;
import java.util.List;

/**
 * 用户申述查询返回申诉列表
 * 
 * @author david
 * 
 */
public class UserAppealListResponse implements Serializable {

	private static final long serialVersionUID = 4435089716310294683L;

	/**
	 * 申诉查询列表
	 */
	private List<UserAppealListStruc> validateList;

	public List<UserAppealListStruc> getValidateList() {
		return validateList;
	}

	public void setValidateList(List<UserAppealListStruc> validateList) {
		this.validateList = validateList;
	}
}
