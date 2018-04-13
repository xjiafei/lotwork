package com.winterframework.firefrog.phone.web.cotroller.dto;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class QuickInitResponse implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5506230557780735709L;

	private List<QuickInitDto> list;
	private Map<String,String> map;
	private Long isvip;
	
	public Long getIsvip() {
		return isvip;
	}

	public void setIsvip(Long isvip) {
		this.isvip = isvip;
	}

	public Map<String, String> getMap() {
		return map;
	}

	public void setMap(Map<String, String> map) {
		this.map = map;
	}

	public List<QuickInitDto> getList() {
		return list;
	}

	public void setList(List<QuickInitDto> list) {
		this.list = list;
	}
	

}
