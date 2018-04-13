package com.winterframework.firefrog.game.dao.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.winterframework.firefrog.common.util.FileUtil;
import com.winterframework.firefrog.game.dao.IGameBettypeAssistDao;
import com.winterframework.firefrog.game.dao.IGameBettypeStatusDao;
import com.winterframework.firefrog.game.dao.IGameOrderDao;
import com.winterframework.firefrog.game.dao.IGamePointDao;
import com.winterframework.firefrog.game.dao.IGameReturnPointDao;
import com.winterframework.firefrog.game.dao.IGameSlipDao;
import com.winterframework.firefrog.game.dao.IGameUserAwardDao;
import com.winterframework.firefrog.game.dao.IPackageItemAssistDao;
import com.winterframework.firefrog.game.dao.ISlipItemAssistDao;
import com.winterframework.firefrog.game.dao.vo.GameBettypeAssist;
import com.winterframework.firefrog.game.dao.vo.GameBettypeStatus;
import com.winterframework.firefrog.game.dao.vo.GameOrder;
import com.winterframework.firefrog.game.dao.vo.GameSlip;
import com.winterframework.firefrog.game.dao.vo.VOConverter;
import com.winterframework.firefrog.game.entity.BetMethodDescription;
import com.winterframework.firefrog.game.entity.FileMode;
import com.winterframework.firefrog.game.entity.GameBetType;
import com.winterframework.firefrog.game.entity.ItemAssist;
import com.winterframework.firefrog.game.entity.SlipItemAssist;
import com.winterframework.firefrog.game.service.IGameBetTypeStatusService;
import com.winterframework.firefrog.game.service.IGameUserAwardGroupService;
import com.winterframework.firefrog.game.service.assist.bet.SpecialLotteryAssistFactory;
import com.winterframework.firefrog.game.service.wincaculate.amount.LotteryWinAmountCaculator;
import com.winterframework.orm.dal.ibatis3.BaseIbatis3Dao;

@Repository("gameSlipDaoImpl")
public class GameSlipDaoImpl extends BaseIbatis3Dao<GameSlip> implements IGameSlipDao {

	@Resource(name = "fileUtil")
	private FileUtil fileUtil;

	@Resource(name = "gameOrderDaoImpl")
	private IGameOrderDao gameOrderDao;

	@Resource(name = "gameReturnPointDaoImpl")
	private IGameReturnPointDao gameReturnPointDao;

	@Resource(name = "gameUserAwardDaoImpl")
	private IGameUserAwardDao userAwardDao;

	@Resource(name = "lotteryWinAmountCaculator")
	private LotteryWinAmountCaculator lotteryWinAmountCaculator;

	@Resource(name = "gameBettypeStatusDaoImpl")
	private IGameBettypeStatusDao bettypeStatusDao;

	@Resource(name = "specialLotteryAssistFactory")
	private SpecialLotteryAssistFactory specialLotteryAssistFactory;

	@Resource(name = "gameAssistProcess")
	private GameAssistProcess gameAssistProcess;

	@Resource(name = "slipItemAssistDaoImpl")
	private ISlipItemAssistDao slipItemAssistDaoImpl;

	@Resource(name = "gamePointDaoImpl")
	private IGamePointDao gamePointDaoImpl;
	@Resource(name = "packageItemAssistDaoImpl")
	private IPackageItemAssistDao packageItemAssistDao;
	@Resource(name = "gameBetTypeStatusServiceImpl")
	private IGameBetTypeStatusService gameBetTypeStatusService;
	@Resource(name = "gameBettypeAssistDaoimpl")
	private IGameBettypeAssistDao gameBettypeAssistDao;
	@Resource(name = "gameUserAwardGroupServiceImpl")
	private IGameUserAwardGroupService gameUserAwardGroupService;
	
	
	

	@Override
	public List<GameSlip> querySlipByLotteryIdAndIssueCode(Long lotteryId, Long issueCode) throws Exception {

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("lotteryId", lotteryId);
		map.put("issueCode", issueCode);

		return this.sqlSessionTemplate.selectList("querySlipByLotteryIdAndIssueCode", map);
	}

