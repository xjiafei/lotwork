package com.winterframework.firefrog.fund.web.dto;

import java.util.List;

import com.winterframework.firefrog.fund.dao.vo.FundBank;
import com.winterframework.firefrog.fund.dao.vo.UserBankStruc;


public class BankCardQueryRecordResponse {
	private List<FundBank> bankStruc;
	
	private UserBankStruc[] userBankStruc;

	
	public List<FundBank> getBankStruc() {
		return bankStruc;
	}

	public void setBankStruc(List<FundBank> bankStruc) {
		this.bankStruc = bankStruc;
	}

	public UserBankStruc[] getUserBankStruc() {
		return userBankStruc;
	}

	public void setUserBankStruc(UserBankStruc[] userBankStruc) {
		this.userBankStruc = userBankStruc;
	}
}
