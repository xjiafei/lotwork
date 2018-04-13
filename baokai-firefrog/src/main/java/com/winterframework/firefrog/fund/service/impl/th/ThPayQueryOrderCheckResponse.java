package com.winterframework.firefrog.fund.service.impl.th;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "pay")
public class ThPayQueryOrderCheckResponse implements Serializable{
	
	@XmlElement(name = "response")

	private ThPayQueryResponseOrderCheckDetail thPayQueryResponseOrderCheckDetail;

	public ThPayQueryResponseOrderCheckDetail getThPayQueryResponseOrderCheckDetail(){
		return thPayQueryResponseOrderCheckDetail;
	}

}
