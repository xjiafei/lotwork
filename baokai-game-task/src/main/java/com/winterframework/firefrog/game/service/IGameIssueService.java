package com.winterframework.firefrog.game.service;

import java.util.Date;
import java.util.List;

import com.winterframework.firefrog.common.util.GameContext;
import com.winterframework.firefrog.game.dao.vo.GameIssue;
import com.winterframework.firefrog.game.dao.vo.GameWarnIssueLog;
import com.winterframework.firefrog.game.entity.GameIssueEntity;

public interface IGameIssueService {

	/**
	 * 
	* @Title: queryGameIssueByLotteryIdAndIssueCode 
	* @Description: 查询奖期信息
	* @param lotteryId
	* @param issueCode
	* @return GameIssueEntity  奖期实体Bean
	* @throws Exception
	 */
	public GameIssueEntity queryGameIssueByLotteryIdAndIssueCode(Long lotteryId, Long issueCode) throws Exception;
	GameIssue  getByLotteryIdAndIssueCode(Long lotteryId, Long issueCode) throws Exception;
 
	/**
	 * 
	* @Title: checkGameIssueStatus 
	* @Description: 判断奖期状态 
	* @param lotteryId
	* @param issueCode
	* @return 
	* @throws Exception
	 */
	public boolean checkGameIssueStatus(Long lotteryId, Long issueCode) throws Exception;

	/**
	 * 
	* @Title: checkGameIssueStatus 
	* @Description: 判断奖期状态 
	* @param lotteryId
	* @param issueCode
	* @return 
	* @throws Exception
	 */
	public boolean checkGameIssueStatus(GameIssueEntity entity) throws Exception;

	/**
	 * 
	* @Title: lockGameIssue 
	* @Description: 锁定奖期
	* @param entity  奖期实体Bean
	* @throws Exception
	 */
	public void lockGameIssue(GameIssueEntity entity) throws Exception;

	/**
	 * 
	* @Title: unLockGameIssue 
	* @Description: 奖期解锁
	* @param entity  奖期实体Bean
	* @throws Exception
	 */
	public void unLockGameIssue(GameIssueEntity entity) throws Exception;

	/**
	 * 根据奖期状态(status)、獎期過程狀態(periodStatus)及開獎时间(openDrawTime)小於目前系統時間，奬期(issueCode)降冪排序條件，获取奖期列表。
	 * @param status 奖期状态；0:已生成(M1)、1:開始銷售(M2)、2:結束銷售(M3)、3:開獎號碼確認(M4)、4:計獎完成(M5)、5:驗獎完成(M6)、6:派獎完成(M7)、7:獎期結束(M8)、8:對賬結束(M9)
	 * @param periodStatus 奖期过程状态；0:待銷售(P1)、1:銷售中(P2)、2:待開獎(P3)、3:計獎中(P4)、4:驗獎中(P5)、5:派獎中(P6)、6:待結束(P7)、7:待對賬(P8)
	 * @return
	 * @throws Exception
	 */
	public List<GameIssueEntity> queryGameIssueByStatusAndOpenDrawTime(Long status, Long periodStatus) throws Exception;
	
	List<GameIssueEntity> queryGameIssueByStatusAndSalesStartTime(Long status, Long periodStatus,Date time) throws Exception;
	
	/**
	 * 判断奬期是否为暂停状态。
	 * @param entity 奖期实体Bean
	 * @return
	 * @throws Exception
	 */
	public boolean isGameIssuePause(GameIssueEntity entity) throws Exception;

	/**
	 * 判断是否为锁定状态。
	 * @param entity 奖期实体Bean
	 * @return
	 * @throws Exception
	 */
	public boolean isGameIssueLocked(GameIssueEntity entity) throws Exception;

	/**
	 * 更新奖金结构(awardStruct)、管理员撤单时间(adminEndCancelTime)、奖期状态(status)、奖期过程状态(periodStatus)、更新时间(updateTime)、管理员可撤销时间(如果 entity.issuewarnExceptionTime 非空值)。
	 * @param entity 奖期
	 * @param status 奖期状态；0:已生成(M1)、1:開始銷售(M2)、2:結束銷售(M3)、3:開獎號碼確認(M4)、4:計獎完成(M5)、5:驗獎完成(M6)、6:派獎完成(M7)、7:獎期結束(M8)、8:對賬結束(M9)
	 * @param periodStatus 奖期过程状态；0:待銷售(P1)、1:銷售中(P2)、2:待開獎(P3)、3:計獎中(P4)、4:驗獎中(P5)、5:派獎中(P6)、6:待結束(P7)、7:待對賬(P8)
	 * @throws Exception
	 */
	public void updataGameIssue(GameIssueEntity entity, Long status, Long preiodStatus) throws Exception;
	
