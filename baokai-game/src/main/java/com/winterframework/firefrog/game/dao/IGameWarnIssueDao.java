package com.winterframework.firefrog.game.dao;

import java.util.List;

import com.winterframework.firefrog.game.dao.vo.GameWarnIssue;
import com.winterframework.firefrog.game.dao.vo.GameWarnIssueList;
import com.winterframework.firefrog.game.entity.GameWarnIssueEntity;
import com.winterframework.firefrog.game.entity.LotteryIssueMonitorLogs;
import com.winterframework.firefrog.game.web.dto.LotteryMonitorListRequestDTO;
import com.winterframework.firefrog.game.web.dto.QueryLotteryIssueWarnDTO;
import com.winterframework.modules.page.Page;
import com.winterframework.modules.page.PageRequest;
import com.winterframework.orm.dal.ibatis3.BaseDao;

public interface IGameWarnIssueDao extends BaseDao<GameWarnIssue>{
	int saveOrUpdate(GameWarnIssue warn) throws Exception ;
	int updateIfHave(GameWarnIssue warn) throws Exception ;
	/**
	 * 
	* @Title: queryGameWarnIssue 
	* @Description:彩种监控通知 
	* @return
	* @throws Exception
	 */
	public List<GameWarnIssueEntity> queryGameWarnIssueNotices() throws Exception;
	/** 
	* @Title: updateNoticeStatus 
	* @Description: 变已阅 
	* @param issueCode
	* @throws Exception
	*/
	void updateNoticeStatus(Long lotteryId) throws Exception;
	/**
	 * 
	* @Title: queryGameWarnIssueList 
	* @Description: 5.5.31	彩种奖期监控列表(OMI031) 所有
	* @param pageRequest
	* @return
	* @throws Exception
	 */
	public Page<GameWarnIssueList> queryGameWarnIssueList(PageRequest<LotteryMonitorListRequestDTO> pageRequest) throws Exception;
	
	/**
	 * 
	* @Title: queryGameWarnIssueOnSale 
	* @Description: 彩种奖期监控列表(OMI031)  仅查看尚未结束的奖期
	* @return
	 */
	public Page<GameWarnIssueList> queryGameWarnIssueOnSale(PageRequest<LotteryMonitorListRequestDTO> pageRequest) throws Exception;
	/**
	 * 
	* @Title: queryGameWarnIssue 
	* @Description: 彩种奖期监控列表(OMI031)  仅查看存在异常的奖期
	* @param pageRequest
	* @return
	* @throws Exception
	 */
	public Page<GameWarnIssueList> queryGameWarnIssue(PageRequest<LotteryMonitorListRequestDTO> pageRequest) throws Exception;
	
	/**
	 * 
	* @Title: queryGameWarnIssueByLotteryIdAndIssueCode 
	* @Description: 根据LotteryId和issueCode 返回GameWarnIssue
	* @param lotteryId
	* @param issueCode
	* @return
	* @throws Exception
	 */
	public List<GameWarnIssueEntity> queryGameWarnIssueByLotteryIdAndIssueCode(Long lotteryId, Long issueCode) throws Exception;
	
	/**
	 * 
	* @Title: queryLotteryIssueWarnLog 
	* @Description: 彩种奖期监控日志信息
	* @param pageRequest
	* @return
	* @throws Exception
	 */
	public Page<LotteryIssueMonitorLogs> queryLotteryIssueWarnLog(PageRequest<QueryLotteryIssueWarnDTO> pageRequest) throws Exception;

	/**
	 * 
	* @Title: queryGameWarnOnlyCurrntIssue 
	* @Description: 仅查看当前尚未结束的奖期监控信息
	* @param pageRequest
	* @return
	* @throws Exception
	 */
	public Page<GameWarnIssueList> queryGameWarnOnlyCurrntIssue(PageRequest<LotteryMonitorListRequestDTO> pageRequest) throws Exception;
}
