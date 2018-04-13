package com.winterframework.firefrog.user.dao.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Repository;

import com.winterframework.firefrog.fund.dao.IFundDao;
import com.winterframework.firefrog.user.dao.IUserCustomerDao;
import com.winterframework.firefrog.user.dao.vo.UserCustomer;
import com.winterframework.firefrog.user.dao.vo.UserCustomerVo;
import com.winterframework.firefrog.user.dao.vo.UserFreezeLog;
import com.winterframework.firefrog.user.dao.vo.VOConverter;
import com.winterframework.firefrog.user.entity.CustomerQueryDTO;
import com.winterframework.firefrog.user.entity.FreezeDTO;
import com.winterframework.firefrog.user.entity.FreezeLog;
import com.winterframework.firefrog.user.entity.FreezeLog.FrozenAction;
import com.winterframework.firefrog.user.entity.QueryFreezeUserDTO;
import com.winterframework.firefrog.user.entity.QueryGeneralAgentDTO;
import com.winterframework.firefrog.user.entity.QueryUnFreezeUserLogDTO;
import com.winterframework.firefrog.user.entity.SecurityCard;
import com.winterframework.firefrog.user.entity.UnFreezeDTO;
import com.winterframework.firefrog.user.entity.User;
import com.winterframework.firefrog.user.entity.UserCustomerRequestVo;
import com.winterframework.firefrog.user.entity.UserProfile;
import com.winterframework.modules.page.Page;
import com.winterframework.modules.page.PageRequest;
import com.winterframework.orm.dal.ibatis3.BaseIbatis3Dao;

/**
 * 
 * 
 * 类功能说明: 客户列表查询Dao实现类
 * 
 * <p>
 * Copyright: Copyright(c) 2013
 * </p>
 * 
 * @Version 1.0
 * @author Richard & Tod
 * 
 */
@Repository("userCustomerDaoImpl")
public class UserCustomerDaoImpl extends BaseIbatis3Dao<UserCustomer> implements IUserCustomerDao {

	@Resource(name = "fundDaoImpl")
	protected IFundDao fundDao;
	
	@Override
	public UserProfile selectUserProfileById(int id) {
		return null;
	}
	public List<UserCustomer> querySubUsers(Long id){
		Map<String, Object> filters = new HashMap<String, Object>();
		filters.put("id",id);
		return sqlSessionTemplate.selectList("querySubUserList", filters);
	};

	public Page<User> getAllCustomer(Map<String, Object> map, PageRequest<?> pageRequest) throws Exception {

		Number totalCount = (Number) sqlSessionTemplate.selectOne("getCountByCustomerList", map);
		if (totalCount == null || totalCount.intValue() <= 0) {
			return new Page<User>(0);
		}
		log.info("-----------------------------map : " + map.toString());
		Page<User> page = new Page<User>(pageRequest.getPageNumber(), pageRequest.getPageSize(), totalCount.intValue());

		Map<String, Object> filters = new HashMap<String, Object>();
		filters.put("offset", page.getFirstResult());
		filters.put("pageSize", page.getPageSize());
		filters.put("lastRows", page.getFirstResult() + page.getPageSize());
		filters.put("sortColumns", pageRequest.getSortColumns());
		filters.putAll(map);

		RowBounds rowBounds = new RowBounds(page.getFirstResult(), page.getPageSize());
		List<UserCustomer> list = sqlSessionTemplate.selectList("queryGeneralAgent", filters, rowBounds);
		
		List<User> userList = new ArrayList<User>();

		for (UserCustomer _customer : list) {

			try {

			// 将dao返回的vo转换成 服务层的实体类
				User user = VOConverter.customer2User(_customer);
				userList.add(user);
			} catch (Exception e) {

				log.error("查询客户列表，映射类转换 实体类出错；" + e.getMessage(), e);
				throw e;
			}
		}
		page.setResult(userList);

		return page;

	}
	
