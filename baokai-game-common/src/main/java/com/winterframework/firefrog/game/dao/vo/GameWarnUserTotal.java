 package com.winterframework.firefrog.game.dao.vo;

import java.util.Date;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import com.winterframework.orm.dal.ibatis3.BaseEntity;

 
/**
 * 警告用户汇总表
 * @ClassName
 * @Description
 * @author ibm
 * 2014年10月28日
 */
public class GameWarnUserTotal extends BaseEntity { 
	private static final long serialVersionUID = -7866202138341860370L;
	//alias  
	public static final String TABLE_ALIAS = "警告用户汇总表";
	public static final String ALIAS_LOTTERYID = "彩种"; 
	public static final String ALIAS_USERID = "用户ID";  
	public static final String ALIAS_CONTINUE_WINS_ISSUE = "连续中奖期数";
	public static final String ALIAS_CONTINUE_WINS_TIMES = "连续中奖次数"; 
	public static final String ALIAS_CREATE_TIME = "创建时间";
	public static final String ALIAS_UPDATE_TIME = "更新时间";
	//date formats 
	
	//columns START
	private Long lotteryId; 
	private Long userId; 
	private Long continueWinsIssue = 0L;
	private Long continueWinsTimes = 0L; 
	private Date createTime;
	private Date updateTime;
	//columns END

	public GameWarnUserTotal(){
	}
	public Long getLotteryId() {
		return lotteryId;
	} 
	public void setLotteryId(Long lotteryId) {
		this.lotteryId = lotteryId;
	}


	public Long getUserId() {
		return userId;
	}


	public void setUserId(Long userId) {
		this.userId = userId;
	}


	public Long getContinueWinsIssue() {
		return continueWinsIssue;
	}

	public void setContinueWinsIssue(Long continueWinsIssue) {
		this.continueWinsIssue = continueWinsIssue;
	}

	public Long getContinueWinsTimes() {
		return continueWinsTimes;
	}

	public void setContinueWinsTimes(Long continueWinsTimes) {
		this.continueWinsTimes = continueWinsTimes;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	public Date getUpdateTime() {
		return updateTime;
	}


	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}


	@Override
	public String toString() {
		return new ToStringBuilder(this)
		.append("Id",getId())		
		.append("LotteryId",getLotteryId())		 
		.append("UserId",getUserId())		 
		.append("ContinueWinsIssue",getContinueWinsIssue())		
		.append("ContinuousWinsTimes",getContinueWinsTimes())	 
		.toString();
	}
    @Override
	public int hashCode() {
		return new HashCodeBuilder()
		.append(getId())
		.append(getLotteryId()) 
		.append(getUserId()) 
		.append(getContinueWinsIssue())
		.append(getContinueWinsTimes()) 
		.toHashCode();
	}
    @Override
	public boolean equals(Object obj) {
		if(obj instanceof GameWarnUserTotal == false) return false;
		if(this == obj) return true;
		GameWarnUserTotal other = (GameWarnUserTotal)obj;
		return new EqualsBuilder()
		.append(getId(),other.getId()) 
		.append(getLotteryId(),other.getLotteryId()) 
		.append(getUserId(),other.getUserId()) 
		.append(getContinueWinsIssue(),other.getContinueWinsIssue())
		.append(getContinueWinsTimes(),other.getContinueWinsTimes()) 
		.isEquals();
	} 
}

