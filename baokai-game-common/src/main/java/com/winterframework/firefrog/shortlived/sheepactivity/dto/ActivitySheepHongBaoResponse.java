package com.winterframework.firefrog.shortlived.sheepactivity.dto;

import java.util.List;

import com.winterframework.firefrog.shortlived.sheepactivity.dao.vo.ActivitySheepHongBao;

public class ActivitySheepHongBaoResponse {
	private List<ActivitySheepHongBao> list ;

	public List<ActivitySheepHongBao> getList() {
		return list;
	}

	public void setList(List<ActivitySheepHongBao> list) {
		this.list = list;
	}

	public ActivitySheepHongBaoResponse(List<ActivitySheepHongBao> list ,Long unCheckNum) {
		super();
		this.list = list;
		this.unCheckNum = unCheckNum;
	}
	public ActivitySheepHongBaoResponse() {
		super();

	}
	public Long getUnCheckNum() {
		return unCheckNum;
	}

	public void setUnCheckNum(Long unCheckNum) {
		this.unCheckNum = unCheckNum;
	}

	private Long unCheckNum;
	
}
