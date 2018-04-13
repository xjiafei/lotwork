package com.winterframework.firefrog.phone.web.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.core.type.TypeReference;
import com.winterframework.firefrog.phone.web.cotroller.dto.AddSecpassRequest;
import com.winterframework.firefrog.phone.web.cotroller.dto.AddSecpassResponse;
import com.winterframework.firefrog.phone.web.cotroller.dto.CheckSecpassRequest;
import com.winterframework.firefrog.phone.web.cotroller.dto.CheckSecpassResponse;
import com.winterframework.firefrog.phone.web.cotroller.dto.ModifyLoginpassRequest;
import com.winterframework.firefrog.phone.web.cotroller.dto.ModifyLoginpassResponse;
import com.winterframework.firefrog.phone.web.cotroller.dto.ModifySecpassRequest;
import com.winterframework.firefrog.phone.web.cotroller.dto.ModifySecpassResponse;
import com.winterframework.firefrog.phone.web.cotroller.dto.SafeQuestEdit;
import com.winterframework.firefrog.phone.web.cotroller.dto.SafeQuestEditRequest;
import com.winterframework.firefrog.phone.web.cotroller.dto.SafeQuestEditResponse;
import com.winterframework.firefrog.phone.web.cotroller.dto.SafeQuestInit;
import com.winterframework.firefrog.phone.web.cotroller.dto.SafeQuestInitRequest;
import com.winterframework.firefrog.phone.web.cotroller.dto.SafeQuestInitResponse;
import com.winterframework.firefrog.phone.web.cotroller.dto.SafeQuestSet;
import com.winterframework.firefrog.phone.web.cotroller.dto.SafeQuestSetRequest;
import com.winterframework.firefrog.phone.web.cotroller.dto.SafeQuestSetResponse;
import com.winterframework.firefrog.phone.web.cotroller.dto.SafeQuestVerify;
import com.winterframework.firefrog.phone.web.cotroller.dto.SafeQuestVerifyRequest;
import com.winterframework.firefrog.phone.web.cotroller.dto.SafeQuestVerifyResponse;
import com.winterframework.firefrog.phone.web.cotroller.dto.UserSecurityPwdRequest;
import com.winterframework.firefrog.phone.web.cotroller.dto.UserSecurityQARequest;
import com.winterframework.firefrog.phone.web.cotroller.dto.UserSecurityWithdrawPwdRequest;
import com.winterframework.firefrog.phone.web.cotroller.dto.UserToken;
import com.winterframework.firefrog.session.interceptor.vo.QuStrucResponse;
import com.winterframework.firefrog.session.interceptor.vo.UserStrucResponse;
import com.winterframework.modules.spring.exetend.PropertyConfig;
import com.winterframework.modules.web.jsonresult.Pager;
import com.winterframework.modules.web.jsonresult.Request;
import com.winterframework.modules.web.jsonresult.Response;
@Controller("securityController")
@RequestMapping("/security")
public class SecurityController extends BaseController {
	
	private static final Logger log = LoggerFactory.getLogger(SecurityController.class);
	
	@PropertyConfig(value = "url.front.addSecpass")
	private String addSecpassUrl;
	@PropertyConfig(value="url.front.modifySecpass")
	private String modifySecpassUrl;
	@PropertyConfig(value="url.front.modifyLoginpass")
	private String modifyLoginpassUrl;
	@PropertyConfig(value="url.front.teamUserBalance")
	private String checkSecpassUrl;
	@PropertyConfig(value="url.front.teamBalance")
	private String teamBalanceUrl;
	@PropertyConfig(value="url.front.safeQuestionSet")
	private String safeQuestSetUrl;
	
