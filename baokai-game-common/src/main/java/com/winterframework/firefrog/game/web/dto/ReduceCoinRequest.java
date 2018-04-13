package com.winterframework.firefrog.game.web.dto;

import java.io.Serializable;

/**
 * 
* @ClassName: AddGameMoneyRequest 
* @Description: 5.6.3	增加游戏币(REI003) 
* @author Richard
* @date 2013-12-12 下午2:31:00 
*
 */
public class ReduceCoinRequest implements Serializable {

	
	private static final long serialVersionUID = -1125223117885187849L;

	private Long userid;
	private String orderCodeList;
	private String planCodeList;
	private Integer	type;
	private Long lotteryid;
	private Long issueCode;
	private Long amount;

	
	public ReduceCoinRequest(){
		
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

	/**
	 * 3001 本金返还加款（审核系统内部接口5001-5006接口生成摘要）3002 派发奖金
	* @Title: getType 
	* @Description: 3001 本金返还加款（审核系统内部接口5001-5006接口生成摘要）3002 派发奖金
	* @return
	 */
	public Integer getType() {
		return type;
	}
	
	/**
	 * 
	* @Title: setType 
	* @Description: 3001 本金返还加款（审核系统内部接口5001-5006接口生成摘要）3002 派发奖金
	* @param type
	 */
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
