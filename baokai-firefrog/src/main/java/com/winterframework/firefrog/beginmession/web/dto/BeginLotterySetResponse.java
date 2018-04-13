package com.winterframework.firefrog.beginmession.web.dto;

import java.io.Serializable;
import java.util.List;

import com.winterframework.firefrog.beginmession.dao.vo.BeginLotterySet;

public class BeginLotterySetResponse implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7948246704716871853L;
	
	private List<BeginLotterySet> beginLotterySets;

	public List<BeginLotterySet> getBeginLotterySets() {
		return beginLotterySets;
	}

	public void setBeginLotterySets(List<BeginLotterySet> beginLotterySets) {
		this.beginLotterySets = beginLotterySets;
	}

}
