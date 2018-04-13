package com.winterframework.sup.order.vo;

import java.io.Serializable;

import com.winterframework.modules.web.util.JsonMapper;

public class BaseSupVo implements Serializable {
	
	private static final long serialVersionUID = -6831364540757631355L;

	public String toJson(){
		return JsonMapper.nonEmptyMapper().toJson(this);
	}
}
