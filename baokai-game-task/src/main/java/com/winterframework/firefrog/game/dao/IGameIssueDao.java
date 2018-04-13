package com.winterframework.firefrog.game.dao;

import java.util.Date;
import java.util.List;

import com.winterframework.firefrog.game.dao.vo.GameIssue;
import com.winterframework.firefrog.game.dao.vo.GameSeries;
import com.winterframework.firefrog.game.entity.GameIssueEntity;
import com.winterframework.orm.dal.ibatis3.BaseDao;

public interface IGameIssueDao extends BaseDao<GameIssue> {

	/**
	 * 
	* @Title: getGameIssueByLotteryIdAndIssueCode 
	* @Description: 获取奖期信息 
	* @param lotteryId
	* @param issueCode
	* @return
	* @throws Exception
	 */
	public GameIssueEntity getGameIssueByLotteryIdAndIssueCode(Long lotteryId, Long issueCode) throws Exception;
	GameIssue getByLotteryIdAndIssueCode(Long lotteryId, Long issueCode) throws Exception;

	/**
	 * 
	* @Title: getGameIssueByLotteryIdAndIssueCode 
	* @Description: 获取无锁定的奖期信息 
	* @param lotteryId
	* @param issueCode
	* @return
	* @throws Exception
	 */
	public GameIssueEntity queryGameIssueByWithoutLock(Long lotteryId, Long issueCode, int status) throws Exception;

	/**
	 * 
	* @Title: getGameIssueByLotteryIdAndStatus 
	* @Description: 获取未结束的奖期信息
	* @param lotteryId
	* @return
	* @throws Exception
	 */
	public List<GameIssueEntity> getGameIssueByLotteryIdAndStatus(Long lotteryId) throws Exception;
	
	/**
	 * 根据奖期状态(status)、獎期過程狀態(periodStatus)及開獎时间(openDrawTime)小於目前系統時間，奬期(issueCode)降冪排序條件，获取奖期列表。
	 * @param status 獎期狀態0:已生成(M1)、1:開始銷售(M2)、2:結束銷售(M3)、3:開獎號碼確認(M4)、4:計獎完成(M5)、5:驗獎完成(M6)、6:派獎完成(M7)、7:獎期結束(M8)、8:對賬結束(M9)
	 * @param periodStatus 獎期過程狀態；0:待銷售(P1)、1:銷售中(P2)、2:待開獎(P3)、3:計獎中(P4)、4:驗獎中(P5)、5:派獎中(P6)、6:待結束(P7)、7:待對賬(P8)
	 * @return 將 GameIssue 轉化為 GameIssueEntity
	 */
	public List<GameIssueEntity> getGameIssueByStatusAndOpenDrawTime(Long status, Long periodStatus);
	List<GameIssueEntity> queryGameIssueByStatusAndSalesStartTime(Long status, Long periodStatus,Date time);
	
	/** 
	* @Title: getNextGameIssueByTimeAndLotteryId 
	* @Description: 根据当前期的销售开始时间及彩种ID获取下个奖期 
	* @param @param lotteryid
	* @param @param date
	* @param @return    设定文件 
	* @return GameIssueEntity    返回类型 
	* @throws 
	*/
	public GameIssueEntity getNextGameIssueByTimeAndLotteryId(Long lotteryid, Date date);

	/**
	 * 
	* @Title: getPreGameIssueByLotteryIdAndIssueCode 
	* @Description:根据彩种id及期号，获取上一期奖期信息。
	* @param lotteryid
	* @param issueCode
	* @return
	 */
	public GameIssueEntity getPreGameIssueByLotteryIdAndIssueCode(Long lotteryid, Long issueCode);

	/**
	 * 
	* @Title: queryGameIssuesByIssueCodes 
	* @Description: 根据奖期范围查询对应的奖期信息列表
	* @param lotteryid
	* @param startIssueCode
	* @param endIssueCode
	* @return
	* @throws Exception
	 */
	public List<GameIssueEntity> queryGameIssuesByIssueCodes(Long lotteryid, Long startIssueCode, Long endIssueCode)
			throws Exception;

