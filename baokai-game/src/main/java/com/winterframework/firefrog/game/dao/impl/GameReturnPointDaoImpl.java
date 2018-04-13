package com.winterframework.firefrog.game.dao.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.winterframework.firefrog.game.dao.IGameAwardDao;
import com.winterframework.firefrog.game.dao.IGameAwardGroupDao;
import com.winterframework.firefrog.game.dao.IGameOrderDao;
import com.winterframework.firefrog.game.dao.IGameReturnBetTypePointDao;
import com.winterframework.firefrog.game.dao.IGameReturnPointDao;
import com.winterframework.firefrog.game.dao.IGameUserAwardGroupDao;
import com.winterframework.firefrog.game.dao.vo.GameAwardGroup;
import com.winterframework.firefrog.game.dao.vo.GameOrder;
import com.winterframework.firefrog.game.dao.vo.GamePackageItem;
import com.winterframework.firefrog.game.dao.vo.GameRetBetTypePoint;
import com.winterframework.firefrog.game.dao.vo.GameRetPoint;
import com.winterframework.firefrog.game.dao.vo.GameUserAwardGroup;
import com.winterframework.firefrog.game.entity.GameBetType;
import com.winterframework.firefrog.game.entity.GameSlip;
import com.winterframework.firefrog.game.entity.UserAwardGroupEntity;
import com.winterframework.firefrog.game.enums.GameAwardBetType;
import com.winterframework.firefrog.game.service.IGameUserAwardGroupService;
import com.winterframework.firefrog.game.util.LHCUtil;
import com.winterframework.firefrog.user.dao.IUserCustomerDao;
import com.winterframework.firefrog.user.entity.User;
import com.winterframework.modules.spring.exetend.PropertyConfig;
import com.winterframework.orm.dal.ibatis3.BaseIbatis3Dao;

@Repository("gameReturnPointDaoImpl")
public class GameReturnPointDaoImpl extends BaseIbatis3Dao<GameRetPoint> implements IGameReturnPointDao {

	private static final Logger log = LoggerFactory.getLogger(GameReturnPointDaoImpl.class);
	@Resource(name = "gameUserAwardGroupDaoImpl")
	private IGameUserAwardGroupDao gameUserAwardGroupDao;

	@Resource(name = "gameAwardGroupDaoImpl")
	private IGameAwardGroupDao gameAwardGroupDao;

	@Resource(name = "userCustomerDaoImpl")
	private IUserCustomerDao userCustomerDao;

	@Resource(name = "gameOrderDaoImpl")
	private IGameOrderDao gameOrderDao;

	@Resource(name = "gameAwardDaoImpl")
	private IGameAwardDao gameAwardDaoImpl; 
	@Resource(name = "gameUserAwardGroupServiceImpl")
	private IGameUserAwardGroupService gameUserAwardGroupService;
	
	@Resource(name = "gameReturnBetTypePointDaoImpl")
	private IGameReturnBetTypePointDao gameReturnBetTypePointDao;
	

	
	@PropertyConfig("lhc.def.odds")
	private Integer defOdds;
	
	@Override
	public void updateGameRetunPointByOrderId(Long orderId) {
		this.sqlSessionTemplate.update("cacelReturnPoint", orderId);

	}

	@Override
	public String saveGameOrderUserReturnPoint(List<GameSlip> gameSlipList, Long orderId) throws Exception {
		GameOrder gameOrder = gameOrderDao.getById(orderId);
		//按订单保存用户层级链的返点信息
		User user = userCustomerDao.queryUserById(gameOrder.getUserid());
		GameRetPoint gameRetPoint = new GameRetPoint();
		String userChain = user.getUserProfile().getUserChain();
		String chain = getUserChainAndRetunPointChain(gameSlipList, userChain);
		String[] chains = chain.split("/");
		gameRetPoint.setIssueCode(gameOrder.getIssueCode());
		gameRetPoint.setCreateTime(new Date());
		gameRetPoint.setGameOrderId(orderId);
		gameRetPoint.setRetPointChain(chains[1]);
		gameRetPoint.setRetUserChain(chains[0]);
		gameRetPoint.setStatus(GameRetPoint.STATUS_FOR_DEAL);
		insert(gameRetPoint);
		return chain;

	}
	
