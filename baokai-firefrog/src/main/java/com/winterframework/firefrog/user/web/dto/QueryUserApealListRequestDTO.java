package com.winterframework.firefrog.user.web.dto;

import java.io.Serializable;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

/**
 * @author david
 * 用户申诉查询DTO
 */
public class QueryUserApealListRequestDTO implements Serializable {

	private static final long serialVersionUID = 4989953896140147015L;
	/**用户名*/
	private String account;
	/**审核人姓名*/
	private String operater;
	/**申诉类型
	  * 1 安全邮箱
	  * 2 安全信息*/
	@Min(1)
	@Max(3)
	private Integer appealType;
	/**状态
	 *1通过
	 *2未通过
	 *3未审核*/
	@Min(0)
	@Max(3)
	private Integer passed;
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

	public String getOperater() {
		return operater;
	}

	public void setOperater(String operater) {
		this.operater = operater;
	}

	public Integer getAppealType() {
		return appealType;
	}

	public void setAppealType(Integer appealType) {
		this.appealType = appealType;
	}

	public Integer getPassed() {
		return passed;
	}

	public void setPassed(Integer passed) {
		this.passed = passed;
	}

}
