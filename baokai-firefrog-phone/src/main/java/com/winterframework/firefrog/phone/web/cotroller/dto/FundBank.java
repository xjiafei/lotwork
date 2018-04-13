package com.winterframework.firefrog.phone.web.cotroller.dto;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class FundBank implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6659922091232220267L;
	private Long id;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	private String logo;
	private String name;
	private String code;
	private String url;
	private Long inUse;
	private Long deposit;
	private Long withdraw;
	private Long bind;
	private Long mownecumId;
	@JsonProperty("upLimit") 
	private Long uplimit;
	@JsonProperty("lowLimit") 
	private Long lowlimit;
	@JsonProperty("vipUpLimit") 
	private Long vipuplimit;
	@JsonProperty("vipLowLimit") 
	private Long viplowlimit;
	private String rtnMin;
	private List<RtnStruc> rtnStruc;
	private Long rtnSet; 
	private Long countdown;
	private Long other;
	private Long otheruplimit;
	private Long otherdownlimit;
	private Long othervipdownlimit;
	private Long othervipuplimit;
	private Long canRechargeAppeal;
	private Long otherCanRechargeAppeal;
	private Long moveQuickType;
	private Long moveQuickDeposit;
	private Long moveDeposit;
	private Long vipOpen;
	private Long normalOpen;
	private Long userId;
	private Long moveCanRechargeAppeal;
	
	public Long getVipOpen() {
		return vipOpen;
	}
	public void setVipOpen(Long vipOpen) {
		this.vipOpen = vipOpen;
	}
	public Long getNormalOpen() {
		return normalOpen;
	}
	public void setNormalOpen(Long normalOpen) {
		this.normalOpen = normalOpen;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public Long getCanRechargeAppeal() {
		return canRechargeAppeal;
	}
	public void setCanRechargeAppeal(Long canRechargeAppeal) {
		this.canRechargeAppeal = canRechargeAppeal;
	}
	public Long getOtherCanRechargeAppeal() {
		return otherCanRechargeAppeal;
	}
	public void setOtherCanRechargeAppeal(Long otherCanRechargeAppeal) {
		this.otherCanRechargeAppeal = otherCanRechargeAppeal;
	}
	public Long getMoveQuickType() {
		return moveQuickType;
	}
	public void setMoveQuickType(Long moveQuickType) {
		this.moveQuickType = moveQuickType;
	}
	public Long getMoveQuickDeposit() {
		return moveQuickDeposit;
	}
	public void setMoveQuickDeposit(Long moveQuickDeposit) {
		this.moveQuickDeposit = moveQuickDeposit;
	}
	public Long getMoveDeposit() {
		return moveDeposit;
	}
	public void setMoveDeposit(Long moveDeposit) {
		this.moveDeposit = moveDeposit;
	}
	public String getLogo() {
		return logo;
	}
	public void setLogo(String logo) {
		this.logo = logo;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public Long getInUse() {
		return inUse;
	}
	public void setInUse(Long inUse) {
		this.inUse = inUse;
	}
	public Long getDeposit() {
		return deposit;
	}
	public void setDeposit(Long deposit) {
		this.deposit = deposit;
	}
	public Long getWithdraw() {
		return withdraw;
	}
	public void setWithdraw(Long withdraw) {
		this.withdraw = withdraw;
	}
	public Long getBind() {
		return bind;
	}
	public void setBind(Long bind) {
		this.bind = bind;
	}
	public Long getMownecumId() {
		return mownecumId;
	}
	public void setMownecumId(Long mownecumId) {
		this.mownecumId = mownecumId;
	}
	public Long getUplimit() {
		return uplimit;
	}
	public void setUplimit(Long uplimit) {
		this.uplimit = uplimit;
	}
	public Long getLowlimit() {
		return lowlimit;
	}
	public void setLowlimit(Long lowlimit) {
		this.lowlimit = lowlimit;
	}
	public Long getVipuplimit() {
		return vipuplimit;
	}
	public void setVipuplimit(Long vipuplimit) {
		this.vipuplimit = vipuplimit;
	}
	public Long getViplowlimit() {
		return viplowlimit;
	}
	public void setViplowlimit(Long viplowlimit) {
		this.viplowlimit = viplowlimit;
	}
	public String getRtnMin() {
		return rtnMin;
	}
	public void setRtnMin(String rtnMin) {
		this.rtnMin = rtnMin;
	}
	public List<RtnStruc> getRtnStruc() {
		return rtnStruc;
	}
	public void setRtnStruc(List<RtnStruc> rtnStruc) {
		this.rtnStruc = rtnStruc;
	}
	public Long getRtnSet() {
		return rtnSet;
	}
	public void setRtnSet(Long rtnSet) {
		this.rtnSet = rtnSet;
	}
	public Long getCountdown() {
		return countdown;
	}
	public void setCountdown(Long countdown) {
		this.countdown = countdown;
	}
	public Long getOther() {
		return other;
	}
	public void setOther(Long other) {
		this.other = other;
	}
	public Long getOtheruplimit() {
		return otheruplimit;
	}
	public void setOtheruplimit(Long otheruplimit) {
		this.otheruplimit = otheruplimit;
	}
	public Long getOtherdownlimit() {
		return otherdownlimit;
	}
	public void setOtherdownlimit(Long otherdownlimit) {
		this.otherdownlimit = otherdownlimit;
	}
	public Long getOthervipdownlimit() {
		return othervipdownlimit;
	}
	public void setOthervipdownlimit(Long othervipdownlimit) {
		this.othervipdownlimit = othervipdownlimit;
	}
	public Long getOthervipuplimit() {
		return othervipuplimit;
	}
	public void setOthervipuplimit(Long othervipuplimit) {
		this.othervipuplimit = othervipuplimit;
	}
	public Long getMoveCanRechargeAppeal() {
		return moveCanRechargeAppeal;
	}
	public void setMoveCanRechargeAppeal(Long moveCanRechargeAppeal) {
		this.moveCanRechargeAppeal = moveCanRechargeAppeal;
	}

}
