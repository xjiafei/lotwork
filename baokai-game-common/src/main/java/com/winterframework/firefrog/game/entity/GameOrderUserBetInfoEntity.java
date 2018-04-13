/**   
* @Title: GameOrderUserBetInfoEntity.java 
* @Package com.winterframework.firefrog.game.entity 
* @Description: TODO(用一句话描述该文件做什么) 
* @author 你的名字   
* @date 2014-12-3 上午9:58:51 
* @version V1.0   
*/
package com.winterframework.firefrog.game.entity;

/** 
* @ClassName: GameOrderUserBetInfoEntity 
* @Description: TODO(这里用一句话描述这个类的作用) 
* @author 你的名字 
* @date 2014-12-3 上午9:58:51 
*  
*/
public class GameOrderUserBetInfoEntity {

	private Long id;
	private Long userId;
	private Long totamount;
	private Long countWin;
	private String retPointChain;
	private String retUserChain;

	public Long getTotamount() {
		return totamount;
	}

	public void setTotamount(Long totamount) {
		this.totamount = totamount;
	}

	public Long getCountWin() {
		return countWin;
	}

	public void setCountWin(Long countWin) {
		this.countWin = countWin;
	}

	public String getRetPointChain() {
		return retPointChain;
	}

	public void setRetPointChain(String retPointChain) {
		this.retPointChain = retPointChain;
	}

	public String getRetUserChain() {
		return retUserChain;
	}

	public void setRetUserChain(String retUserChain) {
		this.retUserChain = retUserChain;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

}
