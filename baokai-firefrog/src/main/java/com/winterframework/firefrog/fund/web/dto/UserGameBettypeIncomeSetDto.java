package com.winterframework.firefrog.fund.web.dto;

import java.util.List;


/**
 * @author cms group
 * @version 1.0
 * @since 1.0
 */

public class UserGameBettypeIncomeSetDto {



	private String setCodeName;
	
	private List<UserGameBettypeIncomeDetailDto> details;

	public String getSetCodeName() {
		return setCodeName;
	}

	public void setSetCodeName(String setCodeName) {
		this.setCodeName = setCodeName;
	}

	public List<UserGameBettypeIncomeDetailDto> getDetails() {
		return details;
	}

	public void setDetails(List<UserGameBettypeIncomeDetailDto> details) {
		this.details = details;
	}

	
}
