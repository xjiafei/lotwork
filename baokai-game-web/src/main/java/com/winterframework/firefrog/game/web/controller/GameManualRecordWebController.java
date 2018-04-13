package com.winterframework.firefrog.game.web.controller;

import java.util.Date;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.core.type.TypeReference;
import com.winterframework.firefrog.common.annotation.ValidRequestBody;
import com.winterframework.firefrog.common.httpjsonclient.IHttpJsonClient;
import com.winterframework.firefrog.common.util.PageConverterUtils;
import com.winterframework.firefrog.game.dao.vo.GameIssue;
import com.winterframework.firefrog.game.web.dto.AjaxResponse;
import com.winterframework.firefrog.game.web.dto.GameExceptionAuditRequest;
import com.winterframework.firefrog.game.web.dto.GameIssueListQueryRequest;
import com.winterframework.firefrog.game.web.dto.GameManualRecordEncodingRequest;
import com.winterframework.firefrog.game.web.dto.GameManualRecordRequest;
import com.winterframework.firefrog.game.web.dto.GameManualRecordResponse;
import com.winterframework.firefrog.game.web.dto.GameMultipleQueryResponse;
import com.winterframework.firefrog.game.web.dto.GameRiskIssueListQueryResponse;
import com.winterframework.firefrog.game.web.dto.GameSeriesResponse;
import com.winterframework.firefrog.game.web.dto.PageForView;
import com.winterframework.firefrog.game.web.dto.SingleIssueIncomeAndExpenseQueryRequest;
import com.winterframework.firefrog.game.web.dto.SubUserReportRequest;
import com.winterframework.firefrog.game.web.util.IssueCodeUtil;
import com.winterframework.firefrog.game.web.util.PageUtils;
import com.winterframework.modules.page.PageRequest;
import com.winterframework.modules.spring.exetend.PropertyConfig;
import com.winterframework.modules.web.jsonresult.Request;
import com.winterframework.modules.web.jsonresult.Response;
import com.winterframework.modules.web.jsonresult.ResultPager;
import com.winterframework.modules.web.util.RequestContext;

/** 
* @ClassName GameManualRecordController 
* @Description 手工录号 
* @author  hugh
* @date 2014年9月15日 下午4:33:53 
*  
*/
@Controller("gameManualRecordWebController")
@RequestMapping("/gameoa")
public class GameManualRecordWebController {

	@Resource(name = "httpJsonClientImpl")
	private IHttpJsonClient httpClient;

	@PropertyConfig(value = "url.connect")
	private String serverPath;

	@PropertyConfig(value = "url.game.queryGameManualRecord")
	private String queryGameManualRecord;

	@PropertyConfig(value = "url.game.encodingGameManualRecord")
	private String encodingGameManualRecord;

	@RequestMapping(value = "/queryGameManualRecord")
	@ResponseBody
	public ModelAndView getGameManualRecordsByLottery(@ModelAttribute("request") GameManualRecordRequest request,
			@ModelAttribute("page") PageRequest<GameManualRecordRequest> page) throws Exception {
		String userName = RequestContext.getCurrUser().getUserName();
		Long userId = RequestContext.getCurrUser().getId();
		
		ModelAndView view = new ModelAndView("operations/manualRecord/manualRecord");
		if (null == request || request.getLotteryId() == null) {
			request = new GameManualRecordRequest();
			request.setLotteryId(99101L);
		}

		if (null == page) {
			page = new PageRequest<GameManualRecordRequest>();
		}
		request.setUserId(RequestContext.getCurrUser().getId());
		Response<GameManualRecordResponse> res = httpClient.invokeHttp(serverPath + queryGameManualRecord, request,
				PageUtils.getPager(page), GameManualRecordResponse.class);
		if (res != null && res.getBody() != null && res.getBody().getResult() != null) {
			GameIssue issue = res.getBody().getResult().getGameIssue();
			view.addObject("issue", res.getBody().getResult().getGameIssue());
			GameIssue lastIssue = res.getBody().getResult().getLastGameIssue();
			view.addObject("lastIssue", lastIssue);
			
			view.addObject("records", res.getBody().getResult().getGameManualRecords());
			ResultPager rp = res.getBody().getPager();
			PageForView pages = PageUtils.getPageForView(page, rp);
			pages.setPageNo(page.getPageNo());
			view.addObject("page", pages);
			
			if(issue != null){
				Long timeL = (issue.getOpenDrawTime().getTime() +30000 - new Date().getTime()) / 1000;
				Integer time = timeL < 0 ? 0 : timeL.intValue();
				view.addObject("time", time);
			}else{
				view.addObject("time", 0);
			}
			
			if(lastIssue != null){
				Long lastTimeL = (lastIssue.getOpenDrawTime().getTime() +30000- new Date().getTime()) / 1000;
				Integer lastTime = lastTimeL < 0 ? 0 : lastTimeL.intValue();
				view.addObject("lastTime", lastTime);
			}else{
				view.addObject("lastTime", 0);
			}
			
			
		} else {

			view.addObject("issue", null);
			view.addObject("lastIssue", null);
			view.addObject("records", null);
			view.addObject("page", null);
			view.addObject("time", 0);
			view.addObject("lastTime", 0);
		}
		view.addObject("request", request);
		view.addObject("userName", userName);
		view.addObject("userId", userId);
		view.addObject("systemTime", new Date());
		
		return view;
	}

	@RequestMapping(value = "/encodingGameManualRecord")
	@ResponseBody
	public Object encodingGameManualRecordsByIssue(@ModelAttribute("request") GameManualRecordEncodingRequest request)
			throws Exception {
		AjaxResponse resp = new AjaxResponse();
		Long userId = RequestContext.getCurrUser().getId();
		request.setUserId(userId);

		if (request != null && request.getIssueCode() != null && request.getResult() != null) {
			if(request.getLotteryId()>=99101 && request.getLotteryId()<=99112){
				request.setResult(request.getResult().replaceAll(",",""));
			}
			if (request.getLotteryId() >= 99501 && request.getLotteryId() <= 99502)
			{
				request.setResult(request.getResult().replaceAll(",",""));
			}
			Response<Integer> res = httpClient
					.invokeHttp(serverPath + encodingGameManualRecord, request, Integer.class);

			if (res != null && res.getBody() != null && res.getBody().getResult() != null
					) {
				if(res.getBody().getResult().intValue() == 0){
					resp.setStatus(1l);
					resp.setMessage("录入成功！");
					return resp;
				}else if (res.getBody().getResult().intValue() == 2){
					resp.setStatus(2l);
					resp.setMessage("录入失败！请检查录入号码是否正确！");
					return resp;
				}else if (res.getBody().getResult().intValue() == 3){
					resp.setStatus(2l);
					resp.setMessage("录入失败！其他用户正在录入，请稍后！");
					return resp;
				}
			
			}
		}
		resp.setStatus(2l);
		resp.setMessage("录入失败！开奖号码已经存在或者奖期正在销售或者请检查相应参数是否正确！");
		return resp;
	}

}
