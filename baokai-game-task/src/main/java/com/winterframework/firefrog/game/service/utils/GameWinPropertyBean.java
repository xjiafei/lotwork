package com.winterframework.firefrog.game.service.utils;

public class GameWinPropertyBean {
	//中奖金额。
	Long winAmout = 0L;
	//订单中投比=订单中奖金额/订单金额
	Long winsRatio = 0L;
	//订单中奖金额
	Long totalOrderBouns = 0L;
	//订单最大奖金 
	Long maxSlipWins = 0L;
	//单注最大中投比=注单中奖金额/注单金额
	Long slipWinsRatio = 0L;
	//是否中奖标识
	boolean isWinFlag = false;

	public Long getWinAmout() {
		return winAmout;
	}

	public void setWinAmout(Long winAmout) {
		this.winAmout = winAmout;
	}

	public Long getWinsRatio() {
		return winsRatio;
	}

	public void setWinsRatio(Long winsRatio) {
		this.winsRatio = winsRatio;
	}

	public Long getTotalOrderBouns() {
		return totalOrderBouns;
	}

	public void setTotalOrderBouns(Long totalOrderBouns) {
		this.totalOrderBouns = totalOrderBouns;
	}

	public Long getMaxSlipWins() {
		return maxSlipWins;
	}

	public void setMaxSlipWins(Long maxSlipWins) {
		this.maxSlipWins = maxSlipWins;
	}

	public Long getSlipWinsRatio() {
		return slipWinsRatio;
	}

	public void setSlipWinsRatio(Long slipWinsRatio) {
		this.slipWinsRatio = slipWinsRatio;
	}

	public boolean getIsWinFlag() {
		return isWinFlag;
	}

	public void setIsWinFlag(boolean isWinFlag) {
		this.isWinFlag = isWinFlag;
	}
}
