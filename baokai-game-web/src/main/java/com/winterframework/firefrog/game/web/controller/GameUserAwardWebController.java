package com.winterframework.firefrog.game.web.controller;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.winterframework.firefrog.common.httpjsonclient.IHttpJsonClient;
import com.winterframework.firefrog.common.util.DataConverterUtil;
import com.winterframework.firefrog.game.web.dto.AjaxResponse;
import com.winterframework.firefrog.game.web.dto.GameUserAwardRequest;
import com.winterframework.modules.spring.exetend.PropertyConfig;
import com.winterframework.modules.web.jsonresult.Response;
 
/**
 * 用户奖金WebController 
 * @ClassName
 * @Description
 * @author ibm
 * 2014年11月18日
 */
@RequestMapping(value = "/gameUserCenter")
@Controller("gameUserAwardWebController")
public class GameUserAwardWebController {

	private Logger logger = LoggerFactory.getLogger(GameUserAwardWebController.class);

	@Resource(name = "httpJsonClientImpl")
	private IHttpJsonClient httpClient;

	@PropertyConfig(value = "url.connect")
	private String serverPath; 

	@PropertyConfig(value = "url.game.modifyGameUserAward")
	private String modifyGameUserAwardUrl; 
 
	/**
	 * 
	* @Title: modifyRet 
	* @Description: 修改返点
	* @param request
	* @return
	* @throws Exception
	 */
	@RequestMapping(value = "/modifyUserAward")
	@ResponseBody
	public Object modifyUserAward(@RequestBody String json) throws Exception { 
		GameUserAwardRequest request=(GameUserAwardRequest)DataConverterUtil.convertJson2Object(json,GameUserAwardRequest.class);
		AjaxResponse resp = new AjaxResponse(); 
		logger.info("modify ret start");
		try { 
			httpClient.invokeHttp(serverPath + modifyGameUserAwardUrl, request, Object.class);
			resp.setStatus(1L); 
			resp.setMessage("success");
		} catch (Exception e) {
			logger.error("modify ret error");
			resp.setStatus(2L); 
			resp.setMessage(e.getMessage());
		}

		logger.info("modify ret end");

		return resp;
	} 
}
