package com.winterframework.firefrog.user.web.controller;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.winterframework.firefrog.active.entity.ActiveUserMigrate;
import com.winterframework.firefrog.active.service.ActiveUserMigrateManager;
import com.winterframework.firefrog.beginmession.service.BeginMissionService;
import com.winterframework.firefrog.common.annotation.ValidRequestBody;
import com.winterframework.firefrog.common.annotation.ValidRequestHeader;
import com.winterframework.firefrog.common.config.service.IConfigService;
import com.winterframework.firefrog.common.httpjsonclient.IHttpClient;
import com.winterframework.firefrog.common.noticepublisher.INoticeMsgPublisher;
import com.winterframework.firefrog.common.util.DataConverterUtil;
import com.winterframework.firefrog.common.util.HttpJsonClientExt;
import com.winterframework.firefrog.common.util.IPConverter;
import com.winterframework.firefrog.common.util.PageConverterUtils;
import com.winterframework.firefrog.common.util.UserTools;
import com.winterframework.firefrog.fund.entity.UserFund;
import com.winterframework.firefrog.fund.service.IFundService;
import com.winterframework.firefrog.notice.entity.NoticeTaskEnum;
import com.winterframework.firefrog.user.dao.impl.UserCustomerDaoImpl;
import com.winterframework.firefrog.user.dao.vo.UserCustomer;
import com.winterframework.firefrog.user.dao.vo.UserPtRegister;
import com.winterframework.firefrog.user.dao.vo.UserStruc;
import com.winterframework.firefrog.user.dao.vo.VOConverter;
import com.winterframework.firefrog.user.entity.CustomerQueryDTO;
import com.winterframework.firefrog.user.entity.LoginLog;
import com.winterframework.firefrog.user.entity.QAInfo;
import com.winterframework.firefrog.user.entity.QueryGeneralAgentDTO;
import com.winterframework.firefrog.user.entity.User;
import com.winterframework.firefrog.user.entity.UserAgent;
import com.winterframework.firefrog.user.entity.UserProfile;
import com.winterframework.firefrog.user.exception.RegisterServiceException;
import com.winterframework.firefrog.user.exception.UserExistServiceException;
import com.winterframework.firefrog.user.service.IUserProfileService;
import com.winterframework.firefrog.user.service.IUserSlotExhangeService;
import com.winterframework.firefrog.user.web.controller.game.GameBonus;
import com.winterframework.firefrog.user.web.dto.ConfigLabarUrl;
import com.winterframework.firefrog.user.web.dto.ControllerDTOConverter;
import com.winterframework.firefrog.user.web.dto.CreateGeneralAgentRequest;
import com.winterframework.firefrog.user.web.dto.LoginStrucResponse;
import com.winterframework.firefrog.user.web.dto.QuStrucResponse;
import com.winterframework.firefrog.user.web.dto.QueryAgentSubUserRequestDTO;
import com.winterframework.firefrog.user.web.dto.QueryGeneralAgentRequest;
import com.winterframework.firefrog.user.web.dto.QueryUserByCriteriaRequestDTO;
import com.winterframework.firefrog.user.web.dto.QueryUserListRequestDTO;
import com.winterframework.firefrog.user.web.dto.UpdataGeneralAgentBalanceRequest;
import com.winterframework.firefrog.user.web.dto.UserActivityRegisterRequest;
import com.winterframework.firefrog.user.web.dto.UserActivityRegisterResponse;
import com.winterframework.firefrog.user.web.dto.UserBizSwitchRequest;
import com.winterframework.firefrog.user.web.dto.UserBizSwitchResponse;
import com.winterframework.firefrog.user.web.dto.UserDetailResponse;
import com.winterframework.firefrog.user.web.dto.UserPasswdAndWithdrawPasswordAndSecurityQARequest;
import com.winterframework.firefrog.user.web.dto.UserSecurityCipherRequest;
import com.winterframework.firefrog.user.web.dto.UserSecurityEmailRequest;
import com.winterframework.firefrog.user.web.dto.UserSecurityPwdRequest;
import com.winterframework.firefrog.user.web.dto.UserSecurityQARequest;
import com.winterframework.firefrog.user.web.dto.UserSecurityUsernameRequest;
import com.winterframework.firefrog.user.web.dto.UserSecurityWithdrawPwdRequest;
import com.winterframework.firefrog.user.web.dto.UserStrucResponse;
import com.winterframework.firefrog.user.web.dto.UserWithdrawPwdAndSecurityQARequest;
import com.winterframework.modules.ip.IPSeeker;
import com.winterframework.modules.page.Page;
import com.winterframework.modules.page.PageRequest;
import com.winterframework.modules.spring.exetend.PropertyConfig;
import com.winterframework.modules.web.jsonresult.Request;
import com.winterframework.modules.web.jsonresult.RequestHeader;
import com.winterframework.modules.web.jsonresult.Response;
import com.winterframework.modules.web.jsonresult.ResultPager;

@Controller("userProfileController")
@RequestMapping(value = "/user/profile")
public class UserProfileController {

	private static final Logger logger = LoggerFactory.getLogger(UserProfileController.class);
	private static IPSeeker ipseek = IPSeeker.getInstance();

	@Resource(name = "userSlotExhangeServiceImpl")
	private IUserSlotExhangeService userSlotExhangeService;	

@Resource(name ="userProfileServiceImpl")
	private IUserProfileService userProfileService;

	@Resource(name = "noticeMsgPublisher")
	private INoticeMsgPublisher msgToMQ;

	@Autowired
	private ActiveUserMigrateManager activeUserMigrateManager;

	@Resource(name = "fundChargeServiceImpl")
	private IFundService fundServiceImpl;
	
	@Resource(name = "configServiceImpl")
	private IConfigService configService;
	
	@PropertyConfig(value = "game_change_bonus")
	private String game_change_bonus;
	
	@PropertyConfig(value = "game_change_WG_bonus")
	private String game_change_WG_bonus;
	
	@PropertyConfig(value = "game_change_user_bonus")
	private String game_change_user_bonus;
	
	@Resource(name = "HttpClientImpl")
	protected IHttpClient httpClient;

	@Autowired
	private BeginMissionService beginMissionService;
	
