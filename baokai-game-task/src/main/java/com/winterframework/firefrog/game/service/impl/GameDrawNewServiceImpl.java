package com.winterframework.firefrog.game.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.core.type.TypeReference;
import com.winterframework.firefrog.common.httpjsonclient.IHttpJsonClient;
import com.winterframework.firefrog.common.util.DateUtils;
import com.winterframework.firefrog.common.util.GameContext;
import com.winterframework.firefrog.game.dao.IGameControlEventDao;
import com.winterframework.firefrog.game.dao.vo.GameBonusAwardJsonBean;
import com.winterframework.firefrog.game.dao.vo.GameBonusJsonBean;
import com.winterframework.firefrog.game.dao.vo.GameBonusPoolJson;
import com.winterframework.firefrog.game.dao.vo.GameControlEvent;
import com.winterframework.firefrog.game.dao.vo.GameIssue;
import com.winterframework.firefrog.game.dao.vo.GameOrder;
import com.winterframework.firefrog.game.dao.vo.GameOrderWin;
import com.winterframework.firefrog.game.dao.vo.GameSlip;
import com.winterframework.firefrog.game.dao.vo.GameWarnUser;
import com.winterframework.firefrog.game.dao.vo.VOConverter;
import com.winterframework.firefrog.game.entity.GameIssueEntity;
import com.winterframework.firefrog.game.entity.GameIssuePeriodStatus;
import com.winterframework.firefrog.game.enums.YesNo;
import com.winterframework.firefrog.game.exception.GameException;
import com.winterframework.firefrog.game.service.ICommonGamePlanService;
import com.winterframework.firefrog.game.service.IExportFileService;
import com.winterframework.firefrog.game.service.IGameCheckDrawService;
import com.winterframework.firefrog.game.service.IGameDrawNewService;
import com.winterframework.firefrog.game.service.IGameDrawResultService;
import com.winterframework.firefrog.game.service.IGameIssueService;
import com.winterframework.firefrog.game.service.IGameOrderDrawService;
import com.winterframework.firefrog.game.service.IGameOrderService;
import com.winterframework.firefrog.game.service.IGameOrderWinService;
import com.winterframework.firefrog.game.service.IGameSlipService;
import com.winterframework.firefrog.game.service.IGameWarnNewService;
import com.winterframework.firefrog.game.service.IGameWarnUserService;
import com.winterframework.firefrog.game.service.utils.ParamsParserUtil;
import com.winterframework.firefrog.game.web.dto.GameRiskReportRequest;
import com.winterframework.firefrog.game.web.dto.GameRiskResponse;
import com.winterframework.modules.spring.exetend.PropertyConfig;
import com.winterframework.modules.web.jsonresult.Response;
import com.winterframework.modules.web.util.JsonMapper;


/**
 * 开奖服务类
 * @ClassName
 * @Description
 * @author ibm
 * 2014年10月10日
 */
@Service("gameDrawNewServiceImpl")
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class, readOnly = false)
public  class GameDrawNewServiceImpl implements IGameDrawNewService {

	private static final Logger log = LoggerFactory.getLogger(GameDrawNewServiceImpl.class);

	@Resource(name = "gameOrderDrawServiceImpl")
	private IGameOrderDrawService gameOrderDrawService;
	@Resource(name = "gameControlEventDaoImpl")
	private IGameControlEventDao gameControlEventDao;

	@Resource(name = "gameCheckDrawServiceImpl")
	private IGameCheckDrawService gameCheckDrawServiceImpl;

	@Resource(name = "gameIssueServiceImpl")
	private IGameIssueService gameIssueService; 
	@Resource(name = "gameWarnNewServiceImpl")
	private IGameWarnNewService gameWarnNewService;
	@Resource(name = "gameWarnUserServiceImpl")
	private IGameWarnUserService gameWarnUserService;

