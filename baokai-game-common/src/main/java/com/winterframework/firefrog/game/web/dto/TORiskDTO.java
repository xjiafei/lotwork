package com.winterframework.firefrog.game.web.dto;

import java.io.Serializable;

/**
 * 
* @ClassName: RiskRequest 
* @Description:  风控审核接口传输对象DTO
* @author charles
* @date 2013-12-12 下午2:31:00 
*
 */
public class TORiskDTO implements Serializable {

	


	/**
	 * 
	 */
	private static final long serialVersionUID = 8740580127289360042L;

	//支持多用户ID传递,用逗号分隔
	private String userid;
	
	//支持多用户ID金额传递,用逗号分隔
	private String amount;
	
	private String orderCodeList;
	private String planCodeList;
	private String note;
	private Integer	type;
	private Long lotteryid;
	private Long issueCode;

	
	public TORiskDTO(){
		
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

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}
	
	@Override
	public String toString() {
		String seperator=" | ";
		return new StringBuffer().append(lotteryid).append(seperator).append(issueCode)
				.append(seperator).append(type).append(seperator).append(planCodeList)
				.append(seperator).append(orderCodeList).append(seperator).append(userid)
				.append(seperator).append(amount).append(seperator).append(note).toString();
	}

}
