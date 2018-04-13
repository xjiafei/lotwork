package com.winterframework.firefrog.phone.web.controller;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.core.type.TypeReference;
import com.winterframework.firefrog.phone.web.cotroller.dto.Bank;
import com.winterframework.firefrog.phone.web.cotroller.dto.BankCardApplyBindRequest;
import com.winterframework.firefrog.phone.web.cotroller.dto.BankCardQueryRecordResponse;
import com.winterframework.firefrog.phone.web.cotroller.dto.BankCardQueryRequest;
import com.winterframework.firefrog.phone.web.cotroller.dto.BankCardQueryResponse;
import com.winterframework.firefrog.phone.web.cotroller.dto.BankCardUnbindRequest;
import com.winterframework.firefrog.phone.web.cotroller.dto.BindDate;
import com.winterframework.firefrog.phone.web.cotroller.dto.CardBindingCommitRequest;
import com.winterframework.firefrog.phone.web.cotroller.dto.CardBindingCommitResponse;
import com.winterframework.firefrog.phone.web.cotroller.dto.CardBindingConfirmRequest;
import com.winterframework.firefrog.phone.web.cotroller.dto.CardBindingConfirmResponse;
import com.winterframework.firefrog.phone.web.cotroller.dto.CardBindingDeleteRequest;
import com.winterframework.firefrog.phone.web.cotroller.dto.CardBindingDeleteResponse;
import com.winterframework.firefrog.phone.web.cotroller.dto.CardBindingInitRequest;
import com.winterframework.firefrog.phone.web.cotroller.dto.CardBindingInitResponse;
import com.winterframework.firefrog.phone.web.cotroller.dto.CardListDto;
import com.winterframework.firefrog.phone.web.cotroller.dto.CardListRequest;
import com.winterframework.firefrog.phone.web.cotroller.dto.CardListResponse;
import com.winterframework.firefrog.phone.web.cotroller.dto.CityListRequest;
import com.winterframework.firefrog.phone.web.cotroller.dto.CityListResponse;
import com.winterframework.firefrog.phone.web.cotroller.dto.FundBank;
import com.winterframework.firefrog.phone.web.cotroller.dto.Province;
import com.winterframework.firefrog.phone.web.cotroller.dto.UserBankStruc;
import com.winterframework.firefrog.phone.web.cotroller.dto.UserStrucResponse;
import com.winterframework.firefrog.phone.web.cotroller.dto.UserToken;
import com.winterframework.modules.spring.exetend.PropertyConfig;
import com.winterframework.modules.web.jsonresult.Pager;
import com.winterframework.modules.web.jsonresult.Request;
import com.winterframework.modules.web.jsonresult.Response;

@Controller("bankCardController")
@RequestMapping("/security")
public class BankCardController extends BaseController {
	
	private static final Logger log = LoggerFactory.getLogger(BankCardController.class);
	
	@PropertyConfig(value="url.user.userBankReport")
	private String userBankReportUrl;
	@PropertyConfig(value="url.fund.cardBindingInit")
	private String cardBindingInitUrl;
	@PropertyConfig(value="url.fund.cardBindingConfirm")
	private String cardBindingConfirmUrl;
	@PropertyConfig(value="url.fund.cardBindingCommit")
	private String cardBindingCommitUrl;
	@PropertyConfig(value="url.fund.cardBindingDelete")
	private String cardBindingDeleteUrl;
	@PropertyConfig(value="url.fund.cardList")
	private String cardListUrl;
	@PropertyConfig(value="url.front.teamUserBalance")
	private String teamUserBalanceUrl;
	
