package com.winterframework.firefrog.game.web.dto;

import java.io.Serializable;
import java.util.Date;

/** 
* @ClassName: GetIssueDrawResult 
* @Description: 从ec获取奖期开奖号码
* @author david 
* @date 2014-7-9 下午2:42:04 
*  
*/
public class GetIssueDrawResult implements Serializable {
	private static final long serialVersionUID = 7459787803189649047L;
	private Long lotteryId;//彩种id
    private String webIssueCode;//web期号串，以逗号隔开
    private Date requestTime;//请求时间
	public Long getLotteryId() {
		return lotteryId;
	}
	public void setLotteryId(Long lotteryId) {
		this.lotteryId = lotteryId;
	}
	public String getWebIssueCode() {
		return webIssueCode;
	}
	public void setWebIssueCode(String webIssueCode) {
		this.webIssueCode = webIssueCode;
	}
	public Date getRequestTime() {
		return requestTime;
	}
	public void setRequestTime(Date requestTime) {
		this.requestTime = requestTime;
	}
}
