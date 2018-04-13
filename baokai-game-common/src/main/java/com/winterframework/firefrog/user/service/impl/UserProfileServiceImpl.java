package com.winterframework.firefrog.user.service.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.winterframework.firefrog.user.dao.IUserCustomerDao;
import com.winterframework.firefrog.user.dao.IUserLoginLogDao;
import com.winterframework.firefrog.user.dao.vo.UserCustomer;
import com.winterframework.firefrog.user.entity.CustomerQueryDTO;
import com.winterframework.firefrog.user.entity.LoginLog;
import com.winterframework.firefrog.user.entity.QAInfo;
import com.winterframework.firefrog.user.entity.QueryGeneralAgentDTO;
import com.winterframework.firefrog.user.entity.User;
import com.winterframework.firefrog.user.entity.UserAgent;
import com.winterframework.firefrog.user.entity.UserProfile;
import com.winterframework.firefrog.user.exception.UserSecurityException;
import com.winterframework.firefrog.user.service.IUserProfileService;
import com.winterframework.modules.page.Page;
import com.winterframework.modules.page.PageRequest;

@Service("userProfileServiceImpl")
@Transactional(rollbackFor = Exception.class)
public class UserProfileServiceImpl implements IUserProfileService {

	private static Logger logger = LoggerFactory.getLogger(UserProfileServiceImpl.class);

	@Resource(name = "userCustomerDaoImpl")
	private IUserCustomerDao userCustomerDao;

	@Resource(name = "userLoginLogDaoImpl")
	private IUserLoginLogDao userLoginLogDao;

	@Override
	public UserProfile queryUserProfile(long id) throws Exception {
		User user = userCustomerDao.queryUserById(id);
		if (user == null) {
			logger.warn("User is not exsits.ID:" + id);
			return null;
		}
		UserProfile profile = user.getUserProfile();
		return profile;
	}

	@Override
	public void saveUserProfile(long id, UserProfile profile) throws Exception {
		User user = new User();
		user.setId(id);
		user.setUserProfile(profile);
		userCustomerDao.updateUser(user);
	}

	@Override
	public List<LoginLog> queryUserLoginLog(long id) {
		return userLoginLogDao.queryUserLoginLog(id);
	}

	@Override
	public void saveWithdrawPassword(long id, String withdrawPwd) throws Exception {
		User user = new User();
		user.setId(id);
		UserProfile profile = new UserProfile();
		profile.setWithdrawPwd(withdrawPwd);
		user.setUserProfile(profile);
		userCustomerDao.updateUser(user);
	}

	@Override
	public void saveEmail(long id, String email) throws Exception {
		if (userCustomerDao.queryUserByEmail(email) != null) {
			throw new UserSecurityException(UserCustomer.USER_EMAIL_EXSITS, "邮箱已存在");
		}
		User user = new User();
		user.setId(id);
		UserProfile profile = new UserProfile();
		profile.setEmail(email);
		profile.setEmailActived(1);
		user.setUserProfile(profile);
		userCustomerDao.updateUser(user);
	}

	@Override
	public void saveCipher(long id, String cipher) throws Exception {
		User user = new User();
		user.setId(id);
		UserProfile profile = new UserProfile();
		profile.setCipher(cipher);
		user.setUserProfile(profile);
		userCustomerDao.updateUser(user);
	}

	@Override
	public void savePassword(long id, String password, int passwordLevel) throws Exception {
		User user = new User();
		user.setId(id);
		UserProfile profile = new UserProfile();
		profile.setPassword(password);
		profile.setPasswordLevel(passwordLevel);
		user.setUserProfile(profile);
		userCustomerDao.updateUser(user);
	}

	@Override
	public void saveSecurityQA(long id, List<QAInfo> qaList) throws Exception {
		User user = new User();
		user.setId(id);
		UserProfile profile = new UserProfile();
		profile.setQa(qaList);
		user.setUserProfile(profile);
		userCustomerDao.updateUser(user);
	}

