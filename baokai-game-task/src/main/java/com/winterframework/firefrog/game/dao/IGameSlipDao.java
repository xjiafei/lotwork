package com.winterframework.firefrog.game.dao;

import java.util.Date;
import java.util.List;

import com.winterframework.firefrog.game.dao.vo.GameSlip;
import com.winterframework.firefrog.game.entity.GamePackage;
import com.winterframework.orm.dal.ibatis3.BaseDao;

public interface IGameSlipDao extends BaseDao<GameSlip> {

	/**
	 * 
	* @Title: querySlipByLotteryIdAndIssueCode 
	* @Description: 根据LotteryId和IssueCode获取所有注单信息
	* @param lotteryId
	* @param issueCode
	* @return
	* @throws Exception
	 */
	public List<GameSlip> querySlipByLotteryIdAndIssueCode(Long lotteryId, Long issueCode) throws Exception;

	/**
	 * 
	* @Title: querySlipByOrder 
	* @Description: 根据订单ID查找所对应的注单列表
	* @param orderId
	* @return
	* @throws Exception
	 */
	public List<GameSlip> querySlipByOrder(Long orderId) throws Exception;

	/**
	 * 
	* @Title: updateSlipByOrderID 
	* @Description: 根据订单ID修改对应的所有注单状态
	* @param orderID
	* @param status
	* @throws Exception
	 */
	public void updateSlipByOrderID(Long orderID, Integer status) throws Exception;

	/**
	 * 
	* @Title: queryOrderCodeBySlipID 
	* @Description: 根据注单ID查询对应的订单编号 
	* @param id
	* @return
	* @throws Exception
	 */
	public String queryOrderCodeBySlipID(Long id) throws Exception;

	/** 
	* @Title: revocationGameSlipsByOrderId 
	* @Description: 根据订单Id撤销GameSlip列表
	* @param orderId
	* @param status
	*/
	public void revocationGameSlipsByOrderId(Long orderId, int status);

	/** 
	* @Title: undoGameSlip 
	* @Description: 撤销注单
	* @param lotteryId
	* @param issueCode
	* @param saleDate
	 * @param aimStatus 
	*/
	public void undoGameSlip(Long lotteryId, Long issueCode, Date saleDate, int aimStatus);

	public void saveSlipList(List<com.winterframework.firefrog.game.entity.GameSlip> slipList,
			Long orderid,List<com.winterframework.firefrog.game.dao.vo.GamePackageItem> itemList) throws Exception;
	/**
	 * 改变状态
	 * @param orderId
	 * @param fromStatus
	 * @param toStatus
	 * @return 
	 */
	int changeStatus(Long orderId,Integer fromStatus,Integer toStatus);

}
