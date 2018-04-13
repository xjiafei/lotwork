package com.winterframework.firefrog.game.web.controller;

import java.util.Date;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.winterframework.firefrog.common.annotation.ValidRequestBody;
import com.winterframework.firefrog.common.util.DateUtils;
import com.winterframework.firefrog.game.dao.IGameControlEventDao;
import com.winterframework.firefrog.game.dao.vo.GameDrawResult;
import com.winterframework.firefrog.game.dao.vo.GameWarnEvent;
import com.winterframework.firefrog.game.dao.vo.GameWarnIssueLog;
import com.winterframework.firefrog.game.entity.GameIssueEntity;
import com.winterframework.firefrog.game.entity.GameIssuePeriodStatus;
import com.winterframework.firefrog.game.entity.GameIssueStatus;
import com.winterframework.firefrog.game.entity.GameSeriesConfigEntity;
import com.winterframework.firefrog.game.exception.GameIssueWarnException;
import com.winterframework.firefrog.game.service.IGameControlEventService;
import com.winterframework.firefrog.game.service.IGameDrawService;
import com.winterframework.firefrog.game.service.IGameIssueService;
import com.winterframework.firefrog.game.service.IGamePackageService;
import com.winterframework.firefrog.game.service.IGamePlanService;
import com.winterframework.firefrog.game.service.IGameSeriesConfigService;
import com.winterframework.firefrog.game.service.IGameWarnIssueLogService;
import com.winterframework.firefrog.game.web.controller.utlis.GameAbnormalOperationUtils;
import com.winterframework.firefrog.game.web.dto.OperationsAddNumberRecordRequest;
import com.winterframework.firefrog.game.web.dto.OperationsCancelCurrentPackageRequest;
import com.winterframework.firefrog.game.web.dto.OperationsCancelFollowPlanRequest;
import com.winterframework.firefrog.game.web.dto.OperationsContinueIssueRequest;
import com.winterframework.firefrog.game.web.dto.OperationsContinueIssueResponse;
import com.winterframework.firefrog.game.web.dto.OperationsModifyNumberRecordRequest;
import com.winterframework.firefrog.game.web.dto.OperationsPauseIssueRequest;
import com.winterframework.modules.web.jsonresult.Request;
import com.winterframework.modules.web.jsonresult.Response;
import com.winterframework.modules.web.jsonresult.ResponseHeader;

/** 
* @ClassName: GameAbnormalOperationController 
* @Description: 异常操作项Controller
* @author Alan
* @date 2013-11-18 上午10:30:55 
*  
*/
@Controller("gameAbnormalOperationController")
@RequestMapping("/game")
public class GameAbnormalOperationController {

	@Resource(name = "gameDrawServiceImpl")
	private IGameDrawService gameDrawServiceImpl;

	@Resource(name = "gameWarnIssueLogServiceImpl")
	private IGameWarnIssueLogService gameWarnIssueLogServiceImpl;

	@Resource(name = "gameIssueServiceImpl")
	private IGameIssueService gameIssueServiceImpl;

	@Resource(name = "gamePackageServiceImpl")
	private IGamePackageService gamePackageServcieImpl;

	@Resource(name = "gamePlanServiceImpl")
	private IGamePlanService gamePlanServiceImpl;

	@Resource(name = "gameControlEventDaoImpl")
	private IGameControlEventDao gameControlEventDao;
	
	@Resource(name = "gameSeriesConfigServiceImpl")
	private IGameSeriesConfigService gameSeriesConfigServiceImpl;
	@Resource(name = "gameControlEventServiceImpl")
	private IGameControlEventService gameControlEventService;
	

	private Logger log = LoggerFactory.getLogger(GameAbnormalOperationController.class);

