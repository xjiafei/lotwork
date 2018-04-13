package com.winterframework.firefrog.game.dao.impl;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.stereotype.Repository;

import com.winterframework.firefrog.common.util.DataConverterUtil;
import com.winterframework.firefrog.common.util.FileUtil;
import com.winterframework.firefrog.game.dao.IGameAwardDao;
import com.winterframework.firefrog.game.dao.IGameBettypeAssistDao;
import com.winterframework.firefrog.game.dao.IGameBettypeStatusDao;
import com.winterframework.firefrog.game.dao.IGameOrderDao;
import com.winterframework.firefrog.game.dao.IGamePointDao;
import com.winterframework.firefrog.game.dao.IGameReturnPointDao;
import com.winterframework.firefrog.game.dao.IGameSlipDao;
import com.winterframework.firefrog.game.dao.IGameUserAwardGroupDao;
import com.winterframework.firefrog.game.dao.ISlipItemAssistDao;
import com.winterframework.firefrog.game.dao.vo.GameBetDetailTotAmount;
import com.winterframework.firefrog.game.dao.vo.GameBettypeAssist;
import com.winterframework.firefrog.game.dao.vo.GameBettypeStatus;
import com.winterframework.firefrog.game.dao.vo.GameOrder;
import com.winterframework.firefrog.game.dao.vo.GamePoint;
import com.winterframework.firefrog.game.dao.vo.GameSlip;
import com.winterframework.firefrog.game.dao.vo.VOConverter;
import com.winterframework.firefrog.game.entity.BetDetails.fileMode;
import com.winterframework.firefrog.game.entity.BetMethodDescription;
import com.winterframework.firefrog.game.entity.GameBetType;
import com.winterframework.firefrog.game.entity.GamePackage;
import com.winterframework.firefrog.game.entity.GamePackageItem;
import com.winterframework.firefrog.game.entity.GameSlipOperationsEntity;
import com.winterframework.firefrog.game.entity.GameSlipStatus;
import com.winterframework.firefrog.game.entity.ItemAssist;
import com.winterframework.firefrog.game.entity.Points;
import com.winterframework.firefrog.game.entity.SlipItemAssist;
import com.winterframework.firefrog.game.exception.GameBetMethodStatusStopException;
import com.winterframework.firefrog.game.lock.config.mongo.service.LockService;
import com.winterframework.firefrog.game.service.IGameBetTypeStatusService;
import com.winterframework.firefrog.game.service.IGameUserAwardGroupService;
import com.winterframework.firefrog.game.service.assist.bet.SpecialLotteryAssistFactory;
import com.winterframework.firefrog.game.service.order.utlis.GameAssistProcess;
import com.winterframework.firefrog.game.web.util.IssueCodeUtil;
import com.winterframework.firefrog.user.dao.IUserCustomerDao;
import com.winterframework.orm.dal.ibatis3.BaseIbatis3Dao;

/** 
* @ClassName: GameSlipDaoImpl 
* @Description: 游戏投注订单注单DAO
* @author Denny 
* @date 2013-7-22 下午2:29:39 
*  
*/
@Repository("gameSlipDaoImpl")
public class GameSlipDaoImpl extends BaseIbatis3Dao<GameSlip> implements IGameSlipDao {
	@Resource(name = "fileUtil")
	private FileUtil fileUtil;

	@Resource(name = "gameOrderDaoImpl")
	private IGameOrderDao gameOrderDao;

	@Resource(name = "gameAwardDaoImpl")
	private IGameAwardDao gameAwardDao;

	@Resource(name = "gameReturnPointDaoImpl")
	private IGameReturnPointDao gameReturnPointDao;

	@Resource(name = "gameBettypeStatusDaoImpl")
	private IGameBettypeStatusDao bettypeStatusDao;

	@Resource(name = "userCustomerDaoImpl")
	private IUserCustomerDao userCustomerDao;

	@Resource(name = "gameUserAwardGroupDaoImpl")
	private IGameUserAwardGroupDao gameUserAwardGroupDao;

