package com.winterframework.firefrog.game.dao;

import java.util.List;

import com.winterframework.firefrog.common.util.GameContext;
import com.winterframework.firefrog.game.dao.vo.GameFundQueue;
import com.winterframework.orm.dal.ibatis3.BaseDao;

 

/**
 * 资金请求队列DAO接口
 * @ClassName
 * @Description
 * @author ibm
 * 2014年12月15日
 */
public interface IGameFundQueueDao extends BaseDao<GameFundQueue> {
	/**
	 * 获取未执行的记录
	 * @param ctx
	 * @return
	 * @throws Exception
	 */
	List<GameFundQueue> getByStatus(Integer status) throws Exception;
	List<String> getOrderCodeListByStatus(GameContext ctx,Integer status) throws Exception;
	List<GameFundQueue> getByOrderCodeAndStatus(GameContext ctx,String orderCode,Integer status) throws Exception;
	List<GameFundQueue> getByOrderCodeListAndStatus(GameContext ctx,List<String> orderCodeList,Integer status) throws Exception;
	List<GameFundQueue> getByStatusAndRowCount(Integer status,Integer rowCount) throws Exception;
}