	public Page<User> getAllCustomerDownCList(Map<String, Object> map, PageRequest<?> pageRequest) throws Exception {
		
		log.info("----------------------------account : " + map.get("getPAc").toString());
		UserCustomer parentInfo =(UserCustomer)sqlSessionTemplate.selectOne("getId",map.get("getPAc").toString());
		log.info("--------------------------id : " + parentInfo.getId());
		map.put("parentId", parentInfo.getId());

		Number totalCount = (Number) sqlSessionTemplate.selectOne("getCountByCustomerListDonwCList", map);
		if(parentInfo!=null){
			totalCount=totalCount.intValue()+1;			
		}
		
		log.info("info ip : " + parentInfo.getRegisterIp() + ", date : " + parentInfo.getRegisterDate());
		if (totalCount == null || totalCount.intValue() <= 0) {
			return new Page<User>(0);
		}

		Page<User> page = new Page<User>(pageRequest.getPageNumber(), pageRequest.getPageSize(), totalCount.intValue());

		Map<String, Object> filters = new HashMap<String, Object>();
		filters.put("offset", page.getFirstResult());
		filters.put("pageSize", page.getPageSize());
		filters.put("lastRows", page.getFirstResult() + page.getPageSize());
		filters.put("sortColumns", pageRequest.getSortColumns());
		filters.putAll(map);

		RowBounds rowBounds = new RowBounds(page.getFirstResult(), page.getPageSize());
		List<UserCustomer> list = sqlSessionTemplate.selectList("queryGeneralAgent", filters, rowBounds);

		List<User> userList = new ArrayList<User>();
		User userInfo = VOConverter.customer2User(parentInfo);
		userList.add(userInfo);

		
		for (UserCustomer _customer : list) {

			try {
			// 将dao返回的vo转换成 服务层的实体类
				User user = VOConverter.customer2User(_customer);
				userList.add(user);
			} catch (Exception e) {

				log.error("查询客户列表，映射类转换 实体类出错；" + e.getMessage(), e);
				throw e;
			}
		}
		page.setResult(userList);

		return page;

	}

	/**
	 * 客户列表查询
	 */
	@Override
	public Page<User> queryCustomer(PageRequest<CustomerQueryDTO> pageRequst) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();

		CustomerQueryDTO queryDTO = pageRequst.getSearchDo();
	

		if (StringUtils.isNotBlank(queryDTO.getAccount())) {
			map.put("account", queryDTO.getAccount());
			map.put("isAccountMatch", true);
		}

		if (StringUtils.isNotBlank(queryDTO.getEmail())) {
			map.put("email", queryDTO.getEmail());
		}

		if (null != queryDTO.getUserLvl()) {
			map.put("userLvl", queryDTO.getUserLvl());
		}
		if (null != queryDTO.getVipLvl()) {
			map.put("vipLvl", queryDTO.getVipLvl());
		}

		if (null != queryDTO.getFromDate()) {
			map.put("fromDate", queryDTO.getFromDate());
		}

		if (null != queryDTO.getToDate()) {
			map.put("toDate", queryDTO.getToDate());
		}

		// 一期无此字段
		if (queryDTO.getFromBal() > 0) {
			map.put("fromBal", queryDTO.getFromBal());
		}
		// 一期无此字段
		if (queryDTO.getToBal() > 0) {
			map.put("toBal", queryDTO.getToBal());
		}

		if (null != queryDTO.getParentId() && queryDTO.getParentId() != 0) {
			map.put("parentId", queryDTO.getParentId());

		}
		
		map.put("userChain", queryDTO.getUserChain());
		if (null != queryDTO.getFromLoginDate()) {
			map.put("fromLoginDate", queryDTO.getFromLoginDate());
		}

