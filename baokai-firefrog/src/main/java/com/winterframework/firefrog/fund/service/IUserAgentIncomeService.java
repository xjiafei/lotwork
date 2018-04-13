package com.winterframework.firefrog.fund.service;

import java.util.List;

import com.winterframework.firefrog.fund.dao.vo.GameBettypeStatus;
import com.winterframework.firefrog.fund.dao.vo.UserAgentIncomeVO;
import com.winterframework.firefrog.fund.web.dto.UserAgentIncomeRequest;
import com.winterframework.firefrog.fund.web.dto.UserGameBettypeIncomeGroupDto;

/** 
* @ClassName IUserAgentIncomeService 
* @Description 总代盈亏报表 
* @author  hugh
* @date 2014年9月25日 上午11:23:54 
*  
*/
public interface IUserAgentIncomeService {
	
	/** 
	* @Title: getUserAgentIncomes 
	* @Description: 盈亏查询（包含下级）
	* @param req
	* @return
	*/
	List<UserAgentIncomeVO> getUserAgentIncomes(UserAgentIncomeRequest req);

	
	/** 
	* @Title: getUserAndAgentIncomes 
	* @Description: 盈亏查询（自己 和 下级）
	* @param req
	* @return
	*/
	public List<UserAgentIncomeVO> getUserAndAgentIncomes(UserAgentIncomeRequest request);
	
	
	/** 
	* @Title: getUserBetIncomes 
	* @Description: 用户玩法盈亏
	* @param req
	* @return
	*/
	List<UserGameBettypeIncomeGroupDto> getUserBetIncomes(UserAgentIncomeRequest req);
	
	
	//新增查詢報表 by userid
	List<GameBettypeStatus> getGameBetTypes(Long lotteryId);
	
	public Integer getUserAndAgentIncomesCount(UserAgentIncomeRequest req);
	
	//後台報表條件查詢
	List<UserAgentIncomeVO> getUserAndAgentReport(UserAgentIncomeRequest req);
	
	public Long getCurrentUserReportCount(UserAgentIncomeRequest req);
	
	//後台報表默認查詢 
	List<UserAgentIncomeVO> getCurrentUserReport(UserAgentIncomeRequest req);
	
	//查詢總代下級人數的總比數
	Long selectAllLowers(UserAgentIncomeRequest req);
	
	//查詢當天有紀錄的總代總人數
	Long getGeneralAgentCounts(UserAgentIncomeRequest req);
}
