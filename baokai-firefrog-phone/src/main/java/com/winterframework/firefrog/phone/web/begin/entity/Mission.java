package com.winterframework.firefrog.phone.web.begin.entity;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.winterframework.firefrog.phone.web.begin.vo.BeginNewCharge;
import com.winterframework.firefrog.phone.web.begin.vo.BeginNewDaybet;
import com.winterframework.firefrog.phone.web.begin.vo.BeginNewDayques;
import com.winterframework.firefrog.phone.web.begin.vo.BeginNewMission;
import com.winterframework.firefrog.phone.web.begin.vo.BeginNewTolbet;

public class Mission implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1098871676459093797L;

	private String tipsBank;
	
	private String tipsCard;
	
	private String tipsWithdraw;
	
	private Long isFinish1;
	
	private Long isFinish2;
	
	private Long isFinish3;
	
	private String bindcardLink;
	
	private String rechargeLink;
	
	private String withdrawLink;

	public String getTipsBank() {
		return tipsBank;
	}

	public void setTipsBank(String tipsBank) {
		this.tipsBank = tipsBank;
	}

	public String getTipsCard() {
		return tipsCard;
	}

	public void setTipsCard(String tipsCard) {
		this.tipsCard = tipsCard;
	}

	public String getTipsWithdraw() {
		return tipsWithdraw;
	}

	public void setTipsWithdraw(String tipsWithdraw) {
		this.tipsWithdraw = tipsWithdraw;
	}

	public Long getIsFinish1() {
		return isFinish1;
	}

	public void setIsFinish1(Long isFinish1) {
		this.isFinish1 = isFinish1;
	}

	public Long getIsFinish2() {
		return isFinish2;
	}

	public void setIsFinish2(Long isFinish2) {
		this.isFinish2 = isFinish2;
	}

	public Long getIsFinish3() {
		return isFinish3;
	}

	public void setIsFinish3(Long isFinish3) {
		this.isFinish3 = isFinish3;
	}

	public String getBindcardLink() {
		return bindcardLink;
	}

	public void setBindcardLink(String bindcardLink) {
		this.bindcardLink = bindcardLink;
	}

	public String getRechargeLink() {
		return rechargeLink;
	}

	public void setRechargeLink(String rechargeLink) {
		this.rechargeLink = rechargeLink;
	}

	public String getWithdrawLink() {
		return withdrawLink;
	}

	public void setWithdrawLink(String withdrawLink) {
		this.withdrawLink = withdrawLink;
	}
}
