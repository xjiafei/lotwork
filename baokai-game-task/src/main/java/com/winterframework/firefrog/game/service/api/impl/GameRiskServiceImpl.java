package com.winterframework.firefrog.game.service.api.impl;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.core.type.TypeReference;
import com.winterframework.firefrog.common.httpjsonclient.IHttpJsonClient;
import com.winterframework.firefrog.game.service.api.IGameRiskService;
import com.winterframework.modules.spring.exetend.PropertyConfig;
import com.winterframework.modules.web.jsonresult.Request;
import com.winterframework.modules.web.jsonresult.Response;

/**
 * Risk项目接口实现类
 * 
 * @ClassName
 * @Description
 * @author ibm 2014年10月16日
 */
@Service("gameRiskServiceImplApi")
@Transactional
public class GameRiskServiceImpl implements IGameRiskService {

	private static final Logger log = LoggerFactory
			.getLogger(GameRiskServiceImpl.class);

	@Resource(name = "httpJsonClientImpl")
	private IHttpJsonClient httpClient;

	@PropertyConfig(value = "url.connect")
	private String serverPath;
	// @PropertyConfig(value = "/gameRisk/queryGameSeriesConfigRisk")
	private String exampleUrl;

	@Override
	public void doExample(Long lotteryId) throws Exception {
		Request request = new Request();
		Response response = null;
		response = httpClient.invokeHttp(serverPath + exampleUrl, request,
				new TypeReference<Response>() {

				});
	}

}
