/**
* Copyright (C) abba, 2010
*/
package com.winterframework.modules.page;

/**
* 
* @author abba(xuhengbiao@gmail.com) in 2010-4-30
* @since 1.0
*/
public class FieldWrapper {
	private String fieldName;
	private Object fieldValue;
	private MatchType matchType;

	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public Object getFieldValue() {
		return fieldValue;
	}

	public void setFieldValue(Object fieldValue) {
		this.fieldValue = fieldValue;
	}

	public MatchType getMatchType() {
		return matchType;
	}

	public void setMatchType(MatchType matchType) {
		this.matchType = matchType;
	}

}
