package com.winterframework.firefrog.subsys.web.controller;

import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.winterframework.firefrog.common.annotation.ValidRequestBody;
import com.winterframework.firefrog.common.event.FireFrogEventPublisher;
import com.winterframework.firefrog.common.redis.RedisClient2;
import com.winterframework.firefrog.fund.dao.vo.FundChangeLog;
import com.winterframework.firefrog.fund.entity.FundTransferOrder;
import com.winterframework.firefrog.fund.entity.UserFund;
import com.winterframework.firefrog.fund.exception.FundChangedException;
import com.winterframework.firefrog.fund.service.IFundAtomicOperationService;
import com.winterframework.firefrog.fund.service.IFundChangeLogService;
import com.winterframework.firefrog.fund.service.IFundTransferService;
import com.winterframework.firefrog.fund.web.controller.charge.FundLogReq;
import com.winterframework.firefrog.fund.web.dto.DTOConverter;
import com.winterframework.firefrog.fund.web.dto.FundChangeLogSubResponse;
import com.winterframework.firefrog.fund.web.dto.FundTransferSubResponse;
import com.winterframework.firefrog.subsys.dao.ISubSystemActivityLogDao;
import com.winterframework.firefrog.subsys.service.ISubSysService;
import com.winterframework.firefrog.subsys.vo.SubSystemActivityLog;
import com.winterframework.firefrog.subsys.web.dto.FhxUserTokenMdoel;
import com.winterframework.firefrog.subsys.web.dto.SubSysActivityGiftRequestDTO;
import com.winterframework.firefrog.subsys.web.dto.SubSysActivityGiftResponseDTO;
import com.winterframework.firefrog.subsys.web.dto.SubSysFundChangeLogRequestDTO;
import com.winterframework.firefrog.subsys.web.dto.SubSysFundRequestDTO;
import com.winterframework.firefrog.subsys.web.dto.SubSysFundResponse;
import com.winterframework.firefrog.subsys.web.dto.SubSysTransferRequestDTO;
import com.winterframework.firefrog.subsys.web.dto.SubSysUserCustomer;
import com.winterframework.firefrog.subsys.web.dto.SubSysUserStrucResponse;
import com.winterframework.firefrog.user.entity.GameAwardUserGroupVo;
import com.winterframework.firefrog.user.entity.User;
import com.winterframework.firefrog.user.entity.UserFreezeInfo;
import com.winterframework.firefrog.user.event.LoginEvent;
import com.winterframework.firefrog.user.exception.LoginServiceException;
import com.winterframework.firefrog.user.service.ILoginService;
import com.winterframework.firefrog.user.service.IUserProfileService;
import com.winterframework.firefrog.user.web.dto.ControllerDTOConverter;
import com.winterframework.firefrog.user.web.dto.UserLoginRequestDTO;
import com.winterframework.modules.web.jsonresult.Request;
import com.winterframework.modules.web.jsonresult.Response;
import com.winterframework.modules.web.util.JsonMapper;

/** 
* @ClassName: VersionController 
* @Description: 配置文件处理控制器 
* @author david
* @date 2013-7-4 下午5:52:42 
*  
*/
@Controller("subsystemController")
@RequestMapping(value = "/subsys")
public class SubSystemController {

	private static final Logger log = LoggerFactory.getLogger(SubSystemController.class);
	
	@Resource(name = "loginServiceImpl")
	private ILoginService loginService;
	
	@Resource(name = "fireFrogEventPublisher")
	FireFrogEventPublisher fireFrogEventPublisher;
	
	@Resource(name = "fundTransferServiceImpl")
	private IFundTransferService fundTransferService;
	
	@Resource(name = "fundChangeLogService")
	private IFundChangeLogService fundChange;
	
	@Resource(name ="userProfileServiceImpl")
	private IUserProfileService userProfileService;
	
	@Resource(name ="fundChangeServiceImpl")
	private IFundAtomicOperationService fundChangeServiceImpl;
	
	@Resource(name ="subSysServiceImpl")
	private ISubSysService subSysServiceImpl;
	
