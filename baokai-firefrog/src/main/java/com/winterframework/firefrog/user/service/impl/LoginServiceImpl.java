package com.winterframework.firefrog.user.service.impl;

import java.io.IOException;
import java.util.Date;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.client.ClientProtocolException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.core.type.TypeReference;
import com.winterframework.firefrog.common.util.HttpJsonClientExt;
import com.winterframework.firefrog.common.util.IPConverter;
import com.winterframework.firefrog.fund.web.controller.ConfigUtils;
import com.winterframework.firefrog.global.service.GlobalIpService;
import com.winterframework.firefrog.global.service.GlobalWhiteListIpService;
import com.winterframework.firefrog.user.dao.IUserCustomerDao;
import com.winterframework.firefrog.user.dao.IUserLoginLogDao;
import com.winterframework.firefrog.user.dao.impl.UserCustomerDaoImpl;
import com.winterframework.firefrog.user.dao.vo.UserCustomer;
import com.winterframework.firefrog.user.entity.LoginLog;
import com.winterframework.firefrog.user.entity.UnFreezeDTO;
import com.winterframework.firefrog.user.entity.User;
import com.winterframework.firefrog.user.entity.UserFreezeInfo.FreezeMethodType;
import com.winterframework.firefrog.user.exception.LoginServiceException;
import com.winterframework.firefrog.user.service.IFreezeService;
import com.winterframework.firefrog.user.service.ILoginService;
import com.winterframework.firefrog.user.web.controller.game.GameResp;
import com.winterframework.modules.spring.exetend.PropertyConfig;
import com.winterframework.modules.web.jsonresult.Request;
import com.winterframework.modules.web.jsonresult.RequestBody;
import com.winterframework.modules.web.jsonresult.RequestHeader;
import com.winterframework.modules.web.jsonresult.Response;
import com.winterframework.orm.dal.ibatis3.BaseManager;
import com.winterframework.firefrog.common.entity.ChannelType;


@Service("loginServiceImpl")
@Transactional(rollbackFor = Exception.class)
public class LoginServiceImpl extends BaseManager<UserCustomerDaoImpl, UserCustomer> implements ILoginService {

	@Override
	public void setEntityDao(UserCustomerDaoImpl userCustomerDao) {
		this.entityDao = userCustomerDao;

	}

	@Resource(name = "freezeServiceImpl")
	private IFreezeService freezeService;

	@Resource(name = "userLoginLogDaoImpl")
	public IUserLoginLogDao userLoginLogDao;

	@Resource(name = "userCustomerDaoImpl")
	public IUserCustomerDao userCustomerDao;
	@PropertyConfig(value = "game_login")
	public String loginGameUrl;

	@Resource(name = "globalIpServiceImpl")
	private GlobalIpService globalIpService;
	
	@Resource(name = "globalWhiteListIpServiceImpl")
	private GlobalWhiteListIpService globalWhiteListIpService;
	
