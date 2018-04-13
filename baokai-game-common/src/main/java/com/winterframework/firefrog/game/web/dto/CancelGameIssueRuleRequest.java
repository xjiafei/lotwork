package com.winterframework.firefrog.game.web.dto;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

/** 
* @ClassName: CancelGameIssueRuleRequest 
* @Description: 停止特列奖期规则请求
* @author david 
* @date 2013-8-23 下午3:27:45 
*  
*/
public class CancelGameIssueRuleRequest implements Serializable {

	private static final long serialVersionUID = -4975373561714269943L;

	@NotNull
	private Long lotteryId;
	@NotNull
	private Long ruleId;

	public Long getLotteryId() {
		return lotteryId;
	}

	public void setLotteryId(Long lotteryId) {
		this.lotteryId = lotteryId;
	}

	public Long getRuleId() {
		return ruleId;
	}

	public void setRuleId(Long ruleId) {
		this.ruleId = ruleId;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}
