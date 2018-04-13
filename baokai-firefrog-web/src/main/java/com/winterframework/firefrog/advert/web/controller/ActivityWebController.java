package com.winterframework.firefrog.advert.web.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.winterframework.firefrog.advert.web.dto.ActivityAwardsRequest;
import com.winterframework.firefrog.advert.web.dto.ActivityAwardConfig;
import com.winterframework.firefrog.advert.web.dto.ActivityConfigRequest;
import com.winterframework.firefrog.advert.web.dto.ActivityConfigResponse;
import com.winterframework.firefrog.advert.web.dto.ConfigValueRequestDTO;
import com.winterframework.firefrog.advert.web.dto.ConfigValueResponse;
import com.winterframework.firefrog.advert.web.dto.ExportViewDataModel;
import com.winterframework.firefrog.advert.web.dto.PageForView;
import com.winterframework.firefrog.advert.web.dto.PageUtils;
import com.winterframework.firefrog.advert.web.dto.QueryLotteryRecordRequest;
import com.winterframework.firefrog.advert.web.dto.QueryLotteryRecordResponse;
import com.winterframework.firefrog.advert.web.dto.QueryLotteryResultRequest;
import com.winterframework.firefrog.advert.web.dto.QueryLotteryResultResponse;
import com.winterframework.firefrog.advert.web.dto.QueryRedEnvelopeRequest;
import com.winterframework.firefrog.advert.web.dto.QueryRedEnvelopeResponse;
import com.winterframework.firefrog.advert.web.dto.RedEnvelopeStruc;
import com.winterframework.firefrog.common.http.IHttpJsonClient;
import com.winterframework.firefrog.common.util.DateUtils;
import com.winterframework.firefrog.notice.web.dto.ConfigSaveRequestDTO;
import com.winterframework.modules.page.PageRequest;
import com.winterframework.modules.spring.exetend.PropertyConfig;
import com.winterframework.modules.web.jsonresult.Response;
import com.winterframework.modules.web.jsonresult.ResultPager;
import com.winterframework.modules.web.util.RequestContext;

/** 
* @ClassName: ActivityWebController 
* @Description: 4.0系统升级活动
* @author Hugh
* @date 2014-12-1 下午3:01:15 
*  
*/
@Controller("activityWebController")
@RequestMapping(value = "/adAdmin")
public class ActivityWebController {

	private Logger logger = LoggerFactory.getLogger(ActivityWebController.class);

	@Resource(name = "httpJsonClientImpl")
	private IHttpJsonClient httpClient;

	@PropertyConfig(value = "url.gamecenter.connect")
	private String serverPath;
	@PropertyConfig(value = "url.connect")
	private String urlPath;
	
	private String uploadExcelForActivity = "/activity/uploadAwardList";

	private String queryActivityConfigUrl = "/game/queryActivityConfig";

	private String updateActivityConfigUrl= "/game/updateActivityConfig";
	
	@PropertyConfig("dailyActivityStartTime")
	private String dailyActivityStartTime;
	
	@PropertyConfig("daily.activity.end.time")
	private String dailyActivityEndTime;
	
	@PropertyConfig(value = "url.common.configSave")
	private String saveConfig;
	@PropertyConfig(value = "url.common.configGet")
	private String getConfig;
	
	@RequestMapping(value = "/queryActivityConfig")
	public String queryActivityConfig(Model model ,Long ratioAll) throws Exception {
		Response<ActivityConfigResponse> response = new Response<ActivityConfigResponse>();
		ActivityConfigRequest query = new ActivityConfigRequest();
		
		query.setId(1L);//升级活动 红包活动id 1
		logger.info("query queryActivityConfig start");

		try {
			Long userid = RequestContext.getCurrUser().getId();
			String username = RequestContext.getCurrUser().getUserName();
			response = httpClient.invokeHttp(serverPath + queryActivityConfigUrl, query, userid, username,
					ActivityConfigResponse.class);
		} catch (Exception e) {
			logger.error("query queryActivityConfig error");
			throw e;
		}
		logger.info("query queryActivityConfig end");
		
		if(response != null && response.getBody() !=null &&  response.getBody().getResult() != null)
		model.addAttribute("configs", response.getBody().getResult().getConfigs());
		model.addAttribute("ratioAll", ratioAll != null ? ratioAll : 10000);
		return "/advert/activity";
	
	}

