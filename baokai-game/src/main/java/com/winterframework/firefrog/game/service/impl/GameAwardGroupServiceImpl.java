package com.winterframework.firefrog.game.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.winterframework.firefrog.common.util.UserChainUtil;
import com.winterframework.firefrog.game.dao.IGameAwardCheckDao;
import com.winterframework.firefrog.game.dao.IGameAwardDao;
import com.winterframework.firefrog.game.dao.IGameAwardGroupCheckDao;
import com.winterframework.firefrog.game.dao.IGameAwardGroupDao;
import com.winterframework.firefrog.game.dao.IGameBettypeAssistDao;
import com.winterframework.firefrog.game.dao.IGameBettypeStatusDao;
import com.winterframework.firefrog.game.dao.IGameSeriesDao;
import com.winterframework.firefrog.game.dao.IGameUserAwardGroupDao;
import com.winterframework.firefrog.game.dao.vo.GameAward;
import com.winterframework.firefrog.game.dao.vo.GameAwardCheck;
import com.winterframework.firefrog.game.dao.vo.GameAwardGroup;
import com.winterframework.firefrog.game.dao.vo.GameAwardGroupCheck;
import com.winterframework.firefrog.game.dao.vo.GameBettypeAssist;
import com.winterframework.firefrog.game.dao.vo.GameBettypeStatus;
import com.winterframework.firefrog.game.dao.vo.GameUserAward;
import com.winterframework.firefrog.game.dao.vo.GameUserAwardGroup;
import com.winterframework.firefrog.game.dao.vo.VOConverter;
import com.winterframework.firefrog.game.entity.GameAwardEntity;
import com.winterframework.firefrog.game.entity.GameBetType;
import com.winterframework.firefrog.game.entity.GameAwardEntity.GameAwardStatus;
import com.winterframework.firefrog.game.entity.GameAwardGroupEntity;
import com.winterframework.firefrog.game.entity.GameAwardGroupEntity.Status;
import com.winterframework.firefrog.game.entity.GameAwardGroupEntity.SysAwardGroup;
import com.winterframework.firefrog.game.enums.GameAwardBetType;
import com.winterframework.firefrog.game.exception.GameAwardGroupExistErrorException;
import com.winterframework.firefrog.game.service.IGameAwardGroupService;
import com.winterframework.firefrog.game.service.IGameUserAwardGroupService;
import com.winterframework.firefrog.game.service.IGameUserAwardService;
import com.winterframework.firefrog.game.util.SuperPairUtil;
import com.winterframework.firefrog.game.web.dto.AssistBmBonusStruc;
import com.winterframework.firefrog.game.web.dto.AwardBonusStruc;
import com.winterframework.firefrog.game.web.dto.CreateGameAwardGroupDTO;
import com.winterframework.firefrog.game.web.dto.EditGameAwardGroupDTO;
import com.winterframework.firefrog.user.dao.IUserCustomerDao;
import com.winterframework.firefrog.user.entity.User;

/**
 * 奖金组IGameAwardGroupService Service接口实现类
 * @author Richard & Denny
 * @date 2013-12-27 下午4:16:54 
 */
@Service("gameAwardGroupServiceImpl")
@Transactional(rollbackFor = Exception.class)
public class GameAwardGroupServiceImpl implements IGameAwardGroupService {

	@Resource(name = "gameAwardGroupDaoImpl")
	private IGameAwardGroupDao awardGroupDao;

	@Resource(name = "gameAwardDaoImpl")
	private IGameAwardDao awardDao;

	@Resource(name = "gameAwardCheckDaoImpl")
	private IGameAwardCheckDao awardCheckDao;

	@Resource(name = "gameAwardGroupCheckDaoImpl")
	private IGameAwardGroupCheckDao awardGroupCheckDao;

	@Resource(name = "gameBettypeStatusDaoImpl")
	private IGameBettypeStatusDao bettypeStatusDao;

	@Resource(name = "gameBettypeAssistDaoimpl")
	private IGameBettypeAssistDao gameBettypeAssistDaoImpl;

	@Resource(name = "gameSeriesDaoImpl")
	private IGameSeriesDao gameSeriesDao;

	@Resource(name = "lotteryMap")
	private Map<String, String> awardMap;
	@Resource(name = "gameUserAwardServiceImpl")
	private IGameUserAwardService gameUserAwardService;
	@Resource(name = "userCustomerDaoImpl")
	private IUserCustomerDao customerDao;

	@Resource(name = "gameUserAwardGroupDaoImpl")
	private IGameUserAwardGroupDao gameUserAwardGroupDao;
	@Resource(name = "gameUserAwardGroupServiceImpl")
	private IGameUserAwardGroupService gameUserAwardGroupService;
	

	@Override
	public List<GameAwardGroupEntity> queryGameAwardGroupByLotteryId(Long LotteryId) throws Exception {

		return awardGroupDao.queryGameAwardGroupByLotteryId(LotteryId);
	}