	@ResponseBody
	@RequestMapping("/checkSecpass")
	public Response<CheckSecpassResponse> checkSecpass(@RequestBody Request<CheckSecpassRequest> request) throws Exception{
		
		Response<CheckSecpassResponse> response = new Response<CheckSecpassResponse>(request);
		
		String token = request.getHead().getSessionId();
		try {
			
			String account = getUserNameByToken(token);
			if(null == account){
				response.getHead().setStatus(7L);
				return response;
			}
			UserToken ut = getUserToken(account);
			
			Response<UserStrucResponse> firforgResponse = httpClient.invokeHttp(firefrogUrl+checkSecpassUrl, request,new Pager(), ut.getUserId(),account, new TypeReference<Response<UserStrucResponse>>() {
			});
			
			CheckSecpassResponse result = new CheckSecpassResponse();
			
			if(null != firforgResponse.getBody() && firforgResponse.getBody().getResult() != null){
				
				UserStrucResponse struc = firforgResponse.getBody().getResult();
				if(struc.getWithdrawPasswd().equals(request.getBody().getParam().getSecpass())){
					result.setStatus("success");
				}else{
//					result.setStatus("10100l");
					response.getHead().setStatus(101001L);
				}
			}else{
//				result.setStatus("101004");
				response.getHead().setStatus(101004L);
			}
			
			response.setResult(result);
			
		} catch (Exception e) {
			log.error("checkSecpass error:", e);
			response.getHead().setStatus(901000L);
		}
		
		return response;
	}
	
	@ResponseBody
	@RequestMapping("/addSecpass")
	public Response<AddSecpassResponse> addSecpass(@RequestBody Request<AddSecpassRequest> request) throws Exception{
		
		Response<AddSecpassResponse> response = new Response<AddSecpassResponse>(request);
		String token = request.getHead().getSessionId();
		try {
			
			String account = getUserNameByToken(token);
			if(null == account){
				response.getHead().setStatus(7L);
				return response;
			}
			UserToken ut = getUserToken(account);
			
			UserSecurityWithdrawPwdRequest requestData = new UserSecurityWithdrawPwdRequest();
			requestData.setWithdrawPasswd(request.getBody().getParam().getConfirmNewpass2());
			
			Response<UserStrucResponse> firforgResponse = httpClient.invokeHttp(firefrogUrl+checkSecpassUrl, null,new Pager(), ut.getUserId(),account, new TypeReference<Response<UserStrucResponse>>() {
			});
			
			if(null != firforgResponse.getBody() && firforgResponse.getBody().getResult() != null){
				if(requestData.getWithdrawPasswd().equals(firforgResponse.getBody().getResult().getWithdrawPasswd())){
					response.getHead().setStatus(109997L);//109997  无法再次设定资金安全密码
					return response;
				}
			}
				
			
			Response<UserStrucResponse>  gameResponse = httpClient.invokeHttp(firefrogUrl+addSecpassUrl, requestData,new Pager(), ut.getUserId(), ut.getUserName(), new TypeReference<	Response<UserStrucResponse>>() {
			});
			
			AddSecpassResponse result = new AddSecpassResponse();
			if(null != gameResponse.getBody() && gameResponse.getHead().getStatus()==0){
				result.setStatus("success");
			}
			
			response.setResult(result);
			
		} catch (Exception e) {
			log.error("addSecpass error:",e);
			response.getHead().setStatus(901000L);
		}
		return response;
	}
	
