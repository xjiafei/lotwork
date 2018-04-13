package com.winterframework.firefrog.game.web.dto;

import com.winterframework.firefrog.game.util.LHCUtil;

/**
 * 展示封鎖表單區域字段內容。
 * @author Pogi.Lin
 */
public class GameNumberShares {

	/**球號01~49或生肖*/
	private String number;
	/**盈虧值*/
	private Double profitLoss;
	private Long slipVal;
	private String lockTime;
	/**生肖順序*/
	private Integer zodiacIndex;
	
	/**
	 * 取得球號01~49或生肖。
	 * @return
	 */
	public String getNumber() {
		return number;
	}
	/**
	 * 設定球號01~49或生肖。
	 * @param number
	 */
	public void setNumber(String number) {
		this.number = number;
	}
	/**
	 * 盈虧值。
	 * @return
	 */
	public Double getProfitLoss() {
		return profitLoss;
	}
	/**
	 * 盈虧值。
	 * @param profitLoss
	 */
	public void setProfitLoss(Double profitLoss) {
		this.profitLoss = profitLoss;
	}
	public Long getSlipVal() {
		return slipVal;
	}
	public void setSlipVal(Long slipVal) {
		this.slipVal = slipVal;
	}
	public String getLockTime() {
		return lockTime;
	}
	public void setLockTime(String lockTime) {
		this.lockTime = lockTime;
	}
	/**
	 * 取得生肖順序。<br>
	 * 例：鼠→1、牛→2....狗→11、豬→12
	 * @return 當 number 為 null 時、查無對應的生肖時回傳 0 
	 */
	public Integer getZodiacIndex() {
		if(this.number != null) {
			int index = LHCUtil.Zodiac.getIndex(number);
			if(index == -1) {
				return 0;
			} else {
				return index;
			}
		} else {
			return 0;
		}
	}
}