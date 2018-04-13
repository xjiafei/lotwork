/**   
* @Title: FundSuspiciousCard.java 
* @Package com.winterframework.firefrog.fund.entity 
* @Description: TODO(用一句话描述该文件做什么) 
* @author 你的名字   
* @date 2013-7-24 下午4:58:42 
* @version V1.0   
*/
package com.winterframework.firefrog.fund.entity;

import java.util.Date;

/** 
* @ClassName: FundSuspiciousCard 
* @Description: 可疑银行卡
* @author Alan
* @date 2013-7-24 下午4:58:42 
*  
*/
public class FundSuspiciousCard {

	public enum CardType {
		SUSPICIOUS("suspicious", 1), CHEATER("cheater", 2), REPEATPAY("repeatpay", 3), OTHERS("others", 4);
		private CardType(String key, long value) {
			this.key = key;
			this.value = value;
		}

		private String key;
		private Long value;

		public long getValue() {
			return value;
		}

		public static CardType getCardTypeByValue(long value) {
			for (CardType i : CardType.values()) {
				if (i.getValue() == value) {
					return i;
				}
			}
			return null;
		}
	}

	//ID
	private Long id;
	//银行卡号
	private String cardNumber;
	//备注
	private String memo;
	//类型
	private CardType type;
	//创建者
	private String creatorAccount;
	//创建时间
	private Date createTime;
	private String topAcc;
	private String account;
	private String bankAcc;
	

	public String getBankAcc() {
		return bankAcc;
	}

	public void setBankAcc(String bankAcc) {
		this.bankAcc = bankAcc;
	}

	public String getTopAcc() {
		return topAcc;
	}

	public void setTopAcc(String topAcc) {
		this.topAcc = topAcc;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCardNumber() {
		return cardNumber;
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

	public CardType getType() {
		return type;
	}

	public void setType(CardType type) {
		this.type = type;
	}

	public String getCreatorAccount() {
		return creatorAccount;
	}

	public void setCreatorAccount(String creatorAccount) {
		this.creatorAccount = creatorAccount;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

}
