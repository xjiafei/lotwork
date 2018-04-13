package com.winterframework.firefrog.game.service.impl;

import java.util.Iterator;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.winterframework.firefrog.common.util.DateUtils;
import com.winterframework.firefrog.game.dao.IGameUserAwardDao;
import com.winterframework.firefrog.game.dao.vo.GameUserAward;
import com.winterframework.firefrog.game.entity.GameBetType;
import com.winterframework.firefrog.game.service.IGameUserAwardGroupService;
import com.winterframework.firefrog.game.service.IGameUserAwardService;
 
/**
 * 用户奖金服务实现类
 * @ClassName
 * @Description
 * @author ibm
 * 2014年11月13日
 */
@Service("gameUserAwardServiceImpl")
@Transactional(rollbackFor = Exception.class)
public class GameUserAwardServiceImpl implements IGameUserAwardService {

	private Logger log = LoggerFactory.getLogger(GameUserAwardServiceImpl.class);

	@Resource(name = "gameUserAwardDaoImpl")
	private IGameUserAwardDao gameUserAwardDao;
	@Resource(name = "gameUserAwardGroupServiceImpl")
	private IGameUserAwardGroupService gameUserAwardGroupService;
 
	@Override
	public int save(GameUserAward gameUserAward) throws Exception {
		return this.gameUserAwardDao.update(gameUserAward); 
	}
	@Override
	public int remove(Long id) throws Exception {
		return this.gameUserAwardDao.delete(id); 
	}
	@Override
	public List<GameUserAward> getByLotteryIdAndUserId(Long lotteryId,
			Long userId) throws Exception {
		return this.gameUserAwardDao.getByLotteryIdAndUserId(lotteryId, userId);
	} 
	@Override
	public List<GameUserAward> getByLotteryIdAndUserIdAndAwardGroupId(Long lotteryId,
			Long userId,Long userAwardGroupId) throws Exception {
		return this.gameUserAwardDao.getByLotteryIdAndUserIdAndAwardGroupId(lotteryId, userId,userAwardGroupId);
	}
	
	@Override
	public List<GameUserAward> getNormalByLotteryIdAndUserId(Long lotteryId,Long userId,Long userAwardGroupId) throws Exception {
		List<GameUserAward> userAwardList=this.getByLotteryIdAndUserIdAndAwardGroupId(lotteryId, userId,userAwardGroupId);
		if(userAwardList!=null && userAwardList.size()>0){
			Iterator<GameUserAward> iter= userAwardList.iterator();
			while(iter.hasNext()){
				GameUserAward userAward=iter.next();
				if(userAward.getStatus().intValue()!=GameUserAward.Status.NORMAL.getValue()){
					iter.remove();
				}
			} 
		}
		return userAwardList;
	}
	@Override
	public GameUserAward getByLotteryIdAndUserIdAndBetTypeCode(Long lotteryId,
			Long userId, String betTypeCode) throws Exception {
		if(lotteryId==null || userId==null || StringUtils.isEmpty(betTypeCode))  return null;
		return this.gameUserAwardDao.getByLotteryIdAndUserIdAndBetTypeCode(lotteryId, userId, betTypeCode);
	}
	
	@Override
	public int deleteByAwardBetType(Long lotteryId,Long userId,int awardBetType) throws Exception {
		int retCount=0;
		List<GameUserAward> userAwardList=this.getByLotteryIdAndUserId(lotteryId, userId);
		if(userAwardList!=null && userAwardList.size()>0){
			for(GameUserAward userAward:userAwardList){
				if(userAward.getBetTypeCode()!=null){  
					GameBetType betType=new GameBetType(userAward.getBetTypeCode());
					if(this.gameUserAwardGroupService.getGameAwardBetType(betType).getValue()==awardBetType){ 
						retCount+=this.remove(userAward.getId());		
					}
				}
			}
		}
		return retCount;
	}
	@Deprecated
	@Override
	public int updateUserAwardStatusByAwardBetType(Long lotteryId,Long userId,int awardBetType, int status) throws Exception {
		int retCount=0;
		List<GameUserAward> userAwardList=this.getNormalByLotteryIdAndUserId(lotteryId, userId,null);
		if(userAwardList!=null && userAwardList.size()>0){ 
			for(GameUserAward userAward:userAwardList){
				if(userAward.getBetTypeCode()!=null){  
					GameBetType betType=new GameBetType(userAward.getBetTypeCode());
					if(this.gameUserAwardGroupService.getGameAwardBetType(betType).getValue()==awardBetType){ 
						userAward.setStatus(status);
						userAward.setUpdateTime(DateUtils.currentDate());
						this.save(userAward);
						retCount++;
					}
				}
			}
		}
		return retCount;
	}
	@Override
	public int batchModifyGameUserAward(List<GameUserAward> userAwardList)
			throws Exception {
		int retCount=0;
		if(userAwardList!=null && userAwardList.size()>0){
			for(GameUserAward userAward:userAwardList){
				userAward.setUpdateTime(DateUtils.currentDate());
				this.saveByUserIdLotteryIdBetTypeCode(userAward);
				retCount++;
			}
		} 
		return retCount;
	}
	@Override
	public int saveByUserIdLotteryIdBetTypeCode(GameUserAward userAward) throws Exception { 
		return this.gameUserAwardDao.updateByUserIdLotteryIdBetTypeCode(userAward);
	}
}


