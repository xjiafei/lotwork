package com.winterframework.firefrog.game.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.winterframework.firefrog.common.util.SNUtil;
import com.winterframework.firefrog.game.dao.IGameBettypeStatusDao;
import com.winterframework.firefrog.game.dao.IGameControlEventDao;
import com.winterframework.firefrog.game.dao.IGameIssueDao;
import com.winterframework.firefrog.game.dao.IGamePackageDao;
import com.winterframework.firefrog.game.dao.IGamePackageItemDao;
import com.winterframework.firefrog.game.dao.IGameReturnPointDao;
import com.winterframework.firefrog.game.dao.IGameWarnIssueDao;
import com.winterframework.firefrog.game.dao.vo.EnumTypeConverts;
import com.winterframework.firefrog.game.dao.vo.GameBettypeStatus;
import com.winterframework.firefrog.game.dao.vo.GameControlEvent;
import com.winterframework.firefrog.game.dao.vo.GameWarnIssue;
import com.winterframework.firefrog.game.dao.vo.GameWarnIssueLog;
import com.winterframework.firefrog.game.entity.GameBetType;
import com.winterframework.firefrog.game.entity.GameIssueEntity;
import com.winterframework.firefrog.game.entity.GameOrder;
import com.winterframework.firefrog.game.entity.GamePackage;
import com.winterframework.firefrog.game.entity.GamePackage.GamePackageType;
import com.winterframework.firefrog.game.entity.GamePackageItem;
import com.winterframework.firefrog.game.entity.GamePlan;
import com.winterframework.firefrog.game.entity.Lottery;
import com.winterframework.firefrog.game.exception.GameBetMethodStatusStopException;
import com.winterframework.firefrog.game.exception.GameOrderOrPlanCodeExistErrorException;
import com.winterframework.firefrog.game.service.IGameBetTypeStatusService;
import com.winterframework.firefrog.game.service.IGamePackageService;
import com.winterframework.firefrog.game.service.IGameUserAwardGroupService;
import com.winterframework.firefrog.game.service.IGameWarnIssueLogService;
import com.winterframework.firefrog.game.web.dto.GamePlanBetOriginDataStruc;
import com.winterframework.firefrog.user.dao.IUserCustomerDao;
import com.winterframework.firefrog.user.entity.User;

/**
 * 
* @ClassName: GamePackageServiceImpl 
* @Description: 方案服务接口实现类
* @author Richard
* @date 2013-7-22 下午1:39:02 
*
 */
@Service("gamePackageServiceImpl")
@Transactional(rollbackFor = Exception.class)
public class GamePackageServiceImpl implements IGamePackageService {

	private static final Logger log = LoggerFactory.getLogger(GamePackageServiceImpl.class);
	@Resource(name = "gamePackageDao")
	private IGamePackageDao gamePackageDao;

	@Resource(name = "gamePackageItemDao")
	private IGamePackageItemDao gamePackageItemDao;

	@Resource(name = "gameIssueDaoImpl")
	private IGameIssueDao gameIssueDao;

	@Resource(name = "gameControlEventDaoImpl")
	private IGameControlEventDao gameControlEventDao;

	@Resource(name = "SNUtil")
	private SNUtil snUtil;

	@Resource(name = "gameBettypeStatusDaoImpl")
	private IGameBettypeStatusDao bettypeStatusDao;

	@Resource(name = "gameWarnIssueDaoImpl")
	private IGameWarnIssueDao gameWarnIssueDao;

	@Resource(name = "gameWarnIssueLogServiceImpl")
	private IGameWarnIssueLogService gameWarnIssueLogServiceImpl;

	@Resource(name = "userCustomerDaoImpl")
	private IUserCustomerDao customerDao;

