package com.winterframework.firefrog.phone.web.cotroller.dto;

import java.io.Serializable;
import java.util.List;

public class AllIssueResponse implements Serializable{

	private static final long serialVersionUID = -4036963807806428401L;
	private List<AllIssueDto> list;

	public List<AllIssueDto> getList() {
		return list;
	}

	public void setList(List<AllIssueDto> list) {
		this.list = list;
	}
}
