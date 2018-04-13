package com.winterframework.firefrog.shortlived.sheepactivity.dto;


public class ActivitySheepUserDiceAwardResponse {
	
	private boolean isWin;
	
	private boolean isHaveAward;
	
	private Long continuousWinNum;
	
	private Long lastGuessNum;//剩余次数
	
	private Integer[] resultNum;
	
	private boolean isLittleRsult;// true  xiao  false da
	
	private Long award;

	public boolean isWin() {
		return isWin;
	}

	public void setWin(boolean isWin) {
		this.isWin = isWin;
	}

	public long getContinuousWinNum() {
		return continuousWinNum;
	}

	public void setContinuousWinNum(long continuousWinNum) {
		this.continuousWinNum = continuousWinNum;
	}

	public long getLastGuessNum() {
		return lastGuessNum;
	}

	public Integer[] getResultNum() {
		return resultNum;
	}

	public void setResultNum(Integer[] resultNum) {
		this.resultNum = resultNum;
	}

	public boolean isLittleRsult() {
		return isLittleRsult;
	}

	public void setLittleRsult(boolean isLittleRsult) {
		this.isLittleRsult = isLittleRsult;
	}

	public Long getAward() {
		return award;
	}

	public void setAward(Long award) {
		this.award = award;
	}

	public boolean isHaveAward() {
		return isHaveAward;
	}

	public void setHaveAward(boolean isHaveAward) {
		this.isHaveAward = isHaveAward;
	}

	public void setLastGuessNum(Long lastGuessNum) {
		this.lastGuessNum = lastGuessNum;
	}
	
}
