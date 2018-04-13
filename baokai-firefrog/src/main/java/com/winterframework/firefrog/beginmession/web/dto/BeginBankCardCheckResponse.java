package com.winterframework.firefrog.beginmession.web.dto;

import java.io.Serializable;
import java.util.List;

import com.winterframework.firefrog.beginmession.entity.BeginBankCardCheckData;

public class BeginBankCardCheckResponse implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7983681752167112819L;
	
	private List<BeginBankCardCheckData> checkDatas;
	
	private String timeStart;
	
	private String timeEnd;

	public String getTimeStart() {
		return timeStart;
	}

	public void setTimeStart(String timeStart) {
		this.timeStart = timeStart;
	}

	public String getTimeEnd() {
		return timeEnd;
	}

	public void setTimeEnd(String timeEnd) {
		this.timeEnd = timeEnd;
	}

	public List<BeginBankCardCheckData> getCheckDatas() {
		return checkDatas;
	}

	public void setCheckDatas(List<BeginBankCardCheckData> checkDatas) {
		this.checkDatas = checkDatas;
	}
	
}
