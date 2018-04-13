package com.winterframework.firefrog.user.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * 
* @ClassName: QueryGeneralAgentDTO 
* @Description: 总代用户查询DTO 
* @author Richard
* @date 2013-5-6 下午4:07:18 
*
 */
public class QueryGeneralAgentDTO implements Serializable {

	private static final long serialVersionUID = -4608930527255014498L;

	private String userName;
	private String email;
	private Date fromRegisterDate;
	private Date toRegisterDate;
	private Long fromBal;
	private Long toBal;
	private boolean isAccountMatch;

	public boolean isAccountMatch() {
		return isAccountMatch;
	}

	public void setAccountMatch(boolean isAccountMatch) {
		this.isAccountMatch = isAccountMatch;
	}

	public QueryGeneralAgentDTO() {

	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Date getFromRegisterDate() {
		return fromRegisterDate;
	}

	public void setFromRegisterDate(Date fromRegisterDate) {
		this.fromRegisterDate = fromRegisterDate;
	}

	public Date getToRegisterDate() {
		return toRegisterDate;
	}

	public void setToRegisterDate(Date toRegisterDate) {
		this.toRegisterDate = toRegisterDate;
	}

	public Long getFromBal() {
		return fromBal;
	}

	public void setFromBal(Long fromBal) {
		this.fromBal = fromBal;
	}

	public Long getToBal() {
		return toBal;
	}

	public void setToBal(Long toBal) {
		this.toBal = toBal;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

}
