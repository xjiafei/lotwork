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

public class GameLottoryAward extends BaseEntity {

	private static final long serialVersionUID = 9139637907003308610L;
	//alias
	public static final String TABLE_ALIAS = "奖金组明细";
	public static final String ALIAS_LOTTORYID = "彩种";
	public static final String ALIAS_GAME_GROUP_CODE = "玩法群";
	public static final String ALIAS_GAME_SET_CODE = "玩法组";
	public static final String ALIAS_BET_METHOD_CODE = "投注方式";
	public static final String ALIAS_ACTUAL_BONUS = "实际奖金";
	public static final String ALIAS_THEORY_BONUS = "理论奖金";
	public static final String ALIAS_AWARD_GROUP_ID = "奖金组ID";
	public static final String ALIAS_CREATE_TIME = "创建时间";
	public static final String ALIAS_UPDATE_TIME = "更新时间";

	//date formats

	//columns START
	private Long lottoryid;
	private Long gameGroupCode;
	private Long gameSetCode;
	private Long betMethodCode;
	private String actualBonus;
	private String theoryBonus;
	private Long awardGroupId;
	private Object createTime;
	private Object updateTime;

	//columns END

	public GameLottoryAward() {
	}

	public GameLottoryAward(Long id) {
		this.id = id;
	}

	public void setLottoryid(Long value) {
		this.lottoryid = value;
	}

	public Long getLottoryid() {
		return this.lottoryid;
	}

	public void setGameGroupCode(Long value) {
		this.gameGroupCode = value;
	}

	public Long getGameGroupCode() {
		return this.gameGroupCode;
	}

	public void setGameSetCode(Long value) {
		this.gameSetCode = value;
	}

	public Long getGameSetCode() {
		return this.gameSetCode;
	}

	public void setBetMethodCode(Long value) {
		this.betMethodCode = value;
	}

	public Long getBetMethodCode() {
		return this.betMethodCode;
	}

	public void setActualBonus(String value) {
		this.actualBonus = value;
	}

	public String getActualBonus() {
		return this.actualBonus;
	}

	public void setTheoryBonus(String value) {
		this.theoryBonus = value;
	}

	public String getTheoryBonus() {
		return this.theoryBonus;
	}

	public void setAwardGroupId(Long value) {
		this.awardGroupId = value;
	}

	public Long getAwardGroupId() {
		return this.awardGroupId;
	}

	public void setCreateTime(Object value) {
		this.createTime = value;
	}

	public Object getCreateTime() {
		return this.createTime;
	}

	public void setUpdateTime(Object value) {
		this.updateTime = value;
	}

	public Object getUpdateTime() {
		return this.updateTime;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this).append("Id", getId()).append("Lottoryid", getLottoryid())
				.append("GameGroupCode", getGameGroupCode()).append("GameSetCode", getGameSetCode())
				.append("BetMethodCode", getBetMethodCode()).append("ActualBonus", getActualBonus())
				.append("TheoryBonus", getTheoryBonus()).append("AwardGroupId", getAwardGroupId())
				.append("CreateTime", getCreateTime()).append("UpdateTime", getUpdateTime()).toString();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(getId()).append(getLottoryid()).append(getGameGroupCode())
				.append(getGameSetCode()).append(getBetMethodCode()).append(getActualBonus()).append(getTheoryBonus())
				.append(getAwardGroupId()).append(getCreateTime()).append(getUpdateTime()).toHashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof GameLottoryAward == false) {
			return false;
		}
		if (this == obj) {
			return true;
		}
		GameLottoryAward other = (GameLottoryAward) obj;
		return new EqualsBuilder().append(getId(), other.getId())

		.append(getLottoryid(), other.getLottoryid())

		.append(getGameGroupCode(), other.getGameGroupCode())

		.append(getGameSetCode(), other.getGameSetCode())

		.append(getBetMethodCode(), other.getBetMethodCode())

		.append(getActualBonus(), other.getActualBonus())

		.append(getTheoryBonus(), other.getTheoryBonus())

		.append(getAwardGroupId(), other.getAwardGroupId())

		.append(getCreateTime(), other.getCreateTime())

		.append(getUpdateTime(), other.getUpdateTime())

		.isEquals();
	}
}
