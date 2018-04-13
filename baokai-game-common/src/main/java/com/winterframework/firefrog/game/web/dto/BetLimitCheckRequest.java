package com.winterframework.firefrog.game.web.dto;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

/** 
* @ClassName: BetLimitCheckRequest 
* @Description: 投注限制审核请求参数封装对象 
* @author Denny 
* @date 2013-8-21 下午4:45:16 
*  
*/
public class BetLimitCheckRequest implements Serializable {

	private static final long serialVersionUID = 3576327024642031189L;

	/** 彩种 */
	@NotNull
	private Long lotteryid;
	/** 审核类型(1通过，2不通过) **/
	@NotNull
	private Long auditType;

	public Long getLotteryid() {
		return lotteryid;
	}

	public void setLotteryid(Long lotteryid) {
		this.lotteryid = lotteryid;
	}

	public Long getAuditType() {
		return auditType;
	}

	public void setAuditType(Long auditType) {
		this.auditType = auditType;
	}
}