	/**
	* @Title: inputManualNumberRecord 
	* @Description: 模拟输入开奖号码
	 */
	@RequestMapping(value = "/inputManualNumberRecord")
	@ResponseBody
	public Response<Object> inputManualNumberRecord(
			@RequestBody @ValidRequestBody Request<OperationsAddNumberRecordRequest> request) throws Exception {
		log.info("输入开奖号码开始...");

		Response<Object> response = new Response<Object>();
		ResponseHeader head = ResponseHeader.createReponseHeader(request.getHead());

		Long lotteryid = request.getBody().getParam().getLotteryid();
		Long issueCode = request.getBody().getParam().getIssueCode();
		String numberRecord = request.getBody().getParam().getNumberRecord();
		String disposeMemo = request.getBody().getParam().getDisposeMemo();

		try {
			GameWarnIssueLog warnIssueLog = new GameWarnIssueLog();

			GameDrawResult gameDrawResult = gameDrawServiceImpl.getDrawResuleByLotteryIdAndIssueCode(lotteryid,
					issueCode);

			GameIssueEntity issue = gameIssueServiceImpl.queryGameIssue(lotteryid, issueCode);

			if (gameDrawResult != null) {
				log.info("已经存在开奖号码");

				head.setStatus(2L);//已存在开奖号码

				if (issue.getStatus() == GameIssueStatus.SALE_END) {
					//2、更新奖期状态"开奖号码已确认"
					issue.setStatus(GameIssueStatus.ACK_DRAW_RESULT);
					issue.setPeriodStatus(GameIssuePeriodStatus.WAIT_DRAW_RESULT);
					issue.setFactionDrawTime(new Date());
					gameIssueServiceImpl.updateGameIssue(issue);
				}
			} else {
				if (issue.getStatus() != GameIssueStatus.SALE_END) {
					log.info("奖期状态不是结束销售状态，不能输入开奖号码");

					head.setStatus(3L);//奖期状态不是”结束销售“状态
				} else {
					//后台校验开奖号码
					if (!GameAbnormalOperationUtils.isRightResultNumber(lotteryid, numberRecord)) {
						log.error("输入开奖号码失败：彩种【" + lotteryid + "】,开奖号码【" + numberRecord + "】");
						throw new Exception();
					}
//					warnIssueLog.setDisposeInfo("输入开奖号码：" + numberRecord);
//					warnIssueLog.setStatus(1L);//处理中
//					warnIssueLog.setCreateTime(new Date());
//					warnIssueLog.setDisposeMemo(disposeMemo);
//					warnIssueLog.setDisposeUser(request.getHead().getUserAccount());
					//1、添加开奖结果记录
					gameDrawServiceImpl.addDrawResult(lotteryid, issueCode, numberRecord, warnIssueLog,null, "");

					head.setStatus(0L);//成功

//					//2、更新奖期状态"开奖号码已确认"
//					issue.setStatus(GameIssueStatus.ACK_DRAW_RESULT);
//					issue.setPeriodStatus(GameIssuePeriodStatus.WAIT_DRAW_RESULT);
//					issue.setRecivceDrawTime(new Date());
//					gameIssueServiceImpl.updateGameIssue(issue);

				}
			}

		} catch (Exception e) {
			log.error("输入开奖号码失败...", e);
			head.setStatus(1L);//失败
		}
		log.info("输入开奖号码结束...");

		response.setHead(head);
		return response;
	}

