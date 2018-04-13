package com.winterframework.firefrog.phone.web.controller;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.core.type.TypeReference;
import com.winterframework.firefrog.game.web.dto.GameUserAwardGroupQueryRequest;
import com.winterframework.firefrog.game.web.dto.GameUserAwardGroupQueryResponse;
import com.winterframework.firefrog.game.web.dto.OpenAccountDetailedConfigRequest;
import com.winterframework.firefrog.game.web.dto.UserAwardListStruc;
import com.winterframework.firefrog.phone.web.cotroller.dto.AddCustomizedUserRequest;
import com.winterframework.firefrog.phone.web.cotroller.dto.AddCustomizedUserResponse;
import com.winterframework.firefrog.phone.web.cotroller.dto.ProxyDto;
import com.winterframework.firefrog.phone.web.cotroller.dto.ProxyListRequest;
import com.winterframework.firefrog.phone.web.cotroller.dto.ProxyListResponse;
import com.winterframework.firefrog.phone.web.cotroller.dto.ProxyNumberRequest;
import com.winterframework.firefrog.phone.web.cotroller.dto.ProxyNumberResponse;
import com.winterframework.firefrog.phone.web.cotroller.dto.QueryAgentSubUserRequestDTO;
import com.winterframework.firefrog.phone.web.cotroller.dto.SearchUserRequest;
import com.winterframework.firefrog.phone.web.cotroller.dto.SearchUserResponse;
import com.winterframework.firefrog.phone.web.cotroller.dto.TeamBalanceRequest;
import com.winterframework.firefrog.phone.web.cotroller.dto.TeamBalanceResponse;
import com.winterframework.firefrog.phone.web.cotroller.dto.TeamUserBalanceRequest;
import com.winterframework.firefrog.phone.web.cotroller.dto.TeamUserBalanceResponse;
import com.winterframework.firefrog.phone.web.cotroller.dto.UserDetailResponse;
import com.winterframework.firefrog.phone.web.cotroller.dto.UserSecurityUsernameRequest;
import com.winterframework.firefrog.phone.web.cotroller.dto.UserStruc;
import com.winterframework.firefrog.phone.web.cotroller.dto.UserStrucResponse;
import com.winterframework.firefrog.phone.web.cotroller.dto.UserToken;
import com.winterframework.firefrog.user.dao.vo.UserCustomer;
import com.winterframework.modules.spring.exetend.PropertyConfig;
import com.winterframework.modules.web.jsonresult.Pager;
import com.winterframework.modules.web.jsonresult.Request;
import com.winterframework.modules.web.jsonresult.Response;

@Controller("userController")
@RequestMapping("/user")
public class UserController extends BaseController{

	private static final Logger log = LoggerFactory.getLogger(UserController.class);
	

	@PropertyConfig(value="url.front.addUser")
	private String addUserUrl;
	@PropertyConfig(value="url.front.teamUserBalance")
	private String teamUserBalanceUrl;
	@PropertyConfig(value="url.front.teamBalance")
	private String teamBalanceUrl;
	@PropertyConfig(value="url.front.proxyList")
	private String proxyAgent;
	@PropertyConfig(value="url.front.subUser")
	private String subUserUrl;
	@PropertyConfig(value="url.front.searchUser")
	private String searchUserUrl;
	@PropertyConfig(value="url.front.queryUserAward")
	private String queryUserAwardUrl;
	@PropertyConfig(value="url.front.openAccount")
	private String openAccountUrl;
	