	@RequestMapping(value = "/updateActivityConfig")
	public String updateActivityConfig(Model model, Long[] id ,Long[] lastNumber,Double[] multiple ,Double[] ratio) throws Exception {
		long ratioAll = 0L;
		if(id != null){
			
			for (int i = 0; i < id.length; i++){
				ratioAll += (long) (ratio[i]*100);
			}
			
			if(ratioAll == 10000){
				for (int i = 0; i < id.length; i++) {
					System.out.println(id[i]);
					System.out.println(lastNumber[i]);
					ActivityAwardConfig config = new ActivityAwardConfig();
					config.setId(id[i]);
					config.setMultiple((long)(multiple[i]*100));
					config.setRatio((long) (ratio[i]*100));
					
					config.setLastNumber(lastNumber[i]);
					try {
						httpClient.invokeHttp(serverPath + updateActivityConfigUrl, config,Object.class);
					} catch (Exception e) {
						logger.error("query betLimit error");
						throw e;
					}
				}
			}
			
			
		}

		logger.info("query betLimit start");
	
		
		logger.info("query betLimit end");

		return queryActivityConfig(model ,ratioAll);
	}
	
	@RequestMapping(value = "/queryActivityHongBaoLog")
	public String queryActivityHongBaoLog(Model model,String userName ,Integer status,
			String timestart,String timeend ,Integer channel,
			@ModelAttribute("page") PageRequest<QueryRedEnvelopeRequest> page) throws Exception {
		Response<QueryRedEnvelopeResponse> response = new Response<QueryRedEnvelopeResponse>();
		//ActivityConfigRequest query = new ActivityConfigRequest();
		System.out.println(userName);
		System.out.println(status);
		System.out.println(timestart);
		System.out.println(timeend);
		model.addAttribute("channel", channel);
		model.addAttribute("userName", userName);
		model.addAttribute("status", status);
		model.addAttribute("timestart", timestart);
		model.addAttribute("timeend", timeend);
		if(status != null && status == -1){
			status = null;
		}
		if(channel != null && channel == -1){
			channel = null;
		}
		if(page != null){
			System.out.println(page.getPageNo());
			System.out.println(page.getPageSize());
		}else{
			page = new PageRequest<QueryRedEnvelopeRequest>();
		}
		
		if(StringUtils.isBlank(timestart)){
			timestart = dailyActivityStartTime + " 00:00:00";
		}
		if(StringUtils.isBlank(timeend)){
			timeend = dailyActivityEndTime+ " 23:59:59";
		}

		
		//query.setId(1L);
		logger.info("query queryActivityHongBaoLog start");
		QueryRedEnvelopeRequest query  = new QueryRedEnvelopeRequest();
		query.setStartTime(DateUtils.parse(timestart,DateUtils.DATETIME_FORMAT_PATTERN));
		query.setEndTime(DateUtils.parse(timeend ,DateUtils.DATETIME_FORMAT_PATTERN));
		query.setAccount(userName);
		query.setStatus(status);
		query.setChannel(channel);
		try {
			response = httpClient.invokeHttp(serverPath + "/gameoa/queryRedEnvelope", query,PageUtils.getPager(page), 
					QueryRedEnvelopeResponse.class);
		} catch (Exception e) {
			logger.error("query queryActivityConfig error");
			throw e;
		}
		logger.info("query queryActivityHongBaoLog end");
	
		
		if(response != null && response.getBody() !=null &&  response.getBody().getResult() != null){
			model.addAttribute("struc", response.getBody().getResult().getRedEnvelopeStruc());
			model.addAttribute("bet", response.getBody().getResult().getTotalBetAmount());
			model.addAttribute("red", response.getBody().getResult().getRedEnvelopeAmount());
			ResultPager rp = response.getBody().getPager();
			PageForView pages = PageUtils.getPageForView(page, rp);
			pages.setPageNo(page.getPageNo());
			model.addAttribute("page", pages);
		}else{
			model.addAttribute("page", null);
			model.addAttribute("struc", null);
			model.addAttribute("bet", 0);
			model.addAttribute("red", 0);
		}

		return "/advert/activityHongBaoLog";
	
	}
	
