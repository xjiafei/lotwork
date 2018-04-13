package com.winterframework.firefrog.advert.web.dto;

import java.io.Serializable;
import java.util.List;

public class AdNoticeDelRequest implements Serializable {

	private static final long serialVersionUID = 2579145722838190001L;
	
	private List<Long> ids;

	public List<Long> getIds() {
		return ids;
	}

	public void setIds(List<Long> ids) {
		this.ids = ids;
	}

}
