package com.winterframework.firefrog.user.web.dto;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

/**
 * 用户申述详情，审核界面信息
 * 
 * @author david
 * 
 */
public class UserAppealDetailRequestDTO implements Serializable {

	private static final long serialVersionUID = 5924326617496255471L;

	@NotNull
	private int id;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

}
