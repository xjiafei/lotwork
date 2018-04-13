package com.winterframework.firefrog.beginmession.web.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.winterframework.firefrog.beginmession.dao.vo.BeginDataCountVO;
import com.winterframework.firefrog.beginmession.web.dto.BeginDataCountWebRequest;
import com.winterframework.firefrog.beginmession.web.dto.BeginDataCountWebResponse;
import com.winterframework.firefrog.common.http.IHttpJsonClient;
import com.winterframework.firefrog.common.util.DateUtils;
import com.winterframework.firefrog.common.view.ExcelView;
import com.winterframework.firefrog.common.view.ExportViewDataModel;
import com.winterframework.modules.spring.exetend.PropertyConfig;
import com.winterframework.modules.web.jsonresult.Response;

@Controller
@RequestMapping(value ="/begin")
public class BeginDataCountWebController {

	
	private static final Logger log = LoggerFactory.getLogger(BeginDataCountWebController.class);
	
	@Resource(name = "httpJsonClientImpl")
	private IHttpJsonClient httpClient;
	
	@PropertyConfig(value = "url.connect")
	private String urlPath;
	
	@PropertyConfig(value = "url.begin.report")
	private String getReport;
	
	
	
	@RequestMapping(value="/downloadMissionReport",method =RequestMethod.GET )
	public  ModelAndView downloadFile(@RequestParam(value = "startTime") String startTime,@RequestParam(value = "endTime") String endTime, HttpServletResponse res) throws Exception{
		BeginDataCountWebRequest req = new BeginDataCountWebRequest();
		req.setEndTime(endTime);
		req.setStartTime(startTime);
		Response<BeginDataCountWebResponse> response = httpClient.invokeHttp(urlPath+getReport,req,BeginDataCountWebResponse.class);	
		BeginDataCountWebResponse result = response.getBody().getResult();
		BeginDataCountVO vo= result.getVo();
 		ExportViewDataModel viewTemplates = new ExportViewDataModel();
		String[] titles = new String[] { "同期用户参与人数", "同期完成绑卡数","同期绑卡比例","同期完成首充数","同期首充比例","同期完成首提数","同期首提比例","同期人均首存金额","同期人均投注金额"
				,"同期抽奖人数","同期派奖额","同期派奖次数"};
		String[] columns = new String[] { "activityMissionCount", "bindcardCount","bindcardPercent","chargeCount","chargePercent","withDrawCount","withdrawPercent","chargeAmtPercen","orderAmtPercen",
				"lotteryPersion","totalLotteryAmt","totalLotteryTime"};
		viewTemplates.setFileName(DateUtils.format(new Date(), DateUtils.DATE_FORMAT_PATTERN));
		viewTemplates.setHeader(titles);
		viewTemplates.setColumns(columns);
		List<BeginDataCountVO> list = new ArrayList<BeginDataCountVO>();
		list.add(vo);
		viewTemplates.setDataList(list);
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("dataModel", viewTemplates);
		return new ModelAndView(new ExcelView(), model);
	}
	
	@RequestMapping(value="toDataCount",method =RequestMethod.GET)
	public ModelAndView toDataCount(BeginDataCountWebRequest search) throws Exception {
		ModelAndView model = new ModelAndView("advert/begin/beginMissionReport");
		return model;
	} 

	
	@RequestMapping(value="getMissionReport",method =RequestMethod.GET)
	public ModelAndView getMissionReport(BeginDataCountWebRequest search) throws Exception {
		log.info(" into getMissionReport startTime = {},endTIme={}",new Object[]{search.getStartTime(),search.getEndTime()});
		ModelAndView model = new ModelAndView("advert/begin/beginMissionReport");
		Response<BeginDataCountWebResponse> response = httpClient.invokeHttp(urlPath+getReport,search,BeginDataCountWebResponse.class);	
		BeginDataCountWebResponse res = response.getBody().getResult();
		model.addObject("vo",res.getVo());
		model.addObject("startTime",search.getStartTime());
		model.addObject("endTime",search.getEndTime());
		return model;
	} 
	
	
}
