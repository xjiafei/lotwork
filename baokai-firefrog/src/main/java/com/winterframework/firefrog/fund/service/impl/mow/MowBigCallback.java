package com.winterframework.firefrog.fund.service.impl.mow;

import java.math.BigDecimal;

public class MowBigCallback {

	private String bank_card_num            ;
	private String bank_acc_name            ;
	private BigDecimal amount                   ;
	private String email                    ;
	private String company_order_num        ;
	private String datetime                 ;
	private String key                      ;
	private String note                     ;
	private String mownecum_order_num       ;
	private BigDecimal status                   ;
	private String error_msg                ;
	private BigDecimal mode                     ;
	private String issuing_bank_address     ;
	private Long collection_bank_id;
	public String getBank_card_num() {
		return bank_card_num;
	}
	
	public Long getCollection_bank_id() {
		return collection_bank_id;
	}

	public void setCollection_bank_id(Long collection_bank_id) {
		this.collection_bank_id = collection_bank_id;
	}

	public void setBank_card_num(String bank_card_num) {
		this.bank_card_num = bank_card_num;
	}
	public String getBank_acc_name() {
		return bank_acc_name;
	}
	public void setBank_acc_name(String bank_acc_name) {
		this.bank_acc_name = bank_acc_name;
	}
	public BigDecimal getAmount() {
		return amount;
	}
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getCompany_order_num() {
		return company_order_num;
	}
	public void setCompany_order_num(String company_order_num) {
		this.company_order_num = company_order_num;
	}
	public String getDatetime() {
		return datetime;
	}
	public void setDatetime(String datetime) {
		this.datetime = datetime;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	public String getMownecum_order_num() {
		return mownecum_order_num;
	}
	public void setMownecum_order_num(String mownecum_order_num) {
		this.mownecum_order_num = mownecum_order_num;
	}
	public BigDecimal getStatus() {
		return status;
	}
	public void setStatus(BigDecimal status) {
		this.status = status;
	}
	public String getError_msg() {
		return error_msg;
	}
	public void setError_msg(String error_msg) {
		this.error_msg = error_msg;
	}
	public BigDecimal getMode() {
		return mode;
	}
	public void setMode(BigDecimal mode) {
		this.mode = mode;
	}
	public String getIssuing_bank_address() {
		return issuing_bank_address;
	}
	public void setIssuing_bank_address(String issuing_bank_address) {
		this.issuing_bank_address = issuing_bank_address;
	}
	


}
