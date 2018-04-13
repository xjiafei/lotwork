package com.winterframework.firefrog.game.service.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.winterframework.firefrog.game.dao.IGameUserAwardGroupDao;
import com.winterframework.firefrog.game.dao.vo.GameUserAwardGroup;
import com.winterframework.firefrog.game.entity.GameBetType;
import com.winterframework.firefrog.game.enums.GameAwardBetType;
import com.winterframework.firefrog.game.enums.YesNo;
import com.winterframework.firefrog.game.service.IGameUserAwardGroupService;
import com.winterframework.firefrog.game.service.assist.award.SpecialLotteryGameAwardFactory;
import com.winterframework.firefrog.game.service.assist.bet.ILotteryKeyFactor;
import com.winterframework.firefrog.game.service.assist.bet.LotteryPlayMethodKeyGenerator;
import com.winterframework.firefrog.game.util.LHCUtil;
import com.winterframework.firefrog.game.util.SuperPairUtil;

@Repository("gameUserAwardGroupServiceImpl")
public class GameUserAwardGroupServiceImpl implements
		IGameUserAwardGroupService {
	@Resource(name="gameUserAwardGroupDaoImpl")
	IGameUserAwardGroupDao gameUserAwardGroupDao;
	@Resource(name = "specialLotteryGameAwardFactory")
	private SpecialLotteryGameAwardFactory specialLotteryGameAwardFactory;
	
	@Override
	public List<GameUserAwardGroup> getUserAwardGroupByLotteryIdAndUserId(
			Long lotteryId, Long userId) throws Exception { 
		return gameUserAwardGroupDao.getUserAwardGroupByLotteryIdAndUserId(lotteryId, userId);
	} 
	@Override
	public GameAwardBetType getGameAwardBetType(GameBetType betType) throws Exception {
		LotteryPlayMethodKeyGenerator keyGenerator = new LotteryPlayMethodKeyGenerator(null,
				betType.getGameGroupCode(), betType.getGameSetCode(), betType.getBetMethodCode(), "_$_");
		List<ILotteryKeyFactor> specialList = specialLotteryGameAwardFactory.getObject(keyGenerator);
		if (specialList != null && specialList.size() > 0) {
			return GameAwardBetType.THREE_ONE;
		}else if(SuperPairUtil.isSuperPair(betType.getGameGroupCode())){
			return GameAwardBetType.SUPER;
		} else if(LHCUtil.isLhcBetTypeCode(betType.getBetTypeCode())){
			return LHCUtil.findLhcBetTypeCode(betType.getBetTypeCode()).getGameAwardBetType();
		} else {
			return GameAwardBetType.DIRECT;
		}
	}
	@Override
	public Long getRetPointByUserIdAndLotteryIdAndBetTypeCode(Long userId,
			Long lotteryId, String betTypeCode) throws Exception {
		Map<String, BigDecimal> mapPoint=gameUserAwardGroupDao.getUserAwardPoint(betTypeCode, userId, lotteryId);
		if(null!=mapPoint && mapPoint.size()>0){
			return mapPoint.get("RETVALUE").longValue();
		}
		GameUserAwardGroup userAwardGroup=getBetedByUserIdAndLotteryId(userId, lotteryId);
		GameBetType betType = new GameBetType(betTypeCode);
		GameAwardBetType awardBetType=getGameAwardBetType(betType);
		//區分99601,99602,99603的猜一個號返點抓取
		if((lotteryId == 99601 || lotteryId == 99602 || lotteryId == 99603) && betTypeCode.equals("42_36_78")){
			return userAwardGroup.getSbThreeoneRet();
		}else if(awardBetType.equals(GameAwardBetType.THREE_ONE)){
			return userAwardGroup.getThreeoneRet();
		}else if(awardBetType.equals(GameAwardBetType.SUPER)){
			return userAwardGroup.getSuperRet();
		}else if(awardBetType.equals(GameAwardBetType.LHC_YEAR)){
			return userAwardGroup.getLhcYear();
		}else if(awardBetType.equals(GameAwardBetType.LHC_COLOR)){
			return userAwardGroup.getLhcColor();
		}else if(awardBetType.equals(GameAwardBetType.LHC_FLATCODE)){
			return userAwardGroup.getLhcFlatcode();
		}else if(awardBetType.equals(GameAwardBetType.LHC_HALFWAVE)){
			return userAwardGroup.getLhcHalfwave();
		}else if(awardBetType.equals(GameAwardBetType.LHC_ONEYEAR)){
			return userAwardGroup.getLhcOneyear();
		}else if(awardBetType.equals(GameAwardBetType.LHC_NOTIN)){
			return userAwardGroup.getLhcNotin();
		}else if(awardBetType.equals(GameAwardBetType.LHC_CONTINUEIN23)){
			return userAwardGroup.getLhcContinuein23();
		}else if(awardBetType.equals(GameAwardBetType.LHC_CONTINUEIN4)){
			return userAwardGroup.getLhcContinuein4();
		}else if(awardBetType.equals(GameAwardBetType.LHC_CONTINUEIN5)){
			return userAwardGroup.getLhcContinuein5();
		}else if(awardBetType.equals(GameAwardBetType.LHC_CONTINUENOTIN23)){
			return userAwardGroup.getLhcContinuenotin23();
		}else if(awardBetType.equals(GameAwardBetType.LHC_CONTINUENOTIN4)){
			return userAwardGroup.getLhcContinuenotin4();
		}else if(awardBetType.equals(GameAwardBetType.LHC_CONTINUENOTIN5)){
			return userAwardGroup.getLhcContinuenotin5();
		}else if(awardBetType.equals(GameAwardBetType.LHC_CONTINUECODE)){
			return userAwardGroup.getLhcContinuecode();
		}else{
			return userAwardGroup.getDirectRet();
		}
	}
	@Override
	public Long getRetPointByUserIdAndLotteryIdAndBetTypeCodeAndMultiple(Long userId,
			Long lotteryId, String betTypeCode,Long odds) throws Exception {
		GameUserAwardGroup userAwardGroup=getBetedByUserIdAndLotteryId(userId, lotteryId);
		GameBetType betType = new GameBetType(betTypeCode);
		GameAwardBetType awardBetType=getGameAwardBetType(betType);
		if(awardBetType.equals(GameAwardBetType.THREE_ONE)){
			return userAwardGroup.getThreeoneRet();
		}else if(awardBetType.equals(GameAwardBetType.SUPER)){
			return userAwardGroup.getSuperRet();
		}else if(awardBetType.equals(GameAwardBetType.LHC_YEAR)){
			return userAwardGroup.getLhcYear();
		}else if(awardBetType.equals(GameAwardBetType.LHC_COLOR)){
			return userAwardGroup.getLhcColor();
		}else if(awardBetType.equals(GameAwardBetType.LHC_FLATCODE)){
			return userAwardGroup.getLhcFlatcode();
		}else if(awardBetType.equals(GameAwardBetType.LHC_HALFWAVE)){
			return userAwardGroup.getLhcHalfwave();
		}else if(awardBetType.equals(GameAwardBetType.LHC_ONEYEAR)){
			return userAwardGroup.getLhcOneyear();
		}else if(awardBetType.equals(GameAwardBetType.LHC_NOTIN)){
			return userAwardGroup.getLhcNotin();
		}else if(awardBetType.equals(GameAwardBetType.LHC_CONTINUEIN23)){
			return userAwardGroup.getLhcContinuein23();
		}else if(awardBetType.equals(GameAwardBetType.LHC_CONTINUEIN4)){
			return userAwardGroup.getLhcContinuein4();
		}else if(awardBetType.equals(GameAwardBetType.LHC_CONTINUEIN5)){
			return userAwardGroup.getLhcContinuein5();
		}else if(awardBetType.equals(GameAwardBetType.LHC_CONTINUENOTIN23)){
			return userAwardGroup.getLhcContinuenotin23();
		}else if(awardBetType.equals(GameAwardBetType.LHC_CONTINUENOTIN4)){
			return userAwardGroup.getLhcContinuenotin4();
		}else if(awardBetType.equals(GameAwardBetType.LHC_CONTINUENOTIN5)){
			return userAwardGroup.getLhcContinuenotin5();
		}else if(awardBetType.equals(GameAwardBetType.LHC_CONTINUECODE)){
			return userAwardGroup.getLhcContinuecode();
		}
		int defOdd = 40;
		odds = odds / 10000;
		for (Long i = userAwardGroup.getDirectRet() ; i >= 0; i -= 200) {
			if(defOdd == odds){
				return i;
			}
			defOdd++;
		}
		throw new Exception("无法取得用户(" + userId + ")彩种(" + lotteryId + ")玩法(" + betTypeCode + ")返点。来源直选返点(" + odds + ")");
	}
	
	@Override
	public GameUserAwardGroup getBetedByUserIdAndLotteryId(Long userId, Long lotteryId) throws Exception {
		List<GameUserAwardGroup> userAwardGroupList=getUserAwardGroupByLotteryIdAndUserId(lotteryId,userId);
		if(null!=userAwardGroupList){
			for(GameUserAwardGroup userAwardGroup:userAwardGroupList){
				if(YesNo.YES.getValue()==userAwardGroup.getBetType().intValue()){
					return userAwardGroup;
				}
			}
		}
		return null;
	}
}
