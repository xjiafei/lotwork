package com.winterframework.firefrog.phone.web.controller;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.core.type.TypeReference;
import com.winterframework.firefrog.common.util.DateUtils;
import com.winterframework.firefrog.phone.web.cotroller.dto.Banks;
import com.winterframework.firefrog.phone.web.cotroller.dto.Datas;
import com.winterframework.firefrog.phone.web.cotroller.dto.QuStrucResponse;
import com.winterframework.firefrog.phone.web.cotroller.dto.Questions;
import com.winterframework.firefrog.phone.web.cotroller.dto.UserBankStruc;
import com.winterframework.firefrog.phone.web.cotroller.dto.UserDto;
import com.winterframework.firefrog.phone.web.cotroller.dto.UserStrucResponse;
import com.winterframework.firefrog.phone.web.cotroller.dto.UserToken;
import com.winterframework.firefrog.phone.web.cotroller.dto.WithdrawApplyRequest;
import com.winterframework.firefrog.phone.web.cotroller.dto.WithdrawApplyResponse;
import com.winterframework.firefrog.phone.web.cotroller.dto.WithdrawCommitRequest;
import com.winterframework.firefrog.phone.web.cotroller.dto.WithdrawCommitResponse;
import com.winterframework.firefrog.phone.web.cotroller.dto.WithdrawInitPhoneRequest;
import com.winterframework.firefrog.phone.web.cotroller.dto.WithdrawInitPhoneResponse;
import com.winterframework.firefrog.phone.web.cotroller.dto.WithdrawInitRequest;
import com.winterframework.firefrog.phone.web.cotroller.dto.WithdrawInitResponse;
import com.winterframework.firefrog.phone.web.cotroller.dto.WithdrawVerifyRequest;
import com.winterframework.firefrog.phone.web.cotroller.dto.WithdrawVerifyResponse;
import com.winterframework.modules.spring.exetend.PropertyConfig;
import com.winterframework.modules.web.jsonresult.Pager;
import com.winterframework.modules.web.jsonresult.Request;
import com.winterframework.modules.web.jsonresult.Response;

@Controller("withdrawController")
@RequestMapping("/withdraw")
public class WithdrawController extends BaseController {
	
	private static final Logger log = LoggerFactory.getLogger(WithdrawController.class);
	
	@PropertyConfig("url.withdraw.init")
	private String initUrl;
	@PropertyConfig(value="url.front.teamUserBalance")
	private String checkSecpassUrl;
	@PropertyConfig(value = "url.withdraw.commit")
	private String commitUrl;
	@PropertyConfig(value="url.front.teamUserBalance")
	private String teamUserBalanceUrl;

