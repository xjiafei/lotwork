package com.winterframework.firefrog.game.entity;

import java.util.Date;
import java.util.List;

import com.winterframework.firefrog.user.entity.User;

/**
 * @author cms group
 * @version 1.0
 * @since 1.0
 */

public class GamePackage {

	private Long packageId;
	private User user;
	private Channel channel;
	//	private Long issueCode;
	private GameIssueEntity gameIssue;
	private Lottery lottery;
	private String packageCode;
	private List<GamePackageItem> itemList; 
	private Long awardId;
	//	private Long planId;

	private GamePackageType type;
	
	public enum GamePackageType {
		PACKAGES(1), PLAN(2);

		public int _value = 0;

		GamePackageType(int action) {
			this._value = action;
		}

		public int getValue() {
			return _value;
		}
	}

	private FileMode fileMode;
	private Date saleTime;
	private Date cancelTime;
	private Long userip;
	private Long serverip;
	private Long packageAmount;
	private Date webSaleTime;
	private String retUserChain;
	private Long activityType;
	public GamePackage(User user2) {
		this.user = user2;
	}

	public GamePackage() {
	}

	public GamePackage(Long gamePackageId) {
		this.packageId = gamePackageId;
	}

	public User getUser() {
		return user;
	}



	public void setUser(User user) {
		this.user = user;
	}

	/*	public Long getIssueCode() {
			return issueCode;
		}

		public void setIssueCode(Long issueCode) {
			this.issueCode = issueCode;
		}*/

	public Date getSaleTime() {
		return saleTime;
	}

	public void setSaleTime(Date saleTime) {
		this.saleTime = saleTime;
	}

	public Date getCancelTime() {
		return cancelTime;
	}

	public void setCancelTime(Date cancelTime) {
		this.cancelTime = cancelTime;
	}

	public Long getUserip() {
		return userip;
	}

	public void setUserip(Long userip) {
		this.userip = userip;
	}

	public Long getServerip() {
		return serverip;
	}

	public void setServerip(Long serverip) {
		this.serverip = serverip;
	}

	public Long getPackageAmount() {
		return packageAmount;
	}

	public void setPackageAmount(Long packageAmount) {
		this.packageAmount = packageAmount;
	}

	public GamePackageType getType() {
		return type;
	}

	public void setType(GamePackageType type) {
		this.type = type;
	}

	public String getPackageCode() {
		return packageCode;
	}

	public void setPackageCode(String packageCode) {
		this.packageCode = packageCode;
	}

	public Lottery getLottery() {
		return lottery;
	}

	public void setLottery(Lottery lottery) {
		this.lottery = lottery;
	}

	/*public Long getPlanId() {
		return planId;
	}

	public void setPlanId(Long planId) {
		this.planId = planId;
	}
	*/
	public Channel getChannel() {
		return channel;
	}

	public void setChannel(Channel channel) {
		this.channel = channel;
	}

	public GameIssueEntity getGameIssue() {
		return gameIssue;
	}

	public void setGameIssue(GameIssueEntity gameIssue) {
		this.gameIssue = gameIssue;
	}

	public FileMode getFileMode() {
		return fileMode;
	}

	public void setFileMode(FileMode fileMode) {
		this.fileMode = fileMode;
	}

	public Date getWebSaleTime() {
		return webSaleTime;
	}

	public void setWebSaleTime(Date webSaleTime) {
		this.webSaleTime = webSaleTime;
	}

	public List<GamePackageItem> getItemList() {
		return itemList;
	}

	public void setItemList(List<GamePackageItem> itemList) {
		this.itemList = itemList;
	}

	public Long getPackageId() {
		return packageId;
	}

	public void setPackageId(Long packageId) {
		this.packageId = packageId;
	}

	public String getRetUserChain() {
		return retUserChain;
	}

	public void setRetUserChain(String retUserChain) {
		this.retUserChain = retUserChain;
	}

	public Long getAwardId() {
		return awardId;
	}

	public void setAwardId(Long awardId) {
		this.awardId = awardId;
	}

	public Long getActivityType() {
		return activityType;
	}

	public void setActivityType(Long activityType) {
		this.activityType = activityType;
	}
	
}