	/**
	 * 创建用户
	 * 
	 */
	@RequestMapping(value = "/createUser")
	public @ResponseBody
	Response<UserStrucResponse> createUser(@RequestBody Request<UserCustomer> request) throws Exception {
		Response<UserStrucResponse> response = new Response<UserStrucResponse>(request);
		try {
			UserCustomer user = request.getBody().getParam();
			ConfigLabarUrl configLabarUrl = DataConverterUtil.convertJson2Object(configService.getConfigValueByKey("labar", "labarUrl"), ConfigLabarUrl.class);
			logger.info("configLabarUrl:"+configLabarUrl);
			logger.info("A:"+configLabarUrl.getUrlId() + " B:"+user.getUrlId());
			
			if(user.getUrlId().intValue() == configLabarUrl.getUrlId().intValue()){
				logger.info("createUser拉霸註冊流程");

				String cellphone = request.getBody().getParam().getCellphone();
				String activityType = request.getBody().getParam().getActivityType();
				
				if(cellphone != null && !cellphone.equals("") && activityType != null && !activityType.equals("")){
					userProfileService.createUser(user);
					User userEntity = VOConverter.customer2User(user);
					userProfileService.SendMsg (userEntity);
					
					logger.info("urlId:"+configLabarUrl.getUrlId());
					Long id = userProfileService.userByName(user.getAccount());
					userSlotExhangeService.occupancySave(id, user.getCellphone(), user.getActivityType());
					
					beginMissionService.bindMissionVer(id);
				}else{
					//throw new Exception("createUser拉霸註冊流程 error");
					response.getHead().setStatus(100002L);
				}
				
			}else{
				logger.info("createUser正常註冊流程");
				
				userProfileService.createUser(user);
				User userEntity = VOConverter.customer2User(user);
				userProfileService.SendMsg (userEntity);
				
				beginMissionService.bindMissionVer(userEntity.getId());
			}
		} catch (OverException o) {
			response.getHead().setStatus(1078L);
		} catch (SQLException e) {
			logger.error("Query uesr profile error.", e);
			response.getHead().setStatus(100002L);
		} catch (Exception e) {
			logger.error("Query uesr profile error.", e);
			response.getHead().setStatus(100002L);
		}
		return response;
	}
	
	/**
	 * PT帳號自動註冊
	 * 
	 */
	@RequestMapping(value = "/createPtUser")
	public @ResponseBody
	Response<UserStrucResponse> createPtUser(@RequestBody Request<UserCustomer> request) throws Exception {
		Response<UserStrucResponse> response = new Response<UserStrucResponse>(request);
		try {
			
			UserCustomer user = request.getBody().getParam();
			userProfileService.createUser(user);
			User userEntity = VOConverter.customer2User(user);
			userProfileService.SendMsg (userEntity);
			Long id = userProfileService.userByName(user.getAccount());
			userProfileService.createPtUser(id, user.getParentId());
			beginMissionService.bindMissionVer(id);
		} catch (RegisterServiceException e) {
			logger.error("ip repeat register");
			response.getHead().setStatus(UserCustomer.IP_LIMIT_REGISTER);
		} catch (OverException o) {
			response.getHead().setStatus(1078L);
		} catch (SQLException e) {
			logger.error("Query uesr profile error.", e);
			response.getHead().setStatus(100002L);
		} catch (Exception e) {
			logger.error("Query uesr profile error.", e);
			response.getHead().setStatus(100002L);
		}
		return response;
	}
	
	
	/**
	 * 提供PT查詢自動註冊帳號
	 * 
	 */
	@RequestMapping(value = "getPtUser")
	public @ResponseBody Response<UserActivityRegisterResponse> getPtUser(
			@RequestBody Request<UserActivityRegisterRequest> request)
			throws Exception {
		logger.error("===getPtUser Start");
		Long userId = request.getBody().getParam().getUserId();
		Long parentId = request.getBody().getParam().getParentId();
		Long type = request.getBody().getParam().getType();
		logger.error("=userId"+userId);
		logger.error("=parentId"+parentId);
		logger.error("=type"+type);
		
		Response<UserActivityRegisterResponse> response = new Response<UserActivityRegisterResponse>(request);
		try {
			UserPtRegister userPtRegister = new UserPtRegister();
			userPtRegister.setUserId(userId);
			userPtRegister.setParentId(parentId);
			userPtRegister.setType(type);
			response.setResult(userProfileService.getPtUser(userPtRegister));
		} catch (RegisterServiceException e) {
			logger.error("ip repeat register");
			response.getHead().setStatus(UserCustomer.IP_LIMIT_REGISTER);
		} catch (OverException o) {

			response.getHead().setStatus(1078L);
		} catch (SQLException e) {
			logger.error("Query uesr profile error.", e);
			response.getHead().setStatus(100002L);
		} catch (Exception e) {
			logger.error("Query uesr profile error.", e);
			response.getHead().setStatus(100002L);
		}
		return response;
	}

	@RequestMapping(value = "/changeBonus")
	public @ResponseBody
	Response<UserStrucResponse> changeBonus(@RequestBody Request<GameBonus> request) throws Exception {
		Response<UserStrucResponse> response = new Response<UserStrucResponse>(request);
		try {
			GameBonus user = request.getBody().getParam();
			Request<GameBonus> rq = new Request<GameBonus>();
			rq.setHead(new RequestHeader());
			rq.getHead().setUserId(user.getUserid());
			rq.setBody(new com.winterframework.modules.web.jsonresult.RequestBody<GameBonus>());
			rq.getBody().setParam(user);
			HttpJsonClientExt.postJsonObject(game_change_bonus, rq);
		} catch (Exception e) {
			logger.error("Query uesr profile error.", e);
			response.getHead().setStatus(100002L);
		}
		return response;
	}

	@RequestMapping(value = "/migrate")
	public @ResponseBody
	Response<Object> migrate(@RequestBody Request<ActiveUserMigrate> request) throws Exception {
		Response<Object> response = new Response<Object>(request);
		try {
			ActiveUserMigrate rq = request.body.getParam();
			ActiveUserMigrate old = activeUserMigrateManager.getById(rq.getId());
			if (old == null) {

				activeUserMigrateManager.save(request.getBody().getParam());
			} else {
				if (rq.getBetTime() != null) {
					if (old.getBetTime() == null)
					old.setBetTime(rq.getBetTime());
				}
				if (rq.getFundTime() != null) {
					if (old.getFundTime() == null)
						old.setFundTime(rq.getFundTime());
				}
				if (rq.getUpdateTime() != null) {
					if (old.getUpdateTime() == null)
						old.setUpdateTime(rq.getUpdateTime());
				}
				activeUserMigrateManager.update(old);
			}
		} catch (Exception e) {
			logger.error("Query uesr profile error.", e);
			response.getHead().setStatus(100002L);
		}
		return response;
	}
	
	@RequestMapping(value = "/migrates")
	public @ResponseBody
	Response<Object> migrates(@RequestBody Request<ActiveUserMigrate[]> request) throws Exception {
		Response<Object> response = new Response<Object>(request);
		try {
			ActiveUserMigrate[] rqs = request.body.getParam();
			for (ActiveUserMigrate rq : rqs) {

				ActiveUserMigrate old = activeUserMigrateManager.getById(rq.getId());
				if (old == null) {

					activeUserMigrateManager.save(rq);
				} else {
					if (rq.getBetTime() != null) {
						if (old.getBetTime() == null)
						old.setBetTime(rq.getBetTime());
					}
					if (rq.getFundTime() != null) {
						if (old.getFundTime() == null)
							old.setFundTime(rq.getFundTime());
					}
					if (rq.getUpdateTime() != null) {
						if (old.getUpdateTime() == null)
							old.setUpdateTime(rq.getUpdateTime());
					}
					activeUserMigrateManager.update(old);
				}
			}
		} catch (Exception e) {
			logger.error("Query uesr profile error.", e);
			response.getHead().setStatus(100002L);
		}
		return response;
	}

