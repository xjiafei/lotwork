package com.winterframework.firefrog.game.entity;

import java.io.Serializable;
import java.util.Date;

import com.winterframework.firefrog.user.entity.User;

public class GameWarnOrderEntity implements Serializable {

	private static final long serialVersionUID = 2773472532015604245L;

	private Long id;
	private Lottery lottery;
	private Long issueCode;
	private String webIssueCode;
	private String orderCode;
	private Long countWin;
	private Long winsRatio;
	private Long maxslipWins;
	private Long slipWinsratio;
	private ParentType parantType;
	private GameWarnOrderStatus status;
	private Date createTime;
	private Date updateTime;
	private Long orderId;
	private User user;
	private GameWarnType type;
	private Date saleTime;
	private Long channelId;
	public enum GameWarnType{
		RiskOrder(1), WasteOrder(2);
		
		private int value;
		GameWarnType(int action){
			this.value = action;
		}
		
		public int getValue(){
			return this.value;
		}
	}
	
	public enum GameWarnOrderStatus{
		//状态 0 待审核 1 已审核 2 未通过审核
		PendingAudit(0), Audit(1),unAudit(2);
		
		private int value;
		GameWarnOrderStatus(int action){
			this.value = action;
		}
		
		public int getValue(){
			return value;
		}
	}
	
	public enum ParentType {
		issue(1),plan(2);
		private int value;
		ParentType(int action) {
			this.value = action;
		}
		
		public int getValue(){
			return value;
		}
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

	public Long getIssueCode() {
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
	}

	public String getOrderCode() {
		return orderCode;
	}

	public void setOrderCode(String orderCode) {
		this.orderCode = orderCode;
	}

	public Long getCountWin() {
		return countWin;
	}

	public void setCountWin(Long countWin) {
		this.countWin = countWin;
	}

	public Long getWinsRatio() {
		return winsRatio;
	}

	public void setWinsRatio(Long winsRatio) {
		this.winsRatio = winsRatio;
	}

	public Long getMaxslipWins() {
		return maxslipWins;
	}

	public void setMaxslipWins(Long maxslipWins) {
		this.maxslipWins = maxslipWins;
	}

	public Long getSlipWinsratio() {
		return slipWinsratio;
	}

	public void setSlipWinsratio(Long slipWinsratio) {
		this.slipWinsratio = slipWinsratio;
	}

	public ParentType getParantType() {
		return parantType;
	}

	public void setParantType(ParentType parantType) {
		this.parantType = parantType;
	}

	public GameWarnOrderStatus getStatus() {
		return status;
	}

	public void setStatus(GameWarnOrderStatus status) {
		this.status = status;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public GameWarnType getType() {
		return type;
	}

	public void setType(GameWarnType type) {
		this.type = type;
	}

	public Date getSaleTime() {
		return saleTime;
	}

	public void setSaleTime(Date saleTime) {
		this.saleTime = saleTime;
	}

	public Long getChannelId() {
		return channelId;
	}

	public void setChannelId(Long channelId) {
		this.channelId = channelId;
	}
	
}
