package com.winterframework.firefrog.fund.service.impl.th;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "remit")
public class ThPayQueryResponse implements Serializable{
	
	@XmlElement(name = "response")
	private ThPayQueryResponseDetail thPayQueryResponseDetail;

	public ThPayQueryResponseDetail getThPayQueryResponseDetail() {
		return thPayQueryResponseDetail;
	}

}
