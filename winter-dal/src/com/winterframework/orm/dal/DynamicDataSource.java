package com.winterframework.orm.dal;

import java.util.Map;

import javax.sql.DataSource;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import com.winterframework.modules.utils.ReflectionUtils;

public class DynamicDataSource extends AbstractRoutingDataSource {
	@Override
	protected Object determineCurrentLookupKey() {
		return CustomerContextHolder.getCustomerType();
	}

	@SuppressWarnings("unchecked")
	public Map<String, DataSource> getTargetDataSources() {
		return (Map<String, DataSource>) ReflectionUtils.getFieldValue(this, "targetDataSources");
	}

}