	@Resource(name = "gameDrawResultServiceImpl")
	protected IGameDrawResultService drawResultService;
	@Resource(name = "gameOrderServiceImpl")
	private IGameOrderService gameOrderService; 
	@Resource(name = "gameOrderWinServiceImpl")
	private IGameOrderWinService gameOrderWinService;
	@Resource(name = "gameSlipServiceImpl")
	private IGameSlipService gameSlipService; 
	@Resource(name = "gamePlanService")
	private ICommonGamePlanService gamePlanService;
	@Resource(name = "exportFileServiceImpl")
	private IExportFileService exportFileService;
	
	@PropertyConfig("url.gameRisk.reportIssue")
	private String createGameReportIssue;

	@PropertyConfig(value = "url.connect")
	private String serverPath;

	@Resource(name = "httpJsonClientImpl")
	private IHttpJsonClient httpClient;


	@Override
	public int doBusiness(GameContext ctx,Long lotteryId, Long issueCode) throws Exception {
		/**
		 * 1.前处理：（校验:奖期状态、开奖号码）
		 * 2.处理开奖
		 * 3.后处理
		 */
		GameIssue issue=gameIssueService.getGameIssueByLotteryAndIssue(lotteryId, issueCode);
		if(issue==null) return 0;
		doBefore(ctx,issue);
		doDraw(ctx,issue);
		doAfter(ctx,issue);
		return 1;  
	} 
	@Override
	public int doBusiness(GameContext ctx,GameControlEvent event) throws Exception {
		// 解析event.params 得到需要的业务信息
		Long lotteryId = null;
		Long issueCode = null;
		Map map = ParamsParserUtil.parse(event.getParams());
		if (map != null && map.size() > 0) {
			lotteryId = map.get("lotteryId") == null ? null : Long.valueOf(((String) map.get("lotteryId")));
			issueCode = map.get("issueCode") == null ? null : Long.valueOf(((String) map.get("issueCode")));
		}
		if(lotteryId !=null && issueCode!=null){
			return this.doBusiness(ctx,lotteryId, issueCode);
		}
		return 0;
	}
	
	/**
	 * 前处理
	 * @param issue
	 * @throws Exception
	 */
	protected void doBefore(GameContext ctx,GameIssue issue) throws Exception{
		//校验
		doCheck(ctx,issue);
	}
	/**
	 * 开奖
	 * @param issue
	 * @throws Exception
	 */
	protected void doDraw(GameContext ctx,GameIssue issue) throws Exception{ 
		/**
		 * 1.获取订单
		 * 2.逐个订单处理--判断是否中奖、处理中奖（不中奖）
		 * 3.返点
		 * 3.处理大奖
		 * 4.更新奖期状态
		 * 5.审核
		 * 6.统计用户中奖情况-反写用户警告表
		 * 7.追号处理
		 * 8.下一期是否补开奖？生成补开奖调度事件
		 */
		log.error("开始：开奖");
		Long lotteryId = issue.getLotteryid();
		Long issueCode = issue.getIssueCode(); 
		
		log.error("获取订单");
		List<GameOrder> orderList = gameOrderService.getGameOrderByIssueAndLottery(lotteryId, issueCode);
		if(orderList!=null && orderList.size()>0){ 
			boolean isWin=false;
			for(GameOrder order:orderList){
				this.gameOrderService.draw(ctx, order); 
				//追号处理？？？
			} 
			//处理大奖和审核 都是针对中奖订单
			//处理大奖--分摊奖池
			dealBigAward(ctx,issue); 
			//审核--只需处理中奖订单
			doRisk(ctx,issue); 	
			
			//统计用户中奖情况-反写用户警告表--中奖的用户才统计
			doWarnUser(ctx,issue);
		} 
		log.error("更新奖期状态");
		this.issueFinished(ctx, issue);
		
		/*List<GameOrder> orderList = gameCheckDrawServiceImpl.doBusiness(lotteryId, issueCode);
		if(orderList!=null){
			Set<Long> userIds = new HashSet<Long>();
			ProcessResult result = new ProcessResult();
			for (GameOrder order : orderList) {
				//处理状态：等待开奖
				if (order.getStatus().intValue() != OrderStatus.CANCEL.getValue()) {
					userIds.add(order.getUserid());
					//审核
					gameWarnService.doBusiness(order);
				}
				createGamePlanOrder(result, order);
			}
			//统计用户中奖情况，并入库。
			gameWarnService.countAndClear(lotteryId, issueCode, userIds);
	
			
			//生成调度任务(可能存在多个奖期）
			List<Long> issueCodeList = (List<Long>) result.getFromRetParaMap(String.valueOf(lotteryId));
			if (issueCodeList != null) {
				for (Long ret_issueCode : issueCodeList) {
					//这里不作奖期是否开奖判断，因为存在并发，添加调度任务，由调度控制并发
					gamePlanService.addMakeupOrderDrawEvent(lotteryId, ret_issueCode);
				}
			}
		}*/
	}
	/**
	 * 后处理
	 * @param issue
	 * @throws Exception
	 */
	protected void doAfter(GameContext ctx,GameIssue issue) throws Exception{
		/**
		 * 1.导出开奖奖期文件
		 * 2.生成奖期盈亏报表
		 * 3.生成审核中心奖期盈亏报表
		 * 4.生成走势图调度事件
		 */
		exportFile(ctx,issue);
		addWinsReportEvent(ctx,issue);
		//暂时不用 	createRiskWinsReport(issue);
		//生成trend事件
	}
	/**
	 * 校验
	 * @param issue
	 * @throws Exception
	 */
	protected void doCheck(GameContext ctx,GameIssue issue) throws Exception{
		//校验:奖期状态、开奖号码
		checkIssue(ctx,issue);
		String winNumber=checkWinNumber(ctx,issue);
		ctx.set("winNumber", winNumber);
	}
	
