package com.winterframework.firefrog.game.web.dto;

import java.io.Serializable;

/** 
* @ClassName: SaveProxyBetGameAwardGroupRequest 
* @Description: 保存代理投注奖金组请求DTO 
* @author Denny 
* @date 2013-12-18 下午4:08:25 
*  
*/
public class SaveProxyBetGameAwardGroupRequest implements Serializable {

	private static final long serialVersionUID = -2063593215340703808L;

	private Long awardGroupId;
	private Long userid;
	private Long lotteryid;

	public Long getAwardGroupId() {
		return awardGroupId;
	}

	public void setAwardGroupId(Long awardGroupId) {
		this.awardGroupId = awardGroupId;
	}

	public Long getUserid() {
		return userid;
	}

	public void setUserid(Long userid) {
		this.userid = userid;
	}

	public Long getLotteryid() {
		return lotteryid;
	}

	public void setLotteryid(Long lotteryid) {
		this.lotteryid = lotteryid;
	}

}
