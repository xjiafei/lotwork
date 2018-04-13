package com.winterframework.firefrog.fund.service.impl.huayin;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "trade")
public class HuaYinQueryTradeResponseDetail implements Serializable{
	@XmlElement(name = "merchant_code")
	private String merchantCode;
	@XmlElement(name = "order_no")
	private String orderNo;
	@XmlElement(name = "order_time")
	private String orderTime;
	@XmlElement(name = "order_amount")
	private String orderAmount;
	@XmlElement(name = "orginal_money")
	private String orginalMoney;
	@XmlElement(name = "trade_no")
	private String tradeNo;
	@XmlElement(name = "trade_time")
	private String tradeTime;
	@XmlElement(name = "trade_status")
	private String tradeStatus;
	 
	public String getMerchantCode() {
		return merchantCode;
	}
	public String getOrderNo() {
		return orderNo;
	}
	public String getOrderTime() {
		return orderTime;
	}
	public String getOrderAmount() {
		return orderAmount;
	}
	public String getOrginalMoney() {
		return orginalMoney;
	}
	public String getTradeNo() {
		return tradeNo;
	}
	public String getTradeTime() {
		return tradeTime;
	}
	public String getTradeStatus() {
		return tradeStatus;
	}
	
	
		
	
}
