/**   
* @Title: FundSuspiciousCard.java 
* @Package com.winterframework.firefrog.fund.dao.vo 
* @Description: TODO(用一句话描述该文件做什么) 
* @author 你的名字   
* @date 2013-7-24 下午5:13:03 
* @version V1.0   
*/
package com.winterframework.firefrog.fund.dao.vo;

import java.util.Date;

import com.winterframework.orm.dal.ibatis3.BaseEntity;

/** 
* @ClassName: FundSuspiciousCard 
* @Description: 可疑银行卡VO 
* @author Alan
* @date 2013-7-24 下午5:13:03 
*  
*/
public class FundSuspiciousCardVO extends BaseEntity {

	private static final long serialVersionUID = -3672178373833513369L;

	//alias
	public static final String TABLE_ALIAS = "FundSuspiciousCard";
	public static final String ALIAS_CARD_NUMBER = "卡号";
	public static final String ALIAS_MEMO = "注释";
	public static final String ALIAS_TYPE = " 1可疑卡 2骗子卡 3重复付款 4其他";

	//date formats

	//columns START
	private String cardNumber;
	private String memo;
	private Long type;
	private String creatorAccount;
	private Date gmtCreated;
	private String account;
	private String topAcc;
	private String bankAcc;

	public String getBankAcc() {
		return bankAcc;
	}

	public void setBankAcc(String bankAcc) {
		this.bankAcc = bankAcc;
	}

	//columns END
	public String getCardNumber() {
		return cardNumber;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	

	public String getTopAcc() {
		return topAcc;
	}

	public void setTopAcc(String topAcc) {
		this.topAcc = topAcc;
	}

	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public Long getType() {
		return type;
	}

	public void setType(Long type) {
		this.type = type;
	}

	public String getCreatorAccount() {
		return creatorAccount;
	}

	public void setCreatorAccount(String creatorAccount) {
		this.creatorAccount = creatorAccount;
	}

	@Override
	public Date getGmtCreated() {
		return gmtCreated;
	}

	@Override
	public void setGmtCreated(Date gmtCreated) {
		this.gmtCreated = gmtCreated;
	}

}
