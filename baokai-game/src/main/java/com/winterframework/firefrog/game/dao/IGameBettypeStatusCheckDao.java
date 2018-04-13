package com.winterframework.firefrog.game.dao;

import java.util.List;
import java.util.Map;

import com.winterframework.firefrog.game.dao.vo.GameBettypeStatusCheck;

/** 
* @ClassName: IGameBetTypeStatusCheckDao 
* @Description: 投注方式状态审核DAO接口 
* @author Denny 
* @date 2013-8-27 上午10:54:01 
*  
*/
public interface IGameBettypeStatusCheckDao {


	/**
	* @Title: updateToPublish 
	* @Description: 更新状态
	 */
	public void updateToPublish(Long lotteryid, Long auditType);

	/**
	* @Title: getAllByLotteryId 
	* @Description: 根据彩种查询所有投注方式销售状态审核
	 */
	public List<GameBettypeStatusCheck> getAllByLotteryId(Long lotteryid);
	
	/**
	* @Title: queryGameBettypeStatusCheckByMap 
	* @Description: 根据彩种ID，投注方式查询check有没有对应记录 
	 */
	public GameBettypeStatusCheck queryGameBettypeStatusCheckByMap(Map<String, Object> map);
	
	/**
	* @Title: insertToCheck 
	* @Description: 添加check记录
	 */
	public void insertToCheck(GameBettypeStatusCheck g);

	/** 
	* @Title: updateToCheck 
	* @Description: 更新状态为待审核
	*/
	public void updateToCheck(GameBettypeStatusCheck g);

	/** 
	* @Title: deleteAllByLotteryId 
	* @Description: 根据彩种ID删除所有
	*/
	public void deleteAllByLotteryId(Long lotteryid);

	/** 
	* @Title: updateBetttypeStatusCheckToNotPublished 
	* @Description: 更新投注方式Check表数据为发布不通过
	* @param lotteryid
	*/
	public void updateBetttypeStatusCheckToNotPublished(Long lotteryid);
	
}
