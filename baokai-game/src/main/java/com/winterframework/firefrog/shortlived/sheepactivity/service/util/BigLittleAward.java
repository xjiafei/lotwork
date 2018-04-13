package com.winterframework.firefrog.shortlived.sheepactivity.service.util;

public class BigLittleAward {
	private boolean isHaveAward =true;
	
	private boolean isWin;
	
	private long continuousWinNum;
	private long lastGuessNum;//剩余次数
	
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

	public Integer[] getResultNum() {
		return resultNum;
	}

	public void setResultNum(Integer[] resultNum) {
		this.resultNum = resultNum;
	}



	public Long getAward() {
		return award;
	}

	public void setAward(Long award) {
		this.award = award;
	}

	public boolean isLittleRsult() {
		return isLittleRsult;
	}

	public void setLittleRsult(boolean isLittleRsult) {
		this.isLittleRsult = isLittleRsult;
	}

	public long getLastGuessNum() {
		return lastGuessNum;
	}

	public void setLastGuessNum(long lastGuessNum) {
		this.lastGuessNum = lastGuessNum;
	}

	public boolean isHaveAward() {
		return isHaveAward;
	}

	public void setHaveAward(boolean isHaveAward) {
		this.isHaveAward = isHaveAward;
	}

	public BigLittleAward(boolean isHaveAward) {
		super();
		this.isHaveAward = isHaveAward;
	}

	public BigLittleAward() {
		super();
	}
	
	
}