	@ResponseBody
	@RequestMapping("/cardBindingInit")
	public Response<CardBindingInitResponse> cardBindingInit(@RequestBody Request<CardBindingInitRequest> request) throws Exception{
		
		Response<CardBindingInitResponse> response = new Response<CardBindingInitResponse>(request);
		
		String token = request.getHead().getSessionId();
		try {
			
			String account = getUserNameByToken(token);
			if(null == account){
				response.getHead().setStatus(7L);
				return response;
			}
			UserToken ut = getUserToken(account);
			
			BindDate requestData = new BindDate();
			requestData.setIsBind(1);
			
			CardBindingInitResponse result = new CardBindingInitResponse();
			Response<BankCardQueryRecordResponse> gameResponse = httpClient.invokeHttp(firefrogUrl+ cardBindingInitUrl, requestData,new Pager(), ut.getUserId(), ut.getUserName(), new TypeReference<Response<BankCardQueryRecordResponse>>() {
			});
			
			List<Province> provinceList = new ArrayList<Province>();
			List<Bank> bankList = new ArrayList<Bank>();
			if(null != gameResponse.getBody() && null != gameResponse.getBody().getResult()){
				BankCardQueryRecordResponse cards = gameResponse.getBody().getResult();
				
				for(FundBank struc : cards.getBankStruc()){
					
					if(struc.getId() < 30){
						Bank bank = new Bank();
						bank.setBank(struc.getName());
						bank.setBankId(struc.getId().intValue());
	//					Province province = new Province(); 
	//					province.setName(struc.); 
	//					province.setId(id);//TODO 在那里取？
						bankList.add(bank);
					}
				}
				
				result.setAllowNum(5);//可绑卡张数  
				result.setAvailableNum(cards.getUserBankStruc().length);//已绑卡张数
				result.setLeftNum(result.getAllowNum()-result.getAvailableNum());//剩馀绑卡张数  
				
				//判断 锁定时间
				BankCardQueryRequest requestData2 = new BankCardQueryRequest();
				requestData2.setUserId(ut.getUserId());
				
				Response<BankCardQueryResponse> gameResponse2 = httpClient.invokeHttp(firefrogUrl+ cardListUrl, requestData2, new Pager(), ut.getUserId(), ut.getUserName(), new TypeReference<Response<BankCardQueryResponse>>() {
				});
				
				int islocded = 0;
				if(null != gameResponse2.getBody() && null != gameResponse2.getBody().getResult()){
					if(System.currentTimeMillis() > gameResponse2.getBody().getResult().getOverTime() && gameResponse2.getBody().getResult().getOverTime() >0){
						if(null != gameResponse2.getBody().getResult().getUserBankStruc() && gameResponse2.getBody().getResult().getUserBankStruc().size() > 0 ){
							islocded = 1; //锁定
						}
					}
				}
				result.setIsLocked(islocded);
			}
			result.setSetsecurity(Integer.parseInt(ut.getSecurity()));
			result.setProvinceList(provinceList);
			result.setBankList(bankList);
			
			response.setResult(result);
		} catch (Exception e) {
			log.error("cardBindingInit error:", e);
			response.getHead().setStatus(901000L);
		}
		return response;
	}
	
	@ResponseBody
	@RequestMapping("/cardList")
	public Response<CardListResponse> cardList(@RequestBody Request<CardListRequest> request) throws Exception{
		Response<CardListResponse> response = new Response<CardListResponse>(request);
		
		String token = request.getHead().getSessionId();
		try {
			
			String account = getUserNameByToken(token);
			if(null == account){
				response.getHead().setStatus(7L);
				return response;
			}
			UserToken ut = getUserToken(account);
			
			BankCardQueryRequest requestData = new BankCardQueryRequest();
			requestData.setUserId(ut.getUserId());
			
			CardListResponse result = new CardListResponse();
			Response<BankCardQueryResponse> gameResponse = httpClient.invokeHttp(firefrogUrl+ cardListUrl, requestData, new Pager(), ut.getUserId(), ut.getUserName(), new TypeReference<Response<BankCardQueryResponse>>() {
			});
			
			List<CardListDto> list = new ArrayList<CardListDto>();
			if(null != gameResponse.getBody() && null != gameResponse.getBody().getResult()){
				BankCardQueryResponse cards = gameResponse.getBody().getResult();
				
				for(UserBankStruc struc : cards.getUserBankStruc()){
					CardListDto dto = new CardListDto();
					dto.setAccount(struc.getBankNumber());
//					dto.setBankName(struc.getBranchName());
					dto.setId(struc.getBankId().intValue());
					dto.setBindId( struc.getId());
					dto.setMcBankId(struc.getMcBankId());
					dto.setIseffect(1); //能取到都是激活的。。
					list.add(dto);
				}	
			}
			
			result.setList(list);
			response.setResult(result);
		} catch (Exception e) {
			log.error("cardList error:", e);
			response.getHead().setStatus(901000L);
		}
		return response;
	}
	
