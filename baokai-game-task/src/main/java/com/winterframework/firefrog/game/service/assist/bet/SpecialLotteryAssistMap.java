/**   
* @Title: LotteryWinSlipNumMap.java 
* @Package com.winterframework.firefrog.game.service.wincaculate.config 
* @Description: TODO(用一句话描述该文件做什么) 
* @author 你的名字   
* @date 2014-3-19 下午3:50:49 
* @version V1.0   
*/
package com.winterframework.firefrog.game.service.assist.bet;

import java.util.HashMap;
import java.util.Map;

/** 
* @ClassName: LotteryWinSlipNumMap 
* @Description: TODO(这里用一句话描述这个类的作用) 
* @author 你的名字 
* @date 2014-3-19 下午3:50:49 
*  
*/
@SuppressWarnings("hiding")
public class SpecialLotteryAssistMap<LotteryPlayMethodKeyGenerator, List> extends
		HashMap<LotteryPlayMethodKeyGenerator, List> {
	private static final long serialVersionUID = -12134354544444l;
	private SpecialLotteryAssistMap<LotteryPlayMethodKeyGenerator, List> parent;
	private Long lotteryType;

	public Long getLotteryType() {
		return lotteryType;
	}

	public void setLotteryType(Long lotteryType) {
		this.lotteryType = lotteryType;
	}

	public SpecialLotteryAssistMap<LotteryPlayMethodKeyGenerator, List> getParent() {
		return parent;
	}

	public void setParent(SpecialLotteryAssistMap<LotteryPlayMethodKeyGenerator, List> parent) {
		this.parent = parent;
	}

	public SpecialLotteryAssistMap() {
		super();
	}

	public SpecialLotteryAssistMap(Map<LotteryPlayMethodKeyGenerator, List> map) {
		super(map);
	}
}
