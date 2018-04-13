package com.winterframework.firefrog.fund.service.impl.huayin;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "resppay")
public class HuaYinPayWithdrawResponseDetail implements Serializable{
	@XmlElement(name = "merchant_code")
	private String merchantCode;
	@XmlElement(name = "order_amount")
	private String orderAmount;
	@XmlElement(name = "order_no")
	private String orderNo;
	@XmlElement(name = "error_code")
	private String errorCode;
	@XmlElement(name = "error_msg")
	private String errorMsg;
	@XmlElement(name = "contract_no")
	private String contractNo;
	@XmlElement(name = "sign")
	private String sign;
	@XmlElement(name = "sign_type")
	private String signType;
	@XmlElement(name = "trade_no")
	private String tradeNo;
	@XmlElement(name = "trade_status")
	private String tradeStatus;
	public String getMerchantCode() {
		return merchantCode;
	}
	public String getOrderAmount() {
		return orderAmount;
	}
	public String getOrderNo() {
		return orderNo;
	}
	public String getErrorCode() {
		return errorCode;
	}
	public String getErrorMsg() {
		return errorMsg;
	}
	public String getContractNo() {
		return contractNo;
	}
	public String getSign() {
		return sign;
	}
	public String getSignType() {
		return signType;
	}
	public String getTradeNo() {
		return tradeNo;
	}
	public String getTradeStatus() {
		return tradeStatus;
	}

	
}
