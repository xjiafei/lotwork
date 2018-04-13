package com.winterframework.firefrog.game.service.impl;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.winterframework.firefrog.common.util.GameContext;
import com.winterframework.firefrog.game.dao.IGameWarnUserDao;
import com.winterframework.firefrog.game.dao.vo.GameWarnUser;
import com.winterframework.firefrog.game.service.IGameWarnUserService;

@Service("gameWarnUserServiceImpl")
@Transactional(rollbackFor = Exception.class)
public class GameWarnUserServiceImpl implements IGameWarnUserService {
	
	@Resource(name = "gameWarnUserDaoImpl")
	private IGameWarnUserDao gameWarnUserDao;
 
	@Override
	public GameWarnUser getByLotteryIssueUserId(GameContext ctx,
			Long lotteryId, Long issueCode, Long userId) throws Exception {
		if(lotteryId==null || issueCode==null || userId==null) return null;
		return this.gameWarnUserDao.queryWarnUserByUserIdAndLotteryIdIssueCode(userId, lotteryId, issueCode);
	}
	@Override
	public GameWarnUser getLastWarnUser(GameContext ctx,Long userId,Long lotteryId,
			Long issueCode) throws Exception {
		if(lotteryId==null || issueCode==null || userId==null) return null;
		List<GameWarnUser> warnUserList=this.gameWarnUserDao.getLastWarnUserAfter(userId, lotteryId,issueCode);
		GameWarnUser warnUser=null;
		if(warnUserList==null || warnUserList.size()==0){
			warnUserList=this.gameWarnUserDao.getLastWarnUserBefore(userId, lotteryId, issueCode);
		} 
		if(warnUserList!=null && warnUserList.size()>0){
			Collections.sort(warnUserList, new Comparator<GameWarnUser>(){
				@Override
				public int compare(GameWarnUser o1, GameWarnUser o2) { 
					//倒序（连续的奖期中，计数最大的就是最近中奖的）
					return o2.getContinuousWinsIssue().compareTo(o1.getContinuousWinsIssue());
				}
			});
			warnUser=warnUserList.get(0);
		}
		return warnUser;
	} 

}
