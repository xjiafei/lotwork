package com.winterframework.firefrog.phone.web.cotroller.dto;

public class Datas {
	
	private Double money;//	提现金额
	private String bankName;//	开户银行名称
//	private String province;//	开户银行所在省份
//	private String bankcity;//	开户银行所在城市
	private String truename;//	开户人姓名
	private String bankno;//个人银行帐号
	private String cardid;//	银行id
	public Double getMoney() {
		return money;
	}
	public void setMoney(Double money) {
		this.money = money;
	}
	public String getBankName() {
		return bankName;
	}
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}
	/*public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	public String getBankcity() {
		return bankcity;
	}
	public void setBankcity(String bankcity) {
		this.bankcity = bankcity;
	}*/
	public String getTruename() {
		return truename;
	}
	public void setTruename(String truename) {
		this.truename = truename;
	}
	public String getBankno() {
		return bankno;
	}
	public void setBankno(String bankno) {
		this.bankno = bankno;
	}
	public String getCardid() {
		return cardid;
	}
	public void setCardid(String cardid) {
		this.cardid = cardid;
	}


	
}
