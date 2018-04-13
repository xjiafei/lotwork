package com.winterframework.firefrog.game.web.dto;

import java.io.Serializable;

public class PlansFuturesStruc implements Serializable {

	private static final long serialVersionUID = 9098306202684160537L;
	
	/** 追号明细ID */
	private Long planDetailsId;
	/** 期号 */
	private Long issueCode;
	/** web期号 */
	private String webIssueCode;
	/** 追号倍数 */
	private Integer mutiple;
	/** 投注总金额 */
	private Long totamount;
	/** 状态 */
	private Integer status;
	
	private Long totwin;
	
	private String numberRecord;
	
	private String planCode;
	
	private Boolean canCancel;
	
	private Boolean isOrder;//是否为订单数据组装
	
	public Boolean getIsOrder() {
		return isOrder;
	}

	public void setIsOrder(Boolean isOrder) {
		this.isOrder = isOrder;
	}

	public Long getPlanDetailsId() {
		return planDetailsId;
	}

	public void setPlanDetailsId(Long planDetailsId) {
		this.planDetailsId = planDetailsId;
	}

	public String getWebIssueCode() {
		return webIssueCode;
	}

	public void setWebIssueCode(String webIssueCode) {
		this.webIssueCode = webIssueCode;
	}

	public Integer getMutiple() {
		return mutiple;
	}

	public void setMutiple(Integer mutiple) {
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

	public Long getIssueCode() {
		return issueCode;
	}

	public void setIssueCode(Long issueCode) {
		this.issueCode = issueCode;
	}

	public Long getTotwin() {
		return totwin;
	}

	public void setTotwin(Long totwin) {
		this.totwin = totwin;
	}

	public String getNumberRecord() {
		return numberRecord;
	}

	public void setNumberRecord(String numberRecord) {
		this.numberRecord = numberRecord;
	}

	public String getPlanCode() {
		return planCode;
	}

	public void setPlanCode(String planCode) {
		this.planCode = planCode;
	}

	public Boolean getCanCancel() {
		return canCancel;
	}

	public void setCanCancel(Boolean canCancel) {
		this.canCancel = canCancel;
	}
}