	/**
	* Title: getRetunPointChain
	* Description:
	* @param gamePackageItems
	* @param userChain
	* @return
	* @throws Exception 
	*/
	@Override
	public String getRetunPointChain(List<com.winterframework.firefrog.game.entity.GamePackageItem> gamePackageItems,
			String userChain) throws Exception {
		//为用户层级链代理返点
		String[] accounts = userChain.replaceFirst("/", "").split("/");
		List<Long> userIdList = new ArrayList<Long>();
		String retUserChain = initUserChain(accounts, userIdList);
		if (userIdList == null || userIdList.isEmpty()) {
			return retUserChain;
		}
		Long lotteryId = gamePackageItems.get(0).getGamePackage().getLottery().getLotteryId();
		Long currentUserId = gamePackageItems.get(0).getGamePackage().getUser().getId();

		//获取当前用户的系统奖金组
		Long sysAwardGroupId = gameUserAwardGroupDao.selectUserAwardGroup(currentUserId, lotteryId).getSysGroupAward()
				.getId();
		Long[][] allPoints = new Long[gamePackageItems.size()][userIdList.size()];
		//获取各级用户资金组信息（除总代之外的其他代理，总代的从系统奖金信息中获取）
		List<Long> userIdListExpZongDai = new ArrayList<Long>();
		userIdListExpZongDai.addAll(userIdList.subList(1, userIdList.size()));
		List<GameUserAwardGroup> awardGroupList = new ArrayList<GameUserAwardGroup>();
		if (userIdListExpZongDai != null && !userIdListExpZongDai.isEmpty()) {
			awardGroupList = getUserAwardGroupList(userIdListExpZongDai, lotteryId, sysAwardGroupId);
		}

		//获取对应的用户资金组id
		List<Long> userAwardGroupIds = new ArrayList<Long>();
		for (GameUserAwardGroup tempAwardId : awardGroupList) {
			userAwardGroupIds.add(tempAwardId.getId());
		}

		for (int i = 0; i < gamePackageItems.size(); i++) {
			//获取指定玩法下，各个用户的返点值
			List<Map<String, BigDecimal>> retpoint = new ArrayList<Map<String, BigDecimal>>();
			GameBetType gameByteType = gamePackageItems.get(i).getGameBetType();
			/*LotteryPlayMethodKeyGenerator keyGenerator = new LotteryPlayMethodKeyGenerator(null,
					gameByteType.getGameGroupCode(), gameByteType.getGameSetCode(), gameByteType.getBetMethodCode(),
					"_$_"); */
			GameAwardBetType awardBetType=gameUserAwardGroupService.getGameAwardBetType(gameByteType);
			if (userAwardGroupIds != null && !userAwardGroupIds.isEmpty()) {
				if(lotteryId == 99701){
					retpoint = getSubUserPointByMultiple(sysAwardGroupId, userIdListExpZongDai, lotteryId, awardBetType,gameByteType.getBetTypeCode(),new Double(gamePackageItems.get(i).getOdds() * 10000).longValue());
				}else{
					retpoint = getSubUserPoint(sysAwardGroupId, userIdListExpZongDai, lotteryId, awardBetType,gameByteType.getBetTypeCode());
				}
				
			}
			//总代的返点从系统奖金组中获取
			Long zongdaiId = userIdList.get(0);
			GameAwardGroup sysAward = gameAwardGroupDao.getById(sysAwardGroupId);
			Map<String, BigDecimal> zongDaiMap = new HashMap<String, BigDecimal>();
			zongDaiMap.put("USERID", new BigDecimal(zongdaiId));
			 
			if (awardBetType != null && awardBetType.getValue()==GameAwardBetType.THREE_ONE.getValue()) {
				zongDaiMap.put("RETVALUE", new BigDecimal(sysAward.getThreeoneRet()));
			}else if (awardBetType != null && awardBetType.getValue()==GameAwardBetType.SUPER.getValue()) {
				zongDaiMap.put("RETVALUE", new BigDecimal(sysAward.getSuperRet()));
			}else if (awardBetType != null && awardBetType.getValue()==GameAwardBetType.LHC_YEAR.getValue()) {
				zongDaiMap.put("RETVALUE", new BigDecimal(sysAward.getLhcYear()));
			}else if (awardBetType != null && awardBetType.getValue()==GameAwardBetType.LHC_COLOR.getValue()) {
				zongDaiMap.put("RETVALUE", new BigDecimal(sysAward.getLhcColor()));
			}else if(awardBetType.equals(GameAwardBetType.LHC_FLATCODE)){
				zongDaiMap.put("RETVALUE", new BigDecimal(null == sysAward.getLhcFlatcode() ? 0L : sysAward.getLhcFlatcode()));
			}else if(awardBetType.equals(GameAwardBetType.LHC_HALFWAVE)){
				zongDaiMap.put("RETVALUE", new BigDecimal(null == sysAward.getLhcHalfwave() ? 0L : sysAward.getLhcHalfwave()));
			}else if(awardBetType.equals(GameAwardBetType.LHC_ONEYEAR)){
				zongDaiMap.put("RETVALUE", new BigDecimal(null == sysAward.getLhcOneyear() ? 0L : sysAward.getLhcOneyear()));
			}else if(awardBetType.equals(GameAwardBetType.LHC_NOTIN)){
				zongDaiMap.put("RETVALUE", new BigDecimal(null == sysAward.getLhcNotin() ? 0L : sysAward.getLhcNotin()));
			}else if(awardBetType.equals(GameAwardBetType.LHC_CONTINUEIN23)){
				zongDaiMap.put("RETVALUE", new BigDecimal(null == sysAward.getLhcContinuein23() ? 0L : sysAward.getLhcContinuein23()));
			}else if(awardBetType.equals(GameAwardBetType.LHC_CONTINUEIN4)){
				zongDaiMap.put("RETVALUE", new BigDecimal(null == sysAward.getLhcContinuein4() ? 0L : sysAward.getLhcContinuein4()));
			}else if(awardBetType.equals(GameAwardBetType.LHC_CONTINUEIN5)){
				zongDaiMap.put("RETVALUE", new BigDecimal(null == sysAward.getLhcContinuein5() ? 0L : sysAward.getLhcContinuein5()));
			}else if(awardBetType.equals(GameAwardBetType.LHC_CONTINUENOTIN23)){
				zongDaiMap.put("RETVALUE", new BigDecimal(null == sysAward.getLhcContinuenotin23() ? 0L : sysAward.getLhcContinuenotin23()));
			}else if(awardBetType.equals(GameAwardBetType.LHC_CONTINUENOTIN4)){
				zongDaiMap.put("RETVALUE", new BigDecimal(null == sysAward.getLhcContinuenotin4() ? 0L : sysAward.getLhcContinuenotin4()));
			}else if(awardBetType.equals(GameAwardBetType.LHC_CONTINUENOTIN5)){
				zongDaiMap.put("RETVALUE", new BigDecimal(null == sysAward.getLhcContinuenotin5() ? 0L : sysAward.getLhcContinuenotin5()));
			}else if(awardBetType.equals(GameAwardBetType.LHC_CONTINUECODE)){
				zongDaiMap.put("RETVALUE", new BigDecimal(null == sysAward.getLhcContinuecode() ? 0L : sysAward.getLhcContinuecode()));
			}
			else {
				if(lotteryId == 99701){
					Integer odds = defOdds;
					for (Long x = sysAward.getDirectRet() ; x >= 0; x -= 200) {
						if(odds == new Double(gamePackageItems.get(i).getOdds()).longValue()){
							zongDaiMap.put("RETVALUE", new BigDecimal(x));
							break;
						}
						odds++;
					}
				}else{
					zongDaiMap.put("RETVALUE", new BigDecimal(sysAward.getDirectRet()));
				}
			}
			retpoint.add(zongDaiMap); 
			boolean isHighAwardMode=GamePackageItem.AwardMode.HIGH.getValue()==gamePackageItems.get(i).getAwardMode().intValue();
			//为各个用户节点设置返回值
			for (int j = 0; j < userIdList.size(); j++) {
				boolean hasRetValue = false;
				if (retpoint != null && !retpoint.isEmpty()) { 
					//非（高奖金模式 且 当前投注用户) 才计算返点
					if(!(isHighAwardMode && currentUserId==userIdList.get(j).longValue())){
						//循环返点值列表，如果当前userId与列表Map中的USERID一样，该Map中对应的返点值则为该用户的返点值
						for (Map<String, BigDecimal> map : retpoint) {
							long userId = map.get("USERID").longValue();
							if (userId == userIdList.get(j).longValue()) {
								allPoints[i][j] = getNextUserReturnVal(retpoint, map.get("RETVALUE").longValue(), userId,
										userIdList);
								hasRetValue = true;
								break;
							}
						}
					}
					//如果没有从用户返点表中取到对应的返点值，则设置默认值
					if (!hasRetValue) {
						allPoints[i][j] = Long.valueOf(0L);
					}
				} else {
					allPoints[i][j] = Long.valueOf(0L);
				}
			}

			//返点列表处理
			String returnPointChain = "";
			Long points[] = allPoints[i];
			for (int index = 0; index < points.length; index++) {
				if (index != points.length - 1) {
					returnPointChain += (points[index]) + ",";
				} else {
					returnPointChain += points[index] + "";
				}
			}
			gamePackageItems.get(i).setRetPointChain(returnPointChain);
		}
		return retUserChain;
	}

