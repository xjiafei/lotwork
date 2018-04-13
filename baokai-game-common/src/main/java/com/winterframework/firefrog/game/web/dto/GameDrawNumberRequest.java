package com.winterframework.firefrog.game.web.dto;



/**
 * 获取开奖号码请求
 * @ClassName
 * @Description
 * @author ibm
 * 2016年4月19日
 */
public class GameDrawNumberRequest {
    private Long lotteryId;	//彩种ID
    private Long issueCode;	//奖期编码
    private String issue;	//奖期
    private String briefCode;	//彩种码
    private String openDrawTime;	//開獎时间
	public Long getLotteryId() {
		return lotteryId;
	}
	public void setLotteryId(Long lotteryId) {
		this.lotteryId = lotteryId;
	}
	
	public Long getIssueCode() {
		return issueCode;
	}
	public void setIssueCode(Long issueCode) {
		this.issueCode = issueCode;
	}
	public String getIssue() {
		return issue;
	}
	public void setIssue(String issue) {
		this.issue = issue;
	}
	public String getBriefCode() {
		return briefCode;
	}
	public void setBriefCode(String briefCode) {
		this.briefCode = briefCode;
	}
	/**
	 * @return the openDrawTime
	 */
	public String getOpenDrawTime() {
		return openDrawTime;
	}
	/**
	 * @param openDrawTime the openDrawTime to set
	 */
	public void setOpenDrawTime(String openDrawTime) {
		this.openDrawTime = openDrawTime;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "GameDrawNumberRequest [lotteryId=" + lotteryId + ", issueCode="
				+ issueCode + ", issue=" + issue + ", briefCode=" + briefCode
				+ ", openDrawTime=" + openDrawTime + "]";
	}
    
    
    
}
