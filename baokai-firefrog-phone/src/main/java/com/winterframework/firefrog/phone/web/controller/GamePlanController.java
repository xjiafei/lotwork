package com.winterframework.firefrog.phone.web.controller;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.core.type.TypeReference;
import com.winterframework.firefrog.common.util.DataConverterUtil;
import com.winterframework.firefrog.common.util.DateUtils;
import com.winterframework.firefrog.game.web.dto.GameOrderDetailQueryRequest;
import com.winterframework.firefrog.game.web.dto.GameOrderDetailQueryResponse;
import com.winterframework.firefrog.game.web.dto.GamePlanDetailQueryRequest;
import com.winterframework.firefrog.game.web.dto.GamePlanDetailQueryResponse;
import com.winterframework.firefrog.game.web.dto.GamePlanQueryRequest;
import com.winterframework.firefrog.game.web.dto.GamePlanQueryResponse;
import com.winterframework.firefrog.game.web.dto.OrdersStruc;
import com.winterframework.firefrog.game.web.dto.PlansFuturesStruc;
import com.winterframework.firefrog.game.web.dto.PlansStruc;
import com.winterframework.firefrog.game.web.dto.SlipsStruc;
import com.winterframework.firefrog.phone.web.cotroller.dto.GameDetailProject;
import com.winterframework.firefrog.phone.web.cotroller.dto.TaskDetailProject;
import com.winterframework.firefrog.phone.web.cotroller.dto.TaskDetailRequest;
import com.winterframework.firefrog.phone.web.cotroller.dto.TaskDetailResponse;
import com.winterframework.firefrog.phone.web.cotroller.dto.TaskDetailTasks;
import com.winterframework.firefrog.phone.web.cotroller.dto.TaskListDto;
import com.winterframework.firefrog.phone.web.cotroller.dto.TaskListRequest;
import com.winterframework.firefrog.phone.web.cotroller.dto.TaskListResponse;
import com.winterframework.firefrog.phone.web.cotroller.dto.UserToken;
import com.winterframework.modules.spring.exetend.PropertyConfig;
import com.winterframework.modules.web.jsonresult.Pager;
import com.winterframework.modules.web.jsonresult.Request;
import com.winterframework.modules.web.jsonresult.Response;

@Controller("gamePlanController")
@RequestMapping("/information")
public class GamePlanController extends BaseController {
	
	private static final Logger log = LoggerFactory.getLogger(GamePlanController.class);
	
	@PropertyConfig(value="url.front.gamePlan")
	private String gamePlanUrl;
	@PropertyConfig(value="url.front.gamePlanDetail")
	private String gamePlanDetailUrl;
	
	@PropertyConfig(value = "url.front.gamedetail")
	private String gameDetailUrl;

	@RequestMapping(value = "/taskList")
	@ResponseBody
	public Response<TaskListResponse> taskList(@RequestBody Request<TaskListRequest> request) throws Exception{
		
		Response<TaskListResponse> response = new Response<TaskListResponse>(request);
		String token = request.getHead().getSessionId();
		try {
			
			TaskListResponse result = new TaskListResponse();
			GamePlanQueryRequest gameRequest = new GamePlanQueryRequest();
//			gameRequest.setStatus(0);
			Calendar cal = Calendar.getInstance();
			cal.setTime(new Date());
			cal.add(java.util.Calendar.DATE, -2);
			gameRequest.setStartTime(cal.getTimeInMillis());
			gameRequest.setEndTime(new Date().getTime());
			gameRequest.setLotteryId(request.getBody().getParam().getLotteryId());
			String account = getUserNameByToken(token);
			if(null == account){
				response.getHead().setStatus(7L);
				return response;
			}
			UserToken ut = getUserToken(account);
			
//			Long[] lotterys = allSeries();
			
			List<TaskListDto> list = new ArrayList<TaskListDto>();
			
//			for(Long lotteryid : lotterys){
//				
//				gameRequest.setLotteryId(lotteryid);
				Response<GamePlanQueryResponse> gameResponse =  httpClient.invokeHttp(gameUrl+gamePlanUrl, gameRequest, new Pager(1,10000),ut.getUserId(),ut.getUserName(), new TypeReference<Response<GamePlanQueryResponse>>(){} );
				
				if(null != gameResponse && null != gameResponse.getBody().getResult()){
					
					for(PlansStruc struc : gameResponse.getBody().getResult().getPlansStruc()){
						
						TaskListDto dto = new TaskListDto();
						dto.setTaskid(struc.getPlanid()+"");
						dto.setLotteryid(struc.getLotteryid());
						dto.setBegintime(DateUtils.format(DataConverterUtil.convertLong2Date(struc.getSaleTime()), DateUtils.DATETIME_FORMAT_PATTERN));
						dto.setBeginissue(struc.getStartWebIssueCode());
						dto.setIssuecount(struc.getTotalIssue());
						dto.setFinishedcount(struc.getFinishIssue());
						dto.setStatus(struc.getStatus());
						dto.setFinishedmoney(new BigDecimal(struc.getUsedAmount()==null ? 0 : struc.getUsedAmount()).divide(new BigDecimal(10000), 2, RoundingMode.HALF_UP).floatValue());
						dto.setTotalmoney(new BigDecimal(struc.getTotamount()==null ? 0 : struc.getTotamount()).divide(new BigDecimal(10000),2,RoundingMode.HALF_UP).floatValue());
						
						list.add(dto);
					}
				}
//			}
			result.setList(list);
			response.getBody().setResult(result);
			
		} catch (Exception e) {
			log.error("taskList error:", e);
			response.getHead().setStatus(901000L);
		}
		
		return response;
	}
	
