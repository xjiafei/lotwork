package com.winterframework.firefrog.game.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.winterframework.modules.spring.exetend.PropertyConfig;
import com.winterframework.modules.web.jsonresult.Response;

/** 
* @ClassName: GWVersionController 
* @Description: 投注限制请求web controller
* @author Alan
* @date 2013-9-23 下午3:01:15 
*  
*/
@Controller("gwversionController")
@RequestMapping(value = "/getVersion")
public class GWVersionController {

	private Logger logger = LoggerFactory.getLogger(GWVersionController.class);

	@PropertyConfig(value = "game.web.version")
	private String gamewebversion;

	
	@PropertyConfig(value = "user.api.svn")
	private String userapisvn;
	
	@RequestMapping(value = "/showVersion")
	@ResponseBody
	public Response<String> toShowVersion() throws Exception {
		Response <String> response = new  Response <String>();

		try{
			response.setResult(gamewebversion + userapisvn);
		}catch(Exception e){
			throw e;
		}
		
		return response;
	}
	

}