	@Resource(name = "slipItemAssistDaoImpl")
	private ISlipItemAssistDao slipItemAssistDaoImpl;

	@Resource(name = "specialLotteryAssistFactory")
	private SpecialLotteryAssistFactory specialLotteryAssistFactory;

	@Resource(name = "gameAssistProcess")
	private GameAssistProcess gameAssistProcess;

	@Resource(name = "gamePointDaoImpl")
	private IGamePointDao gamePointDaoImpl;

	@Resource(name = "lockService")
	private LockService lockService;
	@Resource(name = "gameUserAwardGroupServiceImpl")
	private IGameUserAwardGroupService gameUserAwardGroupService;
	@Resource(name = "gameBetTypeStatusServiceImpl")
	private IGameBetTypeStatusService gameBetTypeStatusService;
	@Resource(name = "gameBettypeAssistDaoimpl")
	private IGameBettypeAssistDao gameBettypeAssistDao;
	
	

	@Override
	@Deprecated
	public void saveSlip(com.winterframework.firefrog.game.entity.GameSlip gameOrderDetail, Long orderId) {
		GameSlip gameSlip = VOConverter.gameSlipEntity2GameSlipVo(gameOrderDetail, new GameOrder(orderId));
		//if (gameOrderDetail.getGameOrder().getFileMode().getValue() == fileMode.file.getValue()) {
		if (gameOrderDetail.getFileMode()== fileMode.file.getValue()) {
			gameSlip.setBetDetail(fileUtil.string2File(gameOrderDetail.getBetDetail(), gameOrderDetail.getGameOrder()
					.getOrderCode(), new Date()));
		} else {
			gameSlip.setBetDetail(gameOrderDetail.getBetDetail());
		}
		insert(gameSlip);
	}
	
