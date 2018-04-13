package com.winterframework.firefrog.phone.web.controller;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.core.type.TypeReference;
import com.winterframework.firefrog.common.httpjsonclient.IHttpJsonClient;
import com.winterframework.firefrog.game.web.dto.QueryChartRequest;
import com.winterframework.firefrog.game.web.dto.QueryChartResponse;
import com.winterframework.firefrog.phone.web.chart.AbstractChartTransHandler;
import com.winterframework.firefrog.phone.web.chart.ChartStruc;
import com.winterframework.firefrog.phone.web.chart.ChartTransHandlerFactory;
import com.winterframework.firefrog.phone.web.cotroller.dto.ChartRequest;
import com.winterframework.firefrog.phone.web.cotroller.dto.ChartResponse;
import com.winterframework.modules.spring.exetend.PropertyConfig;
import com.winterframework.modules.web.jsonresult.Request;
import com.winterframework.modules.web.jsonresult.Response;

 
/**
 *ChartController
 * @ClassName
 * @Description
 * @author ibm
 * 2017年10月23日
 */
@Controller("chartController")
@RequestMapping("/chart")
public class ChartController extends BaseController{
//	private Logger logger = Logger.getLogger(ChartController.class);
	private static final Logger logger = LoggerFactory.getLogger(ChartController.class);
	@Resource(name = "httpJsonClientImpl")
	private IHttpJsonClient httpClient;
	
	@PropertyConfig(value = "url.chart")
	protected String chartUrl;
	@PropertyConfig(value = "url.chart.queryChart")
	private String queryChartUrl;
	
	@ResponseBody
	@RequestMapping(value = "/chartData")
	public Response<ChartResponse> chartData(@RequestBody Request<ChartRequest> request) throws Exception {
		Response<ChartResponse> response=new Response<ChartResponse>(request);
		String periodsType=request.getBody().getParam().getPeriodsType();
		Long lotteryId=request.getBody().getParam().getLotteryId();
		Integer periodsNum=request.getBody().getParam().getPeriodsNum();
		String gameMethod=request.getBody().getParam().getGameMethod();
		int type = 0;
		if ("periods".equals(periodsType)) {
			type = 1;
		} else if ("day".equals(periodsType) || "time".equals(periodsType)) {
			type = 2;
		}
		QueryChartRequest bizReq=new QueryChartRequest();
		bizReq.setLotteryId(lotteryId);
		bizReq.setType(type);
		bizReq.setPeriodsNum(periodsNum);
		bizReq.setGameMethod(gameMethod);
		
		Response<QueryChartResponse> res = httpClient.invokeHttp(chartUrl + queryChartUrl, bizReq, new TypeReference<Response<QueryChartResponse>>(){});
		
		if(res!=null && res.getBody().getResult()!=null){
			QueryChartResponse bizRes= res.getBody().getResult();
			AbstractChartTransHandler transHandler=ChartTransHandlerFactory.getHandler(lotteryId,isNeedHandle(lotteryId, gameMethod)?gameMethod:"DEFAULT");
			ChartResponse bizResponse=new ChartResponse();
			if(null!=transHandler){
				ChartStruc chartStruc=transHandler.handle(lotteryId, gameMethod, bizRes.getTrendChartStruc());
				bizResponse.setData(chartStruc.getData());
				bizResponse.setStatistics(chartStruc.getStatistics()); 
			}
			response.setResult(bizResponse);
		}
		return response;
	}
	private boolean isNeedHandle(Long lotteryId,String gameMethod){
		if((!String.valueOf(lotteryId).substring(0,3).equals("993") && gameMethod.equals("Wuxing"))
				|| gameMethod.equals("p5chart") || gameMethod.equals("Sixing")){
			return false;
		}
		return true;
	}
	
}
