package com.winterframework.firefrog.fund.service.impl.huayin;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "dinpay")
public class HuaYinPayResponse implements Serializable{
	
	@XmlElement(name = "response")
	private HuaYinPayResponseDetail huayinPayResponseDetail;

	public HuaYinPayResponseDetail getHuaYinPayResponseDetail() {
		return huayinPayResponseDetail;
	}

}
