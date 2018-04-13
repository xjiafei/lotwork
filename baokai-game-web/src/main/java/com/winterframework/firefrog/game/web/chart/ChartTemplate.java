package com.winterframework.firefrog.game.web.chart;

/**
 * @Title 模板类
 * 
 * 每一个Chart对象都对应一个模板，多个Chart对象可共享同一模板
 * 
 * @author bob
 *
 */
public class ChartTemplate {
	
	// 模板内容
	private String body;

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}


}
