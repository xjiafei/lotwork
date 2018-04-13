package com.winterframework.firefrog.game.dao;

import java.util.List;

import com.winterframework.firefrog.game.dao.vo.GameWinsReport;
import com.winterframework.firefrog.game.dao.vo.WinsSumReport;
import com.winterframework.firefrog.game.entity.WinsReport;
import com.winterframework.firefrog.game.web.dto.WinsReportQueryRequest;
import com.winterframework.modules.page.Page;
import com.winterframework.modules.page.PageRequest;
import com.winterframework.orm.dal.ibatis3.BaseDao;

/** 
* @ClassName: IGameWinsReportDao 
* @Description: 单期盈亏表报表相关操作DAO接口 
* @author Denny 
* @date 2013-10-16 下午3:44:54 
*  
*/
public interface IGameWinsReportDao extends BaseDao<GameWinsReport>{

	/** 
	* @Title: getWinsReport 
	* @Description: 查询单期盈亏表列表 
	*/
	Page<WinsReport> getWinsReport(PageRequest<WinsReportQueryRequest> pr);

	/** 
	* @Title: getWinsSumReport 
	* @Description: 根据彩种ID、开始时间、结束时间查询盈亏记录总计信息
	* @param lotteryid
	* @param startTime
	* @param endTime
	* @return WinsSumReport    返回类型 
	* @throws 
	*/
	WinsSumReport getWinsSumReport(Long lotteryid, Long startTime, Long endTime);

	WinsSumReport getWinsDetailSumReport(Long lotteryid, Long issueCode);

	/**
	 * 
	* @Title: getWinsDetailReport 
	* @Description: 根据彩种ID、奖期期号、排序规则查询盈亏明细记录
	* @param lotteryid
	* @param issueCode
	* @param sortColumns
	* @return List<GameWinsReport>    返回类型 
	* @throws
	 */
	List<GameWinsReport> getWinsDetailReport(Long lotteryid, Long issueCode, String sortColumns);

	/**
	 * 
	* @Title: getWinsReportForExport 
	* @Description: 根据彩种ID、开始结束时间、排序规则查询单期盈亏记录，用于生产报表 
	* @param lotteryid
	* @param startTime
	* @param endTime
	* @param sortColumns
	* @return List<GameWinsReport>    返回类型 
	* @throws
	 */
	List<GameWinsReport> getWinsReportForExport(Long lotteryid, Long startTime, Long endTime, String sortColumns);
	
	
}
