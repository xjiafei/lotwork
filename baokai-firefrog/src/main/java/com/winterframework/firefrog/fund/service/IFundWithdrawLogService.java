package com.winterframework.firefrog.fund.service;

import java.util.Date;
import java.util.List;

import com.winterframework.firefrog.fund.dao.vo.FundWithdraw;
import com.winterframework.firefrog.fund.dao.vo.FundWithdrawLog;
import com.winterframework.firefrog.fund.dao.vo.FundWithdrawUrgency;
import com.winterframework.firefrog.fund.enums.FundLogEnum.LogModel;
import com.winterframework.firefrog.fund.enums.FundLogEnum.STATUS;

/**
 * 
 * @ClassName IFundWithdrawLogService.java
 * @Description 
 * @author Ami.Tsai
 * @date 2015年12月22日
 *
 */
public interface IFundWithdrawLogService {

	
	public void addLog(FundWithdrawLog log);
	
	public List<FundWithdrawLog> searchLogs(String withdrawSn);
	
	public List<String> mergeLogByTime(List<FundWithdrawLog> logs,List<FundWithdrawUrgency> urgencys);
	
	public FundWithdraw getFundWithdrawById(Long id);

	public FundWithdraw getFundWithdrawByMowNum(String sn) throws Exception;
	
	public void saveFundWithLog(String sn , LogModel logModel, Date createTime , STATUS status);
	
	public void saveFundWithLogWithDetail(String sn , LogModel logModel, Date createTime , String detail);
	
}