	/**
	 * 校验奖期
	 * @param issue
	 * @throws Exception
	 */
	protected void checkIssue(GameContext ctx,GameIssue issue) throws Exception{
		/**
		 * 状态：开奖号码确认 && 暂停状态：正常
		 */
		if (issue.getStatus().intValue()!=GameIssue.Status.WIN_NUMBER.getValue()) {
			String msg="获取彩种【" + issue.getLotteryid() + "】奖期期号【" + issue.getIssueCode() + "】信息并非处于开奖号码已确认状态。";
			log.error(msg);
			throw new Exception(msg);
		}
//		//判断奖期是否已暂停或已撤销
		if(issue.getPauseStatus().intValue() !=GameIssue.PauseStatus.NORMAL.getValue()){
			String msg="获取彩种【" + issue.getLotteryid() + "】奖期期号【" + issue.getIssueCode() + "】已暂停或已撤销。";
			log.error(msg);
			throw new Exception(msg);
		}
	}
	protected String checkWinNumber(GameContext ctx,GameIssue issue) throws Exception{
		Long lotteryId=issue.getLotteryid();
		Long issueCode=issue.getIssueCode();
		String winNumber = drawResultService.getnumberRecordByLotteryIdAndIssueCode(lotteryId, issueCode);
		if (StringUtils.isBlank(winNumber)) {
			String msg="彩种【" + lotteryId + "】奖期期号【" + issueCode + "】获取开奖号码失败.";
			log.error(msg);
			throw new Exception(msg);
		}
		return winNumber;
	}
	/**
	 * 导出文件
	 * @param issue
	 */
	private void exportFile(GameContext ctx,GameIssue issue){ 
		if(issue==null) return;
		Long lotteryId = issue.getLotteryid();
		Long issueCode = issue.getIssueCode();  
		log.info("导出文件【" + lotteryId + "】奖期期号【" + issueCode + "】");
		try{  
			GameIssueEntity issueEntity=VOConverter.gameIssue2GameIssueEntity(issue);
			exportFileService.exportGameSlip(issueEntity, "2");  
		}catch(Exception e){
			log.info("导出文件【" + lotteryId + "】奖期期号【" + issueCode + "】出错"+e);
		}
	}
	/**
	 * 添加奖期盈亏表生成事件
	 * @param issue
	 */
	private void addWinsReportEvent(GameContext ctx,GameIssue issue){ 
		Long lotteryId = issue.getLotteryid();
		Long issueCode = issue.getIssueCode();  
		try{
			log.info("添加奖期盈亏表生成事件（LotteryId："+lotteryId+",issueCode:"+issueCode+")");
			GameControlEvent event = new GameControlEvent();
			event.setLotteryid(lotteryId);
			event.setStartIssueCode(issueCode);
			event.setEndIssueCode(issueCode);
			event.setSaleEndTime(issue.getSaleEndTime());
			event.setSaleStartTime(issue.getSaleStartTime());
			event.setEnentType(Long.valueOf(GameControlEvent.EventType.WIN_REPORT.getValue()));
			event.setStatus(Long.valueOf(YesNo.NO.getValue()));
			event.setMessage("生成奖期盈亏报表信息。");
			event.setCreateTime(DateUtils.currentDate());
			gameControlEventDao.insert(event);  
		}catch(Exception e){
			log.info("添加奖期盈亏表生成事件（LotteryId："+lotteryId+",issueCode:"+issueCode + ")出错"+e);
		}
	}
	
