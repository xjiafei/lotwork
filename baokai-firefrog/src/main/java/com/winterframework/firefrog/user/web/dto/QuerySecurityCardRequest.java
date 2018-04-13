package com.winterframework.firefrog.user.web.dto;

import java.io.Serializable;

/**
 * 
* @Description: 查询中心安全动态码的DTO
*
 */
public class QuerySecurityCardRequest implements Serializable {

	private static final long serialVersionUID = 7892696616807272215L;

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

	public QuerySecurityCardRequest() {
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

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
}