	/**
	* Title: saveSlipList
	* Description:
	* @param details
	* @param orderId
	* @param planDetailId
	* @throws Exception 
	* @see com.winterframework.firefrog.game.dao.IGameSlipDao#saveSlipList(java.util.List, java.lang.Long, java.lang.Long) 
	*/
	@Override
	public void saveSlipList(List<com.winterframework.firefrog.game.entity.GameSlip> details,GamePackage gamePackage, Long orderId,
			Long planDetailId) throws Exception {
		log.info("saveSlipList orderId=" + orderId + ", details.szie = " + details.size());
		GameOrder gameOrder = gameOrderDao.getById(orderId);
		int i = 1;
		List<GameSlip> gameSlips = new ArrayList<GameSlip>();
		Map<String,Long> itemsMap=new HashMap<String,Long>();
		for(GamePackageItem itme:gamePackage.getItemList()){
			itemsMap.put(org.apache.commons.codec.digest.DigestUtils.md5Hex(itme.getGameBetType().getBetTypeCode()+itme.getBetDetail()+itme.getMoneyMode().getValue()),itme.getId());
		}
		GameBetType tempBetType = null;
		for (com.winterframework.firefrog.game.entity.GameSlip detail : details) {
			tempBetType = detail.getGameBetType();

			//特殊处理北京快乐8的趣味(因为只有趣味的玩法才能为空)
			Long status = 0L;
			if (gameOrder.getLotteryid().longValue() == 99201L && tempBetType.getBetMethodCode() == null) {
				status = bettypeStatusDao.getStatusOfBjkl8Interest(gameOrder.getLotteryid(),
						tempBetType.getGameGroupCode(), tempBetType.getGameSetCode(), detail.getBetDetail());
			} else {
				status = bettypeStatusDao.getStatus(gameOrder.getLotteryid(), tempBetType.getGameGroupCode(),
						tempBetType.getGameSetCode(), tempBetType.getBetMethodCode());
			}
			if (null == status || status == 0) {
				//当配置不存在或者投注方式暂停时抛出暂停异常
				throw new GameBetMethodStatusStopException(tempBetType.getGameGroupCode() + ""
						+ tempBetType.getGameSetCode() + tempBetType.getBetMethodCode());
			}
			GameSlip gameSlip = VOConverter.gameSlipEntity2GameSlipVo(detail, gameOrder);
			if (detail.getFileMode() == fileMode.file.getValue()) {
				gameSlip.setBetDetail(fileUtil.string2File(detail.getBetDetail(), gameOrder.getOrderCode() + i++,
						new Date()));
			} else {
				gameSlip.setBetDetail(detail.getBetDetail());
			} 
			String betTypeCode=gameSlip.getBetTypeCode();
			Long retPoint=gameUserAwardGroupService.getRetPointByUserIdAndLotteryIdAndBetTypeCode(gameOrder.getUserid(), gameOrder.getLotteryid(), betTypeCode);
			gameSlip.setRetPoint(Long.valueOf(retPoint));
			BetMethodDescription description = new BetMethodDescription(gameOrder.getLotteryid(), detail
					.getGameBetType().getGameGroupCode(), detail.getGameBetType().getGameSetCode(), detail
					.getGameBetType().getBetMethodCode());
			List<ItemAssist> assistList = specialLotteryAssistFactory.getSlipItemAssistList(description,
					gameOrder.getUserid());

			if(gamePackage.getGameIssue().getIssueCode().equals(detail.getIssueCode().getIssueCode())){
			if (assistList == null || assistList.isEmpty()) {
				if(com.winterframework.firefrog.game.dao.vo.GamePackageItem.AwardMode.HIGH.getValue()==gameSlip.getAwardMode().intValue()){
					GameBettypeStatus betTypeStatus=gameBetTypeStatusService.getByLotteryIdAndBetTypeCode(gameOrder.getLotteryid(), betTypeCode);
					//如果是元模式的话，则奖金*返点/10000就是奖金，如果是角色模式,奖金.100000*10为奖金，这样做为了把角模式的最后一位去掉
					//gameSlip.setRetAward(formatLong(gameSlip.getMoneyMode(),betTypeStatus.getTheoryBonus()*retPoint/10000));
					gameSlip.setRetAward(betTypeStatus.getTheoryBonus()*retPoint/10000);
				}
				//初始化彩种的预期金额（包括特殊彩种）
				gameSlip.setSingleWin(gameAssistProcess.getBonus(gameOrder.getLotteryid(), detail.getGameBetType()
						.getBetTypeCode(), gameOrder.getUserid(), gameSlip.getMoneyMode(), gameSlip.getMultiple(), gameSlip.getRetAward()));
				gameSlip.setSingleWinDown(gameAssistProcess.getBonusDown(gameOrder.getLotteryid(), detail.getGameBetType()
						.getBetTypeCode(), gameOrder.getUserid(), gameSlip.getMoneyMode(), gameSlip.getMultiple(), gameSlip.getRetAward()));
				gameSlip.setMutiAward(0l);
			} else {
				Map<String,Long> theoryBonusAssistMap=new HashMap<String,Long>();
				if(com.winterframework.firefrog.game.dao.vo.GamePackageItem.AwardMode.HIGH.getValue()==gameSlip.getAwardMode().intValue()){
					GameBettypeStatus betTypeStatus=gameBetTypeStatusService.getByLotteryIdAndBetTypeCode(gameOrder.getLotteryid(), betTypeCode);
					List<Long> idList=new ArrayList<Long>();
					idList.add(betTypeStatus.getId());
					List<GameBettypeAssist> bettypeAssistList=gameBettypeAssistDao.getBettypeAssistListByTypeIds(idList);
					
					for(GameBettypeAssist bettypeAssist:bettypeAssistList){
						theoryBonusAssistMap.put(betTypeCode+"_"+bettypeAssist.getMethodType(), bettypeAssist.getTheoryBonus());
					}
					for(ItemAssist itemAssist:assistList){
						itemAssist.setEvaluatAward(itemAssist.getEvaluatAward()+theoryBonusAssistMap.get(itemAssist.getBetTypeCode())*retPoint/10000);
					}
				}
				gameSlip.setMutiAward(1L);
			}
			
			insert(gameSlip);
			}
			if (!lockService.checkLotteryId(gameOrder.getLotteryid())) {//封锁彩种不用保存slipassit
				saveGameSlipAssist(assistList, gameSlip.getMoneyMode(), gameSlip.getMultiple(), gameSlip.getId());
			}
			gameSlips.add(gameSlip);
			
			log.info("save gameSlip success,OrderId =" + orderId + ", gameSlipId = " + gameSlip.getId());
		    String betDetail="";
			if (detail.getFileMode() == fileMode.file.getValue()) {
				File file = new File(gameSlip.getBetDetail());
				String content = null;
				try {
					content = FileUtils.readFileToString(file);
				} catch (IOException e) {
					e.printStackTrace();
				}
				betDetail=content;
			} else {
				betDetail=gameSlip.getBetDetail();
			}
			gamePointDaoImpl.insertGamePointSlipId(planDetailId,itemsMap.get(org.apache.commons.codec.digest.DigestUtils.md5Hex(gameSlip.getBetTypeCode()+betDetail+gameSlip.getMoneyMode())) ,gameSlip.getId(), betDetail);
		}
	}
	private static   Long formatLong(Long moneyModel,Long aaa){
		//System.out.println(" Long formatLong(Long moneyModel,Long aaa)============"+aaa);
		if(aaa==null) return null;
		if(1L==moneyModel){
		    aaa= NumberUtils.toLong(String.valueOf(aaa/100)+"00");
		}else if(3L==moneyModel){
		    aaa= NumberUtils.toLong(String.valueOf(aaa/100)+"0000");
		}else{
			aaa= NumberUtils.toLong(String.valueOf(aaa/1000)+"000");
		}
		//System.out.println(" Long formatLong(Long moneyModel,Long aaa)2============"+aaa);

		return aaa;
	}