	/**
	* @Title: addNumberRecord 
	* @Description: 输入开奖号码
	 */
	@RequestMapping(value = "/addNumberRecord")
	@ResponseBody
	public Response<Object> addNumberRecord(
			@RequestBody @ValidRequestBody Request<OperationsAddNumberRecordRequest> request) throws Exception {
		log.info("输入开奖号码开始...");

		Response<Object> response = new Response<Object>();
		ResponseHeader head = ResponseHeader.createReponseHeader(request.getHead());

		Long lotteryid = request.getBody().getParam().getLotteryid();
		Long issueCode = request.getBody().getParam().getIssueCode();
		String numberRecord = request.getBody().getParam().getNumberRecord();
		String disposeMemo = request.getBody().getParam().getDisposeMemo();

		try {
			GameWarnIssueLog warnIssueLog = new GameWarnIssueLog();

			GameDrawResult gameDrawResult = gameDrawServiceImpl.getDrawResuleByLotteryIdAndIssueCode(lotteryid,
					issueCode);

			GameIssueEntity issue = gameIssueServiceImpl.queryGameIssue(lotteryid, issueCode);

			if (gameDrawResult != null) {
				log.info("已经存在开奖号码");

				head.setStatus(2L);//已存在开奖号码
			} else {
				if (issue.getStatus() != GameIssueStatus.SALE_END) {
					log.info("奖期状态不是结束销售状态，不能输入开奖号码");

					head.setStatus(3L);//奖期状态不是”结束销售“状态
				} else {
					//后台校验开奖号码
					if (!GameAbnormalOperationUtils.isRightResultNumber(lotteryid, numberRecord)) {
						log.error("输入开奖号码失败：彩种【" + lotteryid + "】,开奖号码【" + numberRecord + "】");
						throw new Exception();
					}
					warnIssueLog.setDisposeInfo("输入开奖号码：" + numberRecord);
					warnIssueLog.setIssueCode(issue.getIssueCode());
					warnIssueLog.setLotteryid(lotteryid);
					warnIssueLog.setWebIssueCode(issue.getWebIssueCode());
					warnIssueLog.setEvent(GameWarnEvent.MANUAL_R_AWARD.getCode());
					warnIssueLog.setCreateTime(new Date());
					warnIssueLog.setDisposeMemo(disposeMemo);
					warnIssueLog.setDisposeUser(request.getHead().getUserAccount());
					//1、添加开奖结果记录
					gameDrawServiceImpl.inputNumberDrawResult(lotteryid, issueCode, numberRecord, warnIssueLog,null);

					head.setStatus(0L);//成功
				}
			}

		} catch (Exception e) {
			log.error("输入开奖号码失败...", e);
			head.setStatus(1L);//失败
		}
		log.info("输入开奖号码结束...");

		response.setHead(head);
		return response;
	}

	/**
	* @Title: addNumberRecord 
	* @Description: 输入官方实际开奖号码(修正开奖号码)
	 */
	@RequestMapping(value = "/modifyNumberRecord")
	@ResponseBody
	public Response<Object> modifyNumberRecord(
			@RequestBody @ValidRequestBody Request<OperationsModifyNumberRecordRequest> request) throws Exception {
		log.info("输入官方实际开奖号码开始...");

		Response<Object> response = new Response<Object>();
		ResponseHeader head = ResponseHeader.createReponseHeader(request.getHead());

		Long lotteryid = request.getBody().getParam().getLotteryid();
		Long issueCode = request.getBody().getParam().getIssueCode();
		String numberRecord = request.getBody().getParam().getNumberRecord();
		String disposeMemo = request.getBody().getParam().getDisposeMemo();

		try {
			//后台校验开奖号码
			if (!GameAbnormalOperationUtils.isRightResultNumber(lotteryid, numberRecord)) {
				log.error("输入开奖号码失败：彩种【" + lotteryid + "】,开奖号码【" + numberRecord + "】");
				throw new Exception();
			}

			GameWarnIssueLog warnIssueLog = new GameWarnIssueLog();

			GameDrawResult gameDrawResult = gameDrawServiceImpl.getDrawResuleByLotteryIdAndIssueCode(lotteryid,
					issueCode);
			//            GameIssue gameIssue = gameIssueServiceImpl.getEarlierSuspendedGameIssue(lotteryid, issueCode);
			GameIssueEntity gameIssue = gameIssueServiceImpl.getGameIssue(lotteryid, issueCode);
			GameSeriesConfigEntity gameSeriesConfigEntity = gameSeriesConfigServiceImpl
					.queryGameSeriesConfigByLotteryId(lotteryid);
			if (gameIssue.getStatus().getValue() < GameIssueStatus.SALE_END.getValue()) {
				log.info("奖期状态不是结束销售状态，不能输入开奖号码");
				throw new GameIssueWarnException();//奖期状态不是”结束销售“状态
			} else {
				long time = DateUtils.getTimeDiff(gameIssue.getOpenDrawTime(), new Date());
				if (gameSeriesConfigEntity != null && gameSeriesConfigEntity.getIssuewarnExceptionTime() != null
						&& (time / 60 > gameSeriesConfigEntity.getIssuewarnExceptionTime())) {
					log.error("流程异常处理超限：彩种【" + lotteryid + "】,开奖号码【" + numberRecord + "】");
					throw new GameIssueWarnException();
				}
								
				//2、添加异常奖期处理日志
				warnIssueLog.setCreateTime(new Date());
				warnIssueLog.setDisposeInfo("开奖号码修改：" + gameDrawResult.getNumberRecord() + ">>" + numberRecord);
				warnIssueLog.setDisposeMemo(disposeMemo);
				warnIssueLog.setDisposeUser(request.getHead().getUserAccount());
				warnIssueLog.setIssueCode(gameIssue.getIssueCode());
				warnIssueLog.setLotteryid(lotteryid);
				warnIssueLog.setWebIssueCode(gameIssue.getWebIssueCode());
				warnIssueLog.setEvent(GameWarnEvent.MANUAL_R_AWARD_TODO.getCode());

				//1、修改开奖号码
				gameDrawServiceImpl.modifyDrawResult(lotteryid, issueCode, numberRecord, warnIssueLog,null);

				head.setStatus(0L);
			}
		} catch (GameIssueWarnException e) {
			log.info("流程异常处理超限", e);
			head.setStatus(e.getStatus());
		} catch (Exception e) {
			log.info("输入官方实际开奖号码失败", e);
			head.setStatus(1L);
		}
		log.info("输入官方实际开奖号码结束...");

		response.setHead(head);
		return response;
	}

