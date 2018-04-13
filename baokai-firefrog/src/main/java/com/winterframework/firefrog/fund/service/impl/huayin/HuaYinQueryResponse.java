package com.winterframework.firefrog.fund.service.impl.huayin;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "dinpay")
public class HuaYinQueryResponse implements Serializable{
	
	@XmlElement(name = "response")
	private HuaYinQueryResponseDetail huayinQueryResponseDetail;

	public HuaYinQueryResponseDetail getHuaYinQueryResponseDetail() {
		return huayinQueryResponseDetail;
	}

	

}
