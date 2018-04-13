package com.winterframework.firefrog.game.dao;

import java.util.List;

import com.winterframework.firefrog.game.dao.vo.GameBettypeStatus;
import com.winterframework.orm.dal.ibatis3.BaseDao;

public interface IGameBettypeStatusDao extends BaseDao<GameBettypeStatus> {

	/**
	 * 
	* @Title: getTheoryBonusByLottery 
	* @Description: 获取理论奖金
	* @param LotteryId
	* @param groupCode
	* @param setCode
	* @param mothedCode
	* @return
	* @throws Exception
	 */
	public Long getTheoryBonusByLottery(Long lotteryId, Integer groupCode, Integer setCode, Integer methodCode) throws Exception;
	
	
	/** 
	* @Title: getStatus 
	* @Description: 获取投注方式的状态是暂停还是可销售，假如没有该投注方式责为停售 
	* @param lotteryId
	* @param groupCode
	* @param setCode
	* @param methodCode
	* @return
	* @throws Exception
	*/
	public Long getStatus(Long lotteryId, Integer groupCode, Integer setCode, Integer methodCode) throws Exception;

	public List<GameBettypeStatus> getAllByLotteryId(long lotteryid);

	/** 
	* @Title: updateForPublish 
	* @Description: 发布投注方式的状态 
	*/
	public void updateForPublish(GameBettypeStatus gbs);
	
	/**
	 * 
	* @Title: getTheoryByLotteryId 
	* @Description: 理论奖金
	* @param lotteryid
	* @return
	 */
	public List<GameBettypeStatus> getTheoryByLotteryId(Long lotteryid);


	/** 
	* @Title: getValidBetMethodByLotteryId 
	* @Description: 获取有效彩种列表
	* @param lotteryid
	* @return
	*/
	public List<GameBettypeStatus> getValidBetMethodByLotteryId(long lotteryid);


	/** 
	* @Title: getStatusOfBjkl8Interest 
	* @Description: 获取北京快乐8趣味的投注状态
	* @param lotteryid
	* @param gameGroupCode
	* @param gameSetCode
	* @param betDetail
	* @return
	*/
	public Long getStatusOfBjkl8Interest(Long lotteryid, Integer gameGroupCode, Integer gameSetCode, String betDetail) throws Exception;
	
	/**
	 * 获取投注方式状态表(根据彩种和玩法）
	 * @param lotteryId
	 * @param betTypeCode		
	 * @return
	 * @throws Exception
	 */
	public GameBettypeStatus getGameBetTypeStatusByLotteryIdAndBetTypeCode(Long lotteryId,String betTypeCode) throws Exception;

}
