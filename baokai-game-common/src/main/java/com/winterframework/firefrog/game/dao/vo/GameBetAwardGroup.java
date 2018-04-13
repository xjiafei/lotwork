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


public class GameBetAwardGroup extends BaseEntity {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4249021318510399526L;
	//alias
	public static final String TABLE_ALIAS = "用户投注奖金组";
	public static final String ALIAS_LOTTERY_SERIES_CODE = "彩系编码";
	public static final String ALIAS_AWARD_NAME = "奖金组名称";
	public static final String ALIAS_AWARD_GROUPID = "奖金组";
	public static final String ALIAS_LOTTERYID = "彩种";
	public static final String ALIAS_BET_TYPE = "投注类型";
	//date formats
	
	//columns START
	private Integer lotterySeriesCode;
	private Long awardGroupid;
	private String awardName;
	private Long lotteryid;
	private Integer betType;
	private Integer awardRetStatus;	//奖金返点开关
	private Integer sysAwardGroupId;//獎金組對應
	//columns END

	public GameBetAwardGroup(){
	}

	public GameBetAwardGroup(
		Long id
	){
		this.id = id;
	}

	public Integer getLotterySeriesCode() {
		return lotterySeriesCode;
	}

	public void setLotterySeriesCode(Integer lotterySeriesCode) {
		this.lotterySeriesCode = lotterySeriesCode;
	}

	public Long getAwardGroupid() {
		return awardGroupid;
	}

	public void setAwardGroupid(Long awardGroupid) {
		this.awardGroupid = awardGroupid;
	}

	public String getAwardName() {
		return awardName;
	}

	public void setAwardName(String awardName) {
		this.awardName = awardName;
	}

	public Long getLotteryid() {
		return lotteryid;
	}

	public void setLotteryid(Long lotteryid) {
		this.lotteryid = lotteryid;
	}
	

	public Integer getBetType() {
		return betType;
	}

	public void setBetType(Integer betType) {
		this.betType = betType;
	}
	
	public Integer getAwardRetStatus() {
		return awardRetStatus;
	}

	public void setAwardRetStatus(Integer awardRetStatus) {
		this.awardRetStatus = awardRetStatus;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this)
		.append("Id",getId())		
		.append("LotterySeriesCode",getLotterySeriesCode())		
		.append("AwardName",getAwardName())		
		.append("Lotteryid",getLotteryid())		
		.append("BetType",getBetType())		
			.toString();
	}
    @Override
	public int hashCode() {
		return new HashCodeBuilder()
		.append(getId())
		.append(getLotterySeriesCode())
		.append(getAwardName())
		.append(getLotteryid())
		.append(getBetType())
			.toHashCode();
	}
    @Override
	public boolean equals(Object obj) {
		if(obj instanceof GameBetAwardGroup == false) return false;
		if(this == obj) return true;
		GameBetAwardGroup other = (GameBetAwardGroup)obj;
		return new EqualsBuilder()
		.append(getId(),other.getId())

		.append(getLotterySeriesCode(),other.getLotterySeriesCode())

		.append(getAwardName(),other.getAwardName())

		.append(getLotteryid(),other.getLotteryid())

		.append(getBetType(),other.getBetType())

			.isEquals();
	}

	/**
	 * @return the sysAwardGroupId
	 */
	public Integer getSysAwardGroupId() {
		return sysAwardGroupId;
	}

	/**
	 * @param sysAwardGroupId the sysAwardGroupId to set
	 */
	public void setSysAwardGroupId(Integer sysAwardGroupId) {
		this.sysAwardGroupId = sysAwardGroupId;
	}
}

