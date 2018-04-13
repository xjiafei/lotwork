package com.winterframework.firefrog.advert.web.dto.sheep;

import java.util.List;



public class ActivitySheepWheelSurfResponse {
	private List<ActivitySheepWheelSurf> list ;

	public List<ActivitySheepWheelSurf> getList() {
		return list;
	}

	public void setList(List<ActivitySheepWheelSurf> list) {
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
