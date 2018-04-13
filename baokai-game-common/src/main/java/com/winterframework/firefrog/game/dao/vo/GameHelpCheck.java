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

public class GameHelpCheck extends BaseEntity {

	//alias
	public static final String TABLE_ALIAS = "投注方式帮忙说明审核表";
	public static final String ALIAS_LOTTERYID = "彩种";
	public static final String ALIAS_GAME_GROUP_CODE = "玩法群";
	public static final String ALIAS_GAME_SET_CODE = "玩法组";
	public static final String ALIAS_BET_METHOD_CODE = "投注方式";
	public static final String ALIAS_GAME_PROMPT_DES = "玩法提示描述";
	public static final String ALIAS_GAME_EXAMPLES_DES = "投注示例";
	public static final String ALIAS_CRETEAT_TIME = "创建时间";
	public static final String ALIAS_UPDATE_TIME = "更新时间";
	public static final String ALIAS_CHECK_STATUS = "审核状态 1 待审核 2 待发布";

	//date formats

	//columns START
	private Long lotteryid;
	private Integer gameGroupCode;
	private Integer gameSetCode;
	private Integer betMethodCode;
	private String gamePromptDes;
	private String gameExamplesDes;
	private Date creteatTime;
	private Date updateTime;
	private Integer checkStatus;
	private String betTypeCode;

	//columns END

	public GameHelpCheck() {
	}

	public GameHelpCheck(Long id) {
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
//		return this.gameSetCode;
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

	public void setGamePromptDes(String value) {
		this.gamePromptDes = value;
	}

	public String getGamePromptDes() {
		return this.gamePromptDes;
	}

	public void setGameExamplesDes(String value) {
		this.gameExamplesDes = value;
	}

	public String getGameExamplesDes() {
		return this.gameExamplesDes;
	}

	public void setCreteatTime(Date value) {
		this.creteatTime = value;
	}

	public Date getCreteatTime() {
		return this.creteatTime;
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

	@Override
	public String toString() {
		return new ToStringBuilder(this).append("Id", getId()).append("Lotteryid", getLotteryid())
				.append("GameGroupCode", getGameGroupCode()).append("GameSetCode", getGameSetCode())
				.append("BetMethodCode", getBetMethodCode())
				.append("GamePromptDes", getGamePromptDes()).append("GameExamplesDes", getGameExamplesDes())
				.append("CreteatTime", getCreteatTime()).append("UpdateTime", getUpdateTime())
				.append("CheckStatus", getCheckStatus()).toString();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(getId()).append(getLotteryid()).append(getGameGroupCode())
				.append(getGameSetCode()).append(getBetMethodCode())
				.append(getGamePromptDes()).append(getGameExamplesDes()).append(getCreteatTime())
				.append(getUpdateTime()).append(getCheckStatus()).toHashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof GameHelpCheck == false) {
			return false;
		}
		if (this == obj) {
			return true;
		}
		GameHelpCheck other = (GameHelpCheck) obj;
		return new EqualsBuilder().append(getId(), other.getId())

		.append(getLotteryid(), other.getLotteryid())

		.append(getGameGroupCode(), other.getGameGroupCode())

		.append(getGameSetCode(), other.getGameSetCode())

		.append(getBetMethodCode(), other.getBetMethodCode())

		.append(getGamePromptDes(), other.getGamePromptDes())

		.append(getGameExamplesDes(), other.getGameExamplesDes())

		.append(getCreteatTime(), other.getCreteatTime())

		.append(getUpdateTime(), other.getUpdateTime())

		.append(getCheckStatus(), other.getCheckStatus())

		.isEquals();
	}

	public String getBetTypeCode() {
		return betTypeCode;
	}

	public void setBetTypeCode(String betTypeCode) {
		this.betTypeCode = betTypeCode;
	}
}