	@Resource(name = "SubSystemActivityLogDaoImpl")
	private ISubSystemActivityLogDao subSystemActivityLogDaoImpl;
	
	@Resource(name = "RedisClient2")
	private RedisClient2 redisSerive;
	
	private JsonMapper jmapper = JsonMapper.nonEmptyMapper();
	
	@RequestMapping(value = "/getUserInfo")
	@ResponseBody
	public Response<SubSysUserStrucResponse> login(@RequestBody @ValidRequestBody Request<UserLoginRequestDTO> request)
		throws Exception {
		//將有中文字的用戶做URLDecode
		String account = URLDecoder.decode(StringUtils.trimToEmpty(request.getBody().getParam().getAccount()), "UTF-8");
		String passwd = request.getBody().getParam().getPasswd();
		Integer isNewUser = request.getBody().getParam().getIsNewUser();
		Long loginIp = request.getBody().getParam().getLoginIp();
		String token = request.getBody().getParam().getToken();
		
		log.info("=account:"+account);
		log.info("=passwd:"+passwd);
		log.info("=isNewUser:"+isNewUser);
		log.info("=loginIp:"+loginIp);
		log.info("=token:"+token);
		
		
		Response<SubSysUserStrucResponse> response = new Response<SubSysUserStrucResponse>(request);
		
		try {
			
			if(token != null && !token.trim().equals("")){
				String jsonString = redisSerive.get(token);
				
				log.info("=jsonString:"+jsonString);
				
				if(jsonString != null){
					FhxUserTokenMdoel userLogin = jmapper.fromJson(jsonString, FhxUserTokenMdoel.class);
					
					if(userLogin != null){
						String tokenAccount = userLogin.getDatas().getInfo().getAccount();
						passwd = userLogin.getDatas().getInfo().getPasswd();
						log.info("=tokenAccount:"+tokenAccount);
						log.info("=passwd:"+passwd);
						
						if(!tokenAccount.equals(account)){
							log.error("TOKEN帐号与登入帐号不符");
							SubSysUserStrucResponse result = new SubSysUserStrucResponse();
							result.setErrMsg("TOKEN帐号与登入帐号不符");
							result.setStatus(2);
							response.setResult(result);
							return response;
						}
						
					}else{
						log.error("FhxUserTokenMdoel转化为NULL");
						SubSysUserStrucResponse result = new SubSysUserStrucResponse();
						result.setErrMsg("FhxUserTokenMdoel转化为NULL");
						result.setStatus(2);
						response.setResult(result);
						return response;
					}
				}else{
					log.error("");
					SubSysUserStrucResponse result = new SubSysUserStrucResponse();
					result.setErrMsg("TOKEN不存在于REDIS");
					result.setStatus(2);
					response.setResult(result);
					return response;
				}
			}else{
				if(account == null || passwd == null){
					log.error("帐号密码不得为空");
					SubSysUserStrucResponse result = new SubSysUserStrucResponse();
					result.setErrMsg("帐号密码不得为空");
					result.setStatus(2);
					response.setResult(result);
					return response;
				}
			}
			
			User user = userProfileService.queryUserByName(account);
			if(user == null){
				//帐号不存在
				log.error("帐号不存在");
				SubSysUserStrucResponse result = new SubSysUserStrucResponse();
				result.setErrMsg("帐号不存在");
				result.setStatus(2);
				response.setResult(result);
				return response;
			}
			if(!passwd.equals(user.getUserProfile().getPassword())){
				//密碼錯誤
				log.error("密碼錯誤");
				SubSysUserStrucResponse result = new SubSysUserStrucResponse();
				result.setErrMsg("密碼錯誤");
				result.setStatus(2);
				response.setResult(result);
				return response;
			}
			//获取该客户对应的资金信息
			LoginEvent loginEvent = new LoginEvent(user.getId());
			fireFrogEventPublisher.publish(loginEvent);
			user.setFund(loginEvent.getUserFund());

			log.debug("生成 sub   userStruc");
			SubSysUserStrucResponse res = ControllerDTOConverter.user2UserSubStrucResponse(user);
			
			res = subSysServiceImpl.getNewUserBankList(isNewUser, res);
			
			log.info("res.getWithdrawList().size() : " + res.getWithdrawList().size());
			res.setStatus(1);
			
			response.setResult(res);
			log.debug("生成sub userStruc结束");
		} catch (LoginServiceException e) {
			log.error("处理登录错误异常", e);
			SubSysUserStrucResponse result = new SubSysUserStrucResponse();
			result.setErrMsg("处理登录错误异常");
			result.setStatus(0);
			response.setResult(result);
		} catch (Exception e) {
			log.error("login error.", e);
			SubSysUserStrucResponse result = new SubSysUserStrucResponse();
			result.setErrMsg("login error");
			result.setStatus(0);
			response.setResult(result);
			throw e;
		}

		return response;
	}

