package com.winterframework.firefrog.game.web.dto;

/** 
* @ClassName: GameBlockadeInfo 
* @Description: 封锁变价前台需要的结构体信息
* @author 你的名字 
* @date 2014-5-29 上午9:46:27 
*  
*/
public class GameBlockadeInfo {
	//1 仅存在封锁	2 仅存在变价 3 存在封锁和变价
	private Integer type;
	//调整后的总价
	private Long amountAdjust;
	//付款账号
	private String username;

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Long getAmountAdjust() {
		return amountAdjust;
	}

	public void setAmountAdjust(Long amountAdjust) {
		this.amountAdjust = amountAdjust;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
}
