 package com.winterframework.firefrog.game.dao.vo;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import com.winterframework.orm.dal.ibatis3.BaseEntity;

/**
 * @author cms group
 * @version 1.0
 * @since 1.0
 */


public class GameECLog extends BaseEntity {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4104983904944980904L;
	//alias
	public static final String TABLE_ALIAS = "开奖中心日志";
	public static final String ALIAS_CUSTOMER = "商户身份标识";
	public static final String ALIAS_RECORDID = "EC推送记录ID";
	public static final String ALIAS_LOTTERY = "彩票代号";
	public static final String ALIAS_ISSUE = "奖期号";
	public static final String ALIAS_REQUEST_TIME = "请求时间";
	public static final String ALIAS_ISSUE_NUMBER = "中奖号码";
	public static final String ALIAS_VERIFIED_TIME = "号码确认时间";
	public static final String ALIAS_EARLIEST_TIME = "最早获得时间";
	public static final String ALIAS_STOPSALE_TIME = "销售截止时间";
	public static final String ALIAS_DRAWING_TIME = "开奖时间";
	public static final String ALIAS_SAFESTR = "安全校验字符串";
	public static final String ALIAS_TYPE = "日志类型";
	public static final String ALIAS_STRUUID = "UUID";
	public static final String ALIAS_STATUS = "返回状态";
	public static final String ALIAS_MESSAGE = "返回消息";
	
	//date formats
	
	//columns START
	private String customer;
	private Long recordID;
	private String lottery;
	private String issue;
	private String requestTime;
	private String verifiedTime;
	private String earliestTime;
	private String stopsaleTime;
	private String drawingTime;
	private String safeStr;
	private int type;
	private String strUUID;
	private String issueNumber;
	private String status;
	private String message;
	//columns END

	public GameECLog(){
	}

	public GameECLog(
		Long id
	){
		this.id = id;
	}

	
    public String getCustomer() {
		return customer;
	}

	public void setCustomer(String customer) {
		this.customer = customer;
	}

	public Long getRecordID() {
		return recordID;
	}

	public void setRecordID(Long recordID) {
		this.recordID = recordID;
	}

	public String getLottery() {
		return lottery;
	}

	public void setLottery(String lottery) {
		this.lottery = lottery;
	}

	public String getIssue() {
		return issue;
	}

	public void setIssue(String issue) {
		this.issue = issue;
	}

	public String getRequestTime() {
		return requestTime;
	}

	public void setRequestTime(String requestTime) {
		this.requestTime = requestTime;
	}

	public String getVerifiedTime() {
		return verifiedTime;
	}

	public void setVerifiedTime(String verifiedTime) {
		this.verifiedTime = verifiedTime;
	}

	public String getEarliestTime() {
		return earliestTime;
	}

	public void setEarliestTime(String earliestTime) {
		this.earliestTime = earliestTime;
	}

	public String getStopsaleTime() {
		return stopsaleTime;
	}

	public void setStopsaleTime(String stopsaleTime) {
		this.stopsaleTime = stopsaleTime;
	}

	public String getDrawingTime() {
		return drawingTime;
	}

	public void setDrawingTime(String drawingTime) {
		this.drawingTime = drawingTime;
	}

	public String getSafeStr() {
		return safeStr;
	}

	public void setSafeStr(String safeStr) {
		this.safeStr = safeStr;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}


	public String getStrUUID() {
		return strUUID;
	}

	public void setStrUUID(String strUUID) {
		this.strUUID = strUUID;
	}

	public String getIssueNumber() {
		return issueNumber;
	}

	public void setIssueNumber(String issueNumber) {
		this.issueNumber = issueNumber;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this)
		.append("Id",getId())		
		.append("Customer",getCustomer())		
		.append("RecordID",getRecordID())		
		.append("Lottery",getLottery())		
		.append("Issue",getIssue())		
		.append("RequestTime",getRequestTime())		
		.append("getVerifiedTime",getVerifiedTime())
		.append("getEarliestTime",getEarliestTime())
		.append("getStopsaleTime",getStopsaleTime())
		.append("getDrawingTime",getDrawingTime())
		.append("getSafeStr",getSafeStr())
		.append("getType",getType())
		.append("getStrUUID",getStrUUID())
		.append("getIssueNumber",getIssueNumber())
			.toString();
	}
    @Override
	public int hashCode() {
		return new HashCodeBuilder()
		.append(getId())
		.append(getCustomer())
		.append(getRecordID())
		.append(getLottery())
		.append(getIssue())
		.append(getRequestTime())
		.append(getVerifiedTime())
		.append(getEarliestTime())
		.append(getStopsaleTime())
		.append(getDrawingTime())
		.append(getSafeStr())
		.append(getType())
		.append(getStrUUID())
		.append(getIssueNumber())
		.append(getStatus())
		.append(getMessage())
			.toHashCode();
	}
    @Override
	public boolean equals(Object obj) {
		if(obj instanceof GameECLog == false) return false;
		if(this == obj) return true;
		GameECLog other = (GameECLog)obj;
		return new EqualsBuilder()
		.append(getId(),other.getId())

		.append(getCustomer(),other.getCustomer())

		.append(getRecordID(),other.getRecordID())

		.append(getLottery(),other.getLottery())

		.append(getIssue(),other.getIssue())

		.append(getRequestTime(),other.getRequestTime())

		.append(getVerifiedTime(),other.getVerifiedTime())
		
		.append(getEarliestTime(),other.getEarliestTime())

		.append(getStopsaleTime(),other.getStopsaleTime())

		.append(getDrawingTime(),other.getDrawingTime())

		.append(getSafeStr(),other.getSafeStr())

		.append(getType(),other.getType())
		
		.append(getStrUUID(),other.getStrUUID())
		
		.append(getIssueNumber(),other.getIssueNumber())

		.append(getStatus(),other.getStatus())
		
		.append(getMessage(),other.getMessage())
			.isEquals();
	}
}

