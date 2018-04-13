package com.winterframework.firefrog.phone.web.cotroller.dto;

import java.io.Serializable;
import java.util.List;

public class BankCardQueryRecordResponse implements Serializable {

	private static final long serialVersionUID = 3116262285197878791L;

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
