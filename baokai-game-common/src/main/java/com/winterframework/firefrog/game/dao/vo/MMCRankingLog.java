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
public class MMCRankingLog extends BaseEntity{

	private static final long serialVersionUID = -2145043309571048090L;
	
	
	private Long id;
	private String account;
	private Long beforeAmount;
	private Long afterAmount;
	private Long rank;
	private Long giveAmount;
	private Date createDate;
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
	public Long getBeforeAmount() {
		return beforeAmount;
	}
	public void setBeforeAmount(Long beforeAmount) {
		this.beforeAmount = beforeAmount;
	}
	public Long getAfterAmount() {
		return afterAmount;
	}
	public void setAfterAmount(Long afterAmount) {
		this.afterAmount = afterAmount;
	}
	public Long getRank() {
		return rank;
	}
	public void setRank(Long rank) {
		this.rank = rank;
	}
	public Long getGiveAmount() {
		return giveAmount;
	}
	public void setGiveAmount(Long giveAmount) {
		this.giveAmount = giveAmount;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	
		
	
	
}
