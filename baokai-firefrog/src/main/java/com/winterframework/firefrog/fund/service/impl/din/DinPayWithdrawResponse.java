package com.winterframework.firefrog.fund.service.impl.din;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "ddbill")
public class DinPayWithdrawResponse implements Serializable{
	
	@XmlElement(name = "resppay")
	private DinPayWithdrawResponseDetail dinPayWithdrawResponseDetail;

	public DinPayWithdrawResponseDetail getDinPayWithdrawResponseDetail() {
		return dinPayWithdrawResponseDetail;
	}

	public void setDinPayWithdrawResponseDetail(DinPayWithdrawResponseDetail dinPayWithdrawResponseDetail) {
		this.dinPayWithdrawResponseDetail = dinPayWithdrawResponseDetail;
	}

	
}
