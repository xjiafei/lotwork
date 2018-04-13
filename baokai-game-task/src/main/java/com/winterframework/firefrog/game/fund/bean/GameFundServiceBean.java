package com.winterframework.firefrog.game.fund.bean;

import java.util.ArrayList;
import java.util.List;
import com.winterframework.firefrog.game.fund.ff.bean.FundGameVo;

public class GameFundServiceBean {
	
	private List<FundGameVo> fundList = new ArrayList<FundGameVo>(); 

	public List<FundGameVo> getFundList() {
		return fundList;
	}

	public void setFundList(List<FundGameVo> fundList) {
		this.fundList = fundList;
	} 
	
	
}
