package com.winterframework.firefrog.game.web.dto;

import java.io.Serializable;
import java.util.List;

import javax.validation.constraints.NotNull;

/** 
* @ClassName: BetLimitModifyRequest 
* @Description: 投注限制修改请求参数封装对象 
* @author Denny 
* @date 2013-8-21 下午4:43:42 
*  
*/
public class BetLimitModifyRequest implements Serializable {

	private static final long serialVersionUID = 6658700256938719636L;

	/** 彩种 */
	@NotNull
	private Long lotteryid;
	/** 投注限制列表 */
	@NotNull
	private List<BetMethodMultipleStruc> betLimitList;
	
	private List<BetLimitAssistStruc> betAssist;

	public Long getLotteryid() {
		return lotteryid;
	}

	public void setLotteryid(Long lotteryid) {
		this.lotteryid = lotteryid;
	}

	public List<BetMethodMultipleStruc> getBetLimitList() {
		return betLimitList;
	}

	public void setBetLimitList(List<BetMethodMultipleStruc> betLimitList) {
		this.betLimitList = betLimitList;
	}

	public List<BetLimitAssistStruc> getBetAssist() {
		return betAssist;
	}

	public void setBetAssist(List<BetLimitAssistStruc> betAssist) {
		this.betAssist = betAssist;
	}
}
