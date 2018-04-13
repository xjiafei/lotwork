package com.winterframework.firefrog.user.dao;

import java.util.List;

import com.winterframework.firefrog.user.dao.vo.UserCustomer;
import com.winterframework.firefrog.user.entity.CustomerQueryDTO;
import com.winterframework.firefrog.user.entity.FreezeDTO;
import com.winterframework.firefrog.user.entity.FreezeLog;
import com.winterframework.firefrog.user.entity.QueryFreezeUserDTO;
import com.winterframework.firefrog.user.entity.QueryGeneralAgentDTO;
import com.winterframework.firefrog.user.entity.QueryUnFreezeUserLogDTO;
import com.winterframework.firefrog.user.entity.SecurityCard;
import com.winterframework.firefrog.user.entity.UnFreezeDTO;
import com.winterframework.firefrog.user.entity.User;
import com.winterframework.firefrog.user.entity.UserProfile;
import com.winterframework.modules.page.Page;
import com.winterframework.modules.page.PageRequest;
import com.winterframework.orm.dal.ibatis3.BaseDao;

public interface IUserCustomerDao extends BaseDao<UserCustomer>{

	public UserProfile selectUserProfileById(int id);
	public List<UserCustomer> querySubUsers(Long id);

	/**
	 * 客户查询
	 * 
	 * @param queryDTO
	 * @return
	 */
	public Page<User> queryCustomer(PageRequest<CustomerQueryDTO> queryDTO) throws Exception;

	/**
	 * 通过用户名和密码获取用户信息
	 * 
	 * @param userName
	 * @param password
	 * @return
	 * @throws Exception
	 */
	public User getUserByUserNameAndPwd(String userName, String password) throws Exception;

	/**
	 * 通过用户名获取用户信息
	 * 
	 * @param userName
	 * @return
	 * @throws ConvertException
	 * @throws Exception
	 */
	public User getUserByUserName(String userName) throws Exception;
	public User getUserByNickname(String nickname) throws Exception;

	public User queryUserById(long id) throws Exception;

	public User queryUserByEmail(String email) throws Exception;

	public List<User> queryUserById(long[] ids) throws Exception;
	public User queryUserByName(String username) throws Exception;
	public List<UserCustomer> queryUserByName(List<String> username) throws Exception;

	public void updateUser(User user);

	/**
	 * 
	 * 方法描述：根据用户id获取用户及下级用户信息
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	public List<User> getUserAndSubUserList(long userId) throws Exception;

	/**
	 * 
	* @Title: updateUserFreeze 
	* @Description: 更新用户冻结信息
	* @param @param user
	* @param @param freeze
	* @param @throws Exception    设定文件 
	* @return void    返回类型 
	* @throws
	 */
	public void updateUserFreeze(List<User> user, FreezeDTO freeze) throws Exception;

	/**
	 * 
	* @Title: updateUserUnFreeze 
	* @Description:更新用户解冻信息
	* @param @param list
	* @param @param unFreeze    设定文件 
	* @return void    返回类型 
	* @throws
	 */
	public void updateUserUnFreeze(List<User> list, UnFreezeDTO unFreeze);

	/**
	 * 
	* @Title: queryGeneralAgent 
	* @Description: 查询总代用户
	* @param @param pageRequest
	* @param @return
	* @param @throws Exception    设定文件 
	* @return Page<User>    返回类型 
	* @throws
	 */
	public Page<User> queryGeneralAgent(PageRequest<QueryGeneralAgentDTO> pageRequest) throws Exception;

	/**
	 * 
	* @Title: createUser 
	* @Description: 创建用户
	* @param @param user
	* @param @throws Exception    设定文件 
	* @return void    返回类型 
	* @throws
	 */
	public void createUser(User user) throws Exception;

	/** 
	* @Title: searchFreezeUser 
	* @Description: 查询冻结用户 
	* @param pageReqeust
	* @return
	 * @throws Exception 
	*/
	public Page<User> searchFreezeUser(PageRequest<QueryFreezeUserDTO> pageReqeust) throws Exception;

	/** 
	* @Title: searchUnFreezeUserLog 
	* @Description: 查询解冻记录 
	* @param pageReqeust
	* @return
	 * @throws Exception 
	*/
	public Page<FreezeLog> searchUnFreezeUserLog(PageRequest<QueryUnFreezeUserLogDTO> pageReqeust) throws Exception;

	public List<User> getAllUser() throws Exception;
	
	public User getUserByAccount(String account);

	public List<User> getUserByUserLvl(List<Long> userLvl, List<Long> vipLvl) throws Exception;

	public List<User> getUserByUserNames(List<String> userName) throws Exception;

	/** 
	 * 根据userId获取相关的安全信息
	* @return
	*/
	public SecurityCard querySecurityCardById(Long userId);
	public Long querySecurityCardByCard(String card);

	/** 
	 * 更新安全信息
	*/
	public void updateSecurityCard(SecurityCard securityCard);

	public Long userByName(String account) throws Exception;
	
	/**
	 * 清里安全中心信息
	 */
	public void cleanSafeCenter(UserCustomer userCustomer);
	
	/**
	 * 清理个人资料
	 */
	public void cleanPersonalInfo(UserCustomer userCustomer);
	
	public Long getCountByPage(UserCustomer userCustomer);
	

	public List<User> queryUserParent(String account) throws Exception ;
	 


	/**
	 * 更新用户及其下级业务开关状态
	 * @param userChain
	 * @param status
	 * @throws Exception
	 */
	void updateUserAwardRetStatusUserAndSub(String userChain,Integer status) throws Exception;
	void updateUserSuperPairStatusUserAndSub(String userChain,Integer status) throws Exception;
	void updateUserLhcStatusUserAndSub(String userChain,Integer status) throws Exception;
}
