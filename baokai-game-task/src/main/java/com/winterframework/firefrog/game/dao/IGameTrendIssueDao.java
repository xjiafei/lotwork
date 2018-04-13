package com.winterframework.firefrog.game.dao;

import com.winterframework.firefrog.game.dao.vo.GameTrendIssue;
import com.winterframework.orm.dal.ibatis3.BaseDao;

 
/**
 * 开奖号码走势图奖期DAO接口
 * @ClassName
 * @Description
 * @author ibm
 * 2014年10月23日
 */
public interface IGameTrendIssueDao extends BaseDao<GameTrendIssue> {
 
	/**
	 * 根据彩种和奖期
	 * @return
	 * @throws Exception
	 */
	GameTrendIssue getByLotteryIdAndIssueCode(Long lotteryId , Long issueCode) throws Exception;
}
