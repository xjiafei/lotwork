package com.winterframework.firefrog.game.web.dto;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

/** 
* @ClassName: SellingStatusQueryRequest 
* @Description: 销售状态查询请求参数封装对象 
* @author Denny 
* @date 2013-8-21 下午4:54:03 
*  
*/
public class SellingStatusQueryRequest implements Serializable {

	private static final long serialVersionUID = -4709516554522930287L;

	/** 彩种 */
	@NotNull
	private Long lotteryid;

	public Long getLotteryid() {
		return lotteryid;
	}

	public void setLotteryid(Long lotteryid) {
		this.lotteryid = lotteryid;
	}
	
	
}
