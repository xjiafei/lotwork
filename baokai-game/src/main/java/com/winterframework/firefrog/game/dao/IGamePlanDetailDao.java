package com.winterframework.firefrog.game.dao;

import java.util.Date;
import java.util.List;

import com.winterframework.firefrog.game.entity.GamePackage;
import com.winterframework.firefrog.game.entity.GamePlan;
import com.winterframework.firefrog.game.entity.GamePlanDetail;
import com.winterframework.orm.dal.ibatis3.BaseDao;

public interface IGamePlanDetailDao extends BaseDao<com.winterframework.firefrog.game.dao.vo.GamePlanDetail>{

	public void saveGamePlanDetail(GamePlanDetail detail, Long gamePlanId, Long packageAmount) throws Exception;

	/**
	 * 
	* @Title: getGamePlanDetailByPlanIdAndIssueCode 
	* @Description: 根据追号Id和期号获取追号明细记录
	* @param planId
	* @param issueCode
	* @return
	* @throws Exception
	 */
	public com.winterframework.firefrog.game.dao.vo.GamePlanDetail getGamePlanDetailByPlanIdAndIssueCode(Long planId,
			Long issueCode) throws Exception;

	/**
	 * 
	* @Title: updateGamePlanDetailByPlanIdAndIssueCode 
	* @Description:根据追号iD和期数更新追号明细状态
	* @param planId
	* @param issueCode
	* @return
	* @throws Exception
	 */
	public int updateGamePlanDetailByPlanIdAndIssueCode(Long planId, Long issueCode,String cancelUser) throws Exception;

	/**
	 * 
	* @Title: updateGamePlanDetailByPlanId 
	* @Description: 根据追号Id更新追号明细状态
	* @param planId
	* @return
	* @throws Exception
	 */
	public int updateGamePlanDetailByPlanId(Long planId,String cancelUser) throws Exception;
	public int updateGamePlanDetailByPlanIdDate(Long planId,String cancelUser,Date date) throws Exception;

	/**
	 * 
	* @Title: saveGamePlanDetailList 
	* @Description: 保存追号明细
	* @param details
	* @param gamePlan
	 * @return 
	* @throws Exception
	 */
	public Long saveGamePlanDetailList(List<GamePlanDetail> details,GamePackage pkg, GamePlan gamePlan, Long packageAmount,Long currentIssueCode) throws Exception;

	/** 
	* @Title: getPlanDetailsByPlanId 
	* @Description: 根据planid查询追号详情列表 
	* @param planId
	* @return List<GamePlanDetail>    返回类型 
	* @throws 
	*/
	public List<GamePlanDetail> getPlanDetailsByPlanId(long planId);

	/** 
	* @Title: getUnExeuPlanDetailsByPlanId 
	* @Description: 获取未执行的追号记录 
	* @param planId
	* @return
	*/
	public List<GamePlanDetail> getUnExeuPlanDetailsByPlanId(Long planId);
	public List<GamePlanDetail> getUnExeuPlanDetailsByPlanIdDate(Long planId,Date date);
	public com.winterframework.firefrog.game.dao.vo.GamePlanDetail queryGamePlanDetailByPlanID(Long planId,
			Long issueCode);

	public void updateGamePlanDetailByPlanID(Long planId, Long issueCode, Integer status) throws Exception;
	
	/**
	 * 取得未執行追號
	 * @param planId
	 * @return
	 */
	public List<GamePlanDetail> queryGamePlanDetailByNoExecute(Long planId);


	/**
	 * 
	* @Title: queryGamePlanDetailByIssueCode 
	* @Description: 根据IssueCode获取追号明细信息。
	* @param issueCode
	* @return
	 */
	public List<com.winterframework.firefrog.game.dao.vo.GamePlanDetail> queryGamePlanDetailByIssueCode(Long issueCode,Long lotteryId);

	/** 
	* @Title: getPausePlanIdByIssueCode 
	* @Description: 根据issueCode在追号明细中获取暂停奖期的planid列表（用于继续派奖）
	* @param @param issueCode
	* @param    设定文件 
	* @return List<Long>    返回类型 
	* @throws 
	*/
	public List<Long> getPausePlanIdByIssueCode(Long issueCode);

	/**
	 * @param planId
	 * @param issueCode
	 * @param cancelUser
	 * @return撤销预约追号更新plandetail
	 * @throws Exception
	 */
	public int updateReservationCalledGamePlanDetailByPlanIdAndIssueCode(Long planId,
			Long issueCode, String cancelUser) throws Exception;
	
	/**
	 * @param planId
	 * @param issueCode
	 * @param cancelUser
	 * @return撤销预约訂單追号更新plandetail
	 * @throws Exception
	 */
	public int updateGamePlanDetailByPlanIdAndIssueCodeForOrderCancel(Long planId,
			Long issueCode, String cancelUser) throws Exception;
	
	
	/**
	 * @param planId
	 * @param issueCode
	 * @param cancelUser
	 * @return撤销预约追号變更狀態為待撤銷
	 * @throws Exception
	 */
	public int updateReservationCalledGamePlanDetailStatusByPlanIdAndIssueCode(Long planId,
			List<String> issueCode, String cancelUser) throws Exception;
	
	/**
	 * @param planId
	 * @param issueCode
	 * @param cancelUser
	 * @return 方案撤銷是追號時變更已執行追號為待撤銷
	 * @throws Exception
	 */
	public int updateOrderReservationCalledGamePlanDetailStatusByPlanIdAndIssueCode(Long planId,
			List<String> issueCode, String cancelUser) throws Exception;

	/**
	 * @param planId
	 * @param issueCode
	 * @return
	 */
	com.winterframework.firefrog.game.dao.vo.GamePlanDetail queryGamePlanDetailByPlanIDForUndo(
			Long planId, Long issueCode);

	public Date getLastPlanCanStopTime(Long planId);
	
	public Long getUnExeuAndIssueBeforeCurrentPlanDetailsCountByPlanId(Long planId);
	
	/** 
	* @Title: getGamePlanDetailsByPlanId 
	* @Description: 根据追号ID查找对应的追号详情列表
	* @param planid
	* @return
	*/
	public List<com.winterframework.firefrog.game.dao.vo.GamePlanDetail> getGamePlanDetailsByPlanId(Long planid);
	
	/** 
	* @Title: selectGamePlanDetailsByPlanId 
	* @Description: 根据追号ID查找对应的追号详情列表
	* @param planid
	* @return
	*/
	public List<com.winterframework.firefrog.game.dao.vo.GamePlanDetail> selectGamePlanDetailsByPlanId(Long planid);
	
	/**
	 * 變更撤銷異常的追號單狀態為撤銷異常
	 * @param planId
	 * @param issueCode
	 * @param cancelUser
	 * @return
	 * @throws Exception
	 */
	public int updateReservationCalledErrorStatusByPlanIdAndIssueCode(Long planId, Long issueCode, Long cancelUser)
			throws Exception ;
}
