package com.winterframework.firefrog.global.dao.vo;





import java.util.Date;

import com.winterframework.orm.dal.ibatis3.BaseEntity;

/**
 * @author cms group
 * @version 1.0
 * @since 1.0
 */


public class GlobalGrayListTestVO extends BaseEntity {
	
	private static final long serialVersionUID = 7584082414837113117L;
	
	//date formats
	
	//columns START
	private Long riskType;
	private String account;
	public Long getRiskType() {
		return riskType;
	}
	public void setRiskType(Long riskType) {
		this.riskType = riskType;
	}
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	
}