	@ResponseBody
	@RequestMapping("/addCustomizedUser")
	public Response<AddCustomizedUserResponse> addUser(@RequestBody Request<AddCustomizedUserRequest> request) throws Exception{
		
		Response<AddCustomizedUserResponse> response = new Response<AddCustomizedUserResponse>(request);
		
		String token = request.getHead().getSessionId();
		try {
			
			String account = getUserNameByToken(token);
			if(null == account){
				response.getHead().setStatus(7L);
				return response;
			}
			UserToken ut = getUserToken(account);
			
			Response<UserStrucResponse> firforgResponse = httpClient.invokeHttp(firefrogUrl+teamUserBalanceUrl, null,new Pager(),ut.getUserId(), null, new TypeReference<Response<UserStrucResponse>>() {
			});
			if(null != firforgResponse.getBody()){
				
				if(firforgResponse.getBody().getResult().getUserLvl() < 0){
					response.getHead().setStatus(109999);//无权限。
					return response;
				}
			}
			
			AddCustomizedUserRequest user = request.getBody().getParam();
			UserCustomer gameRequest = new UserCustomer();
			
			gameRequest.setAccount(user.getUsername());
			gameRequest.setPasswd(user.getUserpass());
			gameRequest.setUserLvl(user.getUsertype());
			gameRequest.setLastLoginIp(Long.parseLong(user.getLoginIp())); 
			gameRequest.setParentId(ut.getUserId());
			gameRequest.setUrlId(ut.getUserId());
			gameRequest.setPasswdLvl(1);
			gameRequest.setRegisterDate(new Date());
			gameRequest.setRegisterIp(Long.parseLong(user.getLoginIp()));
			
			Response<UserStrucResponse> gameResponse = httpClient.invokeHttp(firefrogUrl + addUserUrl, gameRequest, new TypeReference<Response<UserStrucResponse>>(){});
			
			AddCustomizedUserResponse result = new AddCustomizedUserResponse();
			if(null != gameResponse.getHead() && gameResponse.getHead().getStatus() == 0){
				
				result.setStatus("success");
				UserSecurityUsernameRequest requestData = new UserSecurityUsernameRequest();
				requestData.setAccount(user.getUsername());
				
				Response<UserStrucResponse> gameResponse2 = httpClient.invokeHttp(firefrogUrl+searchUserUrl,requestData, new Pager(), ut.getUserId(), ut.getUserName(), new TypeReference<Response<UserStrucResponse>>() {
				});
				
				Long userId = gameResponse2.getBody().getResult()==null ? 0L: gameResponse2.getBody().getResult().getId();
				result.setUid(userId);
				
				//用户分配奖金组。
				GameUserAwardGroupQueryRequest userAwardRequest = new GameUserAwardGroupQueryRequest();
				userAwardRequest.setUserid(ut.getUserId());
				userAwardRequest.setType(0);//总代
				userAwardRequest.setAwardType(0);
				
				Response<GameUserAwardGroupQueryResponse> userAwardResponse = httpClient.invokeHttp(gameUrl+ queryUserAwardUrl, userAwardRequest, new TypeReference<Response<GameUserAwardGroupQueryResponse>>() {
				});
				
				Map<Long, Long> map = new HashMap<Long,Long>();
				if(null != userAwardResponse.getBody() && null !=userAwardResponse.getBody().getResult()){
					
					List<UserAwardListStruc> userAwardList = userAwardResponse.getBody().getResult().getUserAwardListStruc();
					
					OpenAccountDetailedConfigRequest openAccountRequest = new OpenAccountDetailedConfigRequest();
					List<UserAwardListStruc> list = new ArrayList<UserAwardListStruc>();
					for(UserAwardListStruc award : userAwardList){
						if(map.get(award.getLotteryId()) ==null ){
							map.put(award.getLotteryId(), award.getLotteryId());
						    award.setBetType(1);
							award.setDirectLimitRet(0l);
							award.setDirectRet(0l);
							award.setThreeLimitRet(0l);
							award.setThreeoneRet(0l);
							list.add(award);
						}
					}
					openAccountRequest.setUserAwardListStruc(list);
					openAccountRequest.setUserid(userId);
					openAccountRequest.setUserLvl(-1L);
					@SuppressWarnings("unused")
					Response<Object> obj = httpClient.invokeHttp(gameUrl+ openAccountUrl, openAccountRequest,new Pager(),ut.getUserId(),ut.getUserName(), new TypeReference<Response<Object>>(){});
					
				}
				
			}
			response.getHead().setStatus(gameResponse.getHead().getStatus());
			response.setResult(result);
			
		} catch (Exception e) {
			log.error("addUser error:", e);
			response.getHead().setStatus(901000L);
		}
		return response;
	}
	
	@ResponseBody
	@RequestMapping("/teamBalance")
	public Response<TeamBalanceResponse> teamBalance(@RequestBody Request<TeamBalanceRequest> request) throws Exception{
		
		Response<TeamBalanceResponse> response = new Response<TeamBalanceResponse>(request);
		String token = request.getHead().getSessionId();
		try {
			
			String account = getUserNameByToken(token);
			if(null == account){
				response.getHead().setStatus(7L);
				return response;
			}
			UserToken ut = getUserToken(account);
			UserStruc userRequest = new UserStruc();
			userRequest.setAccount(ut.getUserName());
			userRequest.setUserId(ut.getUserId());
			
			Response<UserDetailResponse> gameResponse = httpClient.invokeHttp(firefrogUrl+teamBalanceUrl, userRequest,new Pager(), ut.getUserId(),ut.getUserName(), new TypeReference<Response<UserDetailResponse>>() {
			});
			
			boolean bl = true;
			TeamBalanceResponse result = new TeamBalanceResponse();
			if(null != gameResponse.getBody() && null != gameResponse.getBody().getResult()){
				
				UserDetailResponse struc = gameResponse.getBody().getResult();
				
				String[] chains = struc.getUserStruc().getUserChain().split("/");
				for(String str : chains){
					if(str.equals(ut.getUserName())){
						bl = false;
					}
				}
				
				if(bl){
					response.getHead().setStatus(109999);//无权限。
					return response;
				}

				result.setBalance(new BigDecimal(struc.getUserStruc().getTeamBal()==null ? 0 : struc.getUserStruc().getTeamBal() ).divide(new BigDecimal(10000),4,RoundingMode.HALF_UP).floatValue());
				result.setUsername(struc.getUserStruc().getAccount());
			}else{
				response.getHead().setStatus(101004L); //用户不存在
			}
			
			response.setResult(result);
			
		} catch (Exception e) {
			log.error("userBalance error:", e);
			response.getHead().setStatus(901000L);
		}
		return response;
	}
	
