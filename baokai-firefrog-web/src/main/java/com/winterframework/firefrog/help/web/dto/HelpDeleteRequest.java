package com.winterframework.firefrog.help.web.dto;

import java.util.List;

public class HelpDeleteRequest {
	
	private List<Long> ids;

	public List<Long> getIds() {
		return ids;
	}

	public void setIds(List<Long> ids) {
		this.ids = ids;
	}

}
