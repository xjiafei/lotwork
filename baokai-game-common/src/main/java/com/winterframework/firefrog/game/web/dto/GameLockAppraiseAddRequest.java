/**   
* @Title: GameLockParamAddRequest.java 
* @Package com.winterframework.firefrog.game.web.dto 
* @Description: TODO(用一句话描述该文件做什么) 
* @author 你的名字   
* @date 2014-5-8 下午5:43:39 
* @version V1.0   
*/
package com.winterframework.firefrog.game.web.dto;

import java.io.Serializable;

/** 
* @ClassName: GameLockParamAddRequest 
* @Description: TODO(这里用一句话描述这个类的作用) 
* @author 你的名字 
* @date 2014-5-8 下午5:43:39 
*  
*/
public class GameLockAppraiseAddRequest implements Serializable {
	private static final long serialVersionUID = 213434343211L;
	private Long id;

	private Long gameId;

	private String title;

	private String templete;

	private Long minVal;

	private String changeStruc;

	private Long currUse;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getGameId() {
		return gameId;
	}

	public void setGameId(Long gameId) {
		this.gameId = gameId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getTemplete() {
		return templete;
	}

	public void setTemplete(String templete) {
		this.templete = templete;
	}

	public Long getMinVal() {
		return minVal;
	}

	public void setMinVal(Long minVal) {
		this.minVal = minVal;
	}

	public String getChangeStruc() {
		return changeStruc;
	}

	public void setChangeStruc(String changeStruc) {
		this.changeStruc = changeStruc;
	}

	public Long getCurrUse() {
		return currUse;
	}

	public void setCurrUse(Long currUse) {
		this.currUse = currUse;
	}
}
