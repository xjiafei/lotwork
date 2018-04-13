package com.winterframework.firefrog.phone.web.cotroller.dto;

import java.io.Serializable;

import com.winterframework.firefrog.phone.web.fund.DDBDeposit;
import com.winterframework.firefrog.phone.web.fund.ECPSSDeposit;
import com.winterframework.firefrog.phone.web.fund.THDeposit;


public class ChargeApplyResponse implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5601797981531786584L;

	private Long orderId;

	private String revAccName;
	private String rcvBankName;
	private String rcvAccNum;

	private String rcvEmail;

	private Long expireTime;
	private String breakUrl;

	private UserBankStruc[] cards;

	private String chargeMemo;
	private Long bankId;
	private Long mode;
	private String customerIp;
	private String payOrderNo;
	private THDeposit thpay;
	private ECPSSDeposit pay;
	private DDBDeposit ddbPay;	
	
	public String getCustomerIp() {
		return customerIp;
	}
	public void setCustomerIp(String customerIp) {
		this.customerIp = customerIp;
	}
	public THDeposit getThpay() {
		return thpay;
	}
	public void setThpay(THDeposit thpay) {
		this.thpay = thpay;
	}
	public ECPSSDeposit getPay() {
		return pay;
	}
	public void setPay(ECPSSDeposit pay) {
		this.pay = pay;
	}
	public Long getOrderId() {
		return orderId;
	}
	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}
	public String getRevAccName() {
		return revAccName;
	}
	public void setRevAccName(String revAccName) {
		this.revAccName = revAccName;
	}
	public String getRcvBankName() {
		return rcvBankName;
	}
	public void setRcvBankName(String rcvBankName) {
		this.rcvBankName = rcvBankName;
	}
	public String getRcvAccNum() {
		return rcvAccNum;
	}
	public void setRcvAccNum(String rcvAccNum) {
		this.rcvAccNum = rcvAccNum;
	}
	public String getRcvEmail() {
		return rcvEmail;
	}
	public void setRcvEmail(String rcvEmail) {
		this.rcvEmail = rcvEmail;
	}
	public Long getExpireTime() {
		return expireTime;
	}
	public void setExpireTime(Long expireTime) {
		this.expireTime = expireTime;
	}
	public String getBreakUrl() {
		return breakUrl;
	}
	public void setBreakUrl(String breakUrl) {
		this.breakUrl = breakUrl;
	}
	public UserBankStruc[] getCards() {
		return cards;
	}
	public void setCards(UserBankStruc[] cards) {
		this.cards = cards;
	}
	public String getChargeMemo() {
		return chargeMemo;
	}
	public void setChargeMemo(String chargeMemo) {
		this.chargeMemo = chargeMemo;
	}
	public Long getBankId() {
		return bankId;
	}
	public void setBankId(Long bankId) {
		this.bankId = bankId;
	}
	public Long getMode() {
		return mode;
	}
	public void setMode(Long mode) {
		this.mode = mode;
	}
	public String getPayOrderNo() {
		return payOrderNo;
	}
	public void setPayOrderNo(String payOrderNo) {
		this.payOrderNo = payOrderNo;
	}
	public DDBDeposit getDdbPay() {
		return ddbPay;
	}
	public void setDdbPay(DDBDeposit ddbPay) {
		this.ddbPay = ddbPay;
	}
	
	
	
}
