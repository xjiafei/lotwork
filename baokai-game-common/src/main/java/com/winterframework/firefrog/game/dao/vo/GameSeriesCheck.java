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

public class GameSeriesCheck extends BaseEntity {

	//alias
	public static final String TABLE_ALIAS = "彩票彩系表";
	public static final String ALIAS_LOTTERY_TYPE_CODE = "彩票类型";
	public static final String ALIAS_LOTTERY_TYPE_NAME = "彩票类型名称";
	public static final String ALIAS_LOTTERY_SERIES_CODE = "彩系编码";
	public static final String ALIAS_LOTTERY_SERIES_NAME = "彩系名称";
	public static final String ALIAS_LOTTERYID = "彩种";
	public static final String ALIAS_LOTTERY_NAME = "彩种名称";
	public static final String ALIAS_STATUS = "销售状态 2:待审核 3:待发布";
	public static final String ALIAS_CREATE_TIME = "创建时间";
	public static final String ALIAS_UPDATE_TIME = "更新时间";
	public static final String ALIAS_MINI_LOTTERY_PROFIT = "公司最小流水值";
	public static final String ALIAS_LOTTERY_HELP_DES = "开奖周期描述";
	public static final String ALIAS_CHECK_STATUS = "审核状态 3:待审核 4:待发布";
	
	//date formats

	//columns START
	private Integer lotteryTypeCode;
	private String lotteryTypeName;
	private Integer lotterySeriesCode;
	private String lotterySeriesName;
	private Long lotteryid;
	private String lotteryName;
	private Integer status;
	private Date createTime;
	private Date updateTime;
	private Float miniLotteryProfit;
	private String lotteryHelpDes;
	private Integer checkStatus;
    private Integer checkType;
    private Date takeOffTime;
	//columns END

	public GameSeriesCheck() {
	}

	public GameSeriesCheck(Long id) {
		this.id = id;
	}

	public void setLotteryTypeCode(Integer value) {
		this.lotteryTypeCode = value;
	}

	public Integer getLotteryTypeCode() {
		return this.lotteryTypeCode;
	}

	public void setLotteryTypeName(String value) {
		this.lotteryTypeName = value;
	}

	public String getLotteryTypeName() {
		return this.lotteryTypeName;
	}

	public void setLotterySeriesCode(Integer value) {
		this.lotterySeriesCode = value;
	}

	public Integer getLotterySeriesCode() {
		return this.lotterySeriesCode;
	}

	public void setLotterySeriesName(String value) {
		this.lotterySeriesName = value;
	}

	public String getLotterySeriesName() {
		return this.lotterySeriesName;
	}

	public void setLotteryid(Long value) {
		this.lotteryid = value;
	}

	public Long getLotteryid() {
		return this.lotteryid;
	}

	public void setLotteryName(String value) {
		this.lotteryName = value;
	}

	public String getLotteryName() {
		return this.lotteryName;
	}

	public void setStatus(Integer value) {
		this.status = value;
	}

	public Integer getStatus() {
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

	public void setMiniLotteryProfit(Float value) {
		this.miniLotteryProfit = value;
	}

	public Float getMiniLotteryProfit() {
		return this.miniLotteryProfit;
	}

	public String getLotteryHelpDes() {
		return lotteryHelpDes;
	}

	public void setLotteryHelpDes(String lotteryHelpDes) {
		this.lotteryHelpDes = lotteryHelpDes;
	}

	public Integer getCheckStatus() {
		return checkStatus;
	}

	public void setCheckStatus(Integer checkStatus) {
		this.checkStatus = checkStatus;
	}

    public Integer getCheckType() {
        return checkType;
    }

    public void setCheckType(Integer checkType) {
        this.checkType = checkType;
    }

    public Date getTakeOffTime() {
		return takeOffTime;
	}

	public void setTakeOffTime(Date takeOffTime) {
		this.takeOffTime = takeOffTime;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this).append("Id", getId()).append("LotteryTypeCode", getLotteryTypeCode())
				.append("LotteryTypeName", getLotteryTypeName()).append("LotterySeriesCode", getLotterySeriesCode())
				.append("LotterySeriesName", getLotterySeriesName()).append("Lotteryid", getLotteryid())
				.append("LotteryName", getLotteryName()).append("Status", getStatus())
				.append("CreateTime", getCreateTime()).append("UpdateTime", getUpdateTime())
				.append("MiniLotteryProfit", getMiniLotteryProfit()).append("LotteryHelpDes", getLotteryHelpDes()).toString();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(getId()).append(getLotteryTypeCode()).append(getLotteryTypeName())
				.append(getLotterySeriesCode()).append(getLotterySeriesName()).append(getLotteryid())
				.append(getLotteryName()).append(getStatus()).append(getCreateTime()).append(getUpdateTime())
				.append(getMiniLotteryProfit()).append(getLotteryHelpDes()).toHashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof GameSeriesCheck == false) {
			return false;
		}
		if (this == obj) {
			return true;
		}
		GameSeriesCheck other = (GameSeriesCheck) obj;
		return new EqualsBuilder().append(getId(), other.getId())

		.append(getLotteryTypeCode(), other.getLotteryTypeCode())

		.append(getLotteryTypeName(), other.getLotteryTypeName())

		.append(getLotterySeriesCode(), other.getLotterySeriesCode())

		.append(getLotterySeriesName(), other.getLotterySeriesName())

		.append(getLotteryid(), other.getLotteryid())

		.append(getLotteryName(), other.getLotteryName())

		.append(getStatus(), other.getStatus())

		.append(getCreateTime(), other.getCreateTime())

		.append(getUpdateTime(), other.getUpdateTime())

		.append(getMiniLotteryProfit(), other.getMiniLotteryProfit())
		
		.append(getLotteryHelpDes(), other.getLotteryHelpDes())

		.isEquals();
	}
}
