package com.winterframework.firefrog.game.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.winterframework.firefrog.common.util.DateUtils;
import com.winterframework.firefrog.common.util.GameContext;
import com.winterframework.firefrog.game.dao.IGameSeriesDao;
import com.winterframework.firefrog.game.dao.vo.GameControlEvent;
import com.winterframework.firefrog.game.dao.vo.GameIssue;
import com.winterframework.firefrog.game.dao.vo.GameSeries;
import com.winterframework.firefrog.game.entity.GameIssueEntity;
import com.winterframework.firefrog.game.entity.GameSeriesConfigEntity;
import com.winterframework.firefrog.game.entity.VOConvert;
import com.winterframework.firefrog.game.service.IExportFileService;
import com.winterframework.firefrog.game.service.IGameControlEventService;
import com.winterframework.firefrog.game.service.IGameIssueEndPlanService;
import com.winterframework.firefrog.game.service.IGameIssueFacadeService;
import com.winterframework.firefrog.game.service.IGameIssueService;
import com.winterframework.firefrog.game.service.IGameSeriesConfigService;
import com.winterframework.firefrog.game.service.IGameWarnService;
import com.winterframework.firefrog.user.entity.IndexLottery;

/**
 * 奖期Facade包装层实现类
 * @author Denny 
 * @date 2013-11-21 下午2:17:37 
 */
