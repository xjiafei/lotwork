package com.winterframework.firefrog.game.dao.vo;

import java.util.Date;

import com.winterframework.orm.dal.ibatis3.BaseEntity;

/**
 * @author cms group
 * @version 1.0
 * @since 1.0
 */

public class GamePlanDetail extends BaseEntity {

	private static final long serialVersionUID = 3055516657994987248L;
	//alias
	public static final String TABLE_ALIAS = "追号计划明细表";
	public static final String ALIAS_PLANID = "追号ID";
	public static final String ALIAS_ISSUE_CODE = "奖期";
	public static final String ALIAS_MUTIPLE = "倍数";
	public static final String ALIAS_TOTAMOUNT = "下注总金额";
	public static final String ALIAS_STATUS = "状态 0:未执行 1:已执行 2:已撤销";
	public static final String ALIAS_CREATE_TIME = "创建时间";
	public static final String ALIAS_CANCEL_TIME = "撤销时间";
	public static final String ALIAS_RUN_TIME = "执行时间";

	//date formats

	//columns START
	private Long planid;
	private Long issueCode;
	private Long mutiple;
	private Long totamount;
	private Integer status;
	private Date createTime;
	private Date cancelTime;
	private Date rumTime;
	private String webIssueCode;
	private String cancelUser;
	private Long cancelFee;
	//columns END

	public enum Status {  
		//状态 0:未执行 1:已执行 2:已撤销 3:暂停 4等待執行 5等待撤銷
		UN_EXEC(0), EXEC(1), CANCEL(2), PAUSE(3), WAIT_EXEC(4), WAIT_CANCEL(5) ,CANCEL_ERROR(6);
		private int _value = 0; 
		Status(int value) {
			this._value = value;
		} 
		public int getValue() {
			return _value;
		}
	}
	
	public GamePlanDetail() {
	}
 
	public Long getCancelFee() {
		return cancelFee;
	} 

	public void setCancelFee(Long cancelFee) {
		this.cancelFee = cancelFee;
	}



	public GamePlanDetail(Long id) {
		this.id = id;
	}

	public void setPlanid(Long value) {
		this.planid = value;
	}

	public Long getPlanid() {
		return this.planid;
	}

	public void setIssueCode(Long value) {
		this.issueCode = value;
	}

	public Long getIssueCode() {
		return this.issueCode;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getCancelTime() {
		return cancelTime;
	}

	public void setCancelTime(Date cancelTime) {
		this.cancelTime = cancelTime;
	}

	public Long getMutiple() {
		return mutiple;
	}

	public void setMutiple(Long mutiple) {
		this.mutiple = mutiple;
	}

	public Long getTotamount() {
		return totamount;
	}

	public void setTotamount(Long totamount) {
		this.totamount = totamount;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Date getRumTime() {
		return rumTime;
	}

	public void setRumTime(Date rumTime) {
		this.rumTime = rumTime;
	}

	public String getWebIssueCode() {
		return webIssueCode;
	}

	public void setWebIssueCode(String webIssueCode) {
		this.webIssueCode = webIssueCode;
	}

	public String getCancelUser() {
		return cancelUser;
	}

	public void setCancelUser(String cancelUser) {
		this.cancelUser = cancelUser;
	}
}
