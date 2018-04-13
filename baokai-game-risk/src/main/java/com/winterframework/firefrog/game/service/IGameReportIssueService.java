package com.winterframework.firefrog.game.service;

import com.winterframework.firefrog.game.dao.vo.GameReportIssue;
import com.winterframework.firefrog.game.exception.GameReportException;
//import com.winterframework.firefrog.game.web.dto.GameReportRequest;
//import com.winterframework.firefrog.game.web.dto.GameRiskTransactionReportStruc;
//import com.winterframework.modules.page.Page;
//import com.winterframework.modules.page.PageRequest;
import com.winterframework.firefrog.game.web.dto.GameRiskReportRequest;
import com.winterframework.modules.page.Page;
import com.winterframework.modules.page.PageRequest;

/** 
* @ClassName IGameReportService 
* @Description 游戏报表处理 
* @author  hugh
* @date 2014年4月25日 下午4:04:46 
*  
*/
public interface IGameReportIssueService {
	
	/** 
	* @Title: createGameReport 
	* @Description: 创建营收报表
	* @throws GameReportException
	*/
	void createGameReport(Long lotteryId,Long issueCode) throws GameReportException;
	
	/** 
	* @Title: queryRiskGameIssues 
	* @Description: 查询报表list 
	* @param pr
	* @return
	*/
	Page<GameReportIssue> queryGameReportIssues(PageRequest<GameRiskReportRequest> pr);
}
