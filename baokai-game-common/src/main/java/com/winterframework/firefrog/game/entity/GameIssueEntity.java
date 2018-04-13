package com.winterframework.firefrog.game.entity;

import java.util.Date;

/**
 * 獎期開獎物件。
 * @author Pogi.Lin
 */
public class GameIssueEntity {

	public static long STATUS_STOP_SALE = 2;
	private Long id;
	private Lottery lottery;
	/**獎期*/
	private Long issueCode;
	/**web奖期*/
	private String webIssueCode;
	/**開始銷售時間*/
	private Date saleStartTime;
	/**結束銷售時間*/
	private Date saleEndTime;
	/**理論開獎時間*/
	private Date openDrawTime;
	/**ec确认号码时间*/
	private Date ecVerifiedTime;
	/**获取开奖号码时间*/
	private Date recivceDrawTime;
	/**實際開獎時間*/
	private Date factionDrawTime;
	/**獎期狀態0:已生成(M1)、1:開始銷售(M2)、2:結束銷售(M3)、3:開獎號碼確認(M4)、4:計獎完成(M5)、5:驗獎完成(M6)、6:派獎完成(M7)、7:獎期結束(M8)、8:對賬結束(M9)*/
	private GameIssueStatus status;
	/**創建時間*/
	private Date createTime;
	/**更新時間*/
	private Date updateTime;
	/**獎期過程狀態；0:待銷售(P1)、1:銷售中(P2)、2:待開獎(P3)、3:計獎中(P4)、4:驗獎中(P5)、5:派獎中(P6)、6:待結束(P7)、7:待對賬(P8)*/
	private GameIssuePeriodStatus periodStatus;
	/**獎期暫停狀態；0：停賣、1：正常、2:撤銷*/
	private PauseStatus pauseStatus;
	/**任務鎖定狀態；1:正常、2:鎖定,任務執行中*/
	private EventStatus eventStatus;
	private Long sequence;//奖期规则序列号，值区间1-9999，每日循环
	/**追號單是否完成狀態；0:未完成、1:已完成*/
	private PlanFinishStatus planFinishStatus;
	/**上期是否暫停；0:未暫停、1:暫停（存在上期未完成的計劃）*/
	private LastIssueStop lastIssueStop;
	/**上期期號*/
	private Long lastIssue;
	/**實際上期期號*/
	private Long realLastIssue;
	/**是否已生成報表*/
	private Integer isReported;
	/**是否已生成走勢圖*/
	private Integer isTrend;
	/**開獎號碼*/
	private String numberRecord;
	/**開獎號碼最後更新時間*/
	private Date numberUpdateTime;
	/**開獎號碼更新後台人員ID。系統觸發時填 -1*/
	private Long numberUpdateOperator;
	/**开奖号码更新次数*/
	private Long numberUpdateCount;
	/**奖金结构*/
	private String awardStruct;
	/**管理員撤單時間*/
	private Date adminCanCancelEndTime;
	/**管理員可撤銷時間*/
	private Long issuewarnExceptionTime;
	/**上一個開獎號碼*/
	private String preNumberRecord;
	
	/**
	 * 取得管理員撤單時間。
	 * @return
	 */
	public Date getAdminCanCancelEndTime() {
		return adminCanCancelEndTime;
	}

	/**
	 * 設定管理員撤單時間。
	 * @param adminCanCancelEndTime
	 */
	public void setAdminCanCancelEndTime(Date adminCanCancelEndTime) {
		this.adminCanCancelEndTime = adminCanCancelEndTime;
	}

	/**
	 * 取得開獎號碼。
	 * @return
	 */
	public String getNumberRecord() {
		return numberRecord;
	}

	/**
	 * 設定開獎號碼。
	 * @param numberRecord
	 */
	public void setNumberRecord(String numberRecord) {
		this.numberRecord = numberRecord;
	}

	/**
	 * 取得開獎號碼最後更新時間。
	 * @return
	 */
	public Date getNumberUpdateTime() {
		return numberUpdateTime;
	}

	/**
	 * 設定開獎號碼最後更新時間。
	 * @param numberUpdateTime
	 */
	public void setNumberUpdateTime(Date numberUpdateTime) {
		this.numberUpdateTime = numberUpdateTime;
	}

	/**
	 * 取得開獎號碼更新後台人員ID。
	 * @return 系統觸發時填 -1
	 */
	public Long getNumberUpdateOperator() {
		return numberUpdateOperator;
	}

