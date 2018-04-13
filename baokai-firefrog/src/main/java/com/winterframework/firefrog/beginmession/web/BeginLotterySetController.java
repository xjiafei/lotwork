package com.winterframework.firefrog.beginmession.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.winterframework.firefrog.beginmession.service.BeginLotterySetService;
import com.winterframework.firefrog.beginmession.web.dto.BeginLotterySetRequest;
import com.winterframework.firefrog.beginmession.web.dto.BeginLotterySetResponse;
import com.winterframework.firefrog.common.annotation.ValidRequestBody;
import com.winterframework.modules.web.jsonresult.Request;
import com.winterframework.modules.web.jsonresult.Response;

@Controller
@RequestMapping("/begin")
public class BeginLotterySetController {

	private static final Logger log = LoggerFactory.getLogger(BeginLotterySetController.class);
	
	@Autowired
	private BeginLotterySetService beginLotterySetServiceImpl;
	
	
	@RequestMapping("/insertLotterySet")
	public void insertLotterySet(@RequestBody @ValidRequestBody Request<BeginLotterySetRequest> request)throws Exception{
		
		try {
			if (null != request.getBody()) {
				BeginLotterySetRequest req = request.getBody().getParam();
				beginLotterySetServiceImpl.insert(req);
			}
		}catch (Exception e) {
			log.error("insertLotterySet error:", e);
			throw e;
		}
		
	}
	
	@RequestMapping("/searchLotterySet")
	@ResponseBody
	public Response<BeginLotterySetResponse> selectMaxVersion(@RequestBody @ValidRequestBody Request<BeginLotterySetRequest> request)throws Exception{
		
		Response<BeginLotterySetResponse> response = new Response<BeginLotterySetResponse>(request);
		try {
			if (null != request.getBody()) {
				BeginLotterySetResponse dtoRes = beginLotterySetServiceImpl.selectMaxVersion();
				response.setResult(dtoRes);
			}
		} catch (Exception e) {
			log.error(" searchLotterySet error:", e);
			throw e;
		}
		return response;
	}
	
}
