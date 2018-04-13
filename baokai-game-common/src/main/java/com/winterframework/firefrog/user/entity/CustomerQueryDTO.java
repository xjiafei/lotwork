package com.winterframework.firefrog.user.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 *    
 * 类功能说明:客户查询条件DTO
 *
 * <p>Copyright: Copyright(c) 2013 </p> 
 * @Version 1.0  
 *  
 *
 */
public class CustomerQueryDTO implements Serializable {

	private static final long serialVersionUID = 2993546336837874586L;

	private String account;
	private String email;
	private Integer userLvl;
	private Date fromDate;
	private Date toDate;
	private long fromBal = 0;
	private long toBal = 0;
	private int startNo;
	private int endNo;
	private Date lastLoginDate;
	private String sortColumns;
	private Long parentId;
	private Date fromLoginDate;
	private Date toLoginDate;

	private Long parentChainId;
	private boolean isAccountMatch;

	public CustomerQueryDTO() {

	}

	public String getAccount() {
		return account;
	}

	public boolean isAccountMatch() {
		return isAccountMatch;
	}

	public void setAccountMatch(boolean isAccountMatch) {
		this.isAccountMatch = isAccountMatch;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Integer getUserLvl() {
		return userLvl;
	}

	public void setUserLvl(Integer userLvl) {
		this.userLvl = userLvl;
	}

	public Date getFromDate() {
		return fromDate;
	}

	public void setFromDate(Date fromDate) {
		this.fromDate = fromDate;
	}

	public Date getToDate() {
		return toDate;
	}

	public void setToDate(Date toDate) {
		this.toDate = toDate;
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

	public long getFromBal() {
		return fromBal;
	}

	public void setFromBal(long fromBal) {
		this.fromBal = fromBal;
	}

	public long getToBal() {
		return toBal;
	}

	public void setToBal(long toBal) {
		this.toBal = toBal;
	}

	public Date getLastLoginDate() {
		return lastLoginDate;
	}

	public void setLastLoginDate(Date lastLoginDate) {
		this.lastLoginDate = lastLoginDate;
	}

	public String getSortColumns() {
		return sortColumns;
	}

	public void setSortColumns(String sortColumns) {
		this.sortColumns = sortColumns;
	}

	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	public Date getFromLoginDate() {
		return fromLoginDate;
	}

	public void setFromLoginDate(Date fromLoginDate) {
		this.fromLoginDate = fromLoginDate;
	}

	public Date getToLoginDate() {
		return toLoginDate;
	}

	public void setToLoginDate(Date toLoginDate) {
		this.toLoginDate = toLoginDate;
	}

	public Long getParentChainId() {
		return parentChainId;
	}

	public void setParentChainId(Long parentChainId) {
		this.parentChainId = parentChainId;
	}

}
