/**   
* @Title: CreateGeneralAgentRequest.java 
* @Package com.winterframework.firefrog.user.web.dto 
* @Description: TODO(用一句话描述该文件做什么) 
* @author 你的名字   
* @date 2013-5-6 下午3:43:50 
* @version V1.0   
*/
package com.winterframework.firefrog.user.web.dto;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import com.winterframework.modules.validate.FFLength;

/** 
* @ClassName: CreateGeneralAgentRequest 
* @Description: 总代开户请求 
* @author Ricahrd 
* @date 2013-5-6 下午3:43:50 
*  
*/
public class CreateGeneralAgentRequest implements Serializable {

	private static final long serialVersionUID = 4532262077452354995L;

	@NotNull
	@FFLength(max = 16, min = 4)
	private String account;
	@NotNull
	private String passwd;
	@NotNull
	private Long agentLimit;

	private Long registeIp;
	private Long userLvl;
	
	public Long getRegisteIp() {
		return registeIp;
	}

	public void setRegisteIp(Long registeIp) {
		this.registeIp = registeIp;
	}

	public CreateGeneralAgentRequest() {

	}

	public Long getUserLvl() {
		return userLvl;
	}

	public void setUserLvl(Long userLvl) {
		this.userLvl = userLvl;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getPasswd() {
		return passwd;
	}

	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}

	public Long getAgentLimit() {
		return agentLimit;
	}

	public void setAgentLimit(Long agentLimit) {
		this.agentLimit = agentLimit;
	}

}
