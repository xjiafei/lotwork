package com.winterframework.firefrog.game.web.dto;

/**
 * 彩種封鎖功能來源 request 物件。
 * @author Pogi.Lin
 */
public class GameLockDataQueryRequest {
	/**獎期*/
	private Long issueCode;
	/**彩種ID*/
	private Long lotteryId;
	/**升幂排序依据方式；0:號碼、1:盈虧值、2:生肖*/
	private Long sortType;
	/**玩法；0:特碼(default)、1:正特碼_一肖、2:其他玩法*/
	private Long playType = 0L;
	
	/**
	 * 取得獎期。
	 * @return
	 */
	public Long getIssueCode() {
		return issueCode;
	}
	/**
	 * 設定獎期。
	 * @param issueCode
	 */
	public void setIssueCode(Long issueCode) {
		this.issueCode = issueCode;
	}
	/**
	 * 取得彩種ID。
	 * @return
	 */
	public Long getLotteryId() {
		return lotteryId;
	}
	/**
	 * 設定彩種ID。
	 * @param lotteryId
	 */
	public void setLotteryId(Long lotteryId) {
		this.lotteryId = lotteryId;
	}
	/**
	 * 取得升幂排序依据方式。
	 * @return 0:號碼、1:盈虧值、2:生肖
	 */
	public Long getSortType() {
		return sortType;
	}
	/**
	 * 設定升幂排序依据方式。
	 * @param sortType 0:號碼、1:盈虧值、2:生肖
	 */
	public void setSortType(Long sortType) {
		this.sortType = sortType;
	}
	/**
	 * 取得玩法。
	 * @return 0:特碼(default)、1:正特碼_一肖、2:其他玩法
	 */
	public Long getPlayType() {
		return playType;
	}
	/**
	 * 設定玩法。
	 * @param playType 0:特碼(default)、1:正特碼_一肖、2:其他玩法
	 */
	public void setPlayType(Long playType) {
		this.playType = playType;
	}
}