	/**
	 * 設定開獎號碼更新後台人員ID。
	 * @param numberUpdateOperator 系統觸發時填 -1
	 */
	public void setNumberUpdateOperator(Long numberUpdateOperator) {
		this.numberUpdateOperator = numberUpdateOperator;
	}

	/**
	 * 取得开奖号码更新次数。
	 * @return
	 */
	public Long getNumberUpdateCount() {
		return numberUpdateCount;
	}

	/**
	 * 設定开奖号码更新次数。
	 * @param numberUpdateCount
	 */
	public void setNumberUpdateCount(Long numberUpdateCount) {
		this.numberUpdateCount = numberUpdateCount;
	}


	public GameIssueEntity() {
	}

	public GameIssueEntity(Long issueCode) {
		this.issueCode = issueCode;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Lottery getLottery() {
		return lottery;
	}

	public void setLottery(Lottery lottery) {
		this.lottery = lottery;
	}

	/**
	 * 取得獎期。
	 * @return
	 */
	public Long getIssueCode() {
		return issueCode;
	}

	/**
	 * 設定獎期。
	 * @param issueCode
	 */
	public void setIssueCode(Long issueCode) {
		this.issueCode = issueCode;
	}

	/**
	 * 取得web奖期。
	 * @return
	 */
	public String getWebIssueCode() {
		return webIssueCode;
	}

	/**
	 * 設定web奖期。
	 * @param webIssueCode
	 */
	public void setWebIssueCode(String webIssueCode) {
		this.webIssueCode = webIssueCode;
	}

	/**
	 * 取得開始銷售時間。
	 * @return
	 */
	public Date getSaleStartTime() {
		return saleStartTime;
	}

	/**
	 * 設定開始銷售時間。
	 * @param saleStartTime
	 */
	public void setSaleStartTime(Date saleStartTime) {
		this.saleStartTime = saleStartTime;
	}

	/**
	 * 取得理論開獎時間。
	 * @return
	 */
	public Date getOpenDrawTime() {
		return openDrawTime;
	}

	/**
	 * 設定理論開獎時間。
	 * @param openDrawTime
	 */
	public void setOpenDrawTime(Date openDrawTime) {
		this.openDrawTime = openDrawTime;
	}

	/**
	 * 取得實際開獎時間。
	 * @return
	 */
	public Date getFactionDrawTime() {
		return factionDrawTime;
	}

	/**
	 * 設定實際開獎時間。
	 * @param factionDrawTime
	 */
	public void setFactionDrawTime(Date factionDrawTime) {
		this.factionDrawTime = factionDrawTime;
	}

	/**
	 * 取得狀態。
	 * @return 0:已生成(M1)、1:開始銷售(M2)、2:結束銷售(M3)、3:開獎號碼確認(M4)、4:計獎完成(M5)、5:驗獎完成(M6)、6:派獎完成(M7)、7:獎期結束(M8)、8:對賬結束(M9)
	 */
	public GameIssueStatus getStatus() {
		return status;
	}

	/**
	 * 設定狀態。
	 * @param status 0:已生成(M1)、1:開始銷售(M2)、2:結束銷售(M3)、3:開獎號碼確認(M4)、4:計獎完成(M5)、5:驗獎完成(M6)、6:派獎完成(M7)、7:獎期結束(M8)、8:對賬結束(M9)
	 */
	public void setStatus(GameIssueStatus status) {
		this.status = status;
	}

	/**
	 * 取得創建時間。
	 * @return
	 */
	public Date getCreateTime() {
		return createTime;
	}

	/**
	 * 設定創建時間。
	 * @param createTime
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	/**
	 * 取得更新時間。
	 * @return
	 */
	public Date getUpdateTime() {
		return updateTime;
	}

	/**
	 * 設定更新時間。
	 * @param updateTime
	 */
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	/**
	 * 取得獎期過程狀態。
	 * @return 0:待銷售(P1)、1:銷售中(P2)、2:待開獎(P3)、3:計獎中(P4)、4:驗獎中(P5)、5:派獎中(P6)、6:待結束(P7)、7:待對賬(P8)
	 */
	public GameIssuePeriodStatus getPeriodStatus() {
		return periodStatus;
	}

	/**
	 * 設定獎期過程狀態。
	 * @param periodStatus 0:待銷售(P1)、1:銷售中(P2)、2:待開獎(P3)、3:計獎中(P4)、4:驗獎中(P5)、5:派獎中(P6)、6:待結束(P7)、7:待對賬(P8)
	 */
	public void setPeriodStatus(GameIssuePeriodStatus periodStatus) {
		this.periodStatus = periodStatus;
	}

	/**
	 * 取得結束銷售時間。
	 * @return
	 */
	public Date getSaleEndTime() {
		return saleEndTime;
	}

	/**
	 * 設定結束銷售時間。
	 * @param saleEndTime
	 */
	public void setSaleEndTime(Date saleEndTime) {
		this.saleEndTime = saleEndTime;
	}

	/**
	 * 取得獎期暫停狀態。
	 * @return 0：停賣、1：正常、2:撤銷
	 */
	public PauseStatus getPauseStatus() {
		return pauseStatus;
	}

	/**
	 * 設定獎期暫停狀態。
	 * @param pauseStatus 0：停賣、1：正常、2:撤銷
	 */
	public void setPauseStatus(PauseStatus pauseStatus) {
		this.pauseStatus = pauseStatus;
	}

	/**
	 * 取得任務鎖定狀態。
	 * @return 1:正常、2:鎖定,任務執行中
	 */
	public EventStatus getEventStatus() {
		return eventStatus;
	}

	/**
	 * 設定任務鎖定狀態。
	 * @param eventStatus 1:正常、2:鎖定,任務執行中
	 */
	public void setEventStatus(EventStatus eventStatus) {
		this.eventStatus = eventStatus;
	}

	public Long getSequence() {
		return sequence;
	}

	public void setSequence(Long sequence) {
		this.sequence = sequence;
	}

	/**
	 * 取得追號單是否完成狀態。
	 * @return 0:未完成、1:已完成
	 */
	public PlanFinishStatus getPlanFinishStatus() {
		return planFinishStatus;
	}

	/**
	 * 設定追號單是否完成狀態。
	 * @param planFinishStatus 0:未完成、1:已完成
	 */
	public void setPlanFinishStatus(PlanFinishStatus planFinishStatus) {
		this.planFinishStatus = planFinishStatus;
	}

	/**
	 * 取得上期是否暫停。
	 * @return 0:未暫停、1:暫停（存在上期未完成的計劃）
	 */
	public LastIssueStop getLastIssueStop() {
		return lastIssueStop;
	}

	/**
	 * 設定上期是否暫停。
	 * @param lastIssueStop 0:未暫停、1:暫停（存在上期未完成的計劃）
	 */
	public void setLastIssueStop(LastIssueStop lastIssueStop) {
		this.lastIssueStop = lastIssueStop;
	}
	
	/**
	 * 取得上期期號。
	 * @return
	 */
	public Long getLastIssue() {
		return lastIssue;
	}

	/**
	 * 設定上期期號。
	 * @param lastIssue
	 */
	public void setLastIssue(Long lastIssue) {
		this.lastIssue = lastIssue;
	}

	/**
	 * 取得實際上期期號。
	 * @return
	 */
	public Long getRealLastIssue() {
		return realLastIssue;
	}

	/**
	 * 設定實際上期期號。
	 * @param realLastIssue
	 */
	public void setRealLastIssue(Long realLastIssue) {
		this.realLastIssue = realLastIssue;
	}

	/**
	 * 取得是否已生成報表。
	 * @return
	 */
	public Integer getIsReported() {
		return isReported;
	}

	/**
	 * 設定是否已生成報表。
	 * @param isReported
	 */
	public void setIsReported(Integer isReported) {
		this.isReported = isReported;
	}

	/**
	 * 取得是否已生成走勢圖。
	 * @return
	 */
	public Integer getIsTrend() {
		return isTrend;
	}

	/**
	 * 設定是否已生成走勢圖。
	 * @param isTrend
	 */
	public void setIsTrend(Integer isTrend) {
		this.isTrend = isTrend;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((createTime == null) ? 0 : createTime.hashCode());
		result = prime * result + ((eventStatus == null) ? 0 : eventStatus.hashCode());
		result = prime * result + ((factionDrawTime == null) ? 0 : factionDrawTime.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((issueCode == null) ? 0 : issueCode.hashCode());
		result = prime * result + ((lastIssueStop == null) ? 0 : lastIssueStop.hashCode());
		result = prime * result + ((lottery == null) ? 0 : lottery.hashCode());
		result = prime * result + ((openDrawTime == null) ? 0 : openDrawTime.hashCode());
		result = prime * result + ((pauseStatus == null) ? 0 : pauseStatus.hashCode());
		result = prime * result + ((periodStatus == null) ? 0 : periodStatus.hashCode());
		result = prime * result + ((planFinishStatus == null) ? 0 : planFinishStatus.hashCode());
		result = prime * result + ((saleEndTime == null) ? 0 : saleEndTime.hashCode());
		result = prime * result + ((saleStartTime == null) ? 0 : saleStartTime.hashCode());
		result = prime * result + ((sequence == null) ? 0 : sequence.hashCode());
		result = prime * result + ((status == null) ? 0 : status.hashCode());
		result = prime * result + ((updateTime == null) ? 0 : updateTime.hashCode());
		result = prime * result + ((webIssueCode == null) ? 0 : webIssueCode.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		GameIssueEntity other = (GameIssueEntity) obj;
		if (createTime == null) {
			if (other.createTime != null)
				return false;
		} else if (!createTime.equals(other.createTime))
			return false;
		if (eventStatus != other.eventStatus)
			return false;
		if (factionDrawTime == null) {
			if (other.factionDrawTime != null)
				return false;
		} else if (!factionDrawTime.equals(other.factionDrawTime))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (issueCode == null) {
			if (other.issueCode != null)
				return false;
		} else if (!issueCode.equals(other.issueCode))
			return false;
		if (lastIssueStop != other.lastIssueStop)
			return false;
		if (lottery == null) {
			if (other.lottery != null)
				return false;
		} else if (!lottery.equals(other.lottery))
			return false;
		if (openDrawTime == null) {
			if (other.openDrawTime != null)
				return false;
		} else if (!openDrawTime.equals(other.openDrawTime))
			return false;
		if (pauseStatus != other.pauseStatus)
			return false;
		if (periodStatus != other.periodStatus)
			return false;
		if (planFinishStatus != other.planFinishStatus)
			return false;
		if (saleEndTime == null) {
			if (other.saleEndTime != null)
				return false;
		} else if (!saleEndTime.equals(other.saleEndTime))
			return false;
		if (saleStartTime == null) {
			if (other.saleStartTime != null)
				return false;
		} else if (!saleStartTime.equals(other.saleStartTime))
			return false;
		if (sequence == null) {
			if (other.sequence != null)
				return false;
		} else if (!sequence.equals(other.sequence))
			return false;
		if (status != other.status)
			return false;
		if (updateTime == null) {
			if (other.updateTime != null)
				return false;
		} else if (!updateTime.equals(other.updateTime))
			return false;
		if (webIssueCode == null) {
			if (other.webIssueCode != null)
				return false;
		} else if (!webIssueCode.equals(other.webIssueCode))
			return false;
		return true;
	}

	/**
	 * 取得奖金结构。
	 * @return
	 */
	public String getAwardStruct() {
		return awardStruct;
	}

	/**
	 * 設定奖金结构。
	 * @param awardStruct
	 */
	public void setAwardStruct(String awardStruct) {
		this.awardStruct = awardStruct;
	}

	/**
	 * 取得管理員可撤銷時間。
	 * @return
	 */
	public Long getIssuewarnExceptionTime() {
		return issuewarnExceptionTime;
	}

	/**
	 * 設定管理員可撤銷時間。
	 * @param issuewarnExceptionTime
	 */
	public void setIssuewarnExceptionTime(Long issuewarnExceptionTime) {
		this.issuewarnExceptionTime = issuewarnExceptionTime;
	}

	/**
	 * 取得ec确认号码时间。
	 * @return
	 */
	public Date getEcVerifiedTime() {
		return ecVerifiedTime;
	}

	/**
	 * 設定ec确认号码时间。
	 * @param ecVerifiedTime
	 */
	public void setEcVerifiedTime(Date ecVerifiedTime) {
		this.ecVerifiedTime = ecVerifiedTime;
	}

	/**
	 * 取得获取开奖号码时间。
	 * @return
	 */
	public Date getRecivceDrawTime() {
		return recivceDrawTime;
	}

	/**
	 * 設定获取开奖号码时间。
	 * @param recivceDrawTime
	 */
	public void setRecivceDrawTime(Date recivceDrawTime) {
		this.recivceDrawTime = recivceDrawTime;
	}

	/**
	 * 取得上一個開獎號碼。
	 * @return
	 */
	public String getPreNumberRecord() {
		return preNumberRecord;
	}

	/**
	 * 設定上一個開獎號碼。
	 * @param preNumberRecord
	 */
	public void setPreNumberRecord(String preNumberRecord) {
		this.preNumberRecord = preNumberRecord;
	}
}
