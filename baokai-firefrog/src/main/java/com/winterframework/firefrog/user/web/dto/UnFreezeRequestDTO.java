package com.winterframework.firefrog.user.web.dto;

import java.io.Serializable;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * 
 *    
 * 类功能说明: 解冻用户请求
 *
 * <p>Copyright: Copyright(c) 2013 </p> 
 * @Version 1.0  
 * @author Richard
 *
 */
public class UnFreezeRequestDTO implements Serializable {

	private static final long serialVersionUID = -6676056508478647549L;

	@NotNull
	private Long userId;
	@NotNull
	@Max(value = 1)
	@Min(value = 0)
	private Integer range;
	@NotNull
	private String memo;
	@NotNull
	private String freezeAccount;

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Integer getRange() {
		return range;
	}

	public void setRange(Integer range) {
		this.range = range;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public String getFreezeAccount() {
		return freezeAccount;
	}

	public void setFreezeAccount(String freezeAccount) {
		this.freezeAccount = freezeAccount;
	}

}