	private List<Map<String, BigDecimal>> getSubUserPoint(Long sysGroupAwardId, List<Long> userIdsExceptTopAgent,
			Long lotteryid, GameAwardBetType awardBetType, String gameBetType) {
		//首先根据userId和gameBetType以及lotteryId查找老数据game_award_user表是否存在返点信息，假如不存在，则在gameUserAwardGroup表中获取返点信息
		
		List<Map<String, BigDecimal>> map=new ArrayList<Map<String,BigDecimal>>();
		for(Long userId:userIdsExceptTopAgent){
			Map<String, BigDecimal> mapPoint=gameUserAwardGroupDao.getUserAwardPoint(gameBetType, userId, lotteryid);
			if(mapPoint!=null){
				map.add(mapPoint);
			}else{
				if (awardBetType != null && awardBetType.getValue()==GameAwardBetType.THREE_ONE.getValue()) {
					mapPoint= gameUserAwardGroupDao.getUserAwardGroupThreeOnePoint(sysGroupAwardId, userId, lotteryid);
					map.add(mapPoint);
				}else if (awardBetType != null && awardBetType.getValue()==GameAwardBetType.SUPER.getValue()) {
					mapPoint= gameUserAwardGroupDao.getUserAwardGroupSuperPoint(sysGroupAwardId, userId, lotteryid);
					map.add(mapPoint);
				}else if (awardBetType != null && awardBetType.getValue()==GameAwardBetType.SB_THREE_ONE.getValue()){
					mapPoint= gameUserAwardGroupDao.getUserAwardGroupSbThreeOnePoint(sysGroupAwardId, userId, lotteryid);
					map.add(mapPoint);
				}else {
					//直选
					mapPoint=gameUserAwardGroupDao.getUserAwardGroupDirPoint(sysGroupAwardId, userId, lotteryid);
					map.add(mapPoint);
				}
				
			}
		}
		return map;
		//不定位
//		if (list != null && !list.isEmpty()) {
//			return gameUserAwardGroupDao.getUserAwardGroupThreeOnePoint(sysGroupAwardId, userIdsExceptTopAgent, lotteryid);
//		} else {
//			//直选
//			return gameUserAwardGroupDao.getUserAwardGroupDirPoint(sysGroupAwardId, userIdsExceptTopAgent, lotteryid);
//		}
	}
	