	@Resource(name = "gameReturnPointDaoImpl")
	private IGameReturnPointDao gameReturnPointDao;
	@Resource(name = "gameUserAwardGroupServiceImpl")
	private IGameUserAwardGroupService gameUserAwardGroupService;
	@Resource(name = "gameBetTypeStatusServiceImpl")
	private IGameBetTypeStatusService gameBetTypeStatusService;
	
	
	@Override
	public com.winterframework.firefrog.game.dao.vo.GamePackage getById(Long id) throws Exception { 
		return this.gamePackageDao.getById(id);
	}
	@Override
	public GamePackage saveGamePackage(GameOrder order, List<GamePlanBetOriginDataStruc> gamePlanBetOriginDataStruc)
			throws Exception {

		GamePlan planEntity = (GamePlan) order.getGamePackage();
		User currUser = order.getGamePackage().getUser();
		Lottery lottery = order.getLottery();

		log.info("开始生成方案信息");
		//1.生成方案表
		GamePackage gamePackage = new GamePackage();

		gamePackage.setUser(currUser);
		gamePackage.setGameIssue(order.getGameIssue());
		gamePackage.setLottery(lottery);

		// 方案code生成规则
		//检查方案编号是否重复
		GameIssueEntity gameIssueEntity = gameIssueDao.queryGameIssue(lottery.getLotteryId(), order.getGameIssue()
				.getIssueCode());
		String packageCode = snUtil.createSN(SNUtil.TYPE_PACKAGE, lottery.getLotteryId(), gameIssueEntity.getWebIssueCode());
		List<com.winterframework.firefrog.game.dao.vo.GamePackage> packageList = gamePackageDao
				.getGamePackageByPackageCode(packageCode);
		if (packageList != null && !packageList.isEmpty()) {
			log.error("方案编号，订单编号或追号编号已经存在:" + packageCode);
			throw new GameOrderOrPlanCodeExistErrorException();
		}
		gamePackage.setPackageCode(packageCode);
		gamePackage.setType(GamePackageType.PLAN);
		gamePackage.setSaleTime(new Date());
		gamePackage.setUserip(planEntity.getUserIp());
		gamePackage.setServerip(planEntity.getServerip());
		gamePackage.setPackageAmount(planEntity.getPackageAmount());
		gamePackage.setChannel(planEntity.getChannel());
		gamePackage.setFileMode(planEntity.getFileMode());
		gamePackage.setActivityType(planEntity.getActivityType());
		//初始化用户连链信息
		User user = customerDao.queryUserById(currUser.getId());
		String userChain = user.getUserProfile().getUserChain();
		String retUserChain = gameReturnPointDao.getUserChain(userChain);
		gamePackage.setRetUserChain(retUserChain);
		Long gamePackageId = gamePackageDao.savePackage(gamePackage);
		gamePackage.setPackageId(gamePackageId);

		//生成gamePackageItme 
		List<GamePackageItem> itemList = initGamePackageItems(gamePlanBetOriginDataStruc, gamePackage,
				lottery.getLotteryId(), currUser);

		gameReturnPointDao.getRetunPointChain(itemList, userChain);

		//2.保存方案明细表
		gamePackageItemDao.savePackageitemList(itemList,gamePackage);
		log.info("生成方案信息成功，gamePackageId =" + gamePackageId);
		gamePackage.setItemList(itemList);
		gamePackage.setPackageId(gamePackageId);
		return gamePackage;

	}