	@RequestMapping(value = "exportActivityHongBaoLog")
	public ModelAndView exportGameRiskTransactionReport(
			@ModelAttribute("page") PageRequest<QueryRedEnvelopeRequest> page) throws Exception {
		Response<QueryRedEnvelopeResponse> response = new Response<QueryRedEnvelopeResponse>();

		logger.info("export WinsReport start");
		try {
			QueryRedEnvelopeRequest query  = new QueryRedEnvelopeRequest();
			String timestart = dailyActivityStartTime + " 00:00:00";
			String timeend = dailyActivityEndTime+ " 23:59:59";			
			query.setStartTime(DateUtils.parse(timestart,DateUtils.DATETIME_FORMAT_PATTERN));
			query.setEndTime(DateUtils.parse(timeend ,DateUtils.DATETIME_FORMAT_PATTERN));
			page.setPageSize(100000);
			response = httpClient.invokeHttp(serverPath + "/gameoa/queryRedEnvelope", query,
					PageUtils.getPager(page), new TypeReference<Response<QueryRedEnvelopeResponse>>() {
					});
		} catch (Exception e) {
			logger.error("export WinsReport error", e);
			throw e;
		}

		if (response.getBody().getResult() != null && response.getBody().getResult().getRedEnvelopeStruc() != null) {

			List<RedEnvelopeStruc> reports = response.getBody().getResult().getRedEnvelopeStruc();
	
			//执行导出功能
			ExportViewDataModel viewTemplates = new ExportViewDataModel();
			String[] titles = new String[] { "时间", "用户名", "投注金额", "中奖金额", "状态", "渠道"};
			String[] columns = new String[] { "date", "account", "betAmount", "award", "status", "channel"};

			viewTemplates.setFileName("红包报表导出数据");
			viewTemplates.setHeader(titles);
			viewTemplates.setColumns(columns);
			viewTemplates.setDataList(reports);

			Map<String, Object> model = new HashMap<String, Object>();
			model.put("dataModel", viewTemplates);

			//return new ModelAndView(new ExcelView(), model);

		}

		return null;
	}
	

