/**   
* @Title: BankCardBindHistoryRecordQueryDTO.java 
* @Package com.winterframework.firefrog.fund.web.dto 
* @Description: TODO(用一句话描述该文件做什么) 
* @author 你的名字   
* @date 2013-7-23 下午10:03:16 
* @version V1.0   
*/
package com.winterframework.firefrog.fund.web.dto;

/** 
* @ClassName: BankCardBindHistoryRecordQueryDTO 
* @Description: 绑卡历史记录DTO
* @author Alan
* @date 2013-7-23 下午10:03:16 
*  
*/
public class BankCardBindHistoryRecordQueryDTO {

	public static String SORT_COLUMNS = "ACTION_TIME";

	private Long userId;
	private int startNo;
	private int endNo;
	private String sortColumns;
	private Long bindcardType;
private String  bankCard;


	public String getBankCard() {
	return bankCard;
}

public void setBankCard(String bankCard) {
	this.bankCard = bankCard;
}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
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

	public static String getSORT_COLUMNS() {
		return SORT_COLUMNS;
	}

	public static void setSORT_COLUMNS(String sORT_COLUMNS) {
		SORT_COLUMNS = sORT_COLUMNS;
	}

	public Long getBindcardType() {
		return bindcardType;
	}

	public void setBindcardType(Long bindcardType) {
		this.bindcardType = bindcardType;
	}
}
