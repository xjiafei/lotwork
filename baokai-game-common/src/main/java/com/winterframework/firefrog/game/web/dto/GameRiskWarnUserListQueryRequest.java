package com.winterframework.firefrog.game.web.dto;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

/** 
* @ClassName GameRiskIssueListQueryRequest 
* @Description 审核系统 审核奖期查询 
* @author  hugh
* @date 2014年4月9日 下午3:16:29 
*  
*/
public class GameRiskWarnUserListQueryRequest implements Serializable {

	private static final long serialVersionUID = 1429388665080304524L;

	/** 彩种 */
	@NotNull
	private Long lotteryId;

	/** 奖期 */
	@NotNull
	private Long issueCode;
	
	/** 状态  全部0 已完成1 未处理2*/
	private Integer status = 2;

	public Long getLotteryId() {
		return lotteryId;
	}

	public void setLotteryId(Long lotteryId) {
		this.lotteryId = lotteryId;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Long getIssueCode() {
		return issueCode;
	}

	public void setIssueCode(Long issueCode) {
		this.issueCode = issueCode;
	}


}
