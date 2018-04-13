package com.winterframework.firefrog.phone.web.cotroller.dto;

public class CardListDto {

	private Integer id;//	绑卡银行id
//	private String bankName;//	绑卡银行名称
	private String account;//	绑卡卡号
	private Integer iseffect;//	是否生效1:已生效,0:尚未生效
	private Long bindId;
	private Long mcBankId;
	public Long getBindId() {
		return bindId;
	}
	public void setBindId(Long bindId) {
		this.bindId = bindId;
	}
	public Long getMcBankId() {
		return mcBankId;
	}
	public void setMcBankId(Long mcBankId) {
		this.mcBankId = mcBankId;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	/*public String getBankName() {
		return bankName;
	}
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}*/
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public Integer getIseffect() {
		return iseffect;
	}
	public void setIseffect(Integer iseffect) {
		this.iseffect = iseffect;
	}
}