	public List<GameAwardEntity> queryUserGameAward(Long lotteryId, Long awardGroupId, Long type, Long awardType,
			Long sysAwardGroupId, Long userId) throws Exception {
		List<GameAwardEntity> entityList = new ArrayList<GameAwardEntity>();
		Float miniLotteryProfit = gameSeriesDao.getMiniLotteryProfitByLotteryId(lotteryId);
		if (type == 0) {//type 0:總代,1:非總代
			awardGroupId = awardGroupId == null ? sysAwardGroupId : awardGroupId;
			GameAwardGroup group = awardGroupDao.getById(awardGroupId);
			List<GameAward> list = awardDao.queryGameAwardByGroupId(awardGroupId, lotteryId, 1);
			for (GameAward award : list) {
				if (lotteryId == 99109l && award.findGameGroupCode() == 31 && award.findGameSetCode() == 14) {
					award.setBetTypeCode("31_14_10");
				}
				GameBettypeStatus gameBettypeStatus = bettypeStatusDao.getGameBettypeStatusByLottery(lotteryId,
						award.findGameGroupCode(), award.findGameSetCode(), award.findBetMethodCode(),
						award.findMethodType());
				GameAwardEntity awardEntity=VOConverter.convertGameAward2Entity(award, group, miniLotteryProfit, gameBettypeStatus);
				setTopAgentPoint(awardEntity);
				entityList.add(awardEntity);
			}
		} else {
			if (awardGroupId == null) {
				awardGroupId = awardDao.queryUserAwardGroupIdBySysAwardGroupAndUser(sysAwardGroupId, userId);
			}
			List<GameAward> list = awardDao.queryUserGameAwardByGroupId(awardGroupId, lotteryId, 1);
			if (list != null && list.size() > 0) {
				//获取上级用户
				User user = this.customerDao.queryUserById(userId);
				if (user == null) {
					throw new Exception("user not exists(userId:" + userId + ")");
				}
				User superUser = null;
				if (user.getUserProfile() != null) {
					String account = user.getUserProfile().getAccount();
					String userChain = user.getUserProfile().getUserChain();
					String userAccount = UserChainUtil.getSuper(account, userChain);
					superUser = userAccount == null ? null : this.customerDao.queryUserByName(userAccount);
				}
				Long superUserId = superUser == null ? null : superUser.getId();

				for (GameAward award : list) {
					GameAwardGroup group = awardGroupDao.getById(award.getAwardGroupId());
					String betTypeCode = null;
					if (lotteryId == 99109l && award.findGameGroupCode() == 31 && award.findGameSetCode() == 14) {
						betTypeCode = "31_14_10";
						award.setBetTypeCode(betTypeCode);
					}
					GameBettypeStatus gameBettypeStatus = bettypeStatusDao.getGameBettypeStatusByLottery(lotteryId,
							award.findGameGroupCode(), award.findGameSetCode(), award.findBetMethodCode(),
							award.findMethodType());
					if (betTypeCode == null) {
						betTypeCode = award.findGameGroupCode() + "_" + award.findGameSetCode() + "_"
								+ award.findBetMethodCode();
					}
					if (superUser != null && superUser.getUserProfile().getUserLvl() == 0) { //总代
						GameAward generalAgentAward = this.awardDao.getGameAwardByBetTypeAndLotteryId(lotteryId,
								betTypeCode, sysAwardGroupId);
						award.setMaxRetValue(generalAgentAward == null ? award.getRetValue() : generalAgentAward
								.getRetValue());
					} else {
						GameUserAward superUserAward = this.gameUserAwardService.getByLotteryIdAndUserIdAndBetTypeCode(
								lotteryId, superUserId, betTypeCode);
						award.setMaxRetValue(superUserAward == null ? award.getRetValue() : superUserAward
								.getRetValue());
					}
					this.replaceRetValue(lotteryId, awardGroupId, superUserId, awardType, sysAwardGroupId, award);
					GameAwardEntity awardEntity=VOConverter.convertGameAward2Entity(award, group, miniLotteryProfit,
							gameBettypeStatus);
					setTopAgentPoint(awardEntity);
					entityList.add(awardEntity);
				}
			}
		}
		return removeGameAwardByAwardType(awardType, entityList);
	}

	/**
	 * 設定總代返點。
	 * @param awardEntity
	 * @throws Exception
	 */
	private void setTopAgentPoint(GameAwardEntity awardEntity) throws Exception {
		GameAwardBetType awardBetType=gameUserAwardGroupService.getGameAwardBetType(awardEntity.getGameBetType());
		if(awardBetType.getValue() == GameAwardBetType.THREE_ONE.getValue()){
			awardEntity.setTopAgentPoint(awardEntity.getThreeoneRet());
		}else if(awardBetType.getValue() == GameAwardBetType.SUPER.getValue()){
			awardEntity.setTopAgentPoint(awardEntity.getSuperRet());
		}else if(awardBetType.getValue() == GameAwardBetType.LHC_YEAR.getValue()){
			awardEntity.setTopAgentPoint(awardEntity.getLhcYear());
		}else if(awardBetType.getValue() == GameAwardBetType.LHC_COLOR.getValue()){
			awardEntity.setTopAgentPoint(awardEntity.getLhcColor());
		}else if(awardBetType.getValue() == GameAwardBetType.LHC_FLATCODE.getValue()){
			awardEntity.setTopAgentPoint(awardEntity.getLhcFlatcode());
		}else if(awardBetType.getValue() == GameAwardBetType.LHC_HALFWAVE.getValue()){
			awardEntity.setTopAgentPoint(awardEntity.getLhcHalfwave());
		}else if(awardBetType.getValue() == GameAwardBetType.LHC_ONEYEAR.getValue()){
			awardEntity.setTopAgentPoint(awardEntity.getLhcOneyear());
		}else if(awardBetType.getValue() == GameAwardBetType.LHC_NOTIN.getValue()){
			awardEntity.setTopAgentPoint(awardEntity.getLhcNotin());
		}else if(awardBetType.getValue() == GameAwardBetType.LHC_CONTINUEIN23.getValue()){
			awardEntity.setTopAgentPoint(awardEntity.getLhcContinuein23());
		}else if(awardBetType.getValue() == GameAwardBetType.LHC_CONTINUEIN4.getValue()){
			awardEntity.setTopAgentPoint(awardEntity.getLhcContinuein4());
		}else if(awardBetType.getValue() == GameAwardBetType.LHC_CONTINUEIN5.getValue()){
			awardEntity.setTopAgentPoint(awardEntity.getLhcContinuein5());
		}else if(awardBetType.getValue() == GameAwardBetType.LHC_CONTINUENOTIN23.getValue()){
			awardEntity.setTopAgentPoint(awardEntity.getLhcContinuenotin23());
		}else if(awardBetType.getValue() == GameAwardBetType.LHC_CONTINUENOTIN4.getValue()){
			awardEntity.setTopAgentPoint(awardEntity.getLhcContinuenotin4());
		}else if(awardBetType.getValue() == GameAwardBetType.LHC_CONTINUENOTIN5.getValue()){
			awardEntity.setTopAgentPoint(awardEntity.getLhcContinuenotin5());
		}else if(awardBetType.getValue() == GameAwardBetType.LHC_CONTINUECODE.getValue()){
			awardEntity.setTopAgentPoint(awardEntity.getLhcContinuecode());
		}else{
			awardEntity.setTopAgentPoint(awardEntity.getDirectRet());
		}
	}

