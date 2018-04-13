package com.winterframework.firefrog.beginmession.web.dto;

import java.io.Serializable;
import java.util.List;

import com.winterframework.firefrog.beginmession.dao.vo.BeginLotterySet;

public class BeginLotterySetWebRequest implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1273764173029501358L;

	
	private List<BeginLotterySet> beginLotterySets;

	private String userName;

	public List<BeginLotterySet> getBeginLotterySets() {
		return beginLotterySets;
	}


	public void setBeginLotterySets(List<BeginLotterySet> beginLotterySets) {
		this.beginLotterySets = beginLotterySets;
	}


	public String getUserName() {
		return userName;
	}


	public void setUserName(String userName) {
		this.userName = userName;
	}
	
}
