package com.winterframework.firefrog.user.web.dto;

import java.io.Serializable;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * 用户申述详情，审核界面信息
 * 
 * @author david
 * 
 */
public class UserAppealAuditRequestDTO implements Serializable {

	private static final long serialVersionUID = 5924326617496255471L;

	@NotNull
	private Long id;

	@NotNull
	@Min(1)
	@Max(3)
	private Long passed;

	@NotNull
	//TODO@Pattern(regexp="[1,3,5,7]{1}")
	private int activeDate;

	@NotNull
	private String memo;

	

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getPassed() {
		return passed;
	}

	public void setPassed(Long passed) {
		this.passed = passed;
	}

	public int getActiveDate() {
		return activeDate;
	}

	public void setActiveDate(int activeDate) {
		this.activeDate = activeDate;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

}
