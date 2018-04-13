/**   
* @Title: IndexStruc.java 
* @Package com.winterframework.firefrog.index.web.dto 
* @Description: TODO(用一句话描述该文件做什么) 
* @author 你的名字   
* @date 2013-12-23 上午10:20:44 
* @version V1.0   
*/
package com.winterframework.firefrog.index.web.dto;

/** 
* @ClassName: IndexStruc 
* @Description: TODO(这里用一句话描述这个类的作用) 
* @author 你的名字 
* @date 2013-12-23 上午10:20:44 
*  
*/
public class RedBowActivity {

	private Integer status;
	
	private Integer prize;
	
	private Integer userLv;
	
	private String weekType;
	
	private long activityId;

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getPrize() {
		return prize;
	}

	public void setPrize(Integer prize) {
		this.prize = prize;
	}

	public Integer getUserLv() {
		return userLv;
	}

	public void setUserLv(Integer userLv) {
		this.userLv = userLv;
	}

	public String getWeekType() {
		return weekType;
	}

	public void setWeekType(String weekType) {
		this.weekType = weekType;
	}

	public long getActivityId() {
		return activityId;
	}

	public void setActivityId(long activityId) {
		this.activityId = activityId;
	}
	
}
