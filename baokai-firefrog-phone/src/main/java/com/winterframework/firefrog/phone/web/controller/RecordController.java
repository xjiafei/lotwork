package com.winterframework.firefrog.phone.web.controller;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.core.type.TypeReference;
import com.winterframework.firefrog.common.httpjsonclient.IHttpJsonClient;
import com.winterframework.firefrog.game.web.dto.DTOConvertor4Web;
import com.winterframework.firefrog.game.web.dto.GameOrderDetailQueryRequest;
import com.winterframework.firefrog.game.web.dto.GameOrderDetailQueryResponse;
import com.winterframework.firefrog.game.web.dto.GameOrderDetailQueryResponseDTO;
import com.winterframework.firefrog.phone.web.cotroller.dto.AlipayGetRequest;
import com.winterframework.firefrog.phone.web.cotroller.dto.AlipayGetResponse;
import com.winterframework.firefrog.phone.web.cotroller.dto.BankCardQueryRequest;
import com.winterframework.firefrog.phone.web.cotroller.dto.BankCardQueryResponse;
import com.winterframework.firefrog.phone.web.cotroller.dto.BankCardUnbindRequest;
import com.winterframework.firefrog.phone.web.cotroller.dto.CardBindingDeleteResponse;
import com.winterframework.firefrog.phone.web.cotroller.dto.ChargeQueryRequest;
import com.winterframework.firefrog.phone.web.cotroller.dto.ChargeStruc;
import com.winterframework.firefrog.phone.web.cotroller.dto.ConfigValueRequestDTO;
import com.winterframework.firefrog.phone.web.cotroller.dto.ConfigValueResponse;
import com.winterframework.firefrog.phone.web.cotroller.dto.UserBankStruc;
import com.winterframework.firefrog.phone.web.cotroller.dto.UserToken;
import com.winterframework.modules.spring.exetend.PropertyConfig;
import com.winterframework.modules.web.jsonresult.Pager;
import com.winterframework.modules.web.jsonresult.Request;
import com.winterframework.modules.web.jsonresult.Response;

@Controller("RecordController")
@RequestMapping("/iapi/record")
public class RecordController extends BaseController{
	
	private static final Logger log = LoggerFactory.getLogger(RecordController.class);
	
	private static final Long ALIPAY_BANK_ID = 30L;
	@Resource(name = "httpJsonClientImpl")
	private IHttpJsonClient httpClient;
	
	@PropertyConfig(value = "url.game.queryOrderDetail")
	private String queryOrderDetail;
	
	@PropertyConfig(value = "url.front.chargeRecordQuery")
	private String chargeRecordQuery;
	
	@PropertyConfig(value= "url.fund.cardList")
	private String queryBoundbankCard;
	
	@PropertyConfig(value="url.front.getConfig")
	private String configQueryUrl;
	
	@PropertyConfig(value="url.fund.cardBindingDelete")
	private String cardBindingDeleteUrl;
	
	@RequestMapping(value = "/getOrderDetail")
	@ResponseBody
	public Response<GameOrderDetailQueryResponseDTO> getOrderDetail(@RequestBody Request<GameOrderDetailQueryRequest> request) throws Exception{
		
		Response<GameOrderDetailQueryResponseDTO> response = new Response<GameOrderDetailQueryResponseDTO>(request);
		log.info("getOrderDetail~~~~~~~!!!!");
		log.info("order_id : " + request.getBody().getParam().getOrderId());
		log.info("order_code : " + request.getBody().getParam().getOrderCode());
		String token = request.getHead().getSessionId();
		try {
			String account = getUserNameByToken(token);
			log.info("account : " + account);
			if(null == account){
				response.getHead().setStatus(109990L);
				return response;
			}
			UserToken ut = getUserToken(account);		
//			Response<GameOrderDetailQueryResponse> detailRes =HttpJsonClient.postJsonObject(gameUrl + queryOrderDetail, request);
			
			Response<GameOrderDetailQueryResponse> detailRes = httpClient.invokeHttp(gameUrl + queryOrderDetail, request.getBody().getParam(),GameOrderDetailQueryResponse.class);
			if (detailRes.getBody() != null && detailRes.getBody().getResult() != null) {
				GameOrderDetailQueryResponseDTO result = DTOConvertor4Web.gameOrderDetailQueryResponse2GameOrderDetailQueryResponseDTO(detailRes.getBody().getResult());
				response.getBody().setResult(result);
			}
		} catch (Exception e) {
			log.error("getOrderDetail error:", e);
			response.getHead().setStatus(901000L);
		}
		
		return response;
	}
	
