package com.winterframework.firefrog.user.web.controller;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.core.type.TypeReference;
import com.winterframework.firefrog.common.http.IHttpJsonClient;
import com.winterframework.firefrog.user.web.dto.RetElectronicRetRequest;
import com.winterframework.firefrog.user.web.dto.RetElectronicRetResult;
import com.winterframework.firefrog.user.web.dto.SubCheckStatusRequest;
import com.winterframework.firefrog.user.web.dto.SubCheckStatusResult;
import com.winterframework.modules.spring.exetend.PropertyConfig;
import com.winterframework.modules.web.jsonresult.Response;
import com.winterframework.modules.web.util.RequestContext;

@Controller("subCheckController")
@RequestMapping(value = "/user/sub")
public class SubCheckController {

	private static final Logger logger = LoggerFactory
			.getLogger(SubCheckController.class);

	@Resource(name = "httpJsonClientImpl")
	private IHttpJsonClient httpClient;

	@PropertyConfig(value = "url.wg.api.server.path")
	private String wgURL;
	
	@PropertyConfig(value = "url.wanguo.checkUser")
	private String wgCheckURL;

	/**
	 * 
	* @Title: checkSubStatus 
	* @Description: 查詢外部系統權限
	* @param request
	* @return
	* @throws Exception
	 */
	@RequestMapping(value = "/checkSubStatus")
	@ResponseBody
	public Long checkSubStatus()throws Exception {
		logger.info("check subsystem status start");
		Response<SubCheckStatusResult> responseWgStatus = new Response<SubCheckStatusResult>();
		
		SubCheckStatusRequest  subRequest = new SubCheckStatusRequest();
		Long userId = RequestContext.getCurrUser().getId();
		subRequest.setUserid(userId);
		
		responseWgStatus = httpClient.invokeHttp(wgURL + wgCheckURL,subRequest , userId, null, SubCheckStatusResult.class);
		
		logger.info("responseWgStatus.getBody().getResult().getStatus()="+responseWgStatus.getBody().getResult().getStatus());
		Long status = responseWgStatus.getBody().getResult().getStatus();
//		if (responseWgStatus != null &&  responseWgStatus.getBody() != null &&  responseWgStatus.getBody().getResult().getStatus() == 1){
//			model.addAttribute("wgStatus", responseWgStatus.getBody().getResult().getStatus());
//		}
		

		logger.info("check subsystem status end");

		return status;
	}
	
	
	/**
	 * 
	* @Title: queryGameInfo
	* @Description: 查詢遊戲玩法
	* @param request
	* @return 
	* @throws Exception
	 */
	@RequestMapping("/queryGameInfo")
	@ResponseBody
	public Response<RetElectronicRetResult> queryGameInfo(Long lotteryId,String account) throws Exception {

		logger.info("queryGameInfo=Start:");
		
		String gameName = "";
		Long userId = RequestContext.getCurrUser().getId();
		String userName = RequestContext.getCurrUser().getUserName();
		
		logger.info("=userId:" + userId);
		logger.info("=userName:" + userName);
		logger.info("=lotteryId:" + lotteryId);
		logger.info("=account:" + account);
		
		RetElectronicRetRequest request = new RetElectronicRetRequest();
		request.setUserId(userId);
		request.setLotteryId(lotteryId);
		request.setAccount(account);
		
		Response<RetElectronicRetResult> resp = httpClient.invokeHttp(wgURL + "/ret/firefrog/getUserElectronicRet",
				request, userId, userName,new TypeReference<Response<RetElectronicRetResult>>() {});
		logger.info("getRetCode="+resp.getBody().getResult().getRetCode());
		logger.info("queryGameInfo==End:");

		return resp;
	}
}
