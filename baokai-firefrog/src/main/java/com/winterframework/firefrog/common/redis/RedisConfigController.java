package com.winterframework.firefrog.common.redis;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.winterframework.firefrog.common.annotation.ValidRequestBody;
import com.winterframework.firefrog.fund.web.dto.ConfigRedisRequestDTO;
import com.winterframework.firefrog.fund.web.dto.ConfigRedisResponse;
import com.winterframework.modules.web.jsonresult.Request;
import com.winterframework.modules.web.jsonresult.Response;

@Controller("redisConfigController")
@RequestMapping(value = "/common")
public class RedisConfigController {

	private static final Logger log = LoggerFactory.getLogger(RedisConfigController.class);
	@Resource(name = "RedisClient")
	private RedisClient redisService;

	/** 
	* @Title: getRedisConfig 
	* @Description: 获取redis中的配置信息
	* @param request
	* @return
	* @throws Exception
	*/
	@RequestMapping(value = "/getRedisValue")
	@ResponseBody
	public Response<ConfigRedisResponse> getRedisConfig(
			@RequestBody @ValidRequestBody Request<ConfigRedisRequestDTO> request) throws Exception {
		Response<ConfigRedisResponse> response = new Response<ConfigRedisResponse>(request);
		try {
			String str = redisService.get(request.getBody().getParam().getModule() + "-"
					+ request.getBody().getParam().getKey() + "-" + request.getBody().getParam().getFunction());
			ConfigRedisResponse configRedisResponse = new ConfigRedisResponse();
			configRedisResponse.setVal(str);
			response.setResult(configRedisResponse);
		} catch (Exception e) {
			log.error("getRedisConfig error.", e);
			throw e;
		}
		return response;
	}

}
