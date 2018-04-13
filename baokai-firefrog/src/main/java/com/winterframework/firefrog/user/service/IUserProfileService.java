package com.winterframework.firefrog.user.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.winterframework.firefrog.user.dao.vo.UserCustomer;
import com.winterframework.firefrog.user.dao.vo.UserPtRegister;
import com.winterframework.firefrog.user.entity.CustomerQueryDTO;
import com.winterframework.firefrog.user.entity.LoginLog;
import com.winterframework.firefrog.user.entity.QAInfo;
import com.winterframework.firefrog.user.entity.QueryGeneralAgentDTO;
import com.winterframework.firefrog.user.entity.User;
import com.winterframework.firefrog.user.entity.UserProfile;
import com.winterframework.firefrog.user.web.dto.UserActivityRegisterResponse;
import com.winterframework.modules.page.Page;
import com.winterframework.modules.page.PageRequest;

@Transactional(rollbackFor = Exception.class)
public interface IUserProfileService {

	public void createUser(UserCustomer user) throws Exception;
	
	public void createPtUser(Long id,Long parentId) throws Exception;
	
	public UserActivityRegisterResponse getPtUser(UserPtRegister userPtRegister) throws Exception;

	public UserProfile queryUserProfile(long id) throws Exception;

	public List<LoginLog> queryUserLoginLog(long id) throws Exception;

	public void saveUserProfile(long id, UserProfile profile) throws Exception;

	public void setVip(Long id, Long vipLvl) throws Exception;
	
	public void checkNickname(Long userId, String nickname,boolean isModify) throws Exception;
	
	
	 
	/**
	 * 用户业务开关设置
	 * @param userId
	 * @param type
	 * @param settMode
	 * @param status
	 * @throws Exception
	 */
	public void bizSwitchSetting(Long userId, Integer type,Integer settMode,Integer status) throws Exception;
	/**
	 * 查询用户业务开关状态
	 * @param userId
	 * @param type
	 * @return
	 * @throws Exception
	 */
	Integer queryBizSwitch(Long userId, Integer type) throws Exception;
	public void saveUser(UserCustomer uc) throws Exception;

	public void saveWithdrawPassword(long id, String withdrawPwd) throws Exception;

	public void saveEmail(long id, String email) throws Exception;

	public void saveCipher(long id, String cipher) throws Exception;

	public void savePassword(long id, String password, int passwordLevel) throws Exception;

	public void saveSecurityQA(long id, List<QAInfo> qaList) throws Exception;

	public List<QAInfo> querySecurityQA(long id) throws Exception;

	public User queryUserByName(String name) throws Exception;

	public List<UserCustomer> querySubusers(Long parentId) throws Exception;
	public void saveWithdrawPasswordAndSecurityQA(long id, List<QAInfo> qaList, String withdrawPwd) throws Exception;

	public void savePasswdAndWithdrawPasswordAndSecurityQA(long id, List<QAInfo> qaList, String withdrawPwd,
			String pwd, int pwdLevel) throws Exception;

	/**
	 * 根据条件查询客户信息
	 * 
	 * @param pageRequest
	 * @return
	 * @throws Exception
	 */
	public Page<User> searchCustomer(PageRequest<CustomerQueryDTO> pageRequest) throws Exception;

	/**
	 * 
	* @Title: queryGeneralAgentByCriteria 
	* @Description: 总代用户查询代理接口
	* @param @param pageRequest
	* @param @return
	* @param @throws Exception    设定文件 
	* @return Page<User>    返回类型 
	* @throws
	 */
	public Page<User> queryGeneralAgentByCriteria(PageRequest<QueryGeneralAgentDTO> pageRequest) throws Exception;

	/**
	 * 
	* @Title: createGeneralAgentUser 
	* @Description: 创建总代用户 
	* @param account
	* @param password
	* @param agentLimit
	* @throws Exception
	 */
	public void createGeneralAgentUser(String account, String password, Long agentLimit, Long registeIp)
			throws Exception;

	/**
	 * 
	* @Title: updateGeneralAgentUser 
	* @Description: 更新总代用户配额 
	* @param userId
	* @param availableQuota
	* @throws Exception
	 */
	public void updateGeneralAgentUser(Long userId, Long availableQuota) throws Exception;

	/**
	 * 
	* @Title: queryUserById 
	* @Description: 根据用户ID获取用户信息 
	* @param @param id
	* @param @return
	* @param @throws Exception    设定文件 
	* @return User    返回类型 
	* @throws
	 */
	public User queryUserById(long id) throws Exception;

	/** 
	 * 判断客户的账号是否包含敏感词
	* @return true:合法 false：不合法
	 * @throws Exception 
	*/
	public boolean islegalAccount(UserCustomer customer) throws Exception;
	
	
	public void SendMsg (User userEntity) throws Exception;


	public Long userByName(String account) throws Exception;

	public Page<User> searchCustomerDownCList(PageRequest<CustomerQueryDTO> pageReqeust)throws Exception;
	
	public List<User> queryUserParent(String username) throws Exception;
	
	/**
	 * 确认用户是否为现在用户的下层用户
	 * @param currentUserId
	 * @param childUserId
	 * @return
	 * @throws Exception
	 */
	public boolean checkIsCurrentUsersChild(Long currentUserId,Long childUserId) throws Exception;

}