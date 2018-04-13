package com.winterframework.firefrog.game.web.dto;

import java.io.Serializable;

/** 
* @ClassName: QueryDescByBetMethodRequest 
* @Description: 按投注方式用户ID查询玩法描述请求参数封装DTO 
* @author Denny 
* @date 2013-10-12 上午11:24:46 
*  
*/
public class QueryDescByBetMethodByUserIdRequest implements Serializable {


	/**
	 * 
	 */
	private static final long serialVersionUID = -4732630790589593878L;
	private Long lotteryid;
	private Integer gameGroupCode;
	private Integer gameSetCode;
	private Integer betMethodCode;
	private Long userid;

	public Long getLotteryid() {
		return lotteryid;
	}

	public void setLotteryid(Long lotteryid) {
		this.lotteryid = lotteryid;
	}

	public Integer getGameGroupCode() {
		return gameGroupCode;
	}

	public void setGameGroupCode(Integer gameGroupCode) {
		this.gameGroupCode = gameGroupCode;
	}

	public Integer getGameSetCode() {
		return gameSetCode;
	}

	public void setGameSetCode(Integer gameSetCode) {
		this.gameSetCode = gameSetCode;
	}

	public Integer getBetMethodCode() {
		return betMethodCode;
	}

	public void setBetMethodCode(Integer betMethodCode) {
		this.betMethodCode = betMethodCode;
	}

	public Long getUserid() {
		return userid;
	}

	public void setUserid(Long userid) {
		this.userid = userid;
	}

}
