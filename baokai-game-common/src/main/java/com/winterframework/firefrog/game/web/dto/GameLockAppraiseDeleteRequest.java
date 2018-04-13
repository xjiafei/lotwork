/**   
* @Title: GameLockAppraiseDeleteRequest.java 
* @Package com.winterframework.firefrog.game.web.dto 
* @Description: TODO(用一句话描述该文件做什么) 
* @author 你的名字   
* @date 2014-5-8 上午10:54:43 
* @version V1.0   
*/
package com.winterframework.firefrog.game.web.dto;

/** 
* @ClassName: GameLockAppraiseDeleteRequest 
* @Description: TODO(这里用一句话描述这个类的作用) 
* @author 你的名字 
* @date 2014-5-8 上午10:54:44 
*  
*/
public class GameLockAppraiseDeleteRequest {

	private String ids;
	private Long lotteryid;

	public String getIds() {
		return ids;
	}

	public void setIds(String ids) {
		this.ids = ids;
	}

	public Long getLotteryid() {
		return lotteryid;
	}

	public void setLotteryid(Long lotteryid) {
		this.lotteryid = lotteryid;
	}
}
