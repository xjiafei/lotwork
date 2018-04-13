package com.winterframework.firefrog.phone.web.cotroller.dto;

import java.io.Serializable;
import java.util.List;

public class CardBindingInitResponse implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 9196337746888846619L;

	private Integer allowNum;//	可绑卡张数
	private Integer availableNum;//	已绑卡张数
	private Integer leftNum;//	剩馀绑卡张数
	private Integer setsecurity;//	是否设定资金密码1:已设定, 0:未设定
	private Integer isLocked; //0是未锁定，1是锁定

	public Integer getIsLocked() {
		return isLocked;
	}
	public void setIsLocked(Integer isLocked) {
		this.isLocked = isLocked;
	}
	private List<Province> provinceList;
	private List<Bank> bankList;
	public Integer getAllowNum() {
		return allowNum;
	}
	public void setAllowNum(Integer allowNum) {
		this.allowNum = allowNum;
	}
	public Integer getAvailableNum() {
		return availableNum;
	}
	public void setAvailableNum(Integer availableNum) {
		this.availableNum = availableNum;
	}
	public Integer getLeftNum() {
		return leftNum;
	}
	public void setLeftNum(Integer leftNum) {
		this.leftNum = leftNum;
	}
	public Integer getSetsecurity() {
		return setsecurity;
	}
	public void setSetsecurity(Integer setsecurity) {
		this.setsecurity = setsecurity;
	}
	public List<Province> getProvinceList() {
		return provinceList;
	}
	public void setProvinceList(List<Province> provinceList) {
		this.provinceList = provinceList;
	}
	public List<Bank> getBankList() {
		return bankList;
	}
	public void setBankList(List<Bank> bankList) {
		this.bankList = bankList;
	}

}
