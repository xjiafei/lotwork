package com.winterframework.firefrog.game.web.dto;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

/**
 * 獎期資料結構體(Request)
 * @author Pogi.Lin
 */
public class GetGameIssueStrucsRequest implements Serializable {

	private static final long serialVersionUID = -4975373561714269943L;

	/**彩種ID*/
	@NotNull
	private Long lotteryId;

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

}
