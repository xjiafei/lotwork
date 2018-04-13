package com.winterframework.firefrog.game.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
import com.winterframework.firefrog.common.util.ProcessResult;
import com.winterframework.firefrog.game.dao.IGameControlEventDao;
import com.winterframework.firefrog.game.dao.IGameDrawResultDao;
import com.winterframework.firefrog.game.dao.IGameIssueDao;
import com.winterframework.firefrog.game.dao.IGameOrderWinDao;
import com.winterframework.firefrog.game.dao.IGameSlipDao;
import com.winterframework.firefrog.game.dao.vo.GameBonusAwardJsonBean;
import com.winterframework.firefrog.game.dao.vo.GameBonusJsonBean;
import com.winterframework.firefrog.game.dao.vo.GameBonusPoolJson;
import com.winterframework.firefrog.game.dao.vo.GameControlEvent;
import com.winterframework.firefrog.game.dao.vo.GameDrawResult;
import com.winterframework.firefrog.game.dao.vo.GameIssue;
import com.winterframework.firefrog.game.dao.vo.GameOrder;
import com.winterframework.firefrog.game.dao.vo.GameOrderWin;
import com.winterframework.firefrog.game.dao.vo.GameSlip;
import com.winterframework.firefrog.game.dao.vo.GameWarnIssueLog;
import com.winterframework.firefrog.game.dao.vo.VOConverter;
import com.winterframework.firefrog.game.entity.GameIssueEntity;
import com.winterframework.firefrog.game.entity.GameIssuePeriodStatus;
import com.winterframework.firefrog.game.entity.GameIssueStatus;
import com.winterframework.firefrog.game.entity.GameOrder.OrderStatus;
import com.winterframework.firefrog.game.entity.MoneyMode;
import com.winterframework.firefrog.game.service.ICommonGamePlanService;
import com.winterframework.firefrog.game.service.IExportFileService;
import com.winterframework.firefrog.game.service.IGameCheckDrawService;
import com.winterframework.firefrog.game.service.IGameControlEventService;
import com.winterframework.firefrog.game.service.IGameDrawResultService;
import com.winterframework.firefrog.game.service.IGameDrawService;
import com.winterframework.firefrog.game.service.IGameFundRiskService;
import com.winterframework.firefrog.game.service.IGameIssueService;
import com.winterframework.firefrog.game.service.IGameOrderDrawService;
import com.winterframework.firefrog.game.service.IGameOrderFundService;
import com.winterframework.firefrog.game.service.IGameOrderService;
import com.winterframework.firefrog.game.service.IGameOrderWinFundService;
import com.winterframework.firefrog.game.service.IGameReturnPointFundService;
import com.winterframework.firefrog.game.service.IGameWarnService;
import com.winterframework.firefrog.game.service.bean.BigAwardCacheBean;
import com.winterframework.firefrog.game.service.revocation.IGameRevocationOrderService;
import com.winterframework.firefrog.game.service.utils.ParamsParserUtil;
import com.winterframework.firefrog.game.web.dto.GameRiskReportRequest;
import com.winterframework.firefrog.game.web.dto.GameRiskResponse;
import com.winterframework.firefrog.game.web.dto.TORiskDTO;
import com.winterframework.modules.spring.exetend.PropertyConfig;
import com.winterframework.modules.web.jsonresult.Response;
import com.winterframework.modules.web.util.JsonMapper;

/** 
* @ClassName: GameDrawServiceImpl 
* @Description: 各种开奖方式的超类
* @author 你的名字 
* @date 2014-5-7 下午3:02:41 
*  
*/
@Service("sscGameDrawServiceImpl")
@Transactional(rollbackFor = Exception.class)
public abstract class GameDrawServiceImpl implements IGameDrawService {

	private static final Logger log = LoggerFactory.getLogger(GameDrawServiceImpl.class);
	 
	@Resource(name = "gameControlEventDaoImpl")
	private IGameControlEventDao gameControlEventDao;

	@Resource(name = "gameCheckDrawServiceImpl")
	private IGameCheckDrawService gameCheckDrawServiceImpl;

	@Resource(name = "gameIssueServiceImpl")
	protected IGameIssueService gameIssueService;

	@Resource(name = "gameWarnServiceImpl")
	private IGameWarnService gameWarnService;

	@Resource(name = "gameDrawResultServiceImpl")
	protected IGameDrawResultService drawResultService;
	@Resource(name = "gameOrderServiceImpl")
	protected IGameOrderService gameOrderService;
	@Resource(name = "gamePlanService")
	protected ICommonGamePlanService gamePlanService;
	@Resource(name = "exportFileServiceImpl")
	private IExportFileService exportFileService;
	@Resource(name = "gameSlipDaoImpl")
	private IGameSlipDao gameSlipDao;
	@Resource(name = "gameOrderWinDaoImpl")
	private IGameOrderWinDao orderWinDao;
	@Resource(name = "gameOrderDrawServiceImpl")
	private IGameOrderDrawService gameOrderDrawService;
	@Resource(name = "gameRevocationOrderStatusMachineImpl")
	private IGameRevocationOrderService gameRevocationOrderStatusMachine;  
	@Resource(name = "gameFundRiskServiceImpl")
	private IGameFundRiskService gameFundRiskService;
	@Resource(name = "gameOrderFundServcieImpl")
	private IGameOrderFundService gameOrderFundServcie; 
	@Resource(name = "gameReturnPointFundServcieImpl")
	private IGameReturnPointFundService gameReturnPointFundServcie;
	@Resource(name = "gameOrderWinFundServcieImpl")
	private IGameOrderWinFundService gameOrderWinFundServcie;
	@Resource(name = "gameControlEventServiceImpl")
	private IGameControlEventService gameControlEventService; 
	
