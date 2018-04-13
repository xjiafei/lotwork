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
import com.winterframework.firefrog.game.dao.IGamePackageItemDao;
import com.winterframework.firefrog.game.dao.IPackageItemAssistDao;
import com.winterframework.firefrog.game.dao.vo.GameBettypeAssist;
import com.winterframework.firefrog.game.dao.vo.GameBettypeStatus;
import com.winterframework.firefrog.game.dao.vo.GamePackageItem;
import com.winterframework.firefrog.game.dao.vo.VOConverter;
import com.winterframework.firefrog.game.entity.BetDetails.fileMode;
import com.winterframework.firefrog.game.entity.BetMethodDescription;
import com.winterframework.firefrog.game.entity.ItemAssist;
import com.winterframework.firefrog.game.entity.PackageItemAssist;
import com.winterframework.firefrog.game.service.IGameBetTypeStatusService;
import com.winterframework.firefrog.game.service.assist.bet.SpecialLotteryAssistFactory;
import com.winterframework.firefrog.game.service.order.utlis.GameAssistProcess;
import com.winterframework.orm.dal.ibatis3.BaseIbatis3Dao;

@Repository("gamePackageItemDao")
public class GamePackageItemDaoImpl extends BaseIbatis3Dao<GamePackageItem> implements IGamePackageItemDao {

	@Resource(name = "packageItemAssistDaoImpl")
	private IPackageItemAssistDao packageItemAssistDaoImpl;
	@Resource(name = "specialLotteryAssistFactory")
	private SpecialLotteryAssistFactory specialLotteryAssistFactory;

	@Resource(name = "gameAssistProcess")
	private GameAssistProcess gameAssistProcess;
	@Resource(name = "gameBetTypeStatusServiceImpl")
	private IGameBetTypeStatusService gameBetTypeStatusService;
	@Resource(name = "gameBettypeAssistDaoimpl")
	private IGameBettypeAssistDao gameBettypeAssistDao;
	
	@Resource(name = "fileUtil")
	private FileUtil fileUtil;

	@Override
	public void savePackageitem(com.winterframework.firefrog.game.entity.GamePackageItem gamePackageItemEntity) {
		GamePackageItem gamePackageItem = VOConverter.gpiEntity2gpiVo(gamePackageItemEntity);
		insert(gamePackageItem);
	}

