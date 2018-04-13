package com.winterframework.firefrog.game.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.winterframework.firefrog.game.dao.IGameAwardDao;
import com.winterframework.firefrog.game.dao.vo.GameAward;
import com.winterframework.firefrog.game.service.IGameAwardService;
 
/**
 * 奖金服务
 * @ClassName
 * @Description
 * @author ibm
 * 2014年9月28日
 */
@Service("gameAwardServiceImpl")
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class GameAwardServiceImpl implements IGameAwardService {
	private static final Logger log = LoggerFactory.getLogger(GameAwardServiceImpl.class);

	@Resource(name = "gameAwardDaoImpl")
	private IGameAwardDao gameAwardDao; 
	
	@Override
	public GameAward getValidByLotteryIdAndGroupIdAndBetTypeCode(
			Long lotteryId, Long awardGroupId, String betTypeCode)
			throws Exception {
		return gameAwardDao.getGameAwardByBetTypeAndLotteryId(lotteryId,betTypeCode, awardGroupId);
	} 
	@Override
	public List<GameAward> getValidByLotteryIdAndGroupIdAndBetTypeCodeParent(
			Long lotteryId, Long awardGroupId, String betTypeCode)
			throws Exception {
		return gameAwardDao.getValidByLotteryIdAndGroupIdAndBetTypeCodeParent(lotteryId, awardGroupId, betTypeCode);
	}
	public GameAward getMaxBonusAwardByLotteryIdAndGroupIdAndBetTypeCodeParent(Long lotteryId, Long awardGroupId, String betTypeCode) throws Exception {
		List<GameAward>  awardList=getValidByLotteryIdAndGroupIdAndBetTypeCodeParent(lotteryId, awardGroupId, betTypeCode);
		if(null!=awardList && awardList.size()>0){
			GameAward tempAward=awardList.get(0);
			Long maxBonus=tempAward.getActualBonus();
			GameAward award=null;
			List<Long> lhcMultBonus= new ArrayList<Long>();
			for(int i=1;i<awardList.size();i++){
				award=awardList.get(i);
				if(award.getActualBonus()>maxBonus){					
					maxBonus= award.getActualBonus();
					tempAward=award;
				}
				
			}
			if(Long.valueOf(99701).equals(lotteryId)){
				for(int i=0;i<awardList.size();i++){
					lhcMultBonus.add(awardList.get(i).getActualBonus());
				}
				
			}
			
			tempAward.setLhcMultBonus(lhcMultBonus);
			return tempAward;
		}
		return null;
	};
}