	/**
	 * 生成审核中心奖期盈亏表
	 * @param issue
	 * @throws Exception
	 */
	private void createRiskWinsReport(GameContext ctx,GameIssue issue) throws Exception{
		Long lotteryId = issue.getLotteryid();
		Long issueCode = issue.getIssueCode();   
		try {
			log.info("生成审核中心奖期盈亏表（LotteryId："+lotteryId+",issueCode:"+issueCode+")"); 
			GameRiskReportRequest request = new GameRiskReportRequest();
			request.setLotteryId(lotteryId);
			request.setIssueCode(issueCode);
			httpClient.invokeHttp(serverPath + createGameReportIssue, request,
					new TypeReference<Response<GameRiskResponse>>() {
					});
		} catch (Exception e) {
			log.info("生成审核中心奖期盈亏表（LotteryId："+lotteryId+",issueCode:"+issueCode+")出错"+e);
		}
	}
	
	private void issueFinished(GameContext ctx,GameIssue issue) throws Exception{
		Date curDate=DateUtils.currentDate();
		String winNumber=(String)ctx.get("winNumber");
		issue.setStatus(Long.valueOf(GameIssuePeriodStatus.WAIT_RECONCILIATION.getValue()));
		issue.setPeriodStatus(Long.valueOf(GameIssuePeriodStatus.WAIT_ISSUE_OVER.getValue()));
		issue.setUpdateTime(curDate);
		issue.setFactionDrawTime(curDate);
		/*得到开奖号码的时候更新
		issue.setNumberRecord(winNumber);
		issue.setNumberUpdateTime(curDate);
		issue.setNumberUpdateCount(issue.getNumberUpdateCount().longValue()+1L);*/
		this.gameIssueService.save(ctx,issue);
	}
	
