package com.winterframework.firefrog.fund.service.impl.ecpss;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "root")
public class Root implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6225875999091150306L;

	@XmlAttribute
    protected String tx;
	
	@XmlElement(name = "merCode")
	private String merCode;
	
	@XmlElement(name = "orderNumber")
	private String orderNumber;
	
	@XmlElement(name = "beginTime")
	private String beginTime;
	
	@XmlElement(name = "endTime")
	private String endTime;
	
	@XmlElement(name = "pageIndex")
	private String pageIndex;
	
	@XmlElement(name = "sign")
	private String sign;

	public void setTx(String tx) {
		this.tx = tx;
	}

	public void setMerCode(String merCode) {
		this.merCode = merCode;
	}

	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}

	public void setBeginTime(String beginTime) {
		this.beginTime = beginTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public void setPageIndex(String pageIndex) {
		this.pageIndex = pageIndex;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	
	
}
