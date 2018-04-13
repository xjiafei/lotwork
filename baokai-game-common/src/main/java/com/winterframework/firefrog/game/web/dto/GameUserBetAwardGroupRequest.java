package com.winterframework.firefrog.game.web.dto;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

/**
 * 
* @ClassName: GameUserBetAwardGroupRequest 
* @Description: 查询投注奖金组请求
* @author Richard
* @date 2013-9-17 下午5:16:31 
*
 */
public class GameUserBetAwardGroupRequest implements Serializable{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6924782506962474808L;
	@NotNull
	private Long lotteryid;
	
	@NotNull
	private Long userid;
	
	public GameUserBetAwardGroupRequest(){
		
	}

	public Long getLotteryid() {
		return lotteryid;
	}

	public void setLotteryid(Long lotteryid) {
		this.lotteryid = lotteryid;
	}

	public Long getUserid() {
		return userid;
	}

	public void setUserid(Long userid) {
		this.userid = userid;
	}

}
