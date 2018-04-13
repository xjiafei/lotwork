package com.winterframework.firefrog.user.web.dto;

import java.io.Serializable;

/**
 * 
* @ClassName: QueryGeneralAgentRequestDTO 
* @Description: 查询总代用户请求DTO
* @author Richard
* @date 2013-5-6 下午2:44:55 
*
 */
public class QueryGeneralAgentRequest implements Serializable {

	private static final long serialVersionUID = 4966687835794481343L;
	private String userName;
	private String email;
	private Long fromRegisterDate;
	private Long toRegisterDate;
	private Long fromBal;
	private Long toBal;

	public QueryGeneralAgentRequest() {

	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Long getFromRegisterDate() {
		return fromRegisterDate;
	}

	public void setFromRegisterDate(Long fromRegisterDate) {
		this.fromRegisterDate = fromRegisterDate;
	}

	public Long getToRegisterDate() {
		return toRegisterDate;
	}

	public void setToRegisterDate(Long toRegisterDate) {
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
