package com.winterframework.firefrog.fund.web.dto;

import com.winterframework.firefrog.fund.dao.vo.WithdrawWhiteList;
import com.winterframework.modules.page.PageRequest;

import java.io.Serializable;
import java.util.List;

/**
 * @author Lex
 * @ClassName: WithdrawWhiteList
 * @Description:
 * @date 2014/9/10 14:49
 */
public class WithdrawWhiteListRequest implements Serializable {
    private static final long serialVersionUID = -6667017423993223297L;

    private String action;
    private String operator;
    private String note;
    private List<WithdrawWhiteList> withdrawWhiteList;

    private List<String> accountList;


    public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getAction() {
        return action;
    }

 

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public void setAction(String action) {
        this.action = action;
    }

    public List<WithdrawWhiteList> getWithdrawWhiteList() {
        return withdrawWhiteList;
    }

    public void setWithdrawWhiteList(List<WithdrawWhiteList> withdrawWhiteList) {
        this.withdrawWhiteList = withdrawWhiteList;
    }

    public List<String> getAccountList() {
        return accountList;
    }

    public void setAccountList(List<String> accountList) {
        this.accountList = accountList;
    }
   
}
