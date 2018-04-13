package com.winterframework.firefrog.game.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.winterframework.firefrog.common.util.DateUtils;
import com.winterframework.firefrog.common.util.GameContext;
import com.winterframework.firefrog.game.dao.IGameTrendTaskDao;
import com.winterframework.firefrog.game.dao.vo.GameTrendTask;
import com.winterframework.firefrog.game.service.IGameTrendTaskService;


/**
 * 走势图任务服务实现类
 * @ClassName
 * @Description
 * @author ibm
 * 2014年10月12日
 */	
@Service("gameTrendTaskServiceImpl") 
@Transactional
public class GameTrendTaskServiceImpl implements IGameTrendTaskService {
	@Resource(name="gameTrendTaskDaoImpl")
	private IGameTrendTaskDao gameTrendTaskDao; 
	 
	@Override
	public GameTrendTask getById(GameContext ctx, Long id) throws Exception {
		return gameTrendTaskDao.getById(id);
	}	
	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
	public int save(GameContext ctx, GameTrendTask trendTask) throws Exception {
		if(null==trendTask) return 0;
		if(null!=trendTask.getId()){
			trendTask.setUpdateTime(DateUtils.currentDate());
			gameTrendTaskDao.update(trendTask);
		}else{
			trendTask.setCreateTime(DateUtils.currentDate());
			gameTrendTaskDao.insert(trendTask);
		}
		return 1;
	}
}