	@ResponseBody
	@RequestMapping("/modifySecpass")
	public Response<ModifySecpassResponse> modifySecpass(@RequestBody Request<ModifySecpassRequest> request) throws Exception{
		
		Response<ModifySecpassResponse> response = new Response<ModifySecpassResponse>(request);
		String token = request.getHead().getSessionId();
		try {
			
			String account = getUserNameByToken(token);
			if(null == account){
				response.getHead().setStatus(7L);
				return response;
			}
			UserToken ut = getUserToken(account);
			
			if(request.getBody().getParam().getConfirmNewpass2().length() < 32){
				response.getHead().setStatus(101007L); // 密码长度不符合要求
				return response;
			}
			
			ModifySecpassRequest md = request.getBody().getParam();
			
			Response<UserStrucResponse> firforgResponse = httpClient.invokeHttp(firefrogUrl+checkSecpassUrl, null,new Pager(), ut.getUserId(),account, new TypeReference<Response<UserStrucResponse>>() {
			});
			
			if(firforgResponse.getBody().getResult() !=null ){
				if(!md.getOldpass2().equals(firforgResponse.getBody().getResult().getWithdrawPasswd())){
					response.getHead().setStatus(101001L);
					return response;
				}
			}
			
			UserSecurityWithdrawPwdRequest requestData = new UserSecurityWithdrawPwdRequest();
			requestData.setWithdrawPasswd(md.getConfirmNewpass2());
			
			Response<UserStrucResponse>  gameResponse = httpClient.invokeHttp(firefrogUrl+modifySecpassUrl, requestData, new Pager(), ut.getUserId(), ut.getUserName(), new TypeReference<	Response<UserStrucResponse>>() {
			});
			
			ModifySecpassResponse result = new ModifySecpassResponse();
			if(null != gameResponse.getBody() && gameResponse.getHead().getStatus()==0){
				result.setStatus("success");
			}
			
			response.setResult(result);
			
		} catch (Exception e) {
			log.error("modifySecpass error:",e);
			response.getHead().setStatus(901000L);
		}
		return response;
	}
	
	@ResponseBody
	@RequestMapping("/modifyLoginpass")
	public Response<ModifyLoginpassResponse> modifyLoginpass(@RequestBody Request<ModifyLoginpassRequest> request) throws Exception{
		
		Response<ModifyLoginpassResponse> response = new Response<ModifyLoginpassResponse>(request);
		String token = request.getHead().getSessionId();
		try {
			
			String account = getUserNameByToken(token);
			if(null == account){
				response.getHead().setStatus(7L);
				return response;
			}
			UserToken ut = getUserToken(account);
			UserSecurityPwdRequest requestdata = new UserSecurityPwdRequest();
			
			ModifyLoginpassRequest md = request.getBody().getParam();
			if(md.getConfirmNewpass().length() < 32){
				response.getHead().setStatus(101007L); // 密码长度不符合要求
				return response;
			}
			
			
			Response<UserStrucResponse> firforgResponse = httpClient.invokeHttp(firefrogUrl+checkSecpassUrl, null,new Pager(), ut.getUserId(),account, new TypeReference<Response<UserStrucResponse>>() {
			});
			
			if(firforgResponse.getBody().getResult() !=null ){
				if(!md.getOldpass().equals(firforgResponse.getBody().getResult().getPasswd())){
					response.getHead().setStatus(101001L);
					return response;
				}
			}
			
			requestdata.setPasswdLvl(1);
			requestdata.setPasswd(request.getBody().getParam().getConfirmNewpass());
			
			Response<UserStrucResponse>  gameResponse = httpClient.invokeHttp(firefrogUrl+modifyLoginpassUrl, requestdata, new Pager(), ut.getUserId(), ut.getUserName(), new TypeReference<	Response<UserStrucResponse>>() {
			});
			
			ModifyLoginpassResponse result = new ModifyLoginpassResponse();
			if(null != gameResponse.getBody() && gameResponse.getHead().getStatus()==0){
				result.setStatus("success");
			}
			
			response.setResult(result);
			
		}catch(Exception e){
			log.error("modifyLoginpass error:", e);
			response.getHead().setStatus(901000L);
		}
		return response;
	}
	