	private List<Map<String, BigDecimal>> getSubUserPointByMultiple(Long sysGroupAwardId, List<Long> userIdsExceptTopAgent,
			Long lotteryid, GameAwardBetType awardBetType, String gameBetType,Long odds) {
		//首先根据userId和gameBetType以及lotteryId查找老数据game_award_user表是否存在返点信息，假如不存在，则在gameUserAwardGroup表中获取返点信息
		
		List<Map<String, BigDecimal>> map=new ArrayList<Map<String,BigDecimal>>();
		for(Long userId:userIdsExceptTopAgent){
			Map<String, BigDecimal> mapPoint=gameUserAwardGroupDao.getUserAwardPoint(gameBetType, userId, lotteryid);
			if(mapPoint!=null){
				map.add(mapPoint);
			}else{
				List<Long> userIds = new ArrayList<Long>();
				userIds.add(userId);
				if (awardBetType != null && awardBetType.getValue()==GameAwardBetType.THREE_ONE.getValue()) {
					mapPoint= gameUserAwardGroupDao.getUserAwardGroupThreeOnePoint(sysGroupAwardId, userId, lotteryid);
					map.add(mapPoint);
				}else if (awardBetType != null && awardBetType.getValue()==GameAwardBetType.SUPER.getValue()) {
					mapPoint= gameUserAwardGroupDao.getUserAwardGroupSuperPoint(sysGroupAwardId, userId, lotteryid);
					map.add(mapPoint);
				}else if (awardBetType != null && awardBetType.getValue()==GameAwardBetType.LHC_YEAR.getValue()){
					mapPoint= gameUserAwardGroupDao.getUserAwardGroupYearPoint(sysGroupAwardId, userId, lotteryid);
					map.add(mapPoint);
				}else if (awardBetType != null && awardBetType.getValue()==GameAwardBetType.LHC_COLOR.getValue()){
					mapPoint= gameUserAwardGroupDao.getUserAwardGroupColorPoint(sysGroupAwardId, userId, lotteryid);
					map.add(mapPoint);
				}else if (LHCUtil.isLhcBetTypeCode(gameBetType)){
					//六合彩返點
					List<GameUserAwardGroup> gameUserAwardGroups = 
							gameUserAwardGroupDao.getAwardByUserIdAndLryIdAndSysAwardId(userIds, lotteryid, sysGroupAwardId);
					if(CollectionUtils.isNotEmpty(gameUserAwardGroups)){
						Long retValue = LHCUtil.findLhcBetTypeCode(gameBetType).getRetValue(gameUserAwardGroups.get(0));
						retValue = null == retValue ? 0L : retValue;
						mapPoint = new HashMap<String, BigDecimal>();
						mapPoint.put("USERID", BigDecimal.valueOf(userId));
						mapPoint.put("RETVALUE", BigDecimal.valueOf(retValue));
						map.add(mapPoint);
					} else {
						throw new IllegalArgumentException("取得六合彩返點發生錯誤");
					}
				}
				else {
					//直选
					mapPoint=gameUserAwardGroupDao.getUserAwardGroupDirPoint(sysGroupAwardId, userId, lotteryid);
					Long dfodds = Long.valueOf(defOdds);
					Long userOdds = odds/10000;
					Long userRet = mapPoint.get("RETVALUE").longValue();
					for (Long x = userRet ; x >= 0; x -= 200) {
						if(dfodds == userOdds){
							mapPoint.put("RETVALUE", new BigDecimal(x));
							break;
						}
						dfodds++;
					}
					map.add(mapPoint);
				}
				
			}
		}
		return map;
	}