	@Override
	public void savePackageitemList(
			List<com.winterframework.firefrog.game.entity.GamePackageItem> gamePackageItemEntityList,com.winterframework.firefrog.game.entity.GamePackage gamePackage) throws Exception{
		List<GamePackageItem> gamePackageItemList = new ArrayList<GamePackageItem>();
		int i = 1;
		for (com.winterframework.firefrog.game.entity.GamePackageItem itemEntity : gamePackageItemEntityList) {
			GamePackageItem gamePackageItem = VOConverter.gpiEntity2gpiVo(itemEntity);
			gamePackageItemList.add(gamePackageItem);
			BetMethodDescription description = new BetMethodDescription(itemEntity.getGamePackage().getLottery()
					.getLotteryId(), itemEntity.getGameBetType().getGameGroupCode(), itemEntity.getGameBetType()
					.getGameSetCode(), itemEntity.getGameBetType().getBetMethodCode());
			List<ItemAssist> itemList = specialLotteryAssistFactory.getSlipItemAssistList(description, itemEntity
					.getGamePackage().getUser().getId());
			if (itemList == null || itemList.isEmpty()) {
				if(com.winterframework.firefrog.game.dao.vo.GamePackageItem.AwardMode.HIGH.getValue()==gamePackageItem.getAwardMode().intValue()){
					GameBettypeStatus betTypeStatus=gameBetTypeStatusService.getByLotteryIdAndBetTypeCode(itemEntity.getGamePackage().getLottery().getLotteryId(), gamePackageItem.getBetTypeCode());
					gamePackageItem.setRetAward(betTypeStatus.getTheoryBonus()*gamePackageItem.getRetPoint()/10000);
					//packageItem.setRetAward(formatLong(packageItem.getMoneyMode().getValue(),betTypeStatus.getTheoryBonus()*retPoint/10000));
				}
				//TODO 六合彩目前無追號,GAME_PACKAGE先不儲存金額
				if(itemEntity.getGamePackage().getLottery().getLotteryId()!=99701){
					//初始化彩种的预期金额（包括特殊彩种）
					gamePackageItem.setEvaluateAward(gameAssistProcess.getBonus(itemEntity.getGamePackage().getLottery().getLotteryId(), 
																				itemEntity.getGameBetType().getBetTypeCode(), 
																				itemEntity.getGamePackage().getUser().getId(), 
																				Long.valueOf(gamePackageItem.getMoneyMode()),
																				Long.valueOf(gamePackageItem.getMultiple()),
																				gamePackageItem.getRetAward()));
				}
				gamePackageItem.setMutiAward(0);
			} else {
				Map<String,Long> theoryBonusAssistMap=new HashMap<String,Long>();
				if(com.winterframework.firefrog.game.dao.vo.GamePackageItem.AwardMode.HIGH.getValue()==gamePackageItem.getAwardMode().intValue()){
					GameBettypeStatus betTypeStatus=gameBetTypeStatusService.getByLotteryIdAndBetTypeCode(itemEntity.getGamePackage().getLottery().getLotteryId(), gamePackageItem.getBetTypeCode());
					List<Long> idList=new ArrayList<Long>();
					idList.add(betTypeStatus.getId());
					List<GameBettypeAssist> bettypeAssistList=gameBettypeAssistDao.getBettypeAssistListByTypeIds(idList);
					
					for(GameBettypeAssist bettypeAssist:bettypeAssistList){
						theoryBonusAssistMap.put(gamePackageItem.getBetTypeCode()+"_"+bettypeAssist.getMethodType(), bettypeAssist.getTheoryBonus());
					}
					for(ItemAssist itemAssist:itemList){
						itemAssist.setEvaluatAward(itemAssist.getEvaluatAward()+theoryBonusAssistMap.get(itemAssist.getBetTypeCode())*gamePackageItem.getRetPoint()/10000);
					}
				}
				gamePackageItem.setMutiAward(1);
			}
			
			if (itemEntity.getFileMode().getValue() == fileMode.file.getValue()) {
				gamePackageItem.setBetDetail(fileUtil.string2File(gamePackageItem.getBetDetail(), gamePackage.getPackageCode() + i++,
						new Date()));
			} else {
				gamePackageItem.setBetDetail(gamePackageItem.getBetDetail());
			}
			gamePackageItem.setDiamondAmount(0l);
			insert(gamePackageItem);
			itemEntity.setId(gamePackageItem.getId());
			//插入辅助信息
			saveGamePackageAssist(itemList, Long.valueOf(gamePackageItem.getMoneyMode()),
					Long.valueOf(gamePackageItem.getMultiple()), gamePackageItem.getId());
		}
	}

	@Override
	public List<com.winterframework.firefrog.game.dao.vo.GamePackageItem> getPackageItemListByPackageId(Long packageId) {
		return this.sqlSessionTemplate.selectList("getPackageItemListByPackageId", packageId);
	}
	
	@Override
	public void deleteByPackageId(Long packageId) {
		this.sqlSessionTemplate.delete("deleteByPackageId", packageId);
	}

	/** 
	* @Title: saveGameSlipAssist 
	* @Description: 保存gamePackage的辅助信息
	* @param assistList
	* @param moneyMode 金钱模式 
	* @param multiple 倍数
	* @param slipId 
	*/
	private void saveGamePackageAssist(List<ItemAssist> assistList, Long moneyMode, Long multiple, Long slipId) {
		if (assistList != null && !assistList.isEmpty()) {
			List<PackageItemAssist> list = new ArrayList<PackageItemAssist>();
			for (ItemAssist itemAssist : assistList) {
				PackageItemAssist assist = new PackageItemAssist(itemAssist);
				//处理圆角模式以及倍数
				Long processMoney = gameAssistProcess.getBonus(assist.getEvaluatAward(), Long.valueOf(moneyMode),
						Long.valueOf(multiple));
				assist.setEvaluatAward(processMoney);
				Long processMoneyDown = gameAssistProcess.getBonus(assist.getEvaluatAwardDown(), Long.valueOf(moneyMode),
						Long.valueOf(multiple));
				assist.setEvaluatAwardDown(processMoneyDown);
				assist.setItemId(slipId);
				list.add(assist);
			}
			packageItemAssistDaoImpl.savePackageAssistItem(list);
		}
	}
}
