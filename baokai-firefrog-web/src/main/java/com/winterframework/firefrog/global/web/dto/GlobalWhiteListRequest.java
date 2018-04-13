package com.winterframework.firefrog.global.web.dto;

import java.io.Serializable;
import java.util.Date;


public class GlobalWhiteListRequest implements Serializable {


	private static final long serialVersionUID = 3919995572627700354L;

	private Long id;

	private String userAcunt;
	
	/**
	 * IP Address
	 */
	private String ipAddr;
	private String ipAddr_bk;
	
	/**
	 * 備註
	 */
	private String remark;
	
	/**
	 * 0 : 名稱搜尋   1: IP搜尋 2.全部搜尋
	 */
	private int type;
	
	private Long pageNo;
	
	private String classA;
	
	private String classB;
	
	private String classC;
	
	private String classD;
	
	private String operator;
	
	private Date operationTime;
	
	/**
	 * 搜尋字串
	 */
	private String word;
	
	/**
	 * 查看的id字串
	 * 刪除的id字串
	 * 修改的id字串
	 */
	private String ids;
	
	/**
	 * 0:add  
	 * 1:edit
	 * 2:detail
	 */
	private int mode;
	
	/**
	 * 狀態 0:關閉 1:開啟
	 */
	private Long status;
	
	/**
	 * 訊息傳遞
	 */
	private String msg;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUserAcunt() {
		return userAcunt;
	}

	public void setUserAcunt(String userAcunt) {
		this.userAcunt = userAcunt;
	}

	public String getIpAddr() {
		return ipAddr;
	}

	public void setIpAddr(String ipAddr) {
		this.ipAddr = ipAddr;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public Long getPageNo() {
		return pageNo;
	}

	public void setPageNo(Long pageNo) {
		this.pageNo = pageNo;
	}

	public String getClassA() {
		return classA;
	}

	public void setClassA(String classA) {
		this.classA = classA;
	}

	public String getClassB() {
		return classB;
	}

	public void setClassB(String classB) {
		this.classB = classB;
	}

	public String getClassC() {
		return classC;
	}

	public void setClassC(String classC) {
		this.classC = classC;
	}

	public String getClassD() {
		return classD;
	}

	public void setClassD(String classD) {
		this.classD = classD;
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

	public int getMode() {
		return mode;
	}

	public void setMode(int mode) {
		this.mode = mode;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public Long getStatus() {
		return status;
	}

	public void setStatus(Long status) {
		this.status = status;
	}

	public String getWord() {
		return word;
	}

	public void setWord(String word) {
		this.word = word;
	}

	public String getIds() {
		return ids;
	}

	public void setIds(String ids) {
		this.ids = ids;
	}

	public String getIpAddr_bk() {
		return ipAddr_bk;
	}

	public void setIpAddr_bk(String ipAddr_bk) {
		this.ipAddr_bk = ipAddr_bk;
	}

}
