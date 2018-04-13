package com.winterframework.firefrog.fund.service.impl.ddb;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "ddbill")
public class DdbPayWithdrawResponse implements Serializable{
	
	@XmlElement(name = "resppay")
	private DdbPayWithdrawResponseDetail ddbPayWithdrawResponseDetail;

	public DdbPayWithdrawResponseDetail getDdbPayWithdrawResponseDetail() {
		return ddbPayWithdrawResponseDetail;
	}

	public void setDdbPayWithdrawResponseDetail(DdbPayWithdrawResponseDetail ddbPayWithdrawResponseDetail) {
		this.ddbPayWithdrawResponseDetail = ddbPayWithdrawResponseDetail;
	}

	
}