	/** 
	* @Title: initGamePackageItems 
	* @Description: 初始化方案细节信息
	* @param gamePlanBetOriginDataStruc
	* @param gamePackageId
	* @param lotteryId
	* @param user
	* @return
	* @throws Exception
	*/
	private List<GamePackageItem> initGamePackageItems(List<GamePlanBetOriginDataStruc> gamePlanBetOriginDataStruc,
			GamePackage gamePackage, Long lotteryId, User user) throws Exception {
		List<GamePackageItem> list = new ArrayList<GamePackageItem>();
		Long firstIssue = gamePlanBetOriginDataStruc.get(0).getIssueCode();
		for (GamePlanBetOriginDataStruc betDetail : gamePlanBetOriginDataStruc) {
			Long status;

			if (lotteryId.longValue() == 99201L && betDetail.getBetMethodCode() == null) {
				status = bettypeStatusDao.getStatusOfBjkl8Interest(lotteryId, betDetail.getGameGroupCode(),
						betDetail.getGameSetCode(), betDetail.getBetdetail());
			} else {
				status = bettypeStatusDao.getStatus(lotteryId, betDetail.getGameGroupCode(),
						betDetail.getGameSetCode(), betDetail.getBetMethodCode());
			}

			//投注方式暂停销售
			if (status == 0) {
				throw new GameBetMethodStatusStopException(betDetail.getGameGroupCode() + ""
						+ betDetail.getGameSetCode() + betDetail.getBetMethodCode());
			}
			if (firstIssue == null || firstIssue == betDetail.getIssueCode().longValue()) {
				GamePackageItem packageItem=convertGamePlanBetOriginDataStruc2GamePackageItem(betDetail, gamePackage, lotteryId, user);
				String betTypeCode=packageItem.getGameBetType().getBetTypeCode();
				Long retPoint=gameUserAwardGroupService.getRetPointByUserIdAndLotteryIdAndBetTypeCode(user.getId(), lotteryId, betTypeCode);
				packageItem.setRetPoint(retPoint);
				/*下移至保存时处理（辅助玩法）
				if(com.winterframework.firefrog.game.dao.vo.GamePackageItem.AwardMode.HIGH.getValue()==packageItem.getAwardMode().intValue()){
					GameBettypeStatus betTypeStatus=gameBetTypeStatusService.getByLotteryIdAndBetTypeCode(lotteryId, betTypeCode);
					packageItem.setRetAward(betTypeStatus.getTheoryBonus()*retPoint/10000);
					//packageItem.setRetAward(formatLong(packageItem.getMoneyMode().getValue(),betTypeStatus.getTheoryBonus()*retPoint/10000));
				}*/
				list.add(packageItem);
			}
		}
		return list;
	}
	private static   Long formatLong(int moneyModel,Long aaa){
		//System.out.println(" Long formatLong(Long moneyModel,Long aaa)============"+aaa);
		if(aaa==null) return null;
		if(1==moneyModel){
		    aaa= NumberUtils.toLong(String.valueOf(aaa/100)+"00");
		}else if(3==moneyModel){
		    aaa= NumberUtils.toLong(String.valueOf(aaa/10000)+"0000");
		}else{
			aaa= NumberUtils.toLong(String.valueOf(aaa/1000)+"000");
		}
		//System.out.println(" Long formatLong(Long moneyModel,Long aaa)2============"+aaa);

		return aaa;
	}
	private GamePackageItem convertGamePlanBetOriginDataStruc2GamePackageItem(GamePlanBetOriginDataStruc details,
			GamePackage gamePackage, Long lotteryId, User user) throws Exception {

		GamePackageItem item = new GamePackageItem();

		item.setGamePackage(gamePackage);
		item.setGameBetType(new GameBetType(details.getGameGroupCode(), details.getGameSetCode(), details
				.getBetMethodCode()));
		item.setMoneyMode(EnumTypeConverts.convertMoneyMode(details.getMoneyMode()));
		item.setTotbets(1L);
		item.setTotbets(details.getTotbets().longValue());
		item.setMultiple(details.getMultiple());
		item.setBetDetail(details.getBetdetail());
		item.setCreateTime(new Date());
		item.setItemAmount(new Long(details.getItemAmount()));
		item.setFileMode(EnumTypeConverts.converFileMode(details.getFileMode()));
		item.setAwardMode(details.getAwardMode());
		//追号方案在packageitem中的倍数写固定值1
		int multiple=gamePackage.getType().getValue()==GamePackageType.PLAN.getValue()?1:details.getMultiple().intValue();
		Long totalAmout = (long) (details.getTotbets() * details.getItemAmount() * multiple);
		item.setTotamount(totalAmout);

		return item;
	}

	@Override
	public void cancelGamePackage(Long lotteryid, Long issueCode, GameWarnIssueLog warnIssueLog) throws Exception {
		GameIssueEntity issue = gameIssueDao.queryGameIssue(lotteryid, issueCode);

		//添加奖期警告表
		GameWarnIssue warn = new GameWarnIssue();
		warn.setIssueCode(issueCode);
		warn.setLotteryid(lotteryid);
		warn.setWebIssueCode(issue.getWebIssueCode());
		warn.setIssueWarnId(warnIssueLog.getEvent());
		warn.setCreateTime(new Date());
		warn.setUpdateTime(warn.getCreateTime());
		warn.setReadFlag(0L);
		warn.setStatus(1L);
		warn.setStatusRout(warnIssueLog.getEvent());
		gameWarnIssueDao.updateIfHave(warn);
		gameWarnIssueLogServiceImpl.addWarnIssueLog(warnIssueLog);

		//添加"撤销本期方案"待办任务
		GameControlEvent event = new GameControlEvent();
		event.setLotteryid(lotteryid);
		event.setStartIssueCode(issueCode);
		event.setEndIssueCode(issueCode);
		event.setSaleStartTime(issue.getSaleStartTime());
		event.setSaleEndTime(issue.getSaleEndTime());
		event.setStatus(0L);
		event.setCreateTime(new Date());
		event.setEnentType(4L);
		if (warn.getId() == null) {
			event.setWarnIssueId(-1L * warnIssueLog.getId());
		} else {
			event.setWarnIssueId(warn.getId());
		}
		event.setMessage(warnIssueLog.getDisposeInfo());
		gameControlEventDao.addControlEvent(event);
	}

}
