/**   
* @Title: GameLockAppraiseQueryResponse.java 
* @Package com.winterframework.firefrog.game.web.dto 
* @Description: TODO(用一句话描述该文件做什么) 
* @author 你的名字   
* @date 2014-5-7 下午1:35:40 
* @version V1.0   
*/
package com.winterframework.firefrog.game.web.dto;

import java.io.Serializable;
import java.util.List;

/** 
* @ClassName: GameLockAppraiseQueryResponse 
* @Description: 变价查询
* @author 你的名字 
* @date 2014-5-7 下午1:35:40 
*  
*/
public class GameLockAppraiseQueryResponse implements Serializable{

	/** 
	* @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么) 
	*/ 
	private static final long serialVersionUID = -878656453l;

	private Long id;
	
	private String title;
	
	private String gmtModify;
	
	private Long status;
	
	private Long gameId;
	
	private String templete;
	
	private Long minVal;
	
	private Long curUser;
	
	private List<GameLockAppraiseChangeStruc> changeStrucList;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getGmtModify() {
		return gmtModify;
	}

	public void setGmtModify(String gmtModify) {
		this.gmtModify = gmtModify;
	}

	public Long getStatus() {
		return status;
	}

	public void setStatus(Long status) {
		this.status = status;
	}

	public Long getGameId() {
		return gameId;
	}

	public void setGameId(Long gameId) {
		this.gameId = gameId;
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

	public List<GameLockAppraiseChangeStruc> getChangeStrucList() {
		return changeStrucList;
	}

	public void setChangeStrucList(List<GameLockAppraiseChangeStruc> changeStrucList) {
		this.changeStrucList = changeStrucList;
	}

	public Long getCurUser() {
		return curUser;
	}

	public void setCurUser(Long curUser) {
		this.curUser = curUser;
	}
	
	
}