	@RequestMapping(value = "/migrateById")
	public @ResponseBody
	Response<ActiveUserMigrate> migrateById(@RequestBody Request<ActiveUserMigrate> request) throws Exception {
		Response<ActiveUserMigrate> response = new Response<ActiveUserMigrate>(request);
		try {
			ActiveUserMigrate rq = request.body.getParam();
			response.setResult(activeUserMigrateManager.getById(rq.getId()));
		} catch (Exception e) {
			logger.error("Query uesr profile error.", e);
			response.getHead().setStatus(100002L);
		}
		return response;
	}

	@RequestMapping(value = "/changeUserBonus")
	public @ResponseBody
	Response<UserStrucResponse> changeUserBonus(@RequestBody Request<GameBonus> request) throws Exception {
		Response<UserStrucResponse> response = new Response<UserStrucResponse>(request);
		try {
			GameBonus user = request.getBody().getParam();
			Request<GameBonus> rq = new Request<GameBonus>();
			rq.setHead(new RequestHeader());
			rq.getHead().setUserId(user.getUserid());
			rq.setBody(new com.winterframework.modules.web.jsonresult.RequestBody<GameBonus>());
			rq.getBody().setParam(user);
			HttpJsonClientExt.postJsonObject(game_change_user_bonus, rq);
		} catch (Exception e) {
			logger.error("Query uesr profile error.", e);
			response.getHead().setStatus(100002L);
		}
		return response;
	}

	/**
	 * 创建用户
	 * 0 不合法
	 * 1 合法
	 */
	@RequestMapping(value = "/islegalAccount")
	@ResponseBody
	public Response<Long> islegalAccount(@RequestBody Request<UserCustomer> request) throws Exception {
		Response<Long> res = new Response<Long>(request);
		UserCustomer customer = request.getBody().getParam();
		boolean islegal = false;
		try {
			islegal = userProfileService.islegalAccount(customer);
		} catch (UserExistServiceException e1) {
			res.getHead().setStatus(101002L);
			logger.error("The user account " + customer.getAccount() + "is exists ,please change the account");
			return res;
		} catch (Exception e) {
			logger.error("islegalAccount error.", e);
			throw e;
		}
		if (islegal) {
			res.setResult(1L);
		} else {
			res.setResult(0L);
		}
		return res;
	}

	/**
	 * 根据用户ID查询用户Profile信息。
	 * 
	 * @param id
	 *            用户ID
	 */
	@RequestMapping(value = "/queryUserProfile")
	public @ResponseBody
	Response<UserStrucResponse> queryUserProfile(@RequestBody @ValidRequestHeader Request<Object> request)
			throws Exception {
		long id = request.getHead().getUserId();
		Response<UserStrucResponse> response = new Response<UserStrucResponse>(request);
		try {
			User user = userProfileService.queryUserById(id);
			UserStrucResponse UserResponse = ControllerDTOConverter.user2UserStrucResponse(user);
			response.setResult(UserResponse);
		} catch (Exception e) {
			logger.error("Query uesr profile error.", e);
			throw e;
		}
		return response;
	}

	/**
	 * 根据用户ID查询用户Profile信息。
	 * 
	 * @param id
	 *            用户ID
	 */
	@RequestMapping(value = "/getSecurityCardNumber")
	public @ResponseBody
	Response<Map<String, Object>> getSecurityCardNumber(@RequestBody @ValidRequestHeader Request<Long> request)
			throws Exception {
		long id = request.getBody().getParam();
		Response<Map<String, Object>> response = new Response<Map<String, Object>>(request);
		try {
			UserCustomer user = userCustomerDao.getById(id);
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("BIND_DATE", user.getBindDate());
			map.put("BIND_PHONE_SERIAL", user.getBindPhoneSerial());
			map.put("PHONE_SERIAL_NUM", user.getSercuritySerilizeNumber());
			map.put("PHONE_TYPE", user.getPhoneType());
			response.setResult(map);
		} catch (Exception e) {
			logger.error("Query uesr profile error.", e);
			throw e;
		}
		return response;
	}

	@Resource(name = "userCustomerDaoImpl")
	private UserCustomerDaoImpl userCustomerDao;

	/**
	 * 
	 * 根据用户ID查询用户安全信息。
	 * 
	 * @param id
	 *            用户ID
	 */
	@RequestMapping(value = "/queryUserSecurityInfo")
	public @ResponseBody
	Response<UserStrucResponse> queryUserSecurityInfo(@RequestBody @ValidRequestHeader Request<Object> request)
			throws Exception {
		long id = request.getHead().getUserId();
		Response<UserStrucResponse> response = new Response<UserStrucResponse>(request);
		try {
			UserProfile profile = userProfileService.queryUserProfile(id);
			User user = new User();
			user.setId(id);
			user.setUserProfile(profile);
			UserStrucResponse UserResponse = ControllerDTOConverter.user2UserStrucResponse(user);
			response.setResult(UserResponse);
		} catch (Exception e) {
			logger.error("Query uesr security info error.", e);
			throw e;
		}
		return response;
	}