	@Override
	public List<GameSlip> querySlipByOrder(Long orderId) throws Exception {
		return this.sqlSessionTemplate.selectList("getSlipsByOrderId", orderId);
	}

	@Override
	public void updateSlipByOrderID(Long orderID, Integer status) throws Exception {
		Map<String, Object> slipMap = new HashMap<String, Object>();

		slipMap.put("status", status);
		slipMap.put("orderId", orderID);

		this.sqlSessionTemplate.update("updateGameSlipByOrderId", slipMap);
	}

	@Override
	public String queryOrderCodeBySlipID(Long id) throws Exception {
		return this.sqlSessionTemplate.selectOne("queryOrderCodeBySlipID", id);
	}

	@Override
	public void revocationGameSlipsByOrderId(Long orderId, int status) {
		Map<String, Object> slipMap = new HashMap<String, Object>();

		slipMap.put("status", status);
		slipMap.put("orderId", orderId);
		slipMap.put("cancelModes", 2);
		slipMap.put("cancelTime", new Date());

		this.sqlSessionTemplate.update("revocationGameSlipsByOrderId", slipMap);
	}

	@Override
	public void undoGameSlip(Long lotteryId, Long issueCode, Date saleTime, int aimStatus) {
		Map<String, Object> orderMap = new HashMap<String, Object>();
		orderMap.put("lotteryId", lotteryId);
		orderMap.put("issueCode", issueCode);
		if (saleTime != null) {
			orderMap.put("saleTime", saleTime);
		}
		orderMap.put("status", aimStatus);
		this.sqlSessionTemplate.update(getQueryPath("undoGameSlip"), orderMap);
	}