		if (null != queryDTO.getToLoginDate()) {
			map.put("toLoginDate", queryDTO.getToLoginDate());
		}
		log.info("dao device : " + queryDTO.getDevice());
		if(null != queryDTO.getDevice()){
			map.put("device", queryDTO.getDevice());
		}
		if (true == queryDTO.isAccountMatch()) {
			map.put("isAccountMatch", true);
			
		}
		if(null!=queryDTO.getIncludeSelf()){
			map.put("includeSelf",queryDTO.getIncludeSelf());
		}
		if(null==queryDTO.getIncludeTeamBal() || queryDTO.getIncludeTeamBal()){
			map.put("includeTeamBal","_TEAM");
		}
		return getAllCustomer(map, pageRequst);

	}
	
	/**
	 * 客户列表查询
	 */
	public Page<User> queryCustomerDownCList(PageRequest<CustomerQueryDTO> pageRequst) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();

		CustomerQueryDTO queryDTO = pageRequst.getSearchDo();
	
		//
		if (StringUtils.isNotBlank(queryDTO.getAccount())) {
			map.put("getPAc", queryDTO.getAccount());
		}

		if (StringUtils.isNotBlank(queryDTO.getEmail())) {
			map.put("email", queryDTO.getEmail());
		}

		if (null != queryDTO.getUserLvl()) {
			map.put("userLvl", queryDTO.getUserLvl());
		}
		if (null != queryDTO.getVipLvl()) {
			map.put("vipLvl", queryDTO.getVipLvl());
		}

		if (null != queryDTO.getFromDate()) {
			map.put("fromDate", queryDTO.getFromDate());
		}

		if (null != queryDTO.getToDate()) {
			map.put("toDate", queryDTO.getToDate());
		}

		// 一期无此字段
		if (queryDTO.getFromBal() > 0) {
			map.put("fromBal", queryDTO.getFromBal());
		}
		// 一期无此字段
		if (queryDTO.getToBal() > 0) {
			map.put("toBal", queryDTO.getToBal());
		}

		if (null != queryDTO.getParentId() && queryDTO.getParentId() != 0) {
			map.put("parentId", queryDTO.getParentId());

		}
		
		map.put("userChain", queryDTO.getUserChain());
		if (null != queryDTO.getFromLoginDate()) {
			map.put("fromLoginDate", queryDTO.getFromLoginDate());
		}

		if (null != queryDTO.getToLoginDate()) {
			map.put("toLoginDate", queryDTO.getToLoginDate());
		}

		if (true == queryDTO.isAccountMatch()) {
			map.put("isAccountMatch", true);
			
		}
		if(null!=queryDTO.getIncludeSelf()){
			map.put("includeSelf",queryDTO.getIncludeSelf());
		}
		if(null==queryDTO.getIncludeTeamBal() || queryDTO.getIncludeTeamBal()){
			map.put("includeTeamBal","_TEAM");
		}
		return getAllCustomerDownCList(map, pageRequst);

	}

	@Override
	public User getUserByUserNameAndPwd(String userName, String password) throws Exception {
		UserCustomer uc = new UserCustomer();
		uc.setAccount(userName);
		uc.setPasswd(password);
		List<UserCustomer> ucs = getAllByEntity(uc);
		if (!ucs.isEmpty()) {
			return VOConverter.customer2User(ucs.get(0));
		}
		return null;
	}

	@Override
	public User getUserByUserName(String userName) throws Exception {
		UserCustomer uc = new UserCustomer();
		uc.setAccount(userName);
		List<UserCustomer> ucs = getAllByEntity(uc);
		if (!ucs.isEmpty()) {
			return VOConverter.customer2User(ucs.get(0));
		}
		return null;
	}
	@Override
	public User getUserByNickname(String nickName) throws Exception {
		UserCustomer uc = new UserCustomer();
		uc.setNickname(nickName);
		List<UserCustomer> ucs = getAllByEntity(uc);
		if (!ucs.isEmpty()) {
			return VOConverter.customer2User(ucs.get(0));
		}
		return null;
	}
	
	@Override
	public User getUserByAccount(String account) {
		UserCustomer uc = new UserCustomer();
		uc.setAccount(account);
		List<UserCustomer> ucs = getAllByEntity(uc);
		try{
			if (!ucs.isEmpty()) {
				return VOConverter.customer2User(ucs.get(0));
			}
		}catch(Exception e){
			return null;
		}
		return null;
	}

	@Override
	public User queryUserById(long id) throws Exception {
		UserCustomer userCustomer = getById(id);
		return VOConverter.customer2User(userCustomer);
	}

	@Override
	public void updateUser(User user) {
		UserCustomer userCustomer = VOConverter.convertUser2Customer(user);
		update(userCustomer);
	}

	@Override
	public User queryUserByName(String username) throws Exception {
		if (username == null) {
			return null;
		}
		UserCustomer uc = new UserCustomer();
		uc.setAccount(username);
		List<UserCustomer> ucs = getAllByEntity(uc);
		User user = null;
		if (!ucs.isEmpty()) {
			user = VOConverter.customer2User(ucs.get(0));
			user.setFund(fundDao.getUserFund(user.getId()));
		}
		return user;
	}
	@Override
	public List<UserCustomer> queryUserByName(List<String> username) throws Exception {
		if (username == null) {
			return new ArrayList<UserCustomer>();
		}
		return this.sqlSessionTemplate.selectList("queryUserbyNames", username);
		
	}

	@Override
	public User queryUserByEmail(String email) throws Exception {
		UserCustomer uc = new UserCustomer();
		uc.setEmail(email);
		List<UserCustomer> ucs = getAllByEntity(uc);
		User user = null;
		if (!ucs.isEmpty()) {
			user = VOConverter.customer2User(ucs.get(0));
		}
		return user;
	}

	@Override
	public List<User> getUserAndSubUserList(long userId) throws Exception {

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", userId);
		List<UserCustomer> list = this.sqlSessionTemplate.selectList("queryUserAndSubUserList", map);
		List<User> userList = new ArrayList<User>();
		for (UserCustomer customer : list) {

			try {
				User user = VOConverter.customer2User(customer);
				userList.add(user);

			} catch (Exception e) {

				log.error("根据用户id获取用户及下级用户信息，映射类转换 实体类出错；" + e.getMessage(), e);
				throw e;
			}

		}
		return userList;
	}

	@Override
	public void updateUserFreeze(List<User> userList, FreezeDTO freeze) throws Exception {

		Map<String, Object> map = new HashMap<String, Object>();
		List<Long> ids = new ArrayList<Long>();
		for (User user : userList) {
			ids.add(user.getId());
			map.put("isFreeze", FrozenAction.Freeze.getValue());
			if (user.getFreezeLog() != null && user.getFreezeLog().getUserFreeLog() != null) {
				map.put("freezeId", user.getFreezeLog().getUserFreeLog().getId());
			}
			map.put("freezeDate", new Date());
			map.put("freezer", freeze.getFreezeId());
			map.put("freezeMethod", freeze.getFreezeMethod());
			map.put("freezeMemo", freeze.getMemo());
			map.put("ids", ids);
			map.put("freezeAccount", freeze.getFreezeAccount());
			this.sqlSessionTemplate.update("updateUserFreeze", map);
			ids.clear();
		}

	}

	@Override
	public void updateUserUnFreeze(List<User> list, UnFreezeDTO unFreeze) {

		Map<String, Object> map = new HashMap<String, Object>();
		List<Long> ids = new ArrayList<Long>();

		for (User user : list) {
			ids.add(user.getId());

			map.put("isFreeze", FrozenAction.UnFreeze.getValue());
			//添加了一个字段
			map.put("unfreezeDate", new Date());
			//map.put("freezeDate", new Date());

			map.put("freezeMethod", 0);
			map.put("freezeMemo", unFreeze.getMemo());
			map.put("ids", ids);
			map.put("freezeAccount", unFreeze.getFreezeAccount());

			this.sqlSessionTemplate.update("updateUserFreeze", map);
			ids.clear();
		}
	}

	@Override
	public List<User> queryUserById(long[] ids) throws Exception {
		//		StringBuilder sb = new StringBuilder(1024);
		//		for (long id : ids) {
		//			sb.append(id);
		//			sb.append(",");
		//		}
		//		sb.deleteCharAt(sb.length() - 1);
		List<UserCustomer> cusList = sqlSessionTemplate.selectList("queryUserByIds", ids);
		List<User> userList = new ArrayList<User>();
		User user = null;
		for (UserCustomer cus : cusList) {
			user = VOConverter.customer2User(cus);
			userList.add(user);
		}
		return userList;
	}

	@Override
	public Page<User> queryGeneralAgent(PageRequest<QueryGeneralAgentDTO> pageRequest) throws Exception {

		Map<String, Object> map = new HashMap<String, Object>();

		QueryGeneralAgentDTO agentDTO = pageRequest.getSearchDo();

		if (StringUtils.isNotBlank(agentDTO.getUserName())) {
			map.put("account", agentDTO.getUserName());
		}
		if (true == agentDTO.isAccountMatch()) {
			map.put("isAccountMatch", true);
		}

		if (null != agentDTO.getFromRegisterDate()) {
			map.put("fromDate", agentDTO.getFromRegisterDate());
		}

		if (null != agentDTO.getToRegisterDate()) {
			map.put("toDate", agentDTO.getToRegisterDate());
		}

		// 一期无此字段
		if (null != agentDTO.getFromBal() && agentDTO.getFromBal() > 0) {
			map.put("fromBal", agentDTO.getFromBal());
		}
		// 一期无此字段
		if (null != agentDTO.getToBal() && agentDTO.getToBal() > 0) {
			map.put("toBal", agentDTO.getToBal());
		}

		if (StringUtils.isNotBlank(agentDTO.getEmail())) {
			map.put("email", agentDTO.getEmail());
		}
		if (agentDTO.getUserLvl() != null) {
			map.put("userLvl", agentDTO.getUserLvl());
		}

		Number totalCount = (Number) sqlSessionTemplate.selectOne("getCountByCustomerList", map);
		if (totalCount == null || totalCount.intValue() <= 0) {
			return new Page<User>(0);
		}

		Page<User> page = new Page<User>(pageRequest.getPageNumber(), pageRequest.getPageSize(), totalCount.intValue());

		Map<String, Object> filters = new HashMap<String, Object>();
		filters.put("offset", page.getFirstResult());
		filters.put("pageSize", page.getPageSize());
		filters.put("lastRows", page.getFirstResult() + page.getPageSize());
		filters.put("sortColumns", pageRequest.getSortColumns());
		filters.putAll(map);

		RowBounds rowBounds = new RowBounds(page.getFirstResult(), page.getPageSize());
		List<UserCustomer> list = sqlSessionTemplate.selectList("queryGeneralAgent", filters, rowBounds);

		List<User> userList = new ArrayList<User>();

		for (UserCustomer _customer : list) {

			try {

				// 将dao返回的vo转换成 服务层的实体类
				User user = VOConverter.customer2User(_customer);
				userList.add(user);
			} catch (Exception e) {

				log.error("查询客户列表，映射类转换 实体类出错；" + e.getMessage(), e);
				throw e;
			}
		}
		page.setResult(userList);

		return page;
	}

	@Override
	public void createUser(User user) throws Exception {

		UserCustomer userCustomer = VOConverter.convertUser2Customer(user);

		this.insert(userCustomer);
		user.setId(userCustomer.getId());
	}

	@Override
	public Page<User> searchFreezeUser(PageRequest<QueryFreezeUserDTO> pageReqeust) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();

		QueryFreezeUserDTO queryDTO = pageReqeust.getSearchDo();

		if (StringUtils.isNotBlank(queryDTO.getAccount())) {
			map.put("account", queryDTO.getAccount());
		}
		map.put("isFreeze", queryDTO.getIsFreeze());
		map.put("isAccountMatch", "1");
		map.put("sortColumns", queryDTO.getSortColumns());
		return getFreezeUser(map, pageReqeust);
	}

	private Page<User> getFreezeUser(Map<String, Object> map, PageRequest<QueryFreezeUserDTO> pageReqeust)
			throws Exception {
		Number totalCount = (Number) sqlSessionTemplate.selectOne("getCountByCustomerList", map);
		if (totalCount == null || totalCount.intValue() <= 0) {
			return new Page<User>(0);
		}

		Page<User> page = new Page<User>(pageReqeust.getPageNumber(), pageReqeust.getPageSize(), totalCount.intValue());

		Map<String, Object> filters = new HashMap<String, Object>();
		filters.put("offset", page.getFirstResult());
		filters.put("pageSize", page.getPageSize());
		filters.put("lastRows", page.getFirstResult() + page.getPageSize());
		filters.put("sortColumns", pageReqeust.getSortColumns());
		filters.putAll(map);

		RowBounds rowBounds = new RowBounds(page.getFirstResult(), page.getPageSize());
		List<UserCustomer> list = sqlSessionTemplate.selectList("queryCustomerList", filters, rowBounds);

		List<User> userList = new ArrayList<User>();

		for (UserCustomer userCustomer : list) {

			try {
				userCustomer.setSumBal(fundDao.getTeamFund(userCustomer.getId()));
				userCustomer.setBal(fundDao.getUserFund(userCustomer.getId()).getBal());
				User user = VOConverter.customer2User(userCustomer);
				userList.add(user);

			} catch (Exception e) {
				log.error("查询客户列表，映射类转换 实体类出错；" + e.getMessage(), e);
				throw e;
			}
		}
		page.setResult(userList);

		return page;
	}

	@Override
	public Page<FreezeLog> searchUnFreezeUserLog(PageRequest<QueryUnFreezeUserLogDTO> pageReqeust) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();

		QueryUnFreezeUserLogDTO queryDTO = pageReqeust.getSearchDo();

		if (StringUtils.isNotBlank(queryDTO.getAccount())) {
			map.put("account", queryDTO.getAccount());
		}
		return getUnFreezeUserLog(map, pageReqeust);
	}

	private Page<FreezeLog> getUnFreezeUserLog(Map<String, Object> map, PageRequest<QueryUnFreezeUserLogDTO> pageReqeust)
			throws Exception {
		Number totalCount = (Number) sqlSessionTemplate.selectOne("getUnFreezeLogCount", map);
		if (totalCount == null || totalCount.intValue() <= 0) {
			return new Page<FreezeLog>(0);
		}

		Page<FreezeLog> page = new Page<FreezeLog>(pageReqeust.getPageNumber(), pageReqeust.getPageSize(),
				totalCount.intValue());

		Map<String, Object> filters = new HashMap<String, Object>();
		filters.put("offset", page.getFirstResult());
		filters.put("pageSize", page.getPageSize());
		filters.put("lastRows", page.getFirstResult() + page.getPageSize());
		filters.put("sortColumns", pageReqeust.getSortColumns());
		if (pageReqeust.getSearchDo() != null && StringUtils.isNotEmpty(pageReqeust.getSearchDo().getSortColumns())) {
			filters.put("sortColumns", pageReqeust.getSearchDo().getSortColumns());

		}
		filters.putAll(map);

		RowBounds rowBounds = new RowBounds(page.getFirstResult(), page.getPageSize());
		List<UserFreezeLog> list = sqlSessionTemplate.selectList("queryUnFreezeLog", filters, rowBounds);

		List<FreezeLog> freezeLogList = new ArrayList<FreezeLog>();

		for (UserFreezeLog userFreezeLog : list) {

			try {

				log.info("=userFreezeLog: FreezeAccount:"+userFreezeLog.getFreezeAccount());
				log.info("=userFreezeLog: Memo:"+userFreezeLog.getMemo());
				
				FreezeLog FreezeLog = VOConverter.userFreezeLog2FreezeLog(userFreezeLog);
				freezeLogList.add(FreezeLog);

			} catch (Exception e) {
				log.error("查询客户解冻列表，映射类转换 实体类出错；" + e.getMessage(), e);
				throw e;
			}
		}
		page.setResult(freezeLogList);

		return page;
	}

	@Override
	public List<User> getAllUser() throws Exception {
		List<UserCustomer> customers = this.getAll();
		List<User> users = new ArrayList<User>();
		for (UserCustomer userCustomer : customers) {
			users.add(VOConverter.customer2User(userCustomer));
		}
		return users;
	}

	@Override
	public List<User> getUserByUserLvl(List<Long> userLvl, List<Long> vipLvl) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		if (!userLvl.isEmpty()) {
			map.put("userLvl", userLvl);
		}
		if (!vipLvl.isEmpty()) {
			int size = vipLvl.size();
			for (int i = 0; i < size; i++) {
				if (vipLvl.get(i).longValue() == 1) {
					map.put("vip", 1);
				} else if (vipLvl.get(i).longValue() == 0) {
					map.put("nonVip", 0);
				}
			}
		}
		List<UserCustomer> customers = this.sqlSessionTemplate.selectList("getByUserLvls", map);
		List<User> users = new ArrayList<User>();
		for (UserCustomer userCustomer : customers) {
			users.add(VOConverter.customer2User(userCustomer));
		}
		return users;
	}

	@Override
	public List<User> getUserByUserNames(List<String> userName) throws Exception {
		List<UserCustomer> customers = this.sqlSessionTemplate.selectList(
				"com.winterframework.firefrog.user.dao.vo.UserCustomer.getByNames", userName);
		List<User> users = new ArrayList<User>();
		for (UserCustomer userCustomer : customers) {
			users.add(VOConverter.customer2User(userCustomer));
		}
		return users;
	}

	@Override
	public SecurityCard querySecurityCardById(Long userId) {
		UserCustomer userCustomer = this.sqlSessionTemplate.selectOne("querySecurityCardById", userId);
		return VOConverter.customer2SecurityCard(userCustomer);
	}
	public Long  querySecurityCardByCard(String card){
		 return  this.sqlSessionTemplate.selectOne("querySecurityCardByCard", card);
		
	}


	@Override
	public void updateSecurityCard(SecurityCard securityCard) {
		UserCustomer userCustomer = VOConverter.convertSecurityCard2Customer(securityCard);
		userCustomer.setBindDate(new Date());
		this.sqlSessionTemplate.update("updateSecurityCard", userCustomer);
	}
	
	@Override
	public void cleanSafeCenter(UserCustomer userCustomer) {
		this.sqlSessionTemplate.update("cleanSafeCenter", userCustomer);		
	}
	
	@Override
	public void cleanPersonalInfo(UserCustomer userCustomer) {
		this.sqlSessionTemplate.update("cleanPersonalInfo", userCustomer);		
	}
	@Override
	public Long getCountByPage(UserCustomer userCustomer) {
		return this.sqlSessionTemplate.selectOne("getCountByAccountPasswd", userCustomer);
	}

	public Long userByName(String account) throws Exception {
		return this.sqlSessionTemplate.selectOne("userByName", account);
	}
	@Override
	public void updateUserAwardRetStatusUserAndSub(String userChain,Integer status) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();  
		map.put("userChain", userChain);
		map.put("status", status);
		this.sqlSessionTemplate.update("updateUserAwardRetStatusUserAndSub", map);
	}
	@Override
	public void updateUserSuperPairStatusUserAndSub(String userChain,
			Integer status) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();  
		map.put("userChain", userChain);
		map.put("status", status);
		this.sqlSessionTemplate.update("updateUserSuperPairStatusUserAndSub", map);
	}
	@Override
	public void updateUserLhcStatusUserAndSub(String userChain,
			Integer status) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();  
		map.put("userChain", userChain);
		map.put("status", status);
		this.sqlSessionTemplate.update("updateUserLhcStatusUserAndSub", map);
	}
	@Override
	public List<User> queryUserParent(String account) throws Exception {
		//		StringBuilder sb = new StringBuilder(1024);
		//		for (long id : ids) {
		//			sb.append(id);
		//			sb.append(",");
		//		}
		//		sb.deleteCharAt(sb.length() - 1);
		List<UserCustomer> cusList = sqlSessionTemplate.selectList("queryUserParent", account);
		List<User> userList = new ArrayList<User>();
		User user = null;
		for (UserCustomer cus : cusList) {
			user = VOConverter.customer2User(cus);
			userList.add(user);
		}
		return userList;
	}
}
