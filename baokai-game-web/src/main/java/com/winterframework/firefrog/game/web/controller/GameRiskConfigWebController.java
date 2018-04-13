/**   
* @Title: GameSeriesConfigRiskWebController.java 
* @Package com.winterframework.firefrog.game.web.controller 
* @Description: TODO(用一句话描述该文件做什么) 
* @author 你的名字   
* @date 2014-2-21 上午11:15:14 
* @version V1.0   
*/
package com.winterframework.firefrog.game.web.controller;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.winterframework.firefrog.common.httpjsonclient.IHttpJsonClient;
import com.winterframework.firefrog.game.web.dto.EditSeriesConfigRiskRequest;
import com.winterframework.firefrog.game.web.dto.OperateStatusResponse;
import com.winterframework.firefrog.game.web.dto.QuerySeriesConfigRiskRequest;
import com.winterframework.firefrog.game.web.dto.QuerySeriesConfigRiskResponse;
import com.winterframework.modules.spring.exetend.PropertyConfig;
import com.winterframework.modules.web.jsonresult.Response;


/** 
* @ClassName GameRiskConfigWebController 
* @Description 审核系统  审核配置 
* @author  hugh
* @date 2014年4月17日 上午11:24:25 
*  
*/
@Controller("gameRiskConfigWebController")
@RequestMapping(value = "/gameRisk")
public class GameRiskConfigWebController {
	
	private Logger logger = LoggerFactory.getLogger(GameRiskConfigWebController.class);
	
	@Resource(name = "httpJsonClientImpl")
	private IHttpJsonClient httpClient;
	
	@PropertyConfig(value = "url.connect.risk")
	private String serverPath;
	
	@PropertyConfig(value = "url.game.risk.queryGameSeriesConfigRisk")
	private String queryGameSeriesConfigRiskUrl;
	
	@PropertyConfig(value = "url.game.risk.modifyGameSeriesConfigRisk")
	private String modifyGameSeriesConfigRiskUrl;
		
	/**
	* @Title: querySeriesConfig 
	* @Description: 根据lotteryid(彩种ID)&status(状态)查询参数设置（审核模块）
	* @param lotteryid
	* @return Response<QuerySeriesConfigRiskResponse>
	* @throws Exception
	 */
	private Response<QuerySeriesConfigRiskResponse> querySeriesConfigRisk(Long lotteryId) throws Exception {
		Response<QuerySeriesConfigRiskResponse> response = new Response<QuerySeriesConfigRiskResponse>();
		
		QuerySeriesConfigRiskRequest request = new QuerySeriesConfigRiskRequest();
		request.setLotteryid(lotteryId);
		logger.info("query series config start...");
		try{
			response = httpClient.invokeHttp(serverPath + queryGameSeriesConfigRiskUrl, request, QuerySeriesConfigRiskResponse.class);
		}catch(Exception e){
			logger.error("query series config error...");
			throw e;
		}
		logger.info("query series config end...");
		
		return response;
	}
	
	/**
	* @Title: toSeriesConfig 
	* @Description: 访问参数设置页面
	* @param lotteryid
	* @param model
	* @throws Exception
	 */
	@RequestMapping(value = "toSeriesConfigRisk")
	public String toSeriesConfigRisk(Model model , Long lotteryid) throws Exception {
		if(lotteryid == null){
			lotteryid = 99101L;
		}
		Response<QuerySeriesConfigRiskResponse> response = querySeriesConfigRisk(lotteryid);
		
		model.addAttribute("seriesConfigRisk", response.getBody().getResult());
		model.addAttribute("lotteryid", lotteryid);
		return "/risk/seriesConfigRiskSetting";
	}
	
	/**
	* @Title: toModifySeriesConfig 
	* @Description: 访问参数设置修改页面
	* @param lotteryid
	* @param model
	* @throws Exception
	 */
	@RequestMapping(value = "toModifySeriesConfigRisk")
	public String toModifySeriesConfigRisk(Model model, Long lotteryid) throws Exception {
		if(lotteryid == null){
			lotteryid = 99101L;
		}
		Response<QuerySeriesConfigRiskResponse> response = querySeriesConfigRisk(lotteryid);
		
		model.addAttribute("seriesConfigRisk", response.getBody().getResult());
		model.addAttribute("pageType", "modify");
		model.addAttribute("lotteryid", lotteryid);
		return "/risk/seriesConfigRiskSetting";
	}

	/**
	* @Title: modifySeriesConfig 
	* @Description: 修改参数信息
	* @param modifyForm
	* @param model
	* @return
	* @throws Exception
	 */
	@RequestMapping(value = "modifySeriesConfigRisk")
	@ResponseBody
	public Object modifySeriesConfigRisk(@ModelAttribute("modifyForm") EditSeriesConfigRiskRequest modifyForm, Model model) throws Exception {
//		if(modifyForm.getOrderwarnContinuousWins()==null){
//			modifyForm.setOrderwarnContinuousWins(0L);
//		}else if(modifyForm.getOrderwarnMaxslipWins()==null){
//			modifyForm.setOrderwarnMaxslipWins(0L);
//		}else if(modifyForm.getOrderwarnMaxwins()==null){
//			modifyForm.setOrderwarnMaxwins(0L);
//		}else if(modifyForm.getOrderwarnSlipWinsRatio()==null){
//			modifyForm.setOrderwarnSlipWinsRatio(0L);
//		}else if(modifyForm.getOrderwarnWinsRatio()==null){
//			modifyForm.setOrderwarnWinsRatio(0L);
//		}
		
		OperateStatusResponse resp = new OperateStatusResponse();
		resp.setStatus(1);
		logger.info("modify series config start...");
		try{
			httpClient.invokeHttp(serverPath + modifyGameSeriesConfigRiskUrl, modifyForm, Object.class);
		}catch(Exception e){
			logger.error("modify series config error...");
			resp.setStatus(0);
			throw e;
		}
		logger.info("modify series config end...");
		
		
		//Response<QuerySeriesConfigRiskResponse> response = querySeriesConfigRisk();
		
		//model.addAttribute("seriesConfigRisk", response.getBody().getResult());

		return resp;
	}
	
	

	
	
	
}