	/**
	 * 
	 * @param lotteryid
	 * @param awardGroupId
	 * @param superUserId
	 * @param awardType 1:直選返點、2:不定位返點、3:所有返點、4:任選型返點、5:趣味型返點、6:混選返點、7:骰寶直選返點、8:超級2000返點、9:特肖返點、10:色波兩面返點、11:平碼返點、12:半波返點、13:一肖返點、14:不中返點、15:連肖(中)二三連肖返點、16:連肖(中)四連肖返點、17:連肖(中)五連肖返點、18:連肖(不中)二三連肖返點、19:連肖(不中)四連肖返點、20:連肖(不中)五連肖返點、21:連碼返點
	 * @param sysAwardGroupId
	 * @param gameAward
	 */
	private void replaceRetValue(Long lotteryid, Long awardGroupId, Long superUserId, Long awardType, Long sysAwardGroupId,
			GameAward gameAward) {
		if (gameAward.getRetValue() == null) {
			gameAward.setRetValue(-1L);
		}
		if (gameAward.getRetValue() == -1) {
			GameUserAwardGroup gameUserAwardGroup = gameUserAwardGroupDao.getById(awardGroupId);
			if (awardType == 1 || awardType == 3 || awardType == 4) {
				gameAward.setRetValue(Long.valueOf(gameUserAwardGroup.getDirectRet()));			
			} else if (awardType == 2 || awardType == 5 || awardType == 7) {
				gameAward.setRetValue(Long.valueOf(gameUserAwardGroup.getThreeoneRet()));
			} else if(awardType==8){
				gameAward.setRetValue(Long.valueOf(gameUserAwardGroup.getSuperRet()));
			}else if(awardType==9){
				gameAward.setRetValue(Long.valueOf(gameUserAwardGroup.getLhcYear()));
			}else if(awardType==10){
				gameAward.setRetValue(Long.valueOf(gameUserAwardGroup.getLhcColor()));
			}else if(awardType==22){
				gameAward.setRetValue(Long.valueOf(gameUserAwardGroup.getSbThreeoneRet()));
			}else if(awardType==11){
				gameAward.setRetValue(Long.valueOf(gameUserAwardGroup.getLhcFlatcode()));
			}else if(awardType==12){
				gameAward.setRetValue(Long.valueOf(gameUserAwardGroup.getLhcHalfwave()));
			}else if(awardType==13){
				gameAward.setRetValue(Long.valueOf(gameUserAwardGroup.getLhcOneyear()));
			}else if(awardType==14){
				gameAward.setRetValue(Long.valueOf(gameUserAwardGroup.getLhcNotin()));
			}else if(awardType==15){
				gameAward.setRetValue(Long.valueOf(gameUserAwardGroup.getLhcContinuein23()));
			}else if(awardType==16){
				gameAward.setRetValue(Long.valueOf(gameUserAwardGroup.getLhcContinuein4()));
			}else if(awardType==17){
				gameAward.setRetValue(Long.valueOf(gameUserAwardGroup.getLhcContinuein5()));
			}else if(awardType==18){
				gameAward.setRetValue(Long.valueOf(gameUserAwardGroup.getLhcContinuenotin23()));
			}else if(awardType==19){
				gameAward.setRetValue(Long.valueOf(gameUserAwardGroup.getLhcContinuenotin4()));
			}else if(awardType==20){
				gameAward.setRetValue(Long.valueOf(gameUserAwardGroup.getLhcContinuenotin5()));
			}else if(awardType==21){
				gameAward.setRetValue(Long.valueOf(gameUserAwardGroup.getLhcContinuecode()));
			}else {
				gameAward.setRetValue(Long.valueOf(gameUserAwardGroup.getDirectRet()));
			}
		}
		if (sysAwardGroupId != null && gameAward.getMaxRetValue() != null && gameAward.getMaxRetValue() == -1) {
			GameUserAwardGroup userAwardGroupEntity = gameUserAwardGroupDao.getUserAwardGroupByUserAndSysAwardId(
					sysAwardGroupId, superUserId);
			if (userAwardGroupEntity != null) {
				if (awardType == 1 || awardType == 3 || awardType == 4) {
					gameAward.setMaxRetValue(Long.valueOf(userAwardGroupEntity.getDirectRet()));

				} else if (awardType == 2 || awardType == 5 || awardType == 7) {
					gameAward.setMaxRetValue(Long.valueOf(userAwardGroupEntity.getThreeoneRet()));
				} else if(awardType==8){
					gameAward.setMaxRetValue(Long.valueOf(userAwardGroupEntity.getSuperRet()));
				} else if(awardType==9){
					gameAward.setMaxRetValue(Long.valueOf(userAwardGroupEntity.getLhcYear()));
				} else if(awardType==10){
					gameAward.setMaxRetValue(Long.valueOf(userAwardGroupEntity.getLhcColor()));
				} else if(awardType==22){
					gameAward.setMaxRetValue(Long.valueOf(userAwardGroupEntity.getSbThreeoneRet()));
				} else if(awardType==11){
					gameAward.setMaxRetValue(Long.valueOf(userAwardGroupEntity.getLhcFlatcode()));
				} else if(awardType==12){
					gameAward.setMaxRetValue(Long.valueOf(userAwardGroupEntity.getLhcHalfwave()));
				} else if(awardType==13){
					gameAward.setMaxRetValue(Long.valueOf(userAwardGroupEntity.getLhcOneyear()));
				} else if(awardType==14){
					gameAward.setMaxRetValue(Long.valueOf(userAwardGroupEntity.getLhcNotin()));
				} else if(awardType==15){
					gameAward.setMaxRetValue(Long.valueOf(userAwardGroupEntity.getLhcContinuein23()));
				} else if(awardType==16){
					gameAward.setMaxRetValue(Long.valueOf(userAwardGroupEntity.getLhcContinuein4()));
				} else if(awardType==17){
					gameAward.setMaxRetValue(Long.valueOf(userAwardGroupEntity.getLhcContinuein5()));
				} else if(awardType==18){
					gameAward.setMaxRetValue(Long.valueOf(userAwardGroupEntity.getLhcContinuenotin23()));
				} else if(awardType==19){
					gameAward.setMaxRetValue(Long.valueOf(userAwardGroupEntity.getLhcContinuenotin4()));
				} else if(awardType==20){
					gameAward.setMaxRetValue(Long.valueOf(userAwardGroupEntity.getLhcContinuenotin5()));
				} else if(awardType==21){
					gameAward.setMaxRetValue(Long.valueOf(userAwardGroupEntity.getLhcContinuecode()));
				} else {
					gameAward.setMaxRetValue(Long.valueOf(userAwardGroupEntity.getDirectRet()));
				}
			} else {
				GameAwardGroup group = awardGroupDao.getById(sysAwardGroupId);
				if (awardType == 1 || awardType == 3 || awardType == 4) {
					gameAward.setMaxRetValue(Long.valueOf(group.getDirectRet()));

				} else if (awardType == 2 || awardType == 5 || awardType == 7) {
					gameAward.setMaxRetValue(Long.valueOf(group.getThreeoneRet()));
				}else if(awardType==8){
					gameAward.setMaxRetValue(Long.valueOf(group.getSuperRet()));
				}else if(awardType==9){
					gameAward.setMaxRetValue(Long.valueOf(group.getLhcYear()));
				}else if(awardType==10){
					gameAward.setMaxRetValue(Long.valueOf(group.getLhcColor()));
				}else if(awardType==22){
					gameAward.setMaxRetValue(Long.valueOf(group.getSbThreeoneRet()));
				}else if(awardType==11){
					gameAward.setMaxRetValue(Long.valueOf(group.getLhcFlatcode()));
				}else if(awardType==12){
					gameAward.setMaxRetValue(Long.valueOf(group.getLhcHalfwave()));
				}else if(awardType==13){
					gameAward.setMaxRetValue(Long.valueOf(group.getLhcOneyear()));
				}else if(awardType==14){
					gameAward.setMaxRetValue(Long.valueOf(group.getLhcNotin()));
				}else if(awardType==15){
					gameAward.setMaxRetValue(Long.valueOf(group.getLhcContinuein23()));
				}else if(awardType==16){
					gameAward.setMaxRetValue(Long.valueOf(group.getLhcContinuein4()));
				}else if(awardType==17){
					gameAward.setMaxRetValue(Long.valueOf(group.getLhcContinuein5()));
				}else if(awardType==18){
					gameAward.setMaxRetValue(Long.valueOf(group.getLhcContinuenotin23()));
				}else if(awardType==19){
					gameAward.setMaxRetValue(Long.valueOf(group.getLhcContinuenotin4()));
				}else if(awardType==20){
					gameAward.setMaxRetValue(Long.valueOf(group.getLhcContinuenotin5()));
				}else if(awardType==21){
					gameAward.setMaxRetValue(Long.valueOf(group.getLhcContinuecode()));
				}else {
					gameAward.setMaxRetValue(Long.valueOf(group.getDirectRet()));
				}
			}
		}
	}

