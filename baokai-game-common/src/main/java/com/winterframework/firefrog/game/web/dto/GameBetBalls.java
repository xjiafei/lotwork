package com.winterframework.firefrog.game.web.dto;

/** 
* @ClassName: GameBetBalls 
* @Description: 倍数超限 tpldate数据结构  
* @author david 
* @date 2013-9-27 下午3:06:09 
*  
*/
public class GameBetBalls {
	private Long id;
	private String ball;
	private String type;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getBall() {
		return ball;
	}

	public void setBall(String ball) {
		this.ball = ball;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
}
