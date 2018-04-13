package com.winterframework.firefrog.fund.service.impl.ecpss;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "response")
public class ECPSSQueryResponseDetail implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3074576918211586951L;

	@XmlElement(name = "orderAmount")
	private String orderAmount;
	
	@XmlElement(name = "orderDate")
	private String orderDate;
	
	@XmlElement(name = "orderNumber")
	private String orderNumber;
	
	@XmlElement(name = "orderStatus")
	private String orderStatus;
	
	@XmlElement(name = "gouduiStatus")
	private String gouduiStatus;
	
	@XmlElement(name = "refundStatus")
	private String refundStatus;

	public String getOrderAmount() {
		return orderAmount;
	}

	public String getOrderDate() {
		return orderDate;
	}

	public String getOrderNumber() {
		return orderNumber;
	}

	public String getOrderStatus() {
		return orderStatus;
	}

	public String getGouduiStatus() {
		return gouduiStatus;
	}

	public String getRefundStatus() {
		return refundStatus;
	}
		
}
