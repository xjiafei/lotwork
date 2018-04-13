package com.winterframework.firefrog.beginmession.web.dto;

import java.io.Serializable;
import java.util.List;

import com.winterframework.firefrog.beginmession.dao.vo.BeginBankCardCheckData;

public class BeginBankCardCheckWebResponse implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8261340746168112226L;
	
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
