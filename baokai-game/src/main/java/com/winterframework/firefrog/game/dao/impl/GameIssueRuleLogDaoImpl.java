package com.winterframework.firefrog.game.dao.impl;

import org.springframework.stereotype.Repository;

import com.winterframework.firefrog.game.dao.IGameIssueRuleLogDao;
import com.winterframework.firefrog.game.dao.vo.GameIssueRuleLog;
import com.winterframework.orm.dal.ibatis3.BaseIbatis3Dao;

/**
 * 
* @ClassName: GameIssueRuleLogDaoImpl 
* @Description: 奖期规则日志 
* @author Richard
* @date 2013-11-14 下午5:39:57 
*
 */
@Repository("gameIssueRuleLogDaoImpl")
public class GameIssueRuleLogDaoImpl extends BaseIbatis3Dao<GameIssueRuleLog> implements IGameIssueRuleLogDao {


}
