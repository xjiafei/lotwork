package com.winterframework.firefrog.fund.service;

import java.util.List;

import com.winterframework.firefrog.fund.dao.vo.FundWithdrawTips;
import com.winterframework.firefrog.fund.web.dto.FundWithdrawTipsResponse;

public interface IFundWithdrawTipsService {

	public void deleteTipsAndAddTipsList(List<FundWithdrawTips> tipsList , FundWithdrawTips deleteCondition) throws Exception;
	
	public List<FundWithdrawTips> searchTipsByCondition(FundWithdrawTips tips);
	
	public Integer getGroupBCount(FundWithdrawTips tips);
	
	public void insertTip(FundWithdrawTips tip) throws Exception;
	
	public FundWithdrawTipsResponse processDrawTipsData(FundWithdrawTipsResponse response , List<FundWithdrawTips> uncheckDrawTips ,List<FundWithdrawTips> reviewDrawTips);
}
