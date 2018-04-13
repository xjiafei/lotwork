package com.winterframework.firefrog.advert.web.dto.sheep;

import java.util.List;

public class ActivitySheepHongBaoResponse {
	private List<ActivitySheepHongBao> list ;

	public List<ActivitySheepHongBao> getList() {
		return list;
	}

	public void setList(List<ActivitySheepHongBao> list) {
		this.list = list;
	}
	
	public Long getUnCheckNum() {
		return unCheckNum;
	}

	public void setUnCheckNum(Long unCheckNum) {
		this.unCheckNum = unCheckNum;
	}

	private Long unCheckNum;
}
