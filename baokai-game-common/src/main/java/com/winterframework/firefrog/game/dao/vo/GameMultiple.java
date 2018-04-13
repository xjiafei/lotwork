package com.winterframework.firefrog.game.dao.vo;

import java.util.Date;

import com.winterframework.orm.dal.ibatis3.BaseEntity;

/**
 * @author cms group
 * @version 1.0
 * @since 1.0
 */

public class GameMultiple extends BaseEntity {

	private static final long serialVersionUID = 8162828327726589639L;
	//alias
	public static final String TABLE_ALIAS = "倍数限制表";
	public static final String ALIAS_LOTTERYID = "彩种";
	public static final String ALIAS_GAME_GROUP_CODE = "玩法群编码";
	public static final String ALIAS_GAME_SET_CODE = "玩法组编码";
	public static final String ALIAS_BET_METHOD_CODE = "投注方式编码";
	public static final String ALIAS_MAX_MUTIPLE = "最大倍数限制";
	public static final String ALIAS_CREATE_TIME = "创建时间";
	public static final String ALIAS_UPDATE_TIME = "更新时间";

	//date formats

	//columns START
	private Long lotteryid;
	private String betTypeCode;
	private Integer maxMultiple;
	private Date createTime;
	private Date updateTime;
	private Integer maxCountIssue;
	private String specialMultiple;

	//columns END

	public GameMultiple() {
	}

	public GameMultiple(Long id) {
		this.id = id;
	}

	public void setLotteryid(Long value) {
		this.lotteryid = value;
	}

	public Long getLotteryid() {
		return this.lotteryid;
	}

	public Integer getGameGroupCode() {
		return Integer.valueOf(betTypeCode.split("_")[0]);
	}

	public Integer getGameSetCode() {
		return Integer.valueOf(betTypeCode.split("_")[1]);
	}

	public Integer getBetMethodCode() {
		return Integer.valueOf(betTypeCode.split("_")[2]);
	}

	public void setMaxMultiple(Integer value) {
		this.maxMultiple = value;
	}

	public Integer getMaxMultiple() {
		return this.maxMultiple;
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

	public Integer getMaxCountIssue() {
		return maxCountIssue;
	}

	public void setMaxCountIssue(Integer maxCountIssue) {
		this.maxCountIssue = maxCountIssue;
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
