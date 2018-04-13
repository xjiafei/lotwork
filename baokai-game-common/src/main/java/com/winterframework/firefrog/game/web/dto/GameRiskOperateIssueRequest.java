package com.winterframework.firefrog.game.web.dto;

import java.io.Serializable;

/** 
* @ClassName GameRiskIssueListQueryRequest 
* @Description 审核系统奖期风控订单审核操作
* @author  hugh
* @date 2014年4月9日 下午3:16:29 
*  
*/
public class GameRiskOperateIssueRequest implements Serializable {

	private static final long serialVersionUID = 1429388665080304524L;

	/** 彩种 */
	private Long lotteryId;

	/** issueCode */
	private Long issueCode;

	public Long getLotteryId() {
		return lotteryId;
	}

	public void setLotteryId(Long lotteryId) {
		this.lotteryId = lotteryId;
	}

	public Long getIssueCode() {
		return issueCode;
	}

	public void setIssueCode(Long issueCode) {
		this.issueCode = issueCode;
	}


}