	@ResponseBody
	@RequestMapping("/cardBindingConfirm")
	public Response<CardBindingConfirmResponse> cardBindingConfirm(@RequestBody Request<CardBindingConfirmRequest> request) throws Exception{
		
		Response<CardBindingConfirmResponse> response = new Response<CardBindingConfirmResponse>(request);
		String token = request.getHead().getSessionId();
		try {
			
			String account = getUserNameByToken(token);
			if(null == account){
				response.getHead().setStatus(7L);
				return response;
			}
			UserToken ut = getUserToken(account);
			
			CardBindingConfirmRequest confirm = request.getBody().getParam();
			
//			BankCardApplyBindRequest requestData = new BankCardApplyBindRequest();
//			requestData.setUserId(ut.getUserId());
//			requestData.setBankNumber(confirm.getAccount());
//			requestData.setBankId(confirm.getId().longValue());
//			requestData.setBankAccount(confirm.getAccount());
			
			BankCardQueryRequest requestData = new BankCardQueryRequest();
			requestData.setUserId(ut.getUserId());
			
			CardBindingConfirmResponse result = new CardBindingConfirmResponse();
			Response<BankCardQueryResponse> gameResponse = httpClient.invokeHttp(firefrogUrl+ cardListUrl, requestData, new Pager(), ut.getUserId(), ut.getUserName(), new TypeReference<Response<BankCardQueryResponse>>() {
			});
			
			boolean t = false;
			if(null != gameResponse.getBody() && null != gameResponse.getBody().getResult()){
				BankCardQueryResponse cards = gameResponse.getBody().getResult();
				
				for(UserBankStruc struc : cards.getUserBankStruc()){
					
					if (struc.getBankAccount().equals(confirm.getAccountName())
							&& struc.getBankNumber().equals(
									confirm.getAccount())
							&& struc.getId().longValue() == confirm.getId()
									.longValue()) {
						log.info("cardBindingConfirm 验证成功。");
						t = true;
					}
				}	
			}
			
			if(t){
				response.getHead().setStatus(0L);
				result.setStatus("success");
			}else{
				response.getHead().setStatus(109998L);	//绑卡验证失败
			}
			/*Response<Object> gameResponse = httpClient.invokeHttp(firefrogUrl+cardBindingConfirmUrl, requestData, ut.getUserId(), ut.getUserName(),new TypeReference<Response<Object>>(){});
			
			CardBindingConfirmResponse result = new CardBindingConfirmResponse();
			if(gameResponse.getHead().getStatus() >= 0){
				Long status = gameResponse.getHead().getStatus();
				
				if(2010L== status){//已绑定
					response.getHead().setStatus(109998L);	//绑卡验证失败
				}else{
					result.setStatus("success");
					//else if(2007L==status){//银行卡绑定功能被锁定
				}
				//}else if(2008L==status){//绑卡功能达到可绑定上限
			//	}
			}*/
			response.setResult(result);
		} catch (Exception e) {
			log.error("cardBindingConfirm error:", e);
			response.getHead().setStatus(901000L);
		}
		return response;
	}
	
	@ResponseBody
	@RequestMapping("/cardBindingCommit")
	public Response<CardBindingCommitResponse> cardBindingCommit(@RequestBody Request<CardBindingCommitRequest> request) throws Exception{
		
		Response<CardBindingCommitResponse> response = new Response<CardBindingCommitResponse>(request);
		
		String token = request.getHead().getSessionId();
		try {
			
			String account = getUserNameByToken(token);
			if(null == account){
				response.getHead().setStatus(7L);
				return response;
			}
			UserToken ut = getUserToken(account);
						
			//add 			
			//判断 锁定时间
			BankCardQueryRequest requestData2 = new BankCardQueryRequest();
			requestData2.setUserId(ut.getUserId());
//			requestData2.setUserId(112L);
			
			Response<BankCardQueryResponse> gameResponse2 = httpClient.invokeHttp(firefrogUrl+ cardListUrl, requestData2, new Pager(), ut.getUserId(), ut.getUserName(), new TypeReference<Response<BankCardQueryResponse>>() {
			});
//			Response<BankCardQueryResponse> gameResponse2 = httpClient.invokeHttp(firefrogUrl+ cardListUrl, requestData2, new Pager(), 112L, "sksk", new TypeReference<Response<BankCardQueryResponse>>() {
//			});
			int islocded = 0;
			if(null != gameResponse2.getBody() && null != gameResponse2.getBody().getResult()){
				if(System.currentTimeMillis() > gameResponse2.getBody().getResult().getOverTime() && gameResponse2.getBody().getResult().getOverTime() >0){
					if(null != gameResponse2.getBody().getResult().getUserBankStruc() && gameResponse2.getBody().getResult().getUserBankStruc().size() > 0 ){
						islocded = 1; //锁定
					}
				}
			}
			
			if(islocded>0){
				response.getHead().setStatus(109994); //109994	银行卡已经被锁定 
				return response;
			}
			//判断是否已经绑卡  109993	该银行卡已经绑定
			boolean isExist = false;
			CardBindingCommitRequest commit = request.getBody().getParam();

			if(null != gameResponse2.getBody() && null != gameResponse2.getBody().getResult()){
				BankCardQueryResponse cards = gameResponse2.getBody().getResult();
				
				for(UserBankStruc struc : cards.getUserBankStruc()){
					
					if (struc.getBankNumber().equals(commit.getAccount())) {
						log.info("cardBindingCommit 已存在卡号。");
						isExist = true;
					}
				}	
			}
			
			if(isExist){
				response.getHead().setStatus(109993); //该银行卡已经绑定
				return response;
			}
			
			
			Response<UserStrucResponse> firforgResponse = httpClient.invokeHttp(firefrogUrl+teamUserBalanceUrl, null,new Pager(),ut.getUserId(), null, new TypeReference<Response<UserStrucResponse>>() {
			});
//			Response<UserStrucResponse> firforgResponse = httpClient.invokeHttp(firefrogUrl+teamUserBalanceUrl, null,new Pager(),112L, null, new TypeReference<Response<UserStrucResponse>>() {
//			});
			
			if(!firforgResponse.getBody().getResult().getWithdrawPasswd().equals(commit.getSecpass())){
				response.getHead().setStatus(102001);
				return response;
			}
			

			
			BankCardApplyBindRequest requestData = new BankCardApplyBindRequest();
			requestData.setUserId(ut.getUserId());
//			requestData.setUserId(112L);
			requestData.setBankAccount(commit.getAccountName());
			requestData.setBankId(commit.getBankId().longValue());
			requestData.setBankNumber(commit.getAccount());
			requestData.setBranchName(commit.getBank());
			requestData.setCity(commit.getCity());
			requestData.setMcBankId(commit.getBankId().longValue());
			requestData.setProvince(commit.getProvince());
			requestData.setBindcardType(0L);
			
			CardBindingCommitResponse result = new CardBindingCommitResponse();
			Response<Object> gameResponse = httpClient.invokeHttp(firefrogUrl+cardBindingCommitUrl, requestData, ut.getUserId(), ut.getUserName(),new TypeReference<Response<Object>>(){});
//			Response<Object> gameResponse = httpClient.invokeHttp(firefrogUrl+cardBindingCommitUrl, requestData, 112L, "sksk",new TypeReference<Response<Object>>(){});
			response.getHead().setStatus(gameResponse.getHead().getStatus());
			if(gameResponse.getHead().getStatus() ==0L){
				result.setStatus("success");
			}
			response.setResult(result);
		}catch(Exception e){
			log.error("cardBindingCommit error",e);
			response.getHead().setStatus(901000L);
		}
		return response;
	}
	
