package com.winterframework.firefrog.version.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.winterframework.modules.spring.exetend.PropertyConfig;
import com.winterframework.modules.web.jsonresult.Response;

/** 
* @ClassName: GTVersionController 
* @Description: 投注限制请求web controller
* @author Alan
* @date 2013-9-23 下午3:01:15 
*  
*/
@Controller("gtversionController")
@RequestMapping(value = "/getVersion")
public class GTVersionController {

	private Logger logger = LoggerFactory.getLogger(GTVersionController.class);

	@PropertyConfig(value = "game.task.version")
	private String gametaskversion;
	@PropertyConfig(value = "user.api.svn")
	private String userapisvn;
	
	@RequestMapping(value = "/showVersion")
	@ResponseBody
	public Response<String> toShowVersion() throws Exception {
		Response <String> response = new  Response <String>();
		try{
			response.setResult(gametaskversion + userapisvn);
		}catch(Exception e){
			throw e;
		}
		
		return response;
	}
	

}
