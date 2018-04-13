package com.winterframework.firefrog.beginmession.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.winterframework.firefrog.beginmession.dao.vo.BeginNewLog;
import com.winterframework.firefrog.beginmession.service.BeginNewLogService;
import com.winterframework.firefrog.beginmession.web.dto.BeginNewLogRequest;
import com.winterframework.firefrog.beginmession.web.dto.BeginNewLogResponse;
import com.winterframework.firefrog.common.annotation.ValidRequestBody;
import com.winterframework.firefrog.common.util.PageConverterUtils;
import com.winterframework.modules.page.Page;
import com.winterframework.modules.page.PageRequest;
import com.winterframework.modules.web.jsonresult.Request;
import com.winterframework.modules.web.jsonresult.Response;
import com.winterframework.modules.web.jsonresult.ResultPager;

@Controller
@RequestMapping("/begin")
public class BeginNewLogController {

	private static final Logger log = LoggerFactory.getLogger(BeginNewLogController.class);
	
	@Autowired
	private BeginNewLogService logService;
	
	@RequestMapping("/searchLog")
	@ResponseBody
	public Response<BeginNewLogResponse> searchLog(@RequestBody @ValidRequestBody Request<BeginNewLogRequest> request)throws Exception{
		Response<BeginNewLogResponse> response = new Response<BeginNewLogResponse>(request);
		try {
			
			if (null != request.getBody()) {
				 BeginNewLog newLog  = new BeginNewLog();
				 newLog.setLogType(request.getBody().getParam().getType());
				PageRequest<BeginNewLog> pageRequest = PageConverterUtils.getRestPageRequest(request
						.getBody().getPager().getStartNo(), request.getBody().getPager().getEndNo());				
				pageRequest.setSearchDo(newLog);
				Page<BeginNewLog> Logs = logService.getAllByPage(pageRequest);
				BeginNewLogResponse qures = new BeginNewLogResponse();
				qures.setLogs(Logs.getResult());
				ResultPager pager = new ResultPager();
				pager.setStartNo(Logs.getThisPageFirstElementNumber());
				pager.setEndNo(Logs.getThisPageLastElementNumber());
				pager.setTotal(Logs.getTotalCount());
				response.setResultPage(pager);
				response.setResult(qures);
			}
		} catch (Exception e) {
			log.error(" searchLog error:", e);
			throw e;
		}
		return response;
	}
}