	@ResponseBody
	@RequestMapping("/teamUserBalance")
	public Response<TeamUserBalanceResponse> teamUserBalance(@RequestBody Request<TeamUserBalanceRequest> request) throws Exception{
		
		Response<TeamUserBalanceResponse> response = new Response<TeamUserBalanceResponse>(request);
		String token = request.getHead().getSessionId();
		try {
			
			String account = getUserNameByToken(token);
			if(null == account){
				response.getHead().setStatus(7L);
				return response;
			}
			UserToken ut = getUserToken(account);
			TeamUserBalanceResponse result = new TeamUserBalanceResponse();
			
			Response<UserStrucResponse> firforgResponse = httpClient.invokeHttp(firefrogUrl+teamUserBalanceUrl, request,new Pager(),Long.parseLong(request.getBody().getParam().getUid()), null, new TypeReference<Response<UserStrucResponse>>() {
			});
			
			boolean bl = true;
			if(null != firforgResponse.getBody() && firforgResponse.getBody().getResult() != null){
				
				UserStrucResponse struc = firforgResponse.getBody().getResult();
				
				String[] chains = struc.getUserChain().split("/");
				for(String str : chains){
					if(str.equals(ut.getUserName())){
						bl = false;
					}
				}
				
				if(bl){
					response.getHead().setStatus(109999);//无权限。
					return response;
				}
				
				result.setBalance(new BigDecimal(struc.getTeamBal()==null ? 0 : struc.getTeamBal()).divide(new BigDecimal(10000),4,RoundingMode.HALF_UP).floatValue());
				result.setUsername(struc.getAccount());
			}else{
				response.getHead().setStatus(101004L);
			}
			
			response.setResult(result);
			
		} catch (Exception e) {
			log.error("teamUserBalance error:", e);
			response.getHead().setStatus(901000L);
		}
		return response;
	}
	
	@ResponseBody
	@RequestMapping("/proxyList")
	public Response<ProxyListResponse> proxyList(@RequestBody Request<ProxyListRequest> request) throws Exception{
		
		Response<ProxyListResponse> response = new Response<ProxyListResponse>(request);
		String token = request.getHead().getSessionId();
		try {
			
			String account = getUserNameByToken(token);
			if(null == account){
				response.getHead().setStatus(7L);
				return response;
			}
			UserToken ut = getUserToken(account);
			
			ProxyListRequest plr = request.getBody().getParam();
			
			QueryAgentSubUserRequestDTO gameRequest = new QueryAgentSubUserRequestDTO();
			Long userId = ut.getUserId();
			gameRequest.setUserId(ut.getUserId());
			if(null != plr.getUid()){
				userId = Long.parseLong(plr.getUid());
				gameRequest.setUserId(userId);
			}
			
			int num = 10;
			Pager page = new Pager();
			page.setStartNo(num* plr.getP()+1);
			page.setEndNo(num*plr.getP()+num);
			
			Response<UserStrucResponse> gameResponse = new Response<UserStrucResponse>();
			ProxyListResponse result = new ProxyListResponse();
			List<ProxyDto> dtos = new ArrayList<ProxyDto>();
			if(plr.getType()==1){//代理
				
				gameResponse = httpClient.invokeHttp(firefrogUrl+proxyAgent, gameRequest, page, userId, ut.getUserName(), new TypeReference<Response<UserStrucResponse>>() {
				});
			}else{
				gameResponse = httpClient.invokeHttp(firefrogUrl+subUserUrl, gameRequest, page, userId, ut.getUserName(), new TypeReference<Response<UserStrucResponse>>() {
				});
			}
			
			
			if(null != gameResponse.getBody() && gameResponse.getBody().getResult() != null){
				
				UserStrucResponse users = gameResponse.getBody().getResult();
				List<UserStrucResponse> list  = users.getSubUsers();
				for(UserStrucResponse struc : list){
					
					ProxyDto dto = new ProxyDto();
					dto.setBalance(new BigDecimal(struc.getAvailBal()==null ? 0 : struc.getAvailBal()).divide(new BigDecimal(10000),2,RoundingMode.HALF_UP).doubleValue());
					dto.setName(struc.getAccount());
					dto.setUid(struc.getId()+"");
					dtos.add(dto);
				}
				if(plr.getType()==1){
				result.setCount(users.getTeamACount() == null ? 0 : users.getTeamACount().intValue());
				}else{
					result.setCount(users.getTeamUCount() == null ? 0 :users.getTeamUCount().intValue());
				}
				result.setContent(dtos);
			}
		
			response.setResultPage(gameResponse.getBody().getPager());
			response.setResult(result);
			
		}catch(Exception e){
			log.error("proxyList error:", e);
			response.getHead().setStatus(901000L);
		}
		return response;
	}
	
