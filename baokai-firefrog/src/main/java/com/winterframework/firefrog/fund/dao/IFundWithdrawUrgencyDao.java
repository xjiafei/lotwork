package com.winterframework.firefrog.fund.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.winterframework.firefrog.fund.dao.vo.FundWithdrawUrgency;
import com.winterframework.orm.dal.ibatis3.BaseDao;

public interface IFundWithdrawUrgencyDao extends BaseDao<FundWithdrawUrgency>{

	/**
	 * 根據提現單號日期,查詢該日期後相關log
	 * @param withdrawSn
	 * @return
	 */
	public List<FundWithdrawUrgency> getUrgenctAfterTime(Date date);
	
	/**
	 * 根據提現單號日期,完結狀態時間,查詢該日期間log
	 * @param withdrawSn
	 * @return
	 */
	public List<FundWithdrawUrgency> getUrgenctBetweenTime(Map<String,Date> map);
	
}
