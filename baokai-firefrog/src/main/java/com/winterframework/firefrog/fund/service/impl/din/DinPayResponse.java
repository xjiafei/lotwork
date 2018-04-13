package com.winterframework.firefrog.fund.service.impl.din;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "dinpay")
public class DinPayResponse implements Serializable{
	
	@XmlElement(name = "response")
	private DinPayResponseDetail dinPayResponseDetail;

	public DinPayResponseDetail getDinPayResponseDetail() {
		return dinPayResponseDetail;
	}

}
