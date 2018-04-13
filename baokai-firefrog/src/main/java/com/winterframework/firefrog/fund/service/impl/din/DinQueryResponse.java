package com.winterframework.firefrog.fund.service.impl.din;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "dinpay")
public class DinQueryResponse implements Serializable{
	
	@XmlElement(name = "response")
	private DinQueryResponseDetail dinQueryResponseDetail;

	public DinQueryResponseDetail getDinQueryResponseDetail() {
		return dinQueryResponseDetail;
	}

	

}
