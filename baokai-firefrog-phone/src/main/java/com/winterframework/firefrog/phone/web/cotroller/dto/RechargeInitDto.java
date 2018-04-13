package com.winterframework.firefrog.phone.web.cotroller.dto;

public class RechargeInitDto {

	private String bankName;//银行名称
//	private Long bid;//	银行id
	private String bank;//	银行编号
//	private String bankradio;//	银行选项值
//	private String accountName;//	绑卡用户名
//	private String hiddenaccount;//	绑卡号码
	private Long loadmin;//	充值金额下限
	private Long loadmax;//	充值金额上限
//	private Long mcBankId;
//	public Long getMcBankId() {
//		return mcBankId;
//	}
//	public void setMcBankId(Long mcBankId) {
//		this.mcBankId = mcBankId;
//	}
	public String getBankName() {
		return bankName;
	}
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}
//	public Long getBid() {
//		return bid;
//	}
//	public void setBid(Long bid) {
//		this.bid = bid;
//	}
	public String getBank() {
		return bank;
	}
	public void setBank(String bank) {
		this.bank = bank;
	}
	/*public String getBankradio() {
		return bankradio;
	}
	public void setBankradio(String bankradio) {
		this.bankradio = bankradio;
	}*/
	/*public String getAccountName() {
		return accountName;
	}
	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}
	public String getHiddenaccount() {
		return hiddenaccount;
	}
	public void setHiddenaccount(String hiddenaccount) {
		this.hiddenaccount = hiddenaccount;
	}*/
	public Long getLoadmin() {
		return loadmin;
	}
	public void setLoadmin(Long loadmin) {
		this.loadmin = loadmin;
	}
	public Long getLoadmax() {
		return loadmax;
	}
	public void setLoadmax(Long loadmax) {
		this.loadmax = loadmax;
	}
	
}
