package com.winterframework.firefrog.game.web.dto;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

/** 
* @ClassName: BetLimitPublishRequest 
* @Description: 投注限制发布请求参数封装对象 
* @author Denny 
* @date 2013-8-21 下午4:46:24 
*  
*/
public class BetLimitPublishRequest implements Serializable {

	private static final long serialVersionUID = -8236312625491676884L;

	/** 彩种 */
	@NotNull
	private Long lotteryid;
	/** 发布类型(1通过，2不通过) **/
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
