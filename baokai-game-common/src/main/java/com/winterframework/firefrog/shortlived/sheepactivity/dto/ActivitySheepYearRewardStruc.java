/**   
* @Title: AssistBmBonusStruc.java 
* @Package com.winterframework.firefrog.game.web.dto 
* @Description: TODO(用一句话描述该文件做什么) 
* @author 你的名字   
* @date 2014-4-14 下午2:07:34 
* @version V1.0   
*/
package com.winterframework.firefrog.shortlived.sheepactivity.dto;

import java.io.Serializable;

/** 
* @ClassName: ActivitySheepYearRewardStruc 
* @Description: 羊年活动reward结构
* @author david
*  
*/
public class ActivitySheepYearRewardStruc implements Serializable{

	private static final long serialVersionUID = -1543757401485976506L;

	private String status;
	
	private String message;
	
	private ActivityRewardDateStruc data;

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

	public ActivityRewardDateStruc getData() {
		return data;
	}

	public void setData(ActivityRewardDateStruc data) {
		this.data = data;
	}


}
