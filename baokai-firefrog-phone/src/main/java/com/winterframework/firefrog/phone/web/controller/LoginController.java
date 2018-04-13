package com.winterframework.firefrog.phone.web.controller;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.core.type.TypeReference;
import com.winterframework.firefrog.game.entity.ChannelType;
import com.winterframework.firefrog.phone.utils.Encrypter;
import com.winterframework.firefrog.phone.web.cotroller.dto.BeginMissionRequest;
import com.winterframework.firefrog.phone.web.cotroller.dto.BeginMissionResponse;
import com.winterframework.firefrog.phone.web.cotroller.dto.FrontRequest;
import com.winterframework.firefrog.phone.web.cotroller.dto.FrontResponse;
import com.winterframework.firefrog.phone.web.cotroller.dto.MessageListResponseDTO;
import com.winterframework.firefrog.phone.web.cotroller.dto.UserLoginRequestDTO;
import com.winterframework.firefrog.phone.web.cotroller.dto.UserStrucResponse;
import com.winterframework.firefrog.phone.web.cotroller.dto.UserToken;
import com.winterframework.modules.spring.exetend.PropertyConfig;
import com.winterframework.modules.web.jsonresult.Pager;
import com.winterframework.modules.web.jsonresult.Request;
import com.winterframework.modules.web.jsonresult.Response;

@Controller("loginController")
@RequestMapping("/front")
public class LoginController extends BaseController {
	
	private static final Logger log = LoggerFactory.getLogger(LoginController.class);

	@PropertyConfig(value = "url.front.login")
	private String loginUrl;
	@PropertyConfig(value="url.front.userMsgList")
	private String msgListUrl;
	@PropertyConfig(value = "url.begin.gotoNewMission")
	private String gotoNewMissionUrl;
	
	@RequestMapping(value = "/login")
	@ResponseBody
	public Response<FrontResponse> login(@RequestBody Request<FrontRequest> request){
		
		Response<FrontResponse> fresponse = new Response<FrontResponse>(request);
		try {
			
			FrontRequest front = request.getBody().getParam();
			UserLoginRequestDTO dto = new UserLoginRequestDTO();
			dto.setAccount(front.getUsername());
			dto.setPasswd(front.getLoginpassSource());
			dto.setLoginIp(front.getLoginIp());
			dto.setUserAgent(ChannelType.transDeviceToChannelType(front.getDevice()));
			
			Response<UserStrucResponse> response = httpClient.invokeHttp(firefrogUrl + loginUrl, dto, new TypeReference<Response<UserStrucResponse>>() {});
			
			//取Response 状态信息。
			fresponse.getHead().setStatus(response.getHead().getStatus());
			
			UserStrucResponse user = response.getBody().getResult();
			if(null != user){
				
				FrontResponse result = new FrontResponse();
				
				result.setSource(user.getSource());//如果是3.0 则是老用户
				
				result.setUserid(user.getId());
				result.setUsername(user.getAccount());
				result.setNickname(user.getAccount());
				result.setLanguage(null);
				result.setUserrank(null);
//				result.setFrozentype(Long.parseLong(user.getFreezeMemo())); //暂时用不着，等确认
				result.setIsfrozen(user.getIsFreeze().longValue());
//				result.setIstester(istester);  //暂时用不着，等确认
				String token = "";
				UserToken ut = new UserToken();
				ut.setUserId(user.getId());
				ut.setLoginDate(new Date());
				java.util.Calendar cal=java.util.Calendar.getInstance();  
				cal.setTime(new Date());   
				cal.add(java.util.Calendar.DAY_OF_MONTH,1);  
				
				ut.setSecurity(user.getWithdrawPasswd()==null ? "0" : "1");
				ut.setTimeOut(cal.getTime());
				ut.setUserName(user.getAccount());
				Encrypter en = Encrypter.getInstance(encrypterKey,encrypterValue);
				token = new String(en.encrypt(ut.getUserName() +"|"+ ut.getLoginDate()));
				ut.setToken(token);
				result.setToken(token);
				result.setNeedSetSafeQuest(user.getQuStruc() == null ? true : false);
				
				setRedis(ut);
				
				//0:用户;1:总代;2:代理	
				if(user.getUserLvl()==0){
					result.setAgentType(1L);
				}else if(user.getUserLvl()==-1){
					result.setAgentType(0L);
				}else{
					result.setAgentType(2L);
				}
				
				result.setNeedSetSecurityPass(user.getWithdrawPasswd()==null? true : false);
				//0:无、1:有
				String openlink = "0";
				if(user.getUserLvl()!=-1 && user.getIsAllZero()==1){
					openlink = "1";
				}
				result.setOpenlink(openlink); 
				result.setIsvip(user.getVipLvl().longValue());
				
				//add newhand mission param isTrain
				BeginMissionRequest bmReq = new BeginMissionRequest();
				bmReq.setUserId(ut.getUserId());
				Response<BeginMissionResponse> newMisisonRes = httpClient.invokeHttp(firefrogUrl + gotoNewMissionUrl,bmReq,new TypeReference<Response<BeginMissionResponse>>() {
				});
				result.setIsTrain("Y".equals(newMisisonRes.getBody().getResult().getIsNewMission())?true : false);
				//add
				Response<MessageListResponseDTO> gameResponse = httpClient.invokeHttp(firefrogUrl + msgListUrl, null, new Pager(0,1000),
						ut.getUserId(), ut.getUserName(), new TypeReference<Response<MessageListResponseDTO>>() {});
				
				int unread = 0;
				if(gameResponse.getBody().getResult()!=null){
					unread = gameResponse.getBody().getResult().getUnreadCnt();
				}
				result.setUnread(unread);
				
				fresponse.setResult(result);
			}else{
				fresponse.getHead().setStatus(response.getHead().getStatus());
			}
			
		} catch (Exception e) {
			
			log.error("Login error:", e);
			fresponse.getHead().setStatus(901000L);
		}
		
		return fresponse;
	}
		
	
}
