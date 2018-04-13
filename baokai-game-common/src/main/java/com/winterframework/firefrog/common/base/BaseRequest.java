package com.winterframework.firefrog.common.base;

import java.io.Serializable;

import com.winterframework.modules.web.util.JsonMapper;

public class BaseRequest implements Serializable{

	private static final long serialVersionUID = 1L;
	
	public String toJson(){
		return JsonMapper.nonEmptyMapper().toJson(this);
	}

}
