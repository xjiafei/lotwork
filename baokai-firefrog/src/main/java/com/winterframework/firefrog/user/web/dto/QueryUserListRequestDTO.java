package com.winterframework.firefrog.user.web.dto;

import java.io.Serializable;

import org.hibernate.validator.constraints.Email;

import com.winterframework.modules.validate.FFLength;

/**
 * @author Richard
 * 用户列表查询请求对应DTO
 */
public class QueryUserListRequestDTO implements Serializable {

	private static final long serialVersionUID = -4527518883827561213L;

	/**用户名*/
	@FFLength(max = 16, min = 4)
	private String account;
	/**邮件*/
	@FFLength(max = 50, min = 6)
	@Email
	private String email;
	/**用户组类型*/
	private Integer userLvl;
	/**起始日期*/
	private Long fromDate;
	/**结束日期*/
	private Long toDate;
	/**最小金额*/
	private Long fromBal;
	/**最大金额*/
	private Long toBal;

	public QueryUserListRequestDTO() {

	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Integer getUserLvl() {
		return userLvl;
	}

	public void setUserLvl(Integer userLvl) {
		this.userLvl = userLvl;
	}

	public Long getFromDate() {
		return fromDate;
	}

	public void setFromDate(Long fromDate) {
		this.fromDate = fromDate;
	}

	public Long getToDate() {
		return toDate;
	}

	public void setToDate(Long toDate) {
		this.toDate = toDate;
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

}
