package com.winterframework.firefrog.version.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.core.type.TypeReference;
import com.winterframework.firefrog.common.annotation.ValidRequestBody;
import com.winterframework.firefrog.fund.web.dto.ConfigSaveRequestDTO;
import com.winterframework.firefrog.notice.web.dto.EmailConfigDto;
import com.winterframework.modules.spring.exetend.PropertyConfig;
import com.winterframework.modules.web.jsonresult.Request;
import com.winterframework.modules.web.jsonresult.Response;

/** 
* @ClassName: VersionController 
* @Description: 配置文件处理控制器 
* @author david
* @date 2013-7-4 下午5:52:42 
*  
*/
@Controller("uaversionController")
@RequestMapping(value = "/getVersion")
public class UAVersionController {

	private static final Logger log = LoggerFactory.getLogger(UAVersionController.class);
	@PropertyConfig(value = "user.api.version")
	private String userapiversion;
	@PropertyConfig(value = "user.api.svn")
	private String userapisvn;
	
	@RequestMapping(value = "/showVersion")
	@ResponseBody
	public Response<String> toShowVersion() throws Exception {
		Response <String> response = new  Response <String>();
		try{
		//	Manifests.append(servletContext);
		//	String revision = Manifests.read("App-Svn-Revision");
			response.setResult(userapiversion + userapisvn);
		}catch(Exception e){
			throw e;
		}
		
		return response;
	}


}