	/** 
	* @Title: fundTransfer 
	* @Description: 用户转账
	* @param request
	* @return
	* @throws Exception
	*/
	@RequestMapping(value = "/fundTransfer")
	@ResponseBody
	public Response<FundTransferSubResponse>  fundTransferSubSystem(@RequestBody @ValidRequestBody Request<SubSysTransferRequestDTO> request) throws Exception {
		@SuppressWarnings("rawtypes")
		Response<FundTransferSubResponse> response = new Response<FundTransferSubResponse>(request);
		log.info("/subsys/fundTransfer start. SN="+request.getBody().getParam().getSn() + ",applTime="+request.getBody().getParam().getApplyTime()
				+",token="+request.getBody().getParam().getToken()+",reason="+request.getBody().getParam().getDirection()
				+",amountBal="+request.getBody().getParam().getAmountBal());
		
		try {
			
			if(request.getBody().getParam().getSn() == null){
				FundTransferSubResponse result = new FundTransferSubResponse ();
				result.setErrMsg("SN为NULL");
				result.setStatus(1);
				response.setResult(result);
				log.error("子系统传入SN为NULL");
				return response;
			}
			
			if(!subSysServiceImpl.validateToken(request.getBody().getParam().getToken())){
				FundTransferSubResponse result = new FundTransferSubResponse();
				result.setStatus(1);
				result.setErrMsg("token错误");
				response.setResult(result);
				log.error("token错误");
				return response;
			}
			
			
			FundLogReq fundLogReq = new FundLogReq();
			fundLogReq.setSn(request.getBody().getParam().getSn());
			fundLogReq.setUserId(request.getBody().getParam().getUserId());
			FundChangeLog changeLog = fundChange.getChangeLogForSub(fundLogReq);
			
			Integer status = 0;
			if(changeLog.getStatus().equals("1")){
				status = 1;
			}
			if(status == 1){
				FundTransferSubResponse result = new FundTransferSubResponse();
				result.setStatus(1);
				result.setErrMsg("SN重复");
				response.setResult(result);
				log.error("SN重复");
				return response;
			}
			
			FundTransferOrder fundTransferOrder = DTOConverter.fundTransferRequestDTOSubSystem(request, request.getBody().getParam().getDirection());
			
			fundTransferOrder.setSn(request.getBody().getParam().getSn());
			if(fundTransferOrder.getApplyUser().getId() == null){
				
				User uid =  userProfileService.queryUserByName(request.getBody().getParam().getRcvAccount());
				
				if(uid != null){
					UserFreezeInfo ufi = uid.getUserFreeze();
					if((ufi != null && ufi.isFreeze()) && request.getBody().getParam().getDirection() != 9){
						FundTransferSubResponse result = new FundTransferSubResponse ();
						result.setErrMsg("帐号已被冻结");
						result.setStatus(4);
						response.setResult(result);
						log.error("帐号已被冻结");
						return response;
					}
					fundTransferOrder.getApplyUser().setId(uid.getId());
				}else {
					FundTransferSubResponse result = new FundTransferSubResponse ();
					result.setErrMsg("帐号有误");
					result.setStatus(1);
					response.setResult(result);
					log.error("查询帐号有误");
					return response;
				}
			}
			
			UserFund userFund = fundChangeServiceImpl.getUserFund(fundTransferOrder.getApplyUser().getId());
			
			int direction = request.getBody().getParam().getDirection();
			
			if((direction == 6 || direction == 0) && userFund.getBal() < request.getBody().getParam().getTransferAmt()){
				FundTransferSubResponse result = new FundTransferSubResponse ();
				result.setErrMsg("宝开彩票余额不足");
				result.setStatus(1);
				response.setResult(result);
				log.error("宝开彩票余额不足");
				return response;
			}
			
			Long balance = fundTransferService.transferSubsystemFundMW (fundTransferOrder, request.getBody().getParam().getDirection(), request.getBody().getParam().getToken());
			
			FundTransferSubResponse result = new FundTransferSubResponse ();
			result.setAmount( fundTransferOrder.getTransferAmt() );
			result.setSn(fundTransferOrder.getSn());
			result.setUserId(fundTransferOrder.getApplyUser().getId());
			result.setBalance( balance );
			result.setStatus(0);
			response.setResult(result);
			
		} catch (FundChangedException e) {
			FundTransferSubResponse result = new FundTransferSubResponse ();
			result.setErrMsg("子系統转账时保存资金，资金记录发生变动");
			result.setStatus(1);
			response.setResult(result);
			log.error("子系統转账时保存资金，资金记录发生变动", e);
		} catch (Exception e) {
			log.error("save subsys/fundTransfer error.", e);
			FundTransferSubResponse result = new FundTransferSubResponse ();
			result.setErrMsg("子系統转账时发生異常");
			result.setStatus(1);
			response.setResult(result);
			throw e;
		}
		log.info("/subsys/fundTransfer end. SN="+request.getBody().getParam().getSn() + ",applTime="+request.getBody().getParam().getApplyTime()
				+",token="+request.getBody().getParam().getToken());
		return response;
	}
	
	
	/** 
	* @Title: checktrancition 
	* @Description: 查詢交易
	* @param request
	* @return
	* @throws Exception
	*/
	@RequestMapping(value = "/checktrancition")
	@ResponseBody
	public Response<FundChangeLogSubResponse>  checktrancition(@RequestBody @ValidRequestBody Request<SubSysFundChangeLogRequestDTO> request) throws Exception {
		@SuppressWarnings("rawtypes")
		Response<FundChangeLogSubResponse> response = new Response<FundChangeLogSubResponse>(request);
		log.info("/subsys/checktrancition start. SN="+request.getBody().getParam().getSn() +",token="+request.getBody().getParam().getToken());
		try {
			if(request.getBody().getParam().getSn() == null){
				FundChangeLogSubResponse result = new FundChangeLogSubResponse ();
				result.setStatus(1);
				result.setErrMsg("SN 為NULL");
				response.setResult(result);
				log.error("子系統傳入SN為NULL");
				return response;
			}
			
			if(!subSysServiceImpl.validateToken(request.getBody().getParam().getToken())){
				FundChangeLogSubResponse result = new FundChangeLogSubResponse();
				result.setStatus(1);
				result.setErrMsg("token錯誤");
				response.setResult(result);
				log.error("token錯誤");
				return response;
			}
			
			FundLogReq fundLogReq = DTOConverter.fundChangeRequestDTOSubSystem(request);
			
			FundChangeLog changeLog = fundChange.getChangeLogForSub(fundLogReq);
			Integer status = 0;
			if(changeLog.getStatus().equals("1")){
				status = 1;
			}
			FundChangeLogSubResponse result = new FundChangeLogSubResponse ();
			result.setStatus(status);
			result.setBalance(changeLog.getCtBal());
			response.setResult(result);
			
		} catch (Exception e) {
			log.error("save subsys/fundTransfer error.", e);
			FundChangeLogSubResponse result = new FundChangeLogSubResponse ();
			result.setErrMsg("子系統转账时发生異常");
			result.setStatus(1);
			response.setResult(result);
			throw e;
		}
		log.info("/subsys/checktrancition end. SN="+request.getBody().getParam().getSn() +",token="+request.getBody().getParam().getToken() );
		return response;
	}
	