	/**
	 * 保存用户Profile信息。
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/saveUserProfile")
	@ResponseBody
	public Response<UserStrucResponse> saveUserProfile(
			@RequestBody @ValidRequestHeader @ValidRequestBody Request<UserStrucResponse> request) throws Exception {
		long id = request.getHead().getUserId();
		if (request.getBody() != null && request.getBody().getParam().getId() != null) {
			id = request.getBody().getParam().getId();
		}
		UserStrucResponse userStruct = request.getBody().getParam();
		Response<UserStrucResponse> response = new Response<UserStrucResponse>(request);
		try {
			userProfileService.saveUserProfile(id, ControllerDTOConverter.convertUserStrucResponse2User(userStruct)
					.getUserProfile());
		} catch (Exception e) {
			logger.error("Save user profile error.", e);
			logger.error("check nickname.", e);
			if(null!=e.getMessage()){
				String msg=e.getMessage();
				if(msg.contains("special")){
					response.getHead().setStatus(100199L);
				}else if(msg.contains("duplicate")){
					response.getHead().setStatus(100198L);
				}else if(msg.contains("twice")){
					response.getHead().setStatus(100197L);
				}else{
					response.getHead().setStatus(100196L);
				}
			}else{
				throw e;
			}
		}
		return response;
	}
	/**
	 * 校验昵称
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/checkNickname")
	@ResponseBody
	public Response<UserStrucResponse> checkNickname(
			@RequestBody @ValidRequestHeader @ValidRequestBody Request<UserStrucResponse> request) throws Exception {
		Response<UserStrucResponse> response = new Response<UserStrucResponse>(request);
		if (request.getBody() != null && request.getBody().getParam() != null) {
			String nickname = request.getBody().getParam().getNickname();
			Long userId = request.getBody().getParam().getId();
			try {
				boolean isModify=null==userId?false:true;
				userProfileService.checkNickname(userId,nickname,isModify);
			} catch (Exception e) {
				logger.error("check nickname.", e);
				if(null!=e.getMessage()){
					String msg=e.getMessage();
					if(msg.contains("special")){
						response.getHead().setStatus(100199L);
					}else if(msg.contains("duplicate")){
						response.getHead().setStatus(100198L);
					}else if(msg.contains("twice")){
						response.getHead().setStatus(100197L);
					}else{
						response.getHead().setStatus(100196L);
					}
				}
			}
		}
		return response;
	}
	/**
	 * 保存用户Profile信息。
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/setVip")
	@ResponseBody
	public Response<UserStrucResponse> setVip(
			@RequestBody @ValidRequestHeader @ValidRequestBody Request<UserStrucResponse> request) throws Exception {
		UserStrucResponse userStruct = request.getBody().getParam();
		Response<UserStrucResponse> response = new Response<UserStrucResponse>(request);
		try {
			userProfileService.setVip(userStruct.getId(), userStruct.getVipLvl().longValue());
		} catch (Exception e) {
			logger.error("Save user vip error.", e);
			throw e;
		}
		return response;
	}
	/**
	 * 设置用户业务开关
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/bizSwitch")
	@ResponseBody
	public Response<UserBizSwitchResponse> bizSwitch(
			@RequestBody @ValidRequestHeader @ValidRequestBody Request<UserBizSwitchRequest> request) throws Exception {
		UserBizSwitchRequest bizReq = request.getBody().getParam();
		Response<UserBizSwitchResponse> response = new Response<UserBizSwitchResponse>(request);
		try {
			userProfileService.bizSwitchSetting(bizReq.getUserId(),bizReq.getType(),bizReq.getSettMode(),bizReq.getStatus());
		} catch (Exception e) {
			logger.error("Set user bizSwitch error.", e);
			throw e;
		}
		return response;
	}
	@RequestMapping(value = "/queryBizSwitch")
	public @ResponseBody
	Response<UserBizSwitchResponse> queryBizSwitch(@RequestBody @ValidRequestBody Request<UserBizSwitchRequest> request)
			throws Exception {
		logger.info("queryBizSwitch...");
		Response<UserBizSwitchResponse> response = new Response<UserBizSwitchResponse>(request);
		try {
			UserBizSwitchResponse bizRes=new UserBizSwitchResponse();
			Long userId=request.getBody().getParam().getUserId();
			Integer type=request.getBody().getParam().getType();
			Integer status=userProfileService.queryBizSwitch(userId,type);
			logger.info("userId..."+userId+" status:"+status);
			bizRes.setStatus(status);
			response.setResult(bizRes);
		} catch (Exception e) {
			logger.error("queryUserDetailInfo error.", e);
			throw e;
		}
		return response;
	}
	/**
	 * 查询用户详细信息。（相比queryUserProfile接口多出了最近5次登陆的信息）
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/queryUserDetailInfoByAccount")
	public @ResponseBody
	Response<UserDetailResponse> queryUserDetailInfoByAccount(@RequestBody @ValidRequestBody Request<UserStruc> request)
			throws Exception {

		Response<UserDetailResponse> response = new Response<UserDetailResponse>(request);
		try {
			User user = userProfileService.queryUserByName(request.getBody().getParam().getAccount());
			if (user != null) {

				List<LoginLog> loginLogList = userProfileService.queryUserLoginLog(user.getId());
				UserProfile profile = userProfileService.queryUserProfile(user.getId());
				int size = loginLogList.size();
				LoginStrucResponse[] loginStruc = new LoginStrucResponse[size];
				for (int i = 0; i < size; i++) {
					loginStruc[i] = new LoginStrucResponse();
					LoginLog log = loginLogList.get(i);
					loginStruc[i].setLoginDate(DataConverterUtil.convertDate2Long(log.getLoginDate()));
					loginStruc[i].setLoginIp(log.getLoginIP());
					loginStruc[i].setLoginAddress(ipseek.getAddress(IPConverter.longToIp(log.getLoginIP())));
				}
				user.setUserProfile(profile);
				UserDetailResponse detail = new UserDetailResponse();
				detail.setLoginStruc(loginStruc);
				detail.setUserStruc(ControllerDTOConverter.user2UserStrucResponse(user));

				///余额相关
				//queryUserDetailInfo
				UserFund uf = fundServiceImpl.getUserFund(user.getId());
				if (uf != null) {
					detail.getUserStruc().setAvailBal(uf.getBal());
					detail.getUserStruc().setFreezeBal(uf.getFrozenAmt());
				}
				//团队余额
				detail.getUserStruc().setTeamBal(fundServiceImpl.getTeamFund(user.getId()));

				response.setResult(detail);
			}
		} catch (Exception e) {
			logger.error("queryUserDetailInfo error.", e);
			throw e;
		}
		return response;
	}

	@RequestMapping(value = "/queryUserDetailInfoByAccounts")
	public @ResponseBody
	Response<List<UserCustomer>> queryUserDetailInfoByAccounts(@RequestBody @ValidRequestBody Request<UserStruc> request)
			throws Exception {

		Response<List<UserCustomer>> response = new Response<List<UserCustomer>>(request);
		try {
			List<UserCustomer> user = userCustomerDao.queryUserByName(request.getBody().getParam().getAccounts());
			response.setResult(user);
		} catch (Exception e) {
			logger.error("queryUserDetailInfo error.", e);
			throw e;
		}
		return response;
	}

	/**
	 * 查询用户详细信息。（相比queryUserProfile接口多出了最近5次登陆的信息）
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/queryUserDetailInfo")
	public @ResponseBody
	Response<UserDetailResponse> queryUserDetailInfo(@RequestBody @ValidRequestHeader Request<UserStruc> request)
			throws Exception {
		long id = request.getHead().getUserId();

		if (request.getBody() != null && request.getBody().getParam() != null
				&& request.getBody().getParam().getUserId() != null) {
			id = request.getBody().getParam().getUserId().longValue();
		}
		if (id == -1)
			return null;
		Response<UserDetailResponse> response = new Response<UserDetailResponse>(request);
		try {
			List<LoginLog> loginLogList = userProfileService.queryUserLoginLog(id);
			User user = userProfileService.queryUserById(id);
			int size = loginLogList.size();
			LoginStrucResponse[] loginStruc = new LoginStrucResponse[size];
			for (int i = 0; i < size; i++) {
				loginStruc[i] = new LoginStrucResponse();
				LoginLog log = loginLogList.get(i);
				loginStruc[i].setLoginDate(DataConverterUtil.convertDate2Long(log.getLoginDate()));
				loginStruc[i].setLoginIp(log.getLoginIP());
				if (log.getLoginIP() != null && ("" + log.getLoginIP()).length() == 10) {
					//排除脏数据
					loginStruc[i].setLoginAddress(ipseek.getAddress(IPConverter.longToIp(log.getLoginIP())));
				}

			}
			UserDetailResponse detail = new UserDetailResponse();
			detail.setLoginStruc(loginStruc);
			detail.setUserStruc(ControllerDTOConverter.user2UserStrucResponse(user));

			///余额相关
			//queryUserDetailInfo
			UserFund uf = fundServiceImpl.getUserFund(user.getId());
			if (uf != null) {
				detail.getUserStruc().setAvailBal(uf.getBal());
				detail.getUserStruc().setCanWithdrawBal(uf.getDisableAmt());
				detail.getUserStruc().setFreezeBal(uf.getFrozenAmt());
			}
			//团队余额
			detail.getUserStruc().setTeamBal(fundServiceImpl.getTeamFund(user.getId()));
			response.setResult(detail);
		} catch (Exception e) {
			logger.error("queryUserDetailInfo error.", e);
			throw e;
		}
		return response;
	}

	/**
	 * 保存用户安全密码。
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/saveWithdrawPassword")
	public @ResponseBody
	Response<UserStrucResponse> saveWithdrawPassword(
			@RequestBody @ValidRequestHeader @ValidRequestBody Request<UserSecurityWithdrawPwdRequest> request)
			throws Exception {
		long id = request.getHead().getUserId();
		UserSecurityWithdrawPwdRequest UserSecurity = request.getBody().getParam();
		Response<UserStrucResponse> response = new Response<UserStrucResponse>(request);
		try {
			userProfileService.saveWithdrawPassword(id, UserSecurity.getWithdrawPasswd());
			Map<String, String> map = new HashMap<String, String>();
			msgToMQ.addMessageToMq(UserTools.getUserFromHead(request), NoticeTaskEnum.WithdrawPwdSet, map);
		} catch (Exception e) {
			logger.error("Save withdrawPassword error.", e);
			throw e;
		}
		return response;
	}

	/**
	 * 修改用户安全密码。
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/modifyWithdrawPassword")
	public @ResponseBody
	Response<UserStrucResponse> modifyWithdrawPassword(
			@RequestBody @ValidRequestHeader @ValidRequestBody Request<UserSecurityWithdrawPwdRequest> request)
			throws Exception {
		long id = request.getHead().getUserId();
		UserSecurityWithdrawPwdRequest UserSecurity = request.getBody().getParam();
		Response<UserStrucResponse> response = new Response<UserStrucResponse>(request);
		try {
			userProfileService.saveWithdrawPassword(id, UserSecurity.getWithdrawPasswd());
			Map<String, String> map = new HashMap<String, String>();
			msgToMQ.addMessageToMq(UserTools.getUserFromHead(request), NoticeTaskEnum.WithdrawPwdModify, map);
		} catch (Exception e) {
			logger.error("Save withdrawPassword error.", e);
			throw e;
		}
		return response;
	}

	/**
	 * 保存用户邮箱。
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/saveEmail")
	public @ResponseBody
	Response<UserStrucResponse> saveEmail(
			@RequestBody @ValidRequestHeader @ValidRequestBody Request<UserSecurityEmailRequest> request)
			throws Exception {
		long id = request.getHead().getUserId();
		UserSecurityEmailRequest UserSecurity = request.getBody().getParam();
		Response<UserStrucResponse> response = new Response<UserStrucResponse>(request);
		try {
			userProfileService.saveEmail(id, UserSecurity.getEmail());
			//msgToMQ.addMessageToMq(UserTools.getUserFromHead(request), NoticeTaskEnum.CHANGEMAIL_NEW, new HashMap<String,String>());

		} catch (Exception e) {
			logger.error("Save email error.", e);
			throw e;
		}
		return response;
	}

	/**
	 * 保存预设安全信息。
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/saveCipher")
	public @ResponseBody
	Response<UserStrucResponse> saveCipher(
			@RequestBody @ValidRequestHeader @ValidRequestBody Request<UserSecurityCipherRequest> request)
			throws Exception {
		long id = request.getHead().getUserId();
		UserSecurityCipherRequest UserSecurity = request.getBody().getParam();
		Response<UserStrucResponse> response = new Response<UserStrucResponse>(request);
		try {
			userProfileService.saveCipher(id, UserSecurity.getCipher());
			Map<String, String> map = new HashMap<String, String>();
			msgToMQ.addMessageToMq(UserTools.getUserFromHead(request), NoticeTaskEnum.CipherSet, map);
		} catch (Exception e) {
			logger.error("Save cipher error.", e);
			throw e;
		}
		return response;
	}

	/**
	 * 修改预设安全信息。
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/modifyCipher")
	public @ResponseBody
	Response<UserStrucResponse> modifyCipher(
			@RequestBody @ValidRequestHeader @ValidRequestBody Request<UserSecurityCipherRequest> request)
			throws Exception {
		long id = request.getHead().getUserId();
		UserSecurityCipherRequest UserSecurity = request.getBody().getParam();
		Response<UserStrucResponse> response = new Response<UserStrucResponse>(request);
		try {
			userProfileService.saveCipher(id, UserSecurity.getCipher());
			Map<String, String> map = new HashMap<String, String>();
			msgToMQ.addMessageToMq(UserTools.getUserFromHead(request), NoticeTaskEnum.CipherModify, map);
		} catch (Exception e) {
			logger.error("Save cipher error.", e);
			throw e;
		}
		return response;
	}

	/**
	 * 保存密码。
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/savePassword")
	public @ResponseBody
	Response<UserStrucResponse> savePassword(
			@RequestBody @ValidRequestHeader @ValidRequestBody Request<UserSecurityPwdRequest> request)
			throws Exception {
		long id = request.getHead().getUserId();
		UserSecurityPwdRequest UserSecurity = request.getBody().getParam();
		Response<UserStrucResponse> response = new Response<UserStrucResponse>(request);
		try {
			userProfileService.savePassword(id, UserSecurity.getPasswd(), UserSecurity.getPasswdLvl());
			Map<String, String> map = new HashMap<String, String>();
			msgToMQ.addMessageToMq(UserTools.getUserFromHead(request), NoticeTaskEnum.PasswordModify, map);
		} catch (Exception e) {
			logger.error("Save password error.", e);
			throw e;
		}
		return response;
	}

	/**
	 * 保存安全问题。
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/saveSecurityQA")
	public @ResponseBody
	Response<UserStrucResponse> saveSecurityQA(
			@RequestBody @ValidRequestHeader @ValidRequestBody Request<UserSecurityQARequest> request) throws Exception {
		long id = request.getHead().getUserId();
		UserSecurityQARequest userSecurity = request.getBody().getParam();
		Response<UserStrucResponse> response = new Response<UserStrucResponse>(request);
		List<QAInfo> qaList = new ArrayList<QAInfo>();
		for (QuStrucResponse qa : userSecurity.getQuStruc()) {
			QAInfo qaInfo = new QAInfo();
			qaInfo.setAns(qa.getAns());
			qaInfo.setQu(qa.getQu());
			qaList.add(qaInfo);
		}
		try {
			userProfileService.saveSecurityQA(id, qaList);

			Map<String, String> map = new HashMap<String, String>();
			msgToMQ.addMessageToMq(UserTools.getUserFromHead(request), NoticeTaskEnum.SecurityQASet, map);
		} catch (Exception e) {
			logger.error("Save SecurityQA error.", e);
			throw e;
		}
		return response;
	}

	/**
	 * 修改安全问题。
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/modifySecurityQA")
	public @ResponseBody
	Response<UserStrucResponse> modifySecurityQA(
			@RequestBody @ValidRequestHeader @ValidRequestBody Request<UserSecurityQARequest> request) throws Exception {
		long id = request.getHead().getUserId();
		String account = request.getHead().getUserAccount();
		UserSecurityQARequest userSecurity = request.getBody().getParam();
		Response<UserStrucResponse> response = new Response<UserStrucResponse>(request);
		List<QAInfo> qaList = new ArrayList<QAInfo>();
		for (QuStrucResponse qa : userSecurity.getQuStruc()) {
			QAInfo qaInfo = new QAInfo();
			qaInfo.setAns(qa.getAns());
			qaInfo.setQu(qa.getQu());
			qaList.add(qaInfo);
		}
		try {
			userProfileService.saveSecurityQA(id, qaList);
			Map<String, String> map = new HashMap<String, String>();

			msgToMQ.addMessageToMq(UserTools.getUserFromHead(request), NoticeTaskEnum.SecurityQAModify, map);
		} catch (Exception e) {
			logger.error("Save SecurityQA error.", e);
			throw e;
		}
		return response;
	}

	/**
	 * 保存安全问题和安全密码。
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/saveWithdrawPasswordAndSecurityQA")
	public @ResponseBody
	Response<UserStrucResponse> saveWithdrawPasswordAndSecurityQA(
			@RequestBody @ValidRequestHeader @ValidRequestBody Request<UserWithdrawPwdAndSecurityQARequest> request)
			throws Exception {
		long id = request.getHead().getUserId();
		UserWithdrawPwdAndSecurityQARequest param = request.getBody().getParam();
		Response<UserStrucResponse> response = new Response<UserStrucResponse>(request);
		List<QAInfo> qaList = new ArrayList<QAInfo>();
		for (QuStrucResponse qa : param.getQuStruc()) {
			QAInfo qaInfo = new QAInfo();
			qaInfo.setAns(qa.getAns());
			qaInfo.setQu(qa.getQu());
			qaList.add(qaInfo);
		}
		String withdrawPwd = param.getWithdrawPasswd();
		try {
			userProfileService.saveWithdrawPasswordAndSecurityQA(id, qaList, withdrawPwd);
			Map<String, String> map = new HashMap<String, String>();
			//msgToMQ.addMessageToMq(UserTools.getUserFromHead(request), NoticeTaskEnum.WithdrawPwdModify, map);
			msgToMQ.addMessageToMq(UserTools.getUserFromHead(request), NoticeTaskEnum.WithdrawPwdSet, map);
		} catch (Exception e) {
			logger.error("saveWithdrawPasswordAndSecurityQA error.", e);
			throw e;
		}
		return response;
	}

	@RequestMapping(value = "/savePasswdAndWithdrawPasswordAndSecurityQA")
	public @ResponseBody
	Response<UserStrucResponse> savePasswdAndWithdrawPasswordAndSecurityQA(
			@RequestBody @ValidRequestHeader @ValidRequestBody Request<UserPasswdAndWithdrawPasswordAndSecurityQARequest> request)
			throws Exception {
		long id = request.getHead().getUserId();
		UserPasswdAndWithdrawPasswordAndSecurityQARequest param = request.getBody().getParam();
		Response<UserStrucResponse> response = new Response<UserStrucResponse>(request);
		List<QAInfo> qaList = new ArrayList<QAInfo>();
		for (QuStrucResponse qa : param.getQuStruc()) {
			QAInfo qaInfo = new QAInfo();
			qaInfo.setAns(qa.getAns());
			qaInfo.setQu(qa.getQu());
			qaList.add(qaInfo);
		}
		String withdrawPwd = param.getWithdrawPasswd();
		String pwd = param.getPasswd();
		int pwdLevel = param.getPasswdLvl();
		try {
			userProfileService.savePasswdAndWithdrawPasswordAndSecurityQA(id, qaList, withdrawPwd, pwd, pwdLevel);
		} catch (Exception e) {
			logger.error("savePasswdAndWithdrawPasswordAndSecurityQA error.", e);
			throw e;
		}
		return response;
	}

	/**
	 * 根据用户名查询用户。
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/queryUserByName")
	public @ResponseBody
	Response<UserStrucResponse> queryUserByName(
			@RequestBody @ValidRequestBody Request<UserSecurityUsernameRequest> request) throws Exception {
		UserSecurityUsernameRequest UserSecurity = request.getBody().getParam();
		Response<UserStrucResponse> response = new Response<UserStrucResponse>(request);
		try {
			User user = userProfileService.queryUserByName(UserSecurity.getAccount());
			response.setResult(ControllerDTOConverter.user2UserStrucResponse(user));
		} catch (Exception e) {
			logger.error("queryUserByName error.", e);
			throw e;
		}
		return response;
	}

	/**
	 * 
	* @Title: queryUserByCriteria 
	* @Description: 客户列表查询接口
	* @param request
	* @return
	* @throws Exception
	 */
	//@RequestMapping(value = "/queryUserByCriteria")  // 20130510 接口暂不公布
	@ResponseBody
	public Response<List<UserStrucResponse>> queryUserByCriteria(
			@RequestBody @ValidRequestBody Request<QueryUserListRequestDTO> request) throws Exception {

		log("接受PHP客户列表查询请求...");

		Response<List<UserStrucResponse>> response = new Response<List<UserStrucResponse>>(request);
		if (null != request.getBody()) {

			try {

				CustomerQueryDTO queryDTO = ControllerDTOConverter.convertCustomerQueryDTO(request);
				queryDTO.setSortColumns("REGISTER_DATE DESC");
				response = searchCustomer(response, request, queryDTO);
			} catch (Exception ep) {
				log("查询客户列表系统异常：", ep);
				throw ep;
			}
		}

		log("成功处理客户列表查询请求...");
		return response;
	}