	@Override
	public void saveSlipList(List<com.winterframework.firefrog.game.entity.GameSlip> details,
			Long orderId,List<com.winterframework.firefrog.game.dao.vo.GamePackageItem> itemList) throws Exception {

		GameOrder gameOrder = gameOrderDao.getById(orderId);
		int i = 1;
		List<GameSlip> gameSlips = new ArrayList<GameSlip>();
		Map<String, Long> itemsMap = new HashMap<String, Long>(); 		  
		for (com.winterframework.firefrog.game.dao.vo.GamePackageItem item : itemList) {
			/*String content=item.getBetDetail();
			if (item.getFileMode() == 1) {
				// 文件模式将文件内容读取出来，读取方式直接读取有误问题？
				File file = new File(item.getBetDetail()); 
				try {
					content = FileUtils.readFileToString(file);
				} catch (IOException e) {
					log.error("读取gamePackageItem投注内容错误，itemId="+item.getId()+e);
					throw new RuntimeException("读取gamePackageItem投注内容错误");
				} 
			} */
			itemsMap.put(
					org.apache.commons.codec.digest.DigestUtils.md5Hex(item.getBetTypeCode()
							+ item.getBetDetail()+item.getMoneyMode()), item.getId()); 
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
			GameSlip gameSlip = VOConverter.gameSlipEntity2GameSlipVo(detail, gameOrder);
			/*--Gary
			if (detail.getGameOrder().getFileMode().getValue() == fileMode.file.getValue()) {
				gameSlip.setBetDetail(fileUtil.string2File(detail.getBetDetail(), gameOrder.getOrderCode() + i++,
						new Date()));
			} else {
				gameSlip.setBetDetail(detail.getBetDetail());
			}*/
			gameSlip.setBetDetail(detail.getBetDetail()); 
			BetMethodDescription description = new BetMethodDescription(gameOrder.getLotteryid(), detail
					.getGameBetType().getGameGroupCode(), detail.getGameBetType().getGameSetCode(), detail
					.getGameBetType().getBetMethodCode());
			List<ItemAssist> assistList = specialLotteryAssistFactory.getSlipItemAssistList(description,
					gameOrder.getUserid());

			Long retPoint = 0l;
			String betTypeCode=gameSlip.getBetTypeCode();
			if(gameOrder.getLotteryid().longValue() == 99701l){
				retPoint=gameUserAwardGroupService.getRetPointByUserIdAndLotteryIdAndBetTypeCodeAndMultiple(gameOrder.getUserid(), gameOrder.getLotteryid(), betTypeCode,Long.valueOf(detail.getSingleWin()));
			}else{
				retPoint=gameUserAwardGroupService.getRetPointByUserIdAndLotteryIdAndBetTypeCode(gameOrder.getUserid(), gameOrder.getLotteryid(), betTypeCode);
			}
			if (assistList == null || assistList.isEmpty()) {
				if(com.winterframework.firefrog.game.dao.vo.GamePackageItem.AwardMode.HIGH.getValue()==gameSlip.getAwardMode().intValue()){
					GameBettypeStatus betTypeStatus=gameBetTypeStatusService.getGameBetTypeStatusByLotteryIdAndBetTypeCode(gameOrder.getLotteryid(), gameSlip.getBetTypeCode());
					//如果是元模式的话，则奖金*返点/10000就是奖金，如果是角色模式,奖金.100000*10为奖金，这样做为了把角模式的最后一位去掉
					//gameSlip.setRetAward(formatLong(gameSlip.getMoneyMode(),betTypeStatus.getTheoryBonus()*retPoint/10000));
					gameSlip.setRetAward(betTypeStatus.getTheoryBonus()*retPoint/10000);
				}
				//初始化彩种的预期金额（包括特殊彩种）
				gameSlip.setSingleWin(gameAssistProcess.getBonus(gameOrder.getLotteryid(), detail.getGameBetType()
						.getBetTypeCode(), gameOrder.getUserid(), gameSlip.getMoneyMode(), gameSlip.getMultiple(),gameSlip.getRetAward()));
				gameSlip.setSingleWinDown(gameAssistProcess.getBonusDown(gameOrder.getLotteryid(), detail.getGameBetType()
						.getBetTypeCode(), gameOrder.getUserid(), gameSlip.getMoneyMode(), gameSlip.getMultiple(),gameSlip.getRetAward()));
				gameSlip.setMutiAward(0L);
			} else {
				//重新计算：retAward
				Map<String,Long> theoryBonusAssistMap=new HashMap<String,Long>();
				if(com.winterframework.firefrog.game.dao.vo.GamePackageItem.AwardMode.HIGH.getValue()==gameSlip.getAwardMode().intValue()){
					GameBettypeStatus betTypeStatus=gameBetTypeStatusService.getGameBetTypeStatusByLotteryIdAndBetTypeCode(gameOrder.getLotteryid(), gameSlip.getBetTypeCode());
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
			String content=detail.getBetDetail();
			if(gameSlip.getFileMode() == FileMode.FILE.getValue()){ 
				gameSlip.setBetDetail(fileUtil.string2File(detail.getBetDetail(), gameOrder.getOrderCode() + i++,
						new Date())); 
			}
			insert(gameSlip);			
			gamePointDaoImpl.insertGamePointSlipId(
					detail.getPlanDetailId(),
					itemsMap.get(org.apache.commons.codec.digest.DigestUtils.md5Hex(gameSlip.getBetTypeCode()
							+ content+gameSlip.getMoneyMode())), gameSlip.getId(), detail.getBetDetail());

			saveGameSlipAssist(assistList, gameSlip.getMoneyMode(), gameSlip.getMultiple(), gameSlip.getId());
			gameSlips.add(gameSlip);
		}
		itemsMap=null;	//清空Map
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
				//处理圆角模式以及倍数
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
	public int changeStatus(Long orderId, Integer fromStatus, Integer toStatus) { 
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("orderId", orderId);
		map.put("fromStatus", fromStatus);
		map.put("toStatus", toStatus);  
		return this.sqlSessionTemplate.update(getQueryPath("changeStatus"), map); 
	}
}
