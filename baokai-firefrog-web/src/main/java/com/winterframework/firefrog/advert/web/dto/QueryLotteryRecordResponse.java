package com.winterframework.firefrog.advert.web.dto;

import java.io.Serializable;
import java.util.List;

public class QueryLotteryRecordResponse implements Serializable {
	
	private static final long serialVersionUID = -7501991077456652352L;
	
	private List<LotteryRecordStruc> list;

	public QueryLotteryRecordResponse() {

	}

	public List<LotteryRecordStruc> getList() {
		return list;
	}

	public void setList(List<LotteryRecordStruc> list) {
		this.list = list;
	}

}
