package com.winterframework.firefrog.game.dao.vo;

import java.util.Date;

import com.winterframework.orm.dal.ibatis3.BaseEntity;

/**
 * 獎期資料。
 * @author Pogi.Lin
 */
public class GameIssue extends BaseEntity {

	private static final long serialVersionUID = -2619259061602950899L;

	/**彩種ID*/
	private Long lotteryid;
	/**獎期*/
	private Long issueCode;
	/**web獎期*/
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
	/**狀態；0:已生成(M1)、1:開始銷售(M2)、2:結束銷售(M3)、3:開獎號碼確認(M4)、4:計獎完成(M5)、5:驗獎完成(M6)、6:派獎完成(M7)、7:獎期結束(M8)、8:對賬結束(M9)*/
	private Long status;
	/**創建時間*/
	private Date createTime;
	/**更新時間*/
	private Date updateTime;
	/**獎期過程狀態；0:待銷售(P1)、1:銷售中(P2)、2:待開獎(P3)、3:計獎中(P4)、4:驗獎中(P5)、5:派獎中(P6)、6:待結束(P7)、7:待對賬(P8)*/
	private Long periodStatus;
	/**暫停狀態；0：停賣、1：正常、2:撤銷*/
	private Integer pauseStatus;
	/**任務鎖定狀態；1:正常2:鎖定,任務執行中*/
	private Integer eventStatus;
	/**獎期規則序列號。區間值1-9999，每日循環。*/
	private Long sequence;
	/**追號單是否完成狀態；0:未完成、1:已完成*/
	private Integer planFinishStatus;
	/**上期是否暫停；0:未暫停、1:暫停（存在上期未完成的計劃）*/
	private Integer lastIssueStop;
	/**上期期號*/
	private Long lastIssue;
	/**實際上期期號*/
	private Long realLastIssue;
	/**是否已生成報表*/
	private Integer isReported;
	/**是否已生成走勢圖*/
	private Integer isTrend;
	/**上一個開獎號碼*/
	private String preNumberRecord;
	/**開獎號碼*/
	private String numberRecord;
	/**開獎號碼最後更新時間*/
	private Date numberUpdateTime;
	/**開獎號碼更新後台人員ID*/
	private Long numberUpdateOperator;
	/**開獎號碼更新次數*/
	private Long numberUpdateCount = 0L;
	/**奖金结构*/
	private String awardStruct;
	/**管理員撤單時間*/
	private Date adminEndCancelTime;
	/**管理員可撤銷時間*/
	private Long issuewarnExceptionTime;
	/**主動獲取開獎號碼次數*/
	private Long tryGetNumberCount;
	/**後台操作人員ID*/
	private Long userId;
	
	/**
	 * 取得管理員撤單時間。
	 * @return
	 */
	public Date getAdminEndCancelTime() {
		return adminEndCancelTime;
	}