	/** 
	* @Title: getFundBal 
	* @Description: 查詢餘額
	* @param request
	* @return
	* @throws Exception
	*/
	@RequestMapping(value = "/getFundBal")
	@ResponseBody
	public Response<SubSysFundResponse>  getFundBal(@RequestBody @ValidRequestBody Request<SubSysFundRequestDTO> request) throws Exception {
		@SuppressWarnings("rawtypes")
		Response<SubSysFundResponse> response = new Response<SubSysFundResponse>(request);
		log.info("/subsys/getFundBal start. account="+request.getBody().getParam().getAccount() +",token="+request.getBody().getParam().getToken());
		try {
			if(request.getBody().getParam().getAccount() == null){
				SubSysFundResponse result = new SubSysFundResponse ();
				result.setStatus(1);
				result.setErrMsg("account 為NULL");
				response.setResult(result);
				log.error("子系統傳入account為NULL");
				return response;
			}
			
			if(!subSysServiceImpl.validateToken(request.getBody().getParam().getToken())){
				SubSysFundResponse result = new SubSysFundResponse();
				result.setStatus(1);
				result.setErrMsg("token錯誤");
				response.setResult(result);
				log.error("token錯誤");
				return response;
			}
			
			//FundLogReq fundLogReq = DTOConverter.fundChangeRequestDTOSubSystem(request);
			UserFund userFund = new UserFund();
			SubSysFundResponse result = new SubSysFundResponse ();
			if(request.getBody().getParam().getAccount() != null){
				User uid =  userProfileService.queryUserByName(request.getBody().getParam().getAccount());
				if(uid != null){
					result.setId(uid.getId());
					userFund = fundChangeServiceImpl.getUserFund(uid.getId());
				}else {
					result.setErrMsg("帳號有誤");
					result.setStatus(1);
					response.setResult(result);
					log.error("查詢帳號有誤");
					return response;
				}
			}
			
			
			result.setBal( userFund.getBal() );
			log.info("/subsys/getFundBal result.bal="+result.getBal());
			result.setStatus(0);
			response.setResult(result);
			
		} catch (Exception e) {
			log.error("save subsys/getFundBal error.", e);
			SubSysFundResponse result = new SubSysFundResponse ();
			result.setStatus(1);
			response.setResult(result);
			throw e;
		}
		log.info("/subsys/getFundBal end. accunt="+request.getBody().getParam().getAccount() +",token="+request.getBody().getParam().getToken() );
		return response;
	}
	
	
	/** 
	* @Title: getUser 
	* @Description: 查詢使用者資訊
	* @param request
	* @return
	* @throws Exception
	*/
	@RequestMapping(value = "/getUser")
	@ResponseBody
	public Response<SubSysUserStrucResponse>  getUser(@RequestBody @ValidRequestBody Request<SubSysFundRequestDTO> request) throws Exception {
		@SuppressWarnings("rawtypes")
		Response<SubSysUserStrucResponse> response = new Response<SubSysUserStrucResponse>(request);
		log.info("/subsys/getUser start. account="+request.getBody().getParam().getAccount() +",token="+request.getBody().getParam().getToken());
		try {
			if(request.getBody().getParam().getAccount() == null){
				SubSysUserStrucResponse result = new SubSysUserStrucResponse ();
				result.setStatus(1);
				result.setErrMsg("account 為NULL");
				response.setResult(result);
				log.error("子系統傳入account為NULL");
				return response;
			}
			
			if(!subSysServiceImpl.validateToken(request.getBody().getParam().getToken())){
				SubSysUserStrucResponse result = new SubSysUserStrucResponse ();
				result.setStatus(1);
				result.setErrMsg("token錯誤");
				response.setResult(result);
				log.error("token錯誤");
				return response;
			}
			
			List<User> user =  userProfileService.queryUserParent(request.getBody().getParam().getAccount());
			List<SubSysUserCustomer> userList = new ArrayList<SubSysUserCustomer>();
			for(User v : user){
				SubSysUserCustomer uc = new SubSysUserCustomer();
				uc.setAccount(v.getAccount());
				uc.setUserChain(v.getUserProfile().getUserChain());
				uc.setUserLvl(v.getUserProfile().getUserLvl());
				uc.setUserId(v.getId());
				uc.setParentId(v.getParent().getId());
				List<GameAwardUserGroupVo> userAward = subSysServiceImpl.queryUserAward(v.getId(),request.getBody().getParam().getLotteryId());
				for(GameAwardUserGroupVo x : userAward){					
					uc.setLotteryId(x.getLotteryId());
					uc.setDirectRet(x.getDirectRet());
					uc.setThreeoneRet(x.getThreeoneRet());
					uc.setSetType(x.getSetType());
					uc.setStatus(x.getStatus());
					uc.setBetType(x.getBetType());
					uc.setSysAwardGroupId(x.getSysGroupAwardId());
				}
				userList.add(uc);
			}
			
			SubSysUserStrucResponse result = new SubSysUserStrucResponse();
			
			result.setUser(userList);
			
			result.setStatus(0);
			response.setResult(result);
			
		} catch (Exception e) {
			log.error("save subsys/getUser error.", e);
			SubSysUserStrucResponse result = new SubSysUserStrucResponse ();
			result.setStatus(1);
			result.setErrMsg("查詢使用者資訊錯誤");
			response.setResult(result);
			throw e;
		}
		log.info("/subsys/getUser end. accunt="+request.getBody().getParam().getAccount() +",token="+request.getBody().getParam().getToken());
		return response;
	}
	
