package com.winterframework.firefrog.game.web.dto;

import java.util.List;

public class MmcRankingDto {
	private Long number;
	private String name;
	private Double amount;
	private boolean isIsDiamond;
	
	public Long getNumber() {
		return number;
	}
	public void setNumber(Long number) {
		this.number = number;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Double getAmount() {
		return amount;
	}
	public void setAmount(Double amount) {
		this.amount = amount;
	}
	public boolean isIsDiamond() {
		return isIsDiamond;
	}
	public void setIsDiamond(boolean isIsDiamond) {
		this.isIsDiamond = isIsDiamond;
	}
	
	
	
}
