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


public class GameBettypeStatusCheck extends BaseEntity {
	
	//alias
	public static final String TABLE_ALIAS = "投注方式状态审核表";
	public static final String ALIAS_LOTTERYID = "彩种";
	public static final String ALIAS_GAME_GROUP_CODE = "玩法群编码";
	public static final String ALIAS_GAME_SET_CODE = "玩法组编码";
	public static final String ALIAS_BET_METHOD_CODE = "投注方式编码";
	public static final String ALIAS_STATUS = "销售状态 0:停售 1:可销售";
	public static final String ALIAS_CREATE_TIME = "创建时间";
	public static final String ALIAS_UPDATE_TIME = "更新时间";
	public static final String ALIAS_CHECK_STATUS = "审核状态 1 待审核 2 待发布";
	public static final String ALIAS_THEORY_BONUS = "理论奖金";
	
	//date formats
	
	//columns START
	private Long lotteryid;
	private Integer gameGroupCode;
	private Integer gameSetCode;
	private Integer betMethodCode;
	private Integer status;
	private Date createTime;
	private Date updateTime;
	private Integer checkStatus;
	private Long theoryBonus;
	//columns END

	public GameBettypeStatusCheck(){
	}

	public GameBettypeStatusCheck(
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
	public void setGameGroupCode(Integer value) {
		this.gameGroupCode = value;
	}
	
	public Integer getGameGroupCode() {
		return this.gameGroupCode;
	}
	public void setGameSetCode(Integer value) {
		this.gameSetCode = value;
	}
	
	public Integer getGameSetCode() {
		return this.gameSetCode;
	}
	public void setBetMethodCode(Integer value) {
		this.betMethodCode = value;
	}
	
	public Integer getBetMethodCode() {
		return this.betMethodCode;
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
	public void setCheckStatus(Integer value) {
		this.checkStatus = value;
	}
	
	public Integer getCheckStatus() {
		return this.checkStatus;
	}
	public void setTheoryBonus(Long value) {
		this.theoryBonus = value;
	}
	
	public Long getTheoryBonus() {
		return this.theoryBonus;
	}
    @Override
	public String toString() {
		return new ToStringBuilder(this)
		.append("Id",getId())		
		.append("Lotteryid",getLotteryid())		
		.append("GameGroupCode",getGameGroupCode())		
		.append("GameSetCode",getGameSetCode())		
		.append("BetMethodCode",getBetMethodCode())		
		.append("Status",getStatus())		
		.append("CreateTime",getCreateTime())		
		.append("UpdateTime",getUpdateTime())		
		.append("CheckStatus",getCheckStatus())		
		.append("TheoryBonus",getTheoryBonus())		
			.toString();
	}
    @Override
	public int hashCode() {
		return new HashCodeBuilder()
		.append(getId())
		.append(getLotteryid())
		.append(getGameGroupCode())
		.append(getGameSetCode())
		.append(getBetMethodCode())
		.append(getStatus())
		.append(getCreateTime())
		.append(getUpdateTime())
		.append(getCheckStatus())
		.append(getTheoryBonus())
			.toHashCode();
	}
    @Override
	public boolean equals(Object obj) {
		if(obj instanceof GameBettypeStatusCheck == false) return false;
		if(this == obj) return true;
		GameBettypeStatusCheck other = (GameBettypeStatusCheck)obj;
		return new EqualsBuilder()
		.append(getId(),other.getId())

		.append(getLotteryid(),other.getLotteryid())

		.append(getGameGroupCode(),other.getGameGroupCode())

		.append(getGameSetCode(),other.getGameSetCode())

		.append(getBetMethodCode(),other.getBetMethodCode())

		.append(getStatus(),other.getStatus())

		.append(getCreateTime(),other.getCreateTime())

		.append(getUpdateTime(),other.getUpdateTime())

		.append(getCheckStatus(),other.getCheckStatus())

		.append(getTheoryBonus(),other.getTheoryBonus())

			.isEquals();
	}
}

