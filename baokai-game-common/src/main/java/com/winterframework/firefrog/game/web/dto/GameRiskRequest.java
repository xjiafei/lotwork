package com.winterframework.firefrog.game.web.dto;

import java.io.Serializable;

/** 
* @ClassName GameRiskIssueListQueryRequest 
* @Description 审核系统  主流程 验证及派奖请求 撤销请求 重做请求
* @author  hugh
* @date 2014年4月21日 下午3:16:29 
*  
*/
public class GameRiskRequest implements Serializable {

	private static final long serialVersionUID = 1429388665080304524L;

	/** 彩种 */
	private Long lotteryId;

	/** 奖期 */
	private Long issueCode;

	/** 某时间之后 可选 */
	private Long saleTime;

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

	public Long getSaleTime() {
		return saleTime;
	}

	public void setSaleTime(Long saleTime) {
		this.saleTime = saleTime;
	}

	
}
