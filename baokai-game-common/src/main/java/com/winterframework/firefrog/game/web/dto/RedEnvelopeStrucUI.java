package com.winterframework.firefrog.game.web.dto;

import java.io.Serializable;

public class RedEnvelopeStrucUI implements Serializable {

	private static final long serialVersionUID = -1477183186264315235L;
	
	private String betDate;
	
	private String account;
	
	private Double betAmount;
	
	private Long award;
	
	private String status;
	
	private String channel;

	
	
	
	public RedEnvelopeStrucUI(RedEnvelopeStruc struc) {
		super();
		this.betDate = struc.getBetDate();
		this.account = struc.getAccount();
		this.betAmount = (struc.getBetAmount()!= null ?(double)struc.getBetAmount()/10000:0L);
		this.award = struc.getAward() != null ?struc.getAward():0L;
		this.status = struc.getStatus() != null && struc.getStatus().longValue() == 0?"未领":"已领";
		if(struc.getChannel()!= null && struc.getChannel().longValue() == 0){
			this.channel = "pc";
		}else if(struc.getChannel()!= null && struc.getChannel().longValue() == 1){
			this.channel = "手机";
		}else{
			this.channel = "";
		}
		
	}

	public String getBetDate() {
		return betDate;
	}

	public void setBetDate(String betDate) {
		this.betDate = betDate;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public Double getBetAmount() {
		return betAmount;
	}

	public void setBetAmount(Double betAmount) {
		this.betAmount = betAmount;
	}

	public Long getAward() {
		return award;
	}

	public void setAward(Long award) {
		this.award = award;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}
}
