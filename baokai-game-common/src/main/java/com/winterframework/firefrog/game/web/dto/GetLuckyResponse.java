package com.winterframework.firefrog.game.web.dto;

import java.io.Serializable;
import java.util.Date;

public class GetLuckyResponse implements Serializable{
	
	private static final long serialVersionUID = -5966519327729974866L;
	
	private Long luckyId;
	
	private String desc;
	
	private Date date;

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Long getLuckyId() {
		return luckyId;
	}

	public void setLuckyId(Long luckyId) {
		this.luckyId = luckyId;
	}
	
}
