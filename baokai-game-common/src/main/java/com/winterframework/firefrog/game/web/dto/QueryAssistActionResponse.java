package com.winterframework.firefrog.game.web.dto;

import java.io.Serializable;
import java.util.List;

/**
 * 
* @ClassName: QueryAssitActionResponse 
* @Description: 投注辅助图表查询 响应
* @author Richard
* @date 2013-8-22 下午4:48:50 
*
 */
public class QueryAssistActionResponse implements Serializable{

	private static final long serialVersionUID = 2231828701284667862L;
	
	private List<BaseChartStruc> list;
	
	private List<Object> data;
	
	public QueryAssistActionResponse(){
		
	}

	public List<BaseChartStruc> getList() {
		return list;
	}

	public void setList(List<BaseChartStruc> list) {
		this.list = list;
	}

	public List<Object> getData() {
		return data;
	}

	public void setData(List<Object> data) {
		this.data = data;
	}
}
