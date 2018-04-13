package com.winterframework.firefrog.version.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.winterframework.modules.spring.exetend.PropertyConfig;
import com.winterframework.modules.web.jsonresult.Response;

/** 
* @ClassName: GCVersionController 
* @Description: 投注限制请求web controller
* @author Alan
* @date 2013-9-23 下午3:01:15 
*  
*/
@Controller("gcversionController")
@RequestMapping(value = "/game/chart")
public class GCVersionController {

	private Logger logger = LoggerFactory.getLogger(GCVersionController.class);

	@PropertyConfig(value = "game.chart.version")
	private String gamechartversion;
	
	@PropertyConfig(value = "user.api.svn")
	private String userapisvn;
	@RequestMapping(value = "/showVersion")
	@ResponseBody
	public Response<String> toShowVersion() throws Exception {
		Response <String> response = new  Response <String>();
		try{
			response.setResult(gamechartversion + userapisvn);
		}catch(Exception e){
			throw e;
		}
		
		return response;
	}

}
