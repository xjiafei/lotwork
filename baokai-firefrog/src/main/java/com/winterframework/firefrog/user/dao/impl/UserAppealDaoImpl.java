package com.winterframework.firefrog.user.dao.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Repository;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.winterframework.firefrog.user.dao.IUserAppealDao;
import com.winterframework.firefrog.user.dao.IUserCustomerDao;
import com.winterframework.firefrog.user.dao.vo.UserAppeal;
import com.winterframework.firefrog.user.dao.vo.VOConverter;
import com.winterframework.firefrog.user.entity.Appeal;
import com.winterframework.firefrog.user.entity.User;
import com.winterframework.firefrog.user.entity.UserAppealQueryDTO;
import com.winterframework.modules.page.Page;
import com.winterframework.modules.page.PageRequest;
import com.winterframework.orm.dal.ibatis3.BaseIbatis3Dao;

@Repository("userAppealDaoImpl")
public class UserAppealDaoImpl extends BaseIbatis3Dao<UserAppeal> implements IUserAppealDao {

	@Resource(name = "userCustomerDaoImpl")
	public IUserCustomerDao userCustomerDao;

	@Override
	public void saveUserAppeal(Appeal appeal) throws JsonProcessingException {
		UserAppeal userAppeal = VOConverter.appeal2UserAppeal(appeal);
		insert(userAppeal);
	}

	@Override
	public Page<Appeal> searchUserAppeal(PageRequest<UserAppealQueryDTO> pageReqeust) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();

		UserAppealQueryDTO queryDTO = pageReqeust.getSearchDo();

		if (StringUtils.isNotBlank(queryDTO.getAccount())) {
			map.put("account", queryDTO.getAccount());
		}

		if (StringUtils.isNotBlank(queryDTO.getOperater())) {
			map.put("operatorAccount", queryDTO.getOperater());
		}

		if (null != queryDTO.getAppealType()) {
			map.put("appeal_type", queryDTO.getAppealType());
		}

		if (null != queryDTO.getPassed()) {
			map.put("passed", queryDTO.getPassed());
		}

		return getUserAppeal(map, pageReqeust);
	}

	private Page<Appeal> getUserAppeal(Map<String, Object> map, PageRequest<UserAppealQueryDTO> pageReqeust)
			throws Exception {
		Number totalCount = (Number) sqlSessionTemplate.selectOne("getCountByUserAppealList", map);
		if (totalCount == null || totalCount.intValue() <= 0) {
			return new Page<Appeal>(0);
		}

		Page<Appeal> page = new Page<Appeal>(pageReqeust.getPageNumber(), pageReqeust.getPageSize(),
				totalCount.intValue());

		Map<String, Object> filters = new HashMap<String, Object>();
		filters.put("offset", page.getFirstResult());
		filters.put("pageSize", page.getPageSize());
		filters.put("lastRows", page.getFirstResult() + page.getPageSize());
		filters.put("sortColumns", pageReqeust.getSortColumns());
		filters.putAll(map);

		RowBounds rowBounds = new RowBounds(page.getFirstResult(), page.getPageSize());
		List<UserAppeal> list = sqlSessionTemplate.selectList("queryUserAppealList", filters, rowBounds);

		List<Appeal> appealList = new ArrayList<Appeal>();

		for (UserAppeal _appeal : list) {

			try {

				Appeal appeal = VOConverter.userAppeal2Appeal(_appeal);
				appealList.add(appeal);

			} catch (Exception e) {
				log.error("查询客户列表，映射类转换 实体类出错；" + e.getMessage(), e);
				throw e;
			}
		}
		page.setResult(appealList);

		return page;
	}

	@Override
	public Appeal getUserAppealDetail(int userAppleId) throws Exception {
		UserAppeal userAppeal = getById(Long.valueOf(userAppleId));
		Appeal appeal = null;
		if (userAppeal != null) {
			appeal = VOConverter.userAppeal2Appeal(userAppeal);
		}
		return appeal;
	}

	@Override
	public void userAppealAudit(Appeal appeal) throws Exception {
		UserAppeal userAppeal = VOConverter.appeal2UserAppeal(appeal);
		userAppeal.setActivedDays(appeal.getActivedDays());
		if (null != appeal.getOperater()) {
			User user = userCustomerDao.queryUserById(appeal.getOperater());
			if (null != user && null != user.getUserProfile()) {
				userAppeal.setOperaterAccount(user.getUserProfile().getAccount());
			}
		}
		update(userAppeal);
	}

}
