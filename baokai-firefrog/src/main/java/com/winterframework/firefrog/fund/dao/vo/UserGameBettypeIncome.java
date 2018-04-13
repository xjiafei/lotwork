package com.winterframework.firefrog.fund.dao.vo;


/**
 * @author cms group
 * @version 1.0
 * @since 1.0
 */

public class UserGameBettypeIncome {


	private Long lotteryid;
	private Integer gameGroupCode;
	private Integer gameSetCode;
	private Integer betMethodCode;
	private String betTypeCode;
	private String groupCodeName;
	private String setCodeName;
	private String methodCodeName;
	private Long bet;
	private Long ret;
	private Long trueBet;
	private Long win;
	private Long result;

	public Long getLotteryid() {
		return lotteryid;
	}
	public void setLotteryid(Long lotteryid) {
		this.lotteryid = lotteryid;
	}
	public Integer getGameGroupCode() {
		return gameGroupCode;
	}
	public void setGameGroupCode(Integer gameGroupCode) {
		this.gameGroupCode = gameGroupCode;
	}
	public Integer getGameSetCode() {
		return gameSetCode;
	}
	public void setGameSetCode(Integer gameSetCode) {
		this.gameSetCode = gameSetCode;
	}
	public Integer getBetMethodCode() {
		return betMethodCode;
	}
	public void setBetMethodCode(Integer betMethodCode) {
		this.betMethodCode = betMethodCode;
	}
	public String getBetTypeCode() {
		return betTypeCode;
	}
	public void setBetTypeCode(String betTypeCode) {
		this.betTypeCode = betTypeCode;
	}
	public String getGroupCodeName() {
		return groupCodeName;
	}
	public void setGroupCodeName(String groupCodeName) {
		this.groupCodeName = groupCodeName;
	}
	public String getSetCodeName() {
		return setCodeName;
	}
	public void setSetCodeName(String setCodeName) {
		this.setCodeName = setCodeName;
	}
	public String getMethodCodeName() {
		return methodCodeName;
	}
	public void setMethodCodeName(String methodCodeName) {
		this.methodCodeName = methodCodeName;
	}
	public Long getBet() {
		return bet;
	}
	public void setBet(Long bet) {
		this.bet = bet;
	}
	public Long getRet() {
		return ret;
	}
	public void setRet(Long ret) {
		this.ret = ret;
	}
	public Long getTrueBet() {
		return trueBet;
	}
	public void setTrueBet(Long trueBet) {
		this.trueBet = trueBet;
	}
	public Long getWin() {
		return win;
	}
	public void setWin(Long win) {
		this.win = win;
	}
	public Long getResult() {
		return result;
	}
	public void setResult(Long result) {
		this.result = result;
	}


	
}
