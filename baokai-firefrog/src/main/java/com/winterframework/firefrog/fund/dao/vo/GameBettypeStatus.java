package com.winterframework.firefrog.fund.dao.vo;

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

public class GameBettypeStatus extends BaseEntity {

	private static final long serialVersionUID = -8332264848215801746L;
	//alias
	public static final String TABLE_ALIAS = "投注方式状态表";
	public static final String ALIAS_LOTTORYID = "彩种";
	public static final String ALIAS_GAME_GROUP_CODE = "玩法群编码";
	public static final String ALIAS_GAME_SET_CODE = "玩法组编码";
	public static final String ALIAS_BET_METHOD_CODE = "投注方式编码";
	public static final String ALIAS_STATUS = "销售状态 0:停售 1:可销售";
	public static final String ALIAS_CREATE_TIME = "创建时间";
	public static final String ALIAS_UPDATE_TIME = "更新时间";

	//date formats

	//columns START
	private Long lotteryid;
	private Integer gameGroupCode;
	private Integer gameSetCode;
	private Integer betMethodCode;
	private Integer status;
	private Date createTime;
	private Date updateTime;
	private Long theoryBonus;
	private String betTypeCode;
	private String groupCodeName;
	private String setCodeName;
	private String methodCodeName;
	//columns END

	public GameBettypeStatus() {
	}

	public GameBettypeStatus(Long id) {
		this.id = id;
	}

	public void setLotteryid(Long value) {
		this.lotteryid = value;
	}

	public Long getLottoryid() {
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

	@Override
	public String toString() {
		return new ToStringBuilder(this).append("Id", getId()).append("Lottoryid", getLottoryid())
				.append("GameGroupCode", getGameGroupCode()).append("GameSetCode", getGameSetCode())
				.append("BetMethodCode", getBetMethodCode()).append("Status", getStatus())
				.append("CreateTime", getCreateTime()).append("UpdateTime", getUpdateTime()).toString();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(getId()).append(getLottoryid()).append(getGameGroupCode())
				.append(getGameSetCode()).append(getBetMethodCode()).append(getStatus()).append(getCreateTime())
				.append(getUpdateTime()).toHashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof GameBettypeStatus == false) {
			return false;
		}
		if (this == obj) {
			return true;
		}
		GameBettypeStatus other = (GameBettypeStatus) obj;
		return new EqualsBuilder().append(getId(), other.getId())

		.append(getLottoryid(), other.getLottoryid())

		.append(getGameGroupCode(), other.getGameGroupCode())

		.append(getGameSetCode(), other.getGameSetCode())

		.append(getBetMethodCode(), other.getBetMethodCode())

		.append(getStatus(), other.getStatus())

		.append(getCreateTime(), other.getCreateTime())

		.append(getUpdateTime(), other.getUpdateTime())

		.isEquals();
	}

	public Long getTheoryBonus() {
		return theoryBonus;
	}

	public void setTheoryBonus(Long theoryBonus) {
		this.theoryBonus = theoryBonus;
	}

	public String getGroupCodeName() {
		return groupCodeName;
	}

	public void setGroupCodeName(String groupCodeName) {
		this.groupCodeName = groupCodeName;
	}

	public String getSetCodeName() {
		return setCodeName;
	}

	public void setSetCodeName(String setCodeName) {
		this.setCodeName = setCodeName;
	}

	public String getMethodCodeName() {
		return methodCodeName;
	}

	public void setMethodCodeName(String methodCodeName) {
		this.methodCodeName = methodCodeName;
	}

	public Long getLotteryid() {
		return lotteryid;
	}

	public String getBetTypeCode() {
		return betTypeCode;
	}

	public void setBetTypeCode(String betTypeCode) {
		this.betTypeCode = betTypeCode;
	}
	
	
}
