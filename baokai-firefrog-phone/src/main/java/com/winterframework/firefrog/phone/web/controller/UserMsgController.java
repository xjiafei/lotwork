package com.winterframework.firefrog.phone.web.controller;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
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
import com.winterframework.firefrog.common.util.DataConverterUtil;
import com.winterframework.firefrog.common.util.DateUtils;
import com.winterframework.firefrog.game.web.dto.GameUserAwardGroupQueryRequest;
import com.winterframework.firefrog.game.web.dto.GameUserAwardGroupQueryResponse;
import com.winterframework.firefrog.game.web.dto.UserAwardListStruc;
import com.winterframework.firefrog.phone.web.cotroller.dto.BeginMissionRequest;
import com.winterframework.firefrog.phone.web.cotroller.dto.BeginMissionResponse;
import com.winterframework.firefrog.phone.web.cotroller.dto.FrontRequest;
import com.winterframework.firefrog.phone.web.cotroller.dto.FrontResponse;
import com.winterframework.firefrog.phone.web.cotroller.dto.GameGroup;
import com.winterframework.firefrog.phone.web.cotroller.dto.GameGroupReq;
import com.winterframework.firefrog.phone.web.cotroller.dto.GameGroups;
import com.winterframework.firefrog.phone.web.cotroller.dto.GetBalanceRequest;
import com.winterframework.firefrog.phone.web.cotroller.dto.GetBalanceResponse;
import com.winterframework.firefrog.phone.web.cotroller.dto.Keys;
import com.winterframework.firefrog.phone.web.cotroller.dto.MessageIdsDTO;
import com.winterframework.firefrog.phone.web.cotroller.dto.MessageListResponseDTO;
import com.winterframework.firefrog.phone.web.cotroller.dto.MessageMarkAndDeleteRequestDTO;
import com.winterframework.firefrog.phone.web.cotroller.dto.MessageQueryDetailRequestDTO;
import com.winterframework.firefrog.phone.web.cotroller.dto.ModifyRemarkRequest;
import com.winterframework.firefrog.phone.web.cotroller.dto.MsgStrucResponse;
import com.winterframework.firefrog.phone.web.cotroller.dto.OpenLinkDetailRequest;
import com.winterframework.firefrog.phone.web.cotroller.dto.OpenLinkDetailResponse;
import com.winterframework.firefrog.phone.web.cotroller.dto.OpenLinkListDto;
import com.winterframework.firefrog.phone.web.cotroller.dto.OpenLinkListRequest;
import com.winterframework.firefrog.phone.web.cotroller.dto.OpenLinkListResponse;
import com.winterframework.firefrog.phone.web.cotroller.dto.RetSettingRequest;
import com.winterframework.firefrog.phone.web.cotroller.dto.StatusResponse;
import com.winterframework.firefrog.phone.web.cotroller.dto.UserAgentCountStruc;
import com.winterframework.firefrog.phone.web.cotroller.dto.UserAwardStruc;
import com.winterframework.firefrog.phone.web.cotroller.dto.UserBetInfoSumStruc;
import com.winterframework.firefrog.phone.web.cotroller.dto.UserDetailResponse;
import com.winterframework.firefrog.phone.web.cotroller.dto.UserMsgDelRequest;
import com.winterframework.firefrog.phone.web.cotroller.dto.UserMsgDelResponse;
import com.winterframework.firefrog.phone.web.cotroller.dto.UserMsgDetailRequest;
import com.winterframework.firefrog.phone.web.cotroller.dto.UserMsgDetailResponse;
import com.winterframework.firefrog.phone.web.cotroller.dto.UserMsgListDto;
import com.winterframework.firefrog.phone.web.cotroller.dto.UserMsgListRequest;
import com.winterframework.firefrog.phone.web.cotroller.dto.UserMsgListResponse;
import com.winterframework.firefrog.phone.web.cotroller.dto.UserPointRequest;
import com.winterframework.firefrog.phone.web.cotroller.dto.UserPointResponse;
import com.winterframework.firefrog.phone.web.cotroller.dto.UserStruc;
import com.winterframework.firefrog.phone.web.cotroller.dto.UserToken;
import com.winterframework.firefrog.phone.web.cotroller.dto.UserUrl;
import com.winterframework.modules.spring.exetend.PropertyConfig;
import com.winterframework.modules.utils.DateConvertUtils;
import com.winterframework.modules.web.jsonresult.Pager;
import com.winterframework.modules.web.jsonresult.Request;
import com.winterframework.modules.web.jsonresult.Response;

@Controller("userMsgController")
@RequestMapping("/information")
public class UserMsgController extends BaseController {
	
	private static final Logger log = LoggerFactory.getLogger(UserMsgController.class);
	private static final int DIFF_VALUE = 50;//开户时上下级最小点差
	private static final int PRIZE_UNIT = 100; //奖金返点单位
	@PropertyConfig(value="url.front.userMsgList")
	private String msgListUrl;
	@PropertyConfig(value="url.front.msgDetail")
	private String msgDetailUrl;
	@PropertyConfig(value="url.front.msgDel")
	private String msgDelUrl;
	@PropertyConfig(value="url.front.userBal")
	private String getUserBal;
	@PropertyConfig(value="url.front.openLinkList")
	private String openLinkListUrl;
	@PropertyConfig(value="url.front.openLinkDetail")
	private String openLinkDetailUrl;
	@PropertyConfig(value="url.user.queryUserDetailInfoByAccount")
	private String queryUserByNameUrl;
	@PropertyConfig(value="url.front.userPoint")
	private String userPointUrl;
	@PropertyConfig(value = "url.begin.gotoNewMission")
	private String gotoNewMissionUrl;
	//取得玩家獎金組資料
	@PropertyConfig(value="url.front.queryUserAward")
	private String queryUserAwardUrl;
	//取得該URL註冊玩家所有帳號
	@PropertyConfig(value="url.front.openLinkUsers")
	private String queryUserRegisters;
	//刪除註冊URL
	@PropertyConfig(value="url.front.delOpenLink")
	private String delOpenLink;
	//取得URL資訊getUrlById
	@PropertyConfig(value="url.front.getUrlById")
	private String getUrlById;
	//修改备注
	@PropertyConfig(value="url.front.modifyRemark")
	private String modifyRemark;
	//initCreateUrl
	@PropertyConfig(value="url.front.initCreateUrl")
	private String initCreateUrl;
	//doRetSetting
	@PropertyConfig(value="url.front.doRetSetting")
	private String doRetSetting;
		
	@ResponseBody
	@RequestMapping("/getBalance")
	public Response<GetBalanceResponse> getUserBalance(@RequestBody Request<GetBalanceRequest> request) throws Exception{
		Response<GetBalanceResponse> response = new Response<GetBalanceResponse>(request);
		GetBalanceResponse result = new GetBalanceResponse();
		String token = request.getHead().getSessionId();
		try {
			String account = getUserNameByToken(token);
			if(null == account){
				response.getHead().setStatus(7L);
				return response;
			}
			UserToken ut = getUserToken(account);
			if (null != ut.getUserId()) {
				Response<Long> balance = httpClient.invokeHttp(firefrogUrl
						+ getUserBal, ut.getUserId(), Long.class);
				result.setBalance( new BigDecimal(balance.getBody().getResult()).divide(new BigDecimal(10000),2,RoundingMode.HALF_UP).doubleValue());
			}

			response.setResult(result);
	            
		} catch (Exception e) {
			log.error("getUserBalance error:", e);
			response.getHead().setStatus(901000L);
			
		}
		return response;
	}
	
