package com.winterframework.firefrog.fund.service.impl.ddb;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "dinpay")
public class DdbPayResponse implements Serializable{
	
	@XmlElement(name = "response")
	private DdbPayResponseDetail ddbPayResponseDetail;

	public DdbPayResponseDetail getDdbPayResponseDetail() {
		return ddbPayResponseDetail;
	}

}