	@ResponseBody
	@RequestMapping("/cardBindingDelete")
	public Response<CardBindingDeleteResponse> cardBindingDelete(@RequestBody Request<CardBindingDeleteRequest> request) throws Exception{
		Response<CardBindingDeleteResponse> response = new Response<CardBindingDeleteResponse>(request);
		
		String token = request.getHead().getSessionId();
		try {
			
			String account = getUserNameByToken(token);
			if(null == account){
				response.getHead().setStatus(7L);
				return response;
			}
			UserToken ut = getUserToken(account);
			
			//add 			
			//判断 锁定时间
			BankCardQueryRequest requestData2 = new BankCardQueryRequest();
			requestData2.setUserId(ut.getUserId());
			
			Response<BankCardQueryResponse> gameResponse2 = httpClient.invokeHttp(firefrogUrl+ cardListUrl, requestData2, new Pager(), ut.getUserId(), ut.getUserName(), new TypeReference<Response<BankCardQueryResponse>>() {
			});
			
			int islocded = 0;
			if(null != gameResponse2.getBody() && null != gameResponse2.getBody().getResult()){
				if(System.currentTimeMillis() > gameResponse2.getBody().getResult().getOverTime() && gameResponse2.getBody().getResult().getOverTime() >0){
					islocded = 1; //锁定
				}
			}
			
			if(islocded>0){
				response.getHead().setStatus(109994); //109994	银行卡已经被锁定 
				return response;
			}
			
			CardBindingDeleteRequest del = request.getBody().getParam();
			BankCardUnbindRequest requestData = new BankCardUnbindRequest();
			requestData.setBindcardType(0L);
			requestData.setBankId(del.getId()== null ? 0L : del.getId().longValue());
			requestData.setBindId(del.getBindId()==null ? 0L:del.getBindId());
			requestData.setMcBankId(del.getMcBankId()==null ? 0L:del.getMcBankId());
			requestData.setUserId(ut.getUserId());
			
			CardBindingDeleteResponse result = new CardBindingDeleteResponse();
			Response<Object> gameResponse = httpClient.invokeHttp(firefrogUrl+cardBindingDeleteUrl, requestData, ut.getUserId(), ut.getUserName(), new TypeReference<Response<Object>>(){});
			response.getHead().setStatus(gameResponse.getHead().getStatus());
			if(gameResponse.getHead().getStatus() ==0){
				result.setStatus("success");
			}
			
			response.setResult(result);
			
		} catch (Exception e) {
			log.error("cardBindingDelete error:" ,e);
			response.getHead().setStatus(901000L);
		}
		return response;
	}
	
	@ResponseBody
	@RequestMapping("/cityList")
	public Response<CityListResponse> cityList(@RequestBody Request<CityListRequest> request) throws Exception{
		
		Response<CityListResponse> response = new Response<CityListResponse>(request);
		try {
			
			//TODO 
		} catch (Exception e) {
			log.error("cityList error", e);
			response.getHead().setStatus(901000L);
		}
		return response;
	}
	

}
