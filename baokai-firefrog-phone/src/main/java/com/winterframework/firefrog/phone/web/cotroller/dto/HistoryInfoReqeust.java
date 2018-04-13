package com.winterframework.firefrog.phone.web.cotroller.dto;

import java.io.Serializable;

public class HistoryInfoReqeust implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5261741688151423110L;
	private Long chanId;//	频道id
	private Long lotteryId;//	彩种id
	private Integer num;//	设定返回几笔资料
	public Long getChanId() {
		return chanId;
	}
	public void setChanId(Long chanId) {
		this.chanId = chanId;
	}
	public Long getLotteryId() {
		return lotteryId;
	}
	public void setLotteryId(Long lotteryId) {
		this.lotteryId = lotteryId;
	}
	public Integer getNum() {
		return num;
	}
	public void setNum(Integer num) {
		this.num = num;
	}

	

}
