package com.winterframework.firefrog.global.dao.vo;





import java.util.Date;

import com.winterframework.orm.dal.ibatis3.BaseEntity;

/**
 * @author cms group
 * @version 1.0
 * @since 1.0
 */


public class GlobalGrayListVO extends BaseEntity {
	
	private static final long serialVersionUID = 7584082414837113117L;
	
	//date formats
	
	//columns START
	private Long id;
	private Long userId;
	private Date lastLoginTime;
	private Long riskType;
	private Date createTime;
	private Date updateTime;
	private Date registerDate;
	//columns END
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public Date getLastLoginTime() {
		return lastLoginTime;
	}
	public void setLastLoginTime(Date lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}
	public Long getRiskType() {
		return riskType;
	}
	public void setRiskType(Long riskType) {
		this.riskType = riskType;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	public Date getRegisterDate() {
		return registerDate;
	}
	public void setRegisterDate(Date registerDate) {
		this.registerDate = registerDate;
	}
}