	@Override
	public void saveSlipList(List<com.winterframework.firefrog.game.entity.GameSlip> details, Long orderId)
			throws Exception {

		log.info("saveSlipList orderId=" + orderId + ", details.szie = " + details.size());
		GameOrder gameOrder = gameOrderDao.getById(orderId);
		int i = 1;
		List<GameSlip> gameSlips = new ArrayList<GameSlip>();
		
		GameBetType tempBetType = null;
		for (com.winterframework.firefrog.game.entity.GameSlip detail : details) {
			tempBetType = detail.getGameBetType();
			//特殊处理北京快乐8的趣味(因为只有趣味的玩法才能为空)
			Long status = 0L;
			if (gameOrder.getLotteryid().longValue() == 99201L && tempBetType.getBetMethodCode() == null) {
				status = bettypeStatusDao.getStatusOfBjkl8Interest(gameOrder.getLotteryid(),
						tempBetType.getGameGroupCode(), tempBetType.getGameSetCode(), detail.getBetDetail());
			} else {
				status = bettypeStatusDao.getStatus(gameOrder.getLotteryid(), tempBetType.getGameGroupCode(),
						tempBetType.getGameSetCode(), tempBetType.getBetMethodCode());
			}
			if (null == status || status == 0) {
				//当配置不存在或者投注方式暂停时抛出暂停异常
				throw new GameBetMethodStatusStopException(tempBetType.getGameGroupCode() + ""
						+ tempBetType.getGameSetCode() + tempBetType.getBetMethodCode());
			}
			GameSlip gameSlip = VOConverter.gameSlipEntity2GameSlipVo(detail, gameOrder);
			if (detail.getFileMode() == fileMode.file.getValue()) {
				gameSlip.setBetDetail(fileUtil.string2File(detail.getBetDetail(), gameOrder.getOrderCode() + i++,
						new Date()));
			} else {
				gameSlip.setBetDetail(detail.getBetDetail());
			} 
			String betTypeCode=gameSlip.getBetTypeCode();
			Long retPoint = 0l;
			if(gameOrder.getLotteryid().longValue() == 99701l){
				retPoint=gameUserAwardGroupService.getRetPointByUserIdAndLotteryIdAndBetTypeCodeAndMultiple(gameOrder.getUserid(), gameOrder.getLotteryid(), betTypeCode,Long.valueOf(detail.getSingleWin()));
			}else{
				retPoint=gameUserAwardGroupService.getRetPointByUserIdAndLotteryIdAndBetTypeCode(gameOrder.getUserid(), gameOrder.getLotteryid(), betTypeCode);
			}
			
			gameSlip.setRetPoint(Long.valueOf(retPoint));
			BetMethodDescription description = new BetMethodDescription(gameOrder.getLotteryid(), detail
					.getGameBetType().getGameGroupCode(), detail.getGameBetType().getGameSetCode(), detail
					.getGameBetType().getBetMethodCode());
			List<ItemAssist> assistList = specialLotteryAssistFactory.getSlipItemAssistList(description,
					gameOrder.getUserid());
			if (assistList == null || assistList.isEmpty()) {
				if(com.winterframework.firefrog.game.dao.vo.GamePackageItem.AwardMode.HIGH.getValue()==gameSlip.getAwardMode().intValue()){
					GameBettypeStatus betTypeStatus=gameBetTypeStatusService.getByLotteryIdAndBetTypeCode(gameOrder.getLotteryid(), betTypeCode);
					gameSlip.setRetAward(betTypeStatus.getTheoryBonus()*retPoint/10000);
					//	gameSlip.setRetAward(formatLong(gameSlip.getMoneyMode(),betTypeStatus.getTheoryBonus()*retPoint/10000));
					
				}
				//初始化彩种的预期金额（包括特殊彩种）
				if(gameOrder.getLotteryid().longValue() == 99701l){
					gameSlip.setSingleWin(Long.valueOf(detail.getSingleWin()));
				}else{
					gameSlip.setSingleWin(gameAssistProcess.getBonus(gameOrder.getLotteryid(), detail.getGameBetType()
							.getBetTypeCode(), gameOrder.getUserid(), gameSlip.getMoneyMode(), gameSlip.getMultiple(),gameSlip.getRetAward()));
					gameSlip.setSingleWinDown(gameAssistProcess.getBonusDown(gameOrder.getLotteryid(), detail.getGameBetType()
							.getBetTypeCode(), gameOrder.getUserid(), gameSlip.getMoneyMode(), gameSlip.getMultiple(), gameSlip.getRetAward()));
				}
				gameSlip.setMutiAward(0l);
			} else {
				Map<String,Long> theoryBonusAssistMap=new HashMap<String,Long>();
				if(com.winterframework.firefrog.game.dao.vo.GamePackageItem.AwardMode.HIGH.getValue()==gameSlip.getAwardMode().intValue()){
					GameBettypeStatus betTypeStatus=gameBetTypeStatusService.getByLotteryIdAndBetTypeCode(gameOrder.getLotteryid(), betTypeCode);
					List<Long> idList=new ArrayList<Long>();
					idList.add(betTypeStatus.getId());
					List<GameBettypeAssist> bettypeAssistList=gameBettypeAssistDao.getBettypeAssistListByTypeIds(idList);
					
					for(GameBettypeAssist bettypeAssist:bettypeAssistList){
						theoryBonusAssistMap.put(betTypeCode+"_"+bettypeAssist.getMethodType(), bettypeAssist.getTheoryBonus());
					}
					for(ItemAssist itemAssist:assistList){
						itemAssist.setEvaluatAward(itemAssist.getEvaluatAward()+theoryBonusAssistMap.get(itemAssist.getBetTypeCode())*retPoint/10000);
					}
				}
				gameSlip.setMutiAward(1L);
			}			
			insert(gameSlip);
			// 封锁彩种不用保存slipassit，雖然封鎖彩種不能有輔助玩法，但六合彩是例外。
			if (!lockService.checkLotteryId(gameOrder.getLotteryid()) || IssueCodeUtil.LOTTERYID_LHC == gameOrder.getLotteryid()) {
				saveGameSlipAssist(assistList, gameSlip.getMoneyMode(), gameSlip.getMultiple(), gameSlip.getId());
			}
			gameSlips.add(gameSlip);
			log.info("save gameSlip success,OrderId =" + orderId + ", gameSlipId = " + gameSlip.getId());

			//保存变价列表,六合彩不需要存入GamePoint
			if (detail.getLockPoints() != null && gameOrder.getLotteryid().longValue() != IssueCodeUtil.LOTTERYID_LHC) {
				List<Points> pointList = detail.getLockPoints().getPoints();
				if (pointList != null && !pointList.isEmpty()) {
					List<GamePoint> voList = VOConverter.convertGamePointToVo(pointList, gameSlip.getId());
					gamePointDaoImpl.saveGamePoints(voList);
				}
			}
		}
	}

