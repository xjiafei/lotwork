package com.winterframework.firefrog.user.dao.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.winterframework.firefrog.common.util.DataConverterUtil;
import com.winterframework.firefrog.game.dao.vo.UserCenterReportVo;
import com.winterframework.firefrog.game.entity.UserCenterReportInfo;
import com.winterframework.firefrog.game.web.dto.SubUserReportRequest;
import com.winterframework.firefrog.game.web.dto.UserCenterReportComplexRequest;
import com.winterframework.firefrog.user.dao.IUserCustomerDao;
import com.winterframework.firefrog.user.dao.vo.UserCustomer;
import com.winterframework.firefrog.user.dao.vo.UserFreezeLog;
import com.winterframework.firefrog.user.dao.vo.VOConverter;
import com.winterframework.firefrog.user.entity.CustomerQueryDTO;
import com.winterframework.firefrog.user.entity.FreezeDTO;
import com.winterframework.firefrog.user.entity.FreezeLog;
import com.winterframework.firefrog.user.entity.FreezeLog.FrozenAction;
import com.winterframework.firefrog.user.entity.QueryFreezeUserDTO;
import com.winterframework.firefrog.user.entity.QueryGeneralAgentDTO;
import com.winterframework.firefrog.user.entity.QueryUnFreezeUserLogDTO;
import com.winterframework.firefrog.user.entity.UnFreezeDTO;
import com.winterframework.firefrog.user.entity.User;
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
	
	private Logger log = LoggerFactory.getLogger(UserCustomerDaoImpl.class);

	@Override
	public UserProfile selectUserProfileById(int id) {
		return null;
	}

	public Page<User> getAllCustomer(Map<String, Object> map, PageRequest<?> pageRequest) throws Exception {

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
		List<UserCustomer> list = sqlSessionTemplate.selectList("queryCustomerList", filters, rowBounds);

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

	/**
	 * 客户列表查询
	 */
	@Override
	public Page<User> queryCustomer(PageRequest<CustomerQueryDTO> pageRequst) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();

		CustomerQueryDTO queryDTO = pageRequst.getSearchDo();

		if (StringUtils.isNotBlank(queryDTO.getAccount())) {
			map.put("account", queryDTO.getAccount());
		}

		if (StringUtils.isNotBlank(queryDTO.getEmail())) {
			map.put("email", queryDTO.getEmail());
		}

		if (null != queryDTO.getUserLvl()) {
			map.put("userLvl", queryDTO.getUserLvl());
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

		if (null != queryDTO.getParentId() && queryDTO.getParentId() > 0) {
			map.put("parentId", queryDTO.getParentId());

		}

		if (null != queryDTO.getParentChainId() && queryDTO.getParentChainId() > 0) {

			//			map.put("userChainId", queryDTO.getParentChainId());
			UserCustomer userCustomer = getById(queryDTO.getParentChainId());
			map.put("userChain", userCustomer.getUserChain());
		}

		if (null != queryDTO.getFromLoginDate()) {
			map.put("fromLoginDate", queryDTO.getFromLoginDate());
		}

		if (null != queryDTO.getToLoginDate()) {
			map.put("toLoginDate", queryDTO.getToLoginDate());
		}

		if (true == queryDTO.isAccountMatch()) {
			map.put("isAccountMatch", true);
		}

		return getAllCustomer(map, pageRequst);

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
	public User getUserByNickName(String nickName) throws Exception {
		UserCustomer uc = new UserCustomer();
		uc.setNickName(nickName);
		List<UserCustomer> ucs = getAllByEntity(uc);
		if (!ucs.isEmpty()) {
			return VOConverter.customer2User(ucs.get(0));
		}
		return null;
	}
	
	@Override
	public User queryUserById(Long id) throws Exception {
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
		UserCustomer uc = new UserCustomer();
		uc.setAccount(username);
		List<UserCustomer> ucs = getAllByEntity(uc);
		User user = null;
		if (!ucs.isEmpty()) {
			user = VOConverter.customer2User(ucs.get(0));
		}
		return user;
	}
	@Override
	public User queryCustomerByUserChain(String userChain) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userChain", userChain);
		UserCustomer uc = this.sqlSessionTemplate.selectOne("queryCustomerByUserChain", map);
		User user = null;
		if (null!=uc) {
			user = VOConverter.customer2User(uc);
		}
		return user;
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

		return getAllCustomer(map, pageRequest);
	}

	@Override
	public void createUser(User user) throws Exception {

		UserCustomer userCustomer = VOConverter.convertUser2Customer(user);

		this.insert(userCustomer);
	}

	@Override
	public Page<User> searchFreezeUser(PageRequest<QueryFreezeUserDTO> pageReqeust) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();

		QueryFreezeUserDTO queryDTO = pageReqeust.getSearchDo();

		if (StringUtils.isNotBlank(queryDTO.getAccount())) {
			map.put("account", queryDTO.getAccount());
		}
		map.put("isFreeze", queryDTO.getIsFreeze());
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
	public Page<UserCenterReportInfo> queryUserCenterReportInfo(PageRequest<SubUserReportRequest> pr) {
		
		Map<String, Object> queryParamMap = makeQueryParamMap1(pr.getSearchDo());
		
		Map<String, Object> filters = new HashMap<String, Object>();
		filters.putAll(queryParamMap);
		
		Long totalCount = this.sqlSessionTemplate.selectOne("getUserReportByBetTypeCodeCount",filters);
		
		log.info("totalCount : " + totalCount);
		
		if (totalCount == null || totalCount <= 0) {
			return new Page<UserCenterReportInfo>(0);
		}
		
		Page<UserCenterReportInfo> page = new Page<UserCenterReportInfo>(pr.getPageNumber(), pr.getPageSize(),totalCount.intValue());

		new HashMap<String, Object>();
		filters.put("offset", page.getFirstResult());
		filters.put("pageSize", page.getPageSize());
		filters.put("lastRows", page.getFirstResult() + page.getPageSize());
		filters.put("sortColumns", pr.getSortColumns());

		RowBounds rowBounds = new RowBounds(page.getFirstResult(), page.getPageSize());
		
		List<UserCenterReportVo> userCenterReportVos = this.sqlSessionTemplate.selectList("getUserReportByBetTypeCodeList", filters,rowBounds);

		List<UserCenterReportInfo> userCenterReportInfos = new ArrayList<UserCenterReportInfo>();
		
		for (UserCenterReportVo gi : userCenterReportVos) {
			UserCenterReportInfo userCenterReportInfo;
			
			if(pr.getSearchDo().getUserId().toString().equals(gi.getUserId().toString())){
				filters.put("lev", 1L);
				List<UserCenterReportVo> ownModel = this.sqlSessionTemplate.selectList("getUserReportByBetTypeCodeList", filters,rowBounds);
				userCenterReportInfo = VOConverter.userCenterReportVo2UserCenterReportInfo(ownModel.get(0));
			}else{
				userCenterReportInfo = VOConverter.userCenterReportVo2UserCenterReportInfo(gi);
			}
			userCenterReportInfos.add(userCenterReportInfo);
		}
		
		page.setResult(userCenterReportInfos);
		return page;
	}

	@Override
	public Page<UserCenterReportInfo> getUserReportByBetTypeCodeList(PageRequest<UserCenterReportComplexRequest> pr,Long userId) {
		Map<String, Object> queryParamMap = makeQueryParamMap(pr.getSearchDo(),userId);
		
		//修正為應該只有該使用者著資料
		Number totalCount = 1;
		log.info("totalCount intValue : " +totalCount.intValue() + ",totalCount :" +totalCount);
		
		if (totalCount == null || totalCount.intValue() <= 0) {
			return new Page<UserCenterReportInfo>(0);
		}
		
		log.info("totalCount : " + totalCount);
		
		Page<UserCenterReportInfo> page = new Page<UserCenterReportInfo>(pr.getPageNumber(), pr.getPageSize(), totalCount.intValue());
		
		Map<String, Object> filters = new HashMap<String, Object>();
		filters.put("offset", page.getFirstResult());
		filters.put("pageSize", page.getPageSize());
		filters.put("lastRows", page.getFirstResult() + page.getPageSize());
		filters.put("sortColumns", pr.getSortColumns());
		filters.putAll(queryParamMap);
		log.info("filters : " + filters.toString());
		RowBounds rowBounds = new RowBounds(page.getFirstResult(), page.getPageSize());
		List<UserCenterReportVo> userCenterReportVos = this.sqlSessionTemplate.selectList("getUserReportByBetTypeCodeList", filters,rowBounds);
		List<UserCenterReportInfo> userCenterReportInfos = new ArrayList<UserCenterReportInfo>();
		for (UserCenterReportVo gi : userCenterReportVos) {
			UserCenterReportInfo userCenterReportInfo = VOConverter.userCenterReportVo2UserCenterReportInfo(gi);
			userCenterReportInfos.add(userCenterReportInfo);
		}
		page.setResult(userCenterReportInfos);
		return page;
	}
	
	@Override
	public Page<UserCenterReportInfo> queryUserCenterReportByComplexCondition(
			PageRequest<UserCenterReportComplexRequest> pr,Long userId) {
		Map<String, Object> queryParamMap = makeQueryParamMap(pr.getSearchDo(),userId);
		log.info("dao queryUserCenterReportByComplexCondition userId : " + userId);
		//修正為應該只有該使用者著資料
		Number totalCount = 1;
		log.info("totalCount intValue : " +totalCount.intValue() + ",totalCount :" +totalCount);
		if (totalCount == null || totalCount.intValue() <= 0) {
			return new Page<UserCenterReportInfo>(0);
		}
		log.info("totalCount : " + totalCount);
		Page<UserCenterReportInfo> page = new Page<UserCenterReportInfo>(pr.getPageNumber(), pr.getPageSize(), totalCount.intValue());
		
		Map<String, Object> filters = new HashMap<String, Object>();
		filters.put("offset", page.getFirstResult());
		filters.put("pageSize", page.getPageSize());
		filters.put("lastRows", page.getFirstResult() + page.getPageSize());
		filters.put("sortColumns", pr.getSortColumns());
		filters.putAll(queryParamMap);
		log.info("filters : " + filters.toString());
		RowBounds rowBounds = new RowBounds(page.getFirstResult(), page.getPageSize());
		List<UserCenterReportVo> userCenterReportVos = this.sqlSessionTemplate.selectList("queryUserCenterReportTotalByConditionAndBetTypeCode", filters,rowBounds);
		List<UserCenterReportInfo> userCenterReportInfos = new ArrayList<UserCenterReportInfo>();
		for (UserCenterReportVo gi : userCenterReportVos) {
			filters.put("account", gi.getAccount());
			UserCenterReportInfo userCenterReportInfo = VOConverter.userCenterReportVo2UserCenterReportInfo(gi);
			userCenterReportInfos.add(userCenterReportInfo);
		}
		page.setResult(userCenterReportInfos);
		return page;
	}

	private Map<String, Object> makeQueryParamMap(UserCenterReportComplexRequest userCenterReportComplexRequest, Long userId) {

		log.info("=makeQueryParamMap Start");
		
		Map<String, Object> map = new HashMap<String, Object>();
		//玩法分類
		String[] betTypeSetUpArr = {"gamePlayTypeId" ,"GAME_GROUP_CODE" ,"GAME_SET_CODE" ,"BET_METHOD_CODE"};
	    
	    Long lotteryId = userCenterReportComplexRequest.getLotteryId();
	    String betTypeCode = userCenterReportComplexRequest.getBetTypeCode();
	    String account = userCenterReportComplexRequest.getAccount();
	    Long queryTime = userCenterReportComplexRequest.getQueryTime();
	    
	    log.info("userId : " + userId);
	    log.info("lotteryId : " + lotteryId);
	    log.info("betTypeCode : " + betTypeCode);
	    log.info("account : " + account);
	    log.info("queryTime : " + queryTime);
	    
		if (null != lotteryId) {
			
			if(lotteryId != 0L){
				map.put("lotteryId", lotteryId);
			}
			
			if(null != betTypeCode){ //新增玩法條件
				String[] betTypeArr = betTypeCode.split("_");				
				for(int i=0; i< betTypeSetUpArr.length; i++){
					if(betTypeArr.length > i){
						map.put(betTypeSetUpArr[i], betTypeArr[i]);
					}else{
						map.put(betTypeSetUpArr[i], null);
					}
				}
			}
			
		}
		
		if (null != account) {
			map.put("account", account);
		}
		if (null != queryTime) {
			map.put("startTime", DataConverterUtil.convertLong2Date(userCenterReportComplexRequest.getQueryTime()));
		}
		
		map.put("userId", userId);
		
		log.info("map : " + map.toString());
		
		log.info("=makeQueryParamMap End");
		
		return map;
	}
	
	
	private Map<String, Object> makeQueryParamMap1(SubUserReportRequest subUserReportRequest) {
		
		log.info("=makeQueryParamMap1 Start");
		
		String[] betTypeSetUpArr = {"gamePlayTypeId" ,"GAME_GROUP_CODE" ,"GAME_SET_CODE" ,"BET_METHOD_CODE"}; //玩法分類
		Map<String, Object> map = new HashMap<String, Object>();
		
		Long lotteryId = subUserReportRequest.getLotteryId();
		String betTypeCode = subUserReportRequest.getBetTypeCode();
		String account = subUserReportRequest.getAccount();
		Long queryTime = subUserReportRequest.getQueryTime();
		Long userId = subUserReportRequest.getUserId();
		
		if (null != lotteryId) {
			
			if(lotteryId != 0L){
				map.put("lotteryId", lotteryId);
			}
			
			if(null != betTypeCode){ //新增玩法條件
				String[] betTypeArr = betTypeCode.split("_");				
				for(int i=0; i< betTypeSetUpArr.length; i++){
					if(betTypeArr.length > i){
						map.put(betTypeSetUpArr[i], betTypeArr[i]);
					}else{
						map.put(betTypeSetUpArr[i], null);
					}
				}
			}
		}
		
		if (null != account) {
			map.put("account", account);
		}
		
		if(null != lotteryId && lotteryId.intValue() == 0){
			map.put("lotteryId", null);
			for(String attr : betTypeSetUpArr){
					map.put(attr, null);
			}
		}
		
		if (null != queryTime) {
			map.put("startTime", DataConverterUtil.convertLong2Date(queryTime));
		}
		
		map.put("userId", userId);
		map.put("parentId", userId);
		
		log.info("makeQueryParamMap1 map : " + map.toString());
		
		log.info("=makeQueryParamMap1 End");
		
		return map;
	}

	@Override
	public UserCenterReportInfo getCurrentUserReportByUserId(Long userId) {
		UserCenterReportVo userCenterReportVo = sqlSessionTemplate.selectOne("queryCurrentUserReport",userId);
		return VOConverter.userCenterReportVo2UserCenterReportInfo(userCenterReportVo);
	}
	
	@Override
	public String getUserNameById(Long userId) {
		try {
			UserCustomer user = getById(userId);
			if(user!=null){
				return user.getAccount();
			}else{
				return "";
			}
		} catch (Exception e) {
			return "";
		}
		
		
	}
}
