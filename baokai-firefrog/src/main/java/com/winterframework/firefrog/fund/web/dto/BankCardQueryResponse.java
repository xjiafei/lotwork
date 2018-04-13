/**   
* @Title: BankCardQueryResponse.java 
* @Package com.winterframework.firefrog.fund.web.dto 
* @Description:  查询绑定银行卡响应参数DTO 
* @author Denny  
* @date 2013-7-2 上午9:26:33 
* @version V1.0   
*/
package com.winterframework.firefrog.fund.web.dto;

import java.util.List;

/** 
* @ClassName: BankCardQueryResponse 
* @Description: 查询绑定银行卡响应参数DTO  
* @author Denny 
* @date 2013-7-2 上午9:26:33 
*  
*/
public class BankCardQueryResponse {

	/** 绑卡记录 */
	private List<UserBankStruc> userBankStruc;
	/** 绑卡超时时间 */
	private long overTime;
	private long dbNowTime;
	
	public long getDbNowTime() {
		return dbNowTime;
	}

	public void setDbNowTime(long dbNowTime) {
		this.dbNowTime = dbNowTime;
	}

	public List<UserBankStruc> getUserBankStruc() {
		return userBankStruc;
	}

	public void setUserBankStruc(List<UserBankStruc> userBankStruc) {
		this.userBankStruc = userBankStruc;
	}

	public long getOverTime() {
		return overTime;
	}

	public void setOverTime(long overTime) {
		this.overTime = overTime;
	}

}
