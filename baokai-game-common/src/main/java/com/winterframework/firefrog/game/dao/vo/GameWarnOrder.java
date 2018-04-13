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

/**
 * @author user
 *
 */
/**
 * @author user
 *
 */
/**
 * @author user
 *
 */
public class GameWarnOrder extends BaseEntity {
	
	//alias
	public static final String TABLE_ALIAS = "订单警告表";
	public static final String ALIAS_LOTTERYID = "彩种";
	public static final String ALIAS_ISSUE_CODE = "奖期";
	public static final String ALIAS_WEB_ISSUE_CODE = "WEB显示奖期";
	public static final String ALIAS_ORDER_CODE = "订单编码";
	public static final String ALIAS_COUNT_WIN = "订单总奖金";
	public static final String ALIAS_WINS_RATIO = "订单中投比";
	public static final String ALIAS_MAXSLIP_WINS = "单注最大奖金";
	public static final String ALIAS_SLIP_WINSRATIO = "单注最大中投比";
	public static final String ALIAS_PARENT_TYPE = "订单父类型 1:方案  2:追号";
	public static final String ALIAS_STATUS = "状态 0 待审核 1 已审核 2 未通过审核";
	public static final String ALIAS_CREATE_TIME = "创建时间";
	public static final String ALIAS_UPDATE_TIME = "更新时间";
	public static final String ALIAS_ORDER_ID = "订单ID";
	public static final String ALIAS_USERID = "用户ID";
	public static final String ALIAS_TYPE = "订单类型 1：风险订单 2：废单";
	public static final String ALIAS_SALE_TIME = "销售时间";
	public static final String ALIAS_CHANNEL_ID = "渠道ID";
	
	//date formats
	
	//columns START
	private Long lotteryid;
	private Long issueCode;
	private String webIssueCode;
	private String orderCode;
	/**
	 * 订单总奖金
	 */
	private Long countWin;
	/**
	 * 订单中投比 
	 */
	private Long winsRatio;
	/**
	 * 单注最大奖金
	 */
	private Long maxslipWins;
	/**
	 * 单注最大中投比
	 */
	private Long slipWinsratio;
	/**
	 * 订单父类型 1:方案  2:追号
	 */
	private Long parentType;
	private Long status;
	private Date createTime;
	private Date updateTime;
	private Long orderId;
	private Long userid;
	private Long type;
	private Date saleTime;
	private Long channelId;
	private Long totamount;
	
	//columns END

	public GameWarnOrder(){
	}

	public GameWarnOrder(
		Long id
	){
		this.id = id;
	}

	public void setLotteryid(Long value) {
		this.lotteryid = value;
	}
	
	public Long getLotteryid() {
		return this.lotteryid;
	}
	public void setIssueCode(Long value) {
		this.issueCode = value;
	}
	
	public Long getIssueCode() {
		return this.issueCode;
	}
	public void setWebIssueCode(String value) {
		this.webIssueCode = value;
	}
	
	public String getWebIssueCode() {
		return this.webIssueCode;
	}
	public void setOrderCode(String value) {
		this.orderCode = value;
	}
	
	public String getOrderCode() {
		return this.orderCode;
	}
	/**
	 * 设定订单总奖金
	 */
	public void setCountWin(Long value) {
		this.countWin = value;
	}
	/**
	 * 取得订单总奖金
	 */
	public Long getCountWin() {
		return this.countWin;
	}
	/**
	 * 设定订单中投比 
	 */
	public void setWinsRatio(Long value) {
		this.winsRatio = value;
	}
	/**
	 * 取得订单中投比 
	 */
	public Long getWinsRatio() {
		return this.winsRatio;
	}
	/**
	 * 设定单注最大奖金
	 */
	public void setMaxslipWins(Long value) {
		this.maxslipWins = value;
	}
	/**
	 * 取得单注最大奖金
	 */
	public Long getMaxslipWins() {
		return this.maxslipWins;
	}
	/**
	 * 设定单注最大中投比
	 */
	public void setSlipWinsratio(Long value) {
		this.slipWinsratio = value;
	}
	/**
	 * 取得单注最大中投比
	 */
	public Long getSlipWinsratio() {
		return this.slipWinsratio;
	}
	public void setParentType(Long value) {
		this.parentType = value;
	}
	
