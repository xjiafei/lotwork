package com.winterframework.firefrog.game.fund.dao;

import java.util.List;

import com.winterframework.firefrog.game.fund.bean.GameRiskFund;
import com.winterframework.firefrog.game.web.dto.TORiskDTO;
import com.winterframework.orm.dal.ibatis3.BaseDao;

public interface IGameRiskFundDao extends BaseDao<GameRiskFund> {
	
	/**
	 * 
	* @Title: saveRiskFund 
	* @Description:save Game_RIsk_FUND 
	* @param dto
	* @param users
	* @param amouts
	* @param Status
	* @throws Exception
	 */
	public void saveRiskFund(TORiskDTO dto, String[] users, String[] amouts, int Status) throws Exception;

	/**
	 * 
	* @Title: saveRiskFund 
	* @Description: save Game_Risk_fund
	* @param userid
	* @param amout
	* @param status
	* @param orderCode
	* @param lotteryId
	* @param isuueCode
	* @param type
	* @throws Exception
	 */
	public void saveRiskFund(Long userid, Long amout, int status,String orderCode, Long lotteryId,Long isuueCode, int type)throws Exception;
	
	/**
	 * 
	* @Title: getGameRiskFundByOrderCode 
	* @Description: get GameRiskFund info by OrderCode
	* @param orderCode
	* @return
	 */
	public List<GameRiskFund> getGameRiskFundByOrderCode(String orderCode);
}
