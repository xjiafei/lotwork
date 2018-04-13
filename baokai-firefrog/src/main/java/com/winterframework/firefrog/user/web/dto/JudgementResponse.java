package com.winterframework.firefrog.user.web.dto;

import java.io.Serializable;

/**      
 *    
 * 类功能说明:工具类请求DTO   
 *
 * <p>Copyright: Copyright(c) 2013 </p> 
 * @Version 1.0  
 *  
 * 
 */
public class JudgementResponse implements Serializable {

	private static final long serialVersionUID = -3773386240342121322L;

	private String account;
	private Long effectTime;
	private Integer action;

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public Long getEffectTime() {
		return effectTime;
	}

	public void setEffectTime(Long effectTime) {
		this.effectTime = effectTime;
	}

	public Integer getAction() {
		return action;
	}

	public void setAction(Integer action) {
		this.action = action;
	}

}
