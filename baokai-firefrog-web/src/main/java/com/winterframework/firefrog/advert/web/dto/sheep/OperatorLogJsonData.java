/**   
* @Title: OperatorLogJsonData.java 
* @Package com.winterframework.firefrog.advert.web.dto.sheep 
* @Description: TODO(用一句话描述该文件做什么) 
* @author 你的名字   
* @date 2015-1-24 下午3:03:16 
* @version V1.0   
*/
package com.winterframework.firefrog.advert.web.dto.sheep;

/** 
* @ClassName: OperatorLogJsonData 
* @Description: TODO(这里用一句话描述这个类的作用) 
* @author 你的名字 
* @date 2015-1-24 下午3:03:16 
*  
*/
public class OperatorLogJsonData {

	private Long status;
	private Long length;
	private Long activityConfigId;
	
	private Long model;
	private Object[][] rewards;
	public Long getStatus() {
		return status;
	}
	public void setStatus(Long status) {
		this.status = status;
	}
	public Long getLength() {
		return length;
	}
	public void setLength(Long length) {
		this.length = length;
	}
	public Long getModel() {
		return model;
	}
	public void setModel(Long model) {
		this.model = model;
	}
	public Object[][] getRewards() {
		return rewards;
	}
	public void setRewards(Object[][] rewards) {
		this.rewards = rewards;
	}
	public Long getActivityConfigId() {
		return activityConfigId;
	}
	public void setActivityConfigId(Long activityConfigId) {
		this.activityConfigId = activityConfigId;
	}
}
