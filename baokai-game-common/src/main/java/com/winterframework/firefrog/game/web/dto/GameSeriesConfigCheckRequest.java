package com.winterframework.firefrog.game.web.dto;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

/** 
* @ClassName: GameSeriesConfigCheckDTO 
* @Description: 游戏运营参数审核和发布 
* @author Alan
* @date 2013-9-17 下午5:49:48 
*  
*/
public class GameSeriesConfigCheckRequest implements Serializable {

	private static final long serialVersionUID = 9218775259686995532L;
	
	/** 彩种ID **/
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