	/**
	 * 
	 * @param awardType 1:直選返點、2:不定位返點、3:所有返點、4:任選型返點、5:趣味型返點、6:混選返點、7:骰寶直選返點、8:超級2000返點、9:特肖返點、10:色波兩面返點、11:平碼返點、12:半波返點、13:一肖返點、14:不中返點、15:連肖(中)二三連肖返點、16:連肖(中)四連肖返點、17:連肖(中)五連肖返點、18:連肖(不中)二三連肖返點、19:連肖(不中)四連肖返點、20:連肖(不中)五連肖返點、21:連碼返點
	 * @param list
	 * @return
	 */
	private List<GameAwardEntity> removeGameAwardByAwardType(Long awardType, List<GameAwardEntity> list) {
		List<GameAwardEntity> result = new ArrayList<GameAwardEntity>();
		for (GameAwardEntity gameAward : list) {
			if (awardType == 1) {
				if ((gameAward.getGameBetType().getGameGroupCode() == 12
						|| gameAward.getGameBetType().getGameGroupCode() == 13 || gameAward.getGameBetType()
						.getGameGroupCode() == 33) && gameAward.getGameBetType().getBetMethodCode() == 40 ||
						//六合彩非直選就跳過
						(gameAward.getAwardGroupId()==202 && gameAward.getGameBetType().getGameGroupCode()>=53 && gameAward.getGameBetType().getGameGroupCode()<=56 && gameAward.getGameBetType().getBetMethodCode()!=81)
						) {
					continue;
				}
				if(SuperPairUtil.isSuperPair(gameAward.getGameBetType().getGameGroupCode())){
					continue;
				}
				result.add(gameAward);
			} else if (awardType == 2) {
				if ((gameAward.getGameBetType().getGameGroupCode() == 12
						|| gameAward.getGameBetType().getGameGroupCode() == 13 || gameAward.getGameBetType()
						.getGameGroupCode() == 33) && gameAward.getGameBetType().getBetMethodCode() == 40) {
					result.add(gameAward);
				}
			} else if (awardType == 4) {
				if (gameAward.getGameBetType().getGameGroupCode() == 17) {
					result.add(gameAward);
				}
			} else if (awardType == 5) {
				if (gameAward.getGameBetType().getGameGroupCode() == 18) {
					result.add(gameAward);
				}
			} else if (awardType == 6) {
				if ((gameAward.getGameBetType().getGameGroupCode() >= 34 && gameAward.getGameBetType().getGameGroupCode() <= 36)
						|| gameAward.getGameBetType().getGameGroupCode() == 39 
						|| gameAward.getGameBetType().getGameGroupCode() == 41
						|| gameAward.getGameBetType().getGameGroupCode() == 19) {
					result.add(gameAward);
				}
			} else if (awardType == 7) {
				if (isSbZhiXuan(getBetTypeCode(gameAward.getGameBetType()))) {
					result.add(gameAward);
				}
			} else if(awardType == 8){
				if(SuperPairUtil.isSuperPair(gameAward.getGameBetType().getGameGroupCode())){
					result.add(gameAward);
				}
			} else if(awardType == 9){
				if(gameAward.getGameBetType().getBetMethodCode() == 82 ){
					result.add(gameAward);
				}
			} else if(awardType == 10 ){
				if(gameAward.getGameBetType().getBetMethodCode()==83 || gameAward.getGameBetType().getBetMethodCode()==84){
					result.add(gameAward);
				}
			}else if(awardType == 22){
				if(gameAward.getGameBetType().getGameGroupCode() == 42){
					result.add(gameAward);
				}
			} else if(awardType == 11){
				if(gameAward.getGameBetType().getBetMethodCode()==64 ){
					result.add(gameAward);
				}
			} else if(awardType == 12){
				if(gameAward.getGameBetType().getBetMethodCode()==85 ){
					result.add(gameAward);
				}
			} else if(awardType == 13){
				if(gameAward.getGameBetType().getBetMethodCode()==86 ){
					result.add(gameAward);
				}
			} else if(awardType == 14){
				if(gameAward.getGameBetType().getBetMethodCode()>=87 && gameAward.getGameBetType().getBetMethodCode()<=91){
					result.add(gameAward);
				}
			} else if(awardType == 15){
				if((gameAward.getGameBetType().getBetMethodCode()==92 || gameAward.getGameBetType().getBetMethodCode()==93) && gameAward.getGameBetType().getGameSetCode()==40 ){
					result.add(gameAward);
				}
			} else if(awardType == 16){
				if(gameAward.getGameBetType().getBetMethodCode()==94 && gameAward.getGameBetType().getGameSetCode()==40){
					result.add(gameAward);
				}
			} else if(awardType == 17){
				if(gameAward.getGameBetType().getBetMethodCode()==95 && gameAward.getGameBetType().getGameSetCode()==40 ){
					result.add(gameAward);
				}
			} else if(awardType == 18){
				if((gameAward.getGameBetType().getBetMethodCode()==92 || gameAward.getGameBetType().getBetMethodCode()==93) && gameAward.getGameBetType().getGameSetCode()==41){
					result.add(gameAward);
				}
			} else if(awardType == 19){
				if(gameAward.getGameBetType().getBetMethodCode()==94 && gameAward.getGameBetType().getGameSetCode()==41){
					result.add(gameAward);
				}
			} else if(awardType == 20){
				if(gameAward.getGameBetType().getBetMethodCode()==95 && gameAward.getGameBetType().getGameSetCode()==41){
					result.add(gameAward);
				}
			} else if(awardType == 21){
				if(gameAward.getGameBetType().getBetMethodCode()>=13 && gameAward.getGameBetType().getBetMethodCode()<=17){
					result.add(gameAward);
				}
			} else {
				result.add(gameAward);
			}
		}
		return result;
	}
	private boolean isSbZhiXuan(String betTypeCode){
		return "43_37_79".equals(betTypeCode) || "43_37_80".equals(betTypeCode);
	}
	private String getBetTypeCode(GameBetType betType){
		return betType.getGameGroupCode()+"_"+betType.getGameSetCode()+"_"+betType.getBetMethodCode();
	}

