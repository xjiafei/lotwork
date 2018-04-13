package com.winterframework.firefrog.game.dao.vo;

import java.util.Date;

import com.winterframework.orm.dal.ibatis3.BaseEntity;

/**
 * @author cms group
 * @version 1.0
 * @since 1.0
 */

public class GamePackage extends BaseEntity {

	private static final long serialVersionUID = 6839883081838612012L;
	//alias
	public static final String TABLE_ALIAS = "方案表";
	public static final String ALIAS_USERID = "用户ID";
	public static final String ALIAS_ISSUE_CODE = "奖期";
	public static final String ALIAS_LOTTORYID = "彩种";
	public static final String ALIAS_PACKAGE_CODE = "方案编号";
	public static final String ALIAS_STATUS = "状态 1:正常方案 2:撤销方案";
	public static final String ALIAS_SALE_TIME = "销售时间";
	public static final String ALIAS_CANCEL_TIME = "撤销时间";
	public static final String ALIAS_USERIP = "用户IP";
	public static final String ALIAS_SERVERIP = "服务器IP";
	public static final String ALIAS_PACKAGE_AMOUNT = "方案金额";
	public static final String ALIAS_AWARD_ID = "奖金组id";
	public static final String ALIAS_WEB_SALE_TIME = "WEB提交时间";
	// 0非文件 1文件
	public static final String ALIAS_FILE_MODE = "文件模式";
	public static final String ALIAS_ACTIVITY_TYPE = "活動類別";
	//date formats

	//columns START
	private Long userid;
	private Long issueCode;
	private Long lotteryid;
	private String packageCode;
	private Integer type;
	private Date saleTime;
	private Date cancelTime;
	private Long userip;
	private Long serverip;
	private Long packageAmount;
	private Long channelId;
	private String channelVersion;
	private Long awardId;
	private Date webSaleTime;
	private Integer fileMode;
	private String retUserChain;
	private Long activityType;
	//columns END
	public enum Type{
		//方案类型： 1：普通方案  2:追号方案
		COMMON(1), PLAN(2); 
		private int _value=1;
		Type(int value){
			this._value=value;
		}
		public int getValue(){
			return this._value;
		}
	}
	public GamePackage() {
	}

	public GamePackage(Long id) {
		this.id = id;
	}

	public void setUserid(Long value) {
		this.userid = value;
	}

	public Long getUserid() {
		return this.userid;
	}

	public void setIssueCode(Long value) {
		this.issueCode = value;
	}

	public Long getIssueCode() {
		return this.issueCode;
	}

	public void setUserip(Long value) {
		this.userip = value;
	}

	public Long getUserip() {
		return this.userip;
	}

	public void setServerip(Long value) {
		this.serverip = value;
	}

	public Long getServerip() {
		return this.serverip;
	}

	public void setPackageAmount(Long value) {
		this.packageAmount = value;
	}

	public Long getPackageAmount() {
		return this.packageAmount;
	}

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

	public Long getLotteryid() {
		return lotteryid;
	}

	public void setLotteryid(Long lotteryid) {
		this.lotteryid = lotteryid;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getPackageCode() {
		return packageCode;
	}

	public void setPackageCode(String packageCode) {
		this.packageCode = packageCode;
	}

	public Long getChannelId() {
		return channelId;
	}

	public void setChannelId(Long channelId) {
		this.channelId = channelId;
	}

	public String getChannelVersion() {
		return channelVersion;
	}

	public void setChannelVersion(String channelVersion) {
		this.channelVersion = channelVersion;
	}

	public Long getAwardId() {
		return awardId;
	}

	public void setAwardId(Long awardId) {
		this.awardId = awardId;
	}

	public Date getWebSaleTime() {
		return webSaleTime;
	}

	public void setWebSaleTime(Date webSaleTime) {
		this.webSaleTime = webSaleTime;
	}

	public Integer getFileMode() {
		return fileMode;
	}

	public void setFileMode(Integer fileMode) {
		this.fileMode = fileMode;
	}

	public String getRetUserChain() {
		return retUserChain;
	}

	public void setRetUserChain(String retUserChain) {
		this.retUserChain = retUserChain;
	}

	public Long getActivityType() {
		return activityType;
	}

	public void setActivityType(Long activityType) {
		this.activityType = activityType;
	}

}
