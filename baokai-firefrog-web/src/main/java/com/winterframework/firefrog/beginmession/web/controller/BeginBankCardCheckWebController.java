package com.winterframework.firefrog.beginmession.web.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.winterframework.firefrog.beginmession.web.dto.BackendCancelMissionRequest;
import com.winterframework.firefrog.beginmession.web.dto.BeginBankCardCheckWebResponse;
import com.winterframework.firefrog.beginmession.web.dto.BeginBindCardCheckWebRequest;
import com.winterframework.firefrog.beginmession.web.dto.BeginCancelListWebRequest;
import com.winterframework.firefrog.beginmession.web.dto.BeginCancelListWebResponse;
import com.winterframework.firefrog.beginmession.web.dto.BeginFileUploadStatusWebRequest;
import com.winterframework.firefrog.beginmession.web.dto.BeginFileUploadStatusWebResponse;
import com.winterframework.firefrog.beginmession.web.dto.BeginUpdateStatusWebRequest;
import com.winterframework.firefrog.common.http.IHttpJsonClient;
import com.winterframework.firefrog.common.util.DateUtils;
import com.winterframework.firefrog.common.view.ExcelView;
import com.winterframework.firefrog.common.view.ExportViewDataModel;
import com.winterframework.modules.page.Page;
import com.winterframework.modules.spring.exetend.PropertyConfig;
import com.winterframework.modules.web.jsonresult.Pager;
import com.winterframework.modules.web.jsonresult.Response;
import com.winterframework.modules.web.jsonresult.ResultPager;
import com.winterframework.modules.web.util.IUser;
import com.winterframework.modules.web.util.RequestContext;

@Controller
@RequestMapping(value ="/begin")
public class BeginBankCardCheckWebController {
	
	private static final Logger log = LoggerFactory.getLogger(BeginBankCardCheckWebController.class);
	@Resource(name = "httpJsonClientImpl")
	private IHttpJsonClient httpClient;
	
	@PropertyConfig(value = "url.connect")
	private String urlPath;
	
	@PropertyConfig(value = "url.begin.queryCheckData")
	private String queryCheckData;
	
	@PropertyConfig(value = "url.begin.searchCheckDataNoPage")
	private String searchCheckDataNoPage;

	@PropertyConfig(value = "url.begin.updateCheckStatus")
	private String updateCheckStatus;
	
	@PropertyConfig(value = "url.begin.queryCancelList")
	private String queryCancelList;
	
	@PropertyConfig(value = "url.begin.queryCancelListNoPage")
	private String queryCancelListNoPage;
	
	@PropertyConfig(value = "url.begin.fileUploadByStatus")
	private String fileUploadByStatus;

	@PropertyConfig(value = "url.begin.backendCancelMission")
	private String backendCancelMission;
	
	//每頁20筆資料
	@PropertyConfig(value="page.pagesize")
	private Integer pageSize;
	//EXCEL欄位與TITLE設定
	private static final String[] CHECK_TITLE_ARY = {"注册时间","注册IP","注册渠道","注册用户名","直接上级","一代号","总代号","银行卡绑定时间","银行卡号码","银行卡用户名","银行卡所在城市","首次充值时间","首次充值金额","选择时间段内销量总额","首次提现时间","首次提现金额"};
	private static final String[] CHECK_COLUMN_ARY ={"registerDate","registerIp","device","account","parentAccount","chain1","chain0","createTime","bankNum","accountName","city","chargeTime","chargeAmt","totAmount","withdrawTime","withdrawAmt"};
	private static final String[] CANCEL_TITLE_ARY = {"取消资格时间","帐号","原因","操作人"};
	private static final String[] CANCEL_COLUMN_ARY ={"cancelTime","accountName","cancelReason","cancelUser"};
	//查詢審核資料
	@RequestMapping(value="/toBindCardAppeal")
	public ModelAndView toBindCardAppeal(BeginBindCardCheckWebRequest request) throws Exception {
		ModelAndView model = new ModelAndView("advert/begin/bindCardAppeal");
		log.info("-----------toBindCardAppeal-------------");
		log.info("pageNo : " + request.getPageNo());
		log.info("status : " + request.getStatus());
		log.info("timeStart : " + request.getTimeStart());
		log.info("timeEnd : " + request.getTimeEnd());
		Pager pager = new Pager();
		if(request.getPageNo()==null){
			request.setPageNo(1l);
		}
		if(request.getStatus()==null){
			request.setStatus(-1L);
		}
		
		int startNo = (int) ((request.getPageNo() - 1) * pageSize + 1l);
		pager.setStartNo(startNo);
		pager.setEndNo(pageSize + startNo - 1);
		log.info("StartNo : " + pager.getStartNo());
		log.info("EndNo : " + pager.getEndNo());
		Response<BeginBankCardCheckWebResponse> response = httpClient.invokeHttp(urlPath + queryCheckData, request, pager, BeginBankCardCheckWebResponse.class);
		
		ResultPager resultPage = response.getBody().getPager();
		int pageNo = resultPage.getStartNo() / pageSize + (resultPage.getStartNo() % pageSize == 0 ? 0 : 1);
		
		Page<Object> page = new Page<Object>(pageNo, pageSize.intValue(), resultPage.getTotal());
		
		model.addObject("checkDatas", response.getBody().getResult().getCheckDatas());
		model.addObject("type",request.getStatus());
		model.addObject("timeStart",response.getBody().getResult().getTimeStart());
		model.addObject("timeEnd",response.getBody().getResult().getTimeEnd());
		model.addObject("page", page);
		return model;
	}
	
