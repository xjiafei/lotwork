package com.winterframework.firefrog.phone.web.cotroller.dto;

import java.io.Serializable;

public class Projects implements Serializable{
	
	
	private static final long serialVersionUID = -8076716657615107331L;
	private String codes;//	注单号码
	private String methodid	;//玩法id
	private Integer nums;//	注单注数
	private Integer times;//	注单倍数
	private Integer mode;//	元角模式
	private Float money;//	注单金额
	private String name;//	玩法名称
	private Integer fileMode;
	private Double odds; //六合彩賠率 
	private Integer awardMode; //奖金模式
	
	public String getCodes() {
		return codes;
	}
	public void setCodes(String codes) {
		this.codes = codes;
	}
	public String getMethodid() {
		return methodid;
	}
	public void setMethodid(String methodid) {
		this.methodid = methodid;
	}
	public Integer getNums() {
		return nums;
	}
	public void setNums(Integer nums) {
		this.nums = nums;
	}
	public Integer getTimes() {
		return times;
	}
	public void setTimes(Integer times) {
		this.times = times;
	}
	public Integer getMode() {
		return mode;
	}
	public void setMode(Integer mode) {
		this.mode = mode;
	}
	public Float getMoney() {
		return money;
	}
	public void setMoney(Float money) {
		this.money = money;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getFileMode() {
		return fileMode;
	}
	public void setFileMode(Integer fileMode) {
		this.fileMode = fileMode;
	}
	public Double getOdds() {
		return odds;
	}
	public void setOdds(Double odds) {
		this.odds = odds;
	}
	public Integer getAwardMode() {
		return awardMode;
	}
	public void setAwardMode(Integer awardMode) {
		this.awardMode = awardMode;
	}
}

