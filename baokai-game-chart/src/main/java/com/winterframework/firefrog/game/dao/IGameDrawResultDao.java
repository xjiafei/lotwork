package com.winterframework.firefrog.game.dao;

import java.util.Date;
import java.util.List;

import com.winterframework.firefrog.game.dao.vo.GameDrawResult;
import com.winterframework.firefrog.game.dao.vo.LotteryIdAndIssueCode;
import com.winterframework.orm.dal.ibatis3.BaseDao;

public interface IGameDrawResultDao extends BaseDao<GameDrawResult> {

	/** 
	* @Title: getAllByLotteryIdAndCountIssue 
	* @Description: 根据彩种和期数查询历史中奖号码 
	*/
	public List<GameDrawResult> getAllByLotteryIdAndCountIssue(Long lotteryId, Integer countIssue);

	public List<GameDrawResult> getDrawResultByDate(Long lotteryId, Date startDate, Date endDate) throws Exception;

	/** 
	* @Title: getAllLotteryIdAndIssueCode 
	* @Description: 获取所有彩种ID和期号 
	* @throws Exception    设定文件 
	* @return List<LotteryIdAndIssueCode>    返回类型 
	* @throws 
	*/
	List<LotteryIdAndIssueCode> getAllLotteryIdAndIssueCode() throws Exception;
	
	/** 
	* @Title: getDrawResuleByLotteryIdAndIssueCode 
	* @Description:  根据彩种和期号查询下一期中奖记录
	* @param lotteryId
	* @param issueCode
	* @return
	*/
	GameDrawResult getNextDrawResuleByLotteryIdAndIssueCode(Long lotteryId, Long issueCode);
	
	/** 
	* @Title: getAllByLotteryId 
	* @Description: 根据彩种历史中奖号码 
	*/
	public List<GameDrawResult> getAllByLotteryId(long lotteryId) throws Exception;

}
