package com.winterframework.firefrog.beginmession.web;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.winterframework.firefrog.beginmession.dao.vo.BeginDataCountVO;
import com.winterframework.firefrog.beginmession.service.BeginDataCountService;
import com.winterframework.firefrog.beginmession.web.dto.BeginDataCountRequest;
import com.winterframework.firefrog.beginmession.web.dto.BeginDataCountResponse;
import com.winterframework.firefrog.common.annotation.ValidRequestBody;
import com.winterframework.modules.web.jsonresult.Request;
import com.winterframework.modules.web.jsonresult.Response;

@Controller
@RequestMapping("/begin")
public class BeginDataCountController {


	private static final Logger log = LoggerFactory.getLogger(BeginMissionController.class);
	
	@Autowired
	private BeginDataCountService beginDataCountService;

	
	@RequestMapping(value="/getMissionReport")
	@ResponseBody
	public Response<BeginDataCountResponse> getMissionReport(@RequestBody @ValidRequestBody Request<BeginDataCountRequest> request)throws Exception{
		Response<BeginDataCountResponse> response = new Response<BeginDataCountResponse>(request);
		log.info("into getMissionReport ");
		try {
			if (null != request.getBody()) {
				BeginDataCountRequest req= request.getBody().getParam();
				log.info("into getMissionReport statttime={},endTime={}",new Object[]{req.getStartTime(),req.getEndTime()});
				SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
				Date statrTime= format.parse(req.getStartTime());
				format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
				Date endTime = format.parse(req.getEndTime()+"T23:59:59");
				BeginDataCountVO vo = beginDataCountService.getDataCount(statrTime, endTime);
				BeginDataCountResponse res = new BeginDataCountResponse();
				res.setVo(vo);
				response.setResult(res);
			}
		} catch (Exception e) {
			log.error("gotoNewMission error:", e);
			throw e;
		}
		return response;
	}
	
}
