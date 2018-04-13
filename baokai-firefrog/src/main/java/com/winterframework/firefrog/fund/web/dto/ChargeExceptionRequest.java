/**   
* @Title: ChargeExceptionRequest.java 
* @Package com.winterframework.firefrog.fund.web.dto 
* @Description: TODO(用一句话描述该文件做什么) 
* @author 你的名字   
* @date 2013-7-8 下午6:49:57 
* @version V1.0   
*/
package com.winterframework.firefrog.fund.web.dto;

import java.io.Serializable;
import java.math.BigDecimal;

/** 
* @ClassName: ChargeExceptionRequest 
* @Description: TODO(这里用一句话描述这个类的作用) 
* @author 你的名字 
* @date 2013-7-8 下午6:49:57 
*  
*/
public class ChargeExceptionRequest implements Serializable {

	private static final long serialVersionUID = 7498283937644298605L;

	private String exception_order_num;

	private String company_code;

	private String exact_payment_bank;

	private String pay_card_num;

	private String pay_card_name;

	private String receiving_bank;

	private String email;

	private String channel;

	private String note;

	private String area;

	private String exact_time;

	private BigDecimal amount;

	private String fee;

	private BigDecimal transaction_charge;
	private String receiving_account_name;

	private String key;
	private String operating_time;
	
	private String base_info;

	public String getException_order_num() {
		return exception_order_num;
	}
	public Long getExeptionId(){
		return Long.valueOf(exception_order_num.substring(4));
	}
	public void setException_order_num(String exception_order_num) {
		this.exception_order_num = exception_order_num;
	}

	public String getCompany_code() {
		return company_code;
	}

	public String getReceiving_account_name() {
		return receiving_account_name;
	}
	public void setReceiving_account_name(String receiving_account_name) {
		this.receiving_account_name = receiving_account_name;
	}
	public void setCompany_code(String company_code) {
		this.company_code = company_code;
	}

	public String getExact_payment_bank() {
		return exact_payment_bank;
	}

	public void setExact_payment_bank(String exact_payment_bank) {
		this.exact_payment_bank = exact_payment_bank;
	}

	public String getPay_card_num() {
		return pay_card_num;
	}

	public void setPay_card_num(String pay_card_num) {
		this.pay_card_num = pay_card_num;
	}

	public String getPay_card_name() {
		return pay_card_name;
	}

	public void setPay_card_name(String pay_card_name) {
		this.pay_card_name = pay_card_name;
	}

	public String getReceiving_bank() {
		return receiving_bank;
	}

	public void setReceiving_bank(String receiving_bank) {
		this.receiving_bank = receiving_bank;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public String getExact_time() {
		return exact_time;
	}

	public void setExact_time(String exact_time) {
		this.exact_time = exact_time;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public String getFee() {
		return fee;
	}

	public void setFee(String fee) {
		this.fee = fee;
	}

	public BigDecimal getTransaction_charge() {
		return transaction_charge;
	}

	public void setTransaction_charge(BigDecimal transaction_charge) {
		this.transaction_charge = transaction_charge;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}
	public String getOperating_time() {
		return operating_time;
	}
	public void setOperating_time(String operating_time) {
		this.operating_time = operating_time;
	}
	public String getBase_info() {
		return base_info;
	}
	public void setBase_info(String base_info) {
		this.base_info = base_info;
	}

	
}