	/**
	 * 設定管理員撤單時間。
	 * @param adminEndCancelTime
	 */
	public void setAdminEndCancelTime(Date adminEndCancelTime) {
		this.adminEndCancelTime = adminEndCancelTime;
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
	 * 取得後台操作人員ID。
	 * @return
	 */
	public Long getUserId() {
		return userId;
	}

	/**
	 * 設定後台操作人員ID。
	 * @param userId
	 */
	public void setUserId(Long userId) {
		this.userId = userId;
	}

	/**
	 * 獎期狀態
	 * @author Pogi.Lin
	 */
	public enum Status { 
		/**0:已生成(M1)*/
		CREATE(0), 
		/**1:开始销售(M2)*/
		SALE_START(1), 
		/**2:结束销售(M3)*/
		SALE_END(2), 
		/**3:开奖号码确认(M4)*/
		WIN_NUMBER(3), 
		/**4:计奖完成(M5)*/
		REWARD_CAL(4), 
		/**5:验奖完成(M6)*/
		REWARD_CHECK(5), 
		/**6:派奖完成(M7)*/
		REWARD_DISTRI(6), 
		/**7:奖期结束(M8)*/
		FINISH(7), 
		/**8:对账结束(M9)*/
		ACCOUNT(8);
		private int _value = 0; 
		Status(int value) {
			this._value = value;
		} 
		public int getValue() {
			return _value;
		}
	}
	
	/**
	 * 暫停狀態。
	 * @author Pogi.Lin
	 */
	public enum PauseStatus {  
		/**0：暂停*/
		PAUSE(0), 
		/**1：正常*/
		NORMAL(1), 
		/**2:撤销*/
		CANCEL(2);
		private int _value = 1; 
		PauseStatus(int value) {
			this._value = value;
		} 
		public int getValue() {
			return _value;
		}
	}
	
	/**
	 * 獎期過程狀態。
	 * @author Pogi.Lin
	 */
	public enum PeriodStatus {  
		/**0:待销售(P1)*/
		SALE_NEED(0), 
		/**1:销售中(P2)*/
		SALE_ON(1), 
		/**2:待开奖(P3)*/
		REWARD_NEED(2),
		/**3:计奖中(P4)*/
		REWARD_CAL(3), 
		/**4:验奖中(P5)*/
		REWARD_CHECK(4), 
		/**5:派奖中(P6)*/
		REWARD_DISTRI(5),
		/**6:待结束(P7)*/
		FINISH_NEED(6),
		/**7:待对账(P8)*/
		ACCOUNT_NEED(7);
		private int _value = 1; 
		PeriodStatus(int value) {
			this._value = value;
		} 
		public int getValue() {
			return _value;
		}
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
	 * 取得開獎號碼更新後台人員ID。<br>
	 * 系統寫入時填 -1。
	 * @return
	 */
	public Long getNumberUpdateOperator() {
		return numberUpdateOperator;
	}

	/**
	 * 設定開獎號碼更新後台人員ID。<br>
	 * 系統寫入時填 -1。
	 * @param numberUpdateOperator
	 */
	public void setNumberUpdateOperator(Long numberUpdateOperator) {
		this.numberUpdateOperator = numberUpdateOperator;
	}

	/**
	 * 取得開獎號碼更新次數。
	 * @return
	 */
	public Long getNumberUpdateCount() {
		return numberUpdateCount;
	}

	/**
	 * 設定開獎號碼更新次數。
	 * @param numberUpdateCount
	 */
	public void setNumberUpdateCount(Long numberUpdateCount) {
		this.numberUpdateCount = numberUpdateCount;
	}

	public GameIssue() {
	}

	/**
	 * 取得彩種ID。
	 * @return
	 */
	public Long getLotteryid() {
		return lotteryid;
	}

	/**
	 * 設定彩種ID。
	 * @param lotteryid
	 */
	public void setLotteryid(Long lotteryid) {
		this.lotteryid = lotteryid;
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
	 * 取得web獎期。
	 * @return
	 */
	public String getWebIssueCode() {
		return webIssueCode;
	}

	/**
	 * 設定web獎期。
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
	public Long getStatus() {
		return status;
	}

	/**
	 * 設定狀態。
	 * @param status 0:已生成(M1)、1:開始銷售(M2)、2:結束銷售(M3)、3:開獎號碼確認(M4)、4:計獎完成(M5)、5:驗獎完成(M6)、6:派獎完成(M7)、7:獎期結束(M8)、8:對賬結束(M9)
	 */
	public void setStatus(Long status) {
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
	public Long getPeriodStatus() {
		return periodStatus;
	}
	
	/**
	 * 設定獎期過程狀態。
	 * @param periodStatus 0:待銷售(P1)、1:銷售中(P2)、2:待開獎(P3)、3:計獎中(P4)、4:驗獎中(P5)、5:派獎中(P6)、6:待結束(P7)、7:待對賬(P8)
	 */
	public void setPeriodStatus(Long periodStatus) {
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
	 * 取得暫停狀態。
	 * @return 0：停賣、1：正常、2:撤銷
	 */
	public Integer getPauseStatus() {
		return pauseStatus;
	}

	/**
	 * 設定暫停狀態。
	 * @param pauseStatus 0：停賣、1：正常、2:撤銷
	 */
	public void setPauseStatus(Integer pauseStatus) {
		this.pauseStatus = pauseStatus;
	}

	/**
	 * 取得任務鎖定狀態。
	 * @return 1:正常、2:鎖定,任務執行中
	 */
	public Integer getEventStatus() {
		return eventStatus;
	}

	/**
	 * 設定任務鎖定狀態。
	 * @param eventStatus 1:正常、2:鎖定,任務執行中
	 */
	public void setEventStatus(Integer eventStatus) {
		this.eventStatus = eventStatus;
	}

	/**
	 * 取得獎期規則序列號。<br>
	 * 區間值1-9999，每日循環。
	 * @return
	 */
	public Long getSequence() {
		return sequence;
	}

	/**
	 * 設定獎期規則序列號。<br>
	 * 區間值1-9999，每日循環。
	 * @param sequence
	 */
	public void setSequence(Long sequence) {
		this.sequence = sequence;
	}

	/**
	 * 取得追號單是否完成狀態。
	 * @return 0:未完成、1:已完成
	 */
	public Integer getPlanFinishStatus() {
		return planFinishStatus;
	}

	/**
	 * 設定追號單是否完成狀態。
	 * @param planFinishStatus 0:未完成、1:已完成
	 */
	public void setPlanFinishStatus(Integer planFinishStatus) {
		this.planFinishStatus = planFinishStatus;
	}

	/**
	 * 取得上期是否暫停。<br>
	 * 當期開獎依賴上期，與追號相關。
	 * @return 0:未暫停、1:暫停（存在上期未完成的計劃）
	 */
	public Integer getLastIssueStop() {
		return lastIssueStop;
	}

	/**
	 * 設定上期是否暫停。<br>
	 * 當期開獎依賴上期，與追號相關。
	 * @param lastIssueStop 0:未暫停、1:暫停（存在上期未完成的計劃）
	 */
	public void setLastIssueStop(Integer lastIssueStop) {
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
	 * 取得實際上期期號。<br>
	 * 撤銷時候會發生。
	 * @return
	 */
	public Long getRealLastIssue() {
		return realLastIssue;
	}

	/**
	 * 設定實際上期期號。<br>
	 * 撤銷時候會發生。
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
	 * 取得主動獲取開獎號碼次數。
	 * @return
	 */
	public Long getTryGetNumberCount() {
		return tryGetNumberCount;
	}

	/**
	 * 設定主動獲取開獎號碼次數。
	 * @param tryGetNumberCount
	 */
	public void setTryGetNumberCount(Long tryGetNumberCount) {
		this.tryGetNumberCount = tryGetNumberCount;
	}
	
}
