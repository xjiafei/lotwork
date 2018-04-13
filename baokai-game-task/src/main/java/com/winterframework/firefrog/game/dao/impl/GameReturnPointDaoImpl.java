package com.winterframework.firefrog.game.dao.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.winterframework.firefrog.game.dao.IGameAwardDao;
import com.winterframework.firefrog.game.dao.IGameAwardGroupDao;
import com.winterframework.firefrog.game.dao.IGameOrderDao;
import com.winterframework.firefrog.game.dao.IGameReturnBetTypePointDao;
import com.winterframework.firefrog.game.dao.IGameReturnPointDao;
import com.winterframework.firefrog.game.dao.IGameUserAwardDao;
import com.winterframework.firefrog.game.dao.IGameUserAwardGroupDao;
import com.winterframework.firefrog.game.dao.vo.GameAwardGroup;
import com.winterframework.firefrog.game.dao.vo.GameOrder;
import com.winterframework.firefrog.game.dao.vo.GamePackageItem;
import com.winterframework.firefrog.game.dao.vo.GameRetBetTypePoint;
import com.winterframework.firefrog.game.dao.vo.GameRetPoint;
import com.winterframework.firefrog.game.dao.vo.GameSlip;
import com.winterframework.firefrog.game.dao.vo.GameUserAwardGroup;
import com.winterframework.firefrog.game.entity.GameBetType;
import com.winterframework.firefrog.game.entity.UserAwardGroupEntity;
import com.winterframework.firefrog.game.enums.GameAwardBetType;
import com.winterframework.firefrog.game.service.IGameUserAwardGroupService;
import com.winterframework.firefrog.game.service.assist.award.SpecialLotteryGameAwardFactory;
import com.winterframework.firefrog.game.web.util.GameAwardNameUtil;
import com.winterframework.firefrog.user.dao.IUserCustomerDao;
import com.winterframework.firefrog.user.entity.User;
import com.winterframework.orm.dal.ibatis3.BaseIbatis3Dao;

@Repository("gameReturnPointDaoImpl")
public class GameReturnPointDaoImpl extends BaseIbatis3Dao<GameRetPoint> implements IGameReturnPointDao {

	@Resource(name = "gameUserAwardGroupDaoImpl")
	private IGameUserAwardGroupDao gameUserAwardGroupDao;
	@Resource(name = "gameAwardGroupDaoImpl")
	private IGameAwardGroupDao gameAwardGroupDao;
	@Resource(name = "userCustomerDaoImpl")
	private IUserCustomerDao userCustomerDao;

	@Resource(name = "gameUserAwardDaoImpl")
	private IGameUserAwardDao gameAwardDao;

	@Resource(name = "gameOrderDaoImpl")
	private IGameOrderDao gameOrderDao;
	@Resource(name = "gameAwardDaoImpl")
	private IGameAwardDao gameAwardDaoImpl;
	
	@Resource(name = "gameReturnBetTypePointDaoImpl")
	private IGameReturnBetTypePointDao gameReturnBetTypePointDao;
	
	@Resource(name = "specialLotteryGameAwardFactory")
	private SpecialLotteryGameAwardFactory specialLotteryGameAwardFactory;
	@Resource(name = "gameUserAwardGroupServiceImpl")
	private IGameUserAwardGroupService gameUserAwardGroupService;

	@Override
	public void saveUserReturnPoint(GameSlip gameSlip, Long orderId) throws Exception {

		User user = userCustomerDao.queryUserById(gameSlip.getUserid());

		String userChain = user.getUserProfile().getUserChain();
		String retPointChain = "/";
		//为用户层级链代理返点
		String[] accounts = userChain.replaceFirst("/", "").split("/");
		for (int i = 0; i < accounts.length; i++) {
			User u = userCustomerDao.queryUserByName(accounts[i]);
			Long retpoint = getUserPoint(gameSlip, u);
			log.info("计算得用户【" + accounts[i] + "】返点为" + retpoint);
			retPointChain += retpoint + "/";

		}
		GameRetPoint gameRetPoint = new GameRetPoint();
		gameRetPoint.setRetUserChain(userChain);
		gameRetPoint.setIssueCode(gameSlip.getIssueCode());
		gameRetPoint.setCreateTime(new Date());
		gameRetPoint.setGameOrderId(orderId);
		gameRetPoint.setRetPointChain(retPointChain);
		gameRetPoint.setStatus(GameRetPoint.STATUS_FOR_DEAL);
		insert(gameRetPoint);
	}

