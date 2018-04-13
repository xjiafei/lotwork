package com.winterframework.firefrog.game.dao.vo;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.*;

import com.winterframework.orm.dal.ibatis3.*;

/**
 * @author cms group
 * @version 1.0
 * @since 1.0
 */

public class GameMultipleCheck extends BaseEntity {

	private static final long serialVersionUID = -3927845177363706969L;
	//alias
	public static final String TABLE_ALIAS = "倍数限制审核表";
	public static final String ALIAS_LOTTERYID = "彩种";
	public static final String ALIAS_GAME_GROUP_CODE = "玩法群编码";
	public static final String ALIAS_GAME_SET_CODE = "玩法组编码";
	public static final String ALIAS_BET_METHOD_CODE = "投注方式编码";
	public static final String ALIAS_MAX_MULTIPLE = "最大倍数限制";
	public static final String ALIAS_CREATE_TIME = "创建时间";
	public static final String ALIAS_STATUS = "状态 1 待审核 2 待发布";

	//date formats

	//columns START
	private Long lotteryid;
	private Integer gameGroupCode;
	private Integer gameSetCode;
	private Integer betMethodCode;
	private Integer maxMultiple;
	private Date createTime;
	private Integer status;
	private String betTypeCode;
	private String specialMultiple;

	//columns END

	public GameMultipleCheck() {
	}

	public GameMultipleCheck(Long id) {
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
//		return this.gameGroupCode;
		String[] temp = betTypeCode.split("_");
		return Integer.parseInt(temp[0]);
	}

	public void setGameSetCode(Integer value) {
		this.gameSetCode = value;
	}

	public Integer getGameSetCode() {
		String[] temp = betTypeCode.split("_");
		return Integer.parseInt(temp[1]);
	}

	public void setBetMethodCode(Integer value) {
		this.betMethodCode = value;
	}

	public Integer getBetMethodCode() {
//		return this.betMethodCode;
		String[] temp = betTypeCode.split("_");
		return Integer.parseInt(temp[2]);
	}

	public void setMaxMultiple(Integer value) {
		this.maxMultiple = value;
	}

	public Integer getMaxMultiple() {
		return this.maxMultiple;
	}

	public void setCreateTime(Date value) {
		this.createTime = value;
	}

	public Date getCreateTime() {
		return this.createTime;
	}

	public void setStatus(Integer value) {
		this.status = value;
	}

	public Integer getStatus() {
		return this.status;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this).append("Id", getId()).append("Lotteryid", getLotteryid())
				.append("GameGroupCode", getGameGroupCode()).append("GameSetCode", getGameSetCode())
				.append("BetMethodCode", getBetMethodCode()).append("MaxMultiple", getMaxMultiple())
				.append("CreateTime", getCreateTime()).append("Status", getStatus()).toString();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(getId()).append(getLotteryid()).append(getGameGroupCode())
				.append(getGameSetCode()).append(getBetMethodCode()).append(getMaxMultiple()).append(getCreateTime())
				.append(getStatus()).toHashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof GameMultipleCheck == false)
			return false;
		if (this == obj)
			return true;
		GameMultipleCheck other = (GameMultipleCheck) obj;
		return new EqualsBuilder().append(getId(), other.getId())

		.append(getLotteryid(), other.getLotteryid())

		.append(getGameGroupCode(), other.getGameGroupCode())

		.append(getGameSetCode(), other.getGameSetCode())

		.append(getBetMethodCode(), other.getBetMethodCode())

		.append(getMaxMultiple(), other.getMaxMultiple())

		.append(getCreateTime(), other.getCreateTime())

		.append(getStatus(), other.getStatus())

		.isEquals();
	}

	public String getBetTypeCode() {
		return betTypeCode;
	}

	public void setBetTypeCode(String betTypeCode) {
		this.betTypeCode = betTypeCode;
	}

	public String getSpecialMultiple() {
		return specialMultiple;
	}

	public void setSpecialMultiple(String specialMultiple) {
		this.specialMultiple = specialMultiple;
	}
	
}
