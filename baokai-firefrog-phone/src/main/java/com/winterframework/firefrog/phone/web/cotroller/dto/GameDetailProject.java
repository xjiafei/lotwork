package com.winterframework.firefrog.phone.web.cotroller.dto;

import java.io.Serializable;

public class GameDetailProject implements Serializable {

	private static final long serialVersionUID = 4998426626565308112L;
	private String methodid;//	彩种id	varchar
	private String codedetails;//	注单号码	varchar
	private Integer nums;//	注单注数	int
	private Integer multiple;//	倍数	int
	private Float price;//	投注金额	float
	private String modes;//	模式	varchar
	private Integer ifwin;//	是否中奖	Tinyint 1
	private Double bonus;
	public Double getBonus() {
		return bonus;
	}
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
	public Float getPrice() {
		return price;
	}
	public void setPrice(Float price) {
		this.price = price;
	}
	public String getModes() {
		return modes;
	}
	public void setModes(String modes) {
		this.modes = modes;
	}
	public Integer getIfwin() {
		return ifwin;
	}
	public void setIfwin(Integer ifwin) {
		this.ifwin = ifwin;
	}
	public void setBonus(Double bonus) {
		this.bonus = bonus;
	}
	

}
