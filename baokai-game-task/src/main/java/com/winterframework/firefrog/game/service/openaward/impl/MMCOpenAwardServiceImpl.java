package com.winterframework.firefrog.game.service.openaward.impl;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.winterframework.firefrog.common.util.DateUtils;
import com.winterframework.firefrog.common.util.GameContext;
import com.winterframework.firefrog.common.util.ProcessResult;
import com.winterframework.firefrog.event.service.IGameTrendEventService;
import com.winterframework.firefrog.game.dao.IGameControlEventDao;
import com.winterframework.firefrog.game.dao.IGameIssueDao;
import com.winterframework.firefrog.game.dao.IGameOrderDao;
import com.winterframework.firefrog.game.dao.IGameOrderWinDao;
import com.winterframework.firefrog.game.dao.IGameSlipDao;
import com.winterframework.firefrog.game.dao.vo.GameControlEvent;
import com.winterframework.firefrog.game.dao.vo.GameIssue;
import com.winterframework.firefrog.game.dao.vo.GameOrder;
import com.winterframework.firefrog.game.dao.vo.GameTrendTask;
import com.winterframework.firefrog.game.entity.GameOrder.OrderStatus;
import com.winterframework.firefrog.game.exception.GameRiskException;
import com.winterframework.firefrog.game.service.IExportFileService;
import com.winterframework.firefrog.game.service.IGameCheckDrawService;
import com.winterframework.firefrog.game.service.IGameOrderFundService;
import com.winterframework.firefrog.game.service.IGameOrderWinFundService;
import com.winterframework.firefrog.game.service.IGameReturnPointFundService;
import com.winterframework.firefrog.game.service.IGameWarnService;
import com.winterframework.firefrog.game.service.gametrend.config.GameTrendEventParams;
import com.winterframework.firefrog.game.service.gametrend.config.GameTrendEventParams.TrendEventTypeEnum;
import com.winterframework.firefrog.game.service.openaward.IOpenAwardService;
import com.winterframework.modules.web.util.JsonMapper;

