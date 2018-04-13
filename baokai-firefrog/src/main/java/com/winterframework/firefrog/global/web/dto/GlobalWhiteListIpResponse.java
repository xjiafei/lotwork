package com.winterframework.firefrog.global.web.dto;

import java.io.Serializable;

/**
 * 指定IP白名單 Request DTO
 * @author David.Wu
 * 
 * REVISION HISTORY
 * -----------------------------------
 * 2016.05.19 David Wu Create
 *
 */
/**
 * @author David.Wu
 *
 */
public class GlobalWhiteListIpResponse implements Serializable{
	
	private static final long serialVersionUID = -9044833541856672042L;
	
	/**
	 * 成功/失敗
	 * true/false
	 */
	private int isSuccess;
	
	private String msg;
	/**
	 * 1:新增 : 已有IP存在
	 * 2:修改 : 修改的IP 不存在
	 * 
	 */
	private int resultType;
	
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public int getResultType() {
		return resultType;
	}
	public void setResultType(int resultType) {
		this.resultType = resultType;
	}
	public int getIsSuccess() {
		return isSuccess;
	}
	public void setIsSuccess(int isSuccess) {
		this.isSuccess = isSuccess;
	}
}