    /**
	* @Title: pauseIssue
	* @Description: 暂缓派奖(暂停奖期)
	 */
	@RequestMapping(value = "/pauseIssue")
	@ResponseBody
	public Response<Object> pauseIssue(@RequestBody @ValidRequestBody Request<OperationsPauseIssueRequest> request)
			throws Exception {
		log.info("暂停奖期开始...");

		Response<Object> response = new Response<Object>();
		ResponseHeader head = ResponseHeader.createReponseHeader(request.getHead());

		Long lotteryid = request.getBody().getParam().getLotteryid();
		Long issueCode = request.getBody().getParam().getIssueCode();
		String disposeMemo = request.getBody().getParam().getDisposeMemo();

		try {
			GameWarnIssueLog warnIssueLog = new GameWarnIssueLog();
			GameIssueEntity issue = gameIssueServiceImpl.queryGameIssue(lotteryid, issueCode);
			//添加异常奖期处理日志,由于没有添加主控任务，当操作成功时 日志直接表示为处理成功
			warnIssueLog.setCreateTime(new Date());
			warnIssueLog.setDisposeInfo("暂缓派奖操作，将奖期改为暂停状态。");
			warnIssueLog.setDisposeMemo(disposeMemo);
			warnIssueLog.setDisposeUser(request.getHead().getUserAccount());
			warnIssueLog.setIssueCode(issue.getIssueCode());
			warnIssueLog.setLotteryid(lotteryid);
			warnIssueLog.setWebIssueCode(issue.getWebIssueCode());
			warnIssueLog.setEvent(GameWarnEvent.MANUAL_PAUSE.getCode());
			//暂停奖期
			gameIssueServiceImpl.pauseIssue(lotteryid, issueCode, warnIssueLog);

			//暂停成功
			head.setStatus(0L);

		} catch (Exception e) {
			log.error("暂停奖期错误", e);
			//暂停失败
			head.setStatus(1L);
		}
		log.info("暂停奖期结束...");

		response.setHead(head);
		return response;
	}