	@Override
	public List<QAInfo> querySecurityQA(long id) throws Exception {
		UserProfile profile = queryUserProfile(id);
		return profile.getQa();
	}

	@Override
	public User queryUserByName(String username) throws Exception {
		return userCustomerDao.queryUserByName(username);
	}

	@Override
	public Page<User> searchCustomer(PageRequest<CustomerQueryDTO> queryDTO) throws Exception {

		log("开始进入查询客户列表功能...");

		return userCustomerDao.queryCustomer(queryDTO);
	}

	/**
	 * 
	 * 方法描述：设置日志信息
	 * 
	 * @param message
	 */
	private void log(String message) {
		if (logger.isInfoEnabled()) {
			logger.info(message);
		}
	}

	@Override
	public Page<User> queryGeneralAgentByCriteria(PageRequest<QueryGeneralAgentDTO> pageRequest) throws Exception {
		pageRequest.getSearchDo().setAccountMatch(true);
		return userCustomerDao.queryGeneralAgent(pageRequest);
	}

	@Override
	public void createGeneralAgentUser(String account, String password, Integer agentLimit) throws Exception {

		UserAgent agent = new UserAgent();
		UserProfile profile = new UserProfile();
		User user = new User();
		user.setId(-1l); //set parentId 

		profile.setAccount(account);
		profile.setPassword(password);
		profile.setPasswordLevel(1);
		profile.setRegisterDate(new Date());

		agent.setAgentLimit(agentLimit);
		agent.setUserProfile(profile);
		agent.setTeamAgentCount(0);
		agent.setTeamUserCount(0);
		agent.setParent(user);

		profile.setUserChain("/" + profile.getAccount() + "/");

		userCustomerDao.createUser(agent);
	}

	@Override
	public void updateGeneralAgentUser(Long userId, Integer availableQuota) throws Exception {
		UserAgent agent = new UserAgent();
		agent.setId(userId);
		agent.setAgentLimit(availableQuota);
		userCustomerDao.updateUser(agent);
	}

	@Override
	public User queryUserById(long id) throws Exception {
		return userCustomerDao.queryUserById(id);
	}

	@Override
	public void saveWithdrawPasswordAndSecurityQA(long id, List<QAInfo> qaList, String withdrawPwd) throws Exception {
		User user = new User();
		user.setId(id);
		UserProfile profile = new UserProfile();
		profile.setQa(qaList);
		profile.setWithdrawPwd(withdrawPwd);
		user.setUserProfile(profile);
		userCustomerDao.updateUser(user);
	}

	@Override
	public void savePasswdAndWithdrawPasswordAndSecurityQA(long id, List<QAInfo> qaList, String withdrawPwd,
			String pwd, int pwdLevel) throws Exception {
		User user = new User();
		user.setId(id);
		UserProfile profile = new UserProfile();
		profile.setQa(qaList);
		profile.setWithdrawPwd(withdrawPwd);
		profile.setPassword(pwd);
		profile.setPasswordLevel(pwdLevel);
		user.setUserProfile(profile);
		userCustomerDao.updateUser(user);
	}

	/**
	* Title: getUserAndSubUserList
	* Description:
	* @param userId
	* @return
	* @throws Exception 
	* @see com.winterframework.firefrog.user.service.IUserProfileService#getUserAndSubUserList(long) 
	*/
	@Override
	public List<User> getUserAndSubUserList(long userId) throws Exception {
		return userCustomerDao.getUserAndSubUserList(userId);
	}
	
	@Override
	public void saveUserHeadImg(Long userId, String nickName, String headImg)
			throws Exception {
		User user = new User();
		user.setId(userId);
		user.setNickName(nickName);
		user.setHeadImg(headImg);
		userCustomerDao.updateUser(user);
	}

	@Override
	public boolean checkUserNickNameOnly(String nickName) throws Exception {
		User user = userCustomerDao.getUserByNickName(nickName);
		if(user == null){
			return true;
		}else{
			return false;
		}
	}
	
}
