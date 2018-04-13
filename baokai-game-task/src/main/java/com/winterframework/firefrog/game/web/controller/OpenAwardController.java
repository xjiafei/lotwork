package com.winterframework.firefrog.game.web.controller;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.winterframework.firefrog.common.annotation.ValidRequestBody;
import com.winterframework.firefrog.common.util.ProcessResult;
import com.winterframework.firefrog.game.exception.GameRiskException;
import com.winterframework.firefrog.game.service.openaward.IOpenAwardService;
import com.winterframework.firefrog.game.web.dto.TaskOpenAwardRequest;
import com.winterframework.firefrog.game.web.dto.TaskOpenAwardResponse;
import com.winterframework.modules.web.jsonresult.Request;
import com.winterframework.modules.web.jsonresult.Response;

/** 
* @ClassName openAwardController 
* @Description 开奖接口 
* @author  hugh
* @date 2014年8月25日 上午11:02:13 
*  
*/
@Controller("openAwardContorller")
@RequestMapping("/taskGame")
public class OpenAwardController {

	private static final Logger log = LoggerFactory.getLogger(OpenAwardController.class);

	@Resource(name = "mmcOpenAwardService")
	private IOpenAwardService mmcOpenAwardService;

	/** 
	* @Title: mmcOpenAward 
	* @Description: 秒秒彩开奖接口
	* @param request
	* @return
	* @throws Exception
	*/
	@RequestMapping(value = "/mmcOpenAward")
	@ResponseBody
	public Response<TaskOpenAwardResponse> mmcOpenAward(
			@RequestBody @ValidRequestBody Request<TaskOpenAwardRequest> request) throws Exception {
		Long orderId = request.getBody().getParam().getOrderId();
		log.debug("秒秒彩开奖调用  orderId = " + orderId);
		Response<TaskOpenAwardResponse> response = new Response<TaskOpenAwardResponse>(request);
		TaskOpenAwardResponse res = new TaskOpenAwardResponse();

		try {
			ProcessResult result = mmcOpenAwardService.openAward(orderId);
			if (result.isSuccess()) {
				res.setResult(0);
				log.debug("秒秒彩开奖调用执行成功  orderId = " + orderId);
			} else {
				res.setResult(Integer.parseInt(result.getRetCode()));
				res.setMessage(result.getRetMsg());
				log.debug(result.getRetMsg());
			}
		} catch(GameRiskException e1){
			res.setResult(-4);
			res.setMessage("调用资金系统出错");
		} catch (Exception e) {
			res.setResult(-1000);
			String message = "秒秒彩开奖调用执行失败  orderId = " + orderId + " " + e.getMessage();
			res.setMessage(message);
			log.error(message, e);
		}

		response.setResult(res);
		return response;
	}

}
