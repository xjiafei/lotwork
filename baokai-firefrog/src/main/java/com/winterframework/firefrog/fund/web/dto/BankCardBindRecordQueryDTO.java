/**   
* @Title: BankCardBindRecordQueryDTO.java 
* @Package com.winterframework.firefrog.fund.web.dto 
* @Description: TODO(用一句话描述该文件做什么) 
* @author 你的名字   
* @date 2013-7-19 下午1:42:23 
* @version V1.0   
*/
package com.winterframework.firefrog.fund.web.dto;

/** 
* @ClassName: BankCardBindRecordQueryDTO 
* @Description: 绑卡记录DTO 
* @author Alan 
* @date 2013-7-19 下午1:42:23 
*  
*/
public class BankCardBindRecordQueryDTO {

	public static final String SORT_COLUMNS = "ACCOUNT";

	private String userAccount;
	private Long userLvl;
	private String operator;
	private String bankCardNumber;
	private int startNo;
	private int endNo;
	private Long bindcardType;

	private String sortColumns;

	public String getUserAccount() {
		return userAccount;
	}

	public void setUserAccount(String userAccount) {
		this.userAccount = userAccount;
	}

	public Long getUserLvl() {
		return userLvl;
	}

	public void setUserLvl(Long userLvl) {
		this.userLvl = userLvl;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public String getBankCardNumber() {
		return bankCardNumber;
	}

	public void setBankCardNumber(String bankCardNumber) {
		this.bankCardNumber = bankCardNumber;
	}

	public int getStartNo() {
		return startNo;
	}

	public void setStartNo(int startNo) {
		this.startNo = startNo;
	}

	public int getEndNo() {
		return endNo;
	}

	public void setEndNo(int endNo) {
		this.endNo = endNo;
	}

	public String getSortColumns() {
		return sortColumns;
	}

	public void setSortColumns(String sortColumns) {
		this.sortColumns = sortColumns;
	}

	public Long getBindcardType() {
		return bindcardType;
	}

	public void setBindcardType(Long bindcardType) {
		this.bindcardType = bindcardType;
	}

}
