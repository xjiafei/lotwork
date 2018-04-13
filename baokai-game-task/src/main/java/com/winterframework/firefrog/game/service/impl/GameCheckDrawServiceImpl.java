package com.winterframework.firefrog.game.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.winterframework.firefrog.common.util.DateUtils;
import com.winterframework.firefrog.common.util.GameContext;
import com.winterframework.firefrog.common.util.ProcessResult;
import com.winterframework.firefrog.common.wincaculate.ILotterySlipNumCaculatorContext;
import com.winterframework.firefrog.common.wincaculate.ILotteryWinCaculatorFactory;
import com.winterframework.firefrog.common.wincaculate.ILotteryWinSlipNumCaculator;
import com.winterframework.firefrog.common.wincaculate.IWinResultBean;
import com.winterframework.firefrog.game.dao.IGameControlEventDao;
import com.winterframework.firefrog.game.dao.IGameIssueDao;
import com.winterframework.firefrog.game.dao.IGameOrderDao;
import com.winterframework.firefrog.game.dao.IGameOrderWinDao;
import com.winterframework.firefrog.game.dao.IGamePackageItemDao;
import com.winterframework.firefrog.game.dao.IGamePlanDao;
import com.winterframework.firefrog.game.dao.IGameSlipDao;
import com.winterframework.firefrog.game.dao.vo.EnumTypeConverts;
import com.winterframework.firefrog.game.dao.vo.GameBonusAwardJsonBean;
import com.winterframework.firefrog.game.dao.vo.GameBonusJsonBean;
import com.winterframework.firefrog.game.dao.vo.GameBonusPoolJson;
import com.winterframework.firefrog.game.dao.vo.GameControlEvent;
import com.winterframework.firefrog.game.dao.vo.GameDiamondBettype;
import com.winterframework.firefrog.game.dao.vo.GameIssue;
import com.winterframework.firefrog.game.dao.vo.GameOrder;
import com.winterframework.firefrog.game.dao.vo.GameOrderWin;
import com.winterframework.firefrog.game.dao.vo.GamePackageItem;
import com.winterframework.firefrog.game.dao.vo.GamePlanDetail;
import com.winterframework.firefrog.game.dao.vo.GameSlip;
import com.winterframework.firefrog.game.entity.GameBetType;
import com.winterframework.firefrog.game.entity.GameIssueEntity;
import com.winterframework.firefrog.game.entity.GameIssuePeriodStatus;
import com.winterframework.firefrog.game.entity.GameOrder.OrderStatus;
import com.winterframework.firefrog.game.entity.GameSlipStatus;
import com.winterframework.firefrog.game.entity.VOConvert4Task;
import com.winterframework.firefrog.game.service.IExportFileService;
import com.winterframework.firefrog.game.service.IGameCheckDrawService;
import com.winterframework.firefrog.game.service.IGameDiamondBettypeService;
import com.winterframework.firefrog.game.service.IGameDrawResultService;
import com.winterframework.firefrog.game.service.IGameIssueService;
import com.winterframework.firefrog.game.service.IGameOrderSecurityService;
import com.winterframework.firefrog.game.service.IGameOrderService;
import com.winterframework.firefrog.game.service.IGameOrderWinService;
import com.winterframework.firefrog.game.service.IGamePlanService;
import com.winterframework.firefrog.game.service.bean.BigAwardCacheBean;
import com.winterframework.firefrog.game.service.gametrend.config.GameTrendEventParams;
import com.winterframework.firefrog.game.service.gametrend.config.GameTrendEventParams.TrendEventTypeEnum;
import com.winterframework.firefrog.game.service.utils.GameSlipUtilsEnum;
import com.winterframework.firefrog.game.service.utils.GameWinPropertyBean;
import com.winterframework.firefrog.game.service.wincaculate.amount.LotteryWinAmountCaculator;
import com.winterframework.firefrog.game.service.wincaculate.config.LotteryPlayMethodKeyGenerator;
import com.winterframework.firefrog.game.service.wincaculate.config.LotterySlipNumCaculatorContext;
import com.winterframework.firefrog.game.util.SuperPairUtil;
import com.winterframework.modules.spring.exetend.PropertyConfig;
import com.winterframework.modules.web.util.JsonMapper;

