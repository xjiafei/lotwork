package com.winterframework.firefrog.game.dao;

import java.util.Date;
import java.util.List;

import com.winterframework.firefrog.game.dao.vo.GameRetPoint;
import com.winterframework.firefrog.game.dao.vo.GameSlip;
import com.winterframework.firefrog.game.entity.GamePackageItem;
import com.winterframework.orm.dal.ibatis3.BaseDao;

public interface IGameReturnPointDao extends BaseDao<GameRetPoint> {

	/** 
	* @Title: saveUserReturnPoint 
	* @Description:生成注单保存用户返点信息 
	* @param gameSlip
	* @param orderId
	 * @throws Exception 
	*/
	void saveUserReturnPoint(GameSlip gameSlip, Long orderId) throws Exception;

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
	* @Title: getRetPointByIssueAndSaleTime 
	* @Description: 根据彩种，奖期以及销售时间获取返点列表
	* @param lotteryId
	* @param issueCode
	* @param saleTime
	* @return
	*/
	List<GameRetPoint> getRetPointByLotteryInfo(Long lotteryId, Long issueCode, Date saleTime);

	/** 
	* @Title: updateGameRetunPointByLotteryInfo 
	* @Description: 根据彩中信息，将返点信息表中的记录状态改为撤销状态
	* @param lotteryId
	* @param issueCode
	* @param saleTime
	*/
	void updateGameRetunPointByLotteryInfo(Long lotteryId, Long issueCode, Date saleTime);
	
	/** 
	* @Title: updateStatus 
	* @Description: 批处理更新状态
	* @param orderIds
	* @param status
	* @return
	*/
	int updateStatus(List<Long> orderIds, Long status);

	String getRetunPointChain(List<GamePackageItem> itemList, String userChain) throws Exception;

	String saveGameOrderUserReturnPoint(List<com.winterframework.firefrog.game.entity.GameSlip> slipList, Long orderid) throws Exception;
}
