package com.winterframework.firefrog.game.service;

import java.util.List;

import com.winterframework.firefrog.game.dao.vo.FundGameVos;
import com.winterframework.firefrog.game.dao.vo.GameTransactionFund;
import com.winterframework.firefrog.game.web.dto.FundTransactionStruc;
import com.winterframework.firefrog.game.web.dto.GameReportRequest;
import com.winterframework.firefrog.game.web.dto.GameReportStruc;
import com.winterframework.firefrog.game.web.dto.GameRiskTransactionReportStruc;
import com.winterframework.firefrog.game.web.dto.TORiskDTO;
import com.winterframework.modules.page.Page;
import com.winterframework.modules.page.PageRequest;

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
	* @Description: 资金查询接口。对应接口文件中的5.6.1	交易记录查询(REI001)
	* @param userId
	* @param orderCodeList
	* @param planCodeList
	* @return
	* @throws Exception
	 */
	public List<FundTransactionStruc> queryTransactionRecord(Long userId, List<String> orderCodeList, List<String> planCodeList) throws Exception;
	
	/**
	 * 
	* @Title: gameReport 
	* @Description: 游戏报表 
	* @param pageRequest
	* @return
	* @throws Exception
	 */
	Page<GameRiskTransactionReportStruc> queryTransactionReport(PageRequest<GameReportRequest> pageRequest) throws Exception;
	/**
	 * 
	* @Title: gameReport 
	* @Description: 游戏报表 
	* @param pageRequest
	* @return
	* @throws Exception
	 */
	Page<GameReportStruc> gameReport(PageRequest<GameReportRequest> pageRequest) throws Exception;
	/** 
	* @Title: parseGameTransactionFundList 
	* @Description: 转化创建交易实体
	* @param dto
	* @param list
	* @return
	* @throws Exception
	*/
	List<GameTransactionFund> parseGameTransactionFundList(TORiskDTO dto, List<FundGameVos> list) throws Exception;
	
	/** 
	* @Title: parseGameTransactionFund 
	* @Description: 转化创建交易实体
	* @param dto
	* @param vos
	* @return
	* @throws Exception
	*/
	GameTransactionFund parseGameTransactionFund(TORiskDTO dto, FundGameVos vos) throws Exception;
}
