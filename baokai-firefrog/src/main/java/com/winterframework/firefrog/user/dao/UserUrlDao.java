/**
* Copyright (c) 2005-2012 winterframework.com
* Licensed under the Apache License, Version 2.0 (the "License");
*/
package com.winterframework.firefrog.user.dao;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.PageBeanUtilsBean;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Component;

import com.winterframework.firefrog.user.entity.UserUrl;
import com.winterframework.modules.page.Page;
import com.winterframework.modules.page.PageRequest;
import com.winterframework.orm.dal.ibatis3.BaseIbatis3Dao;

/**
 * @author cms group
 * @version 1.0
 * @since 1.0
 */

@Component
public class UserUrlDao extends BaseIbatis3Dao<UserUrl> {
	protected <R> Page<UserUrl> pageQuery(PageRequest<R> pageRequest, String prefix, String postfix) {
		String selectPageCount = "getCountByPage";
		String selectPage = "getByPage";

		if (StringUtils.isNotBlank(prefix)) {
			selectPageCount = prefix + "_" + selectPageCount;
			selectPage = prefix + "_" + selectPage;
		}
		if (StringUtils.isNotBlank(postfix)) {
			selectPageCount = selectPageCount + "_" + postfix;
			selectPage = selectPage + "_" + postfix;
		}
		Map<String, Object> param = null;
		try {
			param = new PageBeanUtilsBean().describe(pageRequest.getSearchDo());
			param.putAll(pageRequest.getFilters());
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		}
		Object str = param.get("type");
		param.put("type", null);
		Map<String, Object> totalCountMap = sqlSessionTemplate.selectOne(this.getQueryPath(selectPageCount), param);

		Integer totalCount = null;
		if (totalCountMap == null) {
			totalCount = 0;
		} else {
			if (str == null) {
				totalCount = Integer.valueOf(String.valueOf(totalCountMap.get("A")))
						+ Integer.valueOf(String.valueOf(totalCountMap.get("B")));

			} else if ("1".equals(String.valueOf(str))) {
				totalCount = Integer.valueOf(String.valueOf(totalCountMap.get("A")));
			} else {
				totalCount = Integer.valueOf(String.valueOf(totalCountMap.get("B")));

			}
		}
		log.info("return zeor:" + totalCountMap);
		Page<UserUrl> page = new Page<UserUrl>(pageRequest, totalCount.intValue());
		page.setOtherCount(totalCountMap);
		if (totalCount == null || totalCount.intValue() <= 0) {
			log.info("return zeor:" + totalCount);
			return page;
		}

		// 其它分页参数,用于不喜欢或是因为兼容性而不使用方言(Dialect)的分页用户使用.
		// 与getSqlMapClientTemplate().queryForList(statementName,
		// parameterObject)配合使用

		Map<String, Object> filters = new HashMap<String, Object>();
		filters.put("offset", page.getFirstResult());
		filters.put("pageSize", page.getPageSize());
		filters.put("lastRows", page.getFirstResult() + page.getPageSize());
		filters.put("sortColumns", pageRequest.getSortColumns());
		param.put("type", str);
		filters.putAll(param);
		try {
			param = new PageBeanUtilsBean().describe(pageRequest.getSearchDo());
			param.putAll(pageRequest.getFilters());
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		}
		// 混合两个filters为一个filters,MapAndObject.get()方法将在两个对象取值,Map如果取值为null,则再在Bean中取值
		RowBounds rowBounds = new RowBounds(page.getFirstResult(), page.getPageSize());
		List<UserUrl> list = sqlSessionTemplate.selectList(this.getQueryPath(selectPage), filters, rowBounds);
		page.setResult(list);
		return page;
	}
}
