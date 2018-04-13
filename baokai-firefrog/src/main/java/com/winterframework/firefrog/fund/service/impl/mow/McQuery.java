package com.winterframework.firefrog.fund.service.impl.mow;

import java.math.BigDecimal;

public class McQuery {

	private String company_order_num;
	private String mownecum_order_num;
	private BigDecimal amount;
	private String card_num;
	private String card_name;
	private String company_user;
	private String key;

	public String getCompany_order_num() {
		return company_order_num;
	}

	public void setCompany_order_num(String company_order_num) {
		this.company_order_num = company_order_num;
	}

	public String getMownecum_order_num() {
		return mownecum_order_num;
	}

	public void setMownecum_order_num(String mownecum_order_num) {
		this.mownecum_order_num = mownecum_order_num;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public String getCard_num() {
		return card_num;
	}

	public void setCard_num(String card_num) {
		this.card_num = card_num;
	}

	public String getCard_name() {
		return card_name;
	}

	public void setCard_name(String card_name) {
		this.card_name = card_name;
	}

	public String getCompany_user() {
		return company_user;
	}

	public void setCompany_user(String company_user) {
		this.company_user = company_user;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

}
