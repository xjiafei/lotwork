package com.winterframework.firefrog.fund.web.dto;

import java.util.List;


/**
 * @author cms group
 * @version 1.0
 * @since 1.0
 */

public class UserGameBettypeIncomeGroupDto {

	private String groupCodeName;

	private List<UserGameBettypeIncomeSetDto> sets;

	public String getGroupCodeName() {
		return groupCodeName;
	}

	public void setGroupCodeName(String groupCodeName) {
		this.groupCodeName = groupCodeName;
	}

	public List<UserGameBettypeIncomeSetDto> getSets() {
		return sets;
	}

	public void setSets(List<UserGameBettypeIncomeSetDto> sets) {
		this.sets = sets;
	}


	
}
