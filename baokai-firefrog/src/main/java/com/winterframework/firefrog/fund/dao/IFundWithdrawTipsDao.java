package com.winterframework.firefrog.fund.dao;

import java.util.List;

import com.winterframework.firefrog.fund.dao.vo.FundWithdrawTips;
import com.winterframework.orm.dal.ibatis3.BaseDao;

public interface IFundWithdrawTipsDao extends BaseDao<FundWithdrawTips>{

	
	/**
	 *查詢提現設定
	 * @param withdrawSn
	 * @return
	 */
	public List<FundWithdrawTips> getTips(FundWithdrawTips tips);
	
	public void deleteByCondition(FundWithdrawTips tips);
	
	public Integer getGroupBCount(FundWithdrawTips tips);
	
}