	/**
	 * 
	* @Title: querySubAgentList 
	* @Description: 查询直接下级代理接口
	* @param request
	* @return
	* @throws Exception
	 */
	@RequestMapping(value = "/querySubAgentList")
	@ResponseBody
	public Response<UserStrucResponse> querySubAgentList(
			@RequestBody @ValidRequestBody Request<QueryAgentSubUserRequestDTO> request) throws Exception {

		log("开始接受PHP查询直接下级代理接口...");

		Response<UserStrucResponse> response = new Response<UserStrucResponse>(request);
		if (null != request.getBody() && null != request.getBody().getParam()) {

			try {
				Long currentUserId = request.getHead().getUserId();
				Long searchUserId = request.getBody().getParam().getUserId();
				if(userProfileService.checkIsCurrentUsersChild(currentUserId, searchUserId)){
					User user = userProfileService.queryUserById(searchUserId);
	
					CustomerQueryDTO queryDTO = ControllerDTOConverter.convertAgentQueryDTO(request);
					queryDTO.setSortColumns(request.getBody().getParam().getOrderBy());
	
					UserStrucResponse userStrucResponse = ControllerDTOConverter.user2UserStrucResponse(user);
					if (user instanceof UserAgent) {
						//int userLevel = ((UserAgent) user).getUserLevel() > 10 ? 10 : ((UserAgent) user).getUserLevel() + 1;
						int userLevel = ((UserAgent) user).getUserLevel() + 1;
						queryDTO.setUserLvl(userLevel);
						Response<List<UserStrucResponse>> tt = searchCustomer(new Response<List<UserStrucResponse>>(),
								request, queryDTO);
						userStrucResponse.setSubUsers(tt.getBody().getResult());
						response.setResultPage(tt.getBody().getPager());
					} else {
						queryDTO.setUserLvl(-1);
					}
					response.setResult(userStrucResponse);
				}
			} catch (Exception ep) {
				log("查询直接下级代理接口系统异常：", ep);
				throw ep;
			}

		}

		log("成功处理PHP查询直接下级代理接口...");
		return response;
	}

