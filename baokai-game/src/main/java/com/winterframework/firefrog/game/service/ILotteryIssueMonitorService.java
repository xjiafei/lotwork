package com.winterframework.firefrog.game.service;

import java.util.List;

import com.winterframework.firefrog.game.dao.vo.GameExceptionAuditOrder;
import com.winterframework.firefrog.game.dao.vo.GameWarnIssueList;
import com.winterframework.firefrog.game.entity.GameWarnIssueEntity;
import com.winterframework.firefrog.game.entity.GameWarnOrderEntity;
import com.winterframework.firefrog.game.entity.LotteryIssueMonitorLogs;
import com.winterframework.firefrog.game.web.dto.GameExceptionAuditRequestDTO;
import com.winterframework.firefrog.game.web.dto.GameWarnDetailQueryDto;
import com.winterframework.firefrog.game.web.dto.LotteryMonitorIssueDetail;
import com.winterframework.firefrog.game.web.dto.LotteryMonitorListRequestDTO;
import com.winterframework.firefrog.game.web.dto.QueryLotteryIssueWarnDTO;
import com.winterframework.modules.page.Page;
import com.winterframework.modules.page.PageRequest;

/**
 * 
* @ClassName: ILotteryMonitorService 
* @Description: 彩票奖期监控接口服务
* @author Richard
* @date 2013-10-12 上午10:53:57 
*
 */
public interface ILotteryIssueMonitorService {
	
	/**
	 * 
	* @Title: queryGameWarnIssue 
	* @Description: 获取彩种监控通知
	* @return
	* @throws Exception
	 */
	public List<GameWarnIssueEntity> queryGameWarnIssue() throws Exception;

	/**
	 * 
	* @Title: queryGameWarnList 
	* @Description: 5.5.31	彩种奖期监控列表(OMI031)
	* @param pageRequest
	* @return
	* @throws Exception
	 */
	public Page<GameWarnIssueList> queryGameWarnList(PageRequest<LotteryMonitorListRequestDTO> pageRequest) throws Exception;
	
	/**
	 * 
	* @Title: queryGameWarnDetail 
	* @Description: 5.5.32	彩种奖期监控详情(OMI032)
	* @param lotteryId
	* @param isscode
	* @return
	* @throws Exception
	 */
	public LotteryMonitorIssueDetail queryGameWarnDetail(PageRequest<GameWarnDetailQueryDto> pageRequest) throws Exception;
	
	/**
	 * 
	* @Title: queryGameWarnOrderDetail 
	* @Description: 5.5.33	彩种奖期监控-风险详情(OMI033)
	* @param lotteryId
	* @param issueCode
	* @return
	* @throws Exception
	 */
	public List<GameWarnOrderEntity> queryGameWarnOrderDetail(Long lotteryId, Long issueCode) throws Exception;
	
	/**
	 * 
	* @Title: queryLotteryIssueWarnLog 
	* @Description: 5.5.34	彩种奖期监控日志(OMI034)
	* @param pageRequest
	* @return
	* @throws Exception
	 */
	public Page<LotteryIssueMonitorLogs> queryLotteryIssueWarnLog(PageRequest<QueryLotteryIssueWarnDTO> pageRequest) throws Exception;
	
	/**
	 * 
	* @Title: auditLotteryIssueMonitor 
	* @Description: 5.5.43	彩种奖期监控-风险方案审核(OMI043)
	* @param lotteryid
	* @param issueCode
	* @throws Exception
	 */
	public void auditLotteryIssueMonitor(Long lotteryid, Long issueCode) throws Exception;
	
	/**
	 * 
	* @Title: auditLotteryOrderMonitor 
	* @Description: 5.5.44	彩种奖期监控-订单审核(OMI044)
	* @param orderCode
	* @throws Exception
	 */
	public void auditLotteryOrderMonitor(String orderCode, Integer status) throws Exception;

	public Page<GameExceptionAuditOrder> queryGameWarnAuditList(PageRequest<GameExceptionAuditRequestDTO> pageRequest);

	/** 
	* @Title: auditLotteryOrdersMonitor 
	* @Description: 批量审核订单
	* @param orderCodes 以逗号隔开的订单列表
	 * @throws Exception 
	*/
	public void auditLotteryOrdersMonitor(String orderCodes) throws Exception;
}