	@PropertyConfig("url.gameRisk.reportIssue")
	private String createGameReportIssue;

	@PropertyConfig(value = "url.connect")
	private String serverPath;

	@Resource(name = "httpJsonClientImpl")
	private IHttpJsonClient httpClient;

	@Resource(name = "gameIssueDaoImpl")
	private IGameIssueDao gameIssueDao;
	
	@Resource(name = "gameDrawResultDaoImpl")
	private IGameDrawResultDao gameDrawResultDao;
	/**
	* Title: doBusiness
	* Description:开奖处理服务类 1:检查奖期状态 2：计奖  3：审核  4：追号 
	* @param lotteryId
	* @param issueCode
	* @throws Exception 
	* @see com.winterframework.firefrog.game.service.IGameDrawService#doBusiness(java.lang.Long, java.lang.Long) 
	*/
	@Override
	public void doBusiness(Long lotteryId, Long issueCode) throws Exception {
		GameContext ctx=new GameContext(); 
		getBizInterface().doBusiness(ctx,lotteryId, issueCode); 
		try{	//资金调用不更新event.status=3（失败）
			doFundRequest(ctx,lotteryId, issueCode);
		}catch(Exception e){ 
			log.error("fund request failed after drawing.lotteryId="+lotteryId+";issueCode="+issueCode,e); 
		}
	}
 

	public void doBusiness(Long lotteryId, Long issueCode, Long orderId) throws Exception {
		log.info("补开开始时时彩【" + lotteryId + "】奖期期号【" + issueCode + "】正常开奖流程。");
		GameIssue  issue = gameIssueService.getByLotteryIdAndIssueCode(lotteryId, issueCode);
		if (!checkIssueStatus(issue)) {
			return;
		}
		//计奖
		GameOrder gameOrder = gameCheckDrawServiceImpl.doBusiness(lotteryId, issueCode, orderId);
		//审核
		gameWarnService.doBusiness(gameOrder,false);
		//统计用户中奖情况，并入库。
		Set<Long> userIds = new HashSet<Long>();
		userIds.add(gameOrder.getUserid());
		gameWarnService.countAndClear(lotteryId, issueCode, userIds);
		//追号
		ProcessResult result = new ProcessResult();
		createGamePlanOrder(result, gameOrder);
		log.info("补开结束时时彩【" + lotteryId + "】奖期期号【" + issueCode + "】正常开奖流程。");
	}

