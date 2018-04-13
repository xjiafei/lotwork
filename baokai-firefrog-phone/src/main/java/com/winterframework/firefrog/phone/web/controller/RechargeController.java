package com.winterframework.firefrog.phone.web.controller;

import java.math.BigDecimal;
import java.math.RoundingMode;
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
import com.winterframework.firefrog.phone.web.cotroller.dto.BankCardQueryRecordResponse;
import com.winterframework.firefrog.phone.web.cotroller.dto.BindDate;
import com.winterframework.firefrog.phone.web.cotroller.dto.ChargeApplyRequest;
import com.winterframework.firefrog.phone.web.cotroller.dto.ChargeApplyResponse;
import com.winterframework.firefrog.phone.web.cotroller.dto.FundBank;
import com.winterframework.firefrog.phone.web.cotroller.dto.RechargeConfirmRequest;
import com.winterframework.firefrog.phone.web.cotroller.dto.RechargeConfirmResponse;
import com.winterframework.firefrog.phone.web.cotroller.dto.RechargeInitDto;
import com.winterframework.firefrog.phone.web.cotroller.dto.RechargeInitRequest;
import com.winterframework.firefrog.phone.web.cotroller.dto.RechargeInitResponse;
import com.winterframework.firefrog.phone.web.cotroller.dto.UserStrucResponse;
import com.winterframework.firefrog.phone.web.cotroller.dto.UserToken;
import com.winterframework.modules.spring.exetend.PropertyConfig;
import com.winterframework.modules.web.jsonresult.Pager;
import com.winterframework.modules.web.jsonresult.Request;
import com.winterframework.modules.web.jsonresult.Response;

@Controller("rechargeController")
@RequestMapping("/recharge")
public class RechargeController extends BaseController{
	
	private static final Logger log = LoggerFactory.getLogger(RechargeController.class) ;

	@PropertyConfig(value="url.fund.cardBindingInit")
	private String cardBindingInitUrl;
	@PropertyConfig(value="url.recharge.confirm")
	private String confirmUrl;
	@PropertyConfig(value="url.front.teamUserBalance")
	private String teamUserBalanceUrl;
	
	@ResponseBody
	@RequestMapping("/init")
	public Response<RechargeInitResponse> init(@RequestBody Request<RechargeInitRequest> request) throws Exception{
		
		Response<RechargeInitResponse> response = new Response<RechargeInitResponse>(request);
		
		String token = request.getHead().getSessionId();
		try {
			String account = getUserNameByToken(token);
			if(null == account){
				response.getHead().setStatus(7L);
				return response;
			}
			UserToken ut = getUserToken(account);
			
			BindDate requestData = new BindDate();
			requestData.setIsBind(0);
			
			RechargeInitResponse result = new RechargeInitResponse();
			Response<BankCardQueryRecordResponse> gameResponse = httpClient.invokeHttp(firefrogUrl+ cardBindingInitUrl, requestData,new Pager(), ut.getUserId(), ut.getUserName(), new TypeReference<Response<BankCardQueryRecordResponse>>() {
			});
			
			List<RechargeInitDto> list =new ArrayList<RechargeInitDto>();
			if(null != gameResponse.getBody() && null != gameResponse.getBody().getResult()){
				
				BankCardQueryRecordResponse cards = gameResponse.getBody().getResult();
				
//				for(UserBankStruc struc : cards.getUserBankStruc()){
					
					for(FundBank bank : cards.getBankStruc()){
						
//						if(struc.getBankId() == bank.getId()){
						if(bank.getId() < 30L&&bank.getDeposit().longValue()==1L){
							RechargeInitDto dto = new RechargeInitDto();
							
//							dto.setAccountName(bank.getName());
							dto.setBank(bank.getId()+"");
							dto.setBankName(bank.getName());
//							dto.setMcBankId(struc.getMcBankId());
//							dto.setBid(struc.getBankId());
//							dto.setHiddenaccount(struc.getBankNumber());
							dto.setLoadmax(new BigDecimal(bank.getUplimit()==null ? 0: bank.getUplimit()).divide(new BigDecimal(10000),2,RoundingMode.HALF_UP).longValue());
							dto.setLoadmin(new BigDecimal(bank.getLowlimit()==null ? 0 : bank.getLowlimit()).divide(new BigDecimal(10000),2,RoundingMode.HALF_UP).longValue());
							list.add(dto);
						}
//						}
					}
//				}
			}
			result.setList(list);
			response.setResult(result);
			
		} catch (Exception e) {
			log.error("RechargeInit error:", e);
			response.getHead().setStatus(901000L);
		}
		return response;
	}
	