	@ResponseBody
	@RequestMapping("/proxyNumber")
	public Response<ProxyNumberResponse> proxyNum(@RequestBody Request<ProxyNumberRequest> request) throws Exception{
		Response<ProxyNumberResponse> response = new Response<ProxyNumberResponse>(request);
		String token = request.getHead().getSessionId();
		try {
			
			String account = getUserNameByToken(token);
			if(null == account){
				response.getHead().setStatus(7L);
				return response;
			}
			UserToken ut = getUserToken(account);
			
			ProxyNumberResponse result = new ProxyNumberResponse();
			Long userId = ut.getUserId();
			if(null != request.getBody().getParam().getUid()){
				userId = Long.parseLong(request.getBody().getParam().getUid());
			}
			Response<UserStrucResponse> firforgResponse = httpClient.invokeHttp(firefrogUrl+teamUserBalanceUrl, request,new Pager(), userId, ut.getUserName(), new TypeReference<Response<UserStrucResponse>>() {
			});
			
			if(null != firforgResponse.getBody() && firforgResponse.getBody().getResult() != null){
				UserStrucResponse struc = firforgResponse.getBody().getResult();
				result.setMembernum(struc.getTeamUCount()==null ? 0 : struc.getTeamUCount().intValue());
				result.setProxynum(struc.getTeamACount()==null? 0: struc.getTeamACount().intValue());
			}else{
				response.getHead().setStatus(101004L);
			}
			
			response.setResult(result);
		} catch (Exception e) {
			log.error("proxyNum error:", e);
			response.getHead().setStatus(901000L);
		}
		return response;
	}
	
	@ResponseBody
	@RequestMapping("/searchUser")
	public Response<SearchUserResponse> searchUser(@RequestBody Request<SearchUserRequest> request) throws Exception{
		Response<SearchUserResponse> response = new Response<SearchUserResponse>(request);
		
		String token = request.getHead().getSessionId();
		try {
			
			String account = getUserNameByToken(token);
			if(null == account){
				response.getHead().setStatus(7L);
				return response;
			}
			UserToken ut = getUserToken(account);
			
			UserSecurityUsernameRequest requestData = new UserSecurityUsernameRequest();
			requestData.setAccount(request.getBody().getParam().getUsername());
			
			Response<UserStrucResponse> gameResponse = httpClient.invokeHttp(firefrogUrl+searchUserUrl,	requestData, new Pager(), ut.getUserId(), ut.getUserName(), new TypeReference<Response<UserStrucResponse>>() {
			});
			
			Boolean bl = true;
			SearchUserResponse result = new SearchUserResponse();
			if(null != gameResponse.getBody() && null != gameResponse.getBody().getResult()){
				UserStrucResponse struc = gameResponse.getBody().getResult();
				
				String[] chains = struc.getUserChain().split("/");
				for(String str : chains){
					if(str.equals(ut.getUserName())){
						bl = false;
					}
				}
				
				if(bl){
					response.getHead().setStatus(109999);//无权限。
					return response;
				}
				result.setBalance(new BigDecimal(struc.getAvailBal()==null ? 0 : struc.getAvailBal()).divide(new BigDecimal(10000),4,RoundingMode.HALF_UP).floatValue());
				result.setUid(struc.getId()+"");
				result.setUsername(struc.getAccount());
			}else{
				response.getHead().setStatus(101004L);//无此用户
			}
			response.setResult(result);
			
		} catch (Exception e) {
			log.error("searchUser error:", e);
			response.getHead().setStatus(901000L);
		}
		return response;
	}
	

}
