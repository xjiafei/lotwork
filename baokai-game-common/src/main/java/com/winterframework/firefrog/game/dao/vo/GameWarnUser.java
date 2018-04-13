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


public class GameWarnUser extends BaseEntity {
	
	//alias
	public static final String TABLE_ALIAS = "用户警告表";
	public static final String ALIAS_LOTTERYID = "彩种";
	public static final String ALIAS_ISSUE_CODE = "奖期";
	public static final String ALIAS_USERID = "用户ID";
	public static final String ALIAS_USER_ACCOUNT = "用户名称";
	public static final String ALIAS_TOTAL_WINS = "总奖金";
	public static final String ALIAS_WINS_RATIO = "中投比";
	public static final String ALIAS_CONTINUOUS_WINS_ISSUE = "连续中奖期数";
	public static final String ALIAS_CONTINUOUS_WINS_TIMES = "连续中奖次数";
	public static final String ALIAS_MAXSLIP_WINS = "单注最大奖金";
	public static final String ALIAS_BET_TOTAMOUNT = "投注总金额";
	public static final String ALIAS_TYPE = "是否是风险用户， 0 不是 ， 1是";
	//date formats
	
	//columns START
	private Long lotteryid;
	private Long issueCode;
	private Long userid;
	private String userAccount;
	private Long totalWins = 0L;
	private Long winsRatio = 0L;
	private Long continuousWinsIssue = 0L;
	private Long continuousWinsTimes = 0L;
	private Long maxslipWins = 0L;
	private Long betTotamount = 0L;
	private Long type = 0L;
	//columns END

	public GameWarnUser(){
	}

	public GameWarnUser(
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
	public void setUserid(Long value) {
		this.userid = value;
	}
	
	public Long getUserid() {
		return this.userid;
	}
	public void setUserAccount(String value) {
		this.userAccount = value;
	}
	
	public String getUserAccount() {
		return this.userAccount;
	}
	public void setTotalWins(Long value) {
		this.totalWins = value;
	}
	
	public Long getTotalWins() {
		return this.totalWins;
	}
	public void setWinsRatio(Long value) {
		this.winsRatio = value;
	}
	
	public Long getWinsRatio() {
		return this.winsRatio;
	}
	public void setContinuousWinsIssue(Long value) {
		this.continuousWinsIssue = value;
	}
	
	public Long getContinuousWinsIssue() {
		return this.continuousWinsIssue;
	}
	public void setContinuousWinsTimes(Long value) {
		this.continuousWinsTimes = value;
	}
	
	public Long getContinuousWinsTimes() {
		return this.continuousWinsTimes;
	}
	public void setMaxslipWins(Long value) {
		this.maxslipWins = value;
	}
	
	public Long getMaxslipWins() {
		return this.maxslipWins;
	}
    @Override
	public String toString() {
		return new ToStringBuilder(this)
		.append("Id",getId())		
		.append("Lotteryid",getLotteryid())		
		.append("IssueCode",getIssueCode())		
		.append("Userid",getUserid())		
		.append("UserAccount",getUserAccount())		
		.append("TotalWins",getTotalWins())		
		.append("WinsRatio",getWinsRatio())		
		.append("ContinuousWinsIssue",getContinuousWinsIssue())		
		.append("ContinuousWinsTimes",getContinuousWinsTimes())		
		.append("MaxslipWins",getMaxslipWins())		
			.toString();
	}
    @Override
	public int hashCode() {
		return new HashCodeBuilder()
		.append(getId())
		.append(getLotteryid())
		.append(getIssueCode())
		.append(getUserid())
		.append(getUserAccount())
		.append(getTotalWins())
		.append(getWinsRatio())
		.append(getContinuousWinsIssue())
		.append(getContinuousWinsTimes())
		.append(getMaxslipWins())
			.toHashCode();
	}
    @Override
	public boolean equals(Object obj) {
		if(obj instanceof GameWarnUser == false) return false;
		if(this == obj) return true;
		GameWarnUser other = (GameWarnUser)obj;
		return new EqualsBuilder()
		.append(getId(),other.getId())

		.append(getLotteryid(),other.getLotteryid())

		.append(getIssueCode(),other.getIssueCode())

		.append(getUserid(),other.getUserid())

		.append(getUserAccount(),other.getUserAccount())

		.append(getTotalWins(),other.getTotalWins())

		.append(getWinsRatio(),other.getWinsRatio())

		.append(getContinuousWinsIssue(),other.getContinuousWinsIssue())

		.append(getContinuousWinsTimes(),other.getContinuousWinsTimes())

		.append(getMaxslipWins(),other.getMaxslipWins())

			.isEquals();
	}

	public Long getBetTotamount() {
		return betTotamount;
	}

	public void setBetTotamount(Long betTotamount) {
		this.betTotamount = betTotamount;
	}

	public Long getType() {
		return type;
	}

	public void setType(Long type) {
		this.type = type;
	}
}

