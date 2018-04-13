package com.winterframework.firefrog.game.service;

import java.util.List;

import com.winterframework.firefrog.common.util.GameContext;
import com.winterframework.firefrog.game.dao.vo.GameControlEvent;
import com.winterframework.firefrog.game.dao.vo.GameFundQueue;

 
/**
 * 资金请求队列服务接口
 * @ClassName
 * @Description
 * @author ibm
 * 2014年12月15日
 */
public interface IGameFundQueueService { 
	////////////////////////行为----begin////////////////////////// 
	public GameFundQueue getById(GameContext ctx,Long id) throws Exception;
	public int save(GameContext ctx,GameFundQueue fundQueue) throws Exception;
	int saveBatch(GameContext ctx,List<GameFundQueue> fundQueueList) throws Exception;
	int saveBatch2(GameContext ctx,List<GameFundQueue> fundQueueList) throws Exception;
	public int remove(GameContext ctx,Long id) throws Exception;
	////////////////////////行为----end//////////////////////////  	
		
	////////////////////////业务服务----begin////////////////////////// 
	/**
	 * 获取未执行的记录
	 * @param ctx
	 * @return
	 * @throws Exception
	 */
	List<GameFundQueue> getUnexecInLine(GameContext ctx) throws Exception;
	List<String> getOrderCodeListUnexecInLine(GameContext ctx) throws Exception;
	List<GameFundQueue> getUnexecInLineByOrderCode(GameContext ctx,String orderCode) throws Exception;
	List<GameFundQueue> getUnexecInLineByOrderCodeList(GameContext ctx,List<String> orderCodeList) throws Exception;
	List<GameFundQueue> getUnexecInLineByRowCount(GameContext ctx,Integer rowCount) throws Exception;
	
	/**
	 * 执行资金请求服务
	 * @param event
	 * @throws Exception
	 */
	void doRequest(GameControlEvent event) throws Exception;
	void doRequestNew(GameControlEvent event) throws Exception;
	void fundRequestByOrder(GameContext ctx,String orderCode) throws Exception;
	void fundRequestByOrderList(GameContext ctx,List<String> orderCodeList) throws Exception;
	void fundRequestSubBatch(GameContext ctx,List<GameFundQueue> fundQueueList) throws Exception;
	 
	////////////////////////业务服务----end////////////////////////// 
}
