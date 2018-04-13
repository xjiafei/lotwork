package com.winterframework.firefrog.game.dao.vo;

import java.util.Date;

import com.winterframework.orm.dal.ibatis3.BaseEntity;

/**
 * @author cms group
 * @version 1.0
 * @since 1.0
 */

public class GamePlan extends BaseEntity {

	private static final long serialVersionUID = 798523894721537358L;
	//alias
	public static final String TABLE_ALIAS = "追号计划表";
	public static final String ALIAS_USERID = "用户ID";
	public static final String ALIAS_LOTTERYID = "彩种";
	public static final String ALIAS_START_ISUUE_CODE = "开始奖期号";
	public static final String ALIAS_FINISH_ISSUE = "完成奖期数";
	public static final String ALIAS_TOTAL_ISSUE = "总计奖期数";
	public static final String ALIAS_STOP_MODE = "停止方式 0:不停止 1:按累计盈利停止 2:中奖即停";
	public static final String ALIAS_STOP_PARMS = "停止参数数量";
	public static final String ALIAS_OPTION_PARMS = "可选的JSON参数";
	public static final String ALIAS_CREATE_TIME = "创建时间";
	public static final String ALIAS_START_WEB_ISSUE = "WEB开始奖期号";
	public static final String ALIAS_PLAN_TYPE = "追号类型 1:普通追号 2:翻倍追号 3:盈利金额追号 4:盈利率追号";
	public static final String ALIAS_TOTAMOUNT = "追号总金额";
	public static final String ALIAS_USERIP = "用户IP";
	public static final String ALIAS_SERVERIP = "服务器IP";
	public static final String ALIAS_SALE_TIME = "销售时间";
	public static final String ALIAS_STATUS = "状态 1:未开始 1:进行中 2 已结束 3 已终止";
	public static final String ALIAS_CANCEL_TIME = "终止时间";
	public static final String ALIAS_PLAN_CODE = "追号编码";
	public static final String ALIAS_PACKAGE_ID = "方案ID";
	public static final String ALIAS_CHANNEL_ID = "渠道ID 1 WEB 2 IOS 3 IPAD 4 android 5 WAC";
	public static final String ALIAS_CANCEL_ISSUE = "取消期数";
	public static final String ALIAS_SOLD_AMOUNT = "销售完成总金额";
	public static final String ALIAS_CANCELED_AMOUNT = "取消总金额";
	public static final String ALIAS_CANCEL_MODES = "撤销方式 0:默认 1:用户 2:系统";

	//date formats

	//columns START
	private Long lotteryid;
	private Long startIsuueCode;
	private Integer finishIssue;
	private Integer totalIssue;
	private Integer stopMode;
	private Long stopParms;
	private String optionParms;
	private Date createTime;
	private String startWebIssue;
	private Integer planType;
	private Integer status;
	private Date cancelTime;
	private String planCode;
	private Long packageId;
	private Integer cancelIssue;
	private Long soldAmount;
	private Long canceledAmount;
	private Integer cancelModes;
	private Long winAmount;
	private Long planUserId;

	//查询使用，不初始化到数据库中
	private String lotteryName;
	private Long totalAmount;
	private Long userId;
	private Date updateTime;
	//columns END
	
	public enum Status { 
		//状态 0:未开始 1:进行中 2 已结束 3 已终止 4 暂停 5 存在异常 6 执行中
		INIT(0), WAITING(1), FINISH(2), STOP(3), PAUSE(4), EXCEP(5);
		private int _value = 0; 
		Status(int value) {
			this._value = value;
		} 
		public int getValue() {
			return _value;
		}
	}
	public enum StopMode { 
		//停止方式 0:不停止 1:按累计盈利停止 2:中奖即停
		NEVER(0), ACCUM(1), WIN(2);
		private int _value = 0; 
		StopMode(int value) {
			this._value = value;
		} 
		public int getValue() {
			return _value;
		}
	}

	public GamePlan() {
	}

	public Long getPlanUserId() {
		return planUserId;
	}

	public void setPlanUserId(Long planUserId) {
		this.planUserId = planUserId;
	}

	public GamePlan(Long id) {
		this.id = id;
	}

	public void setLotteryid(Long value) {
		this.lotteryid = value;
	}

	public Long getLotteryid() {
		return this.lotteryid;
	}

	public void setStartIsuueCode(Long value) {
		this.startIsuueCode = value;
	}

	public Long getStartIsuueCode() {
		return this.startIsuueCode;
	}

	public Integer getFinishIssue() {
		return finishIssue;
	}

	public void setFinishIssue(Integer finishIssue) {
		this.finishIssue = finishIssue;
	}

	public Integer getTotalIssue() {
		return totalIssue;
	}

	public void setTotalIssue(Integer totalIssue) {
		this.totalIssue = totalIssue;
	}

	public Integer getStopMode() {
		return stopMode;
	}

	public void setStopMode(Integer stopMode) {
		this.stopMode = stopMode;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public void setStopParms(Long value) {
		this.stopParms = value;
	}

	public Long getStopParms() {
		return this.stopParms;
	}

	public void setOptionParms(String value) {
		this.optionParms = value;
	}

	public String getOptionParms() {
		return this.optionParms;
	}

	public void setStartWebIssue(String value) {
		this.startWebIssue = value;
	}

	public String getStartWebIssue() {
		return this.startWebIssue;
	}

	public Integer getPlanType() {
		return planType;
	}

	public void setPlanType(Integer planType) {
		this.planType = planType;
	}

	public Integer getStatus() {
		return status;
	}

	public void setPlanCode(String value) {
		this.planCode = value;
	}

	public String getPlanCode() {
		return this.planCode;
	}

	public void setPackageId(Long value) {
		this.packageId = value;
	}

	public Long getPackageId() {
		return this.packageId;
	}

	public void setCancelIssue(Integer value) {
		this.cancelIssue = value;
	}

	public Integer getCancelIssue() {
		return this.cancelIssue;
	}

	public void setSoldAmount(Long value) {
		this.soldAmount = value;
	}

	public Long getSoldAmount() {
		return this.soldAmount;
	}

	public void setCanceledAmount(Long value) {
		this.canceledAmount = value;
	}

	public Long getCanceledAmount() {
		return this.canceledAmount;
	}

	public void setCancelModes(Integer value) {
		this.cancelModes = value;
	}

	public Integer getCancelModes() {
		return this.cancelModes;
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

	public Long getWinAmount() {
		return winAmount==null?0L:winAmount;
	}

	public void setWinAmount(Long winAmount) { 
		this.winAmount = winAmount;
	}


	public Long getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(Long totalAmount) {
		this.totalAmount = totalAmount;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}


	public String getLotteryName() {
		return lotteryName;
	}

	public void setLotteryName(String lotteryName) {
		this.lotteryName = lotteryName;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	
}
