package com.winterframework.firefrog.fund.service.impl.th;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "response")
public class ThPayQueryResponseOrderCheckDetail implements Serializable{

	@XmlElement(name = "is_success")
	private String isSuccess;
	@XmlElement(name = "sign")
	private String sign;
	@XmlElement(name = "errror_msg")
	private String errror_msg;
	@XmlElement(name = "merchant_code")
	private String merchant_code;
	@XmlElement(name = "order_no")
	private String order_no;
	@XmlElement(name = "order_amount")
	private String order_amount;
	@XmlElement(name = "order_time")
	private String order_time;
	@XmlElement(name = "trade_no")
	private String trade_no;
	@XmlElement(name = "trade_time")
	private String trade_time;
	@XmlElement(name = "trade_status")
	private String trade_status;
	
	
	public String getIsSuccess() {
		return isSuccess;
	}
	public String getSign() {
		return sign;
	}
	public String getErrror_msg() {
		return errror_msg;
	}
	public String getMerchant_code() {
		return merchant_code;
	}
	public String getOrder_no() {
		return order_no;
	}
	public String getOrder_amount() {
		return order_amount;
	}
	public String getOrder_time() {
		return order_time;
	}
	public String getTrade_no() {
		return trade_no;
	}
	public String getTrade_time() {
		return trade_time;
	}
	public String getTrade_status() {
		return trade_status;
	}

	
	

	

	
	
}
