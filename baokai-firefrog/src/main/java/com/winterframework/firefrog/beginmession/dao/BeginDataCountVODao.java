package com.winterframework.firefrog.beginmession.dao;

import java.util.Date;

import com.winterframework.firefrog.beginmession.dao.vo.BeginDataCountVO;
import com.winterframework.orm.dal.ibatis3.BaseDao;

public interface BeginDataCountVODao extends BaseDao<BeginDataCountVO> {

	Long getMissionCount(Date startTime,Date endTime);
	
	Long getBindCardAward(Date startTime,Date endTime);
	
	Long getCharegCount(Date startTime,Date endTime);
	
	Long getWithdrawCount(Date startTime,Date endTime);
	
	Double getTotalCharegAmt(Date startTime,Date endTime);
	
	Long getAwardLotteryCount(Date startTime,Date endTime);
	
	Long getAwardTotalAmt(Date startTime,Date endTime);
	
	Long getAwardLotteryTime(Date startTime,Date endTime);
	
	Double getTotalOrderAmt(Date startTime,Date endTime);
}