	@Override
	public List<GameAwardEntity> queryGameAward(Long lotteryId, Long groupId, Integer status) throws Exception {

		List<GameAwardEntity> entityList = new ArrayList<GameAwardEntity>();
		Float miniLotteryProfit = gameSeriesDao.getMiniLotteryProfitByLotteryId(lotteryId);

		if (null != status && status <= GameAwardStatus.DELETE.getValue()) {
			//在正式表中
			List<GameAward> list = awardDao.queryGameAwardByGroupId(groupId, lotteryId, status);

			GameAwardGroup group = awardGroupDao.getById(groupId);

			for (GameAward award : list) {
				if (lotteryId == 99109l && award.findGameGroupCode() == 31 && award.findGameSetCode() == 14) {
					award.setBetTypeCode("31_14_10");
				}
				GameBettypeStatus gameBettypeStatus = bettypeStatusDao.getGameBettypeStatusByLottery(lotteryId,
						award.findGameGroupCode(), award.findGameSetCode(), award.findBetMethodCode(),
						award.findMethodType());
				GameAwardEntity awardEntity=VOConverter.convertGameAward2Entity(award, group, miniLotteryProfit, gameBettypeStatus);
				setTopAgentPoint(awardEntity);
				entityList.add(awardEntity);
			}
			return entityList;
		} else if (null != status && status >= GameAwardStatus.WATING_AUDITING.getValue()) {

			List<GameAwardCheck> list = awardCheckDao.queryGameAwardCheckByGroupId(groupId, lotteryId, status);

			GameAwardGroupCheck groupCheck = awardGroupCheckDao.getById(groupId);

			for (GameAwardCheck check : list) {

				GameBettypeStatus gameBettypeStatus = bettypeStatusDao.getGameBettypeStatusByLottery(lotteryId,
						check.getGameGroupCode(), check.getGameSetCode(), check.getBetMethodCode(),
						check.getMethodType());
				GameAwardEntity awardEntity=VOConverter.convertGameAwardCheck2Entity(check, groupCheck,
						miniLotteryProfit.longValue(), gameBettypeStatus);
				setTopAgentPoint(awardEntity);
				entityList.add(awardEntity);
			}

			return entityList;
		}

		return null;
	}

