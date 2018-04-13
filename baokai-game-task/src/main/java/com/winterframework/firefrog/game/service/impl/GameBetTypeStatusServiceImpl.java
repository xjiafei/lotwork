package com.winterframework.firefrog.game.service.impl;

import com.winterframework.firefrog.game.dao.IGameBettypeStatusDao;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.winterframework.firefrog.game.dao.vo.GameBettypeStatus;
import com.winterframework.firefrog.game.enums.Separator;
import com.winterframework.firefrog.game.service.IGameBetTypeStatusService;
import com.winterframework.firefrog.game.util.SuperPairUtil;
 
/**
 * 投注方式状态服务
 * @ClassName
 * @Description
 * @author ibm
 * 2014年9月28日
 */
@Service("gameBetTypeStatusServiceImpl")
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class GameBetTypeStatusServiceImpl implements IGameBetTypeStatusService {

	private static final Logger log = LoggerFactory.getLogger(GameBetTypeStatusServiceImpl.class);

	@Resource(name = "gameBettypeStatusDaoImpl")
	private IGameBettypeStatusDao gameBettypeStatusDao; 
	
	@Override
	public GameBettypeStatus getGameBetTypeStatusByLotteryIdAndBetTypeCode(
			Long lotteryId, String betTypeCode) throws Exception { 
		return gameBettypeStatusDao.getGameBetTypeStatusByLotteryIdAndBetTypeCode(lotteryId, betTypeCode);
	}
	@Override
	public String getAlias(Long lotteryId, String betTypeCode) throws Exception {
		GameBettypeStatus betTypeStatus=gameBettypeStatusDao.getGameBetTypeStatusByLotteryIdAndBetTypeCode(lotteryId,betTypeCode); 
		if(betTypeStatus==null) return ""; 
		/*String superPair="";
		if(SuperPairUtil.isSuperPair(betTypeCode)){
			superPair=Separator.underline+SuperPairUtil.alias;
		}*/
		//return betTypeStatus.getGroupCodeTitle()+superPair+betTypeStatus.getSetCodeTitle()+betTypeStatus.getMethodCodeTitle();
		return betTypeStatus.getGroupCodeTitle()+betTypeStatus.getSetCodeTitle()+betTypeStatus.getMethodCodeTitle();
	}
	
}
