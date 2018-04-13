package com.winterframework.firefrog.game.web.dto;

import java.io.Serializable;
import java.util.List;

public class QueryLotteryResultResponse implements Serializable {

	private static final long serialVersionUID = 7565663294657611576L;
	private List<LotteryResultStruc> list;

	public List<LotteryResultStruc> getList() {
		return list;
	}

	public void setList(List<LotteryResultStruc> list) {
		this.list = list;
	}

}
