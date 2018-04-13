 package com.winterframework.firefrog.game.dao.vo;

import java.util.Date;

import com.winterframework.orm.dal.ibatis3.BaseEntity;

/**
 * @author cms group
 * @version 1.0
 * @since 1.0
 */


public class GameIssueLog extends BaseEntity {
	
	private static final long serialVersionUID = 5732560006503942906L;
	//alias
	public static final String TABLE_ALIAS = "奖期变更表";
	public static final String ALIAS_LOTTERYID = "彩种";
	public static final String ALIAS_ISSUE_CODE = "奖期，8位日期加4位sequence";
	public static final String ALIAS_WEB_ISSUE_CODE = "WEB显示奖期";
	public static final String ALIAS_SALE_START_TIME = "开始销售时间";
	public static final String ALIAS_SALE_END_TIME = "结束销售时间";
	public static final String ALIAS_OPEN_DRAW_TIME = "开奖时间";
	public static final String ALIAS_FACTION_DRAW_TIME = "实际开奖时间";
	public static final String ALIAS_STATUS = "奖期状态 0:已生成(M1) 1:开始销售(M2) 2:结束销售(M3) 3:开奖号码确认(M4) 4:计奖完成(M5) 5:验奖完成(M6) 6:派奖完成(M7) 7:奖期结束(M8) 8:对账结束(M9)";
	public static final String ALIAS_CREATE_TIME = "创建时间";
	public static final String ALIAS_UPDATE_TIME = "更新时间";
	public static final String ALIAS_PERIOD_STATUS = "奖期过程状态  0:待销售(P1) 1:销售中(P2) 2:待开奖(P3) 3:计奖中(P4) 4:验奖中(P5) 5:派奖中(P6) 6:待结束(P7) 7:待对账(P8)";
	public static final String ALIAS_PAUSE_STATUS = "暂停状态 0：暂停 1：正常";
	public static final String ALIAS_EVENT_STATUS = "任务锁定状态  1:正常 2:锁定,任务执行中";
	public static final String ALIAS_SEQUENCE = "奖期规则序列号，值区间1-9999，每日循环";
	public static final String ALIAS_PLAN_FINISH_STATUS = "计划订单是否完成 0 未完成 1 已完成";
	public static final String ALIAS_LAST_ISSUE_STOP = "上期暂停计划 0 无 1 存在上期未完成的计划";
	
	//date formats
	
	//columns START
	private Long lotteryid;
	private Long issueCode;
	private String webIssueCode;
	private Date saleStartTime;
	private Date saleEndTime;
	private Date openDrawTime;
	private Date factionDrawTime;
	private Long status;
	private Date createTime;
	private Date updateTime;
	private Long periodStatus;
	private Long pauseStatus;
	private Long eventStatus;
	private Long sequence;
	private Long planFinishStatus;
	private Long lastIssueStop;
	//columns END

	public GameIssueLog(){
	}

	public GameIssueLog(
		Long id
	){
		this.id = id;
	}

	public void setLotteryid(Long value) {
		this.lotteryid = value;
	}
	
	public Long getLotteryid() {
		return this.lotteryid;
	}
	public void setIssueCode(Long value) {
		this.issueCode = value;
	}
	
	public Long getIssueCode() {
		return this.issueCode;
	}
	public void setWebIssueCode(String value) {
		this.webIssueCode = value;
	}
	
	public String getWebIssueCode() {
		return this.webIssueCode;
	}
	
	public Date getSaleStartTime() {
		return saleStartTime;
	}

	public void setSaleStartTime(Date saleStartTime) {
		this.saleStartTime = saleStartTime;
	}

	public Date getSaleEndTime() {
		return saleEndTime;
	}

	public void setSaleEndTime(Date saleEndTime) {
		this.saleEndTime = saleEndTime;
	}

	public Date getOpenDrawTime() {
		return openDrawTime;
	}

	public void setOpenDrawTime(Date openDrawTime) {
		this.openDrawTime = openDrawTime;
	}

	public Date getFactionDrawTime() {
		return factionDrawTime;
	}

	public void setFactionDrawTime(Date factionDrawTime) {
		this.factionDrawTime = factionDrawTime;
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

	public void setStatus(Long value) {
		this.status = value;
	}
	
	public Long getStatus() {
		return this.status;
	}

	public void setPeriodStatus(Long value) {
		this.periodStatus = value;
	}
	
	public Long getPeriodStatus() {
		return this.periodStatus;
	}
	public void setPauseStatus(Long value) {
		this.pauseStatus = value;
	}
	
	public Long getPauseStatus() {
		return this.pauseStatus;
	}
	public void setEventStatus(Long value) {
		this.eventStatus = value;
	}
	
	public Long getEventStatus() {
		return this.eventStatus;
	}
	public void setSequence(Long value) {
		this.sequence = value;
	}
	
	public Long getSequence() {
		return this.sequence;
	}
	public void setPlanFinishStatus(Long value) {
		this.planFinishStatus = value;
	}
	
	public Long getPlanFinishStatus() {
		return this.planFinishStatus;
	}
	public void setLastIssueStop(Long value) {
		this.lastIssueStop = value;
	}
	
	public Long getLastIssueStop() {
		return this.lastIssueStop;
	}
    
}