/** 
* @ClassName MMCOpenAwardServiceImpl 
* @Description 秒秒开奖接口 
* @author  hugh
* @date 2014年8月25日 上午11:48:51 
*  
*/
@Service("mmcOpenAwardService")
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class MMCOpenAwardServiceImpl implements IOpenAwardService {

	private static final Logger log = LoggerFactory.getLogger(MMCOpenAwardServiceImpl.class);

	/**
	* @Title: openAward
	* @Description: 
	* @param orderId
	* @return
	* @throws Exception 
	* @see com.winterframework.firefrog.game.service.openaward.IOpenAwardService#openAward(java.lang.Long) 
	*/
	@Override
	public ProcessResult openAward(Long orderId) throws Exception {
		ProcessResult processResult = new ProcessResult();
		
		//获取订单
		GameOrder gameOrder = gameOrderDao.getById(orderId);
		if (gameOrder == null) {
			log.error("订单" + orderId + "不存在");
			processResult.fail("-1", "订单" + orderId + "不存在");
			return processResult;
		}
		Long lotteryId = gameOrder.getLotteryid();
		Long issueCode = gameOrder.getIssueCode();
		String msg = "彩种 " + lotteryId + " 奖期 " + issueCode + " 订单号 " + orderId + " ";

		//开奖前导出 
		//exportFileService.exportRngMmc(lotteryId, issueCode, "1");
		
		//获取奖期
		GameIssue gameIssue = gameIssueDao.getGameIssueByLotteryIssue(lotteryId, issueCode);
		if (gameIssue == null) {
			log.error(msg + "没有开奖号码");
			processResult.fail("-2", msg + "没有开奖号码");
			return processResult;
		}

		//获取奖期开奖号码
		String preNumberRecord = gameIssue.getPreNumberRecord();
		if (StringUtils.isBlank(preNumberRecord)) {
			log.error(msg + "没有开奖号码");
			processResult.fail("-3", msg + "没有开奖号码");
			return processResult;
		}
		
		//导出游戏注单结果文件
		//exportFileService.exportGameSlip2File(lotteryId, issueCode); 
		//判断并更新用户中奖信息
		gameCheckDrawService.doCheckIsDrawMMC(new GameContext(), preNumberRecord, gameOrder,  null,null);

		//更新首页缓存
		gameWarnService.updateIssuseRedisCache(gameOrder);
		
		
		//更新奖期状态
		gameCheckDrawService.updateIssueOpenAwardFinshed(gameIssue, preNumberRecord);
				
		try {
			//执行资金操作
			askFund(gameOrder);
		} catch (Exception e) {
			log.error(msg + "调用资金risk失败");
			throw new GameRiskException("调用资金risk失败"+e.getMessage());
			//processResult.fail("-4", msg + "调用资金risk失败" +e.getMessage());
			//return processResult;
		}
		/*	
		try {
			//生成生成trend事件
			//addGameTrendEvent(gameIssue);
			generateTrendTask(gameIssue,preNumberRecord,gameOrder.getUserid());
		} catch (Exception e) {
			log.error("生成走势图调度事件失败", e);
			//如果事件记录生成失败，得抛出异常
			throw new Exception("生成走势图调度事件失败"+e);
		}
		*/
		//开奖后导出 
		//exportFileService.exportRngMmc(lotteryId, issueCode, "2");
		return processResult;
	} 

	private void generateTrendTask(GameIssue issue,String drawNumber,Long userId) throws Exception{
		GameTrendTask trendTask=new GameTrendTask();
		trendTask.setLotteryId(issue.getLotteryid());
		trendTask.setIssueCode(issue.getIssueCode());
		trendTask.setDrawNumber(drawNumber);
		trendTask.setUserId(userId);
		trendTask.setStatus(GameTrendTask.Status.INIT.getValue());
				
		gameTrendEventService.doProcess(trendTask);
	}
	private void addGameTrendEvent(GameIssue issue) throws Exception{ 
		GameControlEvent event = new GameControlEvent();				
		event.setEnentType(18L);
		event.setLotteryid(10000L);
		event.setSaleStartTime(issue.getSaleStartTime());
		event.setSaleEndTime(issue.getSaleEndTime());
		event.setStartIssueCode(issue.getIssueCode());
		event.setEndIssueCode(issue.getLotteryid());
		event.setStatus(0L);
		event.setMessage("生成走势图。");	
		event.setCreateTime(DateUtils.currentDate());		
		
		GameTrendEventParams params = new GameTrendEventParams();
		params.setLotteryId(issue.getLotteryid());
		params.setIssueCode(issue.getIssueCode());
		params.setType(TrendEventTypeEnum.ADD.getCode());
		params.setNumberRecord(issue.getNumberRecord());		
		event.setParams(JsonMapper.nonEmptyMapper().toJson(params));
		 
		gameControlEventDao.insert(event);
	}
	/** 
	* @Title: askFund 
	* @Description: 执行资金操作
	* @param gameOrder
	*/
	private void askFund(GameOrder gameOrder){
				
		//调用 投注解冻至投注扣款
		gameOrderFundServcie.orderFund(gameOrder);
		//调用返点
		gameReturnPointFundServcie.returnPointFundUpdateRetsStatus(gameOrder.getId());
				
		if(gameOrder.getStatus().intValue() == OrderStatus.PRIZE.getValue() ){
			//调用派奖
			gameOrderWinFundServcie.orderWinFundAndUpdateWinStatus(gameOrder); 		
		}
	}
	

	

	@Resource(name = "gameIssueDaoImpl")
	private IGameIssueDao gameIssueDao;

	@Resource(name = "gameOrderDaoImpl")
	private IGameOrderDao gameOrderDao;

	@Resource(name = "gameSlipDaoImpl")
	private IGameSlipDao gameSlipDao;

	@Resource(name = "gameOrderWinDaoImpl")
	private IGameOrderWinDao gameOrderWinDao;

	@Resource(name = "exportFileServiceImpl")
	private IExportFileService exportFileService;

	@Resource(name = "gameCheckDrawServiceImpl")
	private IGameCheckDrawService gameCheckDrawService;
	
	@Resource(name = "gameReturnPointFundServcieImpl")
	private IGameReturnPointFundService gameReturnPointFundServcie;
	
	@Resource(name = "gameOrderWinFundServcieImpl")
	private IGameOrderWinFundService gameOrderWinFundServcie;
	
	@Resource(name = "gameOrderFundServcieImpl")
	private IGameOrderFundService gameOrderFundServcie;
	
	@Resource(name = "gameWarnServiceImpl")
	private IGameWarnService gameWarnService;
	@Resource(name = "gameControlEventDaoImpl")
	private IGameControlEventDao gameControlEventDao;
	@Resource(name = "gameTrendEventServiceImpl")
	private IGameTrendEventService gameTrendEventService;
	
}