	public Long getParentType() {
		return this.parentType;
	}
	public void setStatus(Long value) {
		this.status = value;
	}
	
	public Long getStatus() {
		return this.status;
	}
	public void setCreateTime(Date value) {
		this.createTime = value;
	}
	
	public Date getCreateTime() {
		return this.createTime;
	}
	public void setUpdateTime(Date value) {
		this.updateTime = value;
	}
	
	public Date getUpdateTime() {
		return this.updateTime;
	}
	public void setOrderId(Long value) {
		this.orderId = value;
	}
	
	public Long getOrderId() {
		return this.orderId;
	}
	public void setUserid(Long value) {
		this.userid = value;
	}
	
	public Long getUserid() {
		return this.userid;
	}
	public void setType(Long value) {
		this.type = value;
	}
	
	public Long getType() {
		return this.type;
	}
	public void setSaleTime(Date value) {
		this.saleTime = value;
	}
	
	public Date getSaleTime() {
		return this.saleTime;
	}
	public void setChannelId(Long value) {
		this.channelId = value;
	}
	
	public Long getChannelId() {
		return this.channelId;
	}
    @Override
	public String toString() {
		return new ToStringBuilder(this)
		.append("Id",getId())		
		.append("Lotteryid",getLotteryid())		
		.append("IssueCode",getIssueCode())		
		.append("WebIssueCode",getWebIssueCode())		
		.append("OrderCode",getOrderCode())		
		.append("CountWin",getCountWin())		
		.append("WinsRatio",getWinsRatio())		
		.append("MaxslipWins",getMaxslipWins())		
		.append("SlipWinsratio",getSlipWinsratio())		
		.append("ParentType",getParentType())		
		.append("Status",getStatus())		
		.append("CreateTime",getCreateTime())		
		.append("UpdateTime",getUpdateTime())		
		.append("OrderId",getOrderId())		
		.append("Userid",getUserid())		
		.append("Type",getType())		
		.append("SaleTime",getSaleTime())		
		.append("ChannelId",getChannelId())		
			.toString();
	}
    @Override
	public int hashCode() {
		return new HashCodeBuilder()
		.append(getId())
		.append(getLotteryid())
		.append(getIssueCode())
		.append(getWebIssueCode())
		.append(getOrderCode())
		.append(getCountWin())
		.append(getWinsRatio())
		.append(getMaxslipWins())
		.append(getSlipWinsratio())
		.append(getParentType())
		.append(getStatus())
		.append(getCreateTime())
		.append(getUpdateTime())
		.append(getOrderId())
		.append(getUserid())
		.append(getType())
		.append(getSaleTime())
		.append(getChannelId())
			.toHashCode();
	}
    @Override
	public boolean equals(Object obj) {
		if(obj instanceof GameWarnOrder == false) return false;
		if(this == obj) return true;
		GameWarnOrder other = (GameWarnOrder)obj;
		return new EqualsBuilder()
		.append(getId(),other.getId())

		.append(getLotteryid(),other.getLotteryid())

		.append(getIssueCode(),other.getIssueCode())

		.append(getWebIssueCode(),other.getWebIssueCode())

		.append(getOrderCode(),other.getOrderCode())

		.append(getCountWin(),other.getCountWin())

		.append(getWinsRatio(),other.getWinsRatio())

		.append(getMaxslipWins(),other.getMaxslipWins())

		.append(getSlipWinsratio(),other.getSlipWinsratio())

		.append(getParentType(),other.getParentType())

		.append(getStatus(),other.getStatus())

		.append(getCreateTime(),other.getCreateTime())

		.append(getUpdateTime(),other.getUpdateTime())

		.append(getOrderId(),other.getOrderId())

		.append(getUserid(),other.getUserid())

		.append(getType(),other.getType())

		.append(getSaleTime(),other.getSaleTime())

		.append(getChannelId(),other.getChannelId())

			.isEquals();
	}

	public Long getTotamount() {
		return totamount;
	}

	public void setTotamount(Long totamount) {
		this.totamount = totamount;
	}

}