	/** 
	* @Title: getUserChainAndRetunPointChain 
	* @Description: 返回用户链和对应的返点链   返点计算说明： 总代的返点在gameAwardGroup表中   根据当前用户的奖金组信息 拿到奖金组的groupChain  例子：根据
	*               groupChain(奖金组id链) 获取返点链为20,10,5,2 则分别获取的返点为10 ,5,3,2 即 自己的所得返点=自己的总返点-自己直接下级的返点
	* @param gameSlipList
	* @param userChain
	* @return
	* @throws Exception
	* 
	* REVISION HITORY
	* ----------------------------------------------------------------------------------------------------
	* 2016.04.26 EDIT BY David : #7332 4.0前台代理报表查询 新增查询功能 玩法 分類 : 計算單一注單返點 (BY USER)
	*  
	*/
	@Override
	public String getUserChainAndRetunPointChain(List<com.winterframework.firefrog.game.entity.GameSlip> gameSlipList,
			String userChain) throws Exception {
		Long lotteryId = gameSlipList.get(0).getGameOrder().getLottery().getLotteryId();
		Long currentUserId = gameSlipList.get(0).getGameOrder().getGamePackage().getUser().getId();
		//为用户层级链代理返点
		String[] accounts = userChain.replaceFirst("/", "").split("/");
		List<Long> userIdList = new ArrayList<Long>();
		String retUserChain = initUserChain(accounts, userIdList);
		if (userIdList == null || userIdList.isEmpty()) {
			return retUserChain + "/";
		}
		//获取当前用户的groupChain 
		UserAwardGroupEntity curUsrAwrdGroup = gameUserAwardGroupDao.selectUserAwardGroup(currentUserId, lotteryId);
		Long sysAwardGroupId = curUsrAwrdGroup.getSysGroupAward().getId();

		//获取各级用户资金组信息（除总代之外的其他代理，总代的从系统奖金信息中获取）
		List<Long> userIdListExpZongDai = new ArrayList<Long>();
		userIdListExpZongDai.addAll(userIdList.subList(1, userIdList.size()));
		List<GameUserAwardGroup> awardGroupList = new ArrayList<GameUserAwardGroup>();
		if (userIdListExpZongDai != null && !userIdListExpZongDai.isEmpty()) {
			awardGroupList = getUserAwardGroupList(userIdListExpZongDai, lotteryId, sysAwardGroupId);
		}
		Long[][] allPoints = new Long[gameSlipList.size()][accounts.length];
		
		//ADD BY David 16.04.26 #7332 4.0前台代理报表查询 新增查询功能 玩法 分類 : 計算所有注單返點加總BY USER(含上層鏈結) Start
		List<GameRetBetTypePoint> retBetTypePointList = new ArrayList<GameRetBetTypePoint>();
		//ADD BY David 16.04.26 #7332 4.0前台代理报表查询 新增查询功能 玩法 分類 : 計算所有注單返點加總BY USER(含上層鏈結) End

		//获取对应的用户资金组id
		List<Long> userAwardGroupIds = new ArrayList<Long>();
		for (GameUserAwardGroup tempAwardId : awardGroupList) {
			userAwardGroupIds.add(tempAwardId.getId());
		}

		for (int i = 0; i < gameSlipList.size(); i++) {
			//获取指定玩法下，各个用户的返点值
			List<Map<String, BigDecimal>> retpoint = new ArrayList<Map<String, BigDecimal>>();
			GameBetType gameByteType = gameSlipList.get(i).getGameBetType();
			
			//ADD BY David 16.04.26 #7332 4.0前台代理报表查询 新增查询功能 玩法 分類 : 計算單一注單返點 (BY USER) Start
			String orderCode = gameSlipList.get(i).getGameOrder().getOrderCode();
			if(orderCode != null){
				//追號方案建立時會查詢所有的返點資料，但此時無 orderCode package package_item 無法對應
				//僅針對產生訂單時的注單建立資料
				GameRetBetTypePoint retBetTypePoint = new GameRetBetTypePoint();
				retBetTypePoint.setPackage_id(gameSlipList.get(i).getGameOrder().getGamePackage().getPackageId());
				retBetTypePoint.setItem_id(gameSlipList.get(i).getGameOrder().getGamePackage().getItemList().get(i).getId());
				retBetTypePoint.setOrder_code(orderCode);
				retBetTypePoint.setIssuecode(gameSlipList.get(i).getGameOrder().getGameIssue().getIssueCode());
				retBetTypePoint.setCreateTime(gameSlipList.get(i).getCrateTime());
				retBetTypePoint.setBettype_ret_user_chain(retUserChain);
				retBetTypePointList.add(retBetTypePoint);
			} else {
				log.debug("Save BetTypePoint Error : OrderCode Is Null");
			}
			//ADD BY David 16.04.26 #7332 4.0前台代理报表查询 新增查询功能 玩法 分類 : 計算單一注單返點 (BY USER) End
			
			/*LotteryPlayMethodKeyGenerator keyGenerator = new LotteryPlayMethodKeyGenerator(null,
					gameByteType.getGameGroupCode(), gameByteType.getGameSetCode(), gameByteType.getBetMethodCode(),
					"_$_");
			List<ILotteryKeyFactor> list = specialLotteryGameAwardFactory.getObject(keyGenerator);*/
			GameAwardBetType awardBetType=gameUserAwardGroupService.getGameAwardBetType(gameByteType);
			//排除江苏骰宝、吉利骰宝(娱乐厅) 、吉利骰宝(至尊厅)
			//猜1个号改為不定位返點
			if( (lotteryId.equals(99601L) || lotteryId.equals(99602L) || lotteryId.equals(99603L)) 
					&& (gameByteType.getGameGroupCode().equals(42) && gameByteType.getGameSetCode().equals(36) && gameByteType.getBetMethodCode().equals(78) )){
				awardBetType = GameAwardBetType.SB_THREE_ONE;
				log.info("進入骰寶類猜一個號, 轉換 awardBetType to GameAwardBetType.THREE_ONE");
			}
			if (userAwardGroupIds != null && !userAwardGroupIds.isEmpty()) {
				if(lotteryId == 99701){
					retpoint = getSubUserPointByMultiple(sysAwardGroupId, userIdListExpZongDai, lotteryId, awardBetType,gameSlipList.get(i).getGameBetType().getBetTypeCode(),gameSlipList.get(i).getSingleWin());
				}else{
					retpoint = getSubUserPoint(sysAwardGroupId, userIdListExpZongDai, lotteryId, awardBetType,gameSlipList.get(i).getGameBetType().getBetTypeCode());
				}
			}
			//总代的返点从系统奖金组中获取
			Long zongdaiId = userIdList.get(0);
			GameAwardGroup sysAward = gameAwardGroupDao.getById(sysAwardGroupId);
			Map<String, BigDecimal> zongDaiMap = new HashMap<String, BigDecimal>();
			zongDaiMap.put("USERID", new BigDecimal(zongdaiId));
			if (awardBetType != null && awardBetType.getValue()==GameAwardBetType.THREE_ONE.getValue()) {
				zongDaiMap.put("RETVALUE", new BigDecimal(sysAward.getThreeoneRet()));
			}else if (awardBetType != null && awardBetType.getValue()==GameAwardBetType.SUPER.getValue()) {
				zongDaiMap.put("RETVALUE", new BigDecimal(sysAward.getSuperRet()));
			}else if (awardBetType != null && awardBetType.getValue()==GameAwardBetType.LHC_YEAR.getValue()) {
				zongDaiMap.put("RETVALUE", new BigDecimal(sysAward.getLhcYear()));
			}else if (awardBetType != null && awardBetType.getValue()==GameAwardBetType.LHC_COLOR.getValue()) {
				zongDaiMap.put("RETVALUE", new BigDecimal(sysAward.getLhcColor()));
			}else if(awardBetType != null && awardBetType.getValue()==GameAwardBetType.SB_THREE_ONE.getValue()){
				zongDaiMap.put("RETVALUE", new BigDecimal(sysAward.getSbThreeoneRet()));
			}else if(awardBetType.equals(GameAwardBetType.LHC_FLATCODE)){
				zongDaiMap.put("RETVALUE", new BigDecimal(null == sysAward.getLhcFlatcode() ? 0L : sysAward.getLhcFlatcode()));
			}else if(awardBetType.equals(GameAwardBetType.LHC_HALFWAVE)){
				zongDaiMap.put("RETVALUE", new BigDecimal(null == sysAward.getLhcHalfwave() ? 0L : sysAward.getLhcHalfwave()));
			}else if(awardBetType.equals(GameAwardBetType.LHC_ONEYEAR)){
				zongDaiMap.put("RETVALUE", new BigDecimal(null == sysAward.getLhcOneyear() ? 0L : sysAward.getLhcOneyear()));
			}else if(awardBetType.equals(GameAwardBetType.LHC_NOTIN)){
				zongDaiMap.put("RETVALUE", new BigDecimal(null == sysAward.getLhcNotin() ? 0L : sysAward.getLhcNotin()));
			}else if(awardBetType.equals(GameAwardBetType.LHC_CONTINUEIN23)){
				zongDaiMap.put("RETVALUE", new BigDecimal(null == sysAward.getLhcContinuein23() ? 0L : sysAward.getLhcContinuein23()));
			}else if(awardBetType.equals(GameAwardBetType.LHC_CONTINUEIN4)){
				zongDaiMap.put("RETVALUE", new BigDecimal(null == sysAward.getLhcContinuein4() ? 0L : sysAward.getLhcContinuein4()));
			}else if(awardBetType.equals(GameAwardBetType.LHC_CONTINUEIN5)){
				zongDaiMap.put("RETVALUE", new BigDecimal(null == sysAward.getLhcContinuein5() ? 0L : sysAward.getLhcContinuein5()));
			}else if(awardBetType.equals(GameAwardBetType.LHC_CONTINUENOTIN23)){
				zongDaiMap.put("RETVALUE", new BigDecimal(null == sysAward.getLhcContinuenotin23() ? 0L : sysAward.getLhcContinuenotin23()));
			}else if(awardBetType.equals(GameAwardBetType.LHC_CONTINUENOTIN4)){
				zongDaiMap.put("RETVALUE", new BigDecimal(null == sysAward.getLhcContinuenotin4() ? 0L : sysAward.getLhcContinuenotin4()));
			}else if(awardBetType.equals(GameAwardBetType.LHC_CONTINUENOTIN5)){
				zongDaiMap.put("RETVALUE", new BigDecimal(null == sysAward.getLhcContinuenotin5() ? 0L : sysAward.getLhcContinuenotin5()));
			}else if(awardBetType.equals(GameAwardBetType.LHC_CONTINUECODE)){
				zongDaiMap.put("RETVALUE", new BigDecimal(null == sysAward.getLhcContinuecode() ? 0L : sysAward.getLhcContinuecode()));
			}
			else {
				if(lotteryId == 99701){
					Integer odds = defOdds;
					for (Long x = sysAward.getDirectRet() ; x >= 0; x -= 200) {
						if(odds == gameSlipList.get(i).getSingleWin()/10000){
							zongDaiMap.put("RETVALUE", new BigDecimal(x));
							break;
						}
						odds++;
					}
				}else{
					zongDaiMap.put("RETVALUE", new BigDecimal(sysAward.getDirectRet()));
				}
			}
			retpoint.add(zongDaiMap);
			boolean isHighAwardMode=GamePackageItem.AwardMode.HIGH.getValue()==gameSlipList.get(i).getAwardMode().intValue();
			//为各个用户节点设置返回值
			for (int j = 0; j < userIdList.size(); j++) {
				if (retpoint != null && !retpoint.isEmpty()) {
					boolean hasRetValue = false; 
					//非（高奖金模式 且 当前投注用户) 才计算返点
					if(!(isHighAwardMode && currentUserId==userIdList.get(j).longValue())){
					//循环返点值列表，如果当前userId与列表Map中的USERID一样，该Map中对应的返点值则为该用户的返点值
					for (Map<String, BigDecimal> map : retpoint) {
						long userId = map.get("USERID").longValue();
						if (userId == userIdList.get(j).longValue()) {
							Long realReturnVal = getNextUserReturnVal(retpoint, map.get("RETVALUE").longValue(),
									userId, userIdList);
							if(lotteryId == 99112l){
								allPoints[i][j] = Long
									.valueOf(realReturnVal * (gameSlipList.get(i).getTotalAmount()+gameSlipList.get(i).getDiamondAmount()) / 10000);
							}else{
							allPoints[i][j] = Long
									.valueOf(realReturnVal * gameSlipList.get(i).getTotalAmount() / 10000);
							}
							hasRetValue = true;
							break;
						}
					}
					}
					//如果没有从用户返点表中取到对应的返点值，则设置默认值
					if (!hasRetValue) {
						allPoints[i][j] = Long.valueOf(0L);
					}
				} else {
					allPoints[i][j] = Long.valueOf(0L);
				}
			}
		}

		Long points[] = new Long[userIdList.size()];
		
		//debug log
		String infoLogStr = "ORDER CODE =" + gameSlipList.get(0).getGameOrder().getOrderCode() 
						 + " PACKAGE_ID = " + gameSlipList.get(0).getGameOrder().getGamePackage().getPackageId()
						 + " CONTANT => ";
		for (int i = 0; i < userIdList.size(); i++) {
			Long point = 0L;
			String infoLogPointStr = "";//debug log
			for (int j = 0; j < gameSlipList.size(); j++) { 
				//如果高奖金模式 当前用户不再计算返点
				if(currentUserId==userIdList.get(i) && GamePackageItem.AwardMode.HIGH.getValue()==gameSlipList.get(j).getAwardMode().intValue()){
					continue;
				}
				
				//ADD BY David 16.04.26 #7332 4.0前台代理报表查询 新增查询功能 玩法 分類 : 計算單一注單返點 (BY USER) Start
				if(retBetTypePointList.size() > 0){ //追號方案建立時會查詢所有的返點資料，但此時無 orderCode package package_item 無法對應
					GameRetBetTypePoint retBetTypePoint = retBetTypePointList.get(j);
					String betTypeRetPointChain = retBetTypePoint.getBettype_ret_point_chain();
					if(betTypeRetPointChain == null){
						betTypeRetPointChain = allPoints[j][i].toString();		
					}else {
						betTypeRetPointChain += "," + allPoints[j][i].toString();
					}
					infoLogPointStr +=  ( (infoLogPointStr.isEmpty())? "" : ",") + allPoints[j][i].toString();
					retBetTypePoint.setBettype_ret_point_chain(betTypeRetPointChain);
				} else {
					log.debug("Save BetTypePoint Error : No Record Found");
				}
				//ADD BY David 16.04.26 #7332 4.0前台代理报表查询 新增查询功能 玩法 分類 : 計算單一注單返點 (BY USER) End

				point += allPoints[j][i];
			}
			infoLogStr += ( (infoLogStr.isEmpty())? "" : " , ") + userIdList.get(i).toString() + " : " + infoLogPointStr ;
			points[i] = point;
		}
		
		//ADD BY David 16.04.26 #7332 4.0前台代理报表查询 新增查询功能 玩法 分類 : 計算單一注單返點 (BY USER) Start
		if(retBetTypePointList.size() > 0){ //追號方案建立時會查詢所有的返點資料，但此時無 orderCode package package_item 無法對應
			log.info(" getUserChainAndRetunPointChain(SLIP RET Point) = " + infoLogStr);		
			saveGameRetBetTypePoint(retBetTypePointList);
		}
		//ADD BY David 16.04.26 #7332 4.0前台代理报表查询 新增查询功能 玩法 分類 : 計算單一注單返點 (BY USER) End
		
		String returnPointChain = "";
		for (int i = 0; i < points.length; i++) {
			if (i != points.length - 1) {
				returnPointChain += (points[i]) + ",";
			} else {
				returnPointChain += points[i] + "";
			}
		}
		return retUserChain + "/" + returnPointChain;
	}
	
