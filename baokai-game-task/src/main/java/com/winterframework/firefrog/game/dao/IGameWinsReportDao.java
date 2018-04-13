package com.winterframework.firefrog.game.dao;

import java.util.List;

import com.winterframework.firefrog.game.dao.vo.GameWinsReport;
import com.winterframework.orm.dal.ibatis3.BaseDao;

public interface IGameWinsReportDao extends BaseDao<GameWinsReport> {

	/**
	 * 
	* @Title: getBetTypeCodeListByLotteryIdAndIssueCode 
	* @Description: get betTypeCode
	* @param lotteryId
	* @param issueCode
	* @return
	 */
	public List<String> getBetTypeCodeListByLotteryIdAndIssueCode(Long lotteryId, Long issueCode);

	/**
	 * 
	* @Title: getTotalSaleAmountByIssueCodeAndBetTypeCode 
	* @Description:saleAmount 
	* @param lotteryId
	* @param issueCode
	* @param betTypeCode
	* @return
	 */
	public Long getTotalSaleAmountByIssueCodeAndBetTypeCode(Long lotteryId, Long issueCode, String betTypeCode);

	/**
	 * 
	* @Title: getTotalWinAmountByIssueCodeAndByTypeCode 
	* @Description: 中奖金额
	* @param lotteryId
	* @param issueCode
	* @param betTypeCode
	* @return
	 */
	public Long getTotalWinAmountByIssueCodeAndByTypeCode(Long lotteryId, Long issueCode, String betTypeCode);

	/**
	 * 
	* @Title: getcancelAmountByIssueCodeAndBetTypeCode 
	* @Description: 撤销费用。
	* @param lotteryId
	* @param issueCode
	* @param betTypeCode
	* @return
	 */
	public Long getCancelAmountByIssueCodeAndBetTypeCode(Long lotteryId, Long issueCode, String betTypeCode);

	/**
	 * 获取追号撤销手续费
	 * @param lotteryId
	 * @param issueCode
	 * @return
	 */
	Long getPlanCancelFeeByLotteryIdAndIssueCode(Long lotteryId, Long issueCode);
	/**
	 * 
	* @Title: getRetPointByIssueCodeAndBetTypeCode 
	* @Description: 返点值
	* @param lotteryId
	* @param issueCode
	* @param betTypeCode
	* @return
	 */
	public List<String> getRetPointByIssueCodeAndBetTypeCode(Long lotteryId, Long issueCode, String betTypeCode);
	
	/**
	 * 获取不存在盈亏表记录的issue_code
	 * @param lotteryId
	 * @return
	 * @throws Exception
	 */
	public List<Long> getGameIssueCodeListNoWinsReport(Long lotteryId) throws Exception;

	public Long getTotalIssueSaleAmountByIssueCode(Long lotteryId,
			Long issueCode) throws Exception;
}