	@RequestMapping(value = "/alipayGet")
	@ResponseBody
	public Response<AlipayGetResponse> alipayGet(@RequestBody Request<AlipayGetRequest> request) throws Exception{
		
		Response<AlipayGetResponse> response = new Response<AlipayGetResponse>(request);
		log.info("alipayGet~~~~~~~!!!!");
		String token = request.getHead().getSessionId();
		try {
			String account = getUserNameByToken(token);
			log.info("account : " + account);
			if(null == account){
				response.getHead().setStatus(109990L);
				return response;
			}
			UserToken ut = getUserToken(account);
			//查詢成功儲值的帳號取第一筆 (chargeRecordQuery)
			ChargeQueryRequest charegeQueryReq = new ChargeQueryRequest();
			charegeQueryReq.setPayBankId(ALIPAY_BANK_ID);
			charegeQueryReq.setUserId(ut.getUserId());
			charegeQueryReq.setDepositeMode(new Long[] {6l});
			Response<List<ChargeStruc>> charegeQueryRes = httpClient.invokeHttp(firefrogUrl + chargeRecordQuery,charegeQueryReq, new Pager(),ut.getUserId(),ut.getUserName(),new TypeReference<Response<List<ChargeStruc>>>(){});
			AlipayGetResponse result = new AlipayGetResponse();
			
			ConfigValueRequestDTO cfgReq = new ConfigValueRequestDTO();
			cfgReq.setModule("fund");
			cfgReq.setFunction("aliPayChargeCoute");
			
			Response<ConfigValueResponse> cfgRsp = httpClient.invokeHttp(firefrogUrl+ configQueryUrl, cfgReq,new Pager(), ut.getUserId(), ut.getUserName(), new TypeReference<Response<ConfigValueResponse>>() {
			});
			int cfgVal = Integer.valueOf(cfgRsp.getBody().getResult().getVal()).intValue();
			//取的綁定的資料，最多五筆(queryBoundbankCard)
			BankCardQueryRequest bankCardQueryReq = new BankCardQueryRequest();
			bankCardQueryReq.setUserId(ut.getUserId());
			bankCardQueryReq.setBindCardType(1L);
			Response<BankCardQueryResponse> bankCardQueryRes = httpClient.invokeHttp(firefrogUrl+ queryBoundbankCard, bankCardQueryReq, new Pager(), ut.getUserId(), ut.getUserName(), new TypeReference<Response<BankCardQueryResponse>>(){
			});
			List<UserBankStruc> userbankstrucs = new ArrayList<UserBankStruc>();
			int count = 0;
			for(UserBankStruc struc:bankCardQueryRes.getBody().getResult().getUserBankStruc()){
				if(count == cfgVal){
					break;
				}
				userbankstrucs.add(struc);
				count++;
			}
			result.setLastChargeAccount(charegeQueryRes.getBody().getResult().get(0).getCardAccount());
			result.setLastChargeCard(charegeQueryRes.getBody().getResult().get(0).getCardNumber());
			result.setUserBankStruc(userbankstrucs);
			response.getBody().setResult(result);
		} catch (Exception e) {
			log.error("getOrderDetail error:", e);
			response.getHead().setStatus(901000L);
		}
		
		return response;
	}
	
	@RequestMapping(value = "/alipayDel")
	@ResponseBody
	public Response<CardBindingDeleteResponse> alipayDel(@RequestBody Request<BankCardUnbindRequest> request) throws Exception{
		
		Response<CardBindingDeleteResponse> response = new Response<CardBindingDeleteResponse>(request);
		String token = request.getHead().getSessionId();
		try {
			String account = getUserNameByToken(token);
			log.info("account : " + account);
			if(null == account){
				response.getHead().setStatus(109990L);
				return response;
			}
			UserToken ut = getUserToken(account);	
			BankCardUnbindRequest req = request.getBody().getParam();
			req.setBindcardType(1L);
			req.setUserId(ut.getUserId());
			req.setBankId(ALIPAY_BANK_ID);
			req.setMcBankId(ALIPAY_BANK_ID);
			
			
			
			CardBindingDeleteResponse result = new CardBindingDeleteResponse();
			Response<Object> gameResponse = httpClient.invokeHttp(firefrogUrl+cardBindingDeleteUrl, req, ut.getUserId(), ut.getUserName(), new TypeReference<Response<Object>>(){});
			response.getHead().setStatus(gameResponse.getHead().getStatus());
			if(gameResponse.getHead().getStatus() ==0){
				result.setStatus("success");
			}
			response.setResult(result);
		} catch (Exception e) {
			log.error("getOrderDetail error:", e);
			response.getHead().setStatus(901000L);
		}
		
		return response;
	}
}
