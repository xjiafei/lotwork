package com.winterframework.firefrog.game.dao;

import com.winterframework.firefrog.game.dao.vo.GameIssue;
import com.winterframework.firefrog.game.dao.vo.GameRiskIssue;
import com.winterframework.firefrog.game.web.dto.GameRiskIssueListQueryRequest;
import com.winterframework.modules.page.Page;
import com.winterframework.modules.page.PageRequest;
import com.winterframework.orm.dal.ibatis3.BaseDao;

/** 
* @ClassName IGameIssueDao 
* @Description 查询审核奖期 
* @author  hugh
* @date 2014年4月9日 下午3:20:56 
*  
*/
public interface IGameIssueDao extends BaseDao<GameIssue> {

	/**
	 * 
	* @Title: getGameIssueByIssueCode 
	* @Description: 根据奖期获取奖期信息
	* @param issueCode
	* @return
	* @throws Exception
	 */
	public GameIssue getGameIssueByIssueCode(Long issueCode) throws Exception;

	
	/** 
	* @Title: getGameRiskIssue 
	* @Description: 查询审核和奖期
	* @param lotteryId
	* @param issueCode
	* @return
	 * @throws Exception 
	*/
	GameRiskIssue getGameRiskIssue(Long lotteryId,Long issueCode) throws Exception;
	
	/** 
	* @Title: queryRiskGameIssues 
	* @Description: 查询审核奖期list 
	* @param pr
	* @return
	*/
	public Page<GameRiskIssue> queryRiskGameIssues(PageRequest<GameRiskIssueListQueryRequest> pr);
	
	public void setOperator(GameRiskIssue gameIssue) throws Exception;

}
