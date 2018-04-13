/**   
* @Title: BankCard.java 
* @Package com.winterframework.firefrog.fund.entity 
* @Description: 银行卡实体类 
* @author Denny  
* @date 2013-6-28 下午3:20:58 
* @version V1.0   
*/
package com.winterframework.firefrog.fund.entity;

import java.io.Serializable;

import com.winterframework.firefrog.fund.dao.vo.FundBank;
import com.winterframework.firefrog.user.entity.User;
import com.winterframework.orm.dal.ibatis3.BaseEntity;

/** 
* @ClassName: BankCard 
* @Description: 银行卡实体 
* @author Denny 
* @date 2013-6-28 下午3:20:58 
*  
*/
public class BankCard extends BaseEntity implements Serializable {

	private static final long serialVersionUID = 7458456669498102623L;

	private Long id;
	/**银行卡号*/
	private String bankCardNo;
	/**银行*/
	private FundBank bank;
	/**开户人*/
	private String accountHolder;
	/**绑定用户*/
	private User bindingUser;
	/**省份*/
	private String province;
	/**城市*/
	private String city;
	/**支行名称*/
	private String subBranch;
	/**支行地址*/
	private String subBranchAddr;
	/**mownecumId*/
	private Long mownecumId;
	private String rcvEmail;
	
	private String account;
	private String topAcc;
	private Long isFreeze;
	private Long freezeMethod;
	private Long vipLvl;
	private Boolean isBlackList;
	
	private Long bindcardType;
	private String nickName;
	
	
	public String getRcvEmail() {
		return rcvEmail;
	}

	public void setRcvEmail(String rcvEmail) {
		this.rcvEmail = rcvEmail;
	}

	public Long getVipLvl() {
		return vipLvl;
	}

	public void setVipLvl(Long vipLvl) {
		this.vipLvl = vipLvl;
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

	public Long getIsFreeze() {
		return isFreeze;
	}

	public void setIsFreeze(Long isFreeze) {
		this.isFreeze = isFreeze;
	}

	public Long getFreezeMethod() {
		return freezeMethod;
	}

	public void setFreezeMethod(Long freezeMethod) {
		this.freezeMethod = freezeMethod;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	

	public String getBankCardNo() {
		return bankCardNo;
	}

	public void setBankCardNo(String bankCardNo) {
		this.bankCardNo = bankCardNo;
	}

	public FundBank getBank() {
		return bank;
	}

	public void setBank(FundBank bank) {
		this.bank = bank;
	}

	public String getAccountHolder() {
		return accountHolder;
	}

	public void setAccountHolder(String accountHolder) {
		this.accountHolder = accountHolder;
	}

	public User getBindingUser() {
		return bindingUser;
	}

	public void setBindingUser(User bindingUser) {
		this.bindingUser = bindingUser;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getSubBranch() {
		return subBranch;
	}

	public void setSubBranch(String subBranch) {
		this.subBranch = subBranch;
	}

	public Long getMownecumId() {
		return mownecumId;
	}

	public void setMownecumId(Long mownecumId) {
		this.mownecumId = mownecumId;
	}

	public String getSubBranchAddr() {
		return subBranchAddr;
	}

	public void setSubBranchAddr(String subBranchAddr) {
		this.subBranchAddr = subBranchAddr;
	}

	public Boolean getIsBlackList() {
		return isBlackList;
	}

	public void setIsBlackList(Boolean isBlackList) {
		this.isBlackList = isBlackList;
	}

	public Long getBindcardType() {
		return bindcardType;
	}

	public void setBindcardType(Long bindcardType) {
		this.bindcardType = bindcardType;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}


}