	/**
	 * 处理大奖
	 * @param ctx
	 * @param issue
	 * @throws Exception
	 */
	private void dealBigAward(GameContext ctx,GameIssue issue) throws Exception{
		/**
		 * 统计该奖期的所有中大奖订单（一等奖、二等奖）--通过注单明细的win_level字段判断
		 * 1.获取订单(只处理中奖订单）
		 * 2.逐一处理订单和注单，同时记录下中大奖的注单和订单的分别中奖注数和总中奖注数
		 * 3.获取奖池和系统奖金组数据
		 * 4.分摊总奖金，得到每单真实派奖金额
		 * 5.根据实际派奖金额，重新处理中大奖的注单和中奖订单的中奖金额
		 */
		if(issue==null) return;

		List<GameOrder> winOrderList=(List<GameOrder>)ctx.get("winOrderList"); 
		if(winOrderList==null) return;
		
		if(issue.getLotteryid()==99401L){
			final String seperator="_";
			log.info("双色球大奖处理 ");
			List<GameOrder> orderList=winOrderList; 
			if(orderList!=null && orderList.size()>0){
				Long countFirst=0L,countSecond=0L;
				Map<GameSlip,String> tmpSlipMap=new HashMap<GameSlip,String>();
				Map<GameOrder,String> tmpOrderMap=new HashMap<GameOrder,String>();
				log.info("逐一处理订单和注单，同时记录下中大奖的注单和订单的分别中奖注数和总中奖注数 ");
				for(GameOrder order:orderList){					
					List<GameSlip> slipList=this.gameSlipService.getByOrderId(ctx, order.getId());
					if(slipList!=null && slipList.size()>0){
						for(GameSlip slip:slipList){ 
							Map<Long,Long> countMap=this.gameSlipService.getBigAwardCount(ctx, slip);
							if(countMap!=null){ 
								countFirst+=countMap.get(1L);
								countSecond+=countMap.get(2L); 
								tmpSlipMap.put(slip,countMap.get(1L)+seperator+countMap.get(2L)); 
							}
						}
						tmpOrderMap.put(order, countFirst+seperator+countSecond);
					}else{
						throw new GameException("订单（orderId："+order.getId()+")的注单信息未找到");
					}
				}
				log.info("获取奖池和系统奖金组数据");
				String pool = issue.getAwardStruct();
				GameBonusJsonBean bonus = new GameBonusJsonBean();
				try {
					bonus = JsonMapper.nonEmptyMapper().fromJson(pool, GameBonusJsonBean.class);
				} catch (Exception e) {
					log.error("获取奖期结构异常");
					throw new Exception("获取奖期结构异常");
				}
				 
				List<GameBonusAwardJsonBean> awardList = bonus.getAwards();
				GameBonusPoolJson gameBonusPool = bonus.getGameBonusPoolJson();
				GameBonusAwardJsonBean awardMoneyFirst = null;
				GameBonusAwardJsonBean awardMoneySecond = null;
				for (GameBonusAwardJsonBean gameBonusAwardJsonBean : awardList) {
					if (gameBonusAwardJsonBean.getGameBetType().endsWith("_1")) {
						awardMoneyFirst = gameBonusAwardJsonBean;
					}
					if (gameBonusAwardJsonBean.getGameBetType().endsWith("_2")) {
						awardMoneySecond = gameBonusAwardJsonBean;
					}
				}
				
				log.info("分摊总奖金，得到每单真实派奖金额");
				Long realDistributeFirst = 0L;
				Long realDistributeSecond = 0L;
				if(countFirst>0){
					Long bigAwardPoolFirst = gameBonusPool.getActualBonus() * gameBonusPool.getDistribute1() / countFirst;
					//真实派奖金额在minAward和maxAward之间
					if (bigAwardPoolFirst > awardMoneyFirst.getMaxAward()) {
						realDistributeFirst = awardMoneyFirst.getMaxAward();
					} else if(bigAwardPoolFirst < awardMoneyFirst.getMinAward()){
						realDistributeFirst = awardMoneyFirst.getMinAward();
					} else {
						realDistributeFirst =bigAwardPoolFirst;
					}
				}
				if(countSecond>0){
					Long bigAwardPoolSecond= gameBonusPool.getActualBonus() * gameBonusPool.getDistribute2()/ countSecond;
					//真实派奖金额在minAward和maxAward之间
					if (bigAwardPoolSecond > awardMoneySecond.getMaxAward()) {
						realDistributeSecond = awardMoneySecond.getMaxAward();
					} else if(bigAwardPoolSecond < awardMoneySecond.getMinAward()){
						realDistributeSecond = awardMoneySecond.getMinAward();
					} else {
						realDistributeSecond =bigAwardPoolSecond;
					} 
				} 
				log.info("根据实际派奖金额，重新处理中大奖的注单和中奖订单的中奖金额");
				//是否中奖正常计奖中已经处理，此处仅仅更新大奖中奖金额
				if(tmpSlipMap!=null && tmpSlipMap.size()>0){
					for(GameSlip slip:tmpSlipMap.keySet()){ 
						String winCount=tmpSlipMap.get(slip); 				
						String[] count=winCount.split(seperator);
						Long winAmount=Long.valueOf(count[0])* realDistributeFirst+Long.valueOf(count[1])* realDistributeSecond;
						slip.setEvaluateWin(winAmount);
						this.gameSlipService.save(ctx, slip);
					}
				}
				if(tmpOrderMap!=null && tmpOrderMap.size()>0){
					for(GameOrder order:tmpOrderMap.keySet()){ 
						GameOrderWin orderWin=this.gameOrderWinService.getByOrderId(ctx, order.getId());
						String winCount=tmpSlipMap.get(order); 				
						String[] count=winCount.split(seperator);
						Long winAmount=Long.valueOf(count[0])* realDistributeFirst+Long.valueOf(count[1])* realDistributeSecond;
						orderWin.setCountWin(winAmount);
						
						this.gameOrderWinService.save(ctx, orderWin);
					}
				}
			} 
		}
	}
	
