package com.winterframework.firefrog.fund.dao;

import java.util.List;

import com.winterframework.firefrog.fund.dao.vo.GameBettypeStatus;
import com.winterframework.firefrog.fund.dao.vo.UserAgentIncomeVO;
import com.winterframework.firefrog.fund.dao.vo.UserGameBettypeIncome;
import com.winterframework.firefrog.fund.web.dto.UserAgentIncomeRequest;
import com.winterframework.orm.dal.ibatis3.BaseDao;


/** 
* @ClassName IUserAgentIncomeDao 
* @Description 总代盈亏 
* @author  hugh
* @date 2014年9月23日 下午3:57:04 
*  
*/
public interface IUserAgentIncomeDao  extends BaseDao<UserAgentIncomeVO>{
	

	/** 
	* @Title: getUserAgentIncomes 
	* @Description: 代理盈亏查询（包含下级）
	* @param req
	* @return
	*/
	List<UserAgentIncomeVO> getUserAgentIncomes(UserAgentIncomeRequest req);
	
	/** 
	* @Title: getUserBetIncomes 
	* @Description: 用户玩法盈亏
	* @param req
	* @return
	*/
	List<UserAgentIncomeVO> getUserIncomes(UserAgentIncomeRequest req);
	
	/** 
	* @Title: getUserBetIncomes 
	* @Description: 用户玩法盈亏
	* @param req
	* @return
	*/
	List<UserGameBettypeIncome> getUserBetIncomes(UserAgentIncomeRequest req);
	
	
	/** 
	* @Title: getGameBetTypes 
	* @Description: 根据彩种获得彩种玩法列表
	* @param lotteryId
	* @return
	*/
	List<GameBettypeStatus> getGameBetTypes(Long lotteryId);
	
	//後台報表條件查詢
	List<UserAgentIncomeVO> getUserAndAgentReport(UserAgentIncomeRequest req);
	
	public UserAgentIncomeVO getAgentReportListByOwn(UserAgentIncomeRequest req);
	
	public Long getAgentReportCount(UserAgentIncomeRequest req);
	
	public List<UserAgentIncomeVO> getAgentReportList(UserAgentIncomeRequest req);
	
	//後台報表條件查詢(個人及下級總和報表)
	List<UserAgentIncomeVO> getAgentReport(UserAgentIncomeRequest req);
	
	//後台報表默認查詢 
	List<UserAgentIncomeVO> getCurrentUserReport(UserAgentIncomeRequest req);
	
	//查詢當天有紀錄的總代
	List<String> getGeneralAgents(UserAgentIncomeRequest req,Integer startNo,Integer endNo);
	
	//查詢玩家相關資料
	UserAgentIncomeVO queryUserIncomes(UserAgentIncomeRequest req);
	
	//查詢下級玩家總比數
	Long selectAllLowers(UserAgentIncomeRequest req);
	
	//查詢當天有紀錄的總代總人數
	Long getGeneralAgentCounts(UserAgentIncomeRequest req);
	
	//查詢下級玩家
	List<String> selectSubLowers(UserAgentIncomeRequest req,  long startNo, long endNo);
		
	
	//查詢總代自己的
	List<UserAgentIncomeVO> getAgentIncome(UserAgentIncomeRequest req);
}
