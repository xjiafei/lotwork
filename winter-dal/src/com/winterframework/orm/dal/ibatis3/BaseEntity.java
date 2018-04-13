package com.winterframework.orm.dal.ibatis3;

import java.util.Date;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.winterframework.modules.utils.DateConvertUtils;

@SuppressWarnings("serial")
public class BaseEntity implements java.io.Serializable {
	public static final String ALIAS_GMT_CREATED = "创建日期";
	public static final String ALIAS_GMT_MODIFIED = "编缉日期";
	public static final String ALIAS_CREATOR = "创建人";
	public static final String ALIAS_MODIFIER = "编缉人";
	public static final String ALIAS_IS_DELETED = "是否有效";
	protected Long id;
	protected Boolean isDeleted;
	protected Long creator;
	protected Long modifier;
	protected Date gmtCreated;
	protected Date gmtModified;

	public BaseEntity() {
	}

	public BaseEntity(Long id) {
		this.id = id;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	};

	public Long getCreator() {
		return creator;
	}

	public void setCreator(Long creator) {
		this.creator = creator;
	}

	public Long getModifier() {
		return modifier;
	}

	public void setModifier(Long modifier) {
		this.modifier = modifier;
	}

	public Date getGmtCreated() {
		return gmtCreated;
	}

	public void setGmtCreated(Date gmtCreated) {
		this.gmtCreated = gmtCreated;
	}

	public String getGmtCreatedString() {
		return date2String(getGmtCreated(), DATE_TIME_FORMAT);
	}

	public void setGmtCreatedString(String value) {
		setGmtCreated(string2Date(value, DATE_TIME_FORMAT, java.sql.Timestamp.class));
	}

	public Date getGmtModified() {
		return gmtModified;
	}

	public void setGmtModified(Date gmtModified) {
		this.gmtModified = gmtModified;
	}

	public String getGmtModifiedString() {
		return date2String(getGmtModified(), DATE_TIME_FORMAT);
	}

	public void setGmtModifiedString(String value) {
		setGmtModified(string2Date(value, DATE_TIME_FORMAT, java.sql.Timestamp.class));
	}

	public Boolean getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(Boolean isDeleted) {
		this.isDeleted = isDeleted;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
	}

	protected static final String DATE_FORMAT = "yyyy-MM-dd";

	protected static final String TIME_FORMAT = "HH:mm:ss";

	protected static final String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";

	protected static final String TIMESTAMP_FORMAT = "yyyy-MM-dd HH:mm:ss.S";

	public static String date2String(java.util.Date date, String dateFormat) {
		return DateConvertUtils.format(date, dateFormat);
	}

	public static <T extends java.util.Date> T string2Date(String dateString, String dateFormat,
			Class<T> targetResultType) {
		return DateConvertUtils.parse(dateString, dateFormat, targetResultType);
	}

}