	@ResponseBody
	@RequestMapping("/confirm")
	public Response<RechargeConfirmResponse> confirm(@RequestBody Request<RechargeConfirmRequest> request) throws Exception{
		
		Response<RechargeConfirmResponse> response = new Response<RechargeConfirmResponse>(request);
		
		String token = request.getHead().getSessionId();
		try {
			String account = getUserNameByToken(token);
			if(null == account){
				response.getHead().setStatus(7L);
				return response;
			}
			UserToken ut = getUserToken(account);
			
			//add 冻结判断 	
			Response<UserStrucResponse> firforgResponse = httpClient.invokeHttp(firefrogUrl+teamUserBalanceUrl, null,new Pager(),ut.getUserId(), null, new TypeReference<Response<UserStrucResponse>>() {
			});
			
			UserStrucResponse user = firforgResponse.getBody().getResult();
			if(user.getFreezeMethod() !=null && user.getFreezeMethod()>0){
				
				//完全冻结
				//1.完全冻结，不能登陆，
				//2.可登陆，不可投注，不可充提
				//3.可登陆，不可投注，可充提
				//4.不可转帐，不可提现。
				if(user.getFreezeMethod().intValue() ==1 ||user.getFreezeMethod().intValue() == 2){
					response.getHead().setStatus(109992L);//109992 此功能已经冻结
					return response;
				}
			}
			
			RechargeConfirmRequest confirmRequest = request.getBody().getParam();
			/*Response<UserStrucResponse> firforgResponse = httpClient.invokeHttp(firefrogUrl+teamUserBalanceUrl, null,new Pager(),ut.getUserId(), null, new TypeReference<Response<UserStrucResponse>>() {
			});
			
			if(!firforgResponse.getBody().getResult().getWithdrawPasswd().equals(confirmRequest.getSecpwd())){
				response.getHead().setStatus(102001);
				return response;
			}*/
			
			
			
			ChargeApplyRequest requestData = new ChargeApplyRequest();
			requestData.setApplyTime(new Date());
			requestData.setBankId(confirmRequest.getBank());
			requestData.setDepositMode(1L);
			requestData.setMcBankId(confirmRequest.getMcBankId());
//			requestData.setMemo(confirmRequest.get);
			requestData.setPreChargeAmt(new BigDecimal(confirmRequest.getAmount()).multiply(new BigDecimal(10000)).setScale(2, RoundingMode.HALF_UP).longValue());
			requestData.setUserAct(ut.getUserName());
			requestData.setUserId(ut.getUserId());
			
			
			Response<ChargeApplyResponse> firefrogResponse = httpClient.invokeHttp(firefrogUrl+confirmUrl, requestData, new Pager(), ut.getUserId(), ut.getUserName(), new TypeReference<Response<ChargeApplyResponse>>() {
			});
			
			RechargeConfirmResponse result = new RechargeConfirmResponse();
			if(null != firefrogResponse.getBody() && null != firefrogResponse.getBody().getResult()){
				ChargeApplyResponse apply = firefrogResponse.getBody().getResult();
				result.setAccName(apply.getRevAccName());
				//result.setAccount(apply.getCards() == null ? "": apply.getCards()[0].getBankAccount());
				result.setAccount(apply.getRcvAccNum());
				result.setAmount(new BigDecimal(confirmRequest.getAmount()).doubleValue());
//				result.setArea(apply.getRcvBankName());//apply.getCards() == null ? "" :apply.getCards()[0].getCity());
				result.setEmail(apply.getRcvEmail());
				result.setKey(apply.getChargeMemo());
				result.setShortname( apply.getRcvBankName());
			}
			
			response.setResult(result);
		} catch (Exception e) {
			log.error("RechargeConfirm error:", e);
			response.getHead().setStatus(901000L);
		}
		
		return response;
	}
}