	@RequestMapping(value = "/queryActivityChouJianUserLog")
	public String queryActivityChouJianUserLog(Model model,String userName ,Integer status,
			String timestart,String timeend ,
			@ModelAttribute("page") PageRequest<QueryLotteryRecordRequest> page) throws Exception {
		Response<QueryLotteryRecordResponse> response = new Response<QueryLotteryRecordResponse>();
		//ActivityConfigRequest query = new ActivityConfigRequest();
		System.out.println(userName);
		System.out.println(status);
		System.out.println(timestart);
		System.out.println(timeend);
		model.addAttribute("userName", userName);
		model.addAttribute("status", status);
		model.addAttribute("timestart", timestart);
		model.addAttribute("timeend", timeend);
		if(status != null && status == -1){
			status = null;
		}
		if(page != null){
			System.out.println(page.getPageNo());
			System.out.println(page.getPageSize());
		}else{
			page = new PageRequest<QueryLotteryRecordRequest>();
		}
		
		if(StringUtils.isBlank(timestart)){
			timestart = dailyActivityStartTime + " 00:00:00";
		}
		if(StringUtils.isBlank(timeend)){
			timeend = dailyActivityEndTime+ " 23:59:59";
		}

		
		//query.setId(1L);
		logger.info("query queryActivityHongBaoLog start");
		QueryLotteryRecordRequest query  = new QueryLotteryRecordRequest();
		query.setActivityStartTime(DateUtils.parse(timestart,DateUtils.DATETIME_FORMAT_PATTERN));
		query.setActivityEndTime(DateUtils.parse(timeend ,DateUtils.DATETIME_FORMAT_PATTERN));
		query.setAccount(userName);
	
		try {
			response = httpClient.invokeHttp(serverPath + "/gameoa/queryLotteryRecord", query,PageUtils.getPager(page), 
					QueryLotteryRecordResponse.class);
		} catch (Exception e) {
			logger.error("query queryActivityConfig error");
			throw e;
		}
		logger.info("query queryActivityHongBaoLog end");
	
		
		if(response != null && response.getBody() !=null &&  response.getBody().getResult() != null){
			model.addAttribute("struc", response.getBody().getResult().getList());
			ResultPager rp = response.getBody().getPager();
			PageForView pages = PageUtils.getPageForView(page, rp);
			pages.setPageNo(page.getPageNo());
			model.addAttribute("page", pages);
		}else{
			model.addAttribute("page", null);
			model.addAttribute("struc", null);

		}

		return "/advert/activityChouJianUserLog";
	
	}
	

	
	@RequestMapping(value = "/queryActivityChouJianLog")
	public String queryActivityChouJianLog(Model model,String userName ,Integer status,
			String timestart,String timeend ,Long config,
			@ModelAttribute("page") PageRequest<QueryLotteryResultRequest> page) throws Exception {
		Response<QueryLotteryResultResponse> response = new Response<QueryLotteryResultResponse>();
		//ActivityConfigRequest query = new ActivityConfigRequest();
		System.out.println(userName);
		System.out.println(status);
		System.out.println(timestart);
		System.out.println(timeend);
		model.addAttribute("userName", userName);
		model.addAttribute("status", status);
		model.addAttribute("timestart", timestart);
		model.addAttribute("timeend", timeend);
		model.addAttribute("config", config);
		if(status != null && status == -1){
			status = null;
		}
		if(config != null && config == -1){
			config = null;			
		}
		if(page != null){
			System.out.println(page.getPageNo());
			System.out.println(page.getPageSize());
		}else{
			page = new PageRequest<QueryLotteryResultRequest>();
		}
		
		if(StringUtils.isBlank(timestart)){
			timestart = dailyActivityStartTime + " 00:00:00";
		}
		if(StringUtils.isBlank(timeend)){
			timeend = dailyActivityEndTime+ " 23:59:59";
		}

		
		//query.setId(1L);
		logger.info("query queryActivityHongBaoLog start");
		QueryLotteryResultRequest query  = new QueryLotteryResultRequest();
		query.setStartTime(DateUtils.parse(timestart,DateUtils.DATETIME_FORMAT_PATTERN));
		query.setEndTime(DateUtils.parse(timeend ,DateUtils.DATETIME_FORMAT_PATTERN));
		query.setAccount(userName);
		query.setChannel(status);
		query.setAwardId(config);
		try {
			response = httpClient.invokeHttp(serverPath + "/gameoa/queryLotteryResult", query,PageUtils.getPager(page), 
					QueryLotteryResultResponse.class);
		} catch (Exception e) {
			logger.error("query queryActivityConfig error");
			throw e;
		}
		logger.info("query queryActivityHongBaoLog end");
	
		
		if(response != null && response.getBody() !=null &&  response.getBody().getResult() != null){
			model.addAttribute("struc", response.getBody().getResult().getList());
			ResultPager rp = response.getBody().getPager();
			PageForView pages = PageUtils.getPageForView(page, rp);
			pages.setPageNo(page.getPageNo());
			model.addAttribute("page", pages);
		}else{
			model.addAttribute("page", null);
			model.addAttribute("struc", null);

		}

		return "/advert/activityChouJianLog";
	
	}
	
