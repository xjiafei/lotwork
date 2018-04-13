package com.winterframework.firefrog.game.service;

import com.winterframework.firefrog.game.dao.vo.GameRiskIssue;
import com.winterframework.firefrog.game.web.dto.GameRiskIssueListQueryRequest;
import com.winterframework.modules.page.Page;
import com.winterframework.modules.page.PageRequest;

/** 
* @ClassName IGameIssueService 
* @Description 审核奖期 
* @author  hugh
* @date 2014年4月9日 下午3:22:42 
*  
*/
public interface IGameIssueService {

	/** 
	* @Title: queryRiskGameIssues 
	* @Description: 查询审核奖期list 
	* @param pr
	* @return
	*/
	Page<GameRiskIssue> queryRiskGameIssues(PageRequest<GameRiskIssueListQueryRequest> pr);
	
	public void setOperator(GameRiskIssue gameIssue) throws Exception;
}