	@ResponseBody
	@RequestMapping("/init")
	public Response<WithdrawInitPhoneResponse> init(@RequestBody Request<WithdrawInitPhoneRequest> request) throws Exception{
		
		Response<WithdrawInitPhoneResponse> response  = new Response<WithdrawInitPhoneResponse>(request);
		
		String token = request.getHead().getSessionId();
		try {
			String account = getUserNameByToken(token);
			if(null == account){
				response.getHead().setStatus(7L);
				return response;
			}
			UserToken ut = getUserToken(account);
			
			WithdrawInitRequest requestData = new WithdrawInitRequest();
			requestData.setBindcardType(0L);
			requestData.setUserId(ut.getUserId());
			
			Response<WithdrawInitResponse> firefrogResponse = httpClient.invokeHttp(firefrogUrl+initUrl, requestData, new Pager(),ut.getUserId(), ut.getUserName(), new TypeReference<Response<WithdrawInitResponse>>() {
			});
			
			WithdrawInitPhoneResponse result = new WithdrawInitPhoneResponse();
			if(null != firefrogResponse.getBody() && null != firefrogResponse.getBody().getResult()){
				
				WithdrawInitResponse init = firefrogResponse.getBody().getResult();
				
				result.setAvailablebalance(new BigDecimal(init.getBal()==null ? 0 : init.getBal()).divide(new BigDecimal(10000), 2, RoundingMode.HALF_UP).doubleValue());
				result.setTimes(init.getAvailWithdrawTime()==null ? 0 : init.getAvailWithdrawTime());
				result.setMaxWithdrawMoney(new BigDecimal(init.getAvailBal()==null ? 0 : init.getAvailBal()).divide(new BigDecimal(10000), 2, RoundingMode.HALF_UP).doubleValue());
				
				if(init.getUserBankStruc().isEmpty()){
					response.getHead().setStatus(109995L);//109995	尚未绑定银行卡
					response.setResult(null);
					return response;
				}
				
				List<Banks> banks = new ArrayList<Banks>();
				for(UserBankStruc struc :init.getUserBankStruc()){
					Banks bank = new Banks();
					//TODO 判斷綁卡時間，未達一小時視為未綁定銀行卡
					if(! DateUtils.betweenOneHour(struc.getBindDate())){					
						bank.setAccount(struc.getBankNumber());
						bank.setAccountName(struc.getBankAccount());
						bank.setBankId(struc.getBankId().intValue());
						bank.setBankName(Bank_Name_Map.get(struc.getBankId().toString()));
//						bank.setCity(struc.getCity());
						bank.setId(struc.getId().intValue());
//						bank.setProvince(struc.getProvince());
						banks.add(bank);
					}
					
				}
				if(banks.size()>0){
					result.setBanks(banks);
				}else{
					response.getHead().setStatus(109995L);//109995	尚未绑定银行卡
					response.setResult(null);
					return response;
				}
				
				result.setUpLimit(new BigDecimal(init.getWithdrawStruc().getUser().getUpLimit()).divide(new BigDecimal(10000)).doubleValue());
				result.setLowLimit(new BigDecimal(init.getWithdrawStruc().getUser().getLowLimit()).divide(new BigDecimal(10000)).doubleValue());
				
			/*	UserInfo userinfo = new UserInfo();
				userinfo.setAvailablebalance(new BigDecimal(init.getWithdrawStruc().getUser().getUpLimit()).divide(new BigDecimal(10000)).doubleValue());
				userinfo.setUserid(ut.getUserId());
				userinfo.setUsername(ut.getUserName());
				result.setUserinfo(userinfo);*/
				
			}
			
			response.setResult(result);
			
		} catch (Exception e) {
			log.error("withdraw init error:", e);
			response.getHead().setStatus(901000L);
		}
		return response;
	}
	
	
	@ResponseBody
	@RequestMapping("/verify")
	public Response<WithdrawVerifyResponse> verify(@RequestBody Request<WithdrawVerifyRequest> request) throws Exception{
		
		Response<WithdrawVerifyResponse> response = new Response<WithdrawVerifyResponse>(request);
		
//		Map<String,String> map = new HashMap<String,String>();
		
		String token = request.getHead().getSessionId();
		try {
			String account = getUserNameByToken(token);
			if(null == account){
				response.getHead().setStatus(7L);
				return response;
			}
			UserToken ut = getUserToken(account);
			
			WithdrawVerifyRequest verifyRequest = request.getBody().getParam();
			String[] bankInfo = verifyRequest.getBankinfo().split("#");
			
			WithdrawInitRequest requestData = new WithdrawInitRequest();
			requestData.setUserId(ut.getUserId());
			requestData.setBindcardType(0L);
			Response<WithdrawInitResponse> firefrogResponse = httpClient.invokeHttp(firefrogUrl+initUrl, requestData, ut.getUserId(), ut.getUserName(), new TypeReference<Response<WithdrawInitResponse>>() {
			});
			
//			map.put("1", "我宠物的名字？");
//			map.put("2", "我最喜欢的演员？");
//			map.put("3", "我最喜欢的球队？");
			
			WithdrawVerifyResponse result = new WithdrawVerifyResponse();
			if(null != firefrogResponse.getBody() && null != firefrogResponse.getBody().getResult()){
				WithdrawInitResponse init = firefrogResponse.getBody().getResult();
				for(UserBankStruc struc :init.getUserBankStruc()){
					if(struc.getId()== Long.parseLong(bankInfo[0]) ){
						
						Datas datas = new Datas();
						datas.setBankName(Bank_Name_Map.get(struc.getBankId().toString()));
						datas.setBankno(struc.getBankId().toString());
						datas.setCardid(struc.getBankNumber());
						datas.setMoney(verifyRequest.getMoney());
						datas.setTruename(struc.getBankAccount());
						result.setDatas(datas);
					}
				}
				//用户的安全问题信息。
				Response<UserStrucResponse> userResponse = httpClient.invokeHttp(firefrogUrl+checkSecpassUrl, request,new Pager(), ut.getUserId(),account, new TypeReference<Response<UserStrucResponse>>() {
				});
				
				List<Questions> list = new ArrayList<Questions>();
				
				Random random = new Random();
				int key = random.nextInt(7);
				while(true){
					boolean t = false;
					for(QuStrucResponse qs : userResponse.getBody().getResult().getQuStruc()){
						
						if(key==qs.getQu()){
							t=true;
						}
					}
					
					if(t){
						break;
					}
					key = random.nextInt(7);					
				}
				Questions q = new Questions();				
				q.setQuestion(QUESTION.get(String.valueOf(key)));
				q.setQuestionId(key);
				
				list.add(q);
				
				result.setQuestions(list);
				
//				result.setAvailablebalance(new BigDecimal(init.getAvailBal()==null ? 0 : init.getAvailBal()).divide(new BigDecimal(10000), 2, RoundingMode.HALF_UP).doubleValue());
				UserDto user = new UserDto();
				user.setUserName(ut.getUserName());
				user.setAvailablebalance(new BigDecimal(init.getAvailBal()==null ? 0 : init.getAvailBal()).divide(new BigDecimal(10000), 2, RoundingMode.HALF_UP).doubleValue());
				result.setUser(user);
			}
			
			response.setResult(result);
			
		} catch (Exception e) {
			log.error("withdraw verify error:", e);
			response.getHead().setStatus(901000L);
		}
		
		return response;
	}
	