	@Override
	public void createAwardGroup(CreateGameAwardGroupDTO createAwardGroupDTO) throws Exception {

		//1.保存进Check表 
		GameAwardGroupCheck awardGroupCheck = new GameAwardGroupCheck();

		//2判断相同彩种是否存在	相同名称的奖金组
		Boolean isExist = awardGroupCheckDao.checkIsExistAwardName(createAwardGroupDTO.getLotteryId(),
				createAwardGroupDTO.getAwardName());
		if (isExist) {
			throw new GameAwardGroupExistErrorException();
		}

		awardGroupCheck.setLotteryid(createAwardGroupDTO.getLotteryId());
		awardGroupCheck.setAwardName(createAwardGroupDTO.getAwardName());
		awardGroupCheck.setDirectRet(createAwardGroupDTO.getDirectRet().longValue());
		awardGroupCheck.setThreeoneRet(createAwardGroupDTO.getThreeoneRet().longValue());
		awardGroupCheck.setStatus(new Long(GameAwardStatus.WATING_AUDITING.getValue()));
		awardGroupCheck.setSysAwardGroup(SysAwardGroup.SYSTEM.getValue());
		awardGroupCheck.setCreateTime(new Date());

		Long awardGroupId = awardGroupCheckDao.saveGameAwardGroupCheck(awardGroupCheck);

		List<GameAwardCheck> list = new ArrayList<GameAwardCheck>();

		for (AwardBonusStruc struc : createAwardGroupDTO.getAwardBonusStrucList()) {
			GameAwardCheck award = null;
			if (struc.getAssistBMBonusList() != null && !struc.getAssistBMBonusList().isEmpty()) {
				for (AssistBmBonusStruc assist : struc.getAssistBMBonusList()) {
					award = new GameAwardCheck();
					award.setActualBonus(assist.getActualBonus());
					award.setAwardGroupId(awardGroupId);
					award.setStatus(GameAwardStatus.WATING_AUDITING.getValue());
					award.setCreateTime(new Date());
					award.setLotteryid(createAwardGroupDTO.getLotteryId());
					award.setBetTypeCode(struc.getGameGroupCode() + "_" + struc.getGameSetCode() + "_"
							+ struc.getBetMethodCode() + "_" + assist.getMethodType());
					list.add(award);
				}
			} else {
				award = new GameAwardCheck();
				award.setActualBonus(struc.getActualBonus());
				award.setAwardGroupId(awardGroupId);
				award.setStatus(GameAwardStatus.WATING_AUDITING.getValue());
				award.setCreateTime(new Date());
				award.setLotteryid(createAwardGroupDTO.getLotteryId());
				award.setBetTypeCode(struc.getGameGroupCode() + "_" + struc.getGameSetCode() + "_"
						+ struc.getBetMethodCode());
				list.add(award);
			}
		}
		awardCheckDao.insert(list);
	}

	@Override
	public void auditAwardGroup(Long lotteryId, String awardId, Integer checkType, Integer checkResult)
			throws Exception {

		GameAwardGroupCheck awardGroupCheck = awardGroupCheckDao.queryGameAwardGroupCheckById(lotteryId,
				Long.parseLong(awardId));

		Long status = checkResult == 1 ? new Long(GameAwardStatus.WATING_PUBLISH.getValue()) : new Long(
				GameAwardStatus.NotAudit.getValue());

		awardGroupCheck.setStatus(status);

		awardGroupCheckDao.update(awardGroupCheck);

		awardCheckDao.updataStatusByGroupId(awardGroupCheck.getId(), lotteryId, checkResult == 1 ? new Long(
				GameAwardStatus.WATING_PUBLISH.getValue()) : new Long(GameAwardStatus.NotAudit.getValue()));
	}

	@Override
	public void editAwardGroup(EditGameAwardGroupDTO awardGroupDTO) throws Exception {

		//1.修改的时候，将源数据复制到check表中并进行修改，待审核通过，发布的时候才覆盖到正式表中，同时删除check表中的数据。

		//获取check表信息，如果没有，从主表中获取，并复制到check表中
		GameAwardGroupCheck awardGroupCheck = getGameAwardGroupCheckById(Long.parseLong(awardGroupDTO.getAwardId()),
				awardGroupDTO.getUpdateType());

		awardGroupCheck.setDirectRet(awardGroupDTO.getDirectRet().longValue());
		awardGroupCheck.setThreeoneRet(awardGroupDTO.getThreeoneRet().longValue());
		awardGroupCheck.setStatus(3L); //待审核
		awardGroupCheckDao.saveOrUpdate(awardGroupCheck);

		//update GameAwardCheck
		List<GameAwardCheck> list = getGameAwardCheckByGroupId(awardGroupCheck.getId(), awardGroupCheck.getLotteryid());

		for (AwardBonusStruc struc : awardGroupDTO.getAwardBonusStrucList()) {

			//奖金组多一级结构，要保存多一级结构的数据
			if (struc.getAssistBMBonusList() != null && !struc.getAssistBMBonusList().isEmpty()) {
				for (AssistBmBonusStruc assist : struc.getAssistBMBonusList()) {
					for (GameAwardCheck check : list) {

						if (check.getGameGroupCode() == struc.getGameGroupCode()
								&& check.getBetMethodCode() == struc.getBetMethodCode()
								&& check.getGameSetCode() == struc.getGameSetCode()
								&& check.getMethodType() == assist.getMethodType()) {
							check.setStatus(GameAwardStatus.WATING_AUDITING.getValue());
							check.setActualBonus(assist.getActualBonus());
							check.setUpdateTime(new Date());

							awardCheckDao.saveOrUpdate(check);
						}
					}
				}
			} else {
				for (GameAwardCheck check : list) {

					if (check.getGameGroupCode() == struc.getGameGroupCode()
							&& check.getBetMethodCode() == struc.getBetMethodCode()
							&& check.getGameSetCode() == struc.getGameSetCode()) {
						check.setStatus(GameAwardStatus.WATING_AUDITING.getValue());
						check.setActualBonus(struc.getActualBonus());
						check.setUpdateTime(new Date());

						awardCheckDao.saveOrUpdate(check);
					}
				}
			}
		}

	}

	/**
	 * 根据GroupId获取check奖金组明细表信息。
	 * @param groupId
	 * @param lotteryId
	 * @return
	 * @throws Exception
	 */
	private List<GameAwardCheck> getGameAwardCheckByGroupId(Long groupId, Long lotteryId) throws Exception {

		List<GameAwardCheck> list = awardCheckDao.queryGameAwardCheckByGroupId(groupId, lotteryId, null);

		if (null != list && list.size() > 0) {
			return list;
		}

		List<GameAwardCheck> gameAwardCheckList = new ArrayList<GameAwardCheck>();
		List<GameAward> gameAwardList = awardDao.queryGameAwardByGroupId(groupId, lotteryId, null);

		for (GameAward award : gameAwardList) {

			GameAwardCheck check = new GameAwardCheck();

			check.setActualBonus(award.getActualBonus());
			check.setAwardGroupId(award.getAwardGroupId());
			check.setBetTypeCode(award.getBetTypeCode());
			check.setCreateTime(award.getCreateTime());
			check.setLotteryid(award.getLotteryid());
			if (null != award.getUpdateTime()) {
				check.setUpdateTime(award.getUpdateTime());
			}

			gameAwardCheckList.add(check);

		}

		return gameAwardCheckList;
	}

