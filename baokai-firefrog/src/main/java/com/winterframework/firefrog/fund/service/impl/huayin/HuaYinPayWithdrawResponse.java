package com.winterframework.firefrog.fund.service.impl.huayin;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "ddbill")
public class HuaYinPayWithdrawResponse implements Serializable{
	
	@XmlElement(name = "resppay")
	private HuaYinPayWithdrawResponseDetail huayinPayWithdrawResponseDetail;

	public HuaYinPayWithdrawResponseDetail getHuaYinPayWithdrawResponseDetail() {
		return huayinPayWithdrawResponseDetail;
	}

	public void setHuaYinPayWithdrawResponseDetail(HuaYinPayWithdrawResponseDetail huayinPayWithdrawResponseDetail) {
		this.huayinPayWithdrawResponseDetail = huayinPayWithdrawResponseDetail;
	}

	
}
