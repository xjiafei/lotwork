package com.winterframework.firefrog.game.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.winterframework.firefrog.common.util.DateUtils;
import com.winterframework.firefrog.common.util.GameContext;
import com.winterframework.firefrog.game.dao.IGameTrendBetDao;
import com.winterframework.firefrog.game.dao.vo.GameTrendBet;
import com.winterframework.firefrog.game.service.IGameTrendBetService;


 
/**
 * 走势图奖期服务实现类
 * @ClassName
 * @Description
 * @author ibm
 * 2014年10月28日
 */
@Service("gameTrendBetServiceImpl") 
@Transactional(rollbackFor = Exception.class)
public class GameTrendBetServiceImpl implements IGameTrendBetService {
	@Resource(name="gameTrendBetDaoImpl")
	private IGameTrendBetDao gameTrendBetDao; 
	  
	@Override
	public int save(GameContext ctx, GameTrendBet trendBet)
			throws Exception {
		if(trendBet==null) return 0;
		if(trendBet.getId()!=null){
			trendBet.setUpdateTime(DateUtils.currentDate());
			this.gameTrendBetDao.update(trendBet);
		}else{
			trendBet.setCreateTime(DateUtils.currentDate());
			this.gameTrendBetDao.insert(trendBet);
		}
		return 1;
	}
	@Override
	public int save(GameContext ctx, List<GameTrendBet> trendBetList)
			throws Exception {
		if(trendBetList==null || trendBetList.size()==0) return 0;
		List<GameTrendBet> updateList=null;
		List<GameTrendBet> insertList=null;
		for(GameTrendBet trendBet:trendBetList){
			if(trendBet!=null){
				if(trendBet.getId()!=null){
					if(updateList==null){
						updateList=new ArrayList<GameTrendBet>();
					}
					trendBet.setUpdateTime(DateUtils.currentDate());
					updateList.add(trendBet);
				}else{
					if(insertList==null){
						insertList=new ArrayList<GameTrendBet>();
					}
					trendBet.setCreateTime(DateUtils.currentDate());
					insertList.add(trendBet);
				}
			}
		}
		if(insertList!=null && insertList.size()>0){
			this.gameTrendBetDao.insert(trendBetList);
		}
		if(updateList!=null && updateList.size()>0){
			for(GameTrendBet trendBet:updateList){				
				this.gameTrendBetDao.update(trendBet);
			}
		} 
		return trendBetList.size();
	}
	@Override
	public int remove(GameContext ctx, Long id) throws Exception { 
		return this.gameTrendBetDao.delete(id);
	}
	@Override
	public GameTrendBet getByLotteryIdAndBetTypeAndType(GameContext ctx,
			Long lotteryId, Integer groupCode, Integer setCode, Integer methodCode,Integer type)
			throws Exception {
		return this.gameTrendBetDao.getByLotteryIdAndBetTypeAndType(lotteryId,groupCode,setCode,methodCode,type);
	} 
	
	@Override
	public List<GameTrendBet> getByLotteryId(GameContext ctx, Long lotteryId)
			throws Exception {
		return this.gameTrendBetDao.getByLotteryId(lotteryId);
	}
}
