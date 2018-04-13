package com.winterframework.firefrog.game.web.dto;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

/** 
* @ClassName: CancelGameIssueRuleRequest 
* @Description: 审核奖期规则请求
* @author david 
* @date 2013-8-23 下午3:27:45 
*  
*/
public class AuditGameIssueRuleRequest implements Serializable {

	private static final long serialVersionUID = -4975373561714269943L;

	@NotNull
	private Long lotteryId;
	@NotNull
	private Long ruleId;
	@NotNull
	private Integer checkType;
	@NotNull
	private Integer checkResult;

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

	public Integer getCheckType() {
		return checkType;
	}

	public void setCheckType(Integer checkType) {
		this.checkType = checkType;
	}

	public Integer getCheckResult() {
		return checkResult;
	}

	public void setCheckResult(Integer checkResult) {
		this.checkResult = checkResult;
	}

}
