package com.winterframework.firefrog.fund.service.impl.ddb;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "response")
public class DdbPayResponseDetail implements Serializable{
	@XmlElement(name = "interface_version")
	private String interfaceVersion;
	@XmlElement(name = "merchant_code")
	private String merchantCode;
	@XmlElement(name = "order_amount")
	private String orderAmount;
	@XmlElement(name = "order_no")
	private String orderNo;
	@XmlElement(name = "order_time")
	private String orderTime;
	@XmlElement(name = "qrcode")
	private String qrcode;
	@XmlElement(name = "resp_code")
	private String respCode;
	@XmlElement(name = "resp_desc")
	private String respDesc;
	@XmlElement(name = "result_code")
	private String resultCode;
	@XmlElement(name = "result_desc")
	private String resultDesc;
	@XmlElement(name = "sign")
	private String sign;
	@XmlElement(name = "sign_type")
	private String signType;
	@XmlElement(name = "trade_no")
	private String tradeNo;
	@XmlElement(name = "trade_time")
	private String tradeTime;
	@XmlElement(name = "error_code")
	private String errorCode;
	public String getInterfaceVersion() {
		return interfaceVersion;
	}
	public String getMerchantCode() {
		return merchantCode;
	}
	public String getOrderAmount() {
		return orderAmount;
	}
	public String getOrderNo() {
		return orderNo;
	}
	public String getOrderTime() {
		return orderTime;
	}
	public String getQrcode() {
		return qrcode;
	}
	public String getRespCode() {
		return respCode;
	}
	public String getRespDesc() {
		return respDesc;
	}
	public String getResultCode() {
		return resultCode;
	}
	public String getResultDesc() {
		return resultDesc;
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
	public String getTradeTime() {
		return tradeTime;
	}
	public String getErrorCode() {
		return errorCode;
	}
	
		
	
}
