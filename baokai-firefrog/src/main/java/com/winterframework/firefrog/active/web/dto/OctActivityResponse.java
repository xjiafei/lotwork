package com.winterframework.firefrog.active.web.dto;

import java.io.Serializable;

public class OctActivityResponse implements Serializable {

	private Boolean isSuccess = true;
	private Boolean isGetPrize = true;
	private String leftAmount;
	private String betAmount;
	private String betScale;
	private String betMutile;
	private String prize;
	
	
	
	public Boolean getIsSuccess() {
		return isSuccess;
	}
	public void setIsSuccess(Boolean isSuccess) {
		this.isSuccess = isSuccess;
	}
	public Boolean getIsGetPrize() {
		return isGetPrize;
	}
	public void setIsGetPrize(Boolean isGetPrize) {
		this.isGetPrize = isGetPrize;
	}
	public String getLeftAmount() {
		return leftAmount;
	}
	public void setLeftAmount(String leftAmount) {
		this.leftAmount = leftAmount;
	}
	public String getBetAmount() {
		return betAmount;
	}
	public void setBetAmount(String betAmount) {
		this.betAmount = betAmount;
	}
	public String getBetScale() {
		return betScale;
	}
	public void setBetScale(String betScale) {
		this.betScale = betScale;
	}
	public String getBetMutile() {
		return betMutile;
	}
	public void setBetMutile(String betMutile) {
		this.betMutile = betMutile;
	}
	public String getPrize() {
		return prize;
	}
	public void setPrize(String prize) {
		this.prize = prize;
	}
	
	public String creatParam(){
		return getLeftAmount()+";"+getBetAmount()+";"+getBetScale()+";"+getBetMutile()+";"+getPrize();
	}
}
