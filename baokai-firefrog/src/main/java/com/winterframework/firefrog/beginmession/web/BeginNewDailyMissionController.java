package com.winterframework.firefrog.beginmession.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.winterframework.firefrog.beginmession.dao.vo.BeginNewMission;
import com.winterframework.firefrog.beginmession.service.BeginNewDailyMissionService;
import com.winterframework.firefrog.beginmession.web.dto.BeginNewDailyMissionRequest;
import com.winterframework.firefrog.beginmession.web.dto.BeginNewDailyMissionResponse;
import com.winterframework.firefrog.common.annotation.ValidRequestBody;
import com.winterframework.firefrog.common.util.DateUtils;
import com.winterframework.modules.web.jsonresult.Request;
import com.winterframework.modules.web.jsonresult.Response;

@Controller
@RequestMapping("/begin")
public class BeginNewDailyMissionController {

	
	private static final Logger log = LoggerFactory.getLogger(BeginNewDailyMissionController.class);
	
	@Autowired
	private BeginNewDailyMissionService beginNewDailyMissionService;
	
	@RequestMapping("/insertDailyMission")
	public void insertNewMission(@RequestBody @ValidRequestBody Request<BeginNewDailyMissionRequest> request)throws Exception{
		
		try {
			if (null != request.getBody()) {
				final String userAccount = request.getBody().getParam().getUserName();
				BeginNewDailyMissionRequest req = request.getBody().getParam();
				BeginNewMission beginNewMission = req.getBeginNewMission();
				beginNewMission.setCreateUser(userAccount);
				beginNewMission.setModifyUser(userAccount);
				beginNewMission.setCreateTime(DateUtils.currentDate());
				beginNewMission.setModifyTime(DateUtils.currentDate());
				req.setBeginNewMission(beginNewMission);
				beginNewDailyMissionService.insertDailyMission(req);
			}
		} catch (Exception e) {
			log.error("insertDailyMission error:", e);
			throw e;
		}
		
	}
	
	@RequestMapping("/searchDailyMission")
	@ResponseBody
	public Response<BeginNewDailyMissionResponse> selectNewMissionMaxVersion(@RequestBody @ValidRequestBody Request<BeginNewDailyMissionRequest> request)throws Exception{
		
		Response<BeginNewDailyMissionResponse> response = new Response<BeginNewDailyMissionResponse>(request);
		try {
			if (null != request.getBody()) {
				BeginNewDailyMissionResponse dtoRes = beginNewDailyMissionService.selectNewMissionMaxVersion();
				response.setResult(dtoRes);
			}
		} catch (Exception e) {
			log.error(" searchDailyMission error:", e);
			throw e;
		}
		return response;
	}
	
}
