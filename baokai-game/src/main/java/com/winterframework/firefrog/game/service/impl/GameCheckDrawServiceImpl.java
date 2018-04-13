package com.winterframework.firefrog.game.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.winterframework.firefrog.common.util.DateUtils;
import com.winterframework.firefrog.common.util.GameContext;
import com.winterframework.firefrog.common.wincaculate.ILotterySlipNumCaculatorContext;
import com.winterframework.firefrog.common.wincaculate.ILotteryWinCaculatorFactory;
import com.winterframework.firefrog.common.wincaculate.ILotteryWinSlipNumCaculator;
import com.winterframework.firefrog.common.wincaculate.IWinResultBean;
import com.winterframework.firefrog.game.dao.IGameControlEventDao;
import com.winterframework.firefrog.game.dao.IGameIssueDao;
import com.winterframework.firefrog.game.dao.IGameOrderDao;
import com.winterframework.firefrog.game.dao.IGameOrderWinDao;
import com.winterframework.firefrog.game.dao.IGamePlanDao;
import com.winterframework.firefrog.game.dao.IGameSlipDao;
import com.winterframework.firefrog.game.dao.vo.GameDiamondBettype;
import com.winterframework.firefrog.game.dao.vo.GameIssue;
import com.winterframework.firefrog.game.dao.vo.GameOrder;
import com.winterframework.firefrog.game.dao.vo.GameOrderWin;
import com.winterframework.firefrog.game.dao.vo.GameSlip;
import com.winterframework.firefrog.game.entity.GameIssuePeriodStatus;
import com.winterframework.firefrog.game.entity.VOConvert4Task;
import com.winterframework.firefrog.game.service.IGameCheckDrawService;
import com.winterframework.firefrog.game.service.IGameDiamondBettypeService;
import com.winterframework.firefrog.game.service.IGameDrawResultService;
import com.winterframework.firefrog.game.service.IGameIssueService;
import com.winterframework.firefrog.game.service.IGameOrderService;
import com.winterframework.firefrog.game.service.IGameOrderWinService;
import com.winterframework.firefrog.game.service.IGamePlanService;
import com.winterframework.firefrog.game.service.bean.BigAwardCacheBean;
import com.winterframework.firefrog.game.service.utils.GameSlipUtilsEnum;
import com.winterframework.firefrog.game.service.utils.GameWinPropertyBean;
import com.winterframework.firefrog.game.service.wincaculate.amount.LotteryWinAmountCaculator;
import com.winterframework.firefrog.game.service.wincaculate.config.LotteryPlayMethodKeyGenerator;
import com.winterframework.firefrog.game.service.wincaculate.config.LotterySlipNumCaculatorContext;
import com.winterframework.firefrog.game.util.SuperPairUtil;
import com.winterframework.modules.spring.exetend.PropertyConfig;

