package com.winterframework.firefrog.fund.dao.vo;

public class FundUserRemarkExtVO extends FundUserRemarkVO {

	/** 
	*/
	private static final long serialVersionUID = 2692838209994355831L;
	/** 
	* 账号
	*/
	private String account;
	/** 
	*用户类型 0:非vip用户，大于0：vip
	*/
	private Integer vipLvl;
	/** 
	*用户状态 是否冻结 1：冻结 0：未冻结
	*/
	private Integer isFreeze;
	/** 
	* 所属总代  /abc/dd/aa/ abc为总代用户
	*/
	private String userChain;

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public Integer getVipLvl() {
		return vipLvl;
	}

	public void setVipLvl(Integer vipLvl) {
		this.vipLvl = vipLvl;
	}

	public Integer getIsFreeze() {
		return isFreeze;
	}

	public void setIsFreeze(Integer isFreeze) {
		this.isFreeze = isFreeze;
	}

	public String getUserChain() {
		return userChain;
	}

	public void setUserChain(String userChain) {
		this.userChain = userChain;
	}
}
