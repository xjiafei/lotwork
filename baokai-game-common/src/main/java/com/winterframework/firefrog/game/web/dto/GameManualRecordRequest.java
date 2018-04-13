package com.winterframework.firefrog.game.web.dto;

import java.io.Serializable;


/** 
* @ClassName GameManualRecordRequest 
* @Description 手工录号 
* @author  hugh
* @date 2014年9月15日 下午12:00:06 
*  
*/
public class GameManualRecordRequest implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long lotteryId;
	private Long userId;
	public Long getLotteryId() {
		return lotteryId;
	}

	public void setLotteryId(Long lotteryId) {
		this.lotteryId = lotteryId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}


}