	@Override 
	public void doBusiness(GameControlEvent event) throws Exception {
		// 解析event.params 得到需要的业务信息
		Long lotteryId = null;
		Long issueCode = null;
		Map map = ParamsParserUtil.parse(event.getParams());
		if (map != null && map.size() > 0) {
			lotteryId = map.get("lotteryId") == null ? -1L : Long.valueOf(((String) map.get("lotteryId")));
			issueCode = map.get("issueCode") == null ? -1L : Long.valueOf(((String) map.get("issueCode")));
		}
		this.doBusiness(lotteryId, issueCode);
	}
	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
	public void doBusiness(GameContext ctx, Long lotteryId, Long issueCode) throws Exception { 
		doDraw(ctx, lotteryId, issueCode, false); 
	}
	protected void doDraw(GameContext ctx, Long lotteryId, Long issueCode,
			boolean isRedraw) throws Exception {
		log.info("正常开始时时彩【" + lotteryId + "】奖期期号【" + issueCode + "】正常开奖流程。");
		GameIssue  issue = gameIssueService.getByLotteryIdAndIssueCode(lotteryId, issueCode);
		//校验奖期状态是否正确
		if (!checkIssueStatus(issue)) {
			return;
		}
		//校验开奖号码是否正确
		if (!checkDrawResultNumber(lotteryId, issueCode)) {
			return;
		} 
		//6.获取开奖号码
		String record = drawResultService.getnumberRecordByLotteryIdAndIssueCode(lotteryId, issueCode);
		if (StringUtils.isBlank(record)) {
			log.error("时时彩【" + lotteryId + "】奖期期号【" + issueCode + "】获取开奖号码失败.");
			throw new RuntimeException("时时彩【" + lotteryId + "】奖期期号【" + issueCode + "】获取开奖号码失败.");
		} 
		//7.获取所有订单
		List<GameOrder> orderList = this.getDrawOrders(lotteryId, issueCode);
		boolean hasOrder=false;
		//本次业务处理：排除系统撤销单 和 已开奖单    
		if (orderList != null && orderList.size()>0) { 
			hasOrder=true;
			log.debug("进行时时彩【" + lotteryId + "】奖期期号【" + issueCode + "】的订单处理。");
			BigAwardCacheBean bigAward = new BigAwardCacheBean(); 
			ProcessResult result=new ProcessResult();
			Set<Long> userIds = new HashSet<Long>(); 
			Set<Long> warnOrderIdSet=new HashSet<Long>();
			Set<Long> realOrderIdSet=new HashSet<Long>();
			for(GameOrder order:orderList){
				/**
				 * 撤销的订单（不管系统撤销还是管理员撤销)，开奖不处理，但是要追号
				 * 如果重开，则先撤销订单
				 */
				if(order.getStatus().intValue()!=GameOrder.Status.CANCEL.getValue()){ 
					//非重开业务 且订单状态：非等待开奖状态，则不做开奖处理
					if(!isRedraw && order.getStatus().intValue() != OrderStatus.WAITING.getValue()) continue; 
					boolean isWarnPre=order.getStatus().intValue()==GameOrder.Status.EXCEP.getValue();
					if(isWarnPre){
						warnOrderIdSet.add(order.getId());
					}
					//开奖
					try {
						order.setWebIssueCode(issue.getWebIssueCode());
						//20140213 add 返回信息，用于派奖用。					
						this.gameCheckDrawServiceImpl.doBusiness(ctx, record,order,  bigAward);
					} catch (Exception e) {
						//  Auto-generated catch block
						log.error("彩种【 "+ lotteryId + "】奖期【" + issueCode + "】 订单【" + order.getId() + "记奖失败.", e);
						throw new RuntimeException("彩种【 "+ lotteryId + "】奖期【" + issueCode + "】 订单【" + order.getId() + "记奖失败." + e.getMessage());
					}	  
					realOrderIdSet.add(order.getId());
				} 
			} 
			try {
				//处理大奖
				this.dealBigAward(issue, bigAward);
			} catch (Exception e) {
				log.error("【 + lotteryId + 】【 + issueCode + 】 " + "处理大奖失败.", e);
				throw new RuntimeException("【 + lotteryId + 】【 + issueCode + 】 " + "处理大奖失败." + e.getMessage());
			}
			//处理大奖之后才进行审核之类的处理
			for(GameOrder order:orderList){
				if(realOrderIdSet.contains(order.getId())){					
					userIds.add(order.getUserid());
					//重开是异常的订单，复原状态，审核时用于识别重开奖前的订单类型
					if(warnOrderIdSet.contains(order.getId())){
						order.setStatus(GameOrder.Status.EXCEP.getValue());
					}
					//审核
					order.setWebIssueCode(issue.getWebIssueCode());
					gameWarnService.doBusiness(ctx,order,isRedraw);
				}
				//追号 
				createGamePlanOrder(ctx,result, order); 
			}
			//统计用户中奖情况，并入库。
			//gameWarnService.countAndClear(lotteryId, issueCode, userIds);
			
			Set<Long> warnUserIdSet=(Set<Long>)ctx.get("WARN_USER_ID_SET");
			gameWarnService.countAndClear(lotteryId, issueCode, warnUserIdSet);
			
			//生成调度任务(可能存在多个奖期）
			List<Long> issueCodeList = (List<Long>) result.getFromRetParaMap(String.valueOf(lotteryId));
			if (issueCodeList != null) {
				for (Long ret_issueCode : issueCodeList) {
					//这里不作奖期是否开奖判断，因为存在并发，添加调度任务，由调度控制并发
					gamePlanService.addMakeupOrderDrawEvent(lotteryId, ret_issueCode);
				}
			}
		}else{
			log.debug("3.1 时时彩【" + lotteryId + "】奖期期号【" + issueCode + "】无订单信息，");
		}
		String preNumberRecord=issue.getNumberRecord();
		//更新奖期状态：开奖完成
		updateIssueDrawed(lotteryId, issueCode, issue, ctx, record); 
				
		log.info("exportSlip event:"+this.getBizInterface().getClass().getSimpleName());
		//导出订单信息
		exportSlip(issue,hasOrder);    
	
		//添加盈亏报表信息。
		addCreateWinReportEvent(issue);
		
		//生成走势图任务
		if(isNeedTrendTask(preNumberRecord,record)){
			addTrendEvent(issue);
		}
				 
		/*try {
			GameRiskReportRequest request = new GameRiskReportRequest();
			request.setLotteryId(lotteryId);
			request.setIssueCode(issueCode);
			httpClient.invokeHttp(serverPath + createGameReportIssue, request,
					new TypeReference<Response<GameRiskResponse>>() {
					});
		} catch (Exception e) {
			log.error("生成报表失败", e);
		}*/
		
		/*//资金调用 
		try{
			this.fundRequestBatches(ctx);
		}catch(Exception ee){
			log.error("资金调用存在异常",ee); 
		}*/
		log.info("正常结束时时彩【" + lotteryId + "】奖期期号【" + issueCode + "】正常开奖流程。");
	}
	//@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
	public void doBusiness_new_bk(Long lotteryId, Long issueCode, boolean isRedraw)
			throws Exception {  
		log.info("正常开始时时彩【" + lotteryId + "】奖期期号【" + issueCode + "】正常开奖流程。");
		GameIssue  issue = gameIssueService.getByLotteryIdAndIssueCode(lotteryId, issueCode);
		//校验奖期状态是否正确
		if (!checkIssueStatus(issue)) {
			return;
		}
		//校验开奖号码是否正确
		if (!checkDrawResultNumber(lotteryId, issueCode)) {
			return;
		}
		GameContext ctx=new GameContext();
		//6.获取开奖号码
		String record = drawResultService.getnumberRecordByLotteryIdAndIssueCode(lotteryId, issueCode);
		if (StringUtils.isBlank(record)) {
			log.error("时时彩【" + lotteryId + "】奖期期号【" + issueCode + "】获取开奖号码失败.");
			throw new RuntimeException("时时彩【" + lotteryId + "】奖期期号【" + issueCode + "】获取开奖号码失败.");
		} 
		//7.获取所有订单
		List<GameOrder> orderList = this.getDrawOrders(lotteryId, issueCode);
		boolean hasOrder=false;
		//本次业务处理：排除系统撤销单 和 已开奖单    
		if (orderList != null && orderList.size()>0) { 
			hasOrder=true;
			log.debug("进行时时彩【" + lotteryId + "】奖期期号【" + issueCode + "】的订单处理。");
			BigAwardCacheBean bigAward = new BigAwardCacheBean(); 
			ProcessResult result=new ProcessResult();
			Set<Long> userIds = new HashSet<Long>(); 
			Set<Long> warnOrderIdSet=new HashSet<Long>();
			Set<Long> realOrderIdSet=new HashSet<Long>();
			for(GameOrder order:orderList){
				/**
				 * 撤销的订单（不管系统撤销还是管理员撤销)，开奖不处理，但是要追号
				 * 如果重开，则先撤销订单
				 */
				if(order.getStatus().intValue()!=GameOrder.Status.CANCEL.getValue()){ 
					//非重开业务 且订单状态：非等待开奖状态，则不做开奖处理
					if(!isRedraw && order.getStatus().intValue() != OrderStatus.WAITING.getValue()) continue; 
					boolean isWarnPre=order.getStatus().intValue()==GameOrder.Status.EXCEP.getValue();
					if(isWarnPre){
						warnOrderIdSet.add(order.getId());
					}
					//开奖
					try {
						order.setWebIssueCode(issue.getWebIssueCode());
						//20140213 add 返回信息，用于派奖用。					
						this.gameCheckDrawServiceImpl.doBusiness(ctx, record,order,  bigAward);
					} catch (Exception e) {
						//  Auto-generated catch block
						log.error("彩种【 "+ lotteryId + "】奖期【" + issueCode + "】 订单【" + order.getId() + "记奖失败.", e);
						throw new RuntimeException("彩种【 "+ lotteryId + "】奖期【" + issueCode + "】 订单【" + order.getId() + "记奖失败." + e.getMessage());
					}	  
					realOrderIdSet.add(order.getId());
				} 
			} 
			try {
				//处理大奖
				this.dealBigAward(issue, bigAward);
			} catch (Exception e) {
				log.error("【 + lotteryId + 】【 + issueCode + 】 " + "处理大奖失败.", e);
				throw new RuntimeException("【 + lotteryId + 】【 + issueCode + 】 " + "处理大奖失败." + e.getMessage());
			}
			//处理大奖之后才进行审核之类的处理
			for(GameOrder order:orderList){
				if(realOrderIdSet.contains(order.getId())){					
					userIds.add(order.getUserid());
					//重开是异常的订单，复原状态，审核时用于识别重开奖前的订单类型
					if(warnOrderIdSet.contains(order.getId())){
						order.setStatus(GameOrder.Status.EXCEP.getValue());
					}
					//审核
					gameWarnService.doBusiness(ctx,order,isRedraw);
				}
				//追号 
				createGamePlanOrder(ctx,result, order); 
			}
			//统计用户中奖情况，并入库。
			//gameWarnService.countAndClear(lotteryId, issueCode, userIds);
	
			
			//生成调度任务(可能存在多个奖期）
			List<Long> issueCodeList = (List<Long>) result.getFromRetParaMap(String.valueOf(lotteryId));
			if (issueCodeList != null) {
				for (Long ret_issueCode : issueCodeList) {
					//这里不作奖期是否开奖判断，因为存在并发，添加调度任务，由调度控制并发
					gamePlanService.addMakeupOrderDrawEvent(lotteryId, ret_issueCode);
				}
			}
		}else{
			log.debug("3.1 时时彩【" + lotteryId + "】奖期期号【" + issueCode + "】无订单信息，");
		}
		//更新奖期状态：开奖完成
		updateIssueDrawed(lotteryId, issueCode, issue, ctx, record); 
				
		//导出订单信息
		exportSlip(issue,hasOrder);    
	
		//生成走势图任务
		addTrendEvent(issue);
				 
		/*try {
			GameRiskReportRequest request = new GameRiskReportRequest();
			request.setLotteryId(lotteryId);
			request.setIssueCode(issueCode);
			httpClient.invokeHttp(serverPath + createGameReportIssue, request,
					new TypeReference<Response<GameRiskResponse>>() {
					});
		} catch (Exception e) {
			log.error("生成报表失败", e);
		}*/
		
		/*//资金调用 
		try{
			this.fundRequestBatches(ctx);
		}catch(Exception ee){
			log.error("资金调用存在异常",ee); 
		}*/
		log.info("正常结束时时彩【" + lotteryId + "】奖期期号【" + issueCode + "】正常开奖流程。"); 
	}