	/**
	 * 紀錄注單返點鏈
	 * @param retBetTypePointList
	 * @throws Exception 
	 * REVISION HITORY
	 * ----------------------------------------------------------------------------------------------------
	 * 2016.04.26 Create BY David : #7332 4.0前台代理报表查询 新增查询功能 玩法 分類 : 計算單一注單返點 (BY USER)
	 */
	public void saveGameRetBetTypePoint(List<GameRetBetTypePoint> retBetTypePointList) throws Exception {		
		for(GameRetBetTypePoint gameRetBetTypePoint : retBetTypePointList){
			log.info("saveGameRetBetTypePoint : ORDER_CODE = "+ gameRetBetTypePoint.getOrder_code() 
					+ " ISSUECODE = "+ gameRetBetTypePoint.getIssuecode()  +" ITEM_ID= " + gameRetBetTypePoint.getItem_id()
					+ " USER_CHAIN = " + gameRetBetTypePoint.getBettype_ret_user_chain() 
					+ " POINT_CHAIN = " + gameRetBetTypePoint.getBettype_ret_point_chain());
			gameReturnBetTypePointDao.saveGameRetBetTypePoint(gameRetBetTypePoint);
		}
	}
	
	@Override
	public Long getRetunPoint(com.winterframework.firefrog.game.entity.GameSlip gameSlipList,
			String userChain) throws Exception {
		Long rtnValue=0L;
		GameBetType gameByteType = gameSlipList.getGameBetType();
		Long lotteryId = gameSlipList.getGameOrder().getLottery().getLotteryId();
		Long currentUserId = gameSlipList.getGameOrder().getGamePackage().getUser().getId();
		GameAwardBetType awardBetType=gameUserAwardGroupService.getGameAwardBetType(gameByteType);
		UserAwardGroupEntity curUsrAwrdGroup = gameUserAwardGroupDao.selectUserAwardGroup(currentUserId, lotteryId);
		Long sysAwardGroupId = curUsrAwrdGroup.getSysGroupAward().getId();
		//总代的返点从系统奖金组中获取
		GameAwardGroup sysAward = gameAwardGroupDao.getById(sysAwardGroupId);
		if (awardBetType != null && awardBetType.getValue()==GameAwardBetType.THREE_ONE.getValue()) {
			rtnValue=sysAward.getThreeoneRet();
		}else if (awardBetType != null && awardBetType.getValue()==GameAwardBetType.SUPER.getValue()) {
			rtnValue=sysAward.getSuperRet();
		}else if(awardBetType != null && awardBetType.getValue()==GameAwardBetType.LHC_COLOR.getValue()){
			rtnValue=sysAward.getLhcColor();
		}else if(awardBetType != null && awardBetType.getValue()==GameAwardBetType.LHC_YEAR.getValue()){
			rtnValue=sysAward.getLhcYear();
		}else if(awardBetType.equals(GameAwardBetType.LHC_FLATCODE)){
			rtnValue= null == sysAward.getLhcFlatcode() ? 0L : sysAward.getLhcFlatcode();
		}else if(awardBetType.equals(GameAwardBetType.LHC_HALFWAVE)){
			rtnValue=null == sysAward.getLhcHalfwave() ? 0L : sysAward.getLhcHalfwave();
		}else if(awardBetType.equals(GameAwardBetType.LHC_ONEYEAR)){
			rtnValue=null == sysAward.getLhcOneyear() ? 0L : sysAward.getLhcOneyear();
		}else if(awardBetType.equals(GameAwardBetType.LHC_NOTIN)){
			rtnValue=null == sysAward.getLhcNotin() ? 0L : sysAward.getLhcNotin();
		}else if(awardBetType.equals(GameAwardBetType.LHC_CONTINUEIN23)){
			rtnValue=null == sysAward.getLhcContinuein23() ? 0L : sysAward.getLhcContinuein23();
		}else if(awardBetType.equals(GameAwardBetType.LHC_CONTINUEIN4)){
			rtnValue=null == sysAward.getLhcContinuein4() ? 0L : sysAward.getLhcContinuein4();
		}else if(awardBetType.equals(GameAwardBetType.LHC_CONTINUEIN5)){
			rtnValue=null == sysAward.getLhcContinuein5() ? 0L : sysAward.getLhcContinuein5();
		}else if(awardBetType.equals(GameAwardBetType.LHC_CONTINUENOTIN23)){
			rtnValue=null == sysAward.getLhcContinuenotin23() ? 0L : sysAward.getLhcContinuenotin23();
		}else if(awardBetType.equals(GameAwardBetType.LHC_CONTINUENOTIN4)){
			rtnValue=null == sysAward.getLhcContinuenotin4() ? 0L : sysAward.getLhcContinuenotin4();
		}else if(awardBetType.equals(GameAwardBetType.LHC_CONTINUENOTIN5)){
			rtnValue=null == sysAward.getLhcContinuenotin5() ? 0L : sysAward.getLhcContinuenotin5();
		}else if(awardBetType.equals(GameAwardBetType.LHC_CONTINUECODE)){
			rtnValue=null == sysAward.getLhcContinuecode() ? 0L : sysAward.getLhcContinuecode();
		}else if(lotteryId == 99701){
			rtnValue = LHCUtil.getZhixuanRet(gameSlipList.getSingleWin()/10000, sysAward.getDirectRet());
		}else if(awardBetType != null && awardBetType.getValue()==GameAwardBetType.SB_THREE_ONE.getValue()){
			rtnValue=sysAward.getSbThreeoneRet();
		}
		else{
			rtnValue=sysAward.getDirectRet();				
		}
		return rtnValue;
	}

