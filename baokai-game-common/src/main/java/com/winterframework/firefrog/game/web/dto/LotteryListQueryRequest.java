package com.winterframework.firefrog.game.web.dto;

import java.io.Serializable;

/** 
* @ClassName: LotteryListQueryRequest 
* @Description: 
* @author Denny 
* @date 2013-9-30 上午11:31:26 
*  
*/
public class LotteryListQueryRequest implements Serializable {

	private static final long serialVersionUID = 5538344785038542312L;

	private Long lotteryid;
	private Integer status;

	public Long getLotteryid() {
		return lotteryid;
	}

	public void setLotteryid(Long lotteryid) {
		this.lotteryid = lotteryid;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

}
