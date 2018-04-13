/**   
* @Title: UWVersionShowController.java 
* @Package com.winterframework.firefrog.version.web.controller;
* @Description: TODO(用一句话描述该文件做什么) 
* @author 你的名字   
* @date 2013-10-24 上午10:38:58 
* @version V1.0   
*/
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
* @ClassName: UWVersionController 
* @Description: TODO(这里用一句话描述这个类的作用) 
* @author 你的名字 
* @date 2013-10-24 上午10:38:58 
*  
*/

@Controller("uwversionController")
@RequestMapping(value = "/userWeb/getVersion")
public class UWVersionController {
	
	@PropertyConfig(value = "user.web.version")
	private String userwebversion;

	@PropertyConfig(value = "user.api.svn")
	private String userapisvn;
	
	private static final Logger logger = LoggerFactory.getLogger(UWVersionController.class);

	@RequestMapping(value = "/showVersion")
	@ResponseBody
	public Response<String> toShowVersion() throws Exception {
		Response <String> response = new  Response <String>();
		try{
			response.setResult(userwebversion + userapisvn);
		}catch(Exception e){
			throw e;
		}
		
		return response;
	}
	
	
}
