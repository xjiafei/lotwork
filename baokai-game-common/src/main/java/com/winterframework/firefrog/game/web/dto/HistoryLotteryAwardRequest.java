package com.winterframework.firefrog.game.web.dto;

import java.io.Serializable;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 歷史開獎資料來源物件。
 * @author Pogi.Lin
 */
public class HistoryLotteryAwardRequest implements Serializable {

	private static final long serialVersionUID = 5352330455357896341L;
	
	/**彩種代碼*/
	private Long lotteryId;
	/**歷史開獎資料筆數*/
	private Long historynum;
	
	/**
	 * 取得彩種代碼。
	 * @return 
	 */
	public Long getLotteryId() {
		return lotteryId;
	}
	/**
	 * 設定彩種代碼。
	 * @param lotteryId 
	 */
	public void setLotteryId(Long lotteryId) {
		this.lotteryId = lotteryId;
	}
	/**
	 * 取得歷史開獎資料筆數。
	 * @return 
	 */
	public Long getHistorynum() {
		return historynum;
	}
	/**
	 * 設定歷史開獎資料筆數。
	 * @param historynum 
	 */
	public void setHistorynum(Long historynum) {
		this.historynum = historynum;
	}
	/* (non-Javadoc)
	* @see java.lang.Object#toString()
	*/
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}
}
