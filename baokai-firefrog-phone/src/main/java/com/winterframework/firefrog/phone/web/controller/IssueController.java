package com.winterframework.firefrog.phone.web.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.core.type.TypeReference;
import com.winterframework.firefrog.common.util.DataConverterUtil;
import com.winterframework.firefrog.common.util.DateUtils;
import com.winterframework.firefrog.game.web.dto.PreviewIssueStruc;
import com.winterframework.firefrog.game.web.dto.TraceGameIssueListQueryRequest;
import com.winterframework.firefrog.game.web.dto.TraceGameIssueListQueryResponse;
import com.winterframework.firefrog.phone.web.cotroller.dto.AllIssueDto;
import com.winterframework.firefrog.phone.web.cotroller.dto.AllIssueRequest;
import com.winterframework.firefrog.phone.web.cotroller.dto.AllIssueResponse;
import com.winterframework.firefrog.phone.web.cotroller.dto.GetTimeRequest;
import com.winterframework.firefrog.phone.web.cotroller.dto.GetTimeResponse;
import com.winterframework.modules.spring.exetend.PropertyConfig;
import com.winterframework.modules.web.jsonresult.Pager;
import com.winterframework.modules.web.jsonresult.Request;
import com.winterframework.modules.web.jsonresult.Response;

@Controller("issueController")
@RequestMapping("/information")
public class IssueController extends BaseController{
	
	private static final Logger log = LoggerFactory.getLogger(IssueController.class);
	
	@PropertyConfig(value="url.front.allIssue")
	private String allIssueUrl;
	@PropertyConfig(value="url.front.queryMaxGameIssue")
	private String queryMaxGameIssue;
	
	@RequestMapping("/allIssue")
	@ResponseBody
	public Response<AllIssueResponse> allIssue(@RequestBody Request<AllIssueRequest> request) throws Exception{
		
		Response<AllIssueResponse> response = new Response<AllIssueResponse>(request);
		
		try {
			
			AllIssueResponse result = new AllIssueResponse();
			TraceGameIssueListQueryRequest gameRequest = new TraceGameIssueListQueryRequest();
//			gameRequest.setQueryType(1);
//			Calendar cal = Calendar.getInstance();
//			cal.setTime(new Date());
//			cal.add(java.util.Calendar.DATE, 15);
//			gameRequest.setShowStartTime(new Date().getTime());
//			gameRequest.setShowEndTime(cal.getTimeInMillis());
			
			AllIssueRequest issueRequest = request.getBody().getParam();
			int num = 360;
			if(null != request.getBody().getParam().getNum()){
				num = request.getBody().getParam().getNum();
			}
			if(null !=issueRequest.getLotteryId() ){
				gameRequest.setLotteryId(issueRequest.getLotteryId());
			}
			
			Pager page = new Pager(0, num);
			
			List<AllIssueDto>  list = new ArrayList<AllIssueDto>();
			if(null != issueRequest.getLotteryId()){
				gameRequest.setLotteryId(issueRequest.getLotteryId());
//				gameRequest.setMaxCountIssue(getMaxGameIssue(issueRequest.getLotteryId()).getBody().getResult().intValue());
				gameRequest.setMaxCountIssue(num);
				
				Response<TraceGameIssueListQueryResponse> gameResponse = httpClient.invokeHttp(gameUrl+ allIssueUrl, gameRequest, page, new TypeReference<Response<TraceGameIssueListQueryResponse>>(){});
				if(null != gameResponse.getBody() && null != gameResponse.getBody().getResult()){
					for(PreviewIssueStruc struc : gameResponse.getBody().getResult().getIssueStrucs()){
						list.add(convert2IssueDto(struc));
					}
				}
			}else{
				for(Long lotteryId : allSeries()){
					
					gameRequest.setLotteryId(lotteryId);
//					gameRequest.setMaxCountIssue(getMaxGameIssue(lotteryId).getBody().getResult().intValue());
					gameRequest.setMaxCountIssue(num);
					
					Response<TraceGameIssueListQueryResponse> gameResponse = httpClient.invokeHttp(gameUrl+ allIssueUrl, gameRequest, page, new TypeReference<Response<TraceGameIssueListQueryResponse>>(){});
					if(null != gameResponse.getBody() && null != gameResponse.getBody().getResult()){
						for(PreviewIssueStruc struc : gameResponse.getBody().getResult().getIssueStrucs()){
							list.add(convert2IssueDto(struc));
						}
					}
					
				}
			}
			
			result.setList(list);
			response.setResult(result);
			
		} catch (Exception e) {
			
			log.error("allIssue error:",e);
			response.getHead().setStatus(901000L);
		}
		
		return response;
	}
	
	public Response<Long> getMaxGameIssue(Long request) throws Exception {
		return httpClient.invokeHttp(gameUrl + queryMaxGameIssue, request, Long.class);
	}

	private AllIssueDto convert2IssueDto(PreviewIssueStruc struc) {
		AllIssueDto dto = new AllIssueDto();
		dto.setIssue(struc.getWebIssueCode());
		dto.setSaleend(DateUtils.format(DataConverterUtil.convertLong2Date(struc.getSaleEndTime()),DateUtils.DATETIME_FORMAT_PATTERN));
		dto.setIssueCode(new BigDecimal(struc.getIssueCode()).toEngineeringString());
		return dto;
	}
	
	@RequestMapping("/getTime")
	@ResponseBody
	public Response<GetTimeResponse> getTime(@RequestBody Request<GetTimeRequest> request) throws Exception{
		
		Response<GetTimeResponse> response = new Response<GetTimeResponse>(request);
		GetTimeResponse result = new GetTimeResponse();
		result.setServerTime(DateUtils.format(new Date(),DateUtils.DATETIME_FORMAT_PATTERN));
		response.setResult(result);
		return response;
	}
	
}
