package com.winterframework.firefrog.phone.web.controller;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.core.type.TypeReference;
import com.winterframework.firefrog.game.dao.vo.FundChangeLog;
import com.winterframework.firefrog.game.web.dto.FundLogReq;
import com.winterframework.firefrog.game.web.util.GameFundTypesUtils;
import com.winterframework.firefrog.phone.utils.ReasonType;
import com.winterframework.firefrog.phone.web.cotroller.dto.UserBankReportDto;
import com.winterframework.firefrog.phone.web.cotroller.dto.UserBankReportRequest;
import com.winterframework.firefrog.phone.web.cotroller.dto.UserBankReportResponse;
import com.winterframework.firefrog.phone.web.cotroller.dto.UserDetailResponse;
import com.winterframework.firefrog.phone.web.cotroller.dto.UserStruc;
import com.winterframework.modules.spring.exetend.PropertyConfig;
import com.winterframework.modules.web.jsonresult.Pager;
import com.winterframework.modules.web.jsonresult.Request;
import com.winterframework.modules.web.jsonresult.Response;

@Controller("bankController")
@RequestMapping("/user")
public class BankController extends BaseController {
	
	private static final Logger log = LoggerFactory.getLogger(BankController.class);
	@PropertyConfig(value="url.user.userBankReport")
	private String userBankReportUrl;
	@PropertyConfig(value="url.user.queryUserDetailInfoByAccount")
	private String queryUserByNameUrl;
	

	
	@ResponseBody
	@RequestMapping("/userBankReport")
	public Response<UserBankReportResponse> userBankReport(@RequestBody Request<UserBankReportRequest> request) throws Exception{
		
		Response<UserBankReportResponse> response =new  Response<UserBankReportResponse>(request);
		try {
			UserBankReportResponse result = new UserBankReportResponse();
			//根据用户名找用信息。
			UserStruc userRequestData = new UserStruc();
			userRequestData.setAccount(request.getBody().getParam().getUsername());
			
			Response<UserDetailResponse> userDataResponse = httpClient.invokeHttp(firefrogUrl+ queryUserByNameUrl, userRequestData, new TypeReference<Response<UserDetailResponse>>() {
			});
			
			if(null == userDataResponse.getBody() || null == userDataResponse.getBody().getResult()){
				response.getHead().setStatus(101004L);
				return response;
			}
			
			FundLogReq requestData = new FundLogReq();
			requestData.setAccount(userDataResponse.getBody().getResult().getUserStruc().getAccount());
			requestData.setUserId(userDataResponse.getBody().getResult().getUserStruc().getId());
			
			Response<List<FundChangeLog>> gameResponse = httpClient.invokeHttp(firefrogUrl+userBankReportUrl, requestData, new Pager(0, 10000),new  TypeReference<Response<List<FundChangeLog>>>() {
			});
			
			List<UserBankReportDto> reportList = new ArrayList<UserBankReportDto>();
			if(null != gameResponse.getBody() && null != gameResponse.getBody().getResult()){
				List<FundChangeLog> list = gameResponse.getBody().getResult();
				for(FundChangeLog struc : list){
					
					UserBankReportDto dto = new UserBankReportDto();
					dto.setAmount(new BigDecimal(struc.getCtDamt()==null? 0 : struc.getCtDamt()).divide(new BigDecimal(10000),3,RoundingMode.HALF_UP).doubleValue());
					dto.setBalance(new BigDecimal(struc.getCtBal()==null? 0 : struc.getCtBal()).divide(new BigDecimal(10000),3,RoundingMode.HALF_UP).doubleValue());
					if (GameFundTypesUtils.addReason.contains(struc.getReason())) {
//						dto.setOperations("+");
						dto.setOperations("1");
					} else {
//						dto.setOperations("-");
						dto.setOperations("0");
					}
					dto.setTime(struc.getGmtCreatedString());
					String[] reasons = struc.getReason().split(",");
					dto.setTitle(ReasonType.TITLE.get(reasons[0]+"-"+reasons[1]) == null ? "" : ReasonType.TITLE.get(reasons[0]+"-"+reasons[1]));
					dto.setUsername(struc.getAccount());
					reportList.add(dto);
				}
			}
			result.setList(reportList);
			response.setResult(result);
			
		} catch (Exception e) {
			log.error("userBankReport error:", e);
			response.getHead().setStatus(901000L);
		}
		return response;
	}

}
