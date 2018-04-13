package com.winterframework.firefrog.phone.web.cotroller.dto;

import java.io.Serializable;
import java.util.List;

public class TaskListResponse implements Serializable  {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5401768946803013407L;
	private List<TaskListDto> list;

	public List<TaskListDto> getList() {
		return list;
	}

	public void setList(List<TaskListDto> list) {
		this.list = list;
	}
	

}
