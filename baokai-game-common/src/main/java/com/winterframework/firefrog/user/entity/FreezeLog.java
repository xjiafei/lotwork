package com.winterframework.firefrog.user.entity;

import java.util.Date;

import com.winterframework.firefrog.user.dao.vo.UserFreezeLog;
import com.winterframework.orm.dal.ibatis3.BaseEntity;

public class FreezeLog extends BaseEntity {

	private static final long serialVersionUID = 8073957120465080994L;

	public enum FrozenAction {
		Freeze(1), UnFreeze(0);

		public int _action = 0;

		FrozenAction(int action) {
			this._action = action;
		}

		public int getValue() {
			return this._action;
		}
	}

	public enum FreezenRange {

		SingleUser(1), UserTree(0);

		public int _range = 1;

		FreezenRange(int range) {
			this._range = range;
		}

		public int getValue() {
			return this._range;
		}
	}

	private Long dbId;
	private FreezenRange range;
	private UserFreezeLog userFreeLog;
	private int method;

	private String reason;
	private String actorAccount;
	private String freezeAccount;
	private String unfreezeAccount;

	private Date time;

	private User frozenUser;

	private User actor;
	private FreezenRange unfreezeRange;
	private int unfreezeMethod;
	private String unfreezeMemo;
	private Date unfreezeDate;
	private User unfreezeActor;

	private FrozenAction action;

	public FreezenRange getUnfreezeRange() {
		return unfreezeRange;
	}

	public void setUnfreezeRange(FreezenRange unfreezeRange) {
		this.unfreezeRange = unfreezeRange;
	}

	public int getUnfreezeMethod() {
		return unfreezeMethod;
	}

	public void setUnfreezeMethod(int unfreezeMethod) {
		this.unfreezeMethod = unfreezeMethod;
	}

	public String getUnfreezeMemo() {
		return unfreezeMemo;
	}

	public void setUnfreezeMemo(String unfreezeMemo) {
		this.unfreezeMemo = unfreezeMemo;
	}

	public Date getUnfreezeDate() {
		return unfreezeDate;
	}

	public void setUnfreezeDate(Date unfreezeDate) {
		this.unfreezeDate = unfreezeDate;
	}

	public User getUnfreezeActor() {
		return unfreezeActor;
	}

	public void setUnfreezeActor(User unfreezeActor) {
		this.unfreezeActor = unfreezeActor;
	}

	private Integer userLvl;//记录冻结用户的级别

	public FreezenRange getRange() {
		return range;
	}

	public void setRange(FreezenRange range) {
		this.range = range;
	}

	public Integer getMethod() {
		return method;
	}

	public void setMethod(Integer method) {
		this.method = method;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	public User getFrozenUser() {
		return frozenUser;
	}

	public void setFrozenUser(User frozenUser) {
		this.frozenUser = frozenUser;
	}

	public User getActor() {
		return actor;
	}

	public void setActor(User actor) {
		this.actor = actor;
	}

	public FrozenAction getAction() {
		return action;
	}

	public void setAction(FrozenAction action) {
		this.action = action;
	}

	public Integer getUserLvl() {
		return userLvl;
	}

	public void setUserLvl(Integer userLvl) {
		this.userLvl = userLvl;
	}

	public Long getDbId() {
		return dbId;
	}

	public void setDbId(Long dbId) {
		this.dbId = dbId;
	}

	public UserFreezeLog getUserFreeLog() {
		return userFreeLog;
	}

	public void setUserFreeLog(UserFreezeLog userFreeLog) {
		this.userFreeLog = userFreeLog;
	}

	public String getActorAccount() {
		return actorAccount;
	}

	public void setActorAccount(String actorAccount) {
		this.actorAccount = actorAccount;
	}

	public String getFreezeAccount() {
		return freezeAccount;
	}

	public void setFreezeAccount(String freezeAccount) {
		this.freezeAccount = freezeAccount;
	}

	public String getUnfreezeAccount() {
		return unfreezeAccount;
	}

	public void setUnfreezeAccount(String unfreezeAccount) {
		this.unfreezeAccount = unfreezeAccount;
	}

}
