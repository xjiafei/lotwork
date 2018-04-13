package com.winterframework.firefrog.game.web.dto;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

/** 
* @ClassName: GameSeriesConfigCheckDTO 
* @Description: 游戏运营参数发布 request
* @author Alan
* @date 2013-9-17 下午5:49:48 
*  
*/
public class GameSeriesConfigPublishRequest implements Serializable {

	private static final long serialVersionUID = 9218775259686995532L;
	
	/** 彩种ID **/
	@NotNull
	private Long lotteryid;
	/** 发布结果(1通过 2不通过) **/
	@NotNull
	private Long publishType;

	public Long getLotteryid() {
		return lotteryid;
	}

	public void setLotteryid(Long lotteryid) {
		this.lotteryid = lotteryid;
	}

	public Long getPublishType() {
		return publishType;
	}

	public void setPublishType(Long publishType) {
		this.publishType = publishType;
	}

}