	@ResponseBody
	@RequestMapping("/commit")
	public Response<WithdrawCommitResponse> commit(@RequestBody Request<WithdrawCommitRequest> request) throws Exception{
		
		Response<WithdrawCommitResponse> response = new Response<WithdrawCommitResponse>(request);
		
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
			UserStrucResponse us = firforgResponse.getBody().getResult();
			if(us.getFreezeMethod() !=null && us.getFreezeMethod()>0){
				
				//完全冻结
				//1.完全冻结，不能登陆，
				//2.可登陆，不可投注，不可充提
				//3.可登陆，不可投注，可充提
				//4.不可转帐，不可提现。
				if(us.getFreezeMethod().intValue() ==1 ||us.getFreezeMethod().intValue() ==2 || us.getFreezeMethod().intValue()==4){
					response.getHead().setStatus(109992L);//109992 此功能已经冻结
					return response;
				}
			}
			
			WithdrawCommitRequest commitRequest = request.getBody().getParam();
			
			WithdrawApplyRequest requestData = new WithdrawApplyRequest();
			
			//用户的安全问题信息。
			Response<UserStrucResponse> userResponse = httpClient.invokeHttp(firefrogUrl+checkSecpassUrl, request,new Pager(), ut.getUserId(),account, new TypeReference<Response<UserStrucResponse>>() {
			});
			
			//1072	答案与问题不匹配
			
			UserStrucResponse user = userResponse.getBody().getResult();
			boolean bs = true;
			for(QuStrucResponse qs : user.getQuStruc()){
				if(qs.getQu()==commitRequest.getQuestionId()){
					if(qs.getAns().equals(commitRequest.getQuestionpwd())){
						bs = false;
					}
				}
			}
			
			if(bs){
				response.getHead().setStatus(1072L);
				return response;
			}
			
			if(!user.getWithdrawPasswd().equals(commitRequest.getSecpwd())){
				response.getHead().setStatus(102001L);
				return response;
			}
			
			WithdrawInitRequest initrequestData = new WithdrawInitRequest();
			initrequestData.setUserId(ut.getUserId());
			initrequestData.setBindcardType(0L);
			Response<WithdrawInitResponse> firefrogResponse = httpClient.invokeHttp(firefrogUrl+initUrl, initrequestData, ut.getUserId(), ut.getUserName(), new TypeReference<Response<WithdrawInitResponse>>() {
			});
			
			UserBankStruc userBankStruc = new UserBankStruc();
			if(null != firefrogResponse.getBody() && null != firefrogResponse.getBody().getResult()){
				WithdrawInitResponse init = firefrogResponse.getBody().getResult();
				for(UserBankStruc struc :init.getUserBankStruc()){
					log.info("struc.Id ="+struc.getId()+"commitRequest.bandId="+commitRequest.getBindId());
					if(struc.getId().longValue()==commitRequest.getBindId().longValue() ){
						
						userBankStruc.setBankId(struc.getBankId());
						userBankStruc.setBankAccount(struc.getBankAccount());
						userBankStruc.setBankNumber(struc.getBankNumber());
					}
				}
			}
			
			requestData.setBankId(userBankStruc.getBankId());
			requestData.setIpAddr(commitRequest.getIpAddr());
			requestData.setIsVip(user.getVipLvl().longValue());
			requestData.setPreWithdrawAmt(new BigDecimal(commitRequest.getMoney()).multiply(new BigDecimal(10000)).setScale(2, RoundingMode.HALF_UP).longValue());
//			UserBankStruc userBankStruc = new UserBankStruc();
			
			requestData.setUserBankStruc(userBankStruc);
			requestData.setUserId(ut.getUserId());
			requestData.setApplyTime(new Date());
			
			WithdrawCommitResponse result = new WithdrawCommitResponse();
			Response<WithdrawApplyResponse> applyResponse = httpClient.invokeHttp(firefrogUrl+ commitUrl, requestData, new Pager(), ut.getUserId(), ut.getUserName(), new TypeReference<Response<WithdrawApplyResponse>>() {
			});

			response.getHead().setStatus(applyResponse.getHead().getStatus());
			if(applyResponse.getHead().getStatus()==0){
				result.setStatus("success");
				response.setResult(result);
			}
			
		} catch (Exception e) {
			log.error("withdraw commit error:", e);
			response.getHead().setStatus(901000L);
		}
		return response;
		
	}
}