	@RequestMapping("/safeQuestInit")
	@ResponseBody
	public Response<SafeQuestInitResponse> safeQuestInit(@RequestBody Request<SafeQuestInitRequest> request) throws Exception{
		
		Response<SafeQuestInitResponse> response = new Response<SafeQuestInitResponse>(request);
		
		String token = request.getHead().getSessionId();
		try {
			
			String account = getUserNameByToken(token);
			if(null == account){
				response.getHead().setStatus(7L);
				return response;
			}
			UserToken ut = getUserToken(account);
			SafeQuestInitResponse result = new SafeQuestInitResponse();
			List<SafeQuestInit> list = new ArrayList<SafeQuestInit>();
			
			for(Map.Entry<String, String> entry: QUESTION.entrySet()){ 
				SafeQuestInit init = new SafeQuestInit();
				init.setIsUsed(0);//0表示没设定过  1 表示有设定过
				init.setQid(Integer.parseInt(entry.getKey()));
				init.setQuestion(entry.getValue());
				list.add(init);
			}
			
			Response<UserStrucResponse> userResponse = httpClient.invokeHttp(firefrogUrl+checkSecpassUrl, request,new Pager(), ut.getUserId(),account, new TypeReference<Response<UserStrucResponse>>() {
			});
			
			if(null != userResponse.getBody() && null != userResponse.getBody().getResult()){
				
				List<QuStrucResponse> qlist = userResponse.getBody().getResult().getQuStruc();
				if(null != qlist && !qlist.isEmpty()){
					
					for(SafeQuestInit init : list){
						for(QuStrucResponse qs : qlist){
							if(init.getQid().intValue() == qs.getQu()){
								init.setIsUsed(1);
							}
						}
					}
				}
			}
			
			Collections.sort(list, new Comparator<SafeQuestInit>() {
				public int compare(SafeQuestInit arg0, SafeQuestInit arg1) {
					return arg0.getQid().compareTo(arg1.getQid());
				}
			});
			result.setList(list);
			response.setResult(result);
		}catch(Exception e){
			log.error("safeQuestInit error:", e);
			response.getHead().setStatus(901000L);
		}
		return response;
	}
	
	@RequestMapping("/safeQuestSet")
	@ResponseBody
	public Response<SafeQuestSetResponse> safeQuestSet(@RequestBody Request<SafeQuestSetRequest> request) throws Exception{
		
		Response<SafeQuestSetResponse> response = new Response<SafeQuestSetResponse>(request);
		

		String token = request.getHead().getSessionId();
		try {
			
			String account = getUserNameByToken(token);
			if(null == account){
				response.getHead().setStatus(7L);
				return response;
			}
			UserToken ut = getUserToken(account);
			
			UserSecurityQARequest requestData = new UserSecurityQARequest();
			
			SafeQuestSetRequest sr = request.getBody().getParam();
			List<SafeQuestSet> quests = sr.getQuests();
			int size = quests==null ? 1 : quests.size();
			com.winterframework.firefrog.phone.web.cotroller.dto.QuStrucResponse[] quStruc = new com.winterframework.firefrog.phone.web.cotroller.dto.QuStrucResponse[size];
			
			for(int i=0; i<size;i++){
				com.winterframework.firefrog.phone.web.cotroller.dto.QuStrucResponse q = new com.winterframework.firefrog.phone.web.cotroller.dto.QuStrucResponse();
				q.setAns(quests.get(i).getQpwd());
				q.setQu(quests.get(i).getQid());
				quStruc[i] = q;
			}
			
			requestData.setQuStruc(quStruc);
			
			SafeQuestSetResponse result = new SafeQuestSetResponse();
			Response<UserStrucResponse> firefrogResponse = httpClient.invokeHttp(firefrogUrl+ safeQuestSetUrl, requestData, new Pager(), ut.getUserId(), ut.getUserName(), new TypeReference<Response<UserStrucResponse>>() {
			});
			if(firefrogResponse.getHead().getStatus()==0){
				result.setStatus("success");
			}
			response.setResult(result);
			response.getHead().setStatus(firefrogResponse.getHead().getStatus());
			
		} catch (Exception e) {
			log.error("safeQuestSet error:", e);
			response.getHead().setStatus(901000L);
		}
		return response;
	}
	
	
	@RequestMapping("/safeQuestVerify")
	@ResponseBody
	public Response<SafeQuestVerifyResponse> safeQuestVerify(@RequestBody Request<SafeQuestVerifyRequest> request) throws Exception{
	
		Response<SafeQuestVerifyResponse> response = new Response<SafeQuestVerifyResponse>(request);
		
		String token = request.getHead().getSessionId();
		try {
			
			String account = getUserNameByToken(token);
			if(null == account){
				response.getHead().setStatus(7L);
				return response;
			}
			UserToken ut = getUserToken(account);
			
			Response<UserStrucResponse> userResponse = httpClient.invokeHttp(firefrogUrl+checkSecpassUrl, request,new Pager(), ut.getUserId(),account, new TypeReference<Response<UserStrucResponse>>() {
			});
			response.getHead().setStatus(userResponse.getHead().getStatus());
			
			SafeQuestVerifyResponse result = new SafeQuestVerifyResponse();
			SafeQuestVerifyRequest safe = request.getBody().getParam(); 
			if(null != userResponse.getBody() && null != userResponse.getBody().getResult()){
				
				UserStrucResponse user = userResponse.getBody().getResult();
				
				int count = 0;
				for(SafeQuestVerify sq : safe.getQuests()){
					
					for(QuStrucResponse qu : user.getQuStruc()){
						
						if(qu.getQu()==sq.getQid().intValue()){
							if(sq.getQpwd().equals(qu.getAns())){
								count++;
							}
						}
						
					}
					
				}
				
				if(count == 0){
					response.getHead().setStatus(109996L);
					return response;
				}
				
				log.info("count = "+ count + ", size ="+safe.getQuests().size());
				if(count==safe.getQuests().size() && count >0){
					result.setStatus("success");
				}else{
					response.getHead().setStatus(1065L);//1065	答案与问题不匹配
				}	
				
			}
			
			response.setResult(result);
			
		} catch (Exception e) {
			log.error("safeQuestVerify error:", e);
			response.getHead().setStatus(901000L);
		}
		return response;
	}
	