	private Long getUserPoint(GameSlip gameSlip, User u) {
		//获取返点信息
		UserAwardGroupEntity guage = gameUserAwardGroupDao.selectUserAwardGroup(u.getId(), gameSlip.getLotteryid());
		try {
			//返回返点
			Long rtn=gameAwardDao.getUserAward(guage.getLotteryId(), gameSlip.getGameGroupCode(), gameSlip.getGameSetCode(),
					gameSlip.getBetMethodCode().longValue(), u.getId());
			if(rtn!=null) return rtn.longValue();
		} catch (Exception e) {
			e.printStackTrace();
		}
		//判断是否是三星一码不定位
		Long retpoint = 0l;
		if (u == null || u.getUserLevel() == -1) {//测试数据容错处理，非代理时 返点设为0
			return 0l;
		}
		//获取返点值，分三星一码不定位和其他返点
		if (guage != null) {
			String[] betTypes = gameSlip.getBetTypeCode().split("_");
			if (GameAwardNameUtil.isSXBDW(Integer.parseInt(betTypes[0]), Integer.parseInt(betTypes[1]),
					Integer.parseInt(betTypes[2]))) {
				retpoint = guage.getThreeoneRet();
			} else {
				retpoint = guage.getDirectRet();
			}
		}
		return retpoint;
	}

	@Override
	public List<GameRetPoint> getGameRetPointByStatus(Integer status) {
		return this.sqlSessionTemplate.selectList("getGameRetPointByStatus", status);
	}

	@Override
	public List<GameRetPoint> getGameRetPointByIssueCode(Long issueCode) {
		return this.sqlSessionTemplate.selectList("getGameRetPointByIssueCode", issueCode);
	}

	@Override
	public GameRetPoint getGameRetPointByGameOrderId(Long gameOrderId) {
		return this.sqlSessionTemplate.selectOne("getGameRetPointByGameOrderId", gameOrderId);
	}

	@Override
	public Long getRetPointTotalAmountByOrderId(Long id) throws Exception {
		return this.sqlSessionTemplate.selectOne("getRetPointTotalAmountByOrderId", id);
	}

	@Override
	public GameRetPoint getRetPointByOrderId(Long id) {
		return this.sqlSessionTemplate.selectOne("getRetPointByOrderId", id);
	}

