package com.winterframework.firefrog.game.service.bean;

import java.util.ArrayList;
import java.util.List;

import com.winterframework.firefrog.game.dao.vo.FundGameVos;
import com.winterframework.firefrog.game.dao.vo.GameTransactionFund;

public class GameFundServiceBean {
	
	private List<FundGameVos> fundList = new ArrayList<FundGameVos>();
	
	private List<GameTransactionFund> trans = new ArrayList<GameTransactionFund>();

	public List<FundGameVos> getFundList() {
		return fundList;
	}

	public void setFundList(List<FundGameVos> fundList) {
		this.fundList = fundList;
	}

	public List<GameTransactionFund> getTrans() {
		return trans;
	}

	public void setTrans(List<GameTransactionFund> trans) {
		this.trans = trans;
	}
	
	
}
