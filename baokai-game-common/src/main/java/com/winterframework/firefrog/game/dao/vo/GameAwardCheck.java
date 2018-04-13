package com.winterframework.firefrog.game.dao.vo;

import java.util.Date;

import com.winterframework.orm.dal.ibatis3.BaseEntity;

/**
 * @author cms group
 * @version 1.0
 * @since 1.0
 */

public class GameAwardCheck extends BaseEntity {

	private static final long serialVersionUID = -36158746255752820L;
	//alias
	public static final String TABLE_ALIAS = "奖金组奖金审核表";
	public static final String ALIAS_LOTTERYID = "彩种";
	public static final String ALIAS_GAME_GROUP_CODE = "玩法群";
	public static final String ALIAS_GAME_SET_CODE = "玩法组";
	public static final String ALIAS_BET_METHOD_CODE = "投注方式";
	public static final String ALIAS_ACTUAL_BONUS = "实际奖金";
	public static final String ALIAS_THEORY_BONUS = "理论奖金";
	public static final String ALIAS_AWARD_GROUP_ID = "奖金组ID";
	public static final String ALIAS_CREATE_TIME = "创建时间";
	public static final String ALIAS_UPDATE_TIME = "更新时间";
	public static final String ALIAS_CHECK_STATUS = "审核状态 1 待审核 2 待发布";

	//date formats

	//columns START
	private Long lotteryid;
	private String betTypeCode;
	private Long actualBonus;
	private Long awardGroupId;
	private Date createTime;
	private Date updateTime;
	private Integer status;

	//columns END

	public GameAwardCheck() {
	}

	public GameAwardCheck(Long id) {
		this.id = id;
	}

	public void setLotteryid(Long value) {
		this.lotteryid = value;
	}

	public Long getLotteryid() {
		return this.lotteryid;
	}

	public void setActualBonus(Long value) {
		this.actualBonus = value;
	}

	public Long getActualBonus() {
		return this.actualBonus;
	}

	public void setAwardGroupId(Long value) {
		this.awardGroupId = value;
	}

	public Long getAwardGroupId() {
		return this.awardGroupId;
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

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getBetTypeCode() {
		return betTypeCode;
	}

	public void setBetTypeCode(String betTypeCode) {
		this.betTypeCode = betTypeCode;
	}

	public Integer getGameGroupCode() {
		return Integer.valueOf(this.betTypeCode.split("_")[0]);
	}

	public Integer getGameSetCode() {
		return Integer.valueOf(this.betTypeCode.split("_")[1]);
	}

	public Integer getBetMethodCode() {
		return Integer.valueOf(this.betTypeCode.split("_")[2]);
	}

	public Integer getMethodType() {
		if (this.betTypeCode.split("_").length >= 4) {
			return Integer.valueOf(this.betTypeCode.split("_")[3]);
		} else {
			return null;
		}

	}
}