	@ResponseBody
	@RequestMapping("/userMsgList")
	public Response<UserMsgListResponse> msgList(@RequestBody Request<UserMsgListRequest> request) throws Exception{
		Response<UserMsgListResponse> response = new Response<UserMsgListResponse>(request);
		String token = request.getHead().getSessionId();
		
		try {
			//做玩家資料驗證
			UserMsgListResponse result = new UserMsgListResponse();
			String account = getUserNameByToken(token);
			if(null == account){
				response.getHead().setStatus(7L);
				return response;
			}
			UserToken ut = getUserToken(account);
			//送user-api查詢相關資料
			Response<MessageListResponseDTO> gameResponse = httpClient.invokeHttp(firefrogUrl + msgListUrl, null, new Pager(0,1000),
					ut.getUserId(), ut.getUserName(), new TypeReference<Response<MessageListResponseDTO>>() {});
			//組成資料reponse回去
			List<UserMsgListDto> msglist = new ArrayList<UserMsgListDto>();
			if(null != gameResponse.getBody().getResult()){
				List<MsgStrucResponse> list = gameResponse.getBody().getResult().getReceives();
				if (!list.isEmpty()) {
					
					for(MsgStrucResponse struc : list){
						UserMsgListDto dto = new UserMsgListDto();
						if((struc.getSendFrom() == null) || ( struc.getSendFrom() !=-1L)){
							dto.setEntry(struc.getId()+""); 
							//"0：不需要保存，读完默认删	1：读完不会默认删除" 固定给1
							dto.setTitle(struc.getTitle());
							dto.setIskeep(1L);
							dto.setSendtime(DateConvertUtils.format(DataConverterUtil.convertLong2Date(struc.getSendTime()), DateUtils.DATETIME_FORMAT_PATTERN));
							dto.setSubject(struc.getContent());
							dto.setIsRead(struc.getIsRead());
							msglist.add(dto);
						}
					}
				}
			}
			result.setList(msglist);
			response.setResult(result);
			
		} catch (Exception e) {
			log.error("msgList error:", e);
			response.getHead().setStatus(901000L);
		}
		return response;
	}
	
	@ResponseBody
	@RequestMapping("/userMsgDetail")
	public Response<UserMsgDetailResponse> msgDetail(@RequestBody Request<UserMsgDetailRequest> request) throws Exception{
		
		Response<UserMsgDetailResponse> response = new Response<UserMsgDetailResponse>(request);
		
			String token = request.getHead().getSessionId();
		
		try {
			
			String account = getUserNameByToken(token);
			if(null == account){
				response.getHead().setStatus(7L);
				return response;
			}
			UserToken ut = getUserToken(account);
			MessageQueryDetailRequestDTO gameRequest = new  MessageQueryDetailRequestDTO();
			gameRequest.setRootId(Long.parseLong(request.getBody().getParam().getMid()));
			
			Response<MessageListResponseDTO> gameResponse = httpClient.invokeHttp(firefrogUrl + msgDetailUrl, gameRequest,new Pager(0,10000), ut.getUserId(),ut.getUserName(),  new TypeReference<Response<MessageListResponseDTO>>(){});
			
			UserMsgDetailResponse result = new UserMsgDetailResponse();
			if(null != gameResponse.getBody() && null != gameResponse.getBody().getResult()){
				
				List<MsgStrucResponse> list = gameResponse.getBody().getResult().getReceives();
				if(null !=  list && !list.isEmpty()){
					MsgStrucResponse struc = list.get(0);
					result.setContent(struc.getContent());
					result.setEntry(String.valueOf(struc.getId()));
					result.setIskeep(1);
					result.setSendtime(DateConvertUtils.format(DataConverterUtil.convertLong2Date(struc.getSendTime()), DateUtils.DATETIME_FORMAT_PATTERN));
					result.setSubject(struc.getTitle());
					result.setTitle(struc.getType()+""); 
				}
				
			}
			
			Response<MessageListResponseDTO> gameResponseList = httpClient.invokeHttp(firefrogUrl + msgListUrl, null, new Pager(0,1000),
					ut.getUserId(), ut.getUserName(), new TypeReference<Response<MessageListResponseDTO>>() {});
			int unread=0;
			if(gameResponseList.getBody().getResult()!=null){
				unread = gameResponseList.getBody().getResult().getUnreadCnt();
			}
			result.setUnread(unread);
			response.setResult(result);
			
		} catch (Exception e) {
			log.error("msgDetail error:", e);
			response.getHead().setStatus(901000L);
		}
		return response;
	}
	
	@ResponseBody
	@RequestMapping("/userMsgDel")
	public Response<UserMsgDelResponse> msgdel(@RequestBody Request<UserMsgDelRequest> request) throws Exception{
		
		Response<UserMsgDelResponse> response = new Response<UserMsgDelResponse>(request);
		String token = request.getHead().getSessionId();
		try {
			String account = getUserNameByToken(token);
			if(null == account){
				response.getHead().setStatus(7L);
				return response;
			}
			UserToken ut = getUserToken(account);
			
			MessageIdsDTO gameRequest = new MessageIdsDTO();
			String[] mids = request.getBody().getParam().getMid().split(",");
			MessageMarkAndDeleteRequestDTO[] dtos = new MessageMarkAndDeleteRequestDTO[mids.length];
			for(int i=0;i<mids.length;i++){
				MessageMarkAndDeleteRequestDTO dto = new MessageMarkAndDeleteRequestDTO();
				dto.setId(Long.valueOf(mids[i]));
				dto.setFlag(1);
				dtos[i] = dto;
			}
			gameRequest.setMsgIds(dtos);
//			gameRequest.setStatus(4L); //删除
			
			Response<MessageIdsDTO> delResponse = httpClient.invokeHttp(firefrogUrl+ msgDelUrl, gameRequest,new Pager(), ut.getUserId(),ut.getUserName(), new TypeReference<Response<MessageIdsDTO>>() {
			} );
			
			UserMsgDelResponse result = new UserMsgDelResponse();
			response.getHead().setStatus(delResponse.getHead().getStatus());
			if (delResponse.getHead().getStatus() == 0) {
				result.setMessageType(0L);
				result.setStatus("success");
			}

			response.setResult(result);
			
		} catch (Exception e) {
			log.error("msgdel error:", e);
			response.getHead().setStatus(901000L);
		}
		return response;
	}
	
	
	@ResponseBody
	@RequestMapping("/userPoint")
	public Response<UserPointResponse> userPoint(@RequestBody Request<UserPointRequest> request) throws Exception{
		
		Response<UserPointResponse> response = new Response<UserPointResponse>(request);
		
		String token = request.getHead().getSessionId();
		try {
			String account = getUserNameByToken(token);
			if(null == account){
				response.getHead().setStatus(7L);
				return response;
			}
			UserToken ut = getUserToken(account);
			
			UserAgentCountStruc requestData = new UserAgentCountStruc();
//			requestData.setEndTime(new Date());
			Calendar cal = Calendar.getInstance();
			cal.setTime(new Date());
			cal.add(java.util.Calendar.DATE, -1);
			requestData.setStartTime(cal.getTime());
			requestData.setUserId(ut.getUserId());
			requestData.setType(2L);
			
			Response<UserBetInfoSumStruc> gameResponse = httpClient.invokeHttp(gameUrl + userPointUrl, requestData, ut.getUserId(), ut.getUserName(), new TypeReference<Response<UserBetInfoSumStruc>>() {
			});
			
			UserPointResponse result = new UserPointResponse();
			Long amount = 0L;
			Long point = 0L;
			Long win = 0L;
			if(null != gameResponse.getBody() && null != gameResponse.getBody().getResult()){
				
				UserBetInfoSumStruc struc = gameResponse.getBody().getResult();
					amount = struc.getBetAmount();
					point = struc.getRetPoint();
					win = struc.getWinAmount();
			}
			result.setAmount(new BigDecimal(amount).divide(new BigDecimal(10000), 4, RoundingMode.HALF_UP).doubleValue());
			result.setPoint(new BigDecimal(point).divide(new BigDecimal(10000), 4, RoundingMode.HALF_UP).doubleValue());
			result.setWin(new BigDecimal(win).divide(new BigDecimal(10000), 4, RoundingMode.HALF_UP).doubleValue());
			response.setResult(result);

		}catch(Exception e){
			log.error("userPoint error:", e);
			response.getHead().setStatus(901000L);
		}
		return response;
	}
	