	@Resource(name = "configUtils")
	private ConfigUtils configUtils;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.winterframework.firefrog.user.service.ILoginService#login(java.lang
	 * .String, java.lang.String, java.lang.Long)登录，并保存登录信息，如果有错误，抛出错误状态码
	 */
	@Override
	public User login(String userName, String password, Long ip , String userAgent) throws Exception {
		User userCustomer = loginMethod(userName, password, ip, userAgent);
		return userCustomer;
	}

	private boolean doUserAppreal(User user) throws Exception {
		boolean result = true;
		//申诉冻结的用户
		if (user.isFreeze() && FreezeMethodType.APPREAL.equals(user.getUserFreeze().getFreezeMethodType())) {
			//判断申诉冻结的有效期
			Date activeDate = user.getUserProfile().getQaActiveDate();
			Date currentDate = new Date();
			if (currentDate.getTime() >= activeDate.getTime()) {
				//解冻用户
				UnFreezeDTO dto = new UnFreezeDTO();
				dto.setUserId(user.getId());
				dto.setMemo("申诉解冻");
				dto.setFreezeAccount(user.getAccount());
				dto.setFreezeId(user.getFreezeId());
				freezeService.unFreezeApproveUser(dto);
			} else {
				result = false;
			}

		}
		return result;
	}

	private User loginMethod(String userName, String password, Long ip, String userAgent) throws Exception {
		String limitType = StringUtils.isNotEmpty(configUtils.getIPSwich()) ? configUtils.getIPSwich() : "0";
		if (ip == null || globalIpService.isIpLimit(IPConverter.longToIp(ip), limitType)) {
			throw new LoginServiceException(UserCustomer.IP_LIMIT, "IP限制");// IP限制
		}
		// 2016-05-25 David Wu Add : 指定IP白名單 Start
		String whiteListIP = "";
		if(ip != null){
			whiteListIP = globalWhiteListIpService.getUserIpPrivileges(IPConverter.longToIp(ip), userName);
			if( !whiteListIP.isEmpty() && whiteListIP.indexOf( IPConverter.longToIp(ip).toString().trim() ) < 0 ){
				//whiteListIP清單不為空表示為指定IP白名單帳號若登入IP不再清單內視為異常拒絕登入
				throw new LoginServiceException(UserCustomer.IP_WHITE_LIST_LIMIT, "指定IP白名單限制");// 指定IP白名單限制
			}
		}
		// 2016-05-25 David Wu Add : 指定IP白名單 End
		User user1 = userCustomerDao.getUserByUserName(userName);
		if (user1 == null) {
			throw new LoginServiceException(UserCustomer.LOGIN_FAIL_REASON_USER_NOT_EXIST, "用户不存在");// 用户不存在
		}
		User user2 = userCustomerDao.getUserByUserNameAndPwd(userName, password);
	
		if (user2 == null) {
			throw new LoginServiceException(UserCustomer.LOGIN_FAIL_REASON_PWD_ERROR, "用户密码错误");// 用户密码错误
		}
		Date lastdt = null;
		if (user2.getLastLoginLog() != null) {
			lastdt = user2.getLastLoginLog().getLoginDate();
		}
		if (user2.isFreeze() && FreezeMethodType.ABSOLUTELY.equals(user2.getUserFreeze().getFreezeMethodType())) {
			throw new LoginServiceException(UserCustomer.LOGIN_FAIL_REASON_USER_FREEZE, "用户被冻结，不能登录");// 用户被冻结
		}

		if (!doUserAppreal(user2)) {
			throw new LoginServiceException(UserCustomer.LOGIN_FAIL_REASON_USER_FREEZE, "用户被冻结，不能登录");// 用户被冻结
		}
		saveLoginLog(ip, user2 , ChannelType.getChannelId(userAgent));

		//保存日志后 时间，ip有更改 需要重新查询user再返回
		User user3 = userCustomerDao.getUserByUserNameAndPwd(userName, password);
		//把上次登陆当然琪琪重新社追回来
		if (lastdt != null){
			user3.getLastLoginLog().setLoginDate(lastdt);
		}else{
			user3.getLastLoginLog().setLoginDate(null);
		}
		// 2016-05-25 David Wu Add : 指定IP白名單 Start
		if(whiteListIP.isEmpty()){
			user3.setWhiteListIpList("");
		}else{
			user3.setWhiteListIpList(whiteListIP);
		}
		// 2016-05-25 David Wu Add : 指定IP白名單 End

		return user3;
	}

	@SuppressWarnings("unchecked")
	public GameResp getGameGroup(Long userid) throws ClientProtocolException, SecurityException, IOException,
			NoSuchMethodException {
		Request<UserQuery> rq = new Request<UserQuery>();
		rq.setHead(new RequestHeader());
		rq.setBody(new RequestBody<UserQuery>());
		UserQuery uq = new UserQuery();
		uq.setUserid(userid);
		rq.getBody().setParam(uq);
		Response<GameResp> response = HttpJsonClientExt.postJsonObject(loginGameUrl, rq,
				new TypeReference<Response<GameResp>>() {
				});
		return response.getBody().getResult();
	}

	private class UserQuery {
		private Long userid;

		public Long getUserid() {
			return userid;
		}

		public void setUserid(Long userid) {
			this.userid = userid;
		}

	}

	private void saveLoginLog(Long ip, User user2 , Integer channelId) {
		LoginLog loginLog = new LoginLog();
		loginLog.setLoginIP(ip);
		loginLog.setUser(user2);
		loginLog.setLoginDate(new Date());
		loginLog.setChannelId(channelId);
		userLoginLogDao.saveLoginInfo(loginLog);

		//保存登录日志后 更新user表信息中的登录字段
		User userUpdate = new User();
		userUpdate.setId(user2.getId());
		userUpdate.setLastLoginLog(loginLog);
		userCustomerDao.updateUser(userUpdate);

	}

}
