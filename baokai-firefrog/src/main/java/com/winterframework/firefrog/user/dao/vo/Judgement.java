package com.winterframework.firefrog.user.dao.vo;

import java.util.Date;

import com.winterframework.orm.dal.ibatis3.BaseEntity;

/**
 * @author david
 */

public class Judgement extends BaseEntity {

	private static final long serialVersionUID = 1042131415873729068L;
	//alias
	public static final String TABLE_ALIAS = "Judgement";
	public static final String ALIAS_ACCOUNT = "账号";
	public static final String ALIAS_EFFECT_TIME = "生效时间";
	public static final String ALIAS_ACTION = "类型";

	//date formats

	//columns START
	private String account;
	private Date effectTime;
	private Long action;

	//columns END

	public Judgement() {
	}

	public Judgement(Long id) {
		this.id = id;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public Date getEffectTime() {
		return effectTime;
	}

	public void setEffectTime(Date effectTime) {
		this.effectTime = effectTime;
	}

	public Long getAction() {
		return action;
	}

	public void setAction(Long action) {
		this.action = action;
	}

}