	@ResponseBody
	@RequestMapping("/openLinkList")
	public Response<OpenLinkListResponse> openLinkList(@RequestBody Request<OpenLinkListRequest> request) throws Exception{
		
		Response<OpenLinkListResponse> response = new Response<OpenLinkListResponse>(request);
		log.info("--------------------openLinkList-------------------");
		String token = request.getHead().getSessionId();
		try {
			//檢查玩家資訊
			String account = getUserNameByToken(token);
			if(null == account){
				response.getHead().setStatus(7L);
				return response;
			}
			UserToken ut = getUserToken(account);
			//回應的response
			OpenLinkListResponse result = new OpenLinkListResponse();
			UserUrl requestData = new UserUrl();
			
			/* http://127.0.0.1:8082/user/url/list */
			Response<List<UserUrl>> gameResponse = httpClient.invokeHttp(firefrogUrl+openLinkListUrl, requestData, new Pager(0,1000), ut.getUserId(), ut.getUserName(), new TypeReference<Response<List<UserUrl>>>() {
			});
			
			List<OpenLinkListDto> list = new ArrayList<OpenLinkListDto>();
			if(null != gameResponse.getBody() && null != gameResponse.getBody().getResult()){
				List<UserUrl> userList = gameResponse.getBody().getResult();
				for(UserUrl user : userList){
					OpenLinkListDto dto = new OpenLinkListDto();
					dto.setId(user.getId().toString());
					dto.setRemark(user.getMemo());
					dto.setType(user.getType().intValue());
					dto.setUrlstring(user.getUrl());
					dto.setRegisters(user.getRegisters()==null ? 0L :user.getRegisters());//已注册用户 0为未注册 其他为已注册
					//2015-07-28 
					/*新增time表示產生時間，start表示開始時間,end表示到期時間*/
					dto.setStart(DateUtils.format(user.getGmtCreated(), DateUtils.DATETIME_FORMAT_PATTERN));
					Calendar cal = Calendar.getInstance();
					cal.setTime(user.getGmtCreated());
					cal.add(java.util.Calendar.DATE, user.getDays());
					dto.setEnd(DateUtils.format(cal.getTime(),DateUtils.DATETIME_FORMAT_PATTERN));
					if(user.getDays() == -1){
						dto.setEnd(null);
					}
					
					list.add(dto);
				}
			}
			
			result.setList(list);
			response.setResult(result);
		} catch (Exception e) {
			log.error("openLinkList error:", e);
			response.getHead().setStatus(901000L);
		}
		
		return response;
	}
	
