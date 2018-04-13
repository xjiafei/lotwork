package com.winterframework.firefrog.shortlived.sheepactivity.dto;

import java.util.List;

import com.winterframework.firefrog.shortlived.sheepactivity.dao.vo.ActivitySheepBigLittle;
import com.winterframework.firefrog.shortlived.sheepactivity.dao.vo.ActivitySheepOperateLog;

public class ActivitySheepOperateResponse {
	private List<ActivitySheepOperateLog> list ;

	public List<ActivitySheepOperateLog> getList() {
		return list;
	}

	public void setList(List<ActivitySheepOperateLog> list) {
		this.list = list;
	}
	
	public ActivitySheepOperateResponse(){
		super();
	}

	public ActivitySheepOperateResponse(List<ActivitySheepOperateLog> list) {
		super();
		this.list = list;
		
	}
	

	
}
