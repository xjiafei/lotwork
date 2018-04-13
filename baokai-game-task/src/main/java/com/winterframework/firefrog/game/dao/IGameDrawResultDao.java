package com.winterframework.firefrog.game.dao;

import java.util.List;

import com.winterframework.firefrog.game.dao.vo.GameDrawResult;
import com.winterframework.firefrog.game.dao.vo.LotteryIdAndIssueCode;
import com.winterframework.orm.dal.ibatis3.BaseDao;

/** 
* @ClassName IGameDrawResultDao 
* @Description TODO 
* @author  hugh
* @date 2014年9月5日 下午2:20:09 
*  
*/
public interface IGameDrawResultDao extends BaseDao<GameDrawResult> {

	/**
	 * 
	* @Title: getnumberRecordByLotteryIdAndIssueCode 
	* @Description: 获取开奖信息
	* @param lotteryId
	* @param issueCode
	* @return
	* @throws Exception
	 */
	public String getnumberRecordByLotteryIdAndIssueCode(Long lotteryId, Long issueCode) throws Exception;

	/** 
	* @Title: getDrawResuleByLotteryIdAndIssueCode 
	* @Description:  根据彩种和期号查询当期中奖记录
	* @param lotteryId
	* @param issueCode
	* @return
	*/
	GameDrawResult getDrawResuleByLotteryIdAndIssueCode(Long lotteryId, Long issueCode);

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
	* @Title: getFollowDrawResuleByLotteryIdAndIssueCode 
	* @Description: 获取该奖期 以及后续99期开奖结果
	* @param lotteryId
	* @param issueCode
	* @return
	*/
	List<GameDrawResult> getFollowDrawResuleByLotteryIdAndIssueCode(Long lotteryId, Long issueCode); 
};
