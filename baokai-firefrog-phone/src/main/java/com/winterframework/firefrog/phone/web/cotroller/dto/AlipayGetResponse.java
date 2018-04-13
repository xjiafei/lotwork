package com.winterframework.firefrog.phone.web.cotroller.dto;

import java.util.List;

import com.winterframework.orm.dal.ibatis3.BaseEntity;

/** 
* @ClassName ActivityAwardConfig 
* @Description 活动奖品配置表
* @author  hugh
* @date 2014年11月24日 下午4:17:12 
*  
*/
public class AlipayGetResponse extends BaseEntity{


	/**
	 * 
	 */
	private static final long serialVersionUID = -2992973480307181301L;
	private List<UserBankStruc> userBankStruc;
	private String lastChargeCard;
	private String lastChargeAccount;
	
	public List<UserBankStruc> getUserBankStruc() {
		return userBankStruc;
	}

	public void setUserBankStruc(List<UserBankStruc> userBankStruc) {
		this.userBankStruc = userBankStruc;
	}

	public String getLastChargeCard() {
		return lastChargeCard;
	}
	
	public void setLastChargeCard(String lastChargeCard) {
		this.lastChargeCard = lastChargeCard;
	}
	
	public String getLastChargeAccount() {
		return lastChargeAccount;
	}
	
	public void setLastChargeAccount(String lastChargeAccount) {
		this.lastChargeAccount = lastChargeAccount;
	}
	
	
}
