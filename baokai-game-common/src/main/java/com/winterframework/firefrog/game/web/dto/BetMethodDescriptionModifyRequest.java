package com.winterframework.firefrog.game.web.dto;

import java.io.Serializable;
import java.net.URLDecoder;
import java.util.List;

import javax.validation.constraints.NotNull;

/** 
* @ClassName: BetMethodDescriptionModifyRequest 
* @Description: 玩法描述修改请求参数封装对象 
* @author Denny 
* @date 2013-8-21 下午4:50:32 
*  
*/
public class BetMethodDescriptionModifyRequest implements Serializable {

	private static final long serialVersionUID = 3882184912632921250L;

	/** 彩种 */
	@NotNull
	private Long lotteryid;
	/** 开奖周期描述 */
	@NotNull
	private String lotteryHelpDes;
	/** 玩法描述列表 */
	@NotNull
	private List<BetMethodHelpStruc> betMethodHelpStruc;

	public Long getLotteryid() {
		return lotteryid;
	}

	public void setLotteryid(Long lotteryid) {
		this.lotteryid = lotteryid;
	}

	@SuppressWarnings("deprecation")
	public String getLotteryHelpDes() {
		return lotteryHelpDes==null ? null : URLDecoder.decode(lotteryHelpDes);
	}

	@SuppressWarnings("deprecation")
	public void setLotteryHelpDes(String lotteryHelpDes) {
		this.lotteryHelpDes = URLDecoder.decode(lotteryHelpDes);
	}

	public List<BetMethodHelpStruc> getBetMethodHelpStruc() {
		return betMethodHelpStruc;
	}

	public void setBetMethodHelpStruc(List<BetMethodHelpStruc> betMethodHelpStruc) {
		this.betMethodHelpStruc = betMethodHelpStruc;
	}

}
