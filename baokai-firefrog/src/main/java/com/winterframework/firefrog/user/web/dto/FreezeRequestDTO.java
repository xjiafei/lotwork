package com.winterframework.firefrog.user.web.dto;

import java.io.Serializable;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**      
 *    
 * 类功能说明:冻结请求DTO   
 *
 * <p>Copyright: Copyright(c) 2013 </p> 
 * @Version 1.0  
 *  
 * 
 */
public class FreezeRequestDTO implements Serializable {

	private static final long serialVersionUID = -3773386240342121322L;

	@NotNull
	@Max(value = 1)
	@Min(value = 0)
	private Integer range;
	@NotNull
	private Long userId;
	@Max(value = 5)
	@Min(value = 1)
	private Integer method;
	@NotNull
	private String memo;
	@NotNull
	private String freezeAccount;

	public Integer getRange() {
		return range;
	}

	public void setRange(Integer range) {
		this.range = range;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Integer getMethod() {
		return method;
	}

	public void setMethod(Integer method) {
		this.method = method;
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
