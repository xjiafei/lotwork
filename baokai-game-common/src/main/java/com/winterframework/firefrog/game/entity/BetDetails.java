package com.winterframework.firefrog.game.entity;

import java.io.Serializable;

/**
 * 每注详情 
 * @author Pogi.Lin
 */
public class BetDetails implements Serializable {

	private static final long serialVersionUID = 760564657835417724L;

	private Long issueCode;
	private Integer gameGroupCode;
	private Integer gameSetCode;
	private Integer betMethodCode;
	private Integer moneyMode;
	private Integer totbets;
	private Integer itemAmount;
	private Long totamount;
	private Integer multiple;
	private String betdetail;
	private Integer fileMode;

	public enum fileMode {
		/**注單內容為文件(1)*/
		file(1),
		/**注單內容非文件(0)*/
		nuFile(0);

		public int _value = 0;

		fileMode(int value) {
			this._value = value;
		}

		public int getValue() {
			return _value;
		}
	}

	public BetDetails() {

	}

	public Long getIssueCode() {
		return issueCode;
	}

	public void setIssueCode(Long issueCode) {
		this.issueCode = issueCode;
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

	public Integer getMoneyMode() {
		return moneyMode;
	}

	public void setMoneyMode(Integer moneyMode) {
		this.moneyMode = moneyMode;
	}

	public Integer getTotbets() {
		return totbets;
	}

	public void setTotbets(Integer totbets) {
		this.totbets = totbets;
	}

	public Long getTotamount() {
		return totamount;
	}

	public void setTotamount(Long totamount) {
		this.totamount = totamount;
	}

	public Integer getItemAmount() {
		return itemAmount;
	}

	public void setItemAmount(Integer itemAmount) {
		this.itemAmount = itemAmount;
	}

	public Integer getMultiple() {
		return multiple;
	}

	public void setMultiple(Integer multiple) {
		this.multiple = multiple;
	}

	public String getBetdetail() {
		return betdetail;
	}

	public void setBetdetail(String betdetail) {
		this.betdetail = betdetail;
	}

	public Integer getFileMode() {
		return fileMode;
	}

	public void setFileMode(Integer fileMode) {
		this.fileMode = fileMode;
	}
	
	

}
