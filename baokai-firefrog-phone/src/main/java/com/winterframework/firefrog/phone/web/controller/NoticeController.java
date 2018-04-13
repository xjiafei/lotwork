package com.winterframework.firefrog.phone.web.controller;

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
import com.winterframework.firefrog.common.util.DateUtils;
import com.winterframework.firefrog.phone.web.cotroller.dto.AdNoticeDetailRequest;
import com.winterframework.firefrog.phone.web.cotroller.dto.AdNoticeListStruc;
import com.winterframework.firefrog.phone.web.cotroller.dto.AdNoticeSearchDto;
import com.winterframework.firefrog.phone.web.cotroller.dto.AdNoticeStruc;
import com.winterframework.firefrog.phone.web.cotroller.dto.NoticeDetailRequest;
import com.winterframework.firefrog.phone.web.cotroller.dto.NoticeDetailResponse;
import com.winterframework.firefrog.phone.web.cotroller.dto.NoticeListDto;
import com.winterframework.firefrog.phone.web.cotroller.dto.NoticeListRequest;
import com.winterframework.firefrog.phone.web.cotroller.dto.NoticeListResponse;
import com.winterframework.firefrog.phone.web.cotroller.dto.UserSecurityUsernameRequest;
import com.winterframework.firefrog.phone.web.cotroller.dto.UserStrucResponse;
import com.winterframework.firefrog.phone.web.cotroller.dto.UserToken;
import com.winterframework.modules.spring.exetend.PropertyConfig;
import com.winterframework.modules.web.jsonresult.Pager;
import com.winterframework.modules.web.jsonresult.Request;
import com.winterframework.modules.web.jsonresult.Response;

@Controller("noticeController")
@RequestMapping("/information")
public class NoticeController extends BaseController {

	private static final Logger log = LoggerFactory.getLogger(NoticeController.class);
	
	@PropertyConfig(value="url.front.noticeList")
	private String noticeListUrl;
	
	@PropertyConfig(value="url.front.noticeDetail")
	private String noticeDetailUrl;
	
	@PropertyConfig(value="url.front.searchUser")
	private String searchUserUrl;
	
	@RequestMapping(value = "/noticeList")
	@ResponseBody
	public Response<NoticeListResponse> noticeList(@RequestBody Request<NoticeListRequest> request) throws Exception{
		
		Response<NoticeListResponse> response = new Response<NoticeListResponse>(request);
		
		try {
			
			NoticeListResponse result = new NoticeListResponse();
			AdNoticeSearchDto gameRequest = new AdNoticeSearchDto();
			// 判斷TOKEN 設定可以查詢的noticeLevel
			//
			String token = request.getHead().getSessionId();
			String account = getUserNameByToken(token);
			if(account!=null){
				UserToken ut2 = getUserToken(account);
				UserSecurityUsernameRequest requestData = new UserSecurityUsernameRequest();
				requestData.setAccount(account);
				Response<UserStrucResponse> userResponse = httpClient.invokeHttp(firefrogUrl+searchUserUrl,requestData, new Pager(), ut2.getUserId(), ut2.getUserName(), new TypeReference<Response<UserStrucResponse>>() {
				});
				Integer vipLv = userResponse.getBody().getResult()==null ? 0: userResponse.getBody().getResult().getVipLvl();
				if(vipLv < 1){
					gameRequest.setRcAll(1l); //不是全部站內用戶
					gameRequest.setRcNonVip(1l); // 不是VIP用戶
				}
			}
			gameRequest.setStatus(3L);
			Pager page = new Pager(0, 20);
			
			Response<AdNoticeListStruc> noticeResponse = httpClient.invokeHttp(firefrogUrl+ noticeListUrl, gameRequest, page, new TypeReference<Response<AdNoticeListStruc>>() {});
			
			List<NoticeListDto> list = new ArrayList<NoticeListDto>();
			
			if(null != noticeResponse && null != noticeResponse.getBody().getResult()){
				
				AdNoticeListStruc strucs = noticeResponse.getBody().getResult();
				
				for(AdNoticeStruc struc : strucs.getNoticeList()){
					
					if(new Date().compareTo(struc.getGmtEffectBegin()) >=0){
						NoticeListDto dto = new NoticeListDto();
						dto.setContent(struc.getContent());
						dto.setId(struc.getId());
						dto.setIshow(1); //0：否、1：是
						dto.setSendtime(DateUtils.format(struc.getGmtEffectBegin(),DateUtils.DATETIME_FORMAT_PATTERN));
						dto.setSubject(struc.getTitle());
						list.add(dto);
					}
				}
				
			}
			result.setList(list);
			response.setResult(result);
			
		} catch (Exception e) {
			log.error("noticeLlist error:",e);
			response.getHead().setStatus(901000L);
		}
		return response;
		
	}
	
	@RequestMapping(value = "/noticeDetail")
	@ResponseBody
	public Response<NoticeDetailResponse> noticeDetail(@RequestBody Request<NoticeDetailRequest> request) throws Exception{
		
		Response<NoticeDetailResponse> response = new Response<NoticeDetailResponse>(request);
		
		try {
			
			NoticeDetailResponse result = new NoticeDetailResponse();
			AdNoticeDetailRequest noticeRequest = new AdNoticeDetailRequest();
			noticeRequest.setId(request.getBody().getParam().getNid());
			
			Response<AdNoticeStruc> noticeResponse = httpClient.invokeHttp(firefrogUrl + noticeDetailUrl, noticeRequest, new Pager(), new TypeReference<Response<AdNoticeStruc>>(){});
			
			if(null != noticeResponse && null != noticeResponse.getBody().getResult() ){
				AdNoticeStruc struc = noticeResponse.getBody().getResult();
				
				result.setContent(struc.getContent());
				result.setId(struc.getId());
				result.setSendtime(DateUtils.format(struc.getGmtEffectBegin(),DateUtils.DATETIME_FORMAT_PATTERN));
				result.setSubject(struc.getTitle());
			}else{
				response.getHead().setStatus(902001L);
			}
			
			response.setResult(result);
			
		} catch (Exception e) {
			
			log.error("noticeDetail error:",e);
			response.getHead().setStatus(901000L);
		}
		return response;
	}

}