	protected void updateIssueDrawed(Long lotteryId, Long issueCode,
			GameIssue issue, GameContext ctx, String record) {
		//更新奖期信息 
		//导出奖期信息
		//生成走势图调度任务 
		//生成奖期盈亏报表调度任务
		try {  
			issue.setStatus(Long.valueOf(GameIssuePeriodStatus.WAIT_RECONCILIATION.getValue()));
			issue.setPeriodStatus(Long.valueOf(GameIssuePeriodStatus.WAIT_ISSUE_OVER.getValue()));
			Date now = DateUtils.currentDate();
			issue.setUpdateTime(now);
			issue.setFactionDrawTime(now);
			/*得到开奖号码的时候更新
			issue.setNumberRecord(record);
			issue.setNumberUpdateTime(now);
			issue.setNumberUpdateCount(issue.getNumberUpdateCount().longValue() + 1L);*/
			gameIssueService.save(ctx, issue);  
		} catch (Exception e) {
			log.error("时时彩【" + lotteryId + "】奖期期号【" + issueCode + "】派奖完成失败.", e);
			throw new RuntimeException("时时彩【" + lotteryId + "】奖期期号【" + issueCode + "】派奖完成失败.");
		}
	}  
	private void fundRequestBatches(GameContext ctx) throws Exception{
		Map<String,List<TORiskDTO>> dtoMap=(Map<String,List<TORiskDTO>>)ctx.get("RISKDTOLIST");
		if(dtoMap!=null && dtoMap.size()>0){
			try{
				this.gameFundRiskService.fundRequestAward(ctx,dtoMap);
			}catch(Exception e){
				log.error("award fund request failed.dtoMap:"+dtoMap);
			}
			try{
				this.gameFundRiskService.fundRequest(ctx,dtoMap);
			}catch(Exception e){
				log.error("common fund request failed.dtoMap:"+dtoMap);
			}
			dtoMap=null;
			ctx.set("RISKDTOLIST", null);
		}		
	}  
	private void fundRequestBatches_bk(GameContext ctx) throws Exception{
		List<TORiskDTO> dtoList=(List<TORiskDTO>)ctx.get("RISKDTOLIST");
		if(dtoList!=null){  
			this.gameFundRiskService.fundRequestBatches(ctx, dtoList,0);
			dtoList=null;	//一次请求处理完之后，清空大对象
		}
	} 
	protected List<GameOrder> getDrawOrders(Long lotteryId, Long issueCode)
			throws Exception {  
		return gameOrderService.getGameOrderByIssueAndLottery(lotteryId, issueCode); 
	}
	protected void addTrendEvent(GameIssue issue){
		try { 
			//生成生成trend事件
			gameControlEventService.addGameTrendChartRegenerateEvent(issue);
		} catch (Exception e) {
			log.error("生成生成trend事件失败", e);
		}
	} 
	protected void addCreateWinReportEvent(GameIssue issue){
		try {  
			gameControlEventService.addCreateWinReportEvent(issue);
		} catch (Exception e) {
			log.error("生成奖期盈亏报表事件失败", e);
		}
	} 
	//@Override
	public void doBusiness_bk(Long lotteryId, Long issueCode, boolean isRedraw)
			throws Exception { 
		GameIssue  issue = gameIssueService.getByLotteryIdAndIssueCode(lotteryId, issueCode);
		//校验奖期状态是否正确
		if (!checkIssueStatus(issue)) {
			return;
		}
		//校验开奖号码是否正确
		if (!checkDrawResultNumber(lotteryId, issueCode)) {
			return;
		}
		GameContext ctx=new GameContext();
		//6.获取开奖号码
		String record = drawResultService.getnumberRecordByLotteryIdAndIssueCode(lotteryId, issueCode);
		if (StringUtils.isBlank(record)) {
			log.error("时时彩【" + lotteryId + "】奖期期号【" + issueCode + "】获取开奖号码失败.");
			throw new RuntimeException("时时彩【" + lotteryId + "】奖期期号【" + issueCode + "】获取开奖号码失败.");
		} 
		//7.获取所有订单
		List<GameOrder> orderList = gameOrderService.getGameOrderByIssueAndLottery(lotteryId, issueCode);
		boolean hasOrder=false;
		//本次业务处理：排除系统撤销单 和 已开奖单    
		if (orderList != null && orderList.size()>0) { 
			hasOrder=true;
			log.debug("进行时时彩【" + lotteryId + "】奖期期号【" + issueCode + "】的订单处理。");
			BigAwardCacheBean bigAward = new BigAwardCacheBean(); 
			ProcessResult result=new ProcessResult();
			Set<Long> userIds = new HashSet<Long>(); 
			Set<Long> warnOrderIdSet=new HashSet<Long>();
			Set<Long> realOrderIdSet=new HashSet<Long>();
			for(GameOrder order:orderList){
				/**
				 * 撤销的订单（不管系统撤销还是管理员撤销)，开奖不处理，但是要追号
				 * 如果重开，则先撤销订单
				 */
				if(order.getStatus().intValue()!=GameOrder.Status.CANCEL.getValue()){ 
					//非重开业务 且订单状态：非等待开奖状态，则不做开奖处理
					if(!isRedraw && order.getStatus().intValue() != OrderStatus.WAITING.getValue()) continue; 
					boolean isWarnPre=order.getStatus().intValue()==GameOrder.Status.EXCEP.getValue();
					if(isWarnPre){
						warnOrderIdSet.add(order.getId());
					}
					//开奖
					try {
						order.setWebIssueCode(issue.getWebIssueCode());
						//20140213 add 返回信息，用于派奖用。					
						this.gameCheckDrawServiceImpl.doBusiness(ctx, record,order,  bigAward);
					} catch (Exception e) {
						//  Auto-generated catch block
						log.error("彩种【 "+ lotteryId + "】奖期【" + issueCode + "】 订单【" + order.getId() + "记奖失败.", e);
						throw new RuntimeException("彩种【 "+ lotteryId + "】奖期【" + issueCode + "】 订单【" + order.getId() + "记奖失败." + e.getMessage());
					}	  
					realOrderIdSet.add(order.getId());
				} 
			} 
			try {
				//处理大奖
				this.dealBigAward(issue, bigAward);
			} catch (Exception e) {
				log.error("【 + lotteryId + 】【 + issueCode + 】 " + "处理大奖失败.", e);
				throw new RuntimeException("【 + lotteryId + 】【 + issueCode + 】 " + "处理大奖失败." + e.getMessage());
			}
			//处理大奖之后才进行审核之类的处理
			for(GameOrder order:orderList){
				if(realOrderIdSet.contains(order.getId())){					
					userIds.add(order.getUserid());
					//重开是异常的订单，复原状态，审核时用于识别重开奖前的订单类型
					if(warnOrderIdSet.contains(order.getId())){
						order.setStatus(GameOrder.Status.EXCEP.getValue());
					}
					//审核
					gameWarnService.doBusiness(order,isRedraw);
				}
				//追号 
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
		}else{
			log.debug("3.1 时时彩【" + lotteryId + "】奖期期号【" + issueCode + "】无订单信息，");
		}
		//更新奖期状态：开奖完成
		updateIssueDrawed(lotteryId, issueCode, issue, ctx, record);
		
		//导出订单信息
		exportSlip(issue,hasOrder);   
		
		//添加盈亏报表信息。
		addCreateWinReportEvent(issue);
		
		addTrendEvent(issue);
				 
		try {
			GameRiskReportRequest request = new GameRiskReportRequest();
			request.setLotteryId(lotteryId);
			request.setIssueCode(issueCode);
			httpClient.invokeHttp(serverPath + createGameReportIssue, request,
					new TypeReference<Response<GameRiskResponse>>() {
					});
		} catch (Exception e) {
			log.error("生成报表失败", e);
		}
		log.info("正常结束时时彩【" + lotteryId + "】奖期期号【" + issueCode + "】正常开奖流程。");
	}

	protected void exportSlip(GameIssue issue,boolean hasOrder) {
		try{
			GameIssueEntity issueEntity=VOConverter.gameIssue2GameIssueEntity(issue);
			exportFileService.exportGameSlip(issueEntity, "2"); 
		}catch(Exception e){
			log.error("开奖完成后导出订单信息出错:", e);
		} 
	}
	
	abstract void createGamePlanOrder(ProcessResult result, GameOrder gameOrder) throws Exception;
	abstract void createGamePlanOrder(GameContext ctx,ProcessResult result, GameOrder gameOrder) throws Exception;

	/** 
	* @Title: checkIssueStatus 
	* @Description: 校验奖期状态是否正确
	* @param lotteryId
	* @param issueCode
	* @return true:奖期正确，false：奖期错误
	* @throws Exception
	*/
	abstract boolean checkIssueStatus(GameIssue entity) throws Exception;

	/** 
	* @Title: checkIssueStatus 
	* @Description: 校验奖期状态是否正确
	* @param lotteryId
	* @param issueCode
	* @return true:奖期正确，false：奖期错误
	* @throws Exception
	*/
	//abstract boolean checkIssueStatus_bk(GameIssueEntity entity) throws Exception;
	/** 
	* @Title: checkDrawResultNumber 
	* @Description: 校验开奖号码
	* @param lotteryId
	* @param issueCode
	* @throws Exception
	*/
	boolean checkDrawResultNumber(Long lotteryId, Long issueCode) throws Exception {
		//6.获取开奖号码
		String record = drawResultService.getnumberRecordByLotteryIdAndIssueCode(lotteryId, issueCode);
		if (StringUtils.isBlank(record)) {
			log.error("时时彩【" + lotteryId + "】奖期期号【" + issueCode + "】获取开奖号码失败.");
			throw new RuntimeException("时时彩【" + lotteryId + "】奖期期号【" + issueCode + "】获取开奖号码失败.");
		}
		return true;
	};
	/** 
	* @Title: dealBigAward 
	* @Description: 处理大奖
	* @param lotteryId
	* @param issueCode
	* @param bigAward
	* @throws Exception
	*/
	private void dealBigAward(GameIssue gameIssue, BigAwardCacheBean bigAward) throws Exception {
		if (gameIssue.getLotteryid().longValue() == 99401L && (bigAward.getBigOne() != 0 || bigAward.getBigTwo() != 0)) {
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
			List<GameSlip> bigAwardSlips = bigAward.getSlips();
			Map<Long,Long>  totalWinAmountMap=new HashMap<Long,Long>();
			for (GameSlip gameSlip : bigAwardSlips) {
				if(gameSlip.getEvaluateWin() == null){
					gameSlip.setEvaluateWin(0L);
				}
				gameSlip.setEvaluateWin(gameSlip.getEvaluateWin() + gameSlip.getAwardOne() * bigAwardOneTure);
				gameSlip.setEvaluateWin(gameSlip.getEvaluateWin() + gameSlip.getAwardTwo() * bigAwardTwoTure);
				gameSlip.setEvaluateWin(gameSlip.getEvaluateWin()/(gameSlip.getMoneyMode().longValue()==MoneyMode.JIAO.getValue()?10:(gameSlip.getMoneyMode().longValue()==MoneyMode.FEN.getValue()?100:1)));
				Long winAmount=totalWinAmountMap.get(gameSlip.getOrderid());
				winAmount=winAmount==null?0L:winAmount; 
				totalWinAmountMap.put(gameSlip.getOrderid(), winAmount+gameSlip.getEvaluateWin()); 
				gameSlipDao.update(gameSlip);
			}
			List<GameOrder> bigAwardOrders = bigAward.getOrders();
			for (GameOrder gameOrder : bigAwardOrders) {
				if(gameOrder.getTotalWin() == null){
					gameOrder.setTotalWin(0L);
				}
				Long winAmount=totalWinAmountMap.get(gameOrder.getId());
				winAmount=winAmount==null?0L:winAmount; 
				gameOrder.setTotalWin(gameOrder.getTotalWin() + winAmount);
				gameOrderService.updateGameOrder(gameOrder);
			}
			List<GameOrderWin> bigAwardOrderWins = bigAward.getOrderWins();
			for (GameOrderWin gameOrderWin : bigAwardOrderWins) {
				if(gameOrderWin.getCountWin() == null){
					gameOrderWin.setCountWin(0L);
				}
				Long winAmount=totalWinAmountMap.get(gameOrderWin.getOrderid());
				winAmount=winAmount==null?0L:winAmount; 
				gameOrderWin.setCountWin(gameOrderWin.getCountWin() + winAmount);;
				orderWinDao.update(gameOrderWin);
			}
			totalWinAmountMap=null;
		}
	}
	 
	/** 
	 * @return	具体业务类接口
	 */
	protected abstract IGameDrawService getBizInterface();
	
	/**
	 * 是否需要生成走势图任务
	 * @return
	 */
	protected boolean isNeedTrendTask(String preNumberRecord,String numberRecord){
		return false;
	}
	
	//@Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
	public void doFundRequest(GameContext ctx,Long lotteryId, Long issueCode) throws Exception {   
		Map<String,List<TORiskDTO>> dtoMap=(Map<String,List<TORiskDTO>>)ctx.get("RISKDTOLIST");
		boolean isSuccess=true;
		if(dtoMap!=null && dtoMap.size()>0){
			try{
				if(!gameFundRiskService.fundRequestAward(ctx,dtoMap)){
					isSuccess=false;
				}
			}catch(Exception e){
				log.error("award fund request failed.dtoMap:"+dtoMap);
				isSuccess=false;
			}
			try{
				if(!gameFundRiskService.fundRequest(ctx,dtoMap)){
					isSuccess=false;
				}
			}catch(Exception e){
				log.error("common fund request failed.dtoMap:"+dtoMap);
				isSuccess=false;
			}
			dtoMap=null;
			ctx.set("RISKDTOLIST", null);
			if(!isSuccess){
				addGameOrderFundEvent(lotteryId,issueCode);
			}
		}	
		/**
		 * 分批请求，处理一批提交一批
		 *//*
		int batchSize=5; //500
		int rowCount=gameOrderService.getUnfundedCountByLotteryIdAndIssueCode(ctx,lotteryId,issueCode);
		int size=(rowCount%batchSize)==0?(rowCount%batchSize):((rowCount/batchSize)+1);
		int beginIndex=0;
		int endIndex=batchSize;
		for(int i=0;i<size;i++){
			beginIndex=i*batchSize+1;
			endIndex=((i+1)*batchSize)>rowCount?rowCount:((i+1)*batchSize);
			getBizInterface().doFundRequestBatch(ctx,lotteryId,issueCode,beginIndex,endIndex); 
		} */
	}
	@Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
	public void doFundRequestBatch(GameContext ctx,Long lotteryId, Long issueCode, int beginIndex,int endIndex)
			throws Exception { 
		List<GameOrder> orderList=gameOrderService.getByLotteryIdAndIssueCodeAndFundStatusAndIndexes(ctx,lotteryId,issueCode,beginIndex,endIndex);
		if(orderList!=null && orderList.size()>0){
			List<Long> orderIdList=new ArrayList<Long>();
			List<TORiskDTO> dtoList=new ArrayList<TORiskDTO>();
			List<TORiskDTO> tmpDtoList=new ArrayList<TORiskDTO>();
			List<GameOrder> tmpOrderList=new ArrayList<GameOrder>();
			int count=0;
			int subBatchSize=3; 
			for(GameOrder order:orderList){
				count++;
				tmpOrderList.add(order);
				/*orderIdList.add(order.getId());
				tmpDtoList=getRiskDtoList(ctx,order);
				if(tmpDtoList!=null){					
					dtoList.addAll(tmpDtoList); 
				}*/
				if(count%subBatchSize==0 || count==orderList.size()){
					try{
						//getBizInterface().doFundRequestSubBatch(ctx,orderIdList,dtoList);
						getBizInterface().doFundRequestSubBatch(ctx,tmpOrderList);
					}catch(Exception e){
						//catch the exception from the new transaction
						log.error("catch the exception from the new transaction.dtoList:"+dtoList,e);
					}
					tmpOrderList.clear();
					/*orderIdList.clear();
					dtoList.clear();
					((Map<String,List<TORiskDTO>>)ctx.get("RISKDTOLIST")).clear();*/
					//ctx.set("RISKDTOLIST", null);
				}
			}
			
		}else{
			log.info("there is no unfunded order.");
		}
	}
	@Override
	@Transactional(propagation=Propagation.REQUIRES_NEW,rollbackFor=Exception.class)
	public void doFundRequestSubBatch(GameContext ctx, List<GameOrder> orderList) throws Exception {
		if(orderList!=null && orderList.size()>0){
			boolean isSuccess=true;
			List<String> orderCodeList=new ArrayList<String>();
			List<TORiskDTO> riskDtoList=new ArrayList<TORiskDTO>();
			List<TORiskDTO> tmpDtoList=new ArrayList<TORiskDTO>(); 
			for(GameOrder order:orderList){
				orderCodeList.add(order.getOrderCode());
				tmpDtoList=getRiskDtoList(ctx,order); 
				if(tmpDtoList!=null){					
					riskDtoList.addAll(tmpDtoList); 
				}
				((Map<String,List<TORiskDTO>>)ctx.get("RISKDTOLIST")).clear();
			}
			try{ 
				gameFundRiskService.fundRequest(riskDtoList);
			}catch(Exception e){
				log.error("fund request failed first time.orderCodeList:"+orderCodeList,e);
				//try again
				try{
 					gameFundRiskService.fundRequest(riskDtoList);
				}catch(Exception ee){
					log.error("fund request failed second time.orderCodeList:"+orderCodeList,e);
					isSuccess=false;
				}
			}
			tmpDtoList=null;
			riskDtoList=null; 
			if(isSuccess){
				gameOrderService.updateFundStatus(ctx, GameOrder.FundStatus.UNWIN,orderCodeList);
			}else{
				//写一条任务：存在订单资金待调用
				addGameOrderFundEvent(orderList.get(0).getLotteryid(),orderList.get(0).getIssueCode());
			}
			orderCodeList=null; 
		} 		
	}
	/*public void doFundRequestSubBatch_bk(GameContext ctx, List<Long> orderIdList,List<TORiskDTO> riskDtoList) throws Exception {
		boolean isSuccess=true;
		try{
			gameFundRiskService.fundRequest(riskDtoList);
		}catch(Exception e){
			log.error("fund request failed first time.orderIdList:"+orderIdList,e);
			//try again
			try{
				gameFundRiskService.fundRequest(riskDtoList);
			}catch(Exception ee){
				log.error("fund request failed second timeorderIdList:"+orderIdList,e);
				isSuccess=false;
			}
		}
		if(isSuccess){
			gameOrderService.updateFundStatus(ctx, GameOrder.FundStatus.UNWIN,orderIdList);
		}else{
			//写一条任务：存在订单资金待调用 			
		}
	}*/
	
	private List<TORiskDTO> getRiskDtoList(GameContext ctx,GameOrder order){
		gameOrderFundServcie.orderFund(ctx,order);
		gameReturnPointFundServcie.returnPointFund(ctx,order);
		if(order.getStatus().intValue()==GameOrder.Status.WIN.getValue()){
			gameOrderWinFundServcie.orderWinFund(ctx,order);
		}
		
		Map<String,List<TORiskDTO>> dtoMap=(Map<String,List<TORiskDTO>>)ctx.get("RISKDTOLIST");
		if(dtoMap!=null && dtoMap.size()>0){
			return dtoMap.get(order.getOrderCode()); 
		}				
		return null;
	} 
	private void addGameOrderFundEvent(Long lotteryId,Long issueCode) throws Exception{
		Date date=DateUtils.currentDate();
		GameControlEvent event = new GameControlEvent();	 
		event.setLotteryid(10003L); 
		event.setStartIssueCode(issueCode);
		event.setEnentType(new Long(GameControlEvent.EventType.ORDER_FUND.getValue()));
		event.setSaleEndTime(date);
		event.setSaleStartTime(date);
		event.setEndIssueCode(lotteryId);
		event.setParams("lotteryId:"+lotteryId+";issueCode:"+issueCode);
		event.setStatus(0L);
		event.setMessage("资金调用");   
		this.gameControlEventService.save(event);
	}
	
	
}
