package com.winterframework.firefrog.game.dao;

import java.util.List;

import com.winterframework.firefrog.game.dao.vo.GameRetPoint;
import com.winterframework.orm.dal.ibatis3.BaseDao;

public interface IGameReturnPointDao extends BaseDao<GameRetPoint> {

	/** 
	* @Title: getGameRetPointByStatus 
	* @Description: 根据状态获取要操作的返点  
	* @param @param status
	* @param @return    设定文件 
	* @return List<GameRetPoint>    返回类型 
	* @throws 
	*/
	List<GameRetPoint> getGameRetPointByStatus(Integer status);

	/** 
	* @Title: getGameRetPointByOrderId 
	* @Description: 根据订单ID获取要操作的返点 
	* @param @param orderId
	* @param @return    设定文件 
	* @return List<GameRetPoint>    返回类型 
	* @throws 
	*/
	List<GameRetPoint> getGameRetPointByIssueCode(Long issueCode);
	
	/** 
	* @Title: getGameRetPointByLotteryIssue 
	* @Description: 返回一期所有的返点记录
	* @param lotteryId
	* @param issueCode
	* @return
	*/
	List<GameRetPoint> getGameRetPointByLotteryIssue(Long lotteryId ,Long issueCode);
	/** 
	* @Title: getGameRetPointByGameOrderId 
	* @Description: 根据订单ID查询用户返点信息
	* @param gameSlipId
	* @return List<GameRetPoint>    返回类型 
	* @throws 
	*/
	GameRetPoint getGameRetPointByGameOrderId(Long gameOrderId);

	Long getRetPointTotalAmountByOrderId(Long id) throws Exception;

	/**
	 * 
	* @Title: getRetPointByOrderId 
	* @Description: 根据订单Id获取retPoint信息
	* @param id
	* @return
	 */
	GameRetPoint getRetPointByOrderId(Long id);
	
	/** 
	* @Title: updateStatus 
	* @Description: 批处理更新状态
	* @param orderIds
	* @param status
	* @return
	*/
	int updateStatus(List<Long> orderIds, Long status);
}
