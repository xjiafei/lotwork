package com.winterframework.firefrog.game.dao;

import java.util.List;

import com.winterframework.firefrog.game.dao.vo.GameReportCountVo;
import com.winterframework.firefrog.game.dao.vo.GameTransactionFund;
import com.winterframework.firefrog.game.web.dto.GameReportRequest;
import com.winterframework.firefrog.game.web.dto.GameReportStruc;
import com.winterframework.firefrog.game.web.dto.GameRiskTransactionReportStruc;
import com.winterframework.modules.page.Page;
import com.winterframework.modules.page.PageRequest;
import com.winterframework.orm.dal.ibatis3.BaseDao;

public interface IGameTransactionFundDao extends BaseDao<GameTransactionFund> {

	/**
	 * 
	* @Title: saveTransactionFund 
	* @Description: Save Game_Transaction_Fund
	* @param userid
	* @param type
	* @param amount
	* @param status
	* @throws Exception
	 */
	public  void saveTransactionFund(Long userid, String type, Long amount, Long status) throws Exception;

	/**
	 * 
	* @Title: queryTransactionByCode 
	* @Description: 查询交易记录
	* @param userId
	* @param conditionList
	* @return
	 */
	public List<GameTransactionFund> queryTransactionByCode(Long userId, List<String> conditionList);

	/**
	 * 
	* @Title: queryAllTransaction 
	* @Description: 查询交易报表
	* @param pageRequest
	* @return
	 */
	Page<GameRiskTransactionReportStruc> queryTransactionReport(PageRequest<GameReportRequest> pageRequest);

	Page<GameReportStruc> queryAllTransaction(PageRequest<GameReportRequest> pageRequest);

	/** 
	* @Title: getFundCountGroup 
	* @Description: 摘要分类统计
	* @param lotteryId
	* @param issueCode
	* @return
	*/
	List<GameReportCountVo> getFundCountGroup(Long lotteryId, Long issueCode);
}
