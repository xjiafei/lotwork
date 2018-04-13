package com.winterframework.firefrog.user.entity;

import java.util.Date;

import com.winterframework.orm.dal.ibatis3.BaseEntity;

public class SecurityCard extends BaseEntity {

	private static final long serialVersionUID = 330877670863849095L;

	/** 
	* 用户id
	*/
	private Long userId;
	/** 
	* 安全中心验证码
	*/
	private Long sercurityCode;

	/** 
	* 安全中心序列号
	*/
	private String sercuritySerilizeNumber;

	/** 
	* 手机类型1:IPhone   2:Android
	*/
	private Long phoneType;

	/** 
	* 解绑原因   0:手机遗失,1:不用也安全,2:使用不便,3:其他
	*/
	private Long unbindType;

	/** 
	* 是否绑定序列号 
	*/
	private Long bindPhoneSerial;
	private Date bindDate;

	public Long getSercurityCode() {
		return sercurityCode;
	}

	public void setSercurityCode(Long sercurityCode) {
		this.sercurityCode = sercurityCode;
	}

	public String getSercuritySerilizeNumber() {
		return sercuritySerilizeNumber;
	}

	public void setSercuritySerilizeNumber(String sercuritySerilizeNumber) {
		this.sercuritySerilizeNumber = sercuritySerilizeNumber;
	}

	public Long getPhoneType() {
		return phoneType;
	}

	public void setPhoneType(Long phoneType) {
		this.phoneType = phoneType;
	}

	public Long getUnbindType() {
		return unbindType;
	}

	public void setUnbindType(Long unbindType) {
		this.unbindType = unbindType;
	}

	public Long getBindPhoneSerial() {
		return bindPhoneSerial;
	}

	public void setBindPhoneSerial(Long bindPhoneSerial) {
		this.bindPhoneSerial = bindPhoneSerial;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Date getBindDate() {
		return bindDate;
	}

	public void setBindDate(Date bindDate) {
		this.bindDate = bindDate;
	}

}
