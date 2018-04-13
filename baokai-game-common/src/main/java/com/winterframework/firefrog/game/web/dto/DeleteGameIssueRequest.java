/**   
* @Title: DeleteGameIssueRequest.java 
* @Package com.winterframework.firefrog.game.web.dto 
* @Description: TODO(用一句话描述该文件做什么) 
* @author 你的名字   
* @date 2014-10-17 上午9:35:07 
* @version V1.0   
*/
package com.winterframework.firefrog.game.web.dto;

/** 
* @ClassName: DeleteGameIssueRequest 
* @Description: TODO(这里用一句话描述这个类的作用) 
* @author 你的名字 
* @date 2014-10-17 上午9:35:07 
*  
*/
public class DeleteGameIssueRequest {

	private Long lotteryId;
	private Integer type;
	private String start;
	private String end;

	public Long getLotteryId() {
		return lotteryId;
	}

	public void setLotteryId(Long lotteryId) {
		this.lotteryId = lotteryId;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getStart() {
		return start;
	}

	public void setStart(String start) {
		this.start = start;
	}

	public String getEnd() {
		return end;
	}

	public void setEnd(String end) {
		this.end = end;
	}

}
