package com.winterframework.firefrog.game.service;

import java.util.List;

import com.winterframework.firefrog.game.dao.vo.WinsSumReport;
import com.winterframework.firefrog.game.entity.WinsReport;
import com.winterframework.firefrog.game.web.dto.WinsReportQueryRequest;
import com.winterframework.modules.page.Page;
import com.winterframework.modules.page.PageRequest;

/** 
* @ClassName: IGameWinsReportService 
* @Description: 运营盈亏报表Srervice接口 
* @author Denny 
* @date 2013-10-15 上午11:52:37 
*  
*/
public interface IGameWinsReportService {

	/** 
	* @Title: queryWinsReport
	* @Description: 运营盈亏报表查询
	*/
	public Page<WinsReport> queryWinsReport(PageRequest<WinsReportQueryRequest> pr);

	/** 
	* @Title: queryWinsSumReport 
	* @Description: 根据彩种ID、开始结束时间查询单期盈亏总计 
	* @throws 
	*/
	public WinsSumReport queryWinsSumReport(Long lotteryid, Long startTime, Long endTime);

	/** 
	* @Title: queryWinsDetailReport 
	* @Description: 根据彩种ID、期号、排序规则查询盈亏详情列表 
	*/
	public List<WinsReport> queryWinsDetailReport(Long lotteryid, Long issueCode, String sortColumns);

	/** 
	* @Title: queryWinsReportForExport 
	* @Description: 查询单期盈亏列表，用于生成报表 
	*/
	public List<WinsReport> queryWinsReportForExport(Long lotteryid, Long startTime, Long endTime, String sortColumns);

	/** 
	* @Title: queryWinsDetailSumReport 
	* @Description: 查询盈亏明细总计
	* @throws 
	*/
	public WinsSumReport queryWinsDetailSumReport(long lotteryid, long issueCode);
}
