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
* @ClassName: ActivitySheepUserDiceAwardRequest 
* @Description: 羊年活动押大小dice award请求request
* @author david
*  
*/
public class ActivitySheepUserDiceAwardRequest implements Serializable{

	private static final long serialVersionUID = -1543757401485976506L;
    
	private Long userId;
	
	private Boolean isGuessLittle;
	
	private Long channel;

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Boolean getIsGuessLittle() {
		return isGuessLittle;
	}

	public void setIsGuessLittle(Boolean isGuessLittle) {
		this.isGuessLittle = isGuessLittle;
	}

	public Long getChannel() {
		return channel;
	}

	public void setChannel(Long channel) {
		this.channel = channel;
	}

}