	/**
	* @Title: pauseIssue
	* @Description: 继续派奖(继续奖期)
	 */
	@RequestMapping(value = "/continueIssue")
	@ResponseBody
	public Response<OperationsContinueIssueResponse> continueIssue(
			@RequestBody @ValidRequestBody Request<OperationsContinueIssueRequest> request) throws Exception {
		log.info("继续奖期开始...");

		Response<OperationsContinueIssueResponse> response = new Response<OperationsContinueIssueResponse>(request);
		ResponseHeader head = ResponseHeader.createReponseHeader(request.getHead());

		Long lotteryid = request.getBody().getParam().getLotteryid();
		Long issueCode = request.getBody().getParam().getIssueCode();
		String disposeMemo = request.getBody().getParam().getDisposeMemo();

		OperationsContinueIssueResponse result = new OperationsContinueIssueResponse();

		try {
			GameIssueEntity issue = gameIssueServiceImpl.queryGameIssue(lotteryid, issueCode);
			//20140212 判断是否存在期号更小的暂停奖期，若存在，则报错，并退出流程
			//判断并获取最小的暂停奖期
			//经确认，继续派奖，不需要判断较前是否有暂停奖期。			
			GameWarnIssueLog warnIssueLog = new GameWarnIssueLog();

			//添加异常奖期处理日志,由于没有添加主控任务，当操作成功时 日志直接表示为处理成功
			warnIssueLog.setCreateTime(new Date());
			warnIssueLog.setDisposeInfo("继续派奖操作，将奖期改为正常状态。");
			warnIssueLog.setDisposeMemo(disposeMemo);
			warnIssueLog.setDisposeUser(request.getHead().getUserAccount());
			warnIssueLog.setIssueCode(issue.getIssueCode());
			warnIssueLog.setLotteryid(lotteryid);
			warnIssueLog.setWebIssueCode(issue.getWebIssueCode());
			warnIssueLog.setEvent(GameWarnEvent.MANUAL_CONTINUE.getCode());
			//暂停奖期
			gameIssueServiceImpl.continueIssue(lotteryid, issueCode, warnIssueLog);
			
			//继续成功
			head.setStatus(0L);
			response.setResult(result);

		} catch (Exception e) {
			log.error("继续奖期错误", e);
			//继续失败
			head.setStatus(1L);
		}
		log.info("继续奖期结束...");

		response.setHead(head);
		return response;
	}

	/**
	* @Title: pauseIssue
	* @Description: 撤销本期方案
	 */
	@RequestMapping(value = "/cancelCurrentPackage")
	@ResponseBody
	public Response<Object> cancelCurrentPackage(
			@RequestBody @ValidRequestBody Request<OperationsCancelCurrentPackageRequest> request) throws Exception {
		log.info("撤销本期方案开始...");

		Response<Object> response = new Response<Object>();
		ResponseHeader head = ResponseHeader.createReponseHeader(request.getHead());

		Long lotteryid = request.getBody().getParam().getLotteryid();
		Long issueCode = request.getBody().getParam().getIssueCode();
		String disposeMemo = request.getBody().getParam().getDisposeMemo();
        GameIssueEntity gameIssue = gameIssueServiceImpl.getGameIssue(lotteryid, issueCode);
        GameSeriesConfigEntity gameSeriesConfigEntity = gameSeriesConfigServiceImpl
                .queryGameSeriesConfigByLotteryId(lotteryid);
		try {
            long time = DateUtils.getTimeDiff(gameIssue.getOpenDrawTime(), new Date());
            if (gameSeriesConfigEntity != null && gameSeriesConfigEntity.getIssuewarnExceptionTime() != null
                    && (time / 60 > gameSeriesConfigEntity.getIssuewarnExceptionTime())) {
                log.error("撤单处理超限：彩种【" + lotteryid + "】,开奖号码【" + issueCode + "】");
                throw new GameIssueWarnException();
            }
			GameWarnIssueLog warnIssueLog = new GameWarnIssueLog();

			//1、添加异常奖期处理日志
			warnIssueLog.setCreateTime(new Date());
			warnIssueLog.setDisposeInfo("撤销本期方案操作");
			warnIssueLog.setDisposeMemo(disposeMemo);
			warnIssueLog.setDisposeUser(request.getHead().getUserAccount());
			warnIssueLog.setIssueCode(gameIssue.getIssueCode());
			warnIssueLog.setLotteryid(lotteryid);
			warnIssueLog.setWebIssueCode(gameIssue.getWebIssueCode());
			warnIssueLog.setEvent(GameWarnEvent.MANUAL_CANCEL_PACKAGE_TODO.getCode());

			//2、撤销本期方案(只是添加一条Game_Control_Event记录)
			gamePackageServcieImpl.cancelGamePackage(lotteryid, issueCode, warnIssueLog);

			//撤销本期方案操作成功
			head.setStatus(0L);

		} catch (GameIssueWarnException e) {
            log.info("撤单处理超限", e);
            head.setStatus(e.getStatus());
        } catch (Exception e) {
			log.error("撤销本期方案错误", e);
			//撤销本期方案失败
			head.setStatus(1L);
		}
		log.info("撤销本期方案结束...");

		response.setHead(head);
		return response;
	}

