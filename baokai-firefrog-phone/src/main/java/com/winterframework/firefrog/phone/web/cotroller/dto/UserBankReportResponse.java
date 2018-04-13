package com.winterframework.firefrog.phone.web.cotroller.dto;

import java.io.Serializable;
import java.util.List;

public class UserBankReportResponse implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1980674179094782908L;
	
	private List<UserBankReportDto> list;

	public List<UserBankReportDto> getList() {
		return list;
	}

	public void setList(List<UserBankReportDto> list) {
		this.list = list;
	}
}
