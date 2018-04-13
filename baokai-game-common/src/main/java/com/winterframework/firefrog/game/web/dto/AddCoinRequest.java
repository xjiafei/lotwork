package com.winterframework.firefrog.game.web.dto;

import java.io.Serializable;

/**
 * 
* @ClassName: AbatementGameMoneyRequest 
* @Description: 5.6.2	扣减游戏币(REI002)
* @author Richard
* @date 2013-12-12 下午2:32:17 
*
 */
public class AddCoinRequest implements Serializable {

	
	private static final long serialVersionUID = -6475678321348080909L;

	private Long userid;
	private String orderCodeList;
	private String planCodeList;
	private Integer	type;
	private Long lotteryid;
	private Long issueCode;
	private Long amount;

	
	public AddCoinRequest(){
		
	}

	public Long getUserid() {
		return userid;
	}

	public void setUserid(Long userid) {
		this.userid = userid;
	}

	public String getOrderCodeList() {
		return orderCodeList;
	}

	public void setOrderCodeList(String orderCodeList) {
		this.orderCodeList = orderCodeList;
	}

	public String getPlanCodeList() {
		return planCodeList;
	}

	public void setPlanCodeList(String planCodeList) {
		this.planCodeList = planCodeList;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Long getLotteryid() {
		return lotteryid;
	}

	public void setLotteryid(Long lotteryid) {
		this.lotteryid = lotteryid;
	}

	public Long getIssueCode() {
		return issueCode;
	}

	public void setIssueCode(Long issueCode) {
		this.issueCode = issueCode;
	}

	public Long getAmount() {
		return amount;
	}

	public void setAmount(Long amount) {
		this.amount = amount;
	}

}