	/**
	* @Title: pauseIssue
	* @Description: 撤销后续追号
	 */
	@RequestMapping(value = "/cancelFollowPlan")
	@ResponseBody
	public Response<Object> cancelFollowPlan(
			@RequestBody @ValidRequestBody Request<OperationsCancelFollowPlanRequest> request) throws Exception {
		log.info("撤销后续追号开始...");

		Response<Object> response = new Response<Object>();
		ResponseHeader head = ResponseHeader.createReponseHeader(request.getHead());

		Long lotteryid = request.getBody().getParam().getLotteryid();
		Long issueCode = request.getBody().getParam().getIssueCode();
		String startIssueCode = request.getBody().getParam().getStartIssueCode();
		String endIssueCode = request.getBody().getParam().getEndIssueCode();
		String disposeMemo = request.getBody().getParam().getDisposeMemo();
		Long userId = request.getHead().getUserId();
		try {
			GameIssueEntity gameIssue = gameIssueServiceImpl.getGameIssue(lotteryid, issueCode);
			GameWarnIssueLog warnIssueLog = new GameWarnIssueLog();			 
			//1、添加异常奖期处理日志
			warnIssueLog.setCreateTime(new Date());
			warnIssueLog.setDisposeInfo("撤销后续追号操作:" + startIssueCode + "(起始期)-" + endIssueCode + "(结束期)");
			warnIssueLog.setDisposeMemo(disposeMemo);
			warnIssueLog.setDisposeUser(request.getHead().getUserAccount());
			warnIssueLog.setIssueCode(gameIssue.getIssueCode());
			warnIssueLog.setLotteryid(lotteryid);
			warnIssueLog.setWebIssueCode(gameIssue.getWebIssueCode());
			warnIssueLog.setEvent(GameWarnEvent.MANUAL_CANCEL_PLAN_TODO.getCode());
			warnIssueLog.setCreator(userId);
			//2、撤销后续追号(只是添加一条Game_Control_Event记录)
			gamePlanServiceImpl.cancelFollowGamePlan(lotteryid, startIssueCode, endIssueCode, warnIssueLog);

			//将GAME_CONTROL_EVENT表中endIssueCode字段改为可为空，因为撤销后续追号的时候可以不输入结束奖期

			//撤销后续追号操作成功
			head.setStatus(0L);

		} catch (Exception e) {
			log.error("撤销后续追号错误", e);
			//撤销后续追号失败
			head.setStatus(1L);
		}
		log.info("撤销后续追号结束...");

		response.setHead(head);
		return response;
	}

	
	
	   /**
		* @Title: reTry
		* @Description: reTry
		 */
		@RequestMapping(value = "/reTry")
		@ResponseBody
		public Response<Object> reTry(@RequestBody @ValidRequestBody Request<OperationsPauseIssueRequest> request)
				throws Exception {
			log.info("reTry开始...");

			Response<Object> response = new Response<Object>();
			ResponseHeader head = ResponseHeader.createReponseHeader(request.getHead());

			Long lotteryid = request.getBody().getParam().getLotteryid();
			Long issueCode = request.getBody().getParam().getIssueCode();
			String disposeMemo = request.getBody().getParam().getDisposeMemo();

			System.out.println(lotteryid);
			System.out.println(issueCode);
			try {
				gameControlEventService.retryDraw(lotteryid, issueCode);
			} catch (Exception e) {
				log.error("reTry", e);
				//暂停失败
				head.setStatus(1L);
			}
			log.info("reTry结束...");

			response.setHead(head);
			return response;
		}
}
