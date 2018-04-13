package com.winterframework.firefrog.phone.web.cotroller.dto;

import java.io.Serializable;

public class TaskDetailProject implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4660818011004040026L;
	
	private String methodid;//	彩种id
	private String codedetails;//	注单号码
	private Integer nums;//	注单注数
	private Integer multiple;//	倍数
	private String modes;//	模式
//	private Double bounees;
	public String getMethodid() {
		return methodid;
	}
	public void setMethodid(String methodid) {
		this.methodid = methodid;
	}
	public String getCodedetails() {
		return codedetails;
	}
	public void setCodedetails(String codedetails) {
		this.codedetails = codedetails;
	}
	public Integer getNums() {
		return nums;
	}
	public void setNums(Integer nums) {
		this.nums = nums;
	}
	public Integer getMultiple() {
		return multiple;
	}
	public void setMultiple(Integer multiple) {
		this.multiple = multiple;
	}
	public String getModes() {
		return modes;
	}
	public void setModes(String modes) {
		this.modes = modes;
	}
	/*public Double getBounees() {
		return bounees;
	}			
	public void setBounees(Double bounees) {
		this.bounees = bounees;
	}*/
	

}
