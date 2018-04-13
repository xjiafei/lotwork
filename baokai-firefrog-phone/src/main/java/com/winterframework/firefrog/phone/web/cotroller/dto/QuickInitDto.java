package com.winterframework.firefrog.phone.web.cotroller.dto;


public class QuickInitDto {

	private Long bankId;
	private String bankName;
	private Double min;
	private Double max;
	private Double vipMin;
    private Double vipMax;
    private boolean isOther;
    
	public Double getVipMin() {
		return vipMin;
	}
	public void setVipMin(Double vipMin) {
		this.vipMin = vipMin;
	}
	public Double getVipMax() {
		return vipMax;
	}
	public void setVipMax(Double vipMax) {
		this.vipMax = vipMax;
	}
	public Long getBankId() {
		return bankId;
	}
	public void setBankId(Long bankId) {
		this.bankId = bankId;
	}
	public String getBankName() {
		return bankName;
	}
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}
	public Double getMin() {
		return min;
	}
	public void setMin(Double min) {
		this.min = min;
	}
	public Double getMax() {
		return max;
	}
	public void setMax(Double max) {
		this.max = max;
	}
	public boolean isOther() {
		return isOther;
	}
	public void setOther(boolean isOther) {
		this.isOther = isOther;
	}
	
	
}