	/**
	 * 
	* @Title: querySubCustomerList 
	* @Description: 查看直接下级客户接口
	* @param request
	* @return
	* @throws Exception
	 */
	@RequestMapping(value = "/querySubCustomerList")
	@ResponseBody
	public Response<UserStrucResponse> querySubCustomerList(
			@RequestBody @ValidRequestBody Request<QueryAgentSubUserRequestDTO> request) throws Exception {

		log("开始接受PHP查看直接下级客户接口请求....");

		Response<UserStrucResponse> response = new Response<UserStrucResponse>(request);
		if (null != request.getBody() && null != request.getBody().getParam()) {

			try {
				Long currentUserId = request.getHead().getUserId();
				Long searchUserId = request.getBody().getParam().getUserId();
				if(userProfileService.checkIsCurrentUsersChild(currentUserId, searchUserId)){
					User user = userProfileService.queryUserById(searchUserId);
					CustomerQueryDTO queryDTO = ControllerDTOConverter.convertAgentQueryDTO(request);
					queryDTO.setSortColumns(request.getBody().getParam().getOrderBy());
	
					//直接下级客户
					queryDTO.setUserLvl(-1);
					UserStrucResponse userStrucResponse = ControllerDTOConverter.user2UserStrucResponse(user);
					Response<List<UserStrucResponse>> tt = searchCustomer(new Response<List<UserStrucResponse>>(), request,
							queryDTO);
					userStrucResponse.setSubUsers(tt.getBody().getResult());
					response.setResultPage(tt.getBody().getPager());
					response.setResult(userStrucResponse);
				}
			} catch (Exception se) {
				log("查看下级客户接口系统异常：", se);
				throw se;
			}

		}
		log("成功处理PHP查看下级客户接口请求...");
		return response;
	}

