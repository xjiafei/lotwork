package com.winterframework.firefrog.game.web.dto;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

/** 
* @ClassName: BetMethodDescriptionCheckRequest 
* @Description: 玩法描述审核请求参数封装对象 
* @author Denny 
* @date 2013-8-21 下午4:51:24 
*  
*/
public class BetMethodDescriptionCheckRequest implements Serializable {

	private static final long serialVersionUID = -4962630437062759032L;

	/** 彩种 */
	@NotNull
	private Long lotteryid;
	/** 审核结果(1通过 2不通过) **/
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
