package com.winterframework.firefrog.fund.service.impl.th;

import java.io.Serializable;
import java.math.BigDecimal;

public class WithdrawConfirmRequest implements Serializable {

	private static final long serialVersionUID = 1857974106834203521L;
	private String mownecum_order_num;
	private String company_order_num;
	private Long status;
	private String detail;
	private BigDecimal amount;
	private BigDecimal exact_transaction_charge;
	private String key;
	private String error_msg;
	private String operating_time;
	

	public String getMownecum_order_num() {
		return mownecum_order_num;
	}
	
	public BigDecimal getExact_transaction_charge() {
		return exact_transaction_charge;
	}

	public void setExact_transaction_charge(BigDecimal exact_transaction_charge) {
		this.exact_transaction_charge = exact_transaction_charge;
	}

	public void setMownecum_order_num(String mownecum_order_num) {
		this.mownecum_order_num = mownecum_order_num;
	}
	public String getCompany_order_num() {
		return company_order_num;
	}
	public void setCompany_order_num(String company_order_num) {
		this.company_order_num = company_order_num;
	}
	public Long getStatus() {
		return status;
	}
	public void setStatus(Long status) {
		this.status = status;
	}
	public String getDetail() {
		return detail;
	}
	public void setDetail(String detail) {
		this.detail = detail;
	}
	public BigDecimal getAmount() {
		return amount;
	}
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getError_msg() {
		return error_msg;
	}
	public void setError_msg(String error_msg) {
		this.error_msg = error_msg;
	}

	public String getOperating_time() {
		return operating_time;
	}

	public void setOperating_time(String operating_time) {
		this.operating_time = operating_time;
	}
	
}