	/**
	 * 
	* @Title: querySubUser 
	* @Description: 查看客户直接下级请求
	* @param request
	* @return
	* @throws Exception
	 */
	@RequestMapping(value = "/querySubUser")
	@ResponseBody
	public Response<List<UserStrucResponse>> querySubUser(
			@RequestBody @ValidRequestBody Request<QueryAgentSubUserRequestDTO> request) throws Exception {
		log("开始接受PHP查看下级用户请求。。。。");
		Response<List<UserStrucResponse>> response = new Response<List<UserStrucResponse>>(request);

		if (null != request.getBody() && null != request.getBody().getParam()) {
			try {
				CustomerQueryDTO queryDTO = ControllerDTOConverter.convertAgentQueryDTO(request);
				queryDTO.setSortColumns("ACCOUNT");
				queryDTO.setAccountMatch(true);
				return searchCustomer(response, request, queryDTO);
			} catch (Exception ep) {
				log("查看下级请求系统异常：", ep);
				throw ep;
			}
		}

		log("成功处理PHP查看下级用户请求...");
		return response;
	}

	/**
	 * 
	* @Title: querySubUserByCriteria  
	* @Description: 按复杂条件查询下级用户
	* @param request
	* @return
	* @throws Exception
	 */
	@RequestMapping(value = "/querySubUserByCriteria")
	@ResponseBody
	public Response<List<UserStrucResponse>> querySubUserByCriteria(
			@RequestBody Request<QueryUserByCriteriaRequestDTO> request) throws Exception {

		log("开始接收多条件查询请求...");
		
		Response<List<UserStrucResponse>> response = new Response<List<UserStrucResponse>>(request);

		if (null != request.getBody() && null != request.getBody().getParam()) {

			try {

				CustomerQueryDTO queryDTO = ControllerDTOConverter.convertMCustomerQueryDTO(request);

				queryDTO.setSortColumns(request.getBody().getParam().getOrderBy());
				if (request.getBody() != null && request.getBody().getParam().getOrderBy() != null) {
					queryDTO.setSortColumns(request.getBody().getParam().getOrderBy());
				}
				queryDTO.setIncludeSelf(request.getHead().getUserId());
				//queryDTO.setSortColumns(" id desc ");
				response = searchCustomer(response, request, queryDTO);

			} catch (Exception e) {
				log("多条件查看客户信息系统异常：", e);
				throw e;
			}
		}

		log("成功处理多条件查询请求...");
		return response;
	}

	/**
	 * 
	* @Title: querySubUserByCriteria 
	* @Description: 按复杂条件查询下级用户
	* @param request
	* @return
	* @throws Exception
	 */
	@RequestMapping(value = "/querySubUsers")
	@ResponseBody
	public Response<List<UserCustomer>> querySubUsers(@RequestBody Request<Privte> request) throws Exception {

		log("开始接收多条件查询请求...");

		Response<List<UserCustomer>> response = new Response<List<UserCustomer>>(request);

		if (null != request.getBody() && null != request.getBody().getParam()) {

			try {

				response.setResult(userProfileService.querySubusers(request.getBody().getParam().getUserId()));
			} catch (Exception e) {
				log("多条件查看客户信息系统异常：", e);
				throw e;
			}
		}

		log("成功处理多条件查询请求...");
		return response;
	}

	private static class Privte {
		private Long userId;

		public Long getUserId() {
			return userId;
		}

		public void setUserId(Long userId) {
			this.userId = userId;
		}

	}

	/**
	 * 
	 * 方法描述：与Service服务接口
	 * 
	 * @param request
	 * @param queryDTO
	 * @return
	 * @throws Exception
	 */
	private Response<List<UserStrucResponse>> searchCustomer(Response<List<UserStrucResponse>> response,
			Request<?> request, CustomerQueryDTO queryDTO) throws Exception {

		Page<User> list = null;
		if (response == null) {
			response = new Response<List<UserStrucResponse>>(request);
		}
		PageRequest<CustomerQueryDTO> pageReqeust = getPageRequest(request);
		pageReqeust.setSearchDo(queryDTO);
		pageReqeust.setSortColumns(queryDTO.getSortColumns());
		list = userProfileService.searchCustomer(pageReqeust);

		if (null != list && list.getResult() != null && list.getResult().size() > 0) {
			response = fillResponse(response, list, request);
		} else {
			response.setResult(new ArrayList<UserStrucResponse>());
		}

		return response;
	}
	
