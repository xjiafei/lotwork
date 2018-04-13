package com.winterframework.firefrog.global.web.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.aspectj.weaver.patterns.ThisOrTargetAnnotationPointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.core.type.TypeReference;
import com.winterframework.firefrog.common.http.IHttpJsonClient;
import com.winterframework.firefrog.global.web.dto.GlobalGrayListRequest;
import com.winterframework.firefrog.global.web.dto.GlobalGrayListResponse;
import com.winterframework.firefrog.global.web.dto.GlobalWhiteListIpStruc;
import com.winterframework.firefrog.global.web.dto.GlobalWhiteListLogStruc;
import com.winterframework.firefrog.global.web.dto.GlobalWhiteListRequest;
import com.winterframework.firefrog.global.web.dto.GlobalWhiteListResponse;
import com.winterframework.modules.page.Page;
import com.winterframework.modules.spring.exetend.PropertyConfig;
import com.winterframework.modules.web.jsonresult.Pager;
import com.winterframework.modules.web.jsonresult.Response;
import com.winterframework.modules.web.jsonresult.ResultPager;
import com.winterframework.modules.web.util.RequestContext;

/**
 * 指定IP白名單
 * @author David.Wu
 *
 */
@Controller("WhiteListController")
@RequestMapping(value = "/globeAdmin")
public class WhiteListController {
	
	@PropertyConfig(value = "url.connect")
	private String urlPath;
	
	@Resource(name = "httpJsonClientImpl")
	private IHttpJsonClient httpClient;
	
	@PropertyConfig(value = "page.pagesize")
	private Integer pageSize;
	
	@PropertyConfig(value = "userFileUpload.fileSize")
	private String fileSize;

	@PropertyConfig(value = "userFileUpload.fileType")
	private String fileType;
	
	@PropertyConfig(value = "url.global.queryWhiteListIP")
	private String queryWhiteListIP;
	@PropertyConfig(value = "url.global.queryWhiteListLog")
	private String queryWhiteListLog;
	@PropertyConfig(value = "url.global.saveWhiteListIp")
	private String saveWhiteListIp;
	@PropertyConfig(value = "url.global.queryWhiteListIPDetail")
	private String queryWhiteListIPDetail;
	
	@PropertyConfig(value = "url.global.deleteWhiteListIp")
	private String deleteWhiteListIp;
	
	private static final Logger logger = LoggerFactory.getLogger(WhiteListController.class);

	
	@RequestMapping(value = "/goWhiteList")
	public ModelAndView goWhiteList() throws Exception {
		GlobalWhiteListRequest globalWhiteListRequest = new GlobalWhiteListRequest();
		globalWhiteListRequest.setPageNo(1L);
		globalWhiteListRequest.setType(0);
		return searchWhiteList(globalWhiteListRequest);
	}
	
	@RequestMapping(value = "/searchWhiteList")
	public ModelAndView searchWhiteList(GlobalWhiteListRequest globalWhiteListRequest) throws Exception {
		ModelAndView mav = new ModelAndView("global/whiteList");
		if (globalWhiteListRequest.getPageNo() == null) {
			globalWhiteListRequest.setPageNo(1L);
		}		
		globalWhiteListRequest.setStatus(1L);
		Pager pager = new Pager();
		int startNo = (int) ((globalWhiteListRequest.getPageNo() - 1) * pageSize + 1l);
		pager.setStartNo(startNo);
		pager.setEndNo(pageSize + startNo - 1);
		Response<List<GlobalWhiteListIpStruc>> resp = null;	
		try {
			resp = httpClient.invokeHttp(urlPath + queryWhiteListIP, globalWhiteListRequest, pager,
					new TypeReference<Response<List<GlobalWhiteListIpStruc>>>() {
					});
		} catch (Exception e) {
			logger.error("取得指定IP白名單異常 : " + e);
		}		
		
		Response<List<GlobalWhiteListLogStruc>> logResp = null;
		try {
			logResp = httpClient.invokeHttp(urlPath + queryWhiteListLog, globalWhiteListRequest, pager,
					new TypeReference<Response<List<GlobalWhiteListLogStruc>>>() {
					});
		} catch (Exception e) {
			logger.error("取得指定IP白名單LOG異常 : " + e);
		}
		
		ResultPager resultPage = resp.getBody().getPager();
		int pageNo = resultPage.getStartNo() / pageSize + (resultPage.getStartNo() % pageSize == 0 ? 0 : 1);
		Page<Object> page = new Page<Object>(pageNo, pageSize.intValue(), resultPage.getTotal());
				
		mav.addObject("page", page);
		mav.addObject("ipList", resp.getBody().getResult() );
		mav.addObject("search", globalWhiteListRequest);
		mav.addObject("mode", "list");	
		mav.addObject("logList", logResp.getBody().getResult() );
		//mav.addObject("global_no", 2);
		return mav;	
	}
		
