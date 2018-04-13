package com.winterframework.firefrog.game.web.dto;

import java.io.Serializable;

import com.winterframework.firefrog.game.entity.GameBetType;
import com.winterframework.firefrog.game.entity.GameSlip;
import com.winterframework.firefrog.game.entity.LockPoint;
import com.winterframework.firefrog.game.entity.MoneyMode;

public class GameSlipResponseDTO implements Serializable {
	private static final long serialVersionUID = -7581681258662866701L;
	private String betDetail;
	private GameBetType gameBetType;
	private MoneyMode moneyMode;
	private Integer multiple;
	private Long totalAmount;
	private Long totalBet;
	private Long issueCode;
	private Long curr;
	private LockPoint lockPoints;

	private Integer fileMode;
	private Integer awardMode;
	public Integer getFileMode() {
		return fileMode;
	}

	public void setFileMode(Integer fileMode) {
		this.fileMode = fileMode;
	}

	public GameSlipResponseDTO() {
	}
	
	public Long getCurr() {
		return curr;
	}

	public void setCurr(Long curr) {
		this.curr = curr;
	}

	public GameSlipResponseDTO(GameSlip slipTemp) {
		this.betDetail = slipTemp.getBetDetail();
		this.gameBetType = slipTemp.getGameBetType();
		this.moneyMode = slipTemp.getMoneyMode();
		this.multiple = slipTemp.getMultiple();
		this.totalAmount = slipTemp.getTotalAmount();
		this.totalBet = slipTemp.getTotalBet();
		this.lockPoints=slipTemp.getLockPoints();
		this.issueCode=slipTemp.getIssueCode().getIssueCode();
		this.awardMode=slipTemp.getAwardMode();
		this.fileMode= slipTemp.getFileMode();
	}
	public String getBetDetail() {
		return betDetail;
	}
	
	public Long getIssueCode() {
		return issueCode;
	}
	public void setIssueCode(Long issueCode) {
		this.issueCode = issueCode;
	}
	public LockPoint getLockPoints() {
		return lockPoints;
	}
	public void setLockPoints(LockPoint lockPoints) {
		this.lockPoints = lockPoints;
	}
	public void setBetDetail(String betDetail) {
		this.betDetail = betDetail;
	}
	public GameBetType getGameBetType() {
		return gameBetType;
	}
	public void setGameBetType(GameBetType gameBetType) {
		this.gameBetType = gameBetType;
	}
	public MoneyMode getMoneyMode() {
		return moneyMode;
	}
	public void setMoneyMode(MoneyMode moneyMode) {
		this.moneyMode = moneyMode;
	}
	public Integer getMultiple() {
		return multiple;
	}
	public void setMultiple(Integer multiple) {
		this.multiple = multiple;
	}
	public Long getTotalAmount() {
		return totalAmount;
	}
	public void setTotalAmount(Long totalAmount) {
		this.totalAmount = totalAmount;
	}
	public Long getTotalBet() {
		return totalBet;
	}
	public void setTotalBet(Long totalBet) {
		this.totalBet = totalBet;
	}

	public Integer getAwardMode() {
		return awardMode;
	}

	public void setAwardMode(Integer awardMode) {
		this.awardMode = awardMode;
	}
	
}
