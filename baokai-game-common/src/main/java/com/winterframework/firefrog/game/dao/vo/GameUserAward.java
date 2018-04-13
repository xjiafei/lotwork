package com.winterframework.firefrog.game.dao.vo;

import java.util.Date;

import com.winterframework.orm.dal.ibatis3.BaseEntity;

/**
 * @author cms group
 * @version 1.0
 * @since 1.0
 */

public class GameUserAward extends BaseEntity {

	private static final long serialVersionUID = 2747443268392776904L;
	//alias
	public static final String TABLE_ALIAS = "用户奖金组明细";
	public static final String ALIAS_LOTTERYID = "彩种id";
	public static final String ALIAS_BETTYPECODE = "玩法";
	public static final String ALIAS_AWARDGROUPID = "奖金组id";
	public static final String ALIAS_CREATE_TIME = "创建时间";
	public static final String ALIAS_UPDATE_TIME = "更新时间";
	public static final String ALIAS_STATUS = "状态";
	public static final String ALIAS_RETVALUE = "返点百分比";
	public static final String ALIAS_USERID = "用户ID";

	//date formats

	//columns START
	private Long lotteryId;
	private String betTypeCode;
	private Long awardGroupId;
	private Date createTime;
	private Date updateTime;
	private Integer status;
	private Long retValue;
	private Long userId;

	//columns END
	public enum Status {  
		//状态:1：正常 2:删除
		NORMAL(1), DETELED(2);
		private int _value = 1; 
		Status(int value) {
			this._value = value;
		} 
		public int getValue() {
			return _value;
		}
	}
	public GameUserAward() {
	}

	public GameUserAward(Long id) {
		this.id = id;
	}

	public Long getLotteryId() {
		return lotteryId;
	}

	public void setLotteryId(Long lotteryId) {
		this.lotteryId = lotteryId;
	}

	public String getBetTypeCode() {
		return betTypeCode;
	}

	public void setBetTypeCode(String betTypeCode) {
		this.betTypeCode = betTypeCode;
	}

	public Long getAwardGroupId() {
		return awardGroupId;
	}

	public void setAwardGroupId(Long awardGroupId) {
		this.awardGroupId = awardGroupId;
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

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Long getRetValue() {
		return retValue;
	}

	public void setRetValue(Long retValue) {
		this.retValue = retValue;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}
	
}