	/**
	 * 处理风险审核
	 * @param ctx
	 * @param issue
	 * @throws Exception
	 */
	private void doRisk(GameContext ctx,GameIssue issue) throws Exception{
		if(issue==null) return;

		List<GameOrder> winOrderList=(List<GameOrder>)ctx.get("winOrderList"); 
		if(winOrderList==null) return;
		List<GameOrder> orderList=winOrderList;
		if(orderList!=null && orderList.size()>0){
			Long maxSlipWinAmout=0L;	//最大注单中奖金额（不到单注）
			for(GameOrder order:orderList){  
				this.gameWarnNewService.doBusiness(ctx, order); 
			}
		}
		
	}
	
	private void doWarnUser(GameContext ctx,GameIssue issue) throws Exception{
		if(issue==null) return;
		Long lotteryId=issue.getLotteryid();
		Long issueCode=issue.getIssueCode();
		List<Long> winUserIdList=(List<Long>)ctx.get("winUserIdList");  
		if(winUserIdList!=null && winUserIdList.size()>0){
			for (Long userId : winUserIdList) {
				GameWarnUser warnUser=this.gameWarnUserService.getByLotteryIssueUserId(ctx,userId,lotteryId,issueCode);
				GameWarnUser lastWarnUser=this.gameWarnUserService.getLastWarnUser(ctx, lotteryId, issueCode, userId);
				if(warnUser==null){
					warnUser = new GameWarnUser();
					warnUser.setLotteryid(lotteryId);
					warnUser.setIssueCode(issueCode);
					warnUser.setUserid(userId);
				} 
				/*warnUser.setType(1L);
				warnUser.setContinuousWinsIssue(lastWarnUser.getContinuousWinsIssue()+1L);
				warnUser.setContinuousWinsTimes(lastWarnUser.getContinuousWinsTimes()+);
				warnUser.setMaxslipWins(warnServiceBean.getMaxSlipWins());
				warnUser.setWinsRatio(warnServiceBean.getIssueUserWinsRatio());
				warnUser.setTotalWins(warnServiceBean.getIssueUserCountWin());
				warnUser.setBetTotamount(warnServiceBean.getIssueUserTotamount()); 
				
				if (null != warnUserDB) {
					GameWarnUser warnUser = packageGameWarnUser(warnServiceBean);
					warnUser.setId(warnUserDB.getId());
					warnUser.setUserAccount(warnUserDB.getUserAccount());
					if (warnServiceBean.getIsWarn() == 1L || warnUserDB.getType() == 1L) {
						warnUser.setType(1L);
					} else {
						warnUser.setType(0L);
					}
					gameWarnUserDao.update(warnUser);
				} else {
					insertWarnUser(warnServiceBean);
				}
				
				
				GameWarnServiceBean warnServiceBean = getCacheWarnServiceBean(lotteryId, issueCode, userId);
				//加上前一期数据  连续中奖期数
				if (warnServiceBean.getContinuousWinsIssue() != null && warnServiceBean.getContinuousWinsIssue() != 0L) {
					GameWarnUser warnUser = gameWarnUserDao.queryLastWarnUser(userId, lotteryId, issueCode);
					if (warnUser == null) {
						warnUser = new GameWarnUser();
					}
					warnServiceBean.setContinuousWinsIssue(warnUser.getContinuousWinsIssue() + 1L); 
					warnServiceBean.setContinuousWinsTimes(warnUser.getContinuousWinsTimes()+ warnServiceBean.getContinuousWinsTimes());
				}
				//插入风控用户
				saveOrUpdateWarnUser(warnServiceBean);*/
			}
		}
		
	}
}