	//修改審核狀態
	@RequestMapping(value="/updateCheckStatus")
	public void updateCheckStatus(BeginUpdateStatusWebRequest request) throws Exception {
		try {
			IUser user = RequestContext.getCurrUser();
			request.setCurUser(user.getUserName());
			httpClient.invokeHttp(urlPath + updateCheckStatus, request, BeginUpdateStatusWebRequest.class);
		} catch (Exception e) {
			log.error("updateCheckStatus error:", e);
			throw e;
		}
			
	}
	
	//查詢取消資格名單
	@RequestMapping(value="/toCancelList")
	public ModelAndView toCancelList(BeginCancelListWebRequest request) throws Exception {
		ModelAndView model = new ModelAndView("advert/begin/bindCardAppeal");
		
		Pager pager = new Pager();
		if(request.getPageNo()==null){
			request.setPageNo(1l);
		}
		
		int startNo = (int) ((request.getPageNo() - 1) * pageSize + 1l);
		pager.setStartNo(startNo);
		pager.setEndNo(pageSize + startNo - 1);
		
		Response<BeginCancelListWebResponse> response = httpClient.invokeHttp(urlPath + queryCancelList, request, pager, BeginCancelListWebResponse.class);
		ResultPager resultPage = response.getBody().getPager();
		int pageNo = resultPage.getStartNo() / pageSize + (resultPage.getStartNo() % pageSize == 0 ? 0 : 1);
		Page<Object> page = new Page<Object>(pageNo, pageSize.intValue(), resultPage.getTotal());
		model.addObject("cancelDatas", response.getBody().getResult().getCancelDatas());
		model.addObject("type",request.getStatus());
		model.addObject("timeStart",response.getBody().getResult().getTimeStart());
		model.addObject("timeEnd",response.getBody().getResult().getTimeEnd());
		model.addObject("page", page);
		return model;
	}
	
	//導出報表
	@RequestMapping(value="/exportExcel")
	public  ModelAndView exportExcel(BeginBindCardCheckWebRequest request) throws Exception{
		Map<String, Object> model = new HashMap<String, Object>();
		try{
			ExportViewDataModel viewTemplates = null;
			switch(request.getStatus().intValue()){
				case 3:
					Response<BeginCancelListWebResponse> cancelResponse = httpClient.invokeHttp(urlPath + queryCancelListNoPage, request, BeginCancelListWebResponse.class);
					viewTemplates = generateExportViewModel(cancelResponse.getBody().getResult().getCancelDatas(),request.getStatus());
					break;
				default:
					Response<BeginBankCardCheckWebResponse> checkResponse = httpClient.invokeHttp(urlPath + searchCheckDataNoPage, request, BeginBankCardCheckWebResponse.class);
					viewTemplates = generateExportViewModel(checkResponse.getBody().getResult().getCheckDatas(),request.getStatus());
					break;
			}
			model.put("dataModel", viewTemplates);
		} catch (Exception e){
			log.error("queryCancelList error:", e);
			throw e;
		}
		return new ModelAndView(new ExcelView(), model);
	}
	
	//批次通過
	@RequestMapping(value="/fileUploadPass",method =RequestMethod.POST,produces = {"application/json"})	
	@ResponseBody
	public Map<String,Object> uploadFilePass(HttpServletResponse response,@RequestParam(value = "tempFile") MultipartFile multipartFile) throws Exception{
		Response<BeginFileUploadStatusWebResponse> res = null;
		Map<String, Object> map = new HashMap<String, Object>();
		try{
			res = fileUploadByStatus(multipartFile,"Y");
		} catch (Exception e){
			log.error("queryCancelList error:", e);
			throw e;
		}
		response.setContentType("application/json");
//		BeginFileUploadStatusWebResponse result = new BeginFileUploadStatusWebResponse();
		log.info("status : " + res.getBody().getResult().getStatus());
		map.put("status", res.getBody().getResult().getStatus());
		return map;
	}
	