	/**
	 * 
	 * 方法描述：与Service服务接口
	 * 
	 * @param request
	 * @param queryDTO
	 * @return
	 * @throws Exception
	 */
	private Response<List<UserStrucResponse>> searchCustomerDownCList(Response<List<UserStrucResponse>> response,
			Request<?> request, CustomerQueryDTO queryDTO) throws Exception {

		Page<User> list = null;
		if (response == null) {
			response = new Response<List<UserStrucResponse>>(request);
		}
		PageRequest<CustomerQueryDTO> pageReqeust = getPageRequest(request);
		pageReqeust.setSearchDo(queryDTO);
		pageReqeust.setSortColumns(queryDTO.getSortColumns());
		list = userProfileService.searchCustomerDownCList(pageReqeust);

		if (null != list && list.getResult() != null && list.getResult().size() > 0) {
			response = fillResponse(response, list, request);
		} else {
			response.setResult(new ArrayList<UserStrucResponse>());
		}

		return response;
	}

	/**
	 * 
	 * 方法描述：日志信息
	 * 
	 * @param message
	 * @param e
	 */
	private void log(String message, Exception e) {
		if (logger.isErrorEnabled()) {
			logger.error(message, e);
		}
	}

	/**
	 * 记录日志信息 方法描述：
	 * 
	 * @param message
	 */
	private void log(String message) {
		if (logger.isInfoEnabled()) {
			logger.debug(message);
		}
	}

	/**
	 * 
	 * 方法描述：设置PageRequest信息
	 * 
	 * @param request
	 * @return
	 */
	private PageRequest<CustomerQueryDTO> getPageRequest(Request<?> request) {

		PageRequest<CustomerQueryDTO> pageReqeust = PageConverterUtils.getRestPageRequest(request.getBody().getPager()
				.getStartNo(), request.getBody().getPager().getEndNo());
		return pageReqeust;
	}

	/**
	 * 填充response
	 * 
	 * @param response
	 * @param list
	 */
	private Response<List<UserStrucResponse>> fillResponse(Response<List<UserStrucResponse>> response, Page<User> list,
			Request<?> request) {

		if (null == response) {
			response = new Response<List<UserStrucResponse>>(request);
		}
		log("开始填充response。。。");
		List<UserStrucResponse> responseList = new ArrayList<UserStrucResponse>();

		for (int i = 0; i < list.getResult().size(); i++) {

			User user = list.getResult().get(i);
			UserStrucResponse userStrucResponse = ControllerDTOConverter.user2UserStrucResponse(user);

			responseList.add(userStrucResponse);

		}

		response.setResult(responseList);

		ResultPager pager = new ResultPager();
		pager.setEndNo(list.getThisPageLastElementNumber());
		pager.setStartNo(list.getThisPageFirstElementNumber());
		pager.setTotal(list.getTotalCount());


		response.setResultPage(pager);
		return response;

	}

	/**
	 * 
	* @Title: createGeneralAgent 
	* @Description: 总代开户 请求
	* @param request
	* @return
	* @throws Exception
	 */
	@RequestMapping(value = "/createGeneralAgent")
	@ResponseBody
	public Object createGeneralAgent(
			@RequestBody @ValidRequestBody @ValidRequestHeader Request<CreateGeneralAgentRequest> request)
			throws Exception {

		log("开始接收总代开户请求...");

		Response<UserStrucResponse> response = new Response<UserStrucResponse>(request);

		if (null != request.getBody() && null != request.getBody().getParam()) {

			try {

				CreateGeneralAgentRequest agentRequest = request.getBody().getParam();

				userProfileService.createGeneralAgentUser(agentRequest.getAccount(), agentRequest.getPasswd(),
						agentRequest.getAgentLimit(), agentRequest.getRegisteIp());

			} catch (Exception e) {
				log("总代开户系统异常：", e);
				throw e;
			}
		}

		return response;
	}

	/**
	 * 
	* @Title: updateGeneralAgentBalance 
	* @Description: 更新总代用户请求  
	* @param request
	* @return
	* @throws Exception
	 */
	@RequestMapping(value = "/updateGeneralAgentBalance")
	@ResponseBody
	public Object updateGeneralAgentBalance(
			@RequestBody @ValidRequestBody Request<UpdataGeneralAgentBalanceRequest> request) throws Exception {

		log("开始接受更新总代用户请求...");

		Response<UserStrucResponse> response = new Response<UserStrucResponse>(request);

		if (null != request.getBody() && null != request.getBody().getParam()) {

			try {

				UpdataGeneralAgentBalanceRequest agentBalanceRequest = request.getBody().getParam();

				userProfileService.updateGeneralAgentUser(agentBalanceRequest.getUserId(),
						agentBalanceRequest.getAvailableQuota());

			} catch (Exception e) {
				log("更新总代用户请求系统异常：", e);
				throw e;
			}
		}
		return response;
	}

	/**
	 * 查询总代用户 
	* @Title: queryGeneralAgentByCriteria 
	* @Description: 查询总代用户 
	* @param request
	* @return
	* @throws Exception
	 */
	@RequestMapping(value = "/queryGeneralAgent")
	@ResponseBody
	public Object queryGeneralAgentByCriteria(@RequestBody @ValidRequestBody Request<QueryGeneralAgentRequest> request)
			throws Exception {

		log("开始接受总代用户查询请求...");
		 
		Response<List<UserStrucResponse>> response = new Response<List<UserStrucResponse>>(request);

		Page<User> list = null;

		if (null != request.getBody()) {
			try {
				QueryGeneralAgentDTO queryDto = ControllerDTOConverter.convertGeneralAgent(request);
				queryDto.setUserLvl(0L);
				PageRequest<QueryGeneralAgentDTO> pageRequest = PageConverterUtils.getRestPageRequest(request.getBody()
						.getPager().getStartNo(), request.getBody().getPager().getEndNo());
				pageRequest.setSearchDo(queryDto);
				pageRequest.setSortColumns("REGISTER_DATE DESC");

				list = userProfileService.queryGeneralAgentByCriteria(pageRequest);

				if (null != list && list.getResult() != null && list.getResult().size() > 0) {
					response = fillResponse(response, list, request);
				} else {
					response.setResult(new ArrayList<UserStrucResponse>());
				}

			} catch (Exception e) {
				log("总代用户查询系统异常：", e);
				throw e;
			}

		}
		return response;
	}
	
		
	/**
	 * 
	* @Title: querySubUser 
	* @Description: 查看客户直接下级请求
	* @param request
	* @return
	* @throws Exception
	 */
	@RequestMapping(value = "/querySubUserForDownClist")
	@ResponseBody
	public Response<List<UserStrucResponse>> querySubUserForDownClist(
			@RequestBody @ValidRequestBody Request<QueryAgentSubUserRequestDTO> request) throws Exception {
		log("开始接受PHP查看下级用户请求。。。。");
		
		Response<List<UserStrucResponse>> response = new Response<List<UserStrucResponse>>(request);

		if (null != request.getBody() && null != request.getBody().getParam()) {
			try {
				CustomerQueryDTO queryDTO = ControllerDTOConverter.convertAgentQueryDTO(request);
				queryDTO.setSortColumns("ACCOUNT");
				queryDTO.setAccountMatch(true);
					
				return searchCustomerDownCList(response, request, queryDTO);
			} catch (Exception ep) {
				log("查看下级请求系统异常：", ep);
				throw ep;
			}
		}

		log("成功处理PHP查看下级用户请求...");
		return response;
	}
	

}
