package com.winterframework.firefrog.game.web.dto;

import java.io.Serializable;

public class GameIssueQueryResponse implements Serializable {

	private static final long serialVersionUID = -7566922148095655398L;
	/**彩種ID*/
	private Long lotteryId;
	/**獎期*/
	private Long issueCode;
	/**web獎期*/
	private String webIssueCode;
	/**開始銷售時間*/
	private Long saleStartTime;
	/**狀態；0:已生成(M1)、1:開始銷售(M2)、2:結束銷售(M3)、3:開獎號碼確認(M4)、4:計獎完成(M5)、5:驗獎完成(M6)、6:派獎完成(M7)、7:獎期結束(M8)、8:對賬結束(M9)*/
	private Integer status;
	/**最新一期已开奖的web奖期*/
	private String lastWebIssueCode;
	/**開獎號碼*/
	private String numberRecord;
	/**結束銷售時間*/
	private Long saleEndTime;
	private boolean isAbleRevoke;
	/**獎期暫停狀態；0：停賣、1：正常、2:撤銷*/
	private Integer pasuseStatus;
	/**奖金结构*/
	private String awardStruc;
	/**開獎時間*/
	private Long openDrawTime;	
	private String message;
	private Long responseStatus;
	private String newNextSaleStartTime;
	private String newSaleEndTime;
	private String nextOpenDrawTime;
	private String nextWebIssueCode;
	private String currentOpenDrawTime;	
	private Long id;
	private Long nextId;
	/**最新一期已开奖的奖期*/
	private Long lastIssueCode;
	
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Long getResponseStatus() {
		return responseStatus;
	}

	public void setResponseStatus(Long responseStatus) {
		this.responseStatus = responseStatus;
	}

	/**
	 * 取得彩種ID。
	 * @return
	 */
	public Long getLotteryId() {
		return lotteryId;
	}

	/**
	 * 設定彩種ID。
	 * @param lotteryId
	 */
	public void setLotteryId(Long lotteryId) {
		this.lotteryId = lotteryId;
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
	public Long getSaleStartTime() {
		return saleStartTime;
	}

	/**
	 * 設定開始銷售時間。
	 * @param saleStartTime
	 */
	public void setSaleStartTime(Long saleStartTime) {
		this.saleStartTime = saleStartTime;
	}

	/**
	 * 取得狀態。
	 * @return 0:已生成(M1)、1:開始銷售(M2)、2:結束銷售(M3)、3:開獎號碼確認(M4)、4:計獎完成(M5)、5:驗獎完成(M6)、6:派獎完成(M7)、7:獎期結束(M8)、8:對賬結束(M9)
	 */
	public Integer getStatus() {
		return status;
	}

	/**
	 * 設定狀態。
	 * @param status 0:已生成(M1)、1:開始銷售(M2)、2:結束銷售(M3)、3:開獎號碼確認(M4)、4:計獎完成(M5)、5:驗獎完成(M6)、6:派獎完成(M7)、7:獎期結束(M8)、8:對賬結束(M9)
	 */
	public void setStatus(Integer status) {
		this.status = status;
	}

	/**
	 * 取得最新一期已开奖的web奖期。
	 * @return
	 */
	public String getLastWebIssueCode() {
		return lastWebIssueCode;
	}

	/**
	 * 設定最新一期已开奖的web奖期。
	 * @param lastWebIssueCode
	 */
	public void setLastWebIssueCode(String lastWebIssueCode) {
		this.lastWebIssueCode = lastWebIssueCode;
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
	 * 取得結束銷售時間。
	 * @return
	 */
	public Long getSaleEndTime() {
		return saleEndTime;
	}

	/**
	 * 設定結束銷售時間。
	 * @param saleEndTime
	 */
	public void setSaleEndTime(Long saleEndTime) {
		this.saleEndTime = saleEndTime;
	}

	public boolean isAbleRevoke() {
		return isAbleRevoke;
	}

	public void setAbleRevoke(boolean isAbleRevoke) {
		this.isAbleRevoke = isAbleRevoke;
	}

	/**
	 * 取得獎期暫停狀態。
	 * @return 0：停賣、1：正常、2:撤銷
	 */
	public Integer getPasuseStatus() {
		return pasuseStatus;
	}

	/**
	 * 設定獎期暫停狀態。
	 * @param pasuseStatus 0：停賣、1：正常、2:撤銷
	 */
	public void setPasuseStatus(Integer pasuseStatus) {
		this.pasuseStatus = pasuseStatus;
	}

	/**
	 * 取得奖金结构。
	 * @return
	 */
	public String getAwardStruc() {
		return awardStruc;
	}

	/**
	 * 設定奖金结构。
	 * @param awardStruc
	 */
	public void setAwardStruc(String awardStruc) {
		this.awardStruc = awardStruc;
	}
	
	/**
	 * 取得開獎時間。
	 * @return
	 */
	public Long getOpenDrawTime() {
		return openDrawTime;
	}

	/**
	 * 設定開獎時間。
	 * @param openDrawTime
	 */
	public void setOpenDrawTime(Long openDrawTime) {
		this.openDrawTime = openDrawTime;
	}	
	
	public String getNewNextSaleStartTime() {
		return newNextSaleStartTime;
	}

	public void setNewNextSaleStartTime(String newNextSaleStartTime) {
		this.newNextSaleStartTime = newNextSaleStartTime;
	}

	public String getNewSaleEndTime() {
		return newSaleEndTime;
	}

	public void setNewSaleEndTime(String newSaleEndTime) {
		this.newSaleEndTime = newSaleEndTime;
	}

	public String getNextOpenDrawTime() {
		return nextOpenDrawTime;
	}

	public void setNextOpenDrawTime(String nextOpenDrawTime) {
		this.nextOpenDrawTime = nextOpenDrawTime;
	}

	public String getNextWebIssueCode() {
		return nextWebIssueCode;
	}

	public void setNextWebIssueCode(String nextWebIssueCode) {
		this.nextWebIssueCode = nextWebIssueCode;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getNextId() {
		return nextId;
	}

	public void setNextId(Long nextId) {
		this.nextId = nextId;
	}

	public String getCurrentOpenDrawTime() {
		return currentOpenDrawTime;
	}

	public void setCurrentOpenDrawTime(String currentOpenDrawTime) {
		this.currentOpenDrawTime = currentOpenDrawTime;
	}
	
	/**
	 * 取得最新一期已开奖的奖期。
	 * @return
	 */
	public Long getLastIssueCode() {
		return lastIssueCode;
	}

	/**
	 * 設定最新一期已开奖的奖期。
	 * @param lastIssueCode
	 */
	public void setLastIssueCode(Long lastIssueCode) {
		this.lastIssueCode = lastIssueCode;
	}
}
