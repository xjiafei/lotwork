package com.winterframework.firefrog.user.web.dto;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

/**
 * 
 *    
 * 类功能说明: PHP下级客户列表请求DTO
 *
 * <p>Copyright: Copyright(c) 2013 </p> 
 * @Version 1.0  
 * @author Richard 
 *
 */
public class QueryAgentSubUserRequestDTO implements Serializable {

	private static final long serialVersionUID = 3158588439359355469L;

	/**用户名*/
	//	@FFLength(max=16,min=4)
	//	private String account; //20130508接口修改注销
	/**最小金额*/
	//	private long fromBal;
	/**最大金额*/
	//	private long toBal;
	/**登录时间*/
	//	private long lastLoginDate;
	@NotNull
	private long userId;
	private String account;
	private String orderBy;

	public String getOrderBy() {
		return orderBy;
	}

	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public QueryAgentSubUserRequestDTO() {

	}

	/*public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public long getFromBal() {
		return fromBal;
	}

	public void setFromBal(long fromBal) {
		this.fromBal = fromBal;
	}

	public long getToBal() {
		return toBal;
	}

	public void setToBal(long toBal) {
		this.toBal = toBal;
	}

	public long getLastLoginDate() {
		return lastLoginDate;
	}

	public void setLastLoginDate(long lastLoginDate) {
		this.lastLoginDate = lastLoginDate;
	}*/

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

}
