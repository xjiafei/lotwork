/**   
* @Title: BankCardApplyBindRequest.java 
* @Package com.winterframework.firefrog.fund.web.dto 
* @Description: 银行卡申请绑定请求参数DTO 
* @author Denny  
* @date 2013-7-2 上午9:25:32 
* @version V1.0   
*/
package com.winterframework.firefrog.fund.web.dto;

/** 
* @ClassName: BankCardApplyBindRequest 
* @Description: 银行卡申请绑定请求参数DTO 
* @author Denny 
* @date 2013-7-2 上午9:25:32 
*  
*/
public class BankCardApplyBindRequest {

	private Long userId;

	private Long mcBankId;

	private Long bankId;
	/** 支行名称 */
	private String branchName;

	private String province;

	private String city;
	/** 开户人姓名 */
	private String bankAccount;
	/** 卡号 */
	private String bankNumber;
	
	private Long bindcardType;
	
	private String nickName;
	
	private String opeType;
	
	private Long id;
	
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public Long getMcBankId() {
		return mcBankId;
	}
	public void setMcBankId(Long mcBankId) {
		this.mcBankId = mcBankId;
	}
	public Long getBankId() {
		return bankId;
	}
	public void setBankId(Long bankId) {
		this.bankId = bankId;
	}
	public String getBranchName() {
		return branchName;
	}
	public void setBranchName(String branchName) {
		this.branchName = branchName;
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
	public String getBankAccount() {
		return bankAccount;
	}
	public void setBankAccount(String bankAccount) {
		this.bankAccount = bankAccount;
	}
	public String getBankNumber() {
		return bankNumber;
	}
	public void setBankNumber(String bankNumber) {
		this.bankNumber = bankNumber;
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
	public String getOpeType() {
		return opeType;
	}
	public void setOpeType(String opeType) {
		this.opeType = opeType;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}

	
}