	/**
	 * 更新奖金结构(awardStruct)、奖期状态(status)、奖期过程状态(periodStatus)、更新时间(updateTime)。
	 * @param entity 
	 * @param status 奖期状态；0:已生成(M1)、1:開始銷售(M2)、2:結束銷售(M3)、3:開獎號碼確認(M4)、4:計獎完成(M5)、5:驗獎完成(M6)、6:派獎完成(M7)、7:獎期結束(M8)、8:對賬結束(M9)
	 * @param periodStatus 奖期过程状态；0:待銷售(P1)、1:銷售中(P2)、2:待開獎(P3)、3:計獎中(P4)、4:驗獎中(P5)、5:派獎中(P6)、6:待結束(P7)、7:待對賬(P8)
	 * @throws Exception
	 */
	void updataGameIssueSaleEnd(GameIssueEntity entity, Long status, Long preiodStatus) throws Exception;
	int updataGameIssueSaleStart(GameIssue issue) throws Exception;
	/** 
	* @Title: queryNextGameIssue 
	* @Description: 取得下一个奖期 
	* @param e 当前江青
	* @return GameIssueEntity 下一个奖期
	* @throws 
	*/
	public GameIssueEntity queryNextGameIssue(GameIssueEntity e);
	GameIssue getNextByLotteryIdAndIssueCode(Long lotteryId,Long issueCode);

	/** 
	* @Title: exportOrdersData 
	* @Description: 导出该期订单数据 
	* @param issueCode
	* @return void
	* @throws 
	*/
	public void exportOrdersData(Long issueCode);

	/**
	 * 
	* @Title: getGameIssueByLotteryIdAndStatus 
	* @Description:获取未结束的奖期信息
	* @param lotteryId 
	* @return List<GameIssueEntity> 
	* @throws Exception
	 */
	public List<GameIssueEntity> getGameIssueByLotteryIdAndStatus(Long lotteryId) throws Exception;

	/**
	 * 
	* @Title: queryPreGameIssue 
	* @Description: 获取上一期奖期信息。
	* @param gameIssue 当前奖期
	* @return GameIssueEntity 上一个奖期
	* @throws Exception
	 */
	public GameIssueEntity queryPreGameIssue(GameIssueEntity gameIssue) throws Exception;

	/**
	 * 
	* @Title: update 
	* @Description: 更新奖期
	* @param nextGameIssue
	* @throws Exception
	 */
	public void updateLastIssueStop(GameIssueEntity nextGameIssue) throws Exception;

	/**
	 * 
	* @Title: queryGameIssueByStatus 
	* @Description: 根据奖期状态及奖期过程状态查询奖期 
	* @param status 奖期状态
	* @param periodStatus 奖期过程状态
	* @return List<GameIssueEntity> 
	* @throws
	 */
	public List<GameIssueEntity> queryGameIssueByStatus(Long status, Long periodStatus);

	/**
	 * 
	* @Title: calculateGameIssueFinishTime 
	* @Description: 根据彩种ID及奖期实际开奖时间计算奖期结束时间 
	* @param lotteryId 彩种ID
	* @param factionDrawTime 奖期实际开奖时间
	* @throws Exception
	* @return Long
	* @throws
	 */
	public Long calculateGameIssueFinishTime(Long lotteryId, Date factionDrawTime) throws Exception;

	/**
	 * 
	* @Title: isLastIssueStop 
	* @Description: 判断上个奖期是否暂停 
	* @param e
	* @return boolean 
	* @throws
	 */
	public boolean isLastIssueStop(GameIssueEntity e);

	/**
	 * 
	* @Title: isLastIssuePlanNotFinished 
	* @Description: 判断上期是否有未完成计划 
	* @param e
	* @return boolean
	* @throws
	 */
	public boolean isLastIssuePlanNotFinished(GameIssueEntity e);

	/** 
	* @Title: generateGameIssue 
	* @Description: 奖期自动生成 
	*/
	public void generateGameIssue();

	/**
	 * 
	* @Title: isEarlierSuspendedGameIssueExist 
	* @Description: 判断是否存在奖期号更小的暂停奖期
	* @param lotteryId
	* @param issueCode
	* @return boolean
	* @throws
	 */
	public boolean isEarlierSuspendedGameIssueExist(Long lotteryId, Long issueCode);

