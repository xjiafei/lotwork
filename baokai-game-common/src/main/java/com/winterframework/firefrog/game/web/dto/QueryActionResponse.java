package com.winterframework.firefrog.game.web.dto;

import java.io.Serializable;
import java.util.List;

/**
 * 
* @ClassName: QueryActionResponse 
* @Description: 投注方式查询响应 
* @author Richard
* @date 2013-8-21 下午2:29:22 
*
 */
public class QueryActionResponse implements Serializable {

	private static final long serialVersionUID = 3427944193481654906L;

	private List<ActionResponse> list;

	public List<ActionResponse> getList() {
		return list;
	}

	public void setList(List<ActionResponse> list) {
		this.list = list;
	}
	
	
}