	@RequestMapping(value = "/addWhiteList")
	public ModelAndView addWhiteList(GlobalWhiteListRequest globalWhiteListRequest){
		ModelAndView mav = new ModelAndView("global/whiteListAdd");
		globalWhiteListRequest.setMode(0);
		globalWhiteListRequest.setMsg("");
		mav.addObject("input", globalWhiteListRequest);
		mav.addObject("mode", 0);	
		return mav;	
	}
	@RequestMapping(value = "/saveWhiteList")
	public ModelAndView saveWhiteList(GlobalWhiteListRequest globalWhiteListRequest){
		Response<GlobalWhiteListResponse> response = new  Response<GlobalWhiteListResponse>();
		ModelAndView mav = null;
		int mode = globalWhiteListRequest.getMode();
		if(mode == 1){//0 add , 1 edit
			mav = new ModelAndView("global/whiteListEdit");
			mav.addObject("mode", 1);
		}else{
			mav = new ModelAndView("global/whiteListAdd");
			mav.addObject("mode", 0);
		}
		Date now = Calendar.getInstance().getTime();
		globalWhiteListRequest.setOperator(RequestContext.getCurrUser().getUserName());
		globalWhiteListRequest.setOperationTime(now);
		try {
			response = httpClient.invokeHttp(urlPath + saveWhiteListIp, globalWhiteListRequest,
					new TypeReference<Response<GlobalWhiteListResponse>>(){
				
			});
			GlobalWhiteListResponse result = response.getBody().getResult();
			if(result.getIsSuccess() > 0){
				return this.goWhiteList();
			}else{				
				logger.error("指定IP白名單紀錄異常 : " + result.getMsg());
				globalWhiteListRequest.setMsg("纪录异常 :" + result.getMsg());
				mav.addObject("input", globalWhiteListRequest);
				return mav;	
			}
		} catch (Exception e) {
			//IpExist
			logger.error("指定IP白名單紀錄異常 : " + e);
			globalWhiteListRequest.setMsg("提交异常，请新提交");
			mav.addObject("input", globalWhiteListRequest);
			return mav;	
		}			
	}
	@RequestMapping(value = "/deleteWhiteListIP")
	public ModelAndView deleteIp(GlobalWhiteListRequest globalWhiteListRequest) throws Exception {
		globalWhiteListRequest.setOperator(RequestContext.getCurrUser().getUserName());
		Date now = Calendar.getInstance().getTime();
		globalWhiteListRequest.setOperationTime(now);
		try{
		httpClient.invokeHttpWithoutResultType(urlPath + deleteWhiteListIp, globalWhiteListRequest);
		}catch(Exception e){
			logger.error("刪除指定IP白名單異常 : " + e);
			globalWhiteListRequest.setMsg("删除指定IP白名单异常");
		}
		return searchWhiteList(globalWhiteListRequest);
	}
	
	@RequestMapping(value = "/editWhiteListIp")
	public ModelAndView editWhiteListIp(GlobalWhiteListRequest globalWhiteListRequest) throws Exception {
		Response<GlobalWhiteListIpStruc> resp = null;
		try{
			resp = httpClient.invokeHttpWithoutResultType(urlPath + queryWhiteListIPDetail, globalWhiteListRequest);
		}catch(Exception e){
			logger.error("修改指定IP白名單異常 : " + e);
			globalWhiteListRequest.setMsg("修改指定IP白名单异常");
		}		
		
		ModelAndView mav = new ModelAndView("global/whiteListEdit");
		mav.addObject("input", resp.getBody().getResult());	
		mav.addObject("mode", 1);	
		return mav;
	}
	
	@RequestMapping(value = "/viewIp")
	public ModelAndView viewIp(GlobalWhiteListRequest globalWhiteListRequest) throws Exception {
		Response<GlobalWhiteListIpStruc> resp = null;
		try{
			resp = httpClient.invokeHttpWithoutResultType(urlPath + queryWhiteListIPDetail, globalWhiteListRequest);
		}catch(Exception e){
			logger.error("查看指定IP白名單異常 : " + e);
			globalWhiteListRequest.setMsg("查看指定IP白名单异常");
		}		
		ModelAndView mav = new ModelAndView("global/whiteListView");
		mav.addObject("input", resp.getBody().getResult());	
		return mav;
	}
	
	
	@RequestMapping(value = "/goGrayList")
	public ModelAndView goGrayList() throws Exception {
		ModelAndView mav = new ModelAndView("global/grayList");
		return mav;
	}
	
	@RequestMapping(value = "/queryGrayList")
	@ResponseBody
	public Response<GlobalGrayListResponse> queryGrayList(@RequestBody GlobalGrayListRequest userAccount) throws Exception {
		//ModelAndView mav = new ModelAndView("global/grayList");
		GlobalGrayListRequest req = new GlobalGrayListRequest();
		req.setAccount(userAccount.getAccount());
		Response<GlobalGrayListResponse> resp = null;
		resp = httpClient.invokeHttp(urlPath + "/globeAdmin/queryGrayList", req,
				new TypeReference<Response<GlobalGrayListResponse>>(){});
		GlobalGrayListResponse res = resp.getBody().getResult();
		
		//v.addObject("userData", res.getUserData());	
		return resp;
	}
	
	@RequestMapping(value = "/queryGrayList2")
	@ResponseBody
	public Response<GlobalGrayListResponse> queryGrayList2(@RequestBody GlobalGrayListRequest userAccount) throws Exception {
		//ModelAndView mav = new ModelAndView("global/grayList");
		GlobalGrayListRequest req = new GlobalGrayListRequest();
		req.setAccount(userAccount.getAccount());
		Response<GlobalGrayListResponse> resp = null;
		resp = httpClient.invokeHttp(urlPath + "/globeAdmin/queryGrayList2", req,
				new TypeReference<Response<GlobalGrayListResponse>>(){});
		GlobalGrayListResponse res = resp.getBody().getResult();
		
		//v.addObject("userData", res.getUserData());	
		return resp;
	}
}