	@RequestMapping(value = "/taskDetail")
	@ResponseBody
	public Response<TaskDetailResponse> taskDetail(@RequestBody Request<TaskDetailRequest> request) throws Exception{
		
		Response<TaskDetailResponse> response = new Response<TaskDetailResponse>(request);
		
		String token = request.getHead().getSessionId();
		try{
			
			String account = getUserNameByToken(token);
			if(null == account){
				response.getHead().setStatus(7L);
				return response;
			}
			UserToken ut = getUserToken(account);
			
			TaskDetailResponse result = new TaskDetailResponse();
			GamePlanDetailQueryRequest gameRequest = new GamePlanDetailQueryRequest();
			gameRequest.setPlanid(Long.parseLong(request.getBody().getParam().getId()));
			
			Response<GamePlanDetailQueryResponse> gameResponse = httpClient.invokeHttp(gameUrl + gamePlanDetailUrl, gameRequest, new Pager(1,10000), ut.getUserId(), ut.getUserName(), new TypeReference<Response<GamePlanDetailQueryResponse>>() {});
			
			if(null != gameResponse && null != gameResponse.getBody().getResult() && null != gameResponse.getBody().getResult().getPlanSlipsStrucs()){
				
				GamePlanDetailQueryResponse gamePlanDetail = gameResponse.getBody().getResult();
				PlansStruc planStruc = gamePlanDetail.getPlansStruc();
				result.setTaskNo(planStruc.getPlanCode());
				result.setBeginissue(planStruc.getStartWebIssueCode());
				result.setBegintime(DateUtils.format(DataConverterUtil.convertLong2Date(planStruc.getSaleTime()), DateUtils.DATETIME_FORMAT_PATTERN));
				result.setIssuecount(planStruc.getTotalIssue());
				result.setFinishedcount(planStruc.getFinishIssue());
				result.setFinishedmoney(new BigDecimal(planStruc.getUsedAmount()).divide(new BigDecimal(10000), 2, RoundingMode.HALF_UP).floatValue());
				result.setTotalmoney(new BigDecimal(planStruc.getTotamount()).divide(new BigDecimal(10000), 2, RoundingMode.HALF_UP).floatValue());
				result.setTraceStop(planStruc.getStopMode());
				result.setBonus(new BigDecimal(planStruc.getTotalWin()==null ? 0 : planStruc.getTotalWin()).divide(new BigDecimal(10000), 4, RoundingMode.HALF_UP).doubleValue());
				result.setLotteryId(planStruc.getLotteryid());
				
				
				List<TaskDetailProject> projects = new ArrayList<TaskDetailProject>();
				List<TaskDetailTasks> taskList = new ArrayList<TaskDetailTasks>();
				
				if(null != gamePlanDetail.getPlanSlipsStrucs() && !gamePlanDetail.getPlanSlipsStrucs().isEmpty()){
					for(SlipsStruc struc : gamePlanDetail.getPlanSlipsStrucs()){
						
						TaskDetailProject project = new TaskDetailProject();
						project.setCodedetails(struc.getBetDetail());
						project.setMethodid(struc.getGameGroupCode()+"_"+ struc.getGameSetCode()+"_"+ struc.getBetMethodCode());
						project.setModes(struc.getMoneyMode()==2 ? "角":(struc.getMoneyMode()==3 ?"分":"元"));
						project.setNums(struc.getTotbets());
						project.setMultiple(struc.getMultiple());
//						project.setBounees(new BigDecimal(struc.getAward()==null ? 0 : struc.getAward()).divide(new BigDecimal(10000),4, RoundingMode.HALF_UP).doubleValue());
						projects.add(project);
					}
				}
				
				result.setProjectList(projects);
				
				if(null != gamePlanDetail.getPlansFuturesStrucs() && !gamePlanDetail.getPlansFuturesStrucs().isEmpty()){
					
					for(PlansFuturesStruc struc : gamePlanDetail.getPlansFuturesStrucs()){
						
						
						TaskDetailTasks task = new TaskDetailTasks();
						task.setTaskdetailid(String.valueOf(struc.getPlanDetailsId()));
						task.setIssue(struc.getWebIssueCode());
						task.setMultiple(struc.getMutiple());
						task.setMoney(new BigDecimal(struc.getTotamount()).divide(new BigDecimal(10000), 4, RoundingMode.HALF_UP).floatValue());
						task.setOpencode(struc.getNumberRecord()); 
//						task.setTime(struc.get);//无此字段。
						///0：否1：是
						task.setCancancel(struc.getStatus()==0 ? 1 : 0);
//						task.setCancancel(struc.getCanCancel()==true ? 1 : 0);
						task.setIssueCode(struc.getIssueCode());
						boolean kk = true;
						for(OrdersStruc orders : gamePlanDetail.getOrdersStrucs()){
							if(orders.getIssueCode().longValue()==struc.getIssueCode().longValue()){
								task.setStatus(orders.getStatus());
								task.setTaskDetailNo(orders.getOrderCode());
								task.setBonus(new BigDecimal(orders.getTotwin()==null ? 0 : orders.getTotwin()).divide(new BigDecimal(10000),4, RoundingMode.HALF_UP).doubleValue());
								kk = false;
								//add
								GameOrderDetailQueryRequest orderDetailRequest = new GameOrderDetailQueryRequest();
								orderDetailRequest.setOrderId(orders.getOrderId());
								Response<GameOrderDetailQueryResponse> OrdergameResponse =  httpClient.invokeHttp(gameUrl + gameDetailUrl, orderDetailRequest, new Pager(1,10000),ut.getUserId(),ut.getUserName(), new TypeReference<Response<GameOrderDetailQueryResponse>>() {});
								
								GameOrderDetailQueryResponse orderDetail =  OrdergameResponse.getBody().getResult();
								List<GameDetailProject> list = new ArrayList<GameDetailProject>(); 
								if(null != OrdergameResponse.getBody().getResult() && null != OrdergameResponse.getBody().getResult().getSlipsStruc()){
									if(null != orderDetail.getSlipsStruc()){
										for(SlipsStruc slip : orderDetail.getSlipsStruc()){
											
											GameDetailProject project = new GameDetailProject();
											project.setCodedetails(slip.getBetDetail());
											project.setMethodid(slip.getGameGroupCode()+"_"+slip.getGameSetCode()+"_"+slip.getBetMethodCode());
											project.setNums(slip.getTotbets());
											project.setMultiple(slip.getMultiple());
											project.setPrice(new BigDecimal(slip.getTotamount()==null ? 0 : slip.getTotamount()).divide(new BigDecimal(10000),2,RoundingMode.HALF_UP).floatValue());
											project.setModes(slip.getMoneyMode()==2 ? "角":(slip.getMoneyMode()==3?"分":"元"));
											Integer ifwin = slip.getStatus();
											project.setBonus(new BigDecimal(slip.getAward()==null ? 0 : slip.getAward()).divide(new BigDecimal(10000),2,RoundingMode.HALF_UP).doubleValue());
											//0：未开奖1：未中奖2：派奖中3：已派奖4：撤銷5：存在異常	
											if(slip.getStatus()==2){
												ifwin = 3;
											}else if(slip.getStatus()==3){
												ifwin = 1;
											}else if(slip.getStatus()==1){
												ifwin = 0;
											}
											project.setIfwin(ifwin);
											list.add(project);
										}
									}
								}
								task.setList(list);
							}
						}
						
						if(kk){
							task.setBonus(new BigDecimal(struc.getTotwin()==null ? 0 : struc.getTotwin()).divide(new BigDecimal(10000),4, RoundingMode.HALF_UP).doubleValue());;
							task.setStatus(struc.getStatus());
						}
						
						taskList.add(task);
						
					}
				}
				result.setTasks(taskList);
			}else{
				response.getHead().setStatus(902001L);
			}
			
			response.setResult(result);
			
		} catch (Exception e) {
			log.error("taskDetail error:", e);
			response.getHead().setStatus(901000L);
		}
		
		return response;
	}
	
	
	
	
}
