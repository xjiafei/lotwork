package com.winterframework.firefrog.game.web.dto;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

/** 
* @ClassName: SellingStatusCheckRequest 
* @Description: 销售状态审核请求参数封装对象 
* @author Denny 
* @date 2013-8-21 下午5:02:13 
*  
*/
public class SellingStatusCheckRequest implements Serializable {

	private static final long serialVersionUID = 8899353011874364583L;

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
