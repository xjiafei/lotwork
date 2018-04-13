package com.winterframework.firefrog.game.service;

import java.util.Date;
import java.util.List;

import com.winterframework.firefrog.game.dao.vo.GameDrawResult;
import com.winterframework.firefrog.game.dao.vo.GameWarnIssueLog;
import com.winterframework.firefrog.game.entity.GameHelpEntity;
import com.winterframework.firefrog.game.entity.GameIssueEntity;

/** 
* @ClassName: IGameDrawService 
* @Description: 历史开奖号码相关统计Service接口 
* @author Denny 
* @date 2013-7-22 下午3:52:14 
* 
*/
public interface IGameDrawService {

	public GameDrawResult getDrawResuleByLotteryIdAndIssueCode(Long lotteryId, Long issueCode);

	/**
	 * 
	* @Title: queryGameHelpByLotteryId 
	* @Description: 投注方式查询
	* @param lotteryId
	* @return
	* @throws Exception
	 */
	public List<GameHelpEntity> queryGameHelpByLotteryId(Long lotteryId) throws Exception;

	public List<GameDrawResult> getAllByLotteryIdAndCountIssue(Long lotteryId, Integer count) throws Exception;

	public List<GameDrawResult> getDrawResultByDate(Long lotteryId, Date startDate, Date endDate) throws Exception;

	//输入开奖号码  2
	void addDrawResult(Long lotteryid, Long issueCode, String numberRecord, GameWarnIssueLog warnIssueLog,
			Date ecVerifiedTime, String memo) throws Exception;

	//异常处理之输入开奖号码	12
	void inputNumberDrawResult(Long lotteryid, Long issueCode, String numberRecord, GameWarnIssueLog warnIssueLogec,
			Date ecVerifiedTime) throws Exception;

	//输入官方实际开奖号码 6
	void modifyDrawResult(Long lotteryid, Long issueCode, String numberRecord, GameWarnIssueLog warnIssueLog,Date ecVerifiedTime)
			throws Exception;

	//输入官方实际开奖号码  
	void modifyDrawResult(Long lotteryid, Long issueCode, String numberRecord, GameWarnIssueLog warnIssueLog, Long type,Date ecVerifiedTime)
			throws Exception;

	//接收提前开奖 销售截止前
	void receivedAwardNumberBeforeSaleTime(Long lotteryid, Long issueCode, String numberRecord,
			GameWarnIssueLog warnIssueLog, Date ecVerifiedTime) throws Exception;

	//接收提前开奖 理论开奖前
	void receivedAwardNumberBeforeSaleTimeNotCare(Long lotteryid, Long issueCode, String numberRecord,
			GameWarnIssueLog warnIssueLog, Date ecVerifiedTime) throws Exception;

	//接收不开奖
	void receivedSystemCancelAward(Long lotteryid, Long issueCode, String numberRecord, GameWarnIssueLog warnIssueLog)
			throws Exception;

	//接收並存檔開獎 待銷售關閉後執行 15
	void saveDrawECResultEvent(Long lotteryId, String gameWebIssueCode, String params)
			throws Exception;
	void saveDrawZKResultEvent(Long lotteryId, String gameWebIssueCode, String params) throws Exception;

}