	/** 
	* @Title: getTopUserPoint 
	* @Description: 获取非总代返点
	* @param gameOrderDetail
	* @param gameAwardGroupId
	* @return
	*/
	private List<GameUserAwardGroup> getUserAwardGroupList(List<Long> userIds, Long lotteryId, Long sysAwardGroupId) {
		//获取返点信息
		List<GameUserAwardGroup> guag = gameUserAwardGroupDao.getAwardByUserIdAndLryIdAndSysAwardId(userIds, lotteryId,
				sysAwardGroupId);
		return guag;
	}

	@Override
	public GameRetPoint getRetPointByOrderId(Long id) {
		return this.sqlSessionTemplate.selectOne("retPointByOrderID", id);
	}
	public GameRetPoint getRetPointByOrderIdWithOutStatus(Long id) {
		return this.sqlSessionTemplate.selectOne("retPointByOrderIDWithOutStatus", id);
	}
	/** 
	* @Title: initUserChain 
	* @Description: 初始化用户链
	* @param accounts
	* @param userIdList
	* @return
	* @throws Exception
	*/
	private String initUserChain(String[] accounts, List<Long> userIdList) throws Exception {
		//为用户层级链代理返点
		String retUserChain = "";
		for (int j = 0; j < accounts.length; j++) {//遍历用户
			Long tempId = userCustomerDao.queryUserByName(accounts[j]).getId();
			if (j != accounts.length - 1) {//拼接用户链资金链的格式为aa,bb,cc
				retUserChain += tempId + ",";
			} else {
				retUserChain += tempId;
			}
			userIdList.add(tempId);
		}
		return retUserChain;
	}

