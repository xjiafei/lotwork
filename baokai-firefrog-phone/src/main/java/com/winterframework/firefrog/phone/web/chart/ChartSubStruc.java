package com.winterframework.firefrog.phone.web.chart;

import java.util.List;

 
/**
 *ChartStruc
 * @ClassName
 * @Description
 * @author ibm
 * 2017年10月24日
 */
public class ChartSubStruc {
  
	private String issue;
	private String code;
	private List<Object> byteNumber;
	public String getIssue() {
		return issue;
	}
	public void setIssue(String issue) {
		this.issue = issue;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public List<Object> getByteNumber() {
		return byteNumber;
	}
	public void setByteNumber(List<Object> byteNumber) {
		this.byteNumber = byteNumber;
	}

	
}
