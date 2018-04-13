package com.winterframework.firefrog.game.web.comtroller;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.winterframework.firefrog.common.annotation.ValidRequestBody;
import com.winterframework.firefrog.common.httpjsonclient.IHttpJsonClient;
import com.winterframework.firefrog.game.dao.vo.GameRiskIssue;
import com.winterframework.firefrog.game.dao.vo.GameWarnOrderLog;
import com.winterframework.firefrog.game.exception.GameRiskException;
import com.winterframework.firefrog.game.service.IGameIssueService;
import com.winterframework.firefrog.game.service.IGameRsikUserOrderService;
import com.winterframework.firefrog.game.web.dto.GameRiskOperateIssueRequest;
import com.winterframework.firefrog.game.web.dto.GameRiskOperateOrdersRequest;
import com.winterframework.firefrog.game.web.dto.GameRiskOperateResponse;
import com.winterframework.firefrog.game.web.dto.GameRiskWarnUserListQueryRequest;
import com.winterframework.firefrog.game.web.dto.GameRiskWarnUserOrderListResponse;
import com.winterframework.firefrog.game.web.dto.GenerateGamePlanRequest;
import com.winterframework.firefrog.game.web.dto.GenerateGamePlanResponse;
import com.winterframework.modules.spring.exetend.PropertyConfig;
import com.winterframework.modules.web.jsonresult.Request;
import com.winterframework.modules.web.jsonresult.Response;


/** 
* @ClassName GameIssueController 
* @Description 奖期相关 - 审核奖期 
* @author  hugh
* @date 2014年4月9日 下午3:54:08 
*  
*/
@Controller("gameRiskUserOrderController")
@RequestMapping("/gameRisk")
public class GameRiskUserOrderController {

	private Logger log = LoggerFactory.getLogger(GameRiskUserOrderController.class);

	@Resource(name = "gameRiskUserOrderServiceImpl")
	private IGameRsikUserOrderService gameRiskUserOrderServiceImpl;
	
	@Resource(name = "gameIssueServiceImpl")
	private IGameIssueService gameIssueService;


	@PropertyConfig(value = "url.connect.task")
	private String serverPath;

	@Resource(name = "httpJsonClientImpl")
	private IHttpJsonClient httpClient;

	@PropertyConfig(value = "url.game.task.zhuihao")
	private String zhuihaoUrl;

	@PropertyConfig(value = "url.game.task.continueGamePlan")
	private String continueGamePlan;
	/** 
	* @Title: queryGameRiskIssue 
	* @Description: 审核奖期下 用户订单查询 
	* @param request
	* @return
	* @throws Exception
	*/
	@RequestMapping(value = "/queryGameRiskUserOrders")
	@ResponseBody
	public Response<GameRiskWarnUserOrderListResponse> queryGameRiskUserOrders(
			@RequestBody @ValidRequestBody Request<GameRiskWarnUserListQueryRequest> request) throws Exception {

		log.info("开始查询审核奖期列表......");
		Response<GameRiskWarnUserOrderListResponse> response = new Response<GameRiskWarnUserOrderListResponse>(request);
		
		try {
			response = gameRiskUserOrderServiceImpl.queryWarnUserOrderList(request);
			GameRiskIssue gameIssue = new GameRiskIssue();
			gameIssue.setLotteryid(request.getBody().getParam().getLotteryId());
			gameIssue.setIssueCode(request.getBody().getParam().getIssueCode());
			gameIssue.setOperator(request.getHead().getUserAccount());
			gameIssueService.setOperator(gameIssue);

		} catch (GameRiskException e) {
			log.error("查询字段为空", e);
			response.getHead().setStatus(e.getStatus());
		} catch (Exception e) {
			log.error("查询奖期列表出错", e);
			throw e;
		}

		log.info("查询审核奖期列表完成。");
		return response;
	}


	/** 
	* @Title: updateWarnOrder 
	* @Description: 审核订单
	* @param request
	* @return
	* @throws Exception
	*/
	@RequestMapping(value = "/updateWarnOrders")
	@ResponseBody
	public Response<GameRiskOperateResponse> updateWarnOrders(
			@RequestBody @ValidRequestBody Request<GameRiskOperateOrdersRequest> request) throws Exception{
		
		log.info("审核订单开始......");
		Response<GameRiskOperateResponse> response = new Response<GameRiskOperateResponse>(request);
		
		GameRiskOperateOrdersRequest re = request.getBody().getParam();
		List<Long> orderIds = re.getOrderIds();
		Long status = re.getStatus();
		GameWarnOrderLog entity = new GameWarnOrderLog();
		entity.setCreateTime(new Date());
		entity.setDisposeInfo(re.getDisposeInfo());
		entity.setDisposeMemo(re.getDisposeMemo());
		entity.setDisposeUser(re.getDisposeUser());
		entity.setDisposeType(re.getStatus());
		
		try {
			GenerateGamePlanRequest planRequest = gameRiskUserOrderServiceImpl.updateWarnOrder(orderIds, status, entity); 
			if(planRequest!=null && planRequest.getPlanList() !=null && planRequest.getPlanList().size()>0){
				//订单进入审核  改为 不暂停追号 此处撤销也无需继续追号   但是要调用追号逻辑，如果追中即停 需要撤销后续追号
				//httpClient.invokeHttp(serverPath + continueGamePlan, planRequest, GenerateGamePlanResponse.class);
				httpClient.invokeHttp(serverPath + zhuihaoUrl, planRequest, GenerateGamePlanResponse.class);
			}	
			
		} catch (GameRiskException e) {
			log.error("查询字段为空", e);
			response.getHead().setStatus(e.getStatus());
		} catch (Exception e) {
			log.error("审核订单出错", e);
			response.getHead().setStatus(500L); 
		}

		log.info("审核订单完成。");
		return response;
	}
	
	/** 
	* @Title: updateNotPassByLotteryAndIssue 
	* @Description: 审核奖期订单不通过
	* @param request
	* @return
	* @throws Exception
	*/
	@RequestMapping(value = "/updateNotPassByLotteryAndIssue")
	@ResponseBody	
	public Response<GameRiskOperateResponse> updateNotPassByLotteryAndIssue(
			@RequestBody @ValidRequestBody Request<GameRiskOperateIssueRequest> request) throws Exception{
		
		log.info("审核奖期订单开始......");
		Response<GameRiskOperateResponse> response = new Response<GameRiskOperateResponse>(request);
		GameRiskOperateIssueRequest re = request.getBody().getParam();
		try {
			gameRiskUserOrderServiceImpl.updateNotPassByLotteryAndIssue(re.getLotteryId(), re.getIssueCode());

		} catch (GameRiskException e) {
			log.error("查询字段为空", e);
			response.getHead().setStatus(e.getStatus());
		} catch (Exception e) {
			log.error("审核奖期订单出错", e);
			throw e;
		}

		log.info("审核奖期订单完成。");
		return response;
	}

}
