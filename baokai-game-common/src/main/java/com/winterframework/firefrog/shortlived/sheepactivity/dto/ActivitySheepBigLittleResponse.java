package com.winterframework.firefrog.shortlived.sheepactivity.dto;

import java.util.List;

import com.winterframework.firefrog.shortlived.sheepactivity.dao.vo.ActivitySheepBigLittle;

public class ActivitySheepBigLittleResponse {
	private List<ActivitySheepBigLittle> list ;

	public List<ActivitySheepBigLittle> getList() {
		return list;
	}

	public void setList(List<ActivitySheepBigLittle> list) {
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