	@Override
	public List<GameRetPoint> getRetPointByLotteryInfo(Long lotteryId, Long issueCode, Date saleTime) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("lotteryId", lotteryId);
		paramMap.put("issueCode", issueCode);
		paramMap.put("saleTime", saleTime);
		return this.sqlSessionTemplate.selectList("getRetPointByLotteryInfo", paramMap);
	}

	@Override
	public void updateGameRetunPointByLotteryInfo(Long lotteryId, Long issueCode, Date saleTime) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("lotteryId", lotteryId);
		paramMap.put("issueCode", issueCode);
		paramMap.put("saleTime", saleTime);
		this.sqlSessionTemplate.update("updateGameRetunPointByLotteryInfo", paramMap);
	}

	/**
	 * 通过orderId更新状态
	 */
	public int updateStatus(List<Long> orderIds, Long status) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("status", status);
		map.put("list", orderIds);
		return this.sqlSessionTemplate.update("updateRetStatus", map);
	}

	/**
	 * 初始化用户链
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
	 * 获取非总代返点
	 * @param userIds
	 * @param lotteryId
	 * @param sysAwardGroupId
	 * @return
	 */
	private List<GameUserAwardGroup> getUserAwardGroupList(List<Long> userIds, Long lotteryId, Long sysAwardGroupId) {
		//获取返点信息
		List<GameUserAwardGroup> guag = gameUserAwardGroupDao.getAwardByUserIdAndLryIdAndSysAwardId(userIds, lotteryId,
				sysAwardGroupId);
		return guag;
	}

	@Override
	public String getRetunPointChain(List<com.winterframework.firefrog.game.entity.GamePackageItem> gamePackageItems,
			String userChain) throws Exception {
		//为用户层级链代理返点
		String[] accounts = userChain.replaceFirst("/", "").split("/");
		List<Long> userIdList = new ArrayList<Long>();
		String retUserChain = initUserChain(accounts, userIdList);
		if (userIdList == null || userIdList.isEmpty()) {
			return retUserChain + "/";
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
			GameAwardBetType awardBetType=gameUserAwardGroupService.getGameAwardBetType(gameByteType);
			if (userAwardGroupIds != null && !userAwardGroupIds.isEmpty()) {
				retpoint = getSubUserPoint(sysAwardGroupId, userIdListExpZongDai, lotteryId, awardBetType,gameByteType.getBetTypeCode());
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
			}else if (awardBetType != null && awardBetType.getValue()==GameAwardBetType.SB_THREE_ONE.getValue()) {
				zongDaiMap.put("RETVALUE", new BigDecimal(sysAward.getSbThreeoneRet()));
			}else if (awardBetType != null && awardBetType.getValue()==GameAwardBetType.LHC_FLATCODE.getValue()) {
				zongDaiMap.put("RETVALUE", new BigDecimal(sysAward.getLhcFlatcode()));
			}else if (awardBetType != null && awardBetType.getValue()==GameAwardBetType.LHC_HALFWAVE.getValue()) {
				zongDaiMap.put("RETVALUE", new BigDecimal(sysAward.getLhcHalfwave()));
			}else if (awardBetType != null && awardBetType.getValue()==GameAwardBetType.LHC_ONEYEAR.getValue()) {
				zongDaiMap.put("RETVALUE", new BigDecimal(sysAward.getLhcOneyear()));
			}else if (awardBetType != null && awardBetType.getValue()==GameAwardBetType.LHC_NOTIN.getValue()) {
				zongDaiMap.put("RETVALUE", new BigDecimal(sysAward.getLhcNotin()));
			}else if (awardBetType != null && awardBetType.getValue()==GameAwardBetType.LHC_CONTINUEIN23.getValue()) {
				zongDaiMap.put("RETVALUE", new BigDecimal(sysAward.getLhcContinuein23()));
			}else if (awardBetType != null && awardBetType.getValue()==GameAwardBetType.LHC_CONTINUEIN4.getValue()) {
				zongDaiMap.put("RETVALUE", new BigDecimal(sysAward.getLhcContinuein4()));
			}else if (awardBetType != null && awardBetType.getValue()==GameAwardBetType.LHC_CONTINUEIN5.getValue()) {
				zongDaiMap.put("RETVALUE", new BigDecimal(sysAward.getLhcContinuein5()));
			}else if (awardBetType != null && awardBetType.getValue()==GameAwardBetType.LHC_CONTINUENOTIN23.getValue()) {
				zongDaiMap.put("RETVALUE", new BigDecimal(sysAward.getLhcContinuenotin23()));
			}else if (awardBetType != null && awardBetType.getValue()==GameAwardBetType.LHC_CONTINUENOTIN4.getValue()) {
				zongDaiMap.put("RETVALUE", new BigDecimal(sysAward.getLhcContinuenotin4()));
			}else if (awardBetType != null && awardBetType.getValue()==GameAwardBetType.LHC_CONTINUENOTIN5.getValue()) {
				zongDaiMap.put("RETVALUE", new BigDecimal(sysAward.getLhcContinuenotin5()));
			}else if (awardBetType != null && awardBetType.getValue()==GameAwardBetType.LHC_CONTINUECODE.getValue()) {
				zongDaiMap.put("RETVALUE", new BigDecimal(sysAward.getLhcContinuecode()));
			}else {
				zongDaiMap.put("RETVALUE", new BigDecimal(sysAward.getDirectRet()));
			}
			boolean isHighAwardMode=GamePackageItem.AwardMode.HIGH.getValue()==gamePackageItems.get(i).getAwardMode().intValue();
			retpoint.add(zongDaiMap);
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
				}else if (awardBetType != null && awardBetType.getValue()==GameAwardBetType.SB_THREE_ONE.getValue()) {
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
	}

	@Override
	public String saveGameOrderUserReturnPoint(List<com.winterframework.firefrog.game.entity.GameSlip> slipList,
			Long orderid) throws Exception {

		GameOrder gameOrder = gameOrderDao.getById(orderid);
		//按订单保存用户层级链的返点信息
		User user = userCustomerDao.queryUserById(gameOrder.getUserid());
		GameRetPoint gameRetPoint = new GameRetPoint();
		String userChain = user.getUserProfile().getUserChain();
		String chain = getUserChainAndRetunPointChain(slipList, userChain);
		String[] chains = chain.split("/");
		gameRetPoint.setIssueCode(gameOrder.getIssueCode());
		gameRetPoint.setCreateTime(new Date());
		gameRetPoint.setGameOrderId(orderid);
		gameRetPoint.setRetPointChain(chains[1]);
		gameRetPoint.setRetUserChain(chains[0]);
		gameRetPoint.setStatus(GameRetPoint.STATUS_FOR_DEAL);
		insert(gameRetPoint);
		return chain;
	}

	/**
	 * 返回用户链和对应的返点链   返点计算说明： 总代的返点在gameAwardGroup表中   根据当前用户的奖金组信息 拿到奖金组的groupChain<br>
	 * 例子：根据 groupChain(奖金组id链) 获取返点链为20,10,5,2 则分别获取的返点为10 ,5,3,2 即 自己的所得返点=自己的总返点-自己直接下级的返点
	 * @param gameSlipList
	 * @param userChain
	 * @return
	 * @throws Exception
	 */
	public String getUserChainAndRetunPointChain(List<com.winterframework.firefrog.game.entity.GameSlip> gameSlipList,
			String userChain) throws Exception {
		//为用户层级链代理返点
		String[] accounts = userChain.replaceFirst("/", "").split("/");
		List<Long> userIdList = new ArrayList<Long>();
		String retUserChain = initUserChain(accounts, userIdList);
		if (userIdList == null || userIdList.isEmpty()) {
			return retUserChain + "/";
		}
		Long lotteryId = gameSlipList.get(0).getGameOrder().getLottery().getLotteryId();
		Long currentUserId = gameSlipList.get(0).getGameOrder().getGamePackage().getUser().getId();
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
			}
			//ADD BY David 16.04.26 #7332 4.0前台代理报表查询 新增查询功能 玩法 分類 : 計算單一注單返點 (BY USER) End
			
			GameAwardBetType awardBetType=gameUserAwardGroupService.getGameAwardBetType(gameByteType);
			//猜1个号不給返點
			if( (lotteryId.equals(99601L) || lotteryId.equals(99602L) || lotteryId.equals(99603L)) 
					&& (gameByteType.getGameGroupCode().equals(42) && gameByteType.getGameSetCode().equals(36) && gameByteType.getBetMethodCode().equals(78) )){
				awardBetType = GameAwardBetType.SB_THREE_ONE;
				log.info("進入骰寶類猜一個號, 轉換 awardBetType to GameAwardBetType.THREE_ONE");
			}
			if (userAwardGroupIds != null && !userAwardGroupIds.isEmpty()) {
				retpoint = getSubUserPoint(sysAwardGroupId, userIdListExpZongDai, lotteryId, awardBetType,gameByteType.getBetTypeCode());
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
			}else if (awardBetType != null && awardBetType.getValue()==GameAwardBetType.SB_THREE_ONE.getValue()) {
				zongDaiMap.put("RETVALUE", new BigDecimal(sysAward.getSbThreeoneRet()));
			}else if (awardBetType != null && awardBetType.getValue()==GameAwardBetType.LHC_FLATCODE.getValue()) {
				zongDaiMap.put("RETVALUE", new BigDecimal(sysAward.getLhcFlatcode()));
			}else if (awardBetType != null && awardBetType.getValue()==GameAwardBetType.LHC_HALFWAVE.getValue()) {
				zongDaiMap.put("RETVALUE", new BigDecimal(sysAward.getLhcHalfwave()));
			}else if (awardBetType != null && awardBetType.getValue()==GameAwardBetType.LHC_ONEYEAR.getValue()) {
				zongDaiMap.put("RETVALUE", new BigDecimal(sysAward.getLhcOneyear()));
			}else if (awardBetType != null && awardBetType.getValue()==GameAwardBetType.LHC_NOTIN.getValue()) {
				zongDaiMap.put("RETVALUE", new BigDecimal(sysAward.getLhcNotin()));
			}else if (awardBetType != null && awardBetType.getValue()==GameAwardBetType.LHC_CONTINUEIN23.getValue()) {
				zongDaiMap.put("RETVALUE", new BigDecimal(sysAward.getLhcContinuein23()));
			}else if (awardBetType != null && awardBetType.getValue()==GameAwardBetType.LHC_CONTINUEIN4.getValue()) {
				zongDaiMap.put("RETVALUE", new BigDecimal(sysAward.getLhcContinuein4()));
			}else if (awardBetType != null && awardBetType.getValue()==GameAwardBetType.LHC_CONTINUEIN5.getValue()) {
				zongDaiMap.put("RETVALUE", new BigDecimal(sysAward.getLhcContinuein5()));
			}else if (awardBetType != null && awardBetType.getValue()==GameAwardBetType.LHC_CONTINUENOTIN23.getValue()) {
				zongDaiMap.put("RETVALUE", new BigDecimal(sysAward.getLhcContinuenotin23()));
			}else if (awardBetType != null && awardBetType.getValue()==GameAwardBetType.LHC_CONTINUENOTIN4.getValue()) {
				zongDaiMap.put("RETVALUE", new BigDecimal(sysAward.getLhcContinuenotin4()));
			}else if (awardBetType != null && awardBetType.getValue()==GameAwardBetType.LHC_CONTINUENOTIN5.getValue()) {
				zongDaiMap.put("RETVALUE", new BigDecimal(sysAward.getLhcContinuenotin5()));
			}else if (awardBetType != null && awardBetType.getValue()==GameAwardBetType.LHC_CONTINUECODE.getValue()) {
				zongDaiMap.put("RETVALUE", new BigDecimal(sysAward.getLhcContinuecode()));
			}else {
				zongDaiMap.put("RETVALUE", new BigDecimal(sysAward.getDirectRet()));
			}			
			boolean isHighAwardMode=GamePackageItem.AwardMode.HIGH.getValue()==gameSlipList.get(i).getAwardMode().intValue();
			retpoint.add(zongDaiMap);
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
								allPoints[i][j] = Long
										.valueOf(realReturnVal * gameSlipList.get(i).getTotalAmount() / 10000);
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
				}
				//ADD BY David 16.04.26 #7332 4.0前台代理报表查询 新增查询功能 玩法 分類 : 計算單一注單返點 (BY USER) End
				
				point += allPoints[j][i];
			}

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

	/**
	 * 根据currentUserId获取当前代理的返点值
	 * @param retpoint
	 * @param currentReturnVal
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
}
