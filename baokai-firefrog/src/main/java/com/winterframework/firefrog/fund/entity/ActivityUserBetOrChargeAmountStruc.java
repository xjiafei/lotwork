package com.winterframework.firefrog.fund.entity;

import java.io.Serializable;


/** 
* @ClassName ActivityUserBetAmountStruc 
* @Description 活动投注金额结构
* @author  david
* @date 2015年11月12日 上午11:52:37 
*  
*/
public class ActivityUserBetOrChargeAmountStruc implements Serializable {

	private static final long serialVersionUID = -9032784286388606635L;
	
	private Long userId;//用户id
	
	private Integer type;//0: 投注金额  1 充值金额
	
	//private Integer isVip;//0:false 1:true
	
	private Long amount;//金额  保存*10000之后的整型值 
	
	private String  date;//格式化为yyyy-MM-dd 的字符串 ，说明传递的是哪一天的投注金额
	
	private String demo;
	
	private String source;//数据来源  3.0,4.0

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

//	public Integer getIsVip() {
//		return isVip;
//	}
//
//	public void setIsVip(Integer isVip) {
//		this.isVip = isVip;
//	}

	public Long getAmount() {
		return amount;
	}

	public void setAmount(Long amount) {
		this.amount = amount;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getDemo() {
		return demo;
	}

	public void setDemo(String demo) {
		this.demo = demo;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}
}