	//批次拒絕
	@RequestMapping(value="/fileUploadReject",method =RequestMethod.POST,produces = {"application/json"} )
	@ResponseBody
	public Map<String,Object> fileUploadReject(@RequestParam(value = "tempFile") MultipartFile multipartFile) throws Exception{
		Response<BeginFileUploadStatusWebResponse> res = null;
		Map<String, Object> map = new HashMap<String, Object>();
		try{
			res = fileUploadByStatus(multipartFile,"N");
		} catch (Exception e){
			log.error("queryCancelList error:", e);
			throw e;
		}
		log.info("status : " + res.getBody().getResult().getStatus());
		map.put("status", res.getBody().getResult().getStatus());
		return map;
	}
	
	//取消資格
	@RequestMapping(value="/backendCancelMission")
	@ResponseBody
	public Response<Object> backendCancelMission(@RequestParam(value = "account") String account,
			@RequestParam(value = "reason") String reason) throws Exception{
		log.debug("account : " +account);
		log.debug("reason : " +reason);
		IUser user = RequestContext.getCurrUser();
		
		BackendCancelMissionRequest request = new BackendCancelMissionRequest();
		request.setAccount(account);
		request.setReason(reason);
		request.setCanceluser(user.getUserName());
		Response<Object> res = new Response<Object>();
		try{
			res = httpClient.invokeHttp(urlPath + backendCancelMission, request, Object.class);
			
		}catch(Exception e){
			log.error("backendCancelMission error:", e);
			res.setResult(0);
		}
		return res;
	}
	
	private Response<BeginFileUploadStatusWebResponse> fileUploadByStatus(MultipartFile multipartFile,String status) throws Exception{
		List<String> actionList = new ArrayList<String>();
		Response<BeginFileUploadStatusWebResponse> response = null;
		Workbook workbook = null;
		if(multipartFile.getOriginalFilename().endsWith("xls")){
			workbook = new HSSFWorkbook(multipartFile.getInputStream());
			
		}else{
			workbook = new XSSFWorkbook(multipartFile.getInputStream());
		}
		
		try{
			if(workbook.getSheetAt(0)!=null){
				Sheet sheet= workbook.getSheetAt(0);
				int count = sheet.getPhysicalNumberOfRows();
				for(int i=0; i < count;i++){
					Row row = sheet.getRow(i);
					if(row != null && row.getCell(0)!=null){
						String account= row.getCell(0).getStringCellValue();
						if("username".equals(account) && i == 0){
							continue;
						} 
						if(row.getCell(0).getCellType() == Cell.CELL_TYPE_STRING){
							actionList.add(account);		
						}
					}
				} 
			}
			if(actionList.size() > 500){
				BeginFileUploadStatusWebResponse result = new BeginFileUploadStatusWebResponse();
				result.setStatus(-1L);
				response = new Response<BeginFileUploadStatusWebResponse>();
				response.setResult(result);
				return response;
			}
			
			
			IUser user = RequestContext.getCurrUser();
			BeginFileUploadStatusWebRequest req = new BeginFileUploadStatusWebRequest();
			req.setAccounts(actionList);
			req.setStatus(status);
			req.setCurUser(user.getUserName());
			response = httpClient.invokeHttp(urlPath + fileUploadByStatus, req, BeginFileUploadStatusWebResponse.class);
		} catch (Exception e){
			log.error("queryCancelList error:", e);
			throw e;
		}
		return response;
	}
	
	private ExportViewDataModel generateExportViewModel(List<?> datas,Long status){
		ExportViewDataModel viewTemplates = new ExportViewDataModel();
		viewTemplates.setFileName(convertStatus(status) + "_" + DateUtils.format(new Date(), DateUtils.DATETIME_EXCEL_FORMAT_PATTERN));
		if(status != 3){
			viewTemplates.setHeader(CHECK_TITLE_ARY);
			viewTemplates.setColumns(CHECK_COLUMN_ARY);
		}else{
			viewTemplates.setHeader(CANCEL_TITLE_ARY);
			viewTemplates.setColumns(CANCEL_COLUMN_ARY);
		}
		viewTemplates.setDataList(datas);
		return viewTemplates;
	}
	
	private String convertStatus(Long status){
		String result = "";
		switch(status.intValue()){
		case -1:
			result = "全部";
			break;
		case 0:
			result = "待审核";
			break;
		case 1:
			result = "已通过";
			break;
		case 2:
			result = "已拒绝";
			break;
		default:
			result = "";
			break;
		}
		return result;
	}
	
}