	@Override
	public void publishAwardGroup(Long lotteryId, String awardId, Integer publishResult) throws Exception {
		//发布 1通过 2不通过
		if (publishResult == 1) {
			//1.新增的时候，是在check表中，待审核通过，发布的时候才复制到正式表中，同时删除check表中的数据。

			//发布的时候要从Check表中获取
			GameAwardGroupCheck groupCheck = awardGroupCheckDao.getById(Long.parseLong(awardId));

			//1.如果没有则插入，如果有则更新
			Long groupid = awardGroupDao.saveOrUpdate(getGameAwardGroup(awardId, groupCheck));

			List<GameAwardCheck> awardCheck = awardCheckDao.queryGameAwardCheckByGroupId(Long.parseLong(awardId),
					lotteryId, GameAwardStatus.WATING_PUBLISH.getValue());

			//新增发布的时候更新奖金表奖金
			List<GameAward> gameAwards = awardDao.queryGameAwardByGroupId(groupid, lotteryId, null);
			if (gameAwards != null && gameAwards.size() != 0) {
				for (GameAward gameAward : gameAwards) {
					for (GameAwardCheck gameAwardCheck : awardCheck) {
						if (gameAward.getBetTypeCode().equals(gameAwardCheck.getBetTypeCode())) {
							gameAward.setActualBonus(gameAwardCheck.getActualBonus());
							gameAward.setUpdateTime(new Date());
							awardDao.update(gameAward);
							break;
						}
					}

				}
			} else {
				List<GameAward> list = new ArrayList<GameAward>();
				for (GameAwardCheck gameAwardCheck : awardCheck) {
					list.add(copyGameAwardCheck2GameAward(gameAwardCheck, groupid));
				}
				awardDao.insert(list);
			}

			awardGroupCheckDao.delete(groupCheck.getId());

			for (GameAwardCheck check : awardCheck) {

				awardCheckDao.delete(check.getId());
			}
		} else {
			GameAwardGroupCheck awardGroupCheck = awardGroupCheckDao.queryGameAwardGroupCheckById(lotteryId,
					Long.parseLong(awardId));

			awardGroupCheck.setStatus(new Long(GameAwardStatus.NotPublish.getValue()));

			awardGroupCheckDao.update(awardGroupCheck);

			awardCheckDao.updataStatusByGroupId(awardGroupCheck.getId(), lotteryId,
					new Long(GameAwardStatus.NotPublish.getValue()));
		}
	}

	private GameAward copyGameAwardCheck2GameAward(GameAwardCheck gameAwardCheck, Long groupid) {
		GameAward gameAward = new GameAward();
		gameAward.setActualBonus(gameAwardCheck.getActualBonus());
		gameAward.setAwardGroupId(gameAwardCheck.getAwardGroupId());
		gameAward.setBetTypeCode(gameAwardCheck.getBetTypeCode());
		gameAward.setCreateTime(gameAwardCheck.getCreateTime());
		gameAward.setCreator(gameAwardCheck.getCreator());
		gameAward.setGmtCreated(gameAwardCheck.getGmtCreated());
		gameAward.setIsDeleted(gameAwardCheck.getIsDeleted());
		gameAward.setLotteryid(gameAwardCheck.getLotteryid());
		gameAward.setAwardGroupId(groupid);
		gameAward.setStatus(GameAwardStatus.CURRENT.getValue());
		return gameAward;
	}

	/**
	 * 用于将GameAwardGroupCheck信息转换为GameAwardGroup信息，如果存在则更新，否则新增。
	 * @param awardId
	 * @param groupCheck
	 * @return
	 * @throws Exception
	 */
	private GameAwardGroup getGameAwardGroup(String awardId, GameAwardGroupCheck groupCheck) throws Exception {

		GameAwardGroup group = awardGroupDao.getById(Long.parseLong(awardId));

		if (null == group) {
			group = new GameAwardGroup();
		}

		group.setId(Long.parseLong(awardId));
		group.setAwardName(groupCheck.getAwardName());
		group.setCreateTime(groupCheck.getCreateTime());
		group.setDirectRet(groupCheck.getDirectRet().longValue());
		group.setLotteryid(groupCheck.getLotteryid());
		group.setStatus(new Long(Status.CURRENT.getValue()));
		group.setSysAwardGroup(groupCheck.getSysAwardGroup());
		group.setThreeoneRet(groupCheck.getThreeoneRet().longValue());
		group.setUpdateTime(new Date());

		return group;
	}
	@Override
	public GameAwardGroup queryById(Long id) throws Exception {
		return awardGroupDao.getById(id);
	}
	@Override
	public List<GameAwardGroupEntity> queryGameAwardGroup(Long lotteryId, Integer status, Long awardId)
			throws Exception {
		//status  1:进行中 2：已删除     check表 状态 3:待审核 4:待发布

		List<GameAwardGroupEntity> list = new ArrayList<GameAwardGroupEntity>();
		if (status != null && status <= GameAwardStatus.DELETE.getValue()) {
			if (awardId != null) {
				GameAwardGroup group = awardGroupDao.getById(awardId);
				list.add(VOConverter.convertGameAwardGroup2Entity(group));

				return list;
			}

			return awardGroupDao.queryGameAwardGroup(lotteryId, status, awardId);

		} else if (status != null && status >= GameAwardStatus.WATING_AUDITING.getValue()) {

			if (awardId != null) {
				GameAwardGroupCheck group = awardGroupCheckDao.getById(awardId);
				list.add(VOConverter.convertGameAwardGroupCheck2Entity(group));
				return list;
			}

			return awardGroupCheckDao.queryGameAwardGroupChecks(lotteryId, status, awardId);
		}
		List<GameAwardGroupEntity> entitys = awardGroupDao.queryGameAwardGroupByLotteryId(lotteryId);

		return entitys;
	}

