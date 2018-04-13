package com.winterframework.firefrog.game.web.dto;

import java.io.Serializable;
import java.util.List;

/** 
* @ClassName: BetMethodDescriptionQueryResponse 
* @Description: 玩法描述查询响应参数封装对象 
* @author Denny 
* @date 2013-8-21 下午4:49:32 
*  
*/
public class BetMethodDescriptionQueryResponse implements Serializable {

	private static final long serialVersionUID = -5230729862937670632L;

	private List<BetMethodHelpStruc> betMethodHelpStruc;
	private String lotteryHelpDes;
	private String lotteryHelpDes_bak;
    private Integer checkStatus;
	public List<BetMethodHelpStruc> getBetMethodHelpStruc() {
		return betMethodHelpStruc;
	}

	public void setBetMethodHelpStruc(List<BetMethodHelpStruc> betMethodHelpStruc) {
		this.betMethodHelpStruc = betMethodHelpStruc;
	}

	public String getLotteryHelpDes() {
		return lotteryHelpDes;
	}

	public void setLotteryHelpDes(String lotteryHelpDes) {
		this.lotteryHelpDes = lotteryHelpDes;
	}

	public String getLotteryHelpDes_bak() {
		return lotteryHelpDes_bak;
	}

	public void setLotteryHelpDes_bak(String lotteryHelpDes_bak) {
		this.lotteryHelpDes_bak = lotteryHelpDes_bak;
	}

    public Integer getCheckStatus() {
        return checkStatus;
    }

    public void setCheckStatus(Integer checkStatus) {
        this.checkStatus = checkStatus;
    }
}
