 package com.winterframework.firefrog.game.dao.vo;

import java.util.Date;
import com.winterframework.orm.dal.ibatis3.BaseEntity;

/**
 * @author hugh
 * @version 1.0
 * @since 1.0
 */


public class GameWarnIssueLog extends BaseEntity {
	
	private static final long serialVersionUID = 4200504763764976449L;
	//alias
	public static final String TABLE_ALIAS = "奖期警告日志表";
	public static final String ALIAS_LOTTERYID = "彩种";
	public static final String ALIAS_ISSUE_CODE = "奖期";
	public static final String ALIAS_WEB_ISSUE_CODE = "WEB显示奖期";
	public static final String ALIAS_EVENT = "处理事件";
	
	public static final String ALIAS_CREATE_TIME = "创建时间";
	public static final String ALIAS_DISPOSE_INFO = "处理内容";
	public static final String ALIAS_DISPOSE_MEMO = "处理备注";
	public static final String ALIAS_DISPOSE_USER = "处理人";
	
	
	//date formats
	
	//columns START
	private Long lotteryid;
	private Long issueCode;
	private String webIssueCode;
	private String event;
	
	private Date createTime;
	private String disposeInfo;
	private String disposeMemo;
	private String disposeUser;
	
	//columns END

	public GameWarnIssueLog(){
	}

	public GameWarnIssueLog(
		Long id
	){
		this.id = id;
	}


	public void setCreateTime(Date value) {
		this.createTime = value;
	}
	
	public Date getCreateTime() {
		return this.createTime;
	}
	public void setDisposeInfo(String value) {
		this.disposeInfo = value;
	}
	
	public String getDisposeInfo() {
		return this.disposeInfo;
	}
	public void setDisposeMemo(String value) {
		this.disposeMemo = value;
	}
	
	public String getDisposeMemo() {
		return this.disposeMemo;
	}
	public void setDisposeUser(String value) {
		this.disposeUser = value;
	}
	
	public String getDisposeUser() {
		return this.disposeUser;
	}

	public Long getLotteryid() {
		return lotteryid;
	}

	public void setLotteryid(Long lotteryid) {
		this.lotteryid = lotteryid;
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

	public String getEvent() {
		return event;
	}

	public void setEvent(String event) {
		this.event = event;
	}

	
	
}

