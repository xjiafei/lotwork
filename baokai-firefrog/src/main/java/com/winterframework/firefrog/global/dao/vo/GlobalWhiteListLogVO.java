package com.winterframework.firefrog.global.dao.vo;





import java.util.Date;

import com.winterframework.orm.dal.ibatis3.BaseEntity;

/**
 * @author cms group
 * @version 1.0
 * @since 1.0
 */


public class GlobalWhiteListLogVO extends BaseEntity {
	
	private static final long serialVersionUID = 7584082414837113117L;
	//alias
	public static final String TABLE_ALIAS = "GlobalWhiteListIp";
	public static final String ALIAS_ID = "id";
	public static final String ALIAS_WHITELIST_IP = "指定IP白名單IP";
	public static final String ALIAS_ACCUNT = "用戶名";
	public static final String ALIAS_COUNTRY = "地理位置";
	public static final String ALIAS_OPERATOR = "操作者ID";
	public static final String ALIAS_OPERATION_TIME = "操作時間";
	public static final String ALIAS_STATUS = "狀態　0.新增 1.修改　2.刪除　3.查詢";
	public static final String ALIAS_LISTIP = "listIP";
	
	//date formats
	
	//columns START
	private Long id;
	private String whiteListIP;
	private String accunt;
	private String country;
	private String operator;
	private Date operationTime;
	private String status;
	private Long listIP;
	private int pageSize;
	
	//columns END
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getAccunt() {
		return accunt;
	}
	public void setAccunt(String accunt) {
		this.accunt = accunt;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getOperator() {
		return operator;
	}
	public void setOperator(String operator) {
		this.operator = operator;
	}
	public Date getOperationTime() {
		return operationTime;
	}
	public void setOperationTime(Date operationTime) {
		this.operationTime = operationTime;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	public String getWhiteListIP() {
		return whiteListIP;
	}
	public void setWhiteListIP(String whiteListIP) {
		this.whiteListIP = whiteListIP;
	}
	public Long getListIP() {
		return listIP;
	}
	public void setListIP(Long listIP) {
		this.listIP = listIP;
	}
}