	/** 
	* @Title: activityGifts 
	* @Description: 活動禮金
	* @param request
	* @return
	* @throws Exception
	*/
	@RequestMapping(value = "/activityGifts")
	@ResponseBody
	@Transactional(rollbackFor = Exception.class)
	public Response<SubSysActivityGiftResponseDTO> activityGifts(@RequestBody @ValidRequestBody Request<SubSysActivityGiftRequestDTO> request) throws Exception {
		@SuppressWarnings("rawtypes")
		Response<SubSysActivityGiftResponseDTO> response = new Response<SubSysActivityGiftResponseDTO>(request);
		log.info("/subsys/activityGifts start. Account="+request.getBody().getParam().getAccount() +",token="+request.getBody().getParam().getToken());
		
		try {
			
			if(!subSysServiceImpl.validateToken(request.getBody().getParam().getToken())){
				SubSysActivityGiftResponseDTO result = new SubSysActivityGiftResponseDTO();
				result.setStatus(0L);
				result.setErrMsg("token错误");
				response.setResult(result);
				log.error("token错误");
				return response;
			}
			if(request.getBody().getParam().getActivitySn() == null || request.getBody().getParam().getDebitSn() == null){
				SubSysActivityGiftResponseDTO result = new SubSysActivityGiftResponseDTO ();
				result.setErrMsg("SN为NULL");
				result.setStatus(0L);
				response.setResult(result);
				log.error("子系统传入SN为NULL");
				return response;
			}
			
			SubSysActivityGiftRequestDTO activityGiftRequestDTO = request.getBody().getParam();
			User uid =  userProfileService.queryUserByName(activityGiftRequestDTO.getAccount());
			
			if(uid != null){
				UserFreezeInfo ufi = uid.getUserFreeze();
				if((ufi != null && ufi.isFreeze())){
					SubSysActivityGiftResponseDTO result = new SubSysActivityGiftResponseDTO ();
					result.setErrMsg("帐号已被冻结");
					result.setStatus(4L);
					response.setResult(result);
					log.error("帐号已被冻结");
					return response;
				}
			}else {
				SubSysActivityGiftResponseDTO result = new SubSysActivityGiftResponseDTO ();
				result.setErrMsg("帐号有误");
				result.setStatus(0L);
				response.setResult(result);
				log.error("查询帐号有误");
				return response;
			}
			
			Date now = Calendar.getInstance().getTime();
			SubSystemActivityLog al = new SubSystemActivityLog();
			al.setAccount(activityGiftRequestDTO.getAccount());
			al.setAmount(activityGiftRequestDTO.getAmount());
			al.setStatus(1L);
			al.setRoundId(activityGiftRequestDTO.getRoundId());
			al.setCreateTime(now);
			al.setDebitSn(activityGiftRequestDTO.getDebitSn());
			al.setActivitySn(activityGiftRequestDTO.getActivitySn());
			if(subSystemActivityLogDaoImpl.querySubSystemActivityLogCount(al) != 0){
				SubSysActivityGiftResponseDTO result = new SubSysActivityGiftResponseDTO();
				al = new SubSystemActivityLog();
				al.setAccount(activityGiftRequestDTO.getAccount());
				al.setRoundId(activityGiftRequestDTO.getRoundId());
				SubSystemActivityLog rData = subSystemActivityLogDaoImpl.querySubSystemActivityLog(al);
				result.setStatus(2L);
				result.setErrMsg("活动已存在");
				result.setActivitySn(rData.getActivitySn());
				result.setDebitSn(rData.getDebitSn());
				result.setRoundId(activityGiftRequestDTO.getRoundId());
				result.setAmount(activityGiftRequestDTO.getAmount());
				response.setResult(result);
				log.error("活动已存在 Account="+request.getBody().getParam().getAccount());
				return response;
			}
			
			FundTransferOrder fundTransferOrder = DTOConverter.activityGiftRequestDTOSubSystem(activityGiftRequestDTO.getAccount(),activityGiftRequestDTO.getAmount(),activityGiftRequestDTO.getNote(),activityGiftRequestDTO.getActivityDirection(),activityGiftRequestDTO.getActivitySn());
			fundTransferOrder.getApplyUser().setId(uid.getId());
			fundTransferService.transferSubsystemGiftMoneyMW (fundTransferOrder, activityGiftRequestDTO.getActivityDirection(), activityGiftRequestDTO.getToken());
			subSystemActivityLogDaoImpl.saveSubSystemActivityLog(al);
			
			SubSysActivityGiftResponseDTO result = new SubSysActivityGiftResponseDTO ();
			
			FundTransferOrder fundTransferOrderToSub = DTOConverter.activityGiftRequestDTOSubSystem(activityGiftRequestDTO.getAccount(),activityGiftRequestDTO.getAmount(),"",activityGiftRequestDTO.getDebitDirection(),activityGiftRequestDTO.getDebitSn());
			fundTransferOrderToSub.getApplyUser().setId(uid.getId());
			log.info("account="+request.getBody().getParam().getAccount()+"轉出4.0="+activityGiftRequestDTO.getAmount());
			String sn = fundTransferService.transferSubsystemGiftMoneyMW(fundTransferOrder, activityGiftRequestDTO.getDebitDirection(), activityGiftRequestDTO.getToken());
			
			result.setActivitySn(activityGiftRequestDTO.getActivitySn());
			result.setDebitSn(activityGiftRequestDTO.getDebitSn());
			result.setStatus(1l);
			result.setRoundId(activityGiftRequestDTO.getRoundId());
			result.setAmount(activityGiftRequestDTO.getAmount());
			response.setResult(result);
			
		} catch (FundChangedException e) {
			SubSysActivityGiftResponseDTO result = new SubSysActivityGiftResponseDTO ();
			result.setErrMsg("子系统转账时保存资金，资金记录发生变动");
			result.setStatus(0l);
			response.setResult(result);
			log.error("子系統转账时保存资金，资金记录发生变动", e);
		} catch (Exception e) {
			log.error("save subsys/fundTransfer error.", e);
			SubSysActivityGiftResponseDTO result = new SubSysActivityGiftResponseDTO ();
			result.setErrMsg("子系統转账时发生異常");
			result.setStatus(0l);
			response.setResult(result);
			throw e;
		}
		log.info("/subsys/activityGifts end. Account="+request.getBody().getParam().getAccount() + ",token="+request.getBody().getParam().getToken());
		return response;
	}
	
}
