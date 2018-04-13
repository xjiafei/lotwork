package com.winterframework.firefrog.game.entity;

import java.io.Serializable;
import java.util.Date;

public class ItemAssist implements Serializable {

	private static final long serialVersionUID = -8944127521157039088L;
	/**創建時間*/
	protected Date createTime;
	/**實際獎金(賠率)*/
	protected Long evaluatAward;
	/**保底獎金組最小獎金(雙色球使用)*/
	protected Long evaluatAwardDown;
	protected Long id;
	/**投注方式編碼(記錄到輔助玩法)*/
	protected String betTypeCode;
	protected Date updateTime;
	protected Long winNumber = 0L;
	/**六合彩獎金識別*/
	protected String lhcCode;
	
	public Long getWinNumber() {
		return winNumber;
	}

	public void setWinNumber(Long winNumber) {
		this.winNumber = winNumber;
	}

	public ItemAssist() {

	}
	/**
	 * 取得創建時間。
	 * @return
	 */
	public Date getCreateTime() {
		return createTime;
	}
	/**
	 * 設定創建時間。
	 * @param createTime
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	/**
	 * 取得實際獎金(賠率)。
	 * @return
	 */
	public Long getEvaluatAward() {
		return evaluatAward;
	}
	/**
	 * 設定實際獎金(賠率)。
	 * @param evaluatAward
	 */
	public void setEvaluatAward(Long evaluatAward) {
		this.evaluatAward = evaluatAward;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	/**
	 * 取得投注方式編碼(記錄到輔助玩法)。
	 * @return
	 */
	public String getBetTypeCode() {
		return betTypeCode;
	}
	/**
	 * 設定投注方式編碼(記錄到輔助玩法)。
	 * @param betTypeCode
	 */
	public void setBetTypeCode(String betTypeCode) {
		this.betTypeCode = betTypeCode;
	}
	/**
	 * 取得保底獎金組最小獎金(雙色球使用)。
	 * @return
	 */
	public Long getEvaluatAwardDown() {
		return evaluatAwardDown;
	}
	/**
	 * 設定保底獎金組最小獎金(雙色球使用)。
	 * @param evaluatAwardDown
	 */
	public void setEvaluatAwardDown(Long evaluatAwardDown) {
		this.evaluatAwardDown = evaluatAwardDown;
	}
	/**
	 * 取得六合彩獎金識別。
	 * @return 
	 */
	public String getLhcCode() {
		return lhcCode;
	}
	/**
	 * 設定六合彩獎金識別。
	 * @param lhcCode 
	 */
	public void setLhcCode(String lhcCode) {
		this.lhcCode = lhcCode;
	}
	/**
	 * 用 lhcCode 判斷此玩法是否為主肖。
	 * @return true:是、false:否
	 */
	public boolean isLhcOnYear() {
		if(lhcCode == null || "".equals(lhcCode)) return false;
		
		if(lhcCode.startsWith("ON")) {
			return true;
		} else {
			return false;
		}
	}
	
}
