/**   
* @Title: GameLockAppriaseOperateRequest.java 
* @Package com.winterframework.firefrog.game.web.dto 
* @Description: TODO(用一句话描述该文件做什么) 
* @author 你的名字   
* @date 2014-5-7 下午3:39:23 
* @version V1.0   
*/
package com.winterframework.firefrog.game.web.dto;

/** 
* @ClassName: GameLockAppriaseOperateRequest 
* @Description: TODO(这里用一句话描述这个类的作用) 
* @author 你的名字 
* @date 2014-5-7 下午3:39:23 
*  
*/
public class GameLockAppriaseOperateRequest {

	private Long id;
	
	private Long status;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getStatus() {
		return status;
	}

	public void setStatus(Long status) {
		this.status = status;
	}
}
