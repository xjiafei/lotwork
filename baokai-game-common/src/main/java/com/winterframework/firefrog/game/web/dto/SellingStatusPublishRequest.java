package com.winterframework.firefrog.game.web.dto;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

/** 
* @ClassName: SellingStatusPublishRequest 
* @Description: 销售状态发布请求参数封装对象 
* @author Denny 
* @date 2013-8-21 下午5:03:09 
*  
*/
public class SellingStatusPublishRequest implements Serializable {

	private static final long serialVersionUID = 239603203534339550L;

	/** 彩种 */
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
