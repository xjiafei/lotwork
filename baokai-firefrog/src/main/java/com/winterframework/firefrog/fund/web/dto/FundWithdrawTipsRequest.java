package com.winterframework.firefrog.fund.web.dto;

import java.io.Serializable;
import java.util.List;

import com.winterframework.firefrog.fund.dao.vo.FundWithdrawTips;

/**
 * 
 * @ClassName FundWithdrawTipRequest.java
 * @Description 
 * @author Ami.Tsai
 * @date 2015年12月23日
 *
 */
public class FundWithdrawTipsRequest implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6957027095979255920L;

	private List<FundWithdrawTips> tipsList;

	private FundWithdrawTips tips;


	public FundWithdrawTips getTips() {
		return tips;
	}

	public void setTips(FundWithdrawTips tips) {
		this.tips = tips;
	}

	public List<FundWithdrawTips> getTipsList() {
		return tipsList;
	}

	public void setTipsList(List<FundWithdrawTips> tipsList) {
		this.tipsList = tipsList;
	}

}
