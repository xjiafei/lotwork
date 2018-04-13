package com.winterframework.firefrog.advert.web.dto.sheep;

import java.util.List;

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
