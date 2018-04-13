package com.winterframework.firefrog.game.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class GameOrder implements Serializable {

	private static final long serialVersionUID = 7630056111002498512L;
	public static int STATUS_CANCEL = 4;
	private Long id;
	private Lottery lottery;
	private GameIssueEntity gameIssue;
	private Date saleTime;
	private Date calculateWinTime;
	private String orderCode;
	private Long totalAmount;
	private Long totalWin;
	private String numberRecord;
	private List<GameSlip> slipList;
	private GamePackage gamePackage;
	private FileMode fileMode;

	private Long parentid;
	
	//最迟可撤销时间
	private Date endCanCancelTime;
	
	private Long gamePlanId;
	private Long gamePlanDetailId;
	private Long cancelFee;
	private Long lastOrderId;
	private Long lastIssueCode;	
	private Integer fundStatus;
	private Long awardGroupId;	//用户奖金组ID
	private Long totDiamondWin;
	private Long diamondMultiple;
	
	private String headImg;
	private String nickName;

	public String getHeadImg() {
		return headImg;
	}

	public void setHeadImg(String headImg) {
		this.headImg = headImg;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	
	public Long getGamePlanId() {
		return gamePlanId;
	}

	public void setGamePlanId(Long gamePlanId) {
		this.gamePlanId = gamePlanId;
	}

	public Long getGamePlanDetailId() {
		return gamePlanDetailId;
	}

	public void setGamePlanDetailId(Long gamePlanDetailId) {
		this.gamePlanDetailId = gamePlanDetailId;
	}

	public Long getCancelFee() {
		return cancelFee;
	}

	public void setCancelFee(Long cancelFee) {
		this.cancelFee = cancelFee;
	}

	public Long getLastOrderId() {
		return lastOrderId;
	}

	public void setLastOrderId(Long lastOrderId) {
		this.lastOrderId = lastOrderId;
	}

	public Long getTotalWin() {
		return totalWin;
	}

	public void setTotalWin(Long totalWin) {
		this.totalWin = totalWin;
	}

	public Long getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(Long totalAmount) {
		this.totalAmount = totalAmount;
	}

	public String getOrderCode() {
		return orderCode;
	}

	public void setOrderCode(String orderCode) {
		this.orderCode = orderCode;
	}

	private OrderParentType parentType;

	public enum OrderParentType {
		PACKAGES(1), PLAN(2);

		private long _value = 0;

		OrderParentType(long action) {
			this._value = action;
		}

		public long getValue() {
			return _value;
		}
	}

	private OrderStatus status;

	public enum OrderStatus {
		WAITING(1), PRIZE(2), UN_PRIZE(3), CANCEL(4), ERROR(5), ARCHIVING(6), AUDITWAIT(8), AUDITFAIL(7), AUDITSUCC(9);

		private int _value = 0;

		OrderStatus(int action) {
			this._value = action;
		}

		public int getValue() {
			return _value;
		}
	}

	public CancelMode cancelModes;

	public GameOrder() {

	}

	public Date getSaleTime() {
		return saleTime;
	}

	public void setSaleTime(Date saleTime) {
		this.saleTime = saleTime;
	}

	public OrderParentType getParentType() {
		return parentType;
	}

	public void setParentType(OrderParentType parentType) {
		this.parentType = parentType;
	}

	public OrderStatus getStatus() {
		return status;
	}

	public void setStatus(OrderStatus status) {
		this.status = status;
	}

	public Lottery getLottery() {
		return lottery;
	}

	public void setLottery(Lottery lottery) {
		this.lottery = lottery;
	}

	public CancelMode getCancelModes() {
		return cancelModes;
	}

	public void setCancelModes(CancelMode cancelModes) {
		this.cancelModes = cancelModes;
	}

	public String getNumberRecord() {
		return numberRecord;
	}

	public void setNumberRecord(String numberRecord) {
		this.numberRecord = numberRecord;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public GameIssueEntity getGameIssue() {
		return gameIssue;
	}

	public void setGameIssue(GameIssueEntity gameIssue) {
		this.gameIssue = gameIssue;
	}

	public Date getCalculateWinTime() {
		return calculateWinTime;
	}

	public void setCalculateWinTime(Date calculateWinTime) {
		this.calculateWinTime = calculateWinTime;
	}

	public List<GameSlip> getSlipList() {
		return slipList;
	}

	public void setSlipList(List<GameSlip> slipList) {
		this.slipList = slipList;
	}

	public GamePackage getGamePackage() {
		return gamePackage;
	}

	public void setGamePackage(GamePackage gamePackage) {
		this.gamePackage = gamePackage;
	}

	public FileMode getFileMode() {
		return fileMode;
	}

	public void setFileMode(FileMode fileMode) {
		this.fileMode = fileMode;
	}

	public Long getParentid() {
		return parentid;
	}

	public void setParentid(Long parentid) {
		this.parentid = parentid;
	}

	public Date getEndCanCancelTime() {
		return endCanCancelTime;
	}

	public void setEndCanCancelTime(Date endCanCancelTime) {
		this.endCanCancelTime = endCanCancelTime;
	}

	public Long getLastIssueCode() {
		return lastIssueCode;
	}

	public void setLastIssueCode(Long lastIssueCode) {
		this.lastIssueCode = lastIssueCode;
	}

	public Integer getFundStatus() {
		return fundStatus;
	}

	public void setFundStatus(Integer fundStatus) {
		this.fundStatus = fundStatus;
	}

	public Long getAwardGroupId() {
		return awardGroupId;
	}

	public void setAwardGroupId(Long awardGroupId) {
		this.awardGroupId = awardGroupId;
	}

	public Long getTotDiamondWin() {
		return totDiamondWin;
	}

	public void setTotDiamondWin(Long totDiamondWin) {
		this.totDiamondWin = totDiamondWin;
	}

	public Long getDiamondMultiple() {
		return diamondMultiple;
	}

	public void setDiamondMultiple(Long diamondMultiple) {
		this.diamondMultiple = diamondMultiple;
	}
}
