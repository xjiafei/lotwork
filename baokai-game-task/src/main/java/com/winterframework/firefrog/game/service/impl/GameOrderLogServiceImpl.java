package com.winterframework.firefrog.game.service.impl;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.winterframework.firefrog.common.util.GameContext;
import com.winterframework.firefrog.game.dao.IGameOrderLogDao;
import com.winterframework.firefrog.game.dao.vo.GameOrderLog;
import com.winterframework.firefrog.game.service.IGameOrderLogService;
 
/**
 * 游戏订单操作日志服务
 * @ClassName
 * @Description
 * @author ibm
 * 2014年10月10日
 */
@Service("gameOrderLogServiceImpl")
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class GameOrderLogServiceImpl  implements IGameOrderLogService {

	private static final Logger log = LoggerFactory.getLogger(GameOrderLogServiceImpl.class);

	@Resource(name = "gameOrderLogDaoImpl")
	private IGameOrderLogDao gameOrderLogDao; 
	
	@Override
	public int save(GameContext ctx,GameOrderLog orderLog) throws Exception { 
		if(orderLog==null) return 0;
		if(orderLog.getId()==null){
			return this.gameOrderLogDao.insert(orderLog);
		} 
		return 0; 
	}
}
