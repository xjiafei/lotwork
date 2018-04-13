package com.winterframework.firefrog.phone.web.cotroller.dto;

import java.io.Serializable;
import java.util.List;

public class OpenLinkDetailResponse implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -761085101219775144L;
	private String start;	//链结有效开始时间
	private String end;//	链结有效结束时间
	private List<Keys> key;//	彩种分类(highGame、lowGame、soccerGame)
	public String getStart() {
		return start;
	}
	public void setStart(String start) {
		this.start = start;
	}
	public String getEnd() {
		return end;
	}
	public void setEnd(String end) {
		this.end = end;
	}
	public List<Keys> getKey() {
		return key;
	}
	public void setKey(List<Keys> key) {
		this.key = key;
	}

}
