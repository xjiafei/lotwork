package com.winterframework.firefrog.game.dao.vo;

import java.util.Date;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import com.winterframework.orm.dal.ibatis3.BaseEntity;

/**
 * @author cms group
 * @version 1.0
 * @since 1.0
 */

public class GameWarnOrderLog extends BaseEntity {

	//alias
	public static final String TABLE_ALIAS = "订单警告处理日志表";
	public static final String ALIAS_WARN_ORDER_ID = "风险订单ID";
	public static final String ALIAS_CREATE_TIME = "创建时间";
	public static final String ALIAS_DISPOSE_INFO = "处理内容";
	public static final String ALIAS_DISPOSE_MEMO = "处理备注";
	public static final String ALIAS_DISPOSE_USER = "处理人";
	public static final String ALIAS_DISPOSE_TYPE = "处理类型 1 审核通过 2 审核不通过";

	//date formats

	//columns START
	private Long warnOrderId;
	private Date createTime;
	private String disposeInfo;
	private String disposeMemo;
	private String disposeUser;
	private Long disposeType;

	//columns END

	public GameWarnOrderLog() {
	}

	public GameWarnOrderLog(Long id) {
		this.id = id;
	}

	public GameWarnOrderLog(Date date, Long warnOrderId) {
		this.createTime = date;
		this.warnOrderId = warnOrderId;
	}

	public void setWarnOrderId(Long value) {
		this.warnOrderId = value;
	}

	public Long getWarnOrderId() {
		return this.warnOrderId;
	}

	public void setCreateTime(Date value) {
		this.createTime = value;
	}

	public Date getCreateTime() {
		return this.createTime;
	}

	public void setDisposeInfo(String value) {
		this.disposeInfo = value;
	}

	public String getDisposeInfo() {
		return this.disposeInfo;
	}

	public void setDisposeMemo(String value) {
		this.disposeMemo = value;
	}

	public String getDisposeMemo() {
		return this.disposeMemo;
	}

	public void setDisposeUser(String value) {
		this.disposeUser = value;
	}

	public String getDisposeUser() {
		return this.disposeUser;
	}

	public void setDisposeType(Long value) {
		this.disposeType = value;
	}

	public Long getDisposeType() {
		return this.disposeType;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this).append("Id", getId()).append("WarnOrderId", getWarnOrderId())
				.append("CreateTime", getCreateTime()).append("DisposeInfo", getDisposeInfo())
				.append("DisposeMemo", getDisposeMemo()).append("DisposeUser", getDisposeUser())
				.append("DisposeType", getDisposeType()).toString();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(getId()).append(getWarnOrderId()).append(getCreateTime())
				.append(getDisposeInfo()).append(getDisposeMemo()).append(getDisposeUser()).append(getDisposeType())
				.toHashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof GameWarnOrderLog == false)
			return false;
		if (this == obj)
			return true;
		GameWarnOrderLog other = (GameWarnOrderLog) obj;
		return new EqualsBuilder().append(getId(), other.getId())

		.append(getWarnOrderId(), other.getWarnOrderId())

		.append(getCreateTime(), other.getCreateTime())

		.append(getDisposeInfo(), other.getDisposeInfo())

		.append(getDisposeMemo(), other.getDisposeMemo())

		.append(getDisposeUser(), other.getDisposeUser())

		.append(getDisposeType(), other.getDisposeType())

		.isEquals();
	}
}
