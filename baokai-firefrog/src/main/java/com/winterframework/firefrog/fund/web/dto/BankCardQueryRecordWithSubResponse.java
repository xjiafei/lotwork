/**   
* @Title: BankCardQueryRecordWithSubResponse.java 
* @Package com.winterframework.firefrog.fund.web.dto 
* @Description: TODO(用一句话描述该文件做什么) 
* @author 你的名字   
* @date 2013-8-13 上午9:36:30 
* @version V1.0   
*/
package com.winterframework.firefrog.fund.web.dto;

import java.util.List;

import com.winterframework.firefrog.fund.dao.vo.FundBank;
import com.winterframework.firefrog.fund.dao.vo.FundBankWithSub;

/** 
* @ClassName: BankCardQueryRecordWithSubResponse 
* @Description: 获取银行列表response 
* @author Alan
* @date 2013-8-13 上午9:36:30 
*  
*/
public class BankCardQueryRecordWithSubResponse {

	private List<FundBankWithSub> bankStruc;
	
	private UserBankStruc[] userBankStruc;

	
	public List<FundBankWithSub> getBankStruc() {
		return bankStruc;
	}

	public void setBankStruc(List<FundBankWithSub> bankStruc) {
		this.bankStruc = bankStruc;
	}

	public UserBankStruc[] getUserBankStruc() {
		return userBankStruc;
	}

	public void setUserBankStruc(UserBankStruc[] userBankStruc) {
		this.userBankStruc = userBankStruc;
	}

}
