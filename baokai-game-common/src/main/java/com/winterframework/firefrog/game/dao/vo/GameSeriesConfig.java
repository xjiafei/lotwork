package com.winterframework.firefrog.game.dao.vo;

import java.util.Date;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import com.winterframework.orm.dal.ibatis3.*;

/**
 * @author cms group
 * @version 1.0
 * @since 1.0
 */


public class GameSeriesConfig extends BaseEntity {
	
	private static final long serialVersionUID = -4272745564238269289L;
	
	//alias
	public static final String TABLE_ALIAS = "彩种配置表";
	public static final String ALIAS_LOTTERYID = "彩种";
	public static final String ALIAS_BACKOUT_START_FEE = "撤单起始金额";
	public static final String ALIAS_BACKOUT_RATIO = "撤单手续费比率";
	public static final String ALIAS_ISSUEWARN_NOT_OPENAWARD = "超过理论开奖时间,官方未开奖";
	public static final String ALIAS_ISSUEWARN_AHEAD_OPENAWARD = "早于理论开奖时间,官方提前开奖";
	public static final String ALIAS_ISSUEWARN_DELAY_OPENAWARD = "超过理论开奖时间,官方延迟开奖";
	public static final String ALIAS_ISSUEWARN_BACKOUT_TIME = "开奖后单笔撤销时间";
	public static final String ALIAS_ISSUEWARN_EXCEPTION_TIME = "开奖后异常处理时间";
	public static final String ALIAS_CREATE_TIME = "创建时间";
	public static final String ALIAS_UPDATE_TIME = "更新时间";
	
	//date formats
	
	//columns START
	private Long lotteryid;
	private Long backoutStartFee;
	private Long backoutRatio;
	private Long issuewarnNotOpenaward;
	private Long issuewarnAheadOpenaward;
	private Long issuewarnDelayOpenaward;
	private Long issuewarnBackoutTime;
	private Long issuewarnExceptionTime;
	private Date createTime;
	private Date updateTime;
	private String email;
	private Long issuewarnUserBackoutTime;
	private Long hasVideo;
	private String videoStruc;
	//columns END
	
	public GameSeriesConfig(){
	}

	public Long getHasVideo() {
		return hasVideo;
	}

	public void setHasVideo(Long hasVideo) {
		this.hasVideo = hasVideo;
	}

	public String getVideoStruc() {
		return videoStruc;
	}

	public void setVideoStruc(String videoStruc) {
		this.videoStruc = videoStruc;
	}

	public GameSeriesConfig(
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
	public void setBackoutStartFee(Long value) {
		this.backoutStartFee = value;
	}
	
	public Long getBackoutStartFee() {
		return this.backoutStartFee;
	}
	public void setBackoutRatio(Long value) {
		this.backoutRatio = value;
	}
	
	public Long getBackoutRatio() {
		return this.backoutRatio;
	}
	public void setIssuewarnNotOpenaward(Long value) {
		this.issuewarnNotOpenaward = value;
	}
	
	public Long getIssuewarnNotOpenaward() {
		return this.issuewarnNotOpenaward;
	}
	public void setIssuewarnAheadOpenaward(Long value) {
		this.issuewarnAheadOpenaward = value;
	}
	
	public Long getIssuewarnAheadOpenaward() {
		return this.issuewarnAheadOpenaward;
	}
	public void setIssuewarnDelayOpenaward(Long value) {
		this.issuewarnDelayOpenaward = value;
	}
	
	public Long getIssuewarnDelayOpenaward() {
		return this.issuewarnDelayOpenaward;
	}
	public void setIssuewarnBackoutTime(Long value) {
		this.issuewarnBackoutTime = value;
	}
	
	public Long getIssuewarnBackoutTime() {
		return this.issuewarnBackoutTime;
	}
	public void setIssuewarnExceptionTime(Long value) {
		this.issuewarnExceptionTime = value;
	}
	
	public Long getIssuewarnExceptionTime() {
		return this.issuewarnExceptionTime;
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
	
    public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	public Long getIssuewarnUserBackoutTime() {
		return issuewarnUserBackoutTime;
	}

	public void setIssuewarnUserBackoutTime(Long issuewarnUserBackoutTime) {
		this.issuewarnUserBackoutTime = issuewarnUserBackoutTime;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this)
		.append("Id",getId())		
		.append("Lotteryid",getLotteryid())		
		.append("BackoutStartFee",getBackoutStartFee())		
		.append("BackoutRatio",getBackoutRatio())		
		.append("IssuewarnNotOpenaward",getIssuewarnNotOpenaward())		
		.append("IssuewarnAheadOpenaward",getIssuewarnAheadOpenaward())		
		.append("IssuewarnDelayOpenaward",getIssuewarnDelayOpenaward())		
		.append("IssuewarnBackoutTime",getIssuewarnBackoutTime())		
		.append("IssuewarnExceptionTime",getIssuewarnExceptionTime())		
		.append("CreateTime",getCreateTime())		
		.append("UpdateTime",getUpdateTime())		
			.toString();
	}
    @Override
	public int hashCode() {
		return new HashCodeBuilder()
		.append(getId())
		.append(getLotteryid())
		.append(getBackoutStartFee())
		.append(getBackoutRatio())
		.append(getIssuewarnNotOpenaward())
		.append(getIssuewarnAheadOpenaward())
		.append(getIssuewarnDelayOpenaward())
		.append(getIssuewarnBackoutTime())
		.append(getIssuewarnExceptionTime())
		.append(getCreateTime())
		.append(getUpdateTime())
			.toHashCode();
	}
    @Override
	public boolean equals(Object obj) {
		if(obj instanceof GameSeriesConfig == false) return false;
		if(this == obj) return true;
		GameSeriesConfig other = (GameSeriesConfig)obj;
		return new EqualsBuilder()
		.append(getId(),other.getId())

		.append(getLotteryid(),other.getLotteryid())

		.append(getBackoutStartFee(),other.getBackoutStartFee())

		.append(getBackoutRatio(),other.getBackoutRatio())

		.append(getIssuewarnNotOpenaward(),other.getIssuewarnNotOpenaward())

		.append(getIssuewarnAheadOpenaward(),other.getIssuewarnAheadOpenaward())

		.append(getIssuewarnDelayOpenaward(),other.getIssuewarnDelayOpenaward())

		.append(getIssuewarnBackoutTime(),other.getIssuewarnBackoutTime())

		.append(getIssuewarnExceptionTime(),other.getIssuewarnExceptionTime())

		.append(getCreateTime(),other.getCreateTime())

		.append(getUpdateTime(),other.getUpdateTime())

			.isEquals();
	}
}

