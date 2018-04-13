package com.winterframework.firefrog.game.dao.vo;

import java.util.Date;

import com.winterframework.orm.dal.ibatis3.BaseEntity;

/**
 * @author cms group
 * @version 1.0
 * @since 1.0
 */

public class GameAwardGroupCheck extends BaseEntity {

	private static final long serialVersionUID = -7868712029621259094L;
	//alias
	public static final String TABLE_ALIAS = "奖金组审核表";
	public static final String ALIAS_LOTTERYID = "彩种";
	public static final String ALIAS_AWARD_NAME = "奖金组名称";
	public static final String ALIAS_DIRECT_RET = "直选及其他返点";
	public static final String ALIAS_THREEONE_RET = "三星一码不定位返点";
	public static final String ALIAS_CHECK_STATUS = "状态 1:待审核 2:待发布";
	public static final String ALIAS_SYS_AWARD_GROUP = "奖金组类型 1:系统奖金组 2:用户奖金组";
	public static final String ALIAS_CREATE_TIME = "创建时间";
	public static final String ALIAS_UPDATE_TIME = "更新时间";

	//date formats

	//columns START
	private Long lotteryid;
	private String awardName;
	private Long directRet;
	private Long threeoneRet;
	private Long status;
	private Integer sysAwardGroup;
	private Date createTime;
	private Date updateTime;
	
	private Long awardGroupid;

	//columns END

	public GameAwardGroupCheck() {
	}

	public GameAwardGroupCheck(Long id) {
		this.id = id;
	}

	public void setLotteryid(Long value) {
		this.lotteryid = value;
	}

	public Long getLotteryid() {
		return this.lotteryid;
	}

	public void setAwardName(String value) {
		this.awardName = value;
	}

	public String getAwardName() {
		return this.awardName;
	}

	public void setDirectRet(Long value) {
		this.directRet = value;
	}

	public Long getDirectRet() {
		return this.directRet;
	}

	public void setThreeoneRet(Long value) {
		this.threeoneRet = value;
	}

	public Long getThreeoneRet() {
		return this.threeoneRet;
	}


	public void setSysAwardGroup(Integer value) {
		this.sysAwardGroup = value;
	}

	public Integer getSysAwardGroup() {
		return this.sysAwardGroup;
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

	public Long getStatus() {
		return status;
	}

	public void setStatus(Long status) {
		this.status = status;
	}

	public Long getAwardGroupid() {
		return awardGroupid;
	}

	public void setAwardGroupid(Long awardGroupid) {
		this.awardGroupid = awardGroupid;
	}
	
}
