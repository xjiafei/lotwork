package com.winterframework.firefrog.fund.web.dto;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import com.winterframework.firefrog.common.convert.BeanConverter.Alias;

public class DepositApplyRequest implements Serializable {

	private static final long serialVersionUID = 1693193089926039794L;
	@NotNull
	private Long typeId;
	private Long rcvId;
	@Alias(field = "rcvAccount")
	private String rcvAct;
	private Long depositAmt;
	private String memo;
	private String attach;
	private Long bankId;
	private String note;
	private UserBankStruc userBankStruc;

	public Long getTypeId() {
		return typeId;
	}

	public void setTypeId(Long typeId) {
		this.typeId = typeId;
	}

	public Long getRcvId() {
		return rcvId;
	}

	public void setRcvId(Long rcvId) {
		this.rcvId = rcvId;
	}

	public String getRcvAct() {
		return rcvAct;
	}

	public void setRcvAct(String rcvAct) {
		this.rcvAct = rcvAct;
	}

	public Long getDepositAmt() {
		return depositAmt;
	}

	public void setDepositAmt(Long depositAmt) {
		this.depositAmt = depositAmt;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public String getAttach() {
		return attach;
	}

	public void setAttach(String attach) {
		this.attach = attach;
	}

	public Long getBankId() {
		return bankId;
	}

	public void setBankId(Long bankId) {
		this.bankId = bankId;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public UserBankStruc getUserBankStruc() {
		return userBankStruc;
	}

	public void setUserBankStruc(UserBankStruc userBankStruc) {
		this.userBankStruc = userBankStruc;
	}

}
