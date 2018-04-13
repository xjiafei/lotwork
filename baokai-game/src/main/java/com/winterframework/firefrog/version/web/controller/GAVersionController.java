package com.winterframework.firefrog.version.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.winterframework.modules.spring.exetend.PropertyConfig;
import com.winterframework.modules.web.jsonresult.Request;
import com.winterframework.modules.web.jsonresult.Response;

/** 
* @ClassName: GAVersionController 
* @Description: 投注限制请求web controller
* @author Alan
* @date 2013-9-23 下午3:01:15 
*  
*/
@Controller("gaversionController")
@RequestMapping(value = "/getVersion")
public class GAVersionController {

	private Logger logger = LoggerFactory.getLogger(GAVersionController.class);

	@PropertyConfig(value = "game.api.version")
	private String gameapiversion;
	@PropertyConfig(value = "user.api.svn")
	private String userapisvn;
	
	@RequestMapping(value = "/showVersion")
	@ResponseBody
	public Response<String> toShowVersion() throws Exception {
		Response <String> response = new  Response <String>();
		try{
			response.setResult(gameapiversion+userapisvn);
		}catch(Exception e){
			throw e;
		}
		
		return response;
	}	

	

}