	/**
	 * 如果UpdateType不是系统待发布奖金组的话，获取GameAwardGroup表的信息，否则获取GameAwardGroupCheck表信息。
	 * @param awardId
	 * @param updateType 1:用户待发布奖金组、2:用户进行中奖金组、3:系统待发布奖金组
	 * @return
	 * @throws Exception
	 */
	private GameAwardGroupCheck getGameAwardGroupCheckById(Long awardId, Integer updateType) throws Exception {

		//1 用户待发布奖金组 	2 用户进行中奖金组		3 系统待发布奖金组
		if (updateType != null && updateType <= 2) {

			GameAwardGroup awardGroup = awardGroupDao.getById(awardId);

			if (null != awardGroup) {
				return copyGameAwardGroup2Check(awardGroup);
			}

		}
		GameAwardGroupCheck awardGroupCheck = awardGroupCheckDao.getById(awardId);

		if (null != awardGroupCheck) {
			return awardGroupCheck;
		}

		return null;
	}

	/**
	 * 将GameAwardGroup信息Copy为Check表信息。
	 * @param awardGroup
	 * @return
	 * @throws Exception
	 */
	private GameAwardGroupCheck copyGameAwardGroup2Check(GameAwardGroup awardGroup) throws Exception {

		GameAwardGroupCheck awardGroupCheck = new GameAwardGroupCheck();

		awardGroupCheck.setAwardName(awardGroup.getAwardName());
		awardGroupCheck.setStatus(new Long(GameAwardStatus.WATING_AUDITING.getValue()));
		awardGroupCheck.setCreateTime(awardGroup.getCreateTime());
		awardGroupCheck.setDirectRet(awardGroup.getDirectRet());
		awardGroupCheck.setId(awardGroup.getId());
		awardGroupCheck.setLotteryid(awardGroup.getLotteryid());
		awardGroupCheck.setSysAwardGroup(awardGroup.getSysAwardGroup());
		awardGroupCheck.setThreeoneRet(awardGroup.getThreeoneRet());
		awardGroupCheck.setUpdateTime(new Date());
		awardGroupCheck.setAwardGroupid(awardGroup.getId());

		return awardGroupCheck;
	}

	@Override
	public List<GameBettypeStatus> queryTheoryByLotteryId(Long lotteryId) throws Exception {

		return bettypeStatusDao.getTheoryByLotteryId(lotteryId);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void delAwardGroup(Long lotteryId, Long awardId) throws Exception {
		//这里删除的只能是check表的信息 "只有待审核和待发布时可以删除系统中的用户奖金组"

		awardCheckDao.delByAwardGroupId(awardId);
		awardGroupCheckDao.delete(awardId);
	}

	@Override
	public Long getMiniLotteryProfit(Long lotteryId) throws Exception {

		Float miniLotteryProfit = gameSeriesDao.getMiniLotteryProfitByLotteryId(lotteryId);
		BigDecimal mini = new BigDecimal(miniLotteryProfit);

		return mini.multiply(new BigDecimal(100)).longValue();
	}

	@Override
	public List<GameAwardEntity> queryGameAwardBak(Long lotteryId, String gameAwardName, GameAwardStatus awardStatus)
			throws Exception {
		List<GameAwardEntity> entityList = new ArrayList<GameAwardEntity>();
		GameAwardEntity entity = null;
		Float miniLotteryProfit = gameSeriesDao.getMiniLotteryProfitByLotteryId(lotteryId);
		if (GameAwardStatus.CURRENT == awardStatus || GameAwardStatus.DELETE == awardStatus) {
			//在正式表中

			GameAwardGroup group = awardGroupDao.queryGameGroupByLotteryIdAndName(lotteryId, gameAwardName);
			if (group == null) {
				return null;
			}
			List<GameAward> list = awardDao.queryGameAwardByGroupId(group.getId(), lotteryId, awardStatus.getValue());

			for (GameAward award : list) {

				GameBettypeStatus gameBettypeStatus = bettypeStatusDao.getGameBettypeStatusByLottery(lotteryId,
						award.findGameGroupCode(), award.findGameSetCode(), award.findBetMethodCode());
				entity = VOConverter.convertGameAward2Entity(award, group, miniLotteryProfit, gameBettypeStatus);
				entityList.add(entity);
			}
			return entityList;
		} else if (GameAwardStatus.WATING_AUDITING == awardStatus || GameAwardStatus.WATING_PUBLISH == awardStatus) {

			GameAwardGroupCheck groupCheck = awardGroupCheckDao.queryGameGroupByLotteryIdAndName(lotteryId,
					gameAwardName);
			if (null == groupCheck) {
				return null;
			}
			List<GameAwardCheck> list = awardCheckDao.queryGameAwardCheckByGroupId(groupCheck.getId(), lotteryId,
					awardStatus.getValue());

			for (GameAwardCheck check : list) {

				GameBettypeStatus gameBettypeStatus = bettypeStatusDao.getGameBettypeStatusByLottery(lotteryId,
						check.getGameGroupCode(), check.getGameSetCode(), check.getBetMethodCode());

				entity = VOConverter.convertGameAwardCheck2Entity(check, groupCheck, miniLotteryProfit.longValue(),
						gameBettypeStatus);
				entityList.add(entity);
			}

			return entityList;
		}
		return null;
	}

	@Override
	public List<GameAwardGroupEntity> queryGameAwardGroupByUserId(long userid) {
		List<GameAwardGroupEntity> allLotteryAwardGroup = awardGroupDao.queryGameAwardGroupByUserId(userid);
		return allLotteryAwardGroup;
	}

	@Override
	public Map<Long, List<GameBettypeAssist>> getBetyTypeAssistByTypeIds(List<Long> betTypeStatusIds) {
		List<GameBettypeAssist> list = gameBettypeAssistDaoImpl.getBettypeAssistListByTypeIds(betTypeStatusIds);
		Map<Long, List<GameBettypeAssist>> resultMap = new HashMap<Long, List<GameBettypeAssist>>();
		for (GameBettypeAssist gameBettypeAssist : list) {
			if (resultMap.containsKey(gameBettypeAssist.getBettypeid())) {
				resultMap.get(gameBettypeAssist.getBettypeid()).add(gameBettypeAssist);
			} else {
				List<GameBettypeAssist> tempArrayList = new ArrayList<GameBettypeAssist>();
				tempArrayList.add(gameBettypeAssist);
				resultMap.put(gameBettypeAssist.getBettypeid(), new ArrayList<GameBettypeAssist>(tempArrayList));
			}
		}
		return resultMap;
	}	
}
