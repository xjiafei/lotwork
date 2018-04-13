package com.winterframework.firefrog.fund.web.dto;

import java.util.List;


/**
 * @author cms group
 * @version 1.0
 * @since 1.0
 */

public class UserGameBettypeIncomeResponse {

	private List<UserGameBettypeIncomeGroupDto> groups;

	public List<UserGameBettypeIncomeGroupDto> getGroups() {
		return groups;
	}

	public void setGroups(List<UserGameBettypeIncomeGroupDto> groups) {
		this.groups = groups;
	}

	
}
