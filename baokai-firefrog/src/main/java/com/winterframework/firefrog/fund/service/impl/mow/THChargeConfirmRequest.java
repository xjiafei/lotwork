/**   
* @Title: ChargeConfirmRequest.java 
* @Package com.winterframework.firefrog.fund.web.dto 
* @Description: TODO(用一句话描述该文件做什么) 
* @author 你的名字   
* @date 2013-7-2 下午2:41:40 
* @version V1.0   
*/
package com.winterframework.firefrog.fund.service.impl.mow;

import java.io.Serializable;
import java.math.BigDecimal;

/** 
* @ClassName: ChargeConfirmRequest 
* @Description: TODO(这里用一句话描述这个类的作用) 
* @author 你的名字 
* @date 2013-7-2 下午2:41:40 
*  
*/
public class THChargeConfirmRequest implements Serializable {

	private static final long serialVersionUID = 6317616623420922012L;
	private String pay_time;
	private String mownecum_order_num;
	private String company_order_num;
	private Long exact_payment_bank_id;
	private String pay_card_num;
	private String pay_card_name;
	private String channel;
	private String area;
	private BigDecimal amount;
	private BigDecimal fee;
	private BigDecimal transaction_charge;
	private Long order_status;
	private String Key;
	private Long bank_id;
	private String operating_time;
	
	public Long getBank_id() {
		return bank_id;
	}
	public void setBank_id(Long bank_id) {
		this.bank_id = bank_id;
	}
	public String getPay_time() {
		return pay_time;
	}
	public void setPay_time(String pay_time) {
		this.pay_time = pay_time;
	}
	public BigDecimal getAmount() {
		return amount;
	}
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	public String getMownecum_order_num() {
		return mownecum_order_num;
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
	public Long getExact_payment_bank_id() {
		return exact_payment_bank_id;
	}
	public void setExact_payment_bank_id(Long exact_payment_bank_id) {
		this.exact_payment_bank_id = exact_payment_bank_id;
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
	public String getChannel() {
		return channel;
	}
	public void setChannel(String channel) {
		this.channel = channel;
	}
	public String getArea() {
		return area;
	}
	public void setArea(String area) {
		this.area = area;
	}
	public BigDecimal getFee() {
		return fee==null?BigDecimal.valueOf(0):fee;
	}
	public void setFee(BigDecimal fee) {
		this.fee = fee;
	}
	public BigDecimal getTransaction_charge() {
		return transaction_charge==null?BigDecimal.valueOf(0L):transaction_charge;
	}
	public void setTransaction_charge(BigDecimal transaction_charge) {
		this.transaction_charge = transaction_charge;
	}
	public Long getOrder_status() {
		return order_status;
	}
	public void setOrder_status(Long order_status) {
		this.order_status = order_status;
	}
	public String getKey() {
		return Key;
	}
	public void setKey(String key) {
		Key = key;
	}
	public String getOperating_time() {
		return operating_time;
	}
	public void setOperating_time(String operating_time) {
		this.operating_time = operating_time;
	}
	
	
}