	/** 
	* @Title: initUserChain 
	* @Description: 初始化用户链
	* @param accounts
	* @param userIdList
	* @return
	* @throws Exception
	*/
	@Override
	public String getUserChain(String userChain) throws Exception {
		//为用户层级链代理返点
		String[] accounts = userChain.replaceFirst("/", "").split("/");
		String retUserChain = "";
		for (int j = 0; j < accounts.length; j++) {//遍历用户
			Long tempId = userCustomerDao.queryUserByName(accounts[j]).getId();
			if (j != accounts.length - 1) {//拼接用户链资金链的格式为aa,bb,cc
				retUserChain += tempId + ",";
			} else {
				retUserChain += tempId;
			}
		}
		return retUserChain;
	}

	/** 
	* @Title: getNextUserReturnVal 
	* @Description: 根据currentUserId获取当前代理的返点值
	* @param map
	* @param currentUserId
	* @param userIdList
	* @return
	*/
	private Long getNextUserReturnVal(List<Map<String, BigDecimal>> retpoint, Long currentReturnVal,
			long currentUserId, List<Long> userIdList) {
		//当前用户的索引
		int currentIndex = userIdList.indexOf(currentUserId);
		//存在下级代理
		if (userIdList.size() > currentIndex + 1) {
			Long nexUserId = userIdList.get(currentIndex + 1);
			for (Map<String, BigDecimal> map : retpoint) {
				long userId = map.get("USERID").longValue();
				if (userId == nexUserId) {
					return currentReturnVal - Long.valueOf(map.get("RETVALUE").longValue());
				}
			}
		}
		return currentReturnVal;
	}
	
	@Override
	public GameRetPoint getGameRetPointByGameOrderId(Long gameOrderId) {
		return this.sqlSessionTemplate.selectOne("getGameRetPointByGameOrderId", gameOrderId);
	}
}
