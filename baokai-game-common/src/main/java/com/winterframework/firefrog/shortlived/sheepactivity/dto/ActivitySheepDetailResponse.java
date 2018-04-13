package com.winterframework.firefrog.shortlived.sheepactivity.dto;

import java.util.List;

import com.winterframework.firefrog.shortlived.sheepactivity.dao.vo.ActivitySheepDetail;

public class ActivitySheepDetailResponse {
	private List<ActivitySheepDetail> list ;

	public List<ActivitySheepDetail> getList() {
		return list;
	}

	public void setList(List<ActivitySheepDetail> list) {
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