	/** 
	* @Title: saveGameSlipAssist 
	* @Description: 保存gameSlip的辅助信息
	* @param assistList
	* @param moneyMode 金钱模式 
	* @param multiple 倍数
	* @param slipId 
	*/
	private void saveGameSlipAssist(List<ItemAssist> assistList, Long moneyMode, Long multiple, Long slipId) {
		if (assistList != null && !assistList.isEmpty()) {
			List<SlipItemAssist> list = new ArrayList<SlipItemAssist>();
			for (ItemAssist itemAssist : assistList) {
				SlipItemAssist assist = new SlipItemAssist(itemAssist);
				Long processMoney = gameAssistProcess.getBonus(assist.getEvaluatAward(), moneyMode, multiple);
				assist.setEvaluatAward(processMoney);
				Long processMoneyDown = gameAssistProcess.getBonus(assist.getEvaluatAwardDown(), moneyMode, multiple);
				assist.setEvaluatAwardDown(processMoneyDown);
				assist.setSlipId(slipId);
				list.add(assist);
			}
			slipItemAssistDaoImpl.saveSlipAssistItem(list);
		}

	}

	@Override
	public List<com.winterframework.firefrog.game.entity.GameSlip> getSlipsByOrderId(long orderId) {
		List<GameSlip> gameSlips = sqlSessionTemplate.selectList("getSlipsByOrderId", orderId);
		List<com.winterframework.firefrog.game.entity.GameSlip> gameOrderDetails = new ArrayList<com.winterframework.firefrog.game.entity.GameSlip>();
		for (GameSlip gs : gameSlips) {
			com.winterframework.firefrog.game.entity.GameSlip god = VOConverter.gameSlip2GameOrderDetail(gs);
			gameOrderDetails.add(god);
		}
		return gameOrderDetails;
	}
	
