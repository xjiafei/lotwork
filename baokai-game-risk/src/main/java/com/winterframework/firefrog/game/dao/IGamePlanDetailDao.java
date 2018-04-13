package com.winterframework.firefrog.game.dao;

import java.util.List;

import com.winterframework.firefrog.game.dao.vo.GamePlanDetail;
import com.winterframework.orm.dal.ibatis3.BaseDao;

public interface IGamePlanDetailDao extends BaseDao<GamePlanDetail> {

	/**
	 * 
	* @Title: queryGamePlanDetailByPlanID 
	* @Description: 获取追号明细
	* @param id
	* @param issueCode
	* @return
	 */
	public GamePlanDetail queryGamePlanDetailByPlanID(Long id, Long issueCode);

	GamePlanDetail queryGamePlanDetailByPlanIDWithOutStatus(Long id, Long issueCode);

	/**
	 * 
	* @Title: updateGamePlanDetailByPlanID 
	* @Description: 更新追号明细状态
	* @param id
	* @param issueCode
	* @return
	* @throws Exception
	 */
	public int updateGamePlanDetailByPlanID(Long id, Long issueCode, Integer status) throws Exception;

	/**
	 * 
	* @Title: queryGamePlanDetails 
	* @Description: 查询期号范围内追号明细信息（根据lotteryid过滤其他彩种明细）
	* @param lotteryid
	* @param startIssueCode
	* @param endIssueCode
	* @return
	* @throws Exception
	 */
	public List<GamePlanDetail> queryGamePlanDetails(Long lotteryid, Long startIssueCode, Long endIssueCode)
			throws Exception;

	/**
	 * 
	* @Title: revocationGamePlanDetails 
	* @Description: 撤销期号范围内追号明细信息 
	* @param lotteryid
	* @param startIssueCode
	* @param endIssueCode
	* @throws Exception
	 */
	public void revocationGamePlanDetails(Long lotteryid, Long startIssueCode, Long endIssueCode) throws Exception;

	/**
	 * 
	* @Title: getPauseGamePlanDetailUniqueIssueCode 
	* @Description: 获取追号计划为暂停状态的第一个追号明细信息。
	* @param lotteryId
	* @return
	 */
	public List<GamePlanDetail> getPauseGamePlanDetailUniqueIssueCode(Long lotteryId);

	/** 
	* @Title: updateGamePlanDetailStatus 
	* @Description: 将"已撤销"状态的追号详情改为"未执行"
	* @param planid
	* @return
	*/
	public void updateGamePlanDetailStatusByPlanId(Integer status, Long planid);

	/** 
	* @Title: selectGamePlanDetailsByPlanId 
	* @Description: 根据追号ID查找对应的追号详情列表
	* @param planid
	* @return
	*/
	public List<GamePlanDetail> selectGamePlanDetailsByPlanId(Long planid);

	/** 
	* @Title: getPlanIdByIssueCode 
	* @Description: 根据奖期号在追号明细中获取某一奖期的所有Planid（用于暂停奖期）
	* @param @param issueCode
	* @param @return    设定文件 
	* @return List    返回类型 
	* @throws 
	*/
	public List<Long> getPlanIdByIssueCode(Long issueCode);

	/** 
	* @Title: getPausePlanIdByIssueCode 
	* @Description: 根据issueCode在追号明细中获取暂停奖期的planid列表（用于继续派奖）
	* @param @param issueCode
	* @param @return    设定文件 
	* @return List<Long>    返回类型 
	* @throws 
	*/
	public List<Long> getPausePlanIdByIssueCode(Long issueCode);

	/** 
	* @Title: selectGamePlanDetailsByPlanIdAndIssueCode 
	* @Description: 根据奖期号和追号ID查询对应的GamePlanDetail信息
	* @param parentid
	* @param issueCode
	* @return
	*/
	public GamePlanDetail selectGamePlanDetailsByPlanIdAndIssueCode(Long parentid, Long issueCode);

	public int updateGamePlanDetailByPlanId(Long id) throws Exception;

	public List<GamePlanDetail> queryGamePlanDetailByIssueCode(Long issueCode, Long lotteryId);

	/**
	 * 
	* @Title: getGamePlanDetailByLotteryId 
	* @Description: 根据彩种信息，获取彩种追号计划中还没有进行过追号信息的第一单
	* @param lotteryId
	* @return
	 */
	public List<GamePlanDetail> getGamePlanDetailByLotteryId(Long lotteryId);

	/** 
	* @Title: selectOneIssueUndoGamePlanDetailsByLotteryIssue 
	* @Description: 获取一期 状态为0 的GamePlanDetail
	* @author hugh
	* @param issueCode
	* @param lotteryId
	* @return
	*/
	List<GamePlanDetail> selectOneIssueUndoGamePlanDetailsByLotteryIssue(Long issueCode, Long lotteryId);

	/**
	 * 
	* @Title: updateGamePlanDetailContinue 
	* @Description: 更新追号明细信息为继续
	* @param planId
	 */
	public void updateGamePlanDetailContinue(Long planId);

	/***
	 * 
	* @Title: updateGamePlanDetailPause 
	* @Description: 更新追号明细信息为暂停。 
	* @param planId
	 */
	public void updateGamePlanDetailPause(Long planId);

	/** 
	* @Title: updateGamePlanDetailByPlanIDAfterIssue 
	* @Description: 将指定奖期后面的追号细节状态改为指定状态
	* @param id
	* @param issueCode
	* @param aimStatus
	*/
	public void updateGamePlanDetailByPlanIdAfterIssue(Long id, Long issueCode, int aimStatus);

	/** 
	* @Title: getGamePlanDetailByLotteryIdAndIssueCode 
	* @Description: 根据彩种和奖期获取未执行的追号细节列表
	* @param lotteryId
	* @param issueCode
	* @return
	*/
	public List<GamePlanDetail> getGamePlanDetailByLotteryIdAndIssueCode(Long lotteryId, Long issueCode);

}
