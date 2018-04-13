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

public class GameDrawLevel extends BaseEntity {

	private static final long serialVersionUID = -6595034929783617913L;
	//alias
	public static final String TABLE_ALIAS = "奖期奖等表";
	public static final String ALIAS_LOTTORYID = "彩种";
	public static final String ALIAS_ISSUE_CODE = "奖期";
	public static final String ALIAS_WEB_ISSUE_CODE = "显示奖期";
	public static final String ALIAS_LEVEL_ONE = "一等奖";
	public static final String ALIAS_ISSUE_BONUS = "奖金";
	public static final String ALIAS_ISSUE_SHAPE = "奖号形态";
	public static final String ALIAS_CREATE_TIME = "创建时间";
	public static final String ALIAS_UPDATE_TIME = "更新时间";

	//date formats

	//columns START
	private Long lottoryid;
	private Long issueCode;
	private String webIssueCode;
	private String levelOne;
	private Long issueBonus;
	private String issueShape;
	private Object createTime;
	private Object updateTime;

	//columns END

	public GameDrawLevel() {
	}

	public GameDrawLevel(Long id) {
		this.id = id;
	}

	public void setLottoryid(Long value) {
		this.lottoryid = value;
	}

	public Long getLottoryid() {
		return this.lottoryid;
	}

	public void setIssueCode(Long value) {
		this.issueCode = value;
	}

	public Long getIssueCode() {
		return this.issueCode;
	}

	public void setWebIssueCode(String value) {
		this.webIssueCode = value;
	}

	public String getWebIssueCode() {
		return this.webIssueCode;
	}

	public void setLevelOne(String value) {
		this.levelOne = value;
	}

	public String getLevelOne() {
		return this.levelOne;
	}

	public void setIssueBonus(Long value) {
		this.issueBonus = value;
	}

	public Long getIssueBonus() {
		return this.issueBonus;
	}

	public void setIssueShape(String value) {
		this.issueShape = value;
	}

	public String getIssueShape() {
		return this.issueShape;
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
				.append("IssueCode", getIssueCode()).append("WebIssueCode", getWebIssueCode())
				.append("LevelOne", getLevelOne()).append("IssueBonus", getIssueBonus())
				.append("IssueShape", getIssueShape()).append("CreateTime", getCreateTime())
				.append("UpdateTime", getUpdateTime()).toString();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(getId()).append(getLottoryid()).append(getIssueCode())
				.append(getWebIssueCode()).append(getLevelOne()).append(getIssueBonus()).append(getIssueShape())
				.append(getCreateTime()).append(getUpdateTime()).toHashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof GameDrawLevel == false) {
			return false;
		}
		if (this == obj) {
			return true;
		}
		GameDrawLevel other = (GameDrawLevel) obj;
		return new EqualsBuilder().append(getId(), other.getId())

		.append(getLottoryid(), other.getLottoryid())

		.append(getIssueCode(), other.getIssueCode())

		.append(getWebIssueCode(), other.getWebIssueCode())

		.append(getLevelOne(), other.getLevelOne())

		.append(getIssueBonus(), other.getIssueBonus())

		.append(getIssueShape(), other.getIssueShape())

		.append(getCreateTime(), other.getCreateTime())

		.append(getUpdateTime(), other.getUpdateTime())

		.isEquals();
	}
}
