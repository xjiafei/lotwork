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

public class GameWinOrder extends BaseEntity {

	private static final long serialVersionUID = -32194109244532994L;
	//alias
	public static final String TABLE_ALIAS = "中奖订单表";
	public static final String ALIAS_ORDERID = "订单ID";
	public static final String ALIAS_USERID = "用户ID";
	public static final String ALIAS_ISSUE_CODE = "奖期";
	public static final String ALIAS_LOTTORYID = "彩种";
	public static final String ALIAS_COUNT_WIN = "总计奖金";
	public static final String ALIAS_ORDER_TIME = "订单创建时间";
	public static final String ALIAS_CALCULATE_WIN_TIME = "计奖时间";
	public static final String ALIAS_SALE_TIME = "销售时间";
	public static final String ALIAS_ACTUAL_WIN = "实际奖金";

	//date formats

	//columns START
	private Long orderid;
	private Long userid;
	private Long issueCode;
	private Integer lottoryid;
	private Long countWin;
	private Object orderTime;
	private Object calculateWinTime;
	private Object saleTime;
	private Long actualWin;

	//columns END

	public GameWinOrder() {
	}

	public GameWinOrder(Long id) {
		this.id = id;
	}

	public void setOrderid(Long value) {
		this.orderid = value;
	}

	public Long getOrderid() {
		return this.orderid;
	}

	public void setUserid(Long value) {
		this.userid = value;
	}

	public Long getUserid() {
		return this.userid;
	}

	public void setIssueCode(Long value) {
		this.issueCode = value;
	}

	public Long getIssueCode() {
		return this.issueCode;
	}

	public void setLottoryid(Integer value) {
		this.lottoryid = value;
	}

	public Integer getLottoryid() {
		return this.lottoryid;
	}

	public void setCountWin(Long value) {
		this.countWin = value;
	}

	public Long getCountWin() {
		return this.countWin;
	}

	public void setOrderTime(Object value) {
		this.orderTime = value;
	}

	public Object getOrderTime() {
		return this.orderTime;
	}

	public void setCalculateWinTime(Object value) {
		this.calculateWinTime = value;
	}

	public Object getCalculateWinTime() {
		return this.calculateWinTime;
	}

	public void setSaleTime(Object value) {
		this.saleTime = value;
	}

	public Object getSaleTime() {
		return this.saleTime;
	}

	public void setActualWin(Long value) {
		this.actualWin = value;
	}

	public Long getActualWin() {
		return this.actualWin;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this).append("Id", getId()).append("Orderid", getOrderid())
				.append("Userid", getUserid()).append("IssueCode", getIssueCode()).append("Lottoryid", getLottoryid())
				.append("CountWin", getCountWin()).append("OrderTime", getOrderTime())
				.append("CalculateWinTime", getCalculateWinTime()).append("SaleTime", getSaleTime())
				.append("ActualWin", getActualWin()).toString();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(getId()).append(getOrderid()).append(getUserid()).append(getIssueCode())
				.append(getLottoryid()).append(getCountWin()).append(getOrderTime()).append(getCalculateWinTime())
				.append(getSaleTime()).append(getActualWin()).toHashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof GameWinOrder == false) {
			return false;
		}
		if (this == obj) {
			return true;
		}
		GameWinOrder other = (GameWinOrder) obj;
		return new EqualsBuilder().append(getId(), other.getId())

		.append(getOrderid(), other.getOrderid())

		.append(getUserid(), other.getUserid())

		.append(getIssueCode(), other.getIssueCode())

		.append(getLottoryid(), other.getLottoryid())

		.append(getCountWin(), other.getCountWin())

		.append(getOrderTime(), other.getOrderTime())

		.append(getCalculateWinTime(), other.getCalculateWinTime())

		.append(getSaleTime(), other.getSaleTime())

		.append(getActualWin(), other.getActualWin())

		.isEquals();
	}
}
