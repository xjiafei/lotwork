package com.winterframework.firefrog.game.service;

import java.util.List;

import com.winterframework.firefrog.game.web.dto.FundTransactionStruc;

/**
 * 
* @ClassName: ITransactionRecordService 
* @Description: 交易记录查询接口 
* @author Richard
* @date 2014-2-17 上午10:29:55 
*
 */
public interface ITransactionRecordService {

	/**
	 * 
	* @Title: queryTransactionRecord 
	* @Description: 交易记录查询
	* @param userId
	* @param orderCodeList
	* @param planCodeList
	* @return
	* @throws Exception
	 */
	public List<FundTransactionStruc> queryTransactionRecord(String orderCode,Long userId) throws Exception;

	/**
	 * 
	* @Title: queryTransactionRecord 
	* @Description: 交易记录查询
	* @param userId
	* @param orderCodeList
	* @param planCodeList
	* @return
	* @throws Exception
	 */
	public List<FundTransactionStruc> queryTransactionRecordPlanCode(String planCode,Long userId) throws Exception;

}