	@RequestMapping(value = "/medals")
	public ModelAndView medals (@RequestParam(value="num",required=false) Long num) throws Exception{
			ModelAndView mav = new ModelAndView("/advert/medals");
			
			if(num != null ){
				ConfigSaveRequestDTO configDto = new ConfigSaveRequestDTO();
				configDto.setFunction("olympic_gold_medals");
				configDto.setModule("activity");
				configDto.setVal(num);
				httpClient.invokeHttp(urlPath+saveConfig,configDto,new TypeReference<Response<ConfigSaveRequestDTO>>(){});
			}
			
			ConfigValueRequestDTO getConfigDto = new ConfigValueRequestDTO();
				getConfigDto.setFunction("olympic_gold_medals");
				getConfigDto.setModule("activity");
				
			ConfigValueResponse medalsNo = (ConfigValueResponse) httpClient.invokeHttp(urlPath+getConfig,getConfigDto,new TypeReference<Response<ConfigValueResponse>>(){}).getBody().getResult();
			mav.addObject("medalsNo", medalsNo.getVal());
		return mav;
	}
	
	@RequestMapping(value = "/uploadAccounts")
	public ModelAndView uploadAccounts (@RequestParam(value="num",required=false) Long num) throws Exception{
		ModelAndView mav = new ModelAndView("/advert/uploadAccounts");
		return mav;
	}
	
	@RequestMapping(value = "/uploadFiles")
	@ResponseBody
	public ModelAndView uploadTicketFile(@RequestParam("file") MultipartFile file) {
		ModelAndView mav = new ModelAndView("/advert/uploadAccounts");
		logger.info(file.getName());
		List<String[]> datas = new ArrayList<String[]>();
		try {
			HSSFWorkbook readWorkbook = new HSSFWorkbook(file.getInputStream());
			HSSFSheet sheet = readWorkbook.getSheetAt(0);
			for(int i = 1 ; ; i++){
				String[] rowData = new String[3];
				HSSFRow row = sheet.getRow(i);
				if(null == row || null == row.getCell(0) || null == row.getCell(1) || null == row.getCell(2)){
					break;
				}
				if(row.getCell(0).getCellType() ==1){
					rowData[0] = row.getCell(0).getStringCellValue().toLowerCase().trim();//用戶名
				}else {
					String v_excelData = row.getCell(0).getNumericCellValue() + "";			         
					BigDecimal bd = new BigDecimal( v_excelData );
			        v_excelData = bd.toPlainString(); 
			        String[] transData = v_excelData.split("\\.");
					rowData[0] = transData[0].toLowerCase().trim();//用戶名
				}
				rowData[1] = row.getCell(1).getStringCellValue();//活動代碼
				rowData[2] = String.valueOf(row.getCell(2).getNumericCellValue());//周數

				logger.info("Analysis row data are :"+rowData[0]+", "+rowData[1]+", "+rowData[2]);
				datas.add(rowData);
			}
			ObjectMapper mapper = new ObjectMapper();
			logger.info(mapper.writeValueAsString(datas));
			ActivityAwardsRequest req = new ActivityAwardsRequest();
			req.setAwardList(datas);
			Response<String> resp = httpClient.invokeHttp(urlPath + 
					uploadExcelForActivity, req,new TypeReference<Response<String>>() {});
			logger.info("upload number = "+resp.getBody().getResult());
			mav.addObject("uploadResult", resp.getBody().getResult());
		} catch (Exception e) {
			logger.error("/uploadFiles has error:"+e);
			mav.addObject("uploadResult", "名单上传失败");
		}
		return mav;
	}
}