	/**
	 * 
	* @Title: updateGameIssueLastIssueStop 
	* @Description: 更新奖期lastIssueStop状态 
	* @param lotteryid
	* @param issueCode
	* @param lastIssueStop
	* @throws Exception
	 */
	public void updateGameIssueLastIssueStop(Long lotteryid, Long issueCode, Integer lastIssueStop) throws Exception;

	/** 
	* @Title: getGameIssueByStatus 
	* @Description: 根据奖期状态及奖期过程状态查询奖期
	* @param @param status
	* @param @param periodStatus
	*/
	public List<GameIssueEntity> getGameIssueByStatus(Long status, Long periodStatus);

	/** 
	* @Title: generateGameIssue 
	* @Description: 彩种奖期生成 
	* @param gs
	*/
	public void generateGameIssue(GameSeries gs);

	/** 
	* @Title: getEarlierSuspendedGameIssue 
	* @Description: 获取期号更小的暂停奖期 
	*/
	public List<GameIssue> getEarlierSuspendedGameIssue(Long lotteryId, Long issueCode);

	/** 
	* @Title: updateGameIssue 
	* @Description: 更新
	* @param gameIssue
	*/
	public void updateGameIssue(GameIssue gameIssue) throws Exception;
	void updataGameIssueSaleEnd(GameIssue gameIssue) throws Exception;
	int updataGameIssueSaleStart(GameIssue gameIssue, Integer preStatus) throws Exception;
	public GameIssueEntity getNextGameIssueByIssueAndLotteryId(Long lotteryId, Long issueCode);
	GameIssue getNextByLotteryIdAndIssueCode(Long lotteryId, Long issueCode);
	public GameIssueEntity queryGameIssue(Long lotteryid, Long issueCode);
	public GameIssueEntity queryGameIssue(Long lotteryid, String webIssueCode);
	
	GameIssue getGameIssueByLotteryIssue(Long lotteryId, Long issueCode) throws Exception;

	/**
	 * 
	* @Title: getNextGameIssueByPlanIdAndIssueCode 
	* @Description: 根据PlanId获取下一期奖期信息。
	* @param currentIssuCode
	* @param planId
	* @return
	 */
	public GameIssueEntity getNextGameIssueByPlanIdAndIssueCode(Long currentIssuCode, Long planId);

	/** 
	* @Title: queryGameIssueByStatusAndSalesEndTimeForBeginSaleStart 
	* @Description: 每一个彩种类型获取一条奖期最小的记录
	* @param status
	* @param periodStatus
	* @return
	*/
	public List<GameIssueEntity> queryGameIssueByStatusAndSalesEndTimeForBeginSaleStart(Long status, Long periodStatus);


	List<GameIssue> getNoAwardNumberSaleEndIssue(Date time);
	
	/**
	 * 获取奖期（根据彩种和日期）
	 * @param lotteryId
	 * @param day （字符串:20141022）
	 * @return
	 * @throws Exception
	 */
	public List<GameIssueEntity> getGameIssueByLotteryIdAndDay(Long lotteryId,String startDay,String endDay) throws Exception;

	/** 
	* @Title: updateTry 
	* @Description: 更新主动获取开奖号码次数
	* @param count
	* @param id
	*/
	void updateTry(Long count ,Long id) ;
	
	public GameIssueEntity getSsqGameIssueForMail(Date saleEndDate) throws Exception;
	
	public String getUserAccount(Long userId);
	/**
	 * 获取未获取开奖号码的奖期
	 * @param lotteryId
	 * @param curTime 当前时间
	 * @return
	 * @throws Exception
	 */
	public List<GameIssue> getNoNumberLatestByLotteryIdAndTime(Long lotteryId,Date time) throws Exception;
	public String getGameIssueLastWebIssueCode(Long lotteryId,Date date) throws Exception;
}