	@Override
	public List<com.winterframework.firefrog.game.entity.GameSlip> getSlipsByMap(Map<String ,Object> map) {
		List<GameSlip> gameSlips = sqlSessionTemplate.selectList("getSlipsByMap", map);
		List<com.winterframework.firefrog.game.entity.GameSlip> gameOrderDetails = new ArrayList<com.winterframework.firefrog.game.entity.GameSlip>();
		for (GameSlip gs : gameSlips) {
			com.winterframework.firefrog.game.entity.GameSlip god = VOConverter.gameSlip2GameOrderDetail(gs);
			gameOrderDetails.add(god);
		}
		return gameOrderDetails;
	}

	@Override
	public int updateGameOrderDetailByOrderId(Long orderId, Long issueCode, Long cancelTime) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("cancelTime", DataConverterUtil.convertLong2Date(cancelTime));
		map.put("status", GameSlipStatus.CANCEL.getValue());
		map.put("orderId", orderId);
		map.put("issueCode", issueCode);
		return this.sqlSessionTemplate.update("updateGameOrderDetailByOrderId", map);
	}

	@Override
	public List<GameSlipOperationsEntity> querySlipOperationsListByOrderID(Long orderid) throws Exception {
		List<GameSlipOperationsEntity> slips = this.sqlSessionTemplate.selectList("querySlipOperationsListByOrderID",
				orderid);
		return slips;
	}

	@Override
	public List<GameSlipOperationsEntity> querySlipOperationsListByPlanID(Long planid) throws Exception {
		List<GameSlipOperationsEntity> slips = this.sqlSessionTemplate.selectList("querySlipOperationsListByPlanID",
				planid);
		return slips;
	}

	@Override
	public List<GameSlip> querySlipByLotteryIdAndIssueCode(Long lotteryId, Long issueCode) throws Exception {

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("lotteryId", lotteryId);
		map.put("issueCode", issueCode);

		return this.sqlSessionTemplate.selectList("querySlipByLotteryIdAndIssueCode", map);
	}

	@Override
	public List<GameSlip> querySlipsByPlanId(long planId) {
		return this.sqlSessionTemplate.selectList("querySlipsByPlanId", planId);
	}

	@Override
	public void delSlipByOrderId(Long orderId, Long lotteryId) {

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("orderId", orderId);
		map.put("lotteryId", lotteryId);

		this.sqlSessionTemplate.delete("delSlipByOrderId", map);
	}

	@Override
	public void updateGameOrderDetailCancalByOrderId(Long orderId, Long issueCode) {

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("orderId", orderId);
		map.put("issueCode", issueCode);
		map.put("status", GameSlipStatus.CANCEL.getValue());

		this.sqlSessionTemplate.update("updateGameOrderDetailCancalByOrderId", map);
	}

	@Override
	public Long queryAllSlipSale(Long lotteryId, Long issueCode, int moneyModel) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("lotteryId", lotteryId);
		map.put("issueCode", issueCode);
		map.put("moneyModel", moneyModel);
		return this.sqlSessionTemplate.selectOne("queryAllSlipSale", map);
	}

	@Override
	public List<com.winterframework.firefrog.game.entity.GameSlip> getCurrentIssueUserBetRecord(String betTypeCode,
			long userId, long lotteryId, Long issueCode) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("lotteryId", lotteryId);
		map.put("issueCode", issueCode);
		map.put("userId", userId);
		map.put("betTypeCode", betTypeCode);
		map.put("status", 1);
		List<GameSlip> gameSlips = this.sqlSessionTemplate.selectList("getCurrentIssueUserBetRecord", map);
		List<com.winterframework.firefrog.game.entity.GameSlip> gameOrderDetails = new ArrayList<com.winterframework.firefrog.game.entity.GameSlip>();
		for (GameSlip gs : gameSlips) {
			com.winterframework.firefrog.game.entity.GameSlip god = VOConverter.gameSlip2GameOrderDetail(gs);
			gameOrderDetails.add(god);
		}
		return gameOrderDetails;
	}
	
	@Override
	public List<GameSlip> querySlipByOrder(Long orderId) throws Exception {
		return this.sqlSessionTemplate.selectList("getSlipsByOrderId", orderId);
	}
	
	@Override
	public int changeStatus(Long orderId, Integer fromStatus, Integer toStatus) { 
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("orderId", orderId);
		map.put("fromStatus", fromStatus);
		map.put("toStatus", toStatus);  
		return this.sqlSessionTemplate.update(getQueryPath("changeStatus"), map); 
	}
	
	@Override
	public void updateSlipByOrderID(Long orderID, Integer status) throws Exception {
		Map<String, Object> slipMap = new HashMap<String, Object>();

		slipMap.put("status", status);
		slipMap.put("orderId", orderID);

		this.sqlSessionTemplate.update("updateGameSlipByOrderId", slipMap);
	}

	@Override
	public List<com.winterframework.firefrog.game.entity.GameSlip> querySlips(long lotteryId, long userId,
			long issueCode, int status) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("lotteryId", lotteryId);
		map.put("issueCode", issueCode);
		map.put("userId", userId);
		map.put("status", 2);
		List<GameSlip> gameSlips = this.sqlSessionTemplate.selectList(this.getQueryPath("querySlips"), map);
		List<com.winterframework.firefrog.game.entity.GameSlip> gameOrderDetails = new ArrayList<com.winterframework.firefrog.game.entity.GameSlip>();
		for (GameSlip gs : gameSlips) {
			com.winterframework.firefrog.game.entity.GameSlip god = VOConverter.gameSlip2GameOrderDetail(gs);
			gameOrderDetails.add(god);
		}
		return gameOrderDetails;
	}
	
	@Override
	public List<com.winterframework.firefrog.game.entity.GameSlip> querySlipsByCondition(long lotteryId, long userId,
			long issueCode, String betTypeCode, String betdetail, int status) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("lotteryId", lotteryId);
		map.put("issueCode", issueCode);
		map.put("userId", userId);
		map.put("status", status);
		map.put("betTypeCode", betTypeCode);
		map.put("betDetail", betdetail);
		List<GameSlip> gameSlips = this.sqlSessionTemplate.selectList(this.getQueryPath("querySlipsByCondition"), map);
		List<com.winterframework.firefrog.game.entity.GameSlip> gameOrderDetails = new ArrayList<com.winterframework.firefrog.game.entity.GameSlip>();
		for (GameSlip gs : gameSlips) {
			com.winterframework.firefrog.game.entity.GameSlip god = VOConverter.gameSlip2GameOrderDetail(gs);
			gameOrderDetails.add(god);
		}
		return gameOrderDetails;
	}
	
	@Override
	public List<com.winterframework.firefrog.game.entity.GameSlip> querySlipsByIssueCode(long issueCode) {
		List<GameSlip> gameSlips = this.sqlSessionTemplate.selectList("querySlipsByIssueCode", issueCode);
		List<com.winterframework.firefrog.game.entity.GameSlip> gameSlip = new ArrayList<com.winterframework.firefrog.game.entity.GameSlip>();
		for (GameSlip gs : gameSlips) {
			com.winterframework.firefrog.game.entity.GameSlip god = VOConverter.gameSlip2GameOrderDetail(gs);
			gameSlip.add(god);
		}
		return gameSlip;
	}
	
	@Override
	public List<com.winterframework.firefrog.game.entity.GameSlip> querySlipsByOrderIdList(List<Long> orderId) {
		List<GameSlip> gameSlips = this.sqlSessionTemplate.selectList("querySlipsByOrderIdList", orderId);
		List<com.winterframework.firefrog.game.entity.GameSlip> gameSlip = new ArrayList<com.winterframework.firefrog.game.entity.GameSlip>();
		for (GameSlip gs : gameSlips) {
			com.winterframework.firefrog.game.entity.GameSlip god = VOConverter.gameSlip2GameOrderDetail(gs);
			gameSlip.add(god);
		}
		return gameSlip;
	}

	@Override   
	public List<com.winterframework.firefrog.game.entity.GameBetDetailTotAmountEntity> queryBetDetaiByIssudeCode(long issueCode ,long lotteryId) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("lotteryId", lotteryId);
		map.put("issueCode", issueCode);
		List<GameBetDetailTotAmount> gameSlips = this.sqlSessionTemplate.selectList("queryBetDetaiByIssudeCode", map);
		List<com.winterframework.firefrog.game.entity.GameBetDetailTotAmountEntity> gameBetDetailTotAmountList = new ArrayList<com.winterframework.firefrog.game.entity.GameBetDetailTotAmountEntity>();
		for (GameBetDetailTotAmount gs : gameSlips) {
			com.winterframework.firefrog.game.entity.GameBetDetailTotAmountEntity god = VOConverter.gameSlip2BetDetailTotAmount(gs);
			gameBetDetailTotAmountList.add(god);
		}
		return gameBetDetailTotAmountList;
	}
	

}
