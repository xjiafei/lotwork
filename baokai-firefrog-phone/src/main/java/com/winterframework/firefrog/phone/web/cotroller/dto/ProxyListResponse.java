package com.winterframework.firefrog.phone.web.cotroller.dto;

import java.io.Serializable;
import java.util.List;

public class ProxyListResponse implements Serializable{

	private static final long serialVersionUID = -7456031203712439321L;
	
	private Integer count;//	总个数
	private List<ProxyDto> content;//	列表
	public Integer getCount() {
		return count;
	}
	public void setCount(Integer count) {
		this.count = count;
	}
	public List<ProxyDto> getContent() {
		return content;
	}
	public void setContent(List<ProxyDto> content) {
		this.content = content;
	}

}