@Service("gameIssueFacadeServiceImpl")
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class GameIssueFacadeServiceImpl implements IGameIssueFacadeService {

	private static final Logger logger = LoggerFactory.getLogger(GameIssueFacadeServiceImpl.class);
	
	public static final String RESULT_BEGIN_ISSUES ="beginIssues";
	
	public static final String RESULT_OPEN_DRAW_ISSUES ="openDrawIssues";

	@Resource(name = "gameIssueServiceImpl")
	private IGameIssueService gameIssueService;  
	@Resource(name = "gameSeriesConfigServiceImpl")
	private IGameSeriesConfigService gameSerisConfigService; 
	@Resource(name = "gameControlEventServiceImpl")
	private IGameControlEventService gameControlEventService; 
	@Resource(name = "gameWarnServiceImpl")
	private IGameWarnService gameWarnService;
	@Resource(name = "exportFileServiceImpl")
	private IExportFileService exportFileService;
	@Resource(name = "RedisService")
	private RedisService redisService; 
	@Resource(name = "saleEndGamePlanServiceImpl")
	private IGameIssueEndPlanService saleEndGamePlanService;

	@Resource(name = "gameSeriesDaoImpl")
	private IGameSeriesDao gameSeriesImpl;
	
	@Override
	public Map<String,List<GameIssueEntity>> monitoringOpenDrawTimeEnd(Long lotteryId) throws Exception {
		logger.debug("Entered Method :: GameIssueFacadeServiceImpl.monitoringOpenDrawTimeEnd"); 
		Map<String,List<GameIssueEntity>> _result = new HashMap<String, List<GameIssueEntity>>();
		//奖期开奖
		List<GameIssueEntity> _openDrawIssues = openDrawTimeIssues(lotteryId);
		//下一奖期开始
		List<GameIssueEntity> _beginIssues = beginSaleIssues(lotteryId);

		_result.put(RESULT_OPEN_DRAW_ISSUES, _openDrawIssues);
		_result.put(RESULT_BEGIN_ISSUES, _beginIssues);
		
		logger.debug("Exited Method :: GameIssueFacadeServiceImpl.monitoringOpenDrawTimeEnd");
		return _result;
	}

	/**
	 * 奖期状态(status)為開始銷售(1)、獎期過程狀態(periodStatus)為銷售中(1)及開獎时间(openDrawTime)小於目前系統時間的所有彩種奬期。
	 * @param lotteryId 控制彩種；null 時不執行吉利分分彩(99111)、非 null 時只執行吉利分分彩。
	 * @throws Exception
	 */
	private List<GameIssueEntity> openDrawTimeIssues(Long lotteryId) throws Exception{
		List<GameIssueEntity> _result = new ArrayList<GameIssueEntity>();
		try {
			// step1. 查找奖期状态为“开始销售”并已过開獎时间的奖期列表 
			List<GameIssueEntity> _beingOpenDrawTimeIssueList = gameIssueService.queryGameIssueByStatusAndOpenDrawTime(1l, 1l); 
			if (_beingOpenDrawTimeIssueList != null && _beingOpenDrawTimeIssueList.size() > 0) {
				for (GameIssueEntity _openDrawTimeIssue : _beingOpenDrawTimeIssueList) {  
					if(lotteryId!=null && _openDrawTimeIssue.getLottery().getLotteryId()!=99111L && _openDrawTimeIssue.getLottery().getLotteryId()!=99114L) continue;	//Jlffc只执行吉利分分彩
					if(lotteryId==null && (_openDrawTimeIssue.getLottery().getLotteryId()==99111L || _openDrawTimeIssue.getLottery().getLotteryId()==99114L)) continue;	//非Jlffc不执行吉利分分彩
					try {
						//为了只影响一期
						logger.info("关闭销售的奖期号," + _openDrawTimeIssue.getIssueCode());
						// 1.奖期结束
						updateGameIssueToEnd(_openDrawTimeIssue); 
						
						//此处try-catch,销售截止任务调度，忽略由于追号引起的错误（但这个错误引起的问题后续需要能手工处理）
						try{
							execEndPlan(_openDrawTimeIssue);
						}catch(Exception e){
							logger.error("销售结束时追号出错 " + _openDrawTimeIssue.getLottery() + " " + _openDrawTimeIssue.getIssueCode(), e);
						} 
						try{
							addGameIssueEndAfterEvent(_openDrawTimeIssue);
						}catch(Exception ee){
							logger.error("奖期理论开奖生成后补任务调度失败 ", ee);
						}
						_result.add(_openDrawTimeIssue);
					} catch (Exception e2) {
						logger.error("执行监控奖期理论开奖时间任务出错 ", e2);
					}
				}
			} 
		} catch (Exception e) { 
			logger.error("执行监控奖期理论开奖时间任务出错 ", e);
			throw e;
		}
		return _result;
	}
	
	/**
	 * 
	 * 2.下一奖期开始
	 * @param lotteryId 控制彩種；null 時不執行吉利分分彩(99111)、非 null 時只執行吉利分分彩。
	 * @throws Exception
	 */
	private List<GameIssueEntity> beginSaleIssues(Long lotteryId) throws Exception{
		List<GameIssueEntity> startIssues = new ArrayList<GameIssueEntity>();
		try{
			List<GameIssueEntity> beingStartIssuelist = gameIssueService.queryGameIssueByStatusAndSalesStartTime(0L, 0L,DateUtils.currentDate());
			if(beingStartIssuelist!=null && beingStartIssuelist.size()>0){
				for(GameIssueEntity startIssue:beingStartIssuelist){  
					if(lotteryId!=null && startIssue.getLottery().getLotteryId()!=99111L && startIssue.getLottery().getLotteryId()!=99114L) continue;	//Jlffc只执行吉利分分彩
					if(lotteryId==null && (startIssue.getLottery().getLotteryId()==99111L || startIssue.getLottery().getLotteryId()==99114L)) continue;	//非Jlffc不执行吉利分分彩
					try {//为了只影响一期
						logger.info("开始销售的奖期号," + startIssue.getIssueCode());
						//20140214 add 增加奖期暂停时，对追号计划的更新为暂停状态。---应该在暂停动作的时候完成追号暂停的任务
						if (gameIssueService.isGameIssuePause(startIssue)) {
							//						sscGameAbnormalPlanService.pauseGamePlan(e.getLottery().getLotteryId(), e.getIssueCode());
							logger.info("时时彩【" + startIssue.getLottery().getLotteryId() + "】奖期期号【" + startIssue.getIssueCode()
									+ "】处于暂停状态。");
						} 
						// 1.奖期结束：更新奖期状态及奖期过程状态
						startIssueSale(startIssue);
						startIssues.add(startIssue);
					} catch (Exception e2) {
						logger.error("执行监控奖期截止时间任务出错 ", e2);
					} 
				}
			}
		} catch (Exception e) { 
			logger.error("执行监控奖期截止时间任务出错 ", e);
			throw e;
		}
		return startIssues;
	}
	
	/** 
	* @Title: startIssueSale 
	* @Description: 开始奖期销售
	* @param nextIssue
	 * @throws Exception 
	*/
	public void startIssueSale(GameIssueEntity issue) throws Exception {

		getAndUpdateAwardStruct(issue);

		GameSeriesConfigEntity en = gameSerisConfigService.queryGameSeriesConfigByLotteryId(issue.getLottery()
				.getLotteryId());
		if (en != null && en.getIssuewarnBackoutTime() != null) {
			issue.setAdminCanCancelEndTime(DateUtils.addMinutes(issue.getOpenDrawTime(), en.getIssuewarnBackoutTime()
					.intValue()));
			issue.setIssuewarnExceptionTime(en.getIssuewarnExceptionTime());
		}
		updateGameIssueToStart(issue);

		updateCurrentIssueforIndexCache(issue);
		//此处try-catch,销售截止任务调度，忽略由于追号引起的错误（但这个错误引起的问题后续需要能手工处理）
		try{
			execStartPlan(issue);
		}catch(Exception e){
			logger.error("销售开始时追号出错 " + issue.getLottery() + " " + issue.getIssueCode(), e);
		} 
	}
	protected void updateGameIssueToStart(GameIssueEntity issue) throws Exception {
		if(issue.getStatus().getValue()==GameIssue.Status.CREATE.getValue()){			
			GameIssue gameIssue=VOConvert.gameIssueEntity2GameIssue(issue);
			gameIssue.setStatus(new Long(GameIssue.Status.SALE_START.getValue()));
			gameIssue.setPeriodStatus(new Long(GameIssue.PeriodStatus.SALE_ON.getValue()));
			gameIssue.setUpdateTime(DateUtils.currentDate());
			gameIssueService.updataGameIssueSaleStart(gameIssue);
		}
	}
	
	private void execEndPlan(GameIssueEntity issue){ 
		GameContext ctx=new GameContext();
		Long lotteryId=issue.getLottery().getLotteryId();
		Long issueCode=issue.getIssueCode();
		//1.追号当期的下一期
		try { 
			saleEndGamePlanService.generatePlanWhenEnd(ctx, null, lotteryId, issueCode); 
		} catch (Exception ex) {
			logger.error("销售开始时追号出错 " + lotteryId+ " " + issueCode, ex);
		} 
	}
	private void execStartPlan(GameIssueEntity issue){ 
		GameContext ctx=new GameContext();
		Long lotteryId=issue.getLottery().getLotteryId();
		Long issueCode=issue.getIssueCode(); 
		//2.当期的下一期是追号首期则开始销售时追号  
		//开始奖期销售
		try { 
			saleEndGamePlanService.generatePlanWhenStart(ctx, null, lotteryId, issueCode);  
		} catch (Exception ex) {
			logger.error("销售开始时追号出错 " + lotteryId+ " " + issueCode, ex);
		} 
	}
	/**
	 * 更新奖期状态为"结束销售(2)"、奖期过程状态为"待开奖(2)"，<br>
	 * 且一并更新奖金结构(awardStruct)、、更新时间(updateTime)。
	 * @param e
	 * @throws Exception
	 */
	public void updateGameIssueToEnd(GameIssueEntity e) throws Exception {
		gameIssueService.updataGameIssueSaleEnd(e, 2L, 2L);
	}

	/**
	 * 更新奖期状态为"开始销售(1)"、 奖期过程状态为"销售中(1)"，<br>
	 * 且一并更新奖金结构(awardStruct)、管理员撤单时间(adminEndCancelTime)、更新时间(updateTime)、管理员可撤销时间(如果 e.issuewarnExceptionTime 非空值)。
	 * @param e
	 * @throws Exception
	 */
	public void updateGameIssueToSale(GameIssueEntity e) throws Exception {
		gameIssueService.updataGameIssue(e, 1L, 1L);
	}
 

	/** 
	* @Title: getAndUpdateAwardStruct 
	* @Description: 获取奖期奖金结构
	* @param issue
	* @throws Exception
	*/
	public void getAndUpdateAwardStruct(GameIssueEntity issue) throws Exception {

		//如果是双色球获取奖金结构
		if (issue.getLottery().getLotteryId().longValue() == 99401L) {
			logger.error("修改双色球奖金结构" + issue.getIssueCode());
			gameIssueService.getAndUpdateAwardStruct(issue);
			logger.error("修改双色球奖金结构成功" + issue.getIssueCode());
		}

	}

	/** 
	* @Title: updateCurrentIssueforIndexCache 
	* @Description: 更新首页缓存中的CurrentIssue
	* @param issue
	*/
	public void updateCurrentIssueforIndexCache(GameIssueEntity issue) {
		if (issue.getLottery() == null || issue.getLottery().getLotteryId() == null) {
			logger.error("更新首页缓存失败", "奖期彩种信息为空");
			return;
		}
		//add 增加信息到redis
		Long lotteryId = issue.getLottery().getLotteryId();
		IndexLottery indexLottery = redisService.getHome(lotteryId);

		indexLottery.setLotteryId(issue.getLottery().getLotteryId());
		indexLottery.setLottery(issue.getLottery().getLotteryName());
		indexLottery.setCurrentIssue(issue.getWebIssueCode());
		indexLottery.setSaleEndTime(issue.getSaleEndTime());
	
		GameSeries series = gameSeriesImpl.getByLotteyId (lotteryId);
		if (series != null){
			indexLottery.setLottery(series.getLotteryName());
		}
		
		redisService.setHome(lotteryId, indexLottery);
	}

	/**
	 * 
	* Title: toBeFinished
	* Description:结束奖期任务
	* @throws Exception 
	 */
	@Override
	public void toBeFinished() throws Exception {
		logger.debug("Entered Method :: GameIssueFacadeServiceImpl.toBeFinished");

		// step1. 查找奖期状态为“派奖完成”，奖期过程状态为“待结束”的奖期列表
		List<GameIssueEntity> list = gameIssueService.queryGameIssueByStatus(7L, 6L);

		if (list != null) {
			for (GameIssueEntity e : list) {

				// step2. 检查奖期的暂停、锁定状态，若未暂停、未锁定，则锁定该奖期，开始执行任务
				if (gameIssueService.isGameIssuePause(e) || gameIssueService.isGameIssueLocked(e)) {
					logger.debug("create report:" + e.getLottery() + "|" + e.getIssueCode() + "|pauls");
					continue;
				}

				// 检查上期是否暂停
				if (gameIssueService.isLastIssueStop(e)) {
					logger.debug("create report:" + e.getLottery() + "|" + e.getIssueCode() + "|stops");

					continue;
				}
				Long warn = e.getIssuewarnExceptionTime() == null ? 0 : e.getIssuewarnExceptionTime();

				// step3. 计算奖期结束时间，并判断结束时间已到的奖期
				//				Long gameIssueFinishTime = gameIssueService.calculateGameIssueFinishTime(e.getLottery().getLotteryId(),
				//						e.getFactionDrawTime());
				Long gameIssueFinishTime = e.getFactionDrawTime().getTime() + warn * 60 * 1000;
				if (gameIssueFinishTime < new Date().getTime()) {
					//gameIssueService.lockGameIssue(e);
					// step4. 更新奖期状态为“奖期结束”，奖期过程状态为“待对账”
					//生成GAME_WINS_REPORT
					//通过任务生成 report
					/*try{
						logger.debug("create report:"+e.getLottery()+"|"+e.getIssueCode()+"|create");
						gameWinsReportService.createGameWinReport(e.getLottery().getLotteryId(), e.getIssueCode());
					} catch (Exception ep) {
						logger.error("createGameWinReport erorr" + ep);
					}*/
					gameIssueService.updataGameIssue(e, 7L, 7L);
					//					gameIssueService.unLockGameIssue(e);

					gameWarnService.clearCacheWarnServiceBeansByLotteryAndIssue(e.getLottery().getLotteryId(),
							e.getIssueCode());
				}

			}
		}
		logger.debug("Exited Method :: GameIssueFacadeServiceImpl.toBeFinished");
	}
	
	public void sendSsqSaleEndEmail() throws Exception {
		Date executeDate = DateUtils.parse(
				DateUtils.format(DateUtils.currentDate(), DateUtils.DATETIME_FORMAT_PATTERN),
				DateUtils.DATETIME_FORMAT_PATTERN);
		logger.info("eeeeeeeeeeeeeeeeeeeeexeuteDate:"+executeDate);

		GameIssueEntity issue = gameIssueService.getSsqGameIssueForMail(executeDate);
		if (issue != null) {
			logger.info("邮件发送成功===================================>"+executeDate);
			exportFileService.sendSsqEmail(issue);
		}
	}
	
	/**
	 * 新增執行任務(gameControlEvent)
	 * @param gameIssue
	 * @throws Exception
	 */
	private void addGameIssueEndAfterEvent(GameIssueEntity gameIssue) throws Exception{ 		
		GameControlEvent event = new GameControlEvent();	 
		event.setLotteryid(GameControlEvent.AsyncType.SALE_END.getValue()); 
		event.setStartIssueCode(gameIssue.getIssueCode());
		event.setEnentType(new Long(GameControlEvent.EventType.ISSUE_END.getValue()));
		event.setSaleEndTime(gameIssue.getSaleEndTime());
		event.setSaleStartTime(gameIssue.getSaleStartTime());
		event.setEndIssueCode(gameIssue.getLottery().getLotteryId());
		event.setParams("lotteryId:"+gameIssue.getLottery().getLotteryId()+";issueCode:"+gameIssue.getIssueCode());
		event.setStatus(new Long(GameControlEvent.Status.INIT.getValue()));
		event.setMessage("奖期销售结束后补任务");   
		this.gameControlEventService.save(event);
	}
}