	@RequestMapping("/safeQuestEdit")
	@ResponseBody
	public Response<SafeQuestEditResponse> safeQuestEdit(@RequestBody Request<SafeQuestEditRequest> request) throws Exception{
		Response<SafeQuestEditResponse> response = new Response<SafeQuestEditResponse>(request);
		
		String token = request.getHead().getSessionId();
		try {
			
			String account = getUserNameByToken(token);
			if(null == account){
				response.getHead().setStatus(7L);
				return response;
			}
			UserToken ut = getUserToken(account);
			
			UserSecurityQARequest requestData = new UserSecurityQARequest();
			
			SafeQuestEditRequest sr = request.getBody().getParam();
			List<SafeQuestEdit> quests = sr.getQuests();
			int size = quests==null ? 1 : quests.size();
			com.winterframework.firefrog.phone.web.cotroller.dto.QuStrucResponse[] quStruc = new com.winterframework.firefrog.phone.web.cotroller.dto.QuStrucResponse[size];
			
			for(int i=0; i<size;i++){
				com.winterframework.firefrog.phone.web.cotroller.dto.QuStrucResponse q = new com.winterframework.firefrog.phone.web.cotroller.dto.QuStrucResponse();
				q.setAns(quests.get(i).getQpwd());
				q.setQu(quests.get(i).getQid());
				quStruc[i] = q;
			}
			
			requestData.setQuStruc(quStruc);
			
			SafeQuestEditResponse result = new SafeQuestEditResponse();
			Response<UserStrucResponse> firefrogResponse = httpClient.invokeHttp(firefrogUrl+ safeQuestSetUrl, requestData, new Pager(), ut.getUserId(), ut.getUserName(), new TypeReference<Response<UserStrucResponse>>() {
			});
			if( firefrogResponse.getHead().getStatus()==0){
				result.setStatus("success");
			}
			response.setResult(result);
			response.getHead().setStatus(firefrogResponse.getHead().getStatus());
			
		} catch (Exception e) {
			log.error("safeQuestEdit error:", e);
			response.getHead().setStatus(901000L);
		}
		return response;
	}
}

