package com.winterframework.firefrog.phone.web.cotroller.dto;

import java.io.Serializable;
import java.util.Date;

public class OpenLinkListDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2211199967900289718L;
	private String id;//	link id
	private Integer type;//	开户类型
	private String urlstring;//	开户链结
	private String remark;//	备注
	private Long registers;//
	private String start;
	private String end;
	
	public String getStart() {
		return start;
	}
	public void setStart(String start) {
		this.start = start;
	}
	public String getEnd() {
		return end;
	}
	public void setEnd(String end) {
		this.end = end;
	}
	public Long getRegisters() {
		return registers;
	}
	public void setRegisters(Long registers) {
		this.registers = registers;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public String getUrlstring() {
		return urlstring;
	}
	public void setUrlstring(String urlstring) {
		this.urlstring = urlstring;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	
}
