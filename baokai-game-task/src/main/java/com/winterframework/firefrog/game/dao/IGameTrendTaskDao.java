package com.winterframework.firefrog.game.dao;

import com.winterframework.firefrog.game.dao.vo.GameTrendTask;
import com.winterframework.orm.dal.ibatis3.BaseDao;

/**
 * 走势图任务DAO接口
 * @ClassName
 * @Description
 * @author ibm
 * 2014年10月12日
 */
public interface IGameTrendTaskDao extends BaseDao<GameTrendTask> {
	GameTrendTask getByLotteryIdAndIssueCode(Long lotteryId,Long issueCode) throws Exception;
}
