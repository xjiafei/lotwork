package com.winterframework.firefrog.game.web.controller;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.winterframework.firefrog.common.httpjsonclient.IHttpJsonClient;
import com.winterframework.firefrog.common.redis.RedisClient;
import com.winterframework.firefrog.game.dao.vo.ActivityConfig;
import com.winterframework.firefrog.game.web.bet.operator.IBetOperation;
import com.winterframework.firefrog.game.web.bet.util.BetHttpJsonClient;
import com.winterframework.firefrog.game.web.dto.ActivityConfigRequest;
import com.winterframework.firefrog.game.web.dto.ActivityConfigResponse;
import com.winterframework.firefrog.game.web.dto.MmcRankingHistoryResponse;
import com.winterframework.firefrog.game.web.dto.MmcRankingRequest;
import com.winterframework.firefrog.game.web.dto.MmcRankingResponse;
import com.winterframework.modules.spring.exetend.PropertyConfig;
import com.winterframework.modules.web.jsonresult.Response;
import com.winterframework.modules.web.util.RequestContext;

/**
 * @Title 投注控制器
 * @Description 
 * 	
 * 	1. 加载投注页面对象及显示投注页面
 * 	2. 加载页面渲染所需配置或数据
 * 	3. 单式上传文件接收及解析
 * 	4. 显示撤单费用
 * 	5. 投注提交
 * 
 * 	此控制器只定义通用方法，如彩系彩种需要一些特殊的方法，可重载此控制器。
 * 
 * @author bob
 *
 */
@Controller("MmcRankingController")
@RequestMapping(value = "/mmcRank")
public class MmcRankingController {
	private Logger logger = LoggerFactory.getLogger(MmcRankingController.class);

	@Resource(name = "httpJsonClientImpl")
	private IHttpJsonClient httpClient;

	@PropertyConfig(value = "url.connect")
	private String serverPath;

	@PropertyConfig(value = "url.operations.queryActivityConfig")
	private String queryActivityConfigUrl;

	@PropertyConfig(value = "url.operations.updateActivityConfig")
	private String updateActivityConfigUrl;
	
	@PropertyConfig(value = "url.operations.getActivityConfig")
	private String getActivityConfig;
	
	@PropertyConfig(value = "url.operations.getMMCRankingHistory")
	private String getMMCRankingHistory;
	
	@PropertyConfig(value = "url.operations.getMMCRanking")
	private String getMMCRanking;

	@RequestMapping(value = "/queryRankingIsOpen")
	@ResponseBody
	public Boolean queryRankingIsOpen() throws Exception {
		Response<ActivityConfig> response = new Response<ActivityConfig>();
		ActivityConfigRequest query = new ActivityConfigRequest();
		
		query.setId(152l);
		logger.info("query queryRankingIsOpen start");
		Date nowDate = new Date();
		try {
			response = httpClient.invokeHttp(serverPath + getActivityConfig, query,
					ActivityConfig.class);
			if(nowDate.before(response.getBody().getResult().getBeginTime()) ){
				logger.info("秒秒風雲榜活動日期未開始");
				return false;
			}
			if(nowDate.after(response.getBody().getResult().getEndTime()) ){
				logger.info("秒秒風雲榜活動日期已結束");
				return false;
			}
			
		} catch (Exception e) {
			logger.error("query queryRankingIsOpen error");
			throw e;
		}
		logger.info("query queryRankingIsOpen end");
		
		return true;
	
	}
	
	
	@RequestMapping(value = "/queryRanking")
	@ResponseBody
	public MmcRankingResponse queryRanking() throws Exception {
		Response<MmcRankingResponse> response = new Response<MmcRankingResponse>();
		MmcRankingRequest query = new MmcRankingRequest();
		
		query.setAccount(RequestContext.getCurrUser().getUserName());
		logger.info("query queryRanking start");
		
		try {
			if(!queryRankingIsOpen()){
				MmcRankingResponse noOpenResponse = new MmcRankingResponse();
				noOpenResponse.setStart(false);
				response.setResult(noOpenResponse);
				return noOpenResponse;
			}
			response = httpClient.invokeHttp(serverPath + getMMCRanking, query,
					MmcRankingResponse.class);
			response.getBody().getResult().setStart(true);
			
		} catch (Exception e) {
			logger.error("query queryRanking error");
			throw e;
		}
		logger.info("query queryRanking end");
		
		return response.getBody().getResult();
	
	}
	
	@RequestMapping(value = "/queryHistory")
	@ResponseBody
	public MmcRankingHistoryResponse queryHistory() throws Exception {
		Response<MmcRankingHistoryResponse> response = new Response<MmcRankingHistoryResponse>();
		MmcRankingRequest query = new MmcRankingRequest();
		
		query.setAccount(RequestContext.getCurrUser().getUserName());
		logger.info("query queryRankingHistory start");
		
		try {
			if(!queryRankingIsOpen()){
				return null;
			}
			response = httpClient.invokeHttp(serverPath + getMMCRankingHistory, query,
					MmcRankingHistoryResponse.class);
		} catch (Exception e) {
			logger.error("query queryRankingHistory error");
			throw e;
		}
		logger.info("query queryRankingHistory end");
		
		return response.getBody().getResult();
	
	}
	
	@RequestMapping(value = "/mmcRankingInfo")
	public String get2000info(Model model) throws Exception {
		
	//	ModelAndView mav = new ModelAndView("bet/2000info/2000infoshow");
		return "/active/mmcRanking/rankingInfo";
	}
}
