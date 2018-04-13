/**   
* @Title: BankCardLockingRequest.java 
* @Package com.winterframework.firefrog.fund.web.dto 
* @Description: TODO(用一句话描述该文件做什么) 
* @author 你的名字   
* @date 2013-7-24 上午9:35:33 
* @version V1.0   
*/
package com.winterframework.firefrog.fund.web.dto;

/** 
* @ClassName: BankCardLockingRequest 
* @Description: 加解锁请求参数
* @author Alan
* @date 2013-7-24 上午9:35:33 
*  
*/
public class BankCardLockingRequest {

	//操作账号
	private String operator;
	//过期时间
	private Long overTime;
	//锁定记录id
	private Long lockId;

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public Long getOverTime() {
		return overTime;
	}

	public void setOverTime(Long overTime) {
		this.overTime = overTime;
	}

	public Long getLockId() {
		return lockId;
	}

	public void setLockId(Long lockId) {
		this.lockId = lockId;
	}

}
