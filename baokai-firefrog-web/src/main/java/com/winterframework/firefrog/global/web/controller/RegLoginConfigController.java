package com.winterframework.firefrog.global.web.controller;

import javax.annotation.Resource;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.core.type.TypeReference;
import com.winterframework.firefrog.common.http.IHttpJsonClient;
import com.winterframework.firefrog.global.web.dto.Config;
import com.winterframework.firefrog.global.web.dto.ConfigSaveRequestDTO;
import com.winterframework.firefrog.global.web.dto.DefaultConfig;
import com.winterframework.firefrog.global.web.dto.RegisterLoginConfigDto;
import com.winterframework.modules.spring.exetend.PropertyConfig;
import com.winterframework.modules.web.jsonresult.Response;

@Controller("RegLoginConfigController")
@RequestMapping(value = "/globeAdmin")
public class RegLoginConfigController {

	@PropertyConfig(value = "url.connect")
	private String urlPath;

	@PropertyConfig(value = "url.global.queryConfig")
	private String queryConfigUrl;

	@PropertyConfig(value = "url.global.saveConfig")
	private String saveConfigUrl;

	@Resource(name = "httpJsonClientImpl")
	private IHttpJsonClient httpClient;

	@RequestMapping(value ={"/goRegLoginConfig","/"})
	public ModelAndView goConfig() throws Exception {
		ModelAndView mav = new ModelAndView("global/regLoginConfig");
		Config conf = new Config();
		conf.setModule("global");
		conf.setFunction("login");
		Response<ConfigSaveRequestDTO<RegisterLoginConfigDto>> resp = httpClient.invokeHttp(urlPath + queryConfigUrl,conf,
				new TypeReference<Response<ConfigSaveRequestDTO<RegisterLoginConfigDto>>>() {
				});
		mav.addObject("config", resp.getBody().getResult().getVal());
		mav.addObject("global_no", 1);
		return mav;
	}

	@RequestMapping(value = "/saveRegLoginConfig")
	public ModelAndView saveConfig(@Valid RegisterLoginConfigDto config, Errors errors) throws Exception {
		DefaultConfig<RegisterLoginConfigDto> reqData = new DefaultConfig<RegisterLoginConfigDto>();
		reqData.setVal(config);
		reqData.setModule("global");
		reqData.setFunction("login");
		httpClient.invokeHttpWithoutResultType(urlPath + saveConfigUrl, reqData);
		return goConfig();
	}

}
