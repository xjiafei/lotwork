package com.winterframework.firefrog.beginmession.web;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.winterframework.firefrog.beginmession.dao.vo.BeginNewCharge;
import com.winterframework.firefrog.beginmession.dao.vo.BeginNewMission;
import com.winterframework.firefrog.beginmession.service.BeginNewMissionService;
import com.winterframework.firefrog.beginmession.web.dto.BeginNewMissionRequest;
import com.winterframework.firefrog.beginmession.web.dto.BeginNewMissionResponse;
import com.winterframework.firefrog.common.annotation.ValidRequestBody;
import com.winterframework.firefrog.common.util.DateUtils;
import com.winterframework.modules.web.jsonresult.Request;
import com.winterframework.modules.web.jsonresult.Response;

@Controller
@RequestMapping("/begin")
public class BeginNewMissionController {

	private static final Logger log = LoggerFactory.getLogger(BeginNewMissionController.class);
	
	@Autowired
	private BeginNewMissionService beginNewMissionService;
	
	@RequestMapping("/insertNewMission")
	public void insertNewMission(@RequestBody @ValidRequestBody Request<BeginNewMissionRequest> request)throws Exception{
		
		try {
			if (null != request.getBody()) {
				final String userAccount = request.getBody().getParam().getUserName();
				BeginNewMissionRequest req = request.getBody().getParam();
				BeginNewMission mission = req.getMission();
				mission.setCreateUser(userAccount);
				mission.setModifyUser(userAccount);
				mission.setCreateTime(DateUtils.currentDate());
				mission.setModifyTime(DateUtils.currentDate());
				
				List<BeginNewCharge> charges = Lists.transform(req.getCharges(), new Function<BeginNewCharge,BeginNewCharge>(){

					@Override
					public BeginNewCharge apply(BeginNewCharge chareg) {
						chareg.setCreateUser(userAccount);
						chareg.setModifyUser(userAccount);
						chareg.setCreateTime(DateUtils.currentDate());
						chareg.setModifyTime(DateUtils.currentDate());
						return chareg;
					}
				});
				req.setCharges(charges);
				req.setMission(mission);
				
				beginNewMissionService.insertNewMission(req);
			}
		} catch (Exception e) {
			log.error("insertNewMission error:", e);
			throw e;
		}
		
	}
	
	@RequestMapping("/searchMission")
	@ResponseBody
	public Response<BeginNewMissionResponse> selectNewMissionMaxVersion(@RequestBody @ValidRequestBody Request<BeginNewMissionRequest> request)throws Exception{
		
		Response<BeginNewMissionResponse> response = new Response<BeginNewMissionResponse>(request);
		try {
			if (null != request.getBody()) {
				BeginNewMissionResponse dtoRes = beginNewMissionService.selectNewMissionMaxVersion();
				response.setResult(dtoRes);
			}
		} catch (Exception e) {
			log.error(" searchQuestion error:", e);
			throw e;
		}
		return response;
	}
}