	/**
	 * 
	* @Title: updataGameIssuePauseStatus 
	* @Description: 更新奖期的暂停状态 
	* @param gameIssueEntity
	* @param i 暂停状态值 
	* @return     
	* @throws
	 */
	public void updataGameIssuePauseStatus(GameIssueEntity gameIssueEntity, int i);

	/**
	 * 根据彩种Id和期号获取不锁定的奖期信息。
	* @Title: queryGameIssueByLotteryIdAndIssueCode 
	* @Description:获取奖期信息
	* @param lotteryId
	* @param issueCode
	* @return
	* @throws Exception
	 */
	public GameIssueEntity queryGameIssueByWithoutLock(Long lotteryId, Long issueCode, int status) throws Exception;

	/** 
	* @Title: queryGameIssueByStatusAndSalesEndTimeForBeginSaleStart 
	* @Description: 每一个彩种类型获取一条奖期最小的记录
	* @param l
	* @param m
	* @return
	*/
	public List<GameIssueEntity> queryGameIssueByStatusAndSalesEndTimeForBeginSaleStart(Long status, Long periodStatus);

	
	/** 
	* @Title: dealNoAwardNumberSaleEndIssue 
	* @Description: 处理销售截止后，（加上10分钟），还没有开奖号码的，就主动去获取开奖号码
	* @throws Exception
	*/
	void dealNoAwardNumberSaleEndIssue() throws Exception;
	
	/** 
	* @Title: getAndUpdateAwardStruct 
	* @Description: 获取奖金结构并更新奖期
	* @param issue
	* @throws Exception
	*/
	void getAndUpdateAwardStruct(GameIssueEntity issue) throws Exception;
	 
	/**
	 * 获取奖期（根据彩种和日期）
	 * @param lotteryId
	 * @param day （字符串:20141022）
	 * @return
	 * @throws Exception
	 */
	public List<GameIssueEntity> getGameIssueByLotteryIdAndDay(Long lotteryId,String startDay,String endDay) throws Exception;
	

	public GameIssue getGameIssueByLotteryAndIssue(Long lotteryId,Long issueCode) throws Exception;
	
	/**
	 * 保存
	 * @param ctx
	 * @param issue
	 * @return
	 * @throws Exception
	 */
	public int save(GameContext ctx,GameIssue issue) throws Exception;
	/**
	 * 销售开始
	 * @param ctx
	 * @param issue
	 * @return
	 * @throws Exception
	 */
	public int saleBegin(GameContext ctx,GameIssue issue) throws Exception;
	/**
	 * 销售结束
	 * @param ctx
	 * @param issue
	 * @return
	 * @throws Exception
	 */
	public int saleEnd(GameContext ctx,GameIssue issue) throws Exception;
	/**
	 * 开奖
	 * @param ctx
	 * @param issue
	 * @return
	 * @throws Exception
	 */
	public int draw(GameContext ctx,GameIssue issue) throws Exception;
	/**
	 * 重开奖
	 * @param ctx
	 * @param issue
	 * @return
	 * @throws Exception
	 */
	public int redraw(GameContext ctx,GameIssue issue) throws Exception;
	/**
	 * 补开奖
	 * @param ctx
	 * @param issue
	 * @return
	 * @throws Exception
	 */
	public int makeupDraw(GameContext ctx,GameIssue issue) throws Exception;
	/**
	 * 撤销
	 * @param lotteryId
	 * @param issueCode
	 * @throws Exception
	 */
	public int cancel(GameContext ctx,GameIssue issue) throws Exception; 
	
	
	public GameIssueEntity getSsqGameIssueForMail(Date saleEndDate) throws Exception;
	
	/**
	 * 查詢by webIssueCode
	 * @param lotteryid
	 * @param webIssueCode
	 * @return
	 */
	public GameIssueEntity queryGameIssueByWebIssueCode(Long lotteryId,
			String webIssueCode);
	
	public List<GameIssue> getNoNumberLatestByLotteryIdAndTime(Long lotteryId,
			Date curTime) throws Exception;
	
	void updateGameIssue(GameIssueEntity issueEntity) throws Exception;
	
	
	//输入开奖号码  2
		void addDrawResult(Long lotteryid, Long issueCode, String numberRecord, GameWarnIssueLog warnIssueLog,
					Date ecVerifiedTime, String memo) throws Exception;
}