@Service("gameCheckDrawServiceImpl")
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class GameCheckDrawServiceImpl implements IGameCheckDrawService {
	private static final Logger log = LoggerFactory.getLogger(GameCheckDrawServiceImpl.class);

	@Resource(name = "gameSlipDaoImpl")
	private IGameSlipDao gameSlipDao;

	@Resource(name = "gamePlanDaoImpl")
	private IGamePlanDao gamePlanDao;

	@Resource(name = "gameOrderDaoImpl")
	IGameOrderDao  gameOrderDao ;
	
	@Resource(name = "gameOrderWinDaoImpl")
	private IGameOrderWinDao orderWinDao;

	@Resource(name = "exportFileServiceImpl")
	private IExportFileService exportFileService;

	@Resource(name = "gameDrawResultServiceImpl")
	private IGameDrawResultService drawResultService;

	@Resource(name = "gameOrderServiceImpl")
	private IGameOrderService gameOrderService;
	@Resource(name = "gameOrderWinServiceImpl")
	private IGameOrderWinService gameOrderWinService;
	@Resource(name = "gameIssueServiceImpl")
	private IGameIssueService gameIssueService;

	@Resource(name = "gameOrderSecurityServiceImpl")
	private IGameOrderSecurityService gameOrderSecurityService;

	@Resource(name = "gameControlEventDaoImpl")
	private IGameControlEventDao gameControlEventDao;

	@Resource(name = "lotteryWinSlipNumCaculatorFactory")
	private ILotteryWinCaculatorFactory<ILotteryWinSlipNumCaculator> factory;

	@PropertyConfig(value = "key.seperator")
	private String seperator;

	@Resource(name = "lotteryWinAmountCaculator")
	private LotteryWinAmountCaculator lotteryWinAmountCaculator;

	@Resource(name = "generateGamePlanServiceImpl")
	private IGamePlanService gamePlanService;

	@Resource(name = "gameIssueDaoImpl")
	private IGameIssueDao gameIssueDao;
	
	@Resource(name = "gameDiamondBettypeServiceImpl")
	private IGameDiamondBettypeService gameDiamondBettypeServiceImpl;

	@Resource(name ="gamePackageItemDao")
	protected IGamePackageItemDao packagetitem ;
	
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public int doBusiness(GameContext ctx, String record,GameOrder order,BigAwardCacheBean bigAward) throws Exception {
		//记录中奖表并判断是否为风险订单->生成风险订单->更新订单中奖状态。
		log.debug("进入判断订单中奖流程处理。");
		Long lotteryId = order.getLotteryid();

		//1通过订单获取注单信息。		
		GameSlip entity = new GameSlip();
		entity.setOrderid(order.getId());
		entity.setStatus(1); //等待开奖
		List<GameSlip> slipList = gameSlipDao.querySlipByOrder(order.getId());

		if (null == slipList || slipList.isEmpty()) {
			String msg="获取注单信息失败， gameOrderId =" + order.getId();
			log.error(msg); 
			throw new Exception(msg); 
		}

		GameWinPropertyBean propertyBean = new GameWinPropertyBean();
		boolean winFlag = false;
		for (GameSlip slip : slipList) {
			Integer status = 3; //未中奖
			String[] contents = new String[] { "" };//投注内容

			//获取投注内容
			contents = GameSlipUtilsEnum.INSTANSE.getBetDetai(ctx, order, slip, contents);

			//4判断是否中奖。
			//isWin 0 不中奖，大于0为中奖注数
			IWinResultBean winResultBean = null;
			Long winNum = 0L;
			Long evaluateWin = 0L;
			//如果是超级对子则转换玩法群code
			boolean isSuperPair=SuperPairUtil.isSuperPair(slip.getGameGroupCode());
			Integer groupCode=isSuperPair?SuperPairUtil.getCommGroupCode(slip.getGameGroupCode()):slip.getGameGroupCode();
			//20140107 edit 修改支持单式上传投注开奖。
			for (int i = 0; i < contents.length; i++) {
				
				LotteryPlayMethodKeyGenerator keyGenerator = new LotteryPlayMethodKeyGenerator(slip.getLotteryid(),
						groupCode, slip.getGameSetCode(), slip.getBetMethodCode(), seperator);
				ILotterySlipNumCaculatorContext context = new LotterySlipNumCaculatorContext();
				context.setKeyGenerator(keyGenerator);
				if(slip.getLotteryid().equals(99701L)){
					Map<String,Object> paramMap= new HashMap<String,Object>();
					paramMap.put("singleWin", slip.getSingleWin());
					paramMap.put("awardGroupId", order.getAwardGroupId());
					paramMap.put("orderTime", order.getOrderTime());
					context.setParamMap(paramMap);
				}
				winResultBean = factory.getObject(keyGenerator).getWinSlipNum(contents[i], record, context);

				if (winResultBean.getIsWin()) {
					winFlag = true;
					status = 2; //中奖
					GameSlipUtilsEnum.INSTANSE.getWinAmount(propertyBean, lotteryWinAmountCaculator, slip, contents[i],
							winResultBean, record);
					//如果是多奖金模式，在开奖时，会将实际奖金设置到该字段中，方便以后直接取用。
					//slip.setEvaluateWin(propertyBean.getWinAmout());
					//如果是多奖金模式，在开奖时，会将实际奖金设置到该字段中，方便以后直接取用。 
					evaluateWin += propertyBean.getWinAmout()==null?0L:propertyBean.getWinAmout();
					winNum += slip.getWinNumber()==null?0L:slip.getWinNumber();
					if (lotteryId.longValue() == 99401L) {
						order.setAwardOne(order.getAwardOne() + slip.getAwardOne());
						order.setAwardTwo(order.getAwardTwo() + slip.getAwardTwo());
						if (slip.getAwardOne() != 0 || slip.getAwardTwo() != 0) {
							bigAward.addSlip(slip);
						}
					}
				}
			}
			slip.setEvaluateWin(evaluateWin);
			slip.setWinNumber(winNum);	
			slip.setStatus(status);
			gameSlipDao.update(slip);
			propertyBean.setWinAmout(0L);
		}

		order.setCalculateWinTime(DateUtils.currentDate());
		if (winFlag) {
			order.setStatus(2);
			if (order.getAwardOne() != 0 || order.getAwardTwo() != 0) {
				bigAward.addOrder(order);
			}
			log.debug("订单中奖，生成中奖订单信息。");
			makeGameOrderWin(order, propertyBean.getTotalOrderBouns(), bigAward,0l);
			//设置订单的中奖金额
			order.setTotalWin(propertyBean.getTotalOrderBouns());
		} else {
			order.setStatus(3);
		}
		gameOrderService.updateGameOrder(order);
		
		return 1;
	}
	
	//@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public List<GameOrder> doBusiness_bk(Long lotteryId, Long issueCode) throws Exception {
		final Date calculateDate = new Date();
		if (null == lotteryId || null == issueCode) {

			log.error("时时彩彩种id及期号不能为空。");
			throw new RuntimeException("时时彩彩种id及期号不能为空");
		}

		log.debug("1.开始时时彩【" + lotteryId + "】奖期期号【" + issueCode + "】正常开奖流程。");
		GameIssueEntity entity = null;
		//时时彩正常开奖流程处理
		ProcessResult result = new ProcessResult();
		entity = gameIssueService.queryGameIssueByLotteryIdAndIssueCode(lotteryId, issueCode);
		//5.订单对比 ->是否存在废单
		gameOrderSecurityService.checkOrderSecurity(entity);

		//6.获取开奖号码
		String record = drawResultService.getnumberRecordByLotteryIdAndIssueCode(lotteryId, issueCode);
		if (StringUtils.isBlank(record)) {
			log.error("时时彩【" + lotteryId + "】奖期期号【" + issueCode + "】获取开奖号码失败.");
			throw new RuntimeException("时时彩【" + lotteryId + "】奖期期号【" + issueCode + "】获取开奖号码失败.");
		}

		//导出所有注单数据--开奖完成后已经有导出信息--Gary
		//准备计算奖金导出生成订单数据文件（名称规则为  lotteryId_issueCode_crrentDateTime。txt)
		//exportFileService.exportGameSlip2File(lotteryId, issueCode); 

		//7.获取所有订单
		List<GameOrder> orderList = gameOrderService.getGameOrderByIssueAndLottery(lotteryId, issueCode);

		//本次业务处理：排除系统撤销单 和 已开奖单    
		if (orderList != null) {
			//此处使用原生的迭代器（集合遍历动态删除元素)
			for (Iterator<GameOrder> iter = orderList.iterator(); iter.hasNext();) {
				GameOrder order = iter.next();
				if ((order != null && order.getStatus().intValue() == OrderStatus.CANCEL.getValue()
						&& order.getPlanId() != null && order.getPlanId() == 0L)
						|| (order.getStatus().intValue() != OrderStatus.WAITING.getValue())) {
					iter.remove();
				}
			}
		}
		if (null == orderList || orderList.isEmpty()) {
			try {
				//更新为计奖完成->验奖完成->派奖完成。
				gameIssueService.updataGameIssue(entity,
						Long.valueOf(GameIssuePeriodStatus.WAIT_RECONCILIATION.getValue()),
						Long.valueOf(GameIssuePeriodStatus.WAIT_ISSUE_OVER.getValue()));
				//在这里要进行解锁。
				log.debug("3.1 时时彩【" + lotteryId + "】奖期期号【" + issueCode + "】无订单信息，");
				//				createGameControlEvent(lotteryId, entity, 3L); // 3为正常追号计划。
				//				gamePlanService.generateGamePlan(lotteryId, issueCode);
			} catch (Exception e) {
				log.error("时时彩【" + lotteryId + "】奖期期号【" + issueCode + "】派奖完成失败.", e);
				throw new RuntimeException("时时彩【" + lotteryId + "】奖期期号【" + issueCode + "】派奖完成失败.");
			}
		}

		//所有中奖订单信息
		if (result.isSuccess()) {
			BigAwardCacheBean bigAward = new BigAwardCacheBean();
			try {
				log.debug("进行时时彩【" + lotteryId + "】奖期期号【" + issueCode + "】的订单处理。");
				//8。 判断是否中奖->记录中奖表并判断是否为风险订单->生成风险订单->更新订单中奖状态。

				for (GameOrder order : orderList) {
					//只处理状态：非已撤销
					if (order.getStatus().intValue() != OrderStatus.CANCEL.getValue()) {
						order.setWebIssueCode(entity.getWebIssueCode());
						//20140213 add 返回信息，用于派奖用。					
						doCheckIsDraw_bk(result, order, record, calculateDate, bigAward);
					}
				}
			} catch (Exception e) {
				//  Auto-generated catch block
				log.error("【 + lotteryId + 】【 + issueCode + 】 " + "记奖失败.", e);
				throw new RuntimeException("【 + lotteryId + 】【 + issueCode + 】 " + "记奖失败." + e.getMessage());
			}
			try {
				//处理大奖
				dealBigAward(entity, bigAward);
			} catch (Exception e) {
				log.error("【 + lotteryId + 】【 + issueCode + 】 " + "处理大奖失败.", e);
				throw new RuntimeException("【 + lotteryId + 】【 + issueCode + 】 " + "处理大奖失败." + e.getMessage());
			}

		}

		//更新奖期状态为验奖完成
		if (result.isSuccess()) {

			try {
				log.debug("更新奖期为验奖完成。");
				//更新验奖状态 7奖期结束， 6派奖完成
				//				gameIssueService.updataGameIssue(entity,
				//						Long.valueOf(GameIssuePeriodStatus.WAIT_RECONCILIATION.getValue()),
				//						Long.valueOf(GameIssuePeriodStatus.WAIT_ISSUE_OVER.getValue()));

				GameIssue gameIssue = gameIssueDao.getById(entity.getId());
				//更新奖期开奖结束，变更奖期开奖号码 开奖次数
				updateIssueOpenAwardFinshed(gameIssue, record);

				try {
					//生成生成trend事件
					addGameTrendChartRegenerateEvent(gameIssue);
				} catch (Exception e) {
					log.error("生成生成trend事件失败", e);
				}

			} catch (Exception e) {

				result.fail();
				log.error("时时彩【" + lotteryId + "】奖期期号【" + issueCode + "】更新验奖完成状态失败。", e);
				throw new RuntimeException("时时彩【" + lotteryId + "】奖期期号【" + issueCode + "】更新验奖完成状态失败.");
			}
		}
		return orderList;
	}

	/** 
	* @Title: updateIssueOpenAwardFinshed 
	* @Description: 更新奖期开奖结束，变更奖期开奖号码 开奖次数
	* @param gameIssue
	* @param record
	* @throws Exception
	*/
	public void updateIssueOpenAwardFinshed(GameIssue gameIssue, String record) throws Exception {
		gameIssue.setStatus(Long.valueOf(GameIssuePeriodStatus.WAIT_RECONCILIATION.getValue()));
		gameIssue.setPeriodStatus(Long.valueOf(GameIssuePeriodStatus.WAIT_ISSUE_OVER.getValue()));
		Date now = new Date();
		gameIssue.setUpdateTime(now);
		gameIssue.setFactionDrawTime(now);
		/*gameIssue.setNumberRecord(record);
		gameIssue.setNumberUpdateTime(now);
		gameIssue.setNumberUpdateCount(gameIssue.getNumberUpdateCount().longValue() + 1L);*/
		gameIssueDao.updateGameIssue(gameIssue);
	} 
	private void addGameTrendChartRegenerateEvent(GameIssue gameIssue) throws Exception{ 		
		GameControlEvent event = new GameControlEvent();				
		event.setCreateTime(new Date()); 
		event.setEnentType(18L);
		event.setLotteryid(10000L);
		event.setMessage("生成走势图。");

		GameTrendEventParams params = new GameTrendEventParams();
		params.setLotteryId(gameIssue.getLotteryid());
		params.setIssueCode(gameIssue.getIssueCode());
		params.setType(TrendEventTypeEnum.ADD.getCode());
		params.setNumberRecord(gameIssue.getNumberRecord());
		event.setParams(JsonMapper.nonEmptyMapper().toJson(params));

		event.setSaleEndTime(gameIssue.getSaleEndTime());
		event.setSaleStartTime(gameIssue.getSaleStartTime());
		event.setStartIssueCode(gameIssue.getIssueCode());
		event.setEndIssueCode(gameIssue.getLotteryid());
		event.setStatus(0L);
		gameControlEventDao.insert(event);
	}

	/** 
	* @Title: dealBigAward 
	* @Description: 处理大奖
	* @param lotteryId
	* @param issueCode
	* @param bigAward
	* @throws Exception
	*/
	private void dealBigAward(GameIssueEntity gameIssue, BigAwardCacheBean bigAward) throws Exception {
		if (gameIssue.getLottery().getLotteryId().longValue() == 99401L && (bigAward.getBigOne() != 0 || bigAward.getBigTwo() != 0)) {
			log.warn("双色球大奖处理  " + bigAward.getBigOne() + " " + bigAward.getBigTwo());
			String pool = gameIssue.getAwardStruct();
			GameBonusJsonBean bonus = new GameBonusJsonBean();
			try {
				bonus = JsonMapper.nonEmptyMapper().fromJson(pool, GameBonusJsonBean.class);
			} catch (Exception e) {
				log.error("获取奖期结构异常");
				throw new Exception("获取奖期结构异常");
			}

			//TODO hugh 双色球
			List<GameBonusAwardJsonBean> awards = bonus.getAwards();
			GameBonusPoolJson gameBonusPool = bonus.getGameBonusPoolJson();
			GameBonusAwardJsonBean awardOneMoney = null;
			GameBonusAwardJsonBean awardTwoMoney = null;
			for (GameBonusAwardJsonBean gameBonusAwardJsonBean : awards) {
				if (gameBonusAwardJsonBean.getGameBetType().endsWith("_1")) {
					awardOneMoney = gameBonusAwardJsonBean;
				}
				if (gameBonusAwardJsonBean.getGameBetType().endsWith("_2")) {
					awardTwoMoney = gameBonusAwardJsonBean;
				}
			}

			//获取真实的派奖金额
			Long bigAwardOneTure = 0L;
			Long bigAwardTwoTure = 0L;
			if (bigAward.getBigOne() != 0) {
				Long bigAwardOnePool = gameBonusPool.getActualBonus() * gameBonusPool.getDistribute1()/1000000
						/ bigAward.getBigOne();
				if (bigAwardOnePool > awardOneMoney.getMaxAward()) {
					bigAwardOneTure = awardOneMoney.getMaxAward();
				} else if (bigAwardOnePool < awardOneMoney.getMaxAward()
						&& bigAwardOnePool > awardOneMoney.getMinAward()) {
					bigAwardOneTure = bigAwardOnePool;
				} else {
					bigAwardOneTure = awardOneMoney.getMinAward()==null ? 0L:awardOneMoney.getMinAward();
				}
			}
			if (bigAward.getBigTwo() != 0) {
				Long bigAwardTwoPool = gameBonusPool.getActualBonus() * gameBonusPool.getDistribute2()/1000000
						/ bigAward.getBigTwo();

				if (bigAwardTwoPool > awardTwoMoney.getMaxAward()) {
					bigAwardTwoTure = awardTwoMoney.getMaxAward();
				} else if (bigAwardTwoPool < awardTwoMoney.getMaxAward()
						&& bigAwardTwoPool > awardTwoMoney.getMinAward()) {
					bigAwardTwoTure = bigAwardTwoPool;
				} else {
					bigAwardTwoTure = awardTwoMoney.getMinAward()==null ? 0L:awardTwoMoney.getMinAward();
				}
			}

			//更新订单、注单、中奖单数据 并保存
			List<GameOrder> bigAwardOrders = bigAward.getOrders();
			for (GameOrder gameOrder : bigAwardOrders) {
				if(gameOrder.getTotalWin() == null){
					gameOrder.setTotalWin(0L);
				}
				gameOrder.setTotalWin(gameOrder.getTotalWin() + gameOrder.getAwardOne() * bigAwardOneTure);
				gameOrder.setTotalWin(gameOrder.getTotalWin() + gameOrder.getAwardTwo() * bigAwardTwoTure);
				gameOrderService.updateGameOrder(gameOrder);
			}
			List<GameSlip> bigAwardSlips = bigAward.getSlips();
			for (GameSlip gameSlip : bigAwardSlips) {
				if(gameSlip.getEvaluateWin() == null){
					gameSlip.setEvaluateWin(0L);
				}
				gameSlip.setEvaluateWin(gameSlip.getEvaluateWin() + gameSlip.getAwardOne() * bigAwardOneTure);
				gameSlip.setEvaluateWin(gameSlip.getEvaluateWin() + gameSlip.getAwardTwo() * bigAwardTwoTure);
				gameSlipDao.update(gameSlip);
			}
			List<GameOrderWin> bigAwardOrderWins = bigAward.getOrderWins();
			for (GameOrderWin gameOrderWin : bigAwardOrderWins) {
				if(gameOrderWin.getCountWin() == null){
					gameOrderWin.setCountWin(0L);
				}
				gameOrderWin.setCountWin(gameOrderWin.getCountWin() +formatLong(gameOrderWin.getAwardOne() * bigAwardOneTure));
				gameOrderWin.setCountWin(gameOrderWin.getCountWin() + formatLong(gameOrderWin.getAwardTwo() * bigAwardTwoTure));
				orderWinDao.update(gameOrderWin);
			}
		}
	}
	private static   Long formatLong(Long aaa){
		if(aaa==null) return null;
		return NumberUtils.toLong(String.valueOf(aaa/100)+"00");
	}
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void doCheckIsDraw(GameContext ctx, String record,GameOrder order,BigAwardCacheBean bigAward) throws Exception {
		log.debug("doCheckIsDraw"+order.getId()); 
		//记录中奖表并判断是否为风险订单->生成风险订单->更新订单中奖状态。
		log.debug("进入判断订单中奖流程处理。");
		Long lotteryId = order.getLotteryid();

		//1通过订单获取注单信息。		
		GameSlip entity = new GameSlip();
		entity.setOrderid(order.getId());
		entity.setStatus(1); //等待开奖
		List<GameSlip> slipList = gameSlipDao.querySlipByOrder(order.getId());

		if (null == slipList || slipList.isEmpty()) {
			String msg="获取注单信息失败， gameOrderId =" + order.getId();
			log.error(msg); 
			throw new Exception(msg); 
		}

		GameWinPropertyBean propertyBean = new GameWinPropertyBean();
		boolean winFlag = false;
		for (GameSlip slip : slipList) {
			Integer status = 3; //未中奖
			String[] contents = new String[] { "" };//投注内容

			//获取投注内容
			contents = GameSlipUtilsEnum.INSTANSE.getBetDetai(ctx, order, slip, contents);

			//4判断是否中奖。
			//isWin 0 不中奖，大于0为中奖注数
			IWinResultBean winResultBean = null;
			Long winNum = 0L;
			Long evaluateWin = 0L;
			//20140107 edit 修改支持单式上传投注开奖。
			for (int i = 0; i < contents.length; i++) {
				LotteryPlayMethodKeyGenerator keyGenerator = new LotteryPlayMethodKeyGenerator(slip.getLotteryid(),
						slip.getGameGroupCode(), slip.getGameSetCode(), slip.getBetMethodCode(), seperator);
				ILotterySlipNumCaculatorContext context = new LotterySlipNumCaculatorContext();
				context.setKeyGenerator(keyGenerator);
				winResultBean = factory.getObject(keyGenerator).getWinSlipNum(contents[i], record, context);

				if (winResultBean.getIsWin()) {
					winFlag = true;
					status = 2; //中奖
					GameSlipUtilsEnum.INSTANSE.getWinAmount(propertyBean, lotteryWinAmountCaculator, slip, contents[i],
							winResultBean, record);
					//如果是多奖金模式，在开奖时，会将实际奖金设置到该字段中，方便以后直接取用。 
					evaluateWin += propertyBean.getWinAmout()==null?0L:propertyBean.getWinAmout();
					winNum += slip.getWinNumber()==null?0L:slip.getWinNumber();
					if (lotteryId.longValue() == 99401L) {
						order.setAwardOne(order.getAwardOne() + slip.getAwardOne());
						order.setAwardTwo(order.getAwardTwo() + slip.getAwardTwo());
						if (slip.getAwardOne() != 0 || slip.getAwardTwo() != 0) {
							bigAward.addSlip(slip);
						}
					}
				}
			}
			slip.setEvaluateWin(evaluateWin);
			slip.setWinNumber(winNum);			
			slip.setStatus(status);
			gameSlipDao.update(slip);
			propertyBean.setWinAmout(0L);
		}

		order.setCalculateWinTime(DateUtils.currentDate());
		if (winFlag) {
			order.setStatus(2);
			if (order.getAwardOne() != 0 || order.getAwardTwo() != 0) {
				bigAward.addOrder(order);
			}
			log.debug("订单中奖，生成中奖订单信息。");
			makeGameOrderWin(order, propertyBean.getTotalOrderBouns(), bigAward,0l);
			//设置订单的中奖金额
			order.setTotalWin(propertyBean.getTotalOrderBouns());
		} else {
			order.setStatus(3);
		}
		gameOrderService.updateGameOrder(order);
	}
	
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void doCheckIsDrawMMC(GameContext ctx, String record,GameOrder order,BigAwardCacheBean bigAward,Long diamondLv) throws Exception {
		log.debug("doCheckIsDraw"+order.getId()); 
		//记录中奖表并判断是否为风险订单->生成风险订单->更新订单中奖状态。
		log.debug("进入判断订单中奖流程处理。");
		Long lotteryId = order.getLotteryid();
		Long multiple = 0l;
		//1通过订单获取注单信息。		
		GameSlip entity = new GameSlip();
		entity.setOrderid(order.getId());
		entity.setStatus(1); //等待开奖
		List<GameSlip> slipList = gameSlipDao.querySlipByOrder(order.getId());

		if (null == slipList || slipList.isEmpty()) {
			String msg="获取注单信息失败， gameOrderId =" + order.getId();
			log.error(msg); 
			throw new Exception(msg); 
		}

			
		GameWinPropertyBean propertyBean = new GameWinPropertyBean();
		boolean winFlag = false;
		boolean diamondMultiple = true;
		Long totDiamondWin = 0l;
		for (GameSlip slip : slipList) {
			Integer status = 3; //未中奖
			String[] contents = new String[] { "" };//投注内容

			if(diamondMultiple && slip.getDiamondAmount() > 0l){
				//鑽石加獎中獎金額
				diamondLv = getDiamondLv(record);
				if(diamondLv > 0){
					List<GameDiamondBettype> betRes =	gameDiamondBettypeServiceImpl.getDiamondBettypeByGroupCode(diamondLv);
					List<Long> multipleList = new ArrayList<Long>();
					for(GameDiamondBettype bet : betRes){
						for(int i = 0 ; i < bet.getProbability() ; i++){
							multipleList.add(bet.getMultiple());
						}
					}
					
					multiple = multipleList.get((int)Math.ceil((Math.random()*multipleList.size())-1));
				}else{
					multiple = 10l;
				}
				diamondMultiple = false;
			}
			
			//获取投注内容
			contents = GameSlipUtilsEnum.INSTANSE.getBetDetai(ctx, order, slip, contents);

			//4判断是否中奖。
			//isWin 0 不中奖，大于0为中奖注数
			IWinResultBean winResultBean = null;
			Long winNum = 0L;
			Long evaluateWin = 0L;
			Long diamondWin = 0L;
			//20140107 edit 修改支持单式上传投注开奖。
			for (int i = 0; i < contents.length; i++) {
				LotteryPlayMethodKeyGenerator keyGenerator = new LotteryPlayMethodKeyGenerator(slip.getLotteryid(),
						slip.getGameGroupCode(), slip.getGameSetCode(), slip.getBetMethodCode(), seperator);
				ILotterySlipNumCaculatorContext context = new LotterySlipNumCaculatorContext();
				context.setKeyGenerator(keyGenerator);
				winResultBean = factory.getObject(keyGenerator).getWinSlipNum(contents[i], record, context);

				if (winResultBean.getIsWin()) {
					winFlag = true;
					status = 2; //中奖
					GameSlipUtilsEnum.INSTANSE.getWinAmount(propertyBean, lotteryWinAmountCaculator, slip, contents[i],
							winResultBean, record);
					//如果是多奖金模式，在开奖时，会将实际奖金设置到该字段中，方便以后直接取用。 
					evaluateWin += propertyBean.getWinAmout()==null?0L:propertyBean.getWinAmout();
					winNum += slip.getWinNumber()==null?0L:slip.getWinNumber();
					if (lotteryId.longValue() == 99401L) {
						order.setAwardOne(order.getAwardOne() + slip.getAwardOne());
						order.setAwardTwo(order.getAwardTwo() + slip.getAwardTwo());
						if (slip.getAwardOne() != 0 || slip.getAwardTwo() != 0) {
							bigAward.addSlip(slip);
						}
					}
				}
			}
			if(status == 2){
				if(lotteryId.longValue() == 99112L){
					if(slip.getDiamondAmount() > 0l){
							diamondWin = ((evaluateWin*multiple)/10)-evaluateWin;
							totDiamondWin += diamondWin;
					}
				}
			}
			slip.setEvaluateWin(evaluateWin);
			slip.setWinNumber(winNum);			
			slip.setStatus(status);
			slip.setDiamondWin(diamondWin);
			gameSlipDao.update(slip);
			propertyBean.setWinAmout(0L);
		}

		order.setCalculateWinTime(DateUtils.currentDate());
		if (winFlag) {
			order.setStatus(2);
			if (order.getAwardOne() != 0 || order.getAwardTwo() != 0) {
				bigAward.addOrder(order);
			}
			log.debug("订单中奖，生成中奖订单信息。");
			makeGameOrderWin(order, propertyBean.getTotalOrderBouns(), bigAward,totDiamondWin);
			//设置订单的中奖金额
			order.setTotalWin(propertyBean.getTotalOrderBouns()+totDiamondWin);
			order.setDiamondMultiple(multiple);
		} else {
			order.setStatus(3);
			order.setDiamondMultiple(0l);
		}
		gameOrderService.updateGameOrderMMC(order);
	}
	
	private Long getDiamondLv(String numberRecord){
		char diamond = numberRecord.charAt(0);
		Long count = 0l;
		for(int i = 1; i<numberRecord.length(); i++){
			if(diamond == numberRecord.charAt(i)){
				count++;
			}
		}
		return count;
	}
	
	
	
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public ProcessResult doCheckIsDraw_bk(ProcessResult result, GameOrder order, String record, Date calculateDate,
			BigAwardCacheBean bigAward) throws Exception {

		//记录中奖表并判断是否为风险订单->生成风险订单->更新订单中奖状态。
		log.debug("进入判断订单中奖流程处理。");
		Long lotteryId = order.getLotteryid();

		//1通过订单获取注单信息。		
		GameSlip entity = new GameSlip();
		entity.setOrderid(order.getId());
		entity.setStatus(1); //等待开奖
		List<GameSlip> slipList = gameSlipDao.querySlipByOrder(order.getId());

		if (null == slipList || slipList.isEmpty()) {
			String msg="获取注单信息失败， gameOrderId =" + order.getId();
			log.error(msg);
			result.fail("-4", "获取注单信息失败"); //不能进行后续操作
			throw new Exception(msg); 
		}

		GameWinPropertyBean propertyBean = new GameWinPropertyBean();
		boolean winFlag = false;
		for (GameSlip slip : slipList) {
			Integer status = 3; //未中奖
			String[] contents = new String[] { "" };//投注内容

			//获取投注内容
			contents = GameSlipUtilsEnum.INSTANSE.getBetDetai_bk(result, order, slip, contents);

			//4判断是否中奖。
			//isWin 0 不中奖，大于0为中奖注数
			IWinResultBean winResultBean = null;
			Long winNum = 0L;
			Long evaluateWin = 0L;
			//20140107 edit 修改支持单式上传投注开奖。
			for (int i = 0; i < contents.length; i++) {
				LotteryPlayMethodKeyGenerator keyGenerator = new LotteryPlayMethodKeyGenerator(slip.getLotteryid(),
						slip.getGameGroupCode(), slip.getGameSetCode(), slip.getBetMethodCode(), seperator);
				ILotterySlipNumCaculatorContext context = new LotterySlipNumCaculatorContext();
				context.setKeyGenerator(keyGenerator);
				winResultBean = factory.getObject(keyGenerator).getWinSlipNum(contents[i], record, context);

				if (winResultBean.getIsWin()) {
					winFlag = true;
					status = 2; //中奖
					GameSlipUtilsEnum.INSTANSE.getWinAmount(propertyBean, lotteryWinAmountCaculator, slip, contents[i],
							winResultBean, record);
					//如果是多奖金模式，在开奖时，会将实际奖金设置到该字段中，方便以后直接取用。
					evaluateWin += propertyBean.getWinAmout()==null?0L:propertyBean.getWinAmout();
					winNum += slip.getWinNumber()==null?0L:slip.getWinNumber();
					if (lotteryId.longValue() == 99401L) {
						order.setAwardOne(order.getAwardOne() + slip.getAwardOne());
						order.setAwardTwo(order.getAwardTwo() + slip.getAwardTwo());
						if (slip.getAwardOne() != 0 || slip.getAwardTwo() != 0) {
							bigAward.addSlip(slip);
						}
					}
				}
			}
			slip.setEvaluateWin(evaluateWin);
			slip.setWinNumber(winNum);	
			slip.setStatus(status);
			gameSlipDao.update(slip);
			propertyBean.setWinAmout(0L);
		}

		order.setCalculateWinTime(calculateDate);
		if (winFlag) {
			order.setStatus(2);
			if (order.getAwardOne() != 0 || order.getAwardTwo() != 0) {
				bigAward.addOrder(order);
			}
			log.debug("订单中奖，生成中奖订单信息。");
			makeGameOrderWin(order, propertyBean.getTotalOrderBouns(), bigAward,0l);
			//设置订单的中奖金额
			order.setTotalWin(propertyBean.getTotalOrderBouns());
		} else {
			order.setStatus(3);
		}
		gameOrderService.updateGameOrder(order);
		return result;
	}

	
	
	private GameOrderWin makeGameOrderWin(GameOrder order, Long winAmout, BigAwardCacheBean bigAward,Long totDiamondWin) throws Exception {
		if(order==null) return null;
		GameOrderWin orderWin = null;
		try {
			orderWin=this.gameOrderWinService.getByOrderId(new GameContext(), order.getId());
			if(orderWin!=null){
				log.info("更新中奖订单信息。");
				orderWin = new GameOrderWin();
				orderWin.setCalculateWinTime(DateUtils.currentDate());
				orderWin.setCountWin(winAmout); 
				orderWin.setStatus(Long.valueOf(GameOrderWin.Status.WAITING.getValue())); //待派奖 
				orderWin.setDiamondCountWin(totDiamondWin);
			}else{ 
				log.info("生成中奖订单信息。");
				orderWin = VOConvert4Task.getGameOrderWinVo(order, winAmout);
				orderWin.setDiamondCountWin(totDiamondWin);
				orderWinDao.insert(orderWin); 
			}
			if (order.getLotteryid() == 99401L) {
				orderWin.setAwardOne(orderWin.getAwardOne() + order.getAwardOne());
				orderWin.setAwardTwo(orderWin.getAwardTwo() + order.getAwardTwo());
				if (order.getAwardOne() != 0 || order.getAwardTwo() != 0) {
					bigAward.addOrderWin(orderWin);
				}
			}

		} catch (Exception e) {
			log.error("生成中奖订单错误", e);
			throw e;
		}
		return orderWin;
	}

	@Override
	public void undo(Long lotteryId, Long issueCode) throws Exception {
		undo(lotteryId, issueCode, null);
	}

	@Override
	public void undo(Long lotteryId, Long issueCode, Date saleTime) throws Exception {
		Date calculateTime = gameOrderService.getCalculateTimeByLotteryIdAndIssueCode(lotteryId, issueCode);
		//撤销追号计划
		gamePlanDao.redoGamePlan(lotteryId, issueCode, saleTime);
		//撤销导出文件
		exportFileService.undoRedoExportFile(lotteryId, issueCode, calculateTime);
		//撤销订单
		int orderStatus = OrderStatus.CANCEL.getValue();
		gameOrderService.undoRedoGameOrders(lotteryId, issueCode, saleTime, orderStatus);
		//撤销注单
		int aimStatus = GameSlipStatus.CANCEL.getValue();
		gameSlipDao.undoGameSlip(lotteryId, issueCode, saleTime, aimStatus);
	}

	@Override
	public void redo(Long lotteryId, Long issueCode) throws Exception {
		Date calculateTime = gameOrderService.getCalculateTimeByLotteryIdAndIssueCode(lotteryId, issueCode);
		//撤销追号计划
		gamePlanDao.redoGamePlan(lotteryId, issueCode, null);
		//撤销到处文件
		exportFileService.undoRedoExportFile(lotteryId, issueCode, calculateTime);
		//撤销订单
		int orderStatus = OrderStatus.WAITING.getValue();
		gameOrderService.undoRedoGameOrders(lotteryId, issueCode, null, orderStatus);
		//撤销注单
		int aimStatus = GameSlipStatus.WAITING.getValue();
		gameSlipDao.undoGameSlip(lotteryId, issueCode, null, aimStatus);
	}

	@Override
	public GameOrder doBusiness(Long lotteryId, Long issueCode, Long orderId) throws Exception {

		final Date calculateDate = new Date();
		if (null == lotteryId || null == issueCode) {

			log.error("时时彩彩种id及期号不能为空。");
			throw new RuntimeException("时时彩彩种id及期号不能为空");
		}

		log.info("1.开始时时彩【" + lotteryId + "】奖期期号【" + issueCode + "】正常开奖流程。");
		GameIssueEntity entity = null;
		//时时彩正常开奖流程处理
		ProcessResult result = new ProcessResult();
		entity = gameIssueService.queryGameIssueByLotteryIdAndIssueCode(lotteryId, issueCode);
		//5.订单对比 ->是否存在废单
		gameOrderSecurityService.checkOrderSecurity(entity);

		//6.获取开奖号码
		String record = drawResultService.getnumberRecordByLotteryIdAndIssueCode(lotteryId, issueCode);
		//导出所有注单数据
		//准备计算奖金导出生成订单数据文件（名称规则为  lotteryId_issueCode_crrentDateTime。txt)
		final Long finalLotteryId = lotteryId;
		final Long finalIssueCode = issueCode;
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					exportFileService.exportGameSlip2File(finalLotteryId, finalIssueCode, calculateDate);
				} catch (Exception e) {
					log.error("导出文件出错：", e);
				}
			}
		}).start();

		//7.获取订单
		GameOrder gameOrder = gameOrderService.getGameOrderById(orderId);
		//所有中奖订单信息
		if (result.isSuccess()) {

			try {
				log.info("进行时时彩【" + lotteryId + "】奖期期号【" + issueCode + "】的订单处理。");
				//8。 判断是否中奖->记录中奖表并判断是否为风险订单->生成风险订单->更新订单中奖状态。
				gameOrder.setWebIssueCode(entity.getWebIssueCode());
				//20140213 add 返回信息，用于派奖用。
				BigAwardCacheBean bigAward = new BigAwardCacheBean();
				doCheckIsDraw_bk(result, gameOrder, record, calculateDate, bigAward);
			} catch (Exception e) {
				//  Auto-generated catch block
				log.error("时时彩【" + lotteryId + "】奖期期号【" + issueCode + "】 返回中奖订单信息失败.", e);
				throw new RuntimeException("时时彩【" + lotteryId + "】奖期期号【" + issueCode + "】 返回中奖订单信息失败.");
			}
		}
		return gameOrder;
	}
	private Integer getBetMethodCode(String bettype) {
		if (bettype.split("_")[2] == null || bettype.split("_")[2].equals("null")) {
			return null;
		}
		return Integer.valueOf(bettype.split("_")[2]);
	}
	
	private Integer getGameSetCode(String bettype) {
		return Integer.valueOf(bettype.split("_")[1]);
	}
	
	private com.winterframework.firefrog.game.dao.vo.GameSlip convertGamePlantoSlip(
			GamePackageItem detailStruc, Long issucode ,long lotteryid ) throws Exception {

		com.winterframework.firefrog.game.dao.vo.GameSlip detail = new com.winterframework.firefrog.game.dao.vo.GameSlip();
		//设置奖期
		GameIssueEntity issue = new GameIssueEntity();
		issue.setIssueCode(issucode);
	
		//初始化圆角模式

		detail.setBetDetail(detailStruc.getBetDetail());
		//初始化玩法信息
		GameBetType betType = new GameBetType();
		String betTypeCode = detailStruc.getBetTypeCode();
		String[] betArr = betTypeCode.split("_");
		betType.setGameGroupCode(Integer.valueOf(betArr[0]));
		betType.setGameSetCode(Integer.valueOf(betArr[1]));
		Integer integer = (betArr[2] == null || betArr[2].equals("null")) ? null : Integer.valueOf(betArr[2]);
		betType.setBetMethodCode(integer);
	
		detail.setBetTypeCode(detailStruc.getBetDetail());
		detail.setTotbets(Long.valueOf(detailStruc.getTotbets()));
		Long totalAomunt = detailStruc.getTotamount();
	
		if(lotteryid == 99601L || lotteryid == 99602L || lotteryid == 99603L){
			totalAomunt=Long.valueOf(detailStruc.getTotamount());
		}else{
			totalAomunt=Long.valueOf(detailStruc.getTotamount()/detailStruc.getMultiple().intValue());			
		}
		detail.setTotamount(totalAomunt);
		detail.setMultiple(1L);
		//初始化注单状态
		
		detail.setFileMode(detailStruc.getFileMode()); 
		detail.setAwardMode(detailStruc.getAwardMode());
		if(detailStruc.getRetPoint()!=null)
		detail.setRetPoint(Long.valueOf(detailStruc.getRetPoint()));
		detail.setRetAward(detailStruc.getRetAward());
		detail.setPackageItemId(detailStruc.getId());

		return detail;
	}
	
	public Float checkSBreawrd (String recordNumber ,Long loteryid , Long issuecode, Long orderCount) throws Exception
	{ 
		Float resultNumber = 0.0f;
		
		//min（（理论奖金/投注）-90%）
		Float Totamount = 0.0f;
		Float sumaward = 0.0f;
		GameContext ctx = new GameContext ();
		List<GameOrder> orderList = gameOrderDao.getGameOrderByIssueAndLottery(loteryid, issuecode);
		// 必定的都要select 所以就把 數量門檻值直接帶進來使用 少selecet 幾次
		if ( orderList.size() < orderCount ){
			return resultNumber;
		}
		if (orderList != null && orderList.size()>0) { 
			for(GameOrder order : orderList){
	
				Long evaluateWin = 0L;
				List<GameSlip> slipList = gameSlipDao.querySlipByOrder(order.getId());
				if (slipList != null && slipList.isEmpty() == false) {
					String[] contents = new String[] { "" };//投注内容
					GameWinPropertyBean propertyBean = new GameWinPropertyBean();
					for (GameSlip slip : slipList) {
					//获取投注内容
						IWinResultBean winResultBean = null;
						boolean isSuperPair = SuperPairUtil.isSuperPair(slip.getGameGroupCode());
						Integer groupCode = isSuperPair?SuperPairUtil.getCommGroupCode(slip.getGameGroupCode()):slip.getGameGroupCode();
						contents = GameSlipUtilsEnum.INSTANSE.getBetDetai(ctx, order, slip, contents);
						Totamount += (float)slip.getTotamount();
						for (int i = 0; i < contents.length; i++) {
							
							LotteryPlayMethodKeyGenerator keyGenerator = new LotteryPlayMethodKeyGenerator(slip.getLotteryid(),
									groupCode, slip.getGameSetCode(), slip.getBetMethodCode(), seperator);
							ILotterySlipNumCaculatorContext context = new LotterySlipNumCaculatorContext();
							context.setKeyGenerator(keyGenerator);
							winResultBean = factory.getObject(keyGenerator).getWinSlipNum(contents[i], recordNumber, context);
							if (winResultBean.getIsWin()) {
								Long tmpWinAmount = lotteryWinAmountCaculator.getEvalWinAmountforTest(slip, winResultBean, contents[i],recordNumber);
								evaluateWin += tmpWinAmount == null ? 0L: tmpWinAmount;
							}
						}
					}
				}
				sumaward += evaluateWin;
			
			}
		}
		
		// 追號 
		List<GamePackageItem> items = packagetitem.getPackageItemListByIssue(loteryid, issuecode);
		
		for(GamePackageItem item : items){
			Long evaluateWin = 0L;
			IWinResultBean winResultBean = null;
			String[] contents = new String[] { "" };//投注内容
			Totamount += item.getTotamount();
			contents = GameSlipUtilsEnum.INSTANSE.getBetPackageDetai(loteryid, item, contents);
			boolean isSuperPair = SuperPairUtil.isSuperPair(Integer.valueOf(item.getBetTypeCode().split("_")[0]));
			Integer groupCode = isSuperPair?SuperPairUtil.getCommGroupCode(Integer.valueOf(item.getBetTypeCode().split("_")[0])):Integer.valueOf(item.getBetTypeCode().split("_")[0]);
			for (int i = 0; i < contents.length; i++) {
			  GameSlip slip = convertGamePlantoSlip (item, issuecode , loteryid );
				LotteryPlayMethodKeyGenerator keyGenerator = new LotteryPlayMethodKeyGenerator(loteryid,
						groupCode, getGameSetCode (item.getBetTypeCode()), getBetMethodCode (item.getBetTypeCode()), seperator);
				ILotterySlipNumCaculatorContext context = new LotterySlipNumCaculatorContext();
				context.setKeyGenerator(keyGenerator);
				winResultBean = factory.getObject(keyGenerator).getWinSlipNum(contents[i], recordNumber, context);
				if (winResultBean.getIsWin()) {
					Long tmpWinAmount = lotteryWinAmountCaculator.getEvalWinAmountforTest(slip, winResultBean, contents[i], recordNumber);
					evaluateWin += tmpWinAmount == null ? 0L: tmpWinAmount;
				}
			}
			sumaward += evaluateWin;
		}
		
		resultNumber = (float) (sumaward/Totamount) * 100.0f;
		return resultNumber;
	}
}
