package com.winterframework.firefrog.version.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.winterframework.modules.spring.exetend.PropertyConfig;
import com.winterframework.modules.web.jsonresult.Response;

/** 
* @ClassName: UPVersionController 
* @Description: 投注限制请求web controller
* @author Alan
* @date 2013-9-23 下午3:01:15 
*  
*/
@Controller("upversionController")
@RequestMapping(value = "/getVersion")
public class UPVersionController {

	private Logger logger = LoggerFactory.getLogger(UPVersionController.class);

	@PropertyConfig(value = "user.phone.version")
	private String userphoneversion;
	
	@PropertyConfig(value = "user.api.svn")
	private String userapisvn;
	
	@RequestMapping(value = "/showVersion")
	@ResponseBody
	public Response<String> toShowVersion() throws Exception {
		Response <String> response = new  Response <String>();
		try{
			response.setResult(userphoneversion + userapisvn);
		}catch(Exception e){
			throw e;
		}
		
		return response;
	}
	

}
