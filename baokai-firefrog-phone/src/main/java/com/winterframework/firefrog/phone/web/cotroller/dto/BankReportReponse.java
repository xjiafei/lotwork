package com.winterframework.firefrog.phone.web.cotroller.dto;

import java.io.Serializable;
import java.util.List;

public class BankReportReponse implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3446552717379903338L;
	private List<BankReportDto> list;

	public List<BankReportDto> getList() {
		return list;
	}

	public void setList(List<BankReportDto> list) {
		this.list = list;
	}
}
