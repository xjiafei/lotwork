package com.winterframework.firefrog.game.web.dto;

import java.io.Serializable;

/**
 * 彩種用戶歷史中獎記錄。
 * @author Pogi.Lin
 */
public class GameOrderWinsDetailDTO implements Serializable {

	private static final long serialVersionUID = 9197765495413657996L;
	/**中獎奬期*/
	private String currentIssue;
	/**中獎金額*/
	private Long amountValue;
	/**中獎金額(含分位符號)*/
	private String amount;
	/**用戶帳號(只取後兩碼中間補3個*)*/
	private String userName;
	
	/**
	 * 取得中獎奬期。
	 * @return
	 */
	public String getCurrentIssue() {
		return currentIssue;
	}
	/**
	 * 設定中獎奬期。
	 * @param currentIssue
	 */
	public void setCurrentIssue(String currentIssue) {
		this.currentIssue = currentIssue;
	}
	/**
	 * 取得用戶帳號(只取後兩碼中間補3個*)。
	 * @return
	 */
	public String getUserName() {
		return userName;
	}
	/**
	 * 設定用戶帳號(只取後兩碼中間補3個*)。
	 * @param userName
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}
	/**
	 * 取得中獎金額(含分位符號)。
	 * @return
	 */
	public String getAmount() {
		return amount;
	}
	/**
	 * 設定中獎金額(含分位符號)。
	 * @param amount
	 */
	public void setAmount(String amount) {
		this.amount = amount;
	}
	/**
	 * 取得中獎金額。
	 * @return
	 */
	public Long getAmountValue() {
		return amountValue;
	}
	/**
	 * 設定中獎金額。
	 * @param amountValue
	 */
	public void setAmountValue(Long amountValue) {
		this.amountValue = amountValue;
	}
		
}
