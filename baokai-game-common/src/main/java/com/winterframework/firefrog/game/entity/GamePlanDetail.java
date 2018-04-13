package com.winterframework.firefrog.game.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/** 
* @ClassName: GamePlanDetail 
* @Description: 追号详情bean
* @author Denny 
* @date 2013-7-19 下午5:49:53 
*  
*/
public class GamePlanDetail implements Serializable {

	private static final long serialVersionUID = 6349102502898769075L;
	private Long id;
	private GamePlan gamePlan;
//	private Long issueCode;
//	private String webIssueCode;
	private GameIssueEntity gameIssue;
	private Long mutiple;
	private BigDecimal totamount;
	private GamePlanDetailStatus detailStatus;
	private Date createTime;
	private Date cancelTime;
//	private String retPointChain;
//	private String retUserChain;
	private List<LockPoint> lockPoint=new ArrayList<LockPoint>();
	
	private Long cancelFee;
	
	public Long getCancelFee() {
		return cancelFee;
	}

	public void setCancelFee(Long cancelFee) {
		this.cancelFee = cancelFee;
	}

	public enum GamePlanDetailStatus {
		//状态 0:未执行 1:已执行 2:已撤销 3暫停 4等待執行 5等待撤銷 6撤销异常
		UN_EXEC(0), EXEC(1), CANCEL(2),PAUSE(3), WAIT_EXEC(4), WAIT_CANCEL(5), CANCEL_ERROR(6);

		private int _value = 0;

		GamePlanDetailStatus(int action) {
			this._value = action;
		}

		public int getValue() {
			return _value;
		}
	}

	public GamePlanDetail() {

	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public List<LockPoint> getLockPoint() {
		return lockPoint;
	}

	public void setLockPoint(List<LockPoint> lockPoint) {
		this.lockPoint = lockPoint;
	}

	public GamePlan getGamePlan() {
		return gamePlan;
	}

	public void setGamePlan(GamePlan gamePlan) {
		this.gamePlan = gamePlan;
	}

	/*public Long getIssueCode() {
		return issueCode;
	}

	public void setIssueCode(Long issueCode) {
		this.issueCode = issueCode;
	}

	public String getWebIssueCode() {
		return webIssueCode;
	}

	public void setWebIssueCode(String webIssueCode) {
		this.webIssueCode = webIssueCode;
	}*/

	public Long getMutiple() {
		return mutiple;
	}

	public void setMutiple(Long mutiple) {
		this.mutiple = mutiple;
	}

	public BigDecimal getTotamount() {
		return totamount;
	}

	public void setTotamount(BigDecimal totamount) {
		this.totamount = totamount;
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

	public GamePlanDetailStatus getDetailStatus() {
		return detailStatus;
	}

	public void setDetailStatus(GamePlanDetailStatus detailStatus) {
		this.detailStatus = detailStatus;
	}

	public GameIssueEntity getGameIssue() {
		return gameIssue;
	}

	public void setGameIssue(GameIssueEntity gameIssue) {
		this.gameIssue = gameIssue;
	}

/*	public String getRetPointChain() {
		return retPointChain;
	}

	public void setRetPointChain(String retPointChain) {
		this.retPointChain = retPointChain;
	}

	public String getRetUserChain() {
		return retUserChain;
	}

	public void setRetUserChain(String retUserChain) {
		this.retUserChain = retUserChain;
	}*/

/*	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(getId()).append(getIssueCode())
				.append(getMutiple()).append(getTotamount())
				.append(getDetailStatus()).toHashCode();
	}*/

	/**
	* Title: equals
	* Description:请勿轻易修改此类的equals和hash,code方法  用于投注追号detail去重处理
	* @param obj
	* @return 
	* @see java.lang.Object#equals(java.lang.Object) 
	*/
	/*@Override
	public boolean equals(Object obj) {
		if (obj instanceof GamePlanDetail == false) {
			return false;
		}
		if (this == obj) {
			return true;
		}
		GamePlanDetail other = (GamePlanDetail) obj;
		return new EqualsBuilder().append(getId(), other.getId())

		.append(getIssueCode(), other.getIssueCode())

		.append(getMutiple(), other.getMutiple())

		.append(getTotamount(), other.getTotamount())

		.append(getDetailStatus(), other.getDetailStatus())


		.isEquals();
	}*/

}
