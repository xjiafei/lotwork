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
import com.winterframework.firefrog.phone.utils.ReasonType;
import com.winterframework.firefrog.phone.web.cotroller.dto.BankReportDto;
import com.winterframework.firefrog.phone.web.cotroller.dto.BankReportReponse;
import com.winterframework.firefrog.phone.web.cotroller.dto.BankReportRequest;
import com.winterframework.firefrog.phone.web.cotroller.dto.UserToken;
import com.winterframework.modules.spring.exetend.PropertyConfig;
import com.winterframework.modules.web.jsonresult.Pager;
import com.winterframework.modules.web.jsonresult.Request;
import com.winterframework.modules.web.jsonresult.Response;

@RequestMapping("/bank")
@Controller("bankReportController")
public class BankReportController extends BaseController{

	
	private static final Logger log = LoggerFactory.getLogger(BankReportController.class);
	
	@PropertyConfig(value="url.user.userBankReport")
	private String userBankReportUrl;
	
	@ResponseBody
	@RequestMapping("/bankReport")
	public Response<BankReportReponse> bankReport(@RequestBody Request<BankReportRequest> request) throws Exception{
		
		Response<BankReportReponse> response = new Response<BankReportReponse>(request);
		
		String token = request.getHead().getSessionId();
		try {
			
			BankReportReponse result = new BankReportReponse();
			String account = getUserNameByToken(token);
			if(null == account){
				response.getHead().setStatus(7L);
				return response;
			}
			UserToken ut = getUserToken(account);
			
			
			FundLogReq requestData = new FundLogReq();
			requestData.setAccount(ut.getUserName());
			requestData.setUserId(ut.getUserId());
	/*		'ADAL'=>array('ADAL','ADML','MDAX'),
			'CWCS'=>array('CWCS','CWTF','CWTS','CWCF'),
			'CWCR'=>array('CWCR','CWTR'),
			'DVCB'=>array('DVCB','DVCN'),
			
			'RHAX'=>array('RHAX','RSXX'),
			'RRHA'=>array('RRHA','RRSX'),
			'BIRX'=>array('BIRX','RRXX'),
			'SOSX'=>array('SOSX','WPXX'),*/
			String[] summary = null; 
			if(request.getBody().getParam() != null && request.getBody().getParam().getOrdertype() != null){
//				requestData.setReason(request.getBody().getParam().getOrdertype());
				String reason = request.getBody().getParam().getOrdertype();
				if(reason.equals("ADAL")){
					summary = new String[3];
					summary[0]="ADAL";
					summary[1]="ADML";
					summary[2]="MDAX";
				}else if(reason.equals("CWCS")){
					summary = new String[4];
					summary[0]="CWCS";
					summary[1]="CWTF";
					summary[2]="CWTS";
					summary[3]="CWCF";
				}else if(reason.equals("CWCR")){
					summary = new String[2];
					summary[0]="CWCR";
					summary[1]="CWTR";
				}else if(reason.equals("DVCB")){
					summary = new String[2];
					summary[0]="DVCB";
					summary[1]="DVCN";
				}else if(reason.equals("RHAX")){
					summary = new String[2];
					summary[0]="RHAX";
					summary[1]="RSXX";
				}else if(reason.equals("RRHA")){
					summary = new String[2];
					summary[0]="RRHA";
					summary[1]="RRSX";
				}else if(reason.equals("BIRX")){
					summary = new String[2];
					summary[0]="BIRX";
					summary[1]="RRXX";
				}else if(reason.equals("SOSX")){
					summary = new String[2];
					summary[0]="SOSX";
					summary[1]="WPXX";
				}else{
					summary = new String[1];
					summary[0]=reason;
				}
				requestData.setSummary(summary);
			}
			
			Response<List<FundChangeLog>> gameResponse = httpClient.invokeHttp(firefrogUrl+userBankReportUrl, requestData, new Pager(0, 10000),new  TypeReference<Response<List<FundChangeLog>>>() {
			});
			List<BankReportDto> list = new ArrayList<BankReportDto>();
			if(null != gameResponse.getBody() && null != gameResponse.getBody().getResult()){
				
				List<FundChangeLog> strucs = gameResponse.getBody().getResult();
				for(FundChangeLog struc:strucs){
					BankReportDto dto = new BankReportDto();
					
					BigDecimal beforeDamt2 = new BigDecimal(struc.getCtBal()==null ? 0: struc.getCtBal()).divide(new BigDecimal(10000),5,BigDecimal.ROUND_HALF_UP);
					BigDecimal ctDamt2 = new BigDecimal(struc.getBeforBal()==null ? 0 : struc.getBeforBal()).divide(new BigDecimal(10000),5,BigDecimal.ROUND_HALF_UP);
					BigDecimal beforeDamt = new BigDecimal(struc.getBeforeDamt()==null ? 0 : struc.getBeforeDamt()).divide(new BigDecimal(10000),5,BigDecimal.ROUND_HALF_UP);
					BigDecimal ctDamt = new BigDecimal(struc.getCtDamt()==null?0:struc.getCtDamt()).divide(new BigDecimal(10000),5,BigDecimal.ROUND_HALF_UP);
					
					dto.setAmount(beforeDamt.subtract(ctDamt).doubleValue()== 0 ?  beforeDamt2.subtract(ctDamt2).doubleValue():beforeDamt.subtract(ctDamt).doubleValue());
					
					dto.setBalance(new BigDecimal(struc.getCtBal()==null ? 0: struc.getCtBal()).divide(new BigDecimal(10000),4,RoundingMode.HALF_UP).doubleValue());
					String[] reasons = struc.getReason().split(",");
					dto.setOrdertype(ReasonType.TITLE.get(reasons[0]+"-"+reasons[1]) == null ? "" : ReasonType.TITLE.get(reasons[0]+"-"+reasons[1]));
					dto.setTime(struc.getGmtCreatedString());
					list.add(dto);
				}
			}
			
			result.setList(list);
			response.setResult(result);
			
		} catch (Exception e) {
			log.error("bankReport error:", e);
			response.getHead().setStatus(901000L);
		}
		return response;
	}
	
}
