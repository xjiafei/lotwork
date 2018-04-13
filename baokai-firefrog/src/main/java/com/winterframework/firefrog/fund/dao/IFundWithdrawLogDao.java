package com.winterframework.firefrog.fund.dao;

import java.util.List;

import com.winterframework.firefrog.fund.dao.vo.FundWithdrawLog;
import com.winterframework.orm.dal.ibatis3.BaseDao;

/**
 * 
 * @ClassName IFundWithdrawLogDao.java
 * @Description 提現歷程Dao
 * @author Ami.Tsai
 * @date 2015年12月21日
 *
 */
public interface IFundWithdrawLogDao extends BaseDao<FundWithdrawLog>{
	
	/**
	 * 根據提現單號,查出歷程
	 * @param withdrawSn
	 * @return
	 */
	public List<FundWithdrawLog> getLogs(String withdrawSn);
}
