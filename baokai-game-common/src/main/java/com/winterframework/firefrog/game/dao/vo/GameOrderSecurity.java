package com.winterframework.firefrog.game.dao.vo;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import com.winterframework.orm.dal.ibatis3.BaseEntity;

/**
 * @author cms group
 * @version 1.0
 * @since 1.0
 */

public class GameOrderSecurity extends BaseEntity {

	private static final long serialVersionUID = 6231645923637020486L;
	//alias
	public static final String TABLE_ALIAS = "GameOrderSecurity";
	public static final String ALIAS_ORDER_ID = "订单ID";
	public static final String ALIAS_UNIQUENESS_CODE = "uniquenessCode";
	public static final String ALIAS_CREATE_TIME = "创建时间";

	//date formats

	//columns START
	private Long orderId;
	private Long uniquenessCode;
	private Object createTime;

	//columns END

	public GameOrderSecurity() {
	}

	public GameOrderSecurity(Long id) {
		this.id = id;
	}

	public void setOrderId(Long value) {
		this.orderId = value;
	}

	public Long getOrderId() {
		return this.orderId;
	}

	public void setUniquenessCode(Long value) {
		this.uniquenessCode = value;
	}

	public Long getUniquenessCode() {
		return this.uniquenessCode;
	}

	public void setCreateTime(Object value) {
		this.createTime = value;
	}

	public Object getCreateTime() {
		return this.createTime;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this).append("Id", getId()).append("OrderId", getOrderId())
				.append("UniquenessCode", getUniquenessCode()).append("CreateTime", getCreateTime()).toString();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(getId()).append(getOrderId()).append(getUniquenessCode())
				.append(getCreateTime()).toHashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof GameOrderSecurity == false) {
			return false;
		}
		if (this == obj) {
			return true;
		}
		GameOrderSecurity other = (GameOrderSecurity) obj;
		return new EqualsBuilder().append(getId(), other.getId())

		.append(getOrderId(), other.getOrderId())

		.append(getUniquenessCode(), other.getUniquenessCode())

		.append(getCreateTime(), other.getCreateTime())

		.isEquals();
	}
}
