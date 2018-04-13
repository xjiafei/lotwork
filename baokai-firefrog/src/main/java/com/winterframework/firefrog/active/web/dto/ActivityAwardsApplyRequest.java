package com.winterframework.firefrog.active.web.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;


/** 
* @ClassName ActivityAwardsApplyRequest 
* @Description 用户获奖记录
* @author  hugh
* @date 2014年11月28日 上午11:52:37 
*  
*/
public class ActivityAwardsApplyRequest implements Serializable {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1017310155712873878L;
	
	
	/**ID*/
	private Long id;
	/**userId*/
	private Long user_id;
	/**用戶帳號*/
	private String account;
	/**資金異動流水號*/
	private String fundChangeLogSN;
	/**應發獎金，非0資料(代表獎金)可轉入 FUND_CHANGE_LOG*/
	private Long changeAmt;
	/**實發獎金*/
	private Long approverAmt;
	/**審批人帳號*/
	private String approver;
	/**審批時間*/
	private Date gmtAppr;
	/**審批註解*/
	private String approverMemo;
	/**活動名稱*/
	private String actName;
	
	
	public String getActName() {
		return actName;
	}
	public void setActName(String actName) {
		this.actName = actName;
	}
	public String getFundChangeLogSN() {
		return fundChangeLogSN;
	}
	public void setFundChangeLogSN(String fundChangeLogSN) {
		this.fundChangeLogSN = fundChangeLogSN;
	}
	public Long getUser_id() {
		return user_id;
	}
	public void setUser_id(Long user_id) {
		this.user_id = user_id;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public Long getChangeAmt() {
		return changeAmt;
	}
	public void setChangeAmt(Long changeAmt) {
		this.changeAmt = changeAmt;
	}
	public Long getApproverAmt() {
		return approverAmt;
	}
	public void setApproverAmt(Long approverAmt) {
		this.approverAmt = approverAmt;
	}
	public String getApprover() {
		return approver;
	}
	public void setApprover(String approver) {
		this.approver = approver;
	}
	public Date getGmtAppr() {
		return gmtAppr;
	}
	public void setGmtAppr(Date gmtAppr) {
		this.gmtAppr = gmtAppr;
	}
	public String getApproverMemo() {
		return approverMemo;
	}
	public void setApproverMemo(String approverMemo) {
		this.approverMemo = approverMemo;
	}
	
	

	
	

}
