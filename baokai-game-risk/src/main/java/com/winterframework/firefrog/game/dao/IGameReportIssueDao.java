package com.winterframework.firefrog.game.dao;

import com.winterframework.firefrog.game.dao.vo.GameReportIssue;
import com.winterframework.firefrog.game.web.dto.GameRiskReportRequest;
import com.winterframework.modules.page.Page;
import com.winterframework.modules.page.PageRequest;
import com.winterframework.orm.dal.ibatis3.BaseDao;


/** 
* @ClassName IGameReportDao 
* @Description 游戏报表 
* @author  hugh
* @date 2014年4月25日 下午4:09:55 
*  
*/
public interface IGameReportIssueDao extends BaseDao<GameReportIssue>{
	/** 
	* @Title: queryRiskGameIssues 
	* @Description: 查询报表list 
	* @param pr
	* @return
	*/
	Page<GameReportIssue> queryGameReportIssues(PageRequest<GameRiskReportRequest> pr);
	

	GameReportIssue getGameReportIssue(Long lotteryId, Long issueCode);
}
