package com.winterframework.firefrog.fund.service.impl.th;

import java.math.BigDecimal;

public class ThPayWithDraw{
	
	private	String input_charset;
	private String merchant_code;
	private String merchant_order;
	private String bank_card_no;
	private String bank_account;
	private	String bank_code;
	private BigDecimal amount;
	private String sign;
	
	


	public String getInput_charset() {
		return input_charset;
	}
	public void setInput_charset(String input_charset) {
		this.input_charset = input_charset;
	}
	public String getMerchant_code() {
		return merchant_code;
	}
	public void setMerchant_code(String merchant_code) {
		this.merchant_code = merchant_code;
	}
	public String getMerchant_order() {
		return merchant_order;
	}
	public void setMerchant_order(String merchant_order) {
		this.merchant_order = merchant_order;
	}
	public String getBank_card_no() {
		return bank_card_no;
	}
	public void setBank_card_no(String bank_card_no) {
		this.bank_card_no = bank_card_no;
	}
	public String getBank_account() {
		return bank_account;
	}
	public void setBank_account(String bank_account) {
		this.bank_account = bank_account;
	}
	public String getBank_code() {
		return bank_code;
	}
	public void setBank_code(String bank_code) {
		this.bank_code = bank_code;
	}
	public BigDecimal getAmount() {
		return amount;
	}
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	public String getSign() {
		return sign;
	}
	public void setSign(String sign) {
		this.sign = sign;
	}
	
	public String createParam(String thpayKey){
		return "amount="+amount+"&bank_account="+bank_account+"&bank_card_no="+bank_card_no+"&bank_code="+bank_code+"&input_charset="+input_charset+"&merchant_code="+merchant_code+"&merchant_order="+merchant_order+"&key=" + thpayKey;
	}
}
