package com.winterframework.firefrog.fund.dao.vo;

import com.winterframework.orm.dal.ibatis3.BaseEntity;

/**
 * @author Lex
 * @ClassName: WhitedrawWhiteList
 * @Description:
 * @date 2014/9/10 11:21
 */
public class WithdrawWhiteList extends BaseEntity {
    private Long id;

    private String account;

    private String operator;
    private String topAcc;

    private String note;

    public String getTopAcc() {
		return topAcc;
	}

	public void setTopAcc(String topAcc) {
		this.topAcc = topAcc;
	}

	public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