	/**
	 * 取得用戶獎金組返點資料。
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("/openLinkDetail")
	public Response<OpenLinkDetailResponse> openLinkDetail(@RequestBody Request<OpenLinkDetailRequest> request) throws Exception{
		Response<OpenLinkDetailResponse> response = new Response<OpenLinkDetailResponse>(request);
		log.info("-------------------openLinkDetail--------------------");
		String token = request.getHead().getSessionId();
		try {
			//做玩家資料驗證
			String account = getUserNameByToken(token);
			if(null == account){
				response.getHead().setStatus(7L);
				return response;
			}
			UserToken ut = getUserToken(account);
			OpenLinkDetailResponse result = new OpenLinkDetailResponse();
			UserUrl requestData = new UserUrl();
			requestData.setId(Long.parseLong(request.getBody().getParam().getId()));
			Response<UserUrl> gameResponse = httpClient.invokeHttp(firefrogUrl+openLinkDetailUrl, requestData, new Pager(0,1000), ut.getUserId(), ut.getUserName(), new TypeReference<Response<UserUrl>>() {
			});
			List<Keys> list = new ArrayList<Keys>();
			if(null != gameResponse.getBody() && null != gameResponse.getBody().getResult()){
				
				UserUrl user = gameResponse.getBody().getResult();
				
				result.setStart(DateUtils.format(gameResponse.getBody().getResult().getGmtCreated(), DateUtils.DATETIME_FORMAT_PATTERN));
				//表示有效期限為永久
				if(gameResponse.getBody().getResult().getDays() == -1){
					result.setEnd(null);
				}else{
					Calendar cal = Calendar.getInstance();
					cal.setTime(gameResponse.getBody().getResult().getGmtCreated());
					cal.add(java.util.Calendar.DATE, gameResponse.getBody().getResult().getDays());
					result.setEnd(DateUtils.format(cal.getTime(),DateUtils.DATETIME_FORMAT_PATTERN));
				}
												
				for(UserAwardStruc struc : user.getUserawardListStruc()){
					Keys key = new Keys();
					key.setCnname(struc.getAwardName());
					key.setIndefinitePoint(struc.getThreeoneRet().floatValue()/100);
					key.setLotteryid(struc.getLotteryId());
					key.setUserpoint(struc.getDirectRet().floatValue()/100);
					key.setLhcYear(struc.getLhcYear().floatValue()/100);
					key.setLhcColor(struc.getLhcColor().floatValue()/100);
					key.setLhcFlatcode(struc.getLhcFlatcode().floatValue()/100);
					key.setLhcHalfwave(struc.getLhcHalfwave().floatValue()/100);
					key.setLhcNotin(struc.getLhcNotin().floatValue()/100);
					key.setLhcOneyear(struc.getLhcOneyear().floatValue()/100);
					key.setLhcContinuein23(struc.getLhcContinuein23().floatValue()/100);
					key.setLhcContinuein4(struc.getLhcContinuein4().floatValue()/100);
					key.setLhcContinuein5(struc.getLhcContinuein5().floatValue()/100);
					key.setLhcContinuenotin23(struc.getLhcContinuenotin23().floatValue()/100);
					key.setLhcContinuenotin4(struc.getLhcContinuenotin4().floatValue()/100);
					key.setLhcContinuenotin5(struc.getLhcContinuenotin5().floatValue()/100);
					key.setLhcContinuecode(struc.getLhcContinuecode().floatValue()/100);
					list.add(key);
				}
				//排序
				Collections.sort(list, new Comparator<Keys>() {
					public int compare(Keys o1, Keys o2) {
						return (o1.getLotteryid().intValue() - o2.getLotteryid().intValue());
					}
				});
				
			}
			result.setKey(list);
			response.setResult(result);
			
		} catch (Exception e) {
			log.error("openLinkDetail error: ", e);
			response.getHead().setStatus(901000L);
		}
		return response;
	}
	
	/**
	 * 查詢獎金詳情各彩種明細
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("/prizeDetail")
	public  Response<OpenLinkDetailResponse> prizeDetail(@RequestBody Request<OpenLinkDetailRequest> request) throws Exception{
		log.info("prizeDetail start~~~~~");
		Response<OpenLinkDetailResponse> response = new Response<OpenLinkDetailResponse>(request);
		String token = request.getHead().getSessionId();
		try{
			String account = getUserNameByToken(token);
			if(null == account){
				response.getHead().setStatus(7L);
				return response;
			}
			UserToken ut = getUserToken(account);
			OpenLinkDetailResponse result = new OpenLinkDetailResponse();			
			//根据用户名找用信息。
			UserStruc userRequestData = new UserStruc();
			userRequestData.setAccount(ut.getUserName());
			log.debug("username : " + ut.getUserName());
			
			Response<UserDetailResponse> userDataResponse = httpClient.invokeHttp(firefrogUrl+ queryUserByNameUrl, userRequestData, new TypeReference<Response<UserDetailResponse>>() {
			});
			int agentType = userDataResponse.getBody().getResult().getUserStruc().getUserLvl();
			//用户分配奖金组。
			GameUserAwardGroupQueryRequest userAwardRequest = new GameUserAwardGroupQueryRequest();
			userAwardRequest.setUserid(ut.getUserId());
			log.debug("agentType : " + userDataResponse.getBody().getResult().getUserStruc().getUserLvl());
			userAwardRequest.setType(agentType==0?0:1);//总代
			userAwardRequest.setAwardType(0);
			Response<GameUserAwardGroupQueryResponse> userAwardResponse = httpClient.invokeHttp(gameUrl+ queryUserAwardUrl, userAwardRequest, new TypeReference<Response<GameUserAwardGroupQueryResponse>>() {
			});
			
			List<Keys> list = new ArrayList<Keys>();
			if(null != userAwardResponse.getBody() && null !=userAwardResponse.getBody().getResult()){
				List<UserAwardListStruc> userAwardList = userAwardResponse.getBody().getResult().getUserAwardListStruc();
				log.info("userAwardList  size : " + userAwardList.size());
				for(UserAwardListStruc award : userAwardList){
					Keys key = new Keys();
					key.setCnname(award.getAwardName());
					key.setLotteryid(award.getLotteryId());
					key.setUserpoint(award.getDirectRet().floatValue()/100);
					key.setIndefinitePoint(award.getThreeoneRet().floatValue()/100);
					key.setLhcYear(award.getLhcYear().floatValue()/100);
					key.setLhcColor(award.getLhcColor().floatValue()/100);
					key.setSbThreeoneRet(award.getSbThreeoneRet().floatValue()/100);
					key.setLhcFlatcode(award.getLhcFlatcode().floatValue()/100);
					key.setLhcHalfwave(award.getLhcHalfwave().floatValue()/100);
					key.setLhcNotin(award.getLhcNotin().floatValue()/100);
					key.setLhcOneyear(award.getLhcOneyear().floatValue()/100);
					key.setLhcContinuein23(award.getLhcContinuein23().floatValue()/100);
					key.setLhcContinuein4(award.getLhcContinuein4().floatValue()/100);
					key.setLhcContinuein5(award.getLhcContinuein5().floatValue()/100);
					key.setLhcContinuenotin23(award.getLhcContinuenotin23().floatValue()/100);
					key.setLhcContinuenotin4(award.getLhcContinuenotin4().floatValue()/100);
					key.setLhcContinuenotin5(award.getLhcContinuenotin5().floatValue()/100);
					key.setLhcContinuecode(award.getLhcContinuecode().floatValue()/100);
					switch(agentType){
						case -1://用戶
							key.setSelected(1);
							break;
						case 0://總代
							key.setSelected(0);
							break;
						default://代理
							if(award.getBetType().intValue() == 1)
								key.setSelected(1);
							else
								key.setSelected(0);
							break;
					}
					list.add(key);
				}
				//排序
				Collections.sort(list, new Comparator<Keys>() {
					public int compare(Keys o1, Keys o2) {
						return (o1.getLotteryid().intValue() - o2.getLotteryid().intValue());
					}
				});
			}
			result.setKey(list);
			response.setResult(result);
		}catch(Exception e){
			log.error("prizeDetail error: ", e);
			response.getHead().setStatus(901000L);		
		}
		
		return response;
	}
	
	//2015-10-22新增链结开户用户
	@ResponseBody
	@RequestMapping("/openLinkUsers")
	public  Response<List<String>> openLinkUsers(@RequestBody Request<OpenLinkDetailRequest> request) throws Exception{
		Response<List<String>> response = new Response<List<String>>(request);
		log.info("-------------------openLinkUsers--------------------");
		String token = request.getHead().getSessionId();
		try{
			String account = getUserNameByToken(token);
			if(null == account){
				response.getHead().setStatus(7L);
				return response;
			}
			UserToken ut = getUserToken(account);
			Long url_id = Long.parseLong(request.getBody().getParam().getId());
			log.debug("url_id : " + url_id);
			List<String> result = new ArrayList<String>();
			UserUrl userUrl = new UserUrl();
			userUrl.setId(url_id);
			
			Response<UserDetailResponse> userDataResponse = httpClient.invokeHttp(firefrogUrl+ queryUserRegisters, userUrl,new Pager(0,1000), ut.getUserId(), ut.getUserName(), new TypeReference<Response<List<String>>>() {
			});
			//Response<UserDetailResponse> userDataResponse = httpClient.invokeHttp(firefrogUrl+ queryUserRegisters, userUrl,new Pager(0,1000), 112, "sksk", new TypeReference<Response<List<String>>>() {
			//});
			
			if(null != userDataResponse.getBody() && null != userDataResponse.getBody().getResult() ){
				result = (List<String>)userDataResponse.getBody().getResult();
			}
			
			response.setResult(result);
		} catch (Exception e){
			log.error("openLinkUsers error: ", e);
			response.getHead().setStatus(901000L);		
		}
		return response;
	}
	
	//2015-10-22新增链结开户用户
	@ResponseBody
	@RequestMapping("/delOpenLink")
	public  Response<StatusResponse> delOpenLink(@RequestBody Request<OpenLinkDetailRequest> request) throws Exception{
		Response<StatusResponse> response = new Response<StatusResponse>(request);
		log.debug("-------------------delOpenLink--------------------");
		String token = request.getHead().getSessionId();
		try{
		    String account = getUserNameByToken(token);
			if(null == account){
				response.getHead().setStatus(7L);
				return response;
			}
			UserToken ut = getUserToken(account);
			
			Long url_id = Long.parseLong(request.getBody().getParam().getId());
			log.debug("url_id : " + url_id);
			UserUrl userUrl = new UserUrl();
			userUrl.setId(url_id);
			//first check isself's url
			Response<UserUrl> urlResponse = httpClient.invokeHttp(firefrogUrl+ getUrlById, userUrl,new Pager(0,1000), ut.getUserId(), ut.getUserName(), new TypeReference<Response<UserUrl>>() {
			});
			//Response<UserUrl> urlResponse = httpClient.invokeHttp(firefrogUrl+ getUrlById, userUrl,new Pager(0,1000), 112, "sksk", new TypeReference<Response<UserUrl>>() {
			//});
			log.debug("url Creator : " + urlResponse.getBody().getResult().getCreator().longValue());
			log.debug("ut user id : " + ut.getUserId().longValue());
			if(urlResponse.getBody().getResult().getCreator().longValue() != ut.getUserId().longValue()){
				response.getHead().setStatus(7L);
				return response;
			}
			Response<UserDetailResponse> dataResponse = httpClient.invokeHttp(firefrogUrl+ delOpenLink, userUrl,new Pager(0,1000), ut.getUserId(), ut.getUserName(), new TypeReference<Response<UserUrl>>() {
			});
			//Response<UserDetailResponse> dataResponse = httpClient.invokeHttp(firefrogUrl+ delOpenLink, userUrl,new Pager(0,1000), 112, "sksk", new TypeReference<Response<UserUrl>>() {
			//});
			StatusResponse result = new StatusResponse();
			result.setStatus("success");
			response.setResult(result);
		} catch (Exception e){
			log.error("delOpenLink error: ", e);
			response.getHead().setStatus(901000L);		
		}
		return response;
	}
	//2015-10-23新增 修改备注
	@ResponseBody
	@RequestMapping("/modifyRemark")
	public  Response<StatusResponse> modifyRemark(@RequestBody Request<ModifyRemarkRequest> request) throws Exception{
		Response<StatusResponse> response = new Response<StatusResponse>(request);
		log.info("-------------------modifyRemark--------------------");
		String token = request.getHead().getSessionId();
		try{
			String account = getUserNameByToken(token);
			if(null == account){
				response.getHead().setStatus(7L);
				return response;
			}
			UserToken ut = getUserToken(account);
			Long url_id = Long.parseLong(request.getBody().getParam().getId());
			log.debug("url_id : " + url_id);
			String remark = request.getBody().getParam().getRemark();
			log.debug("remark : " + remark);
			UserUrl userUrl = new UserUrl();
			userUrl.setId(url_id);
			//first check isself's url
			Response<UserUrl> urlResponse = httpClient.invokeHttp(firefrogUrl+ getUrlById, userUrl,new Pager(0,1000), ut.getUserId(), ut.getUserName(), new TypeReference<Response<UserUrl>>() {
			});
			//Response<UserUrl> urlResponse = httpClient.invokeHttp(firefrogUrl+ getUrlById, userUrl,new Pager(0,1000), 112, "sksk", new TypeReference<Response<UserUrl>>() {
			//});
			log.debug("url Creator : " + urlResponse.getBody().getResult().getCreator().longValue());
			//test code
			//UserToken ut = new UserToken();
			//ut.setUserId(112L);
			log.debug("ut user id : " + ut.getUserId().longValue());
			if(urlResponse.getBody().getResult().getCreator().longValue() != ut.getUserId().longValue()){
				response.getHead().setStatus(7L);
				return response;
			}
			userUrl.setMemo(remark);
			Response<Object> dataResponse = httpClient.invokeHttp(firefrogUrl+ modifyRemark, userUrl,new Pager(0,1000), ut.getUserId(), ut.getUserName(), new TypeReference<Response<Object>>() {
			});
			
			StatusResponse result = new StatusResponse();
			result.setStatus("success");
			response.setResult(result);
		} catch (Exception e){
			log.error("modifyRemark error: ", e);
			response.getHead().setStatus(901000L);		
		}	
		return response;
	}
	
	/**
	 * 手動建立開戶鏈結，取得獎金組返點資料。
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("/initCreateUrl")
	public  Response<GameGroups> initCreateUrl(@RequestBody Request<GameGroupReq> request) throws Exception{
		log.info("--------------------initCreateUrl---------------------");
		Response<GameGroups> response = new Response<GameGroups>(request);
		String token = request.getHead().getSessionId();
		try{
			String account = getUserNameByToken(token);
			if(null == account){
				response.getHead().setStatus(7L);
				return response;
			}
			UserToken ut = getUserToken(account);
			//根据用户名找用信息。
			UserStruc userRequestData = new UserStruc();
			userRequestData.setAccount(ut.getUserName());
			log.debug("username : " + ut.getUserName());
			Response<UserDetailResponse> userDataResponse = httpClient.invokeHttp(firefrogUrl+ queryUserByNameUrl, userRequestData, new TypeReference<Response<UserDetailResponse>>() {
			});
			
			int agentType = userDataResponse.getBody().getResult().getUserStruc().getUserLvl();
			log.debug("agentType : " + agentType);
			log.debug("user_id : " + ut.getUserId());
			log.debug("type : " + request.getBody().getParam().getType());
			
			GameGroupReq data = new GameGroupReq();
			data.setUserId(ut.getUserId());
			data.setType(agentType==0?0L:1L);
			data.setAwardType(0L);
			Response<GameGroups> urlResponse = httpClient.invokeHttp(firefrogUrl+ initCreateUrl, data,new Pager(0,1000),ut.getUserId(), ut.getUserName(), new TypeReference<Response<GameGroups>>() {
			});
			GameGroups result = urlResponse.getBody().getResult();
			response.setResult(result);
		} catch(Exception e){
			log.error("initCreateUrl error: ", e);
			response.getHead().setStatus(901000L);		
		}
		return response;
	}
	
	
	/**
	 * 儲存用戶鏈結返點。
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("/doRetSetting")
	public  Response<StatusResponse> doRetSetting(@RequestBody Request<RetSettingRequest> request) throws Exception{
		log.debug("--------------------doRetSetting---------------------");
		Response<StatusResponse> response = new Response<StatusResponse>(request);
		String token = request.getHead().getSessionId();
		try{
			String account = getUserNameByToken(token);
			if(null == account){
				response.getHead().setStatus(7L);
				return response;
			}
			UserToken ut = getUserToken(account);
			//參數值
			Long type = request.getBody().getParam().getType();
			Long setUp = request.getBody().getParam().getSetUp();
			Long days = request.getBody().getParam().getDays();
			String memo = request.getBody().getParam().getMemo();
			String doamin = request.getBody().getParam().getDomain();
			log.debug("type : " + type);
			log.debug("setUp : " + setUp);
			log.debug("days : " + days);
			log.debug("memo : " + memo);
			log.debug("doamin : " + doamin);
			if(0==type.intValue() && 2==setUp.intValue()){
				response.getHead().setStatus(109784L);
				return response;
			}
			
			List<UserAwardStruc> infos = request.getBody().getParam().getInfos();
			log.debug("infos size : " + infos.size());
			log.debug("-----------------infos-----------------");
			for(UserAwardStruc info:infos){
				log.debug("lotId : " + info.getLotteryId());
				log.debug("LotterySeriesName : " + info.getLotterySeriesName());
				log.debug("AwardName : " + info.getAwardName());
			}
			if(infos.size() <= 0){
				response.getHead().setStatus(109785L);
				return response;
			}
			//查詢用戶資訊
			Response<UserDetailResponse> userInfos = getUserInfos(ut.getUserName(), queryUserByNameUrl);
			Integer agentType = userInfos.getBody().getResult().getUserStruc().getUserLvl();
			log.debug("agentType: " + agentType);	
			if(-1 == agentType.intValue()){
				response.getHead().setStatus(109782L);
				return response;
			}
			
			UserUrl datas= new UserUrl();
			datas.setMemo(memo);
			datas.setType(type);
			datas.setDays(days.intValue());
			datas.setUrl(doamin + "/register/index/");
			//若為代理 size == 1
			Float setValue = null;
			if(1 == type.intValue()){
				datas.setSize(1L);
				if(2 == setUp.intValue()){
					setValue = request.getBody().getParam().getSetValue();
				}
			}
			log.info("setValue" + setValue);
			UserAwardStruc[] strucAry = new UserAwardStruc[infos.size()];
			List<Long> lotIdsList = new ArrayList<Long>();
			List<Long> awardList = new ArrayList<Long>();
			
			for(int i=0;i<infos.size();i++){
				//若為代理且為快捷設置
				UserAwardStruc struc = new UserAwardStruc();
				
				struc.setLotterySeriesCode(infos.get(i).getLotterySeriesCode());
				struc.setLotterySeriesName(infos.get(i).getLotterySeriesName());
				struc.setAwardName(infos.get(i).getAwardName());
				struc.setAwardGroupId(infos.get(i).getAwardGroupId());
				struc.setLotteryId(infos.get(i).getLotteryId());
				if(null != setValue){
					
					//做快捷返點範圍檢查
					log.info("setValue : "+ setValue);
					log.debug("directLimitRet : "+ infos.get(i).getDirectLimitRet());
					log.debug("threeLimitRet : "+ infos.get(i).getThreeLimitRet());
					boolean isOver = checkSetValue(setValue, 
							infos.get(i).getDirectLimitRet().intValue(),
							infos.get(i).getThreeLimitRet().intValue());
					log.debug("isOver : "+ isOver);
					if(isOver) {
						response.getHead().setStatus(109786L);
						return response;
					}
					
					struc.setDirectRet(new BigDecimal(setValue));
					struc.setThreeoneRet(new BigDecimal(setValue));
					struc.setLhcYear(new BigDecimal(setValue));
					struc.setLhcColor(new BigDecimal(setValue));
					struc.setSbThreeoneRet(new BigDecimal(setValue));
					struc.setLhcFlatcode(new BigDecimal(setValue));
					struc.setLhcHalfwave(new BigDecimal(setValue));
					struc.setLhcNotin(new BigDecimal(setValue));
					struc.setLhcOneyear(new BigDecimal(setValue));
					struc.setLhcContinuein23(new BigDecimal(setValue));
					struc.setLhcContinuein4(new BigDecimal(setValue));
					struc.setLhcContinuein5(new BigDecimal(setValue));
					struc.setLhcContinuenotin23Limit(new BigDecimal(setValue));
					struc.setLhcContinuenotin4(new BigDecimal(setValue));
					struc.setLhcContinuenotin5(new BigDecimal(setValue));
					struc.setLhcContinuecode(new BigDecimal(setValue));
					struc.setDirectLimitRet(new BigDecimal(setValue));
					struc.setThreeLimitRet(new BigDecimal(setValue));
					struc.setLhcYearLimit(new BigDecimal(setValue));
					struc.setLhcColorLimit(new BigDecimal(setValue));
					struc.setSbThreeoneRetLimit(new BigDecimal(setValue));
					struc.setLhcFlatcodeLimit(new BigDecimal(setValue));
					struc.setLhcHalfwaveLimit(new BigDecimal(setValue));
					struc.setLhcNotinLimit(new BigDecimal(setValue));
					struc.setLhcOneyearLimit(new BigDecimal(setValue));
					struc.setLhcContinuein23Limit(new BigDecimal(setValue));
					struc.setLhcContinuein4Limit(new BigDecimal(setValue));
					struc.setLhcContinuein5Limit(new BigDecimal(setValue));
					struc.setLhcContinuenotin23Limit(new BigDecimal(setValue));
					struc.setLhcContinuenotin4Limit(new BigDecimal(setValue));
					struc.setLhcContinuenotin5Limit(new BigDecimal(setValue));
					struc.setLhcContinuecodeLimit(new BigDecimal(setValue));
					struc.setStatus(new BigDecimal(1));
					
					strucAry[i] = struc;
				}else{
					int maxLimit = 0;
					
					//返點為null或设置返点过大时候 自动修正返点值					
					if(null == infos.get(i).getDirectRet() || checkRetValue(infos.get(i).getDirectRet().intValue(),infos.get(i).getDirectLimitRet().intValue())){
						maxLimit = infos.get(i).getDirectLimitRet().intValue() - DIFF_VALUE;
						struc.setDirectRet(new BigDecimal(maxLimit));
					} else {
						//返点为负数
						if(infos.get(i).getDirectRet().intValue() < 0){
							struc.setDirectRet(new BigDecimal(0));
						} else {
							struc.setDirectRet(infos.get(i).getDirectRet());
						}
					}
					if(null == infos.get(i).getThreeoneRet() || checkRetValue(infos.get(i).getThreeoneRet().intValue(),infos.get(i).getThreeLimitRet().intValue())){
						maxLimit = infos.get(i).getThreeLimitRet().intValue() - DIFF_VALUE;
						struc.setThreeoneRet(new BigDecimal(maxLimit));
					} else {
						if(infos.get(i).getThreeoneRet().intValue() < 0){
							struc.setThreeoneRet(new BigDecimal(0));
						} else {
							struc.setThreeoneRet(infos.get(i).getThreeoneRet());
						}
					}
					if(null == infos.get(i).getLhcYear() || checkRetValue(infos.get(i).getLhcYear().intValue(),infos.get(i).getLhcYearLimit().intValue())){
						maxLimit = infos.get(i).getLhcYearLimit().intValue() - DIFF_VALUE;
						struc.setLhcYear(new BigDecimal(maxLimit));
					} else {
						if(infos.get(i).getLhcYear().intValue() < 0){
							struc.setLhcYear(new BigDecimal(0));
						} else {
							struc.setLhcYear(infos.get(i).getLhcYear());
						}
					}
					if(null == infos.get(i).getLhcColor() || checkRetValue(infos.get(i).getLhcColor().intValue(),infos.get(i).getLhcColorLimit().intValue())){
						maxLimit = infos.get(i).getLhcColorLimit().intValue() - DIFF_VALUE;
						struc.setLhcColor(new BigDecimal(maxLimit));
					} else {
						if(infos.get(i).getLhcColor().intValue() < 0){
							struc.setLhcColor(new BigDecimal(0));
						} else {
							struc.setLhcColor(infos.get(i).getLhcColor());
						}
					}
					if(null == infos.get(i).getSbThreeoneRet() || checkRetValue(infos.get(i).getSbThreeoneRet().intValue(),infos.get(i).getSbThreeoneRetLimit().intValue())){
						maxLimit = infos.get(i).getSbThreeoneRetLimit().intValue() - DIFF_VALUE;
						struc.setSbThreeoneRet(new BigDecimal(maxLimit));
					} else {
						if(infos.get(i).getSbThreeoneRet().intValue() < 0){
							struc.setSbThreeoneRet(new BigDecimal(0));
						} else {
							struc.setSbThreeoneRet(infos.get(i).getSbThreeoneRet());
						}
					}
					if(null == infos.get(i).getLhcFlatcode() || checkRetValue(infos.get(i).getLhcFlatcode().intValue(),infos.get(i).getLhcFlatcodeLimit().intValue())){
						maxLimit = infos.get(i).getLhcFlatcodeLimit().intValue() - DIFF_VALUE;
						struc.setLhcFlatcode(new BigDecimal(maxLimit));
					} else {
						if(infos.get(i).getLhcFlatcode().intValue() < 0){
							struc.setLhcFlatcode(new BigDecimal(0));
						} else {
							struc.setLhcFlatcode(infos.get(i).getLhcFlatcode());
						}
					}
					if(null == infos.get(i).getLhcHalfwave() || checkRetValue(infos.get(i).getLhcHalfwave().intValue(),infos.get(i).getLhcHalfwaveLimit().intValue())){
						maxLimit = infos.get(i).getLhcHalfwaveLimit().intValue() - DIFF_VALUE;
						struc.setLhcHalfwave(new BigDecimal(maxLimit));
					} else {
						if(infos.get(i).getLhcHalfwave().intValue() < 0){
							struc.setLhcHalfwave(new BigDecimal(0));
						} else {
							struc.setLhcHalfwave(infos.get(i).getLhcHalfwave());
						}
					}
					if(null == infos.get(i).getLhcNotin() || checkRetValue(infos.get(i).getLhcNotin().intValue(),infos.get(i).getLhcNotinLimit().intValue())){
						maxLimit = infos.get(i).getLhcNotinLimit().intValue() - DIFF_VALUE;
						struc.setLhcNotin(new BigDecimal(maxLimit));
					} else {
						if(infos.get(i).getLhcNotin().intValue() < 0){
							struc.setLhcNotin(new BigDecimal(0));
						} else {
							struc.setLhcNotin(infos.get(i).getLhcNotin());
						}
					}
					if(null == infos.get(i).getLhcOneyear() || checkRetValue(infos.get(i).getLhcOneyear().intValue(),infos.get(i).getLhcOneyearLimit().intValue())){
						maxLimit = infos.get(i).getLhcOneyearLimit().intValue() - DIFF_VALUE;
						struc.setLhcOneyear(new BigDecimal(maxLimit));
					} else {
						if(infos.get(i).getLhcOneyear().intValue() < 0){
							struc.setLhcOneyear(new BigDecimal(0));
						} else {
							struc.setLhcOneyear(infos.get(i).getLhcOneyear());
						}
					}
					if(null == infos.get(i).getLhcContinuein23() || checkRetValue(infos.get(i).getLhcContinuein23().intValue(),infos.get(i).getLhcContinuein23Limit().intValue())){
						maxLimit = infos.get(i).getLhcContinuein23Limit().intValue() - DIFF_VALUE;
						struc.setLhcContinuein23(new BigDecimal(maxLimit));
					} else {
						if(infos.get(i).getLhcContinuein23().intValue() < 0){
							struc.setLhcContinuein23(new BigDecimal(0));
						} else {
							struc.setLhcContinuein23(infos.get(i).getLhcContinuein23());
						}
					}
					if(null == infos.get(i).getLhcContinuein4() || checkRetValue(infos.get(i).getLhcContinuein4().intValue(),infos.get(i).getLhcContinuein4Limit().intValue())){
						maxLimit = infos.get(i).getLhcContinuein4Limit().intValue() - DIFF_VALUE;
						struc.setLhcContinuein4(new BigDecimal(maxLimit));
					} else {
						if(infos.get(i).getLhcContinuein4().intValue() < 0){
							struc.setLhcContinuein4(new BigDecimal(0));
						} else {
							struc.setLhcContinuein4(infos.get(i).getLhcContinuein4());
						}
					}
					if(null == infos.get(i).getLhcContinuein5() || checkRetValue(infos.get(i).getLhcContinuein5().intValue(),infos.get(i).getLhcContinuein5Limit().intValue())){
						maxLimit = infos.get(i).getLhcContinuein5Limit().intValue() - DIFF_VALUE;
						struc.setLhcContinuein5(new BigDecimal(maxLimit));
					} else {
						if(infos.get(i).getLhcContinuein5().intValue() < 0){
							struc.setLhcContinuein5(new BigDecimal(0));
						} else {
							struc.setLhcContinuein5(infos.get(i).getLhcContinuein5());
						}
					}
					if(null == infos.get(i).getLhcContinuenotin23() || checkRetValue(infos.get(i).getLhcContinuenotin23().intValue(),infos.get(i).getLhcContinuenotin23Limit().intValue())){
						maxLimit = infos.get(i).getLhcContinuenotin23Limit().intValue() - DIFF_VALUE;
						struc.setLhcContinuenotin23(new BigDecimal(maxLimit));
					} else {
						if(infos.get(i).getLhcContinuenotin23().intValue() < 0){
							struc.setLhcContinuenotin23(new BigDecimal(0));
						} else {
							struc.setLhcContinuenotin23(infos.get(i).getLhcContinuenotin23());
						}
					}
					if(null == infos.get(i).getLhcContinuenotin4() || checkRetValue(infos.get(i).getLhcContinuenotin4().intValue(),infos.get(i).getLhcContinuenotin4Limit().intValue())){
						maxLimit = infos.get(i).getLhcContinuenotin4Limit().intValue() - DIFF_VALUE;
						struc.setLhcContinuenotin4(new BigDecimal(maxLimit));
					} else {
						if(infos.get(i).getLhcContinuenotin4().intValue() < 0){
							struc.setLhcContinuenotin4(new BigDecimal(0));
						} else {
							struc.setLhcContinuenotin4(infos.get(i).getLhcContinuenotin4());
						}
					}
					if(null == infos.get(i).getLhcContinuenotin5() || checkRetValue(infos.get(i).getLhcContinuenotin5().intValue(),infos.get(i).getLhcContinuenotin5Limit().intValue())){
						maxLimit = infos.get(i).getLhcContinuenotin5Limit().intValue() - DIFF_VALUE;
						struc.setLhcContinuenotin5(new BigDecimal(maxLimit));
					} else {
						if(infos.get(i).getLhcContinuenotin5().intValue() < 0){
							struc.setLhcContinuenotin5(new BigDecimal(0));
						} else {
							struc.setLhcContinuenotin5(infos.get(i).getLhcContinuenotin5());
						}
					}
					if(null == infos.get(i).getLhcContinuecode() || checkRetValue(infos.get(i).getLhcContinuecode().intValue(),infos.get(i).getLhcContinuecodeLimit().intValue())){
						maxLimit = infos.get(i).getLhcContinuecodeLimit().intValue() - DIFF_VALUE;
						struc.setLhcContinuecode(new BigDecimal(maxLimit));
					} else {
						if(infos.get(i).getLhcContinuecode().intValue() < 0){
							struc.setLhcContinuecode(new BigDecimal(0));
						} else {
							struc.setLhcContinuecode(infos.get(i).getLhcContinuecode());
						}
					}
					
					struc.setDirectLimitRet(infos.get(i).getDirectLimitRet());
					struc.setThreeLimitRet(infos.get(i).getThreeLimitRet());
					struc.setLhcYearLimit(infos.get(i).getLhcYearLimit());
					struc.setLhcColorLimit(infos.get(i).getLhcColorLimit());
					struc.setSbThreeoneRetLimit(infos.get(i).getSbThreeoneRetLimit());
					struc.setLhcFlatcodeLimit(infos.get(i).getLhcFlatcodeLimit());
					struc.setLhcHalfwaveLimit(infos.get(i).getLhcHalfwaveLimit());
					struc.setLhcNotinLimit(infos.get(i).getLhcNotinLimit());
					struc.setLhcOneyearLimit(infos.get(i).getLhcOneyearLimit());
					struc.setLhcContinuein23Limit(infos.get(i).getLhcContinuein23Limit());
					struc.setLhcContinuein4Limit(infos.get(i).getLhcContinuein4Limit());
					struc.setLhcContinuein5Limit(infos.get(i).getLhcContinuein5Limit());
					struc.setLhcContinuenotin23Limit(infos.get(i).getLhcContinuenotin23Limit());
					struc.setLhcContinuenotin4Limit(infos.get(i).getLhcContinuenotin4Limit());
					struc.setLhcContinuenotin5Limit(infos.get(i).getLhcContinuenotin5Limit());
					struc.setLhcContinuecodeLimit(infos.get(i).getLhcContinuecodeLimit());
					struc.setStatus(new BigDecimal(1));
					strucAry[i] = struc;
				}
				if(infos.get(i).getLotteryId() != null){
					lotIdsList.add(infos.get(i).getLotteryId());
				}
				if(infos.get(i).getAwardGroupId() != null){
					awardList.add(Long.parseLong(infos.get(i).getAwardGroupId()));
				}
			}
			//每个彩种至少选一个奖金组
			int checkResult =1;// checkLotSelect(ut.getUserId() ,ut.getUserName(),agentType ,lotIdsList, awardList);
			log.info("checkResult : " + checkResult);
			if( checkResult < 0){
				switch(checkResult){
				case -1:
					response.getHead().setStatus(109783L);
					return response;
					
				case -2:
					response.getHead().setStatus(109780L);
					return response;
				case -3:
					throw new Exception("checkLotSelect error!");
				}
			}
			
			log.debug("strucAry length : " + strucAry.length);
			datas.setUserawardListStruc(strucAry);

			httpClient.invokeHttp(firefrogUrl+ doRetSetting, datas,new Pager(0,1000),1373718L, "AAA", new TypeReference<Response<UserUrl>>() {
			});
			
			StatusResponse result = new StatusResponse();
			result.setStatus("success");
			response.setResult(result);
		} catch(Exception e){
			log.error("doRetSetting error: ", e);
			response.getHead().setStatus(901000L);		
		}
		return response;
	}
	
	private int checkLotSelect(Long userId,String userName, Integer agentType,List<Long> lotIdsList,List<Long> awardIdList){
		int result = 0;
		GameGroupReq data = new GameGroupReq();
		data.setUserId(userId);
		data.setType(agentType.intValue()==0?0L:1L);
		data.setAwardType(0L);
		try{
			Map<Long,Long> map = new HashMap<Long,Long>();
			log.debug("--------------------before check---------------------");
			Response<GameGroups> urlResponse = httpClient.invokeHttp(firefrogUrl+ initCreateUrl, data,new Pager(0,1000),userId, userName, new TypeReference<Response<GameGroups>>() {
			});
			GameGroups groups = urlResponse.getBody().getResult();
			GameGroup[] ary = groups.getUserAwardListStruc();
			log.debug("lotIdsList.size() : " + lotIdsList.size());
			log.debug("ary.length : " + ary.length);
			for(GameGroup group:ary){
				if(!map.containsKey(group.getAwardGroupId()))
					map.put(group.getAwardGroupId(),group.getLotteryId());
			}
			
			for(Long lotId:lotIdsList){
				if(!map.containsValue(lotId)){
					return -1;
				}
			}
	
			for(Long gid:awardIdList){
				for(int i=0;i<map.size();i++) {
					if(!map.containsKey(gid)){
						return -2;
					}
				}
			}
		}catch(Exception e){
			log.error("get url data error:" + e);
			result = -3;
		}
		return result;
	}
	
	private boolean checkSetValue( float setValue,long directLimitRet,long threeLimitRet){
		boolean isOver = false;
		log.debug("---------checkSetValue---------");
		log.debug("setValue : " + setValue);
		log.debug("directLimitRet : " + directLimitRet);
		log.debug("threeLimitRet : " + threeLimitRet);
		if(setValue*PRIZE_UNIT != 0 && (directLimitRet/PRIZE_UNIT*100 < setValue*100 +DIFF_VALUE 
				|| threeLimitRet/PRIZE_UNIT*100 <  setValue*100 +DIFF_VALUE)){
			isOver = true;
		}
		log.debug("isOver : " + isOver);
		return isOver;
	}
	
	private boolean checkRetValue(long setRet, long limitRet){
		boolean isRetOver = false;
		log.debug("---------checkRetValue---------");
		log.debug("setRet : " + setRet);
		log.debug("limitRet : " + limitRet);
		log.debug("-------------------------------------");
		log.debug("setRet/PRIZE_UNIT : " + setRet/PRIZE_UNIT);
		log.debug("setRet/PRIZE_UNIT : " + DIFF_VALUE);
		long result1 = limitRet;
		log.debug("result1 : " + result1);
		long result2 = (setRet+DIFF_VALUE);
		log.debug("after + result2 : " + result2);
		if(setRet != 0 && result1 < result2){
			isRetOver = true;
		}
		return isRetOver;
	}
	
	@ResponseBody
	@RequestMapping("/getNBEntryInfo")
	public Response<FrontResponse> getNBEntryInfo(@RequestBody Request<FrontRequest> request) throws Exception{
		Response<FrontResponse> response = new Response<FrontResponse>(request);
		String token = request.getHead().getSessionId();
		try {
						
			String account = getUserNameByToken(token);
			if(null == account){
				response.getHead().setStatus(7L);
				return response;
			}
			UserToken ut = getUserToken(account);
			// test code
//			UserToken ut = new UserToken();
//			ut.setUserName("testnewbie1");
//			ut.setUserId(1302228L);
			
			// gotoNewMisssion
			BeginMissionRequest bmReq = new BeginMissionRequest();
			bmReq.setUserId(ut.getUserId());
			Response<BeginMissionResponse> newMisisonRes = httpClient.invokeHttp(firefrogUrl + gotoNewMissionUrl,bmReq,new TypeReference<Response<BeginMissionResponse>>() {
			});
			
			FrontResponse result = new FrontResponse();
			result.setIsTrain("Y".equals(newMisisonRes.getBody().getResult().getIsNewMission())?true : false);
			response.setResult(result);
	            
		} catch (Exception e) {
			log.error("getUserBalance error:", e);
			response.getHead().setStatus(901000L);
			
		}
		return response;
	}

}