@Service("gameCheckDrawServiceImpl")
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class GameCheckDrawServiceImpl implements IGameCheckDrawService {
	private static final Logger log = LoggerFactory.getLogger(GameCheckDrawServiceImpl.class);

	@Resource(name = "gameSlipDaoImpl")
	private IGameSlipDao gameSlipDao;

	@Resource(name = "gamePlanDaoImpl")
	private IGamePlanDao gamePlanDao;

	@Resource(name = "gameOrderWinDaoImpl")
	private IGameOrderWinDao orderWinDao;

//	@Resource(name = "exportFileServiceImpl")
//	private IExportFileService exportFileService;

	@Resource(name = "gameDrawResultServiceImpl")
	private IGameDrawResultService drawResultService;

	@Resource(name = "gameOrderServiceImpl")
	private IGameOrderService gameOrderService;
	@Resource(name = "gameOrderWinServiceImpl")
	private IGameOrderWinService gameOrderWinService;
	@Resource(name = "gameIssueServiceImpl")
	private IGameIssueService gameIssueService;

//	@Resource(name = "gameOrderSecurityServiceImpl")
//	private IGameOrderSecurityService gameOrderSecurityService;

	@Resource(name = "gameControlEventDaoImpl")
	private IGameControlEventDao gameControlEventDao;

	@Resource(name = "lotteryWinSlipNumCaculatorFactory")
	private ILotteryWinCaculatorFactory<ILotteryWinSlipNumCaculator> factory;

	@PropertyConfig(value = "key.seperator")
	private String seperator;

	@Resource(name = "lotteryWinAmountCaculator")
	private LotteryWinAmountCaculator lotteryWinAmountCaculator;
	
	@Resource(name = "lotteryWinAmountCaculatorPreCalculateWin")
	private LotteryWinAmountCaculator lotteryWinAmountCaculatorPreCalculateWin;

//	@Resource(name = "generateGamePlanServiceImpl")
//	private IGamePlanService gamePlanService;

	@Resource(name = "gameIssueDaoImpl")
	private IGameIssueDao gameIssueDao;
	
	@Resource(name = "gameDiamondBettypeServiceImpl")
	private IGameDiamondBettypeService gameDiamondBettypeServiceImpl;
	
	@Resource(name = "gameOrderDaoImpl")
	private IGameOrderDao gameOrderDao;

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

			//鑽石加獎輪盤倍數
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
			//如果是超级对子则转换玩法群code
			boolean isSuperPair=SuperPairUtil.isSuperPair(slip.getGameGroupCode());
			Integer groupCode=isSuperPair?SuperPairUtil.getCommGroupCode(slip.getGameGroupCode()):slip.getGameGroupCode();
			
			//20140107 edit 修改支持单式上传投注开奖。
			for (int i = 0; i < contents.length; i++) {
				LotteryPlayMethodKeyGenerator keyGenerator = new LotteryPlayMethodKeyGenerator(slip.getLotteryid(),
						groupCode, slip.getGameSetCode(), slip.getBetMethodCode(), seperator);
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
	
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public Long preCalculateWin(String numberRecord,Long orderId) throws Exception {
		GameContext ctx=new GameContext();
		Long diamondLv=null;
		log.info("mmc pre calculate win. orderId="+orderId); 
		
		GameOrder order=gameOrderDao.getById(orderId);
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

			//鑽石加獎輪盤倍數
			if(diamondMultiple && slip.getDiamondAmount() > 0l){
				//鑽石加獎中獎金額
				diamondLv = getDiamondLv(numberRecord);
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
			//如果是超级对子则转换玩法群code
			boolean isSuperPair=SuperPairUtil.isSuperPair(slip.getGameGroupCode());
			Integer groupCode=isSuperPair?SuperPairUtil.getCommGroupCode(slip.getGameGroupCode()):slip.getGameGroupCode();
			
			//20140107 edit 修改支持单式上传投注开奖。
			for (int i = 0; i < contents.length; i++) {
				LotteryPlayMethodKeyGenerator keyGenerator = new LotteryPlayMethodKeyGenerator(slip.getLotteryid(),
						groupCode, slip.getGameSetCode(), slip.getBetMethodCode(), seperator);
				ILotterySlipNumCaculatorContext context = new LotterySlipNumCaculatorContext();
				context.setKeyGenerator(keyGenerator);
				winResultBean = factory.getObject(keyGenerator).getWinSlipNum(contents[i], numberRecord, context);

				if (winResultBean.getIsWin()) {
					winFlag = true;
					status = 2; //中奖
					GameSlipUtilsEnum.INSTANSE.getWinAmount(propertyBean, lotteryWinAmountCaculatorPreCalculateWin, slip, contents[i],
							winResultBean, numberRecord);
					//如果是多奖金模式，在开奖时，会将实际奖金设置到该字段中，方便以后直接取用。 
					evaluateWin += propertyBean.getWinAmout()==null?0L:propertyBean.getWinAmout();
					winNum += slip.getWinNumber()==null?0L:slip.getWinNumber();
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
		}

		return propertyBean.getTotalOrderBouns()+totDiamondWin;
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

}
