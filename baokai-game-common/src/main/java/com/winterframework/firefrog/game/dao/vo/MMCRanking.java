package com.winterframework.firefrog.game.dao.vo;

import java.util.Date;

import com.winterframework.orm.dal.ibatis3.BaseEntity;

/** 
* @ClassName Activity 
* @Description 活动表 
* @author  hugh
* @date 2014年11月24日 下午4:17:12 
*  
*/
public class MMCRanking extends BaseEntity{

	private static final long serialVersionUID = -2145043309571048090L;
	
	
	private Long rank;
	private Long id;
	private String account;
	private Long amount;
	private Date creatDate;
	private Long isDayOne;
	private Date updateDate;
	private String orderCode;
	
	public Long getRank() {
		return rank;
	}
	public void setRank(Long rank) {
		this.rank = rank;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public Long getAmount() {
		return amount;
	}
	public void setAmount(Long amount) {
		this.amount = amount;
	}
	public Date getCreatDate() {
		return creatDate;
	}
	public void setCreatDate(Date creatDate) {
		this.creatDate = creatDate;
	}
	public Long getIsDayOne() {
		return isDayOne;
	}
	public void setIsDayOne(Long isDayOne) {
		this.isDayOne = isDayOne;
	}
	public Date getUpdateDate() {
		return updateDate;
	}
	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}
	public String getOrderCode() {
		return orderCode;
	}
	public void setOrderCode(String orderCode) {
		this.orderCode = orderCode;
	}
	
	
}
