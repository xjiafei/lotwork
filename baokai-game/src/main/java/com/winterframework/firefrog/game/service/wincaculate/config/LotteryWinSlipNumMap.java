/**   
* @Title: LotteryWinSlipNumMap.java 
* @Package com.winterframework.firefrog.game.service.wincaculate.config 
* @Description: TODO(用一句话描述该文件做什么) 
* @author 你的名字   
* @date 2014-3-19 下午3:50:49 
* @version V1.0   
*/
package com.winterframework.firefrog.game.service.wincaculate.config;

import java.util.HashMap;
import java.util.Map;

import com.winterframework.firefrog.common.wincaculate.ILotteryWinSlipNumCaculator;

/** 
* @ClassName: LotteryWinSlipNumMap 
* @Description: TODO(这里用一句话描述这个类的作用) 
* @author 你的名字 
* @date 2014-3-19 下午3:50:49 
*  
*/
public class LotteryWinSlipNumMap<LotteryPlayMethodKeyGenerator, ILotteryWinSlipNumCaculator> extends
		HashMap<LotteryPlayMethodKeyGenerator, ILotteryWinSlipNumCaculator> {
	private static final long serialVersionUID = -12134354544444l;
	private LotteryWinSlipNumMap<LotteryPlayMethodKeyGenerator, ILotteryWinSlipNumCaculator> parent;
	private Long lotteryType;

	public Long getLotteryType() {
		return lotteryType;
	}

	public void setLotteryType(Long lotteryType) {
		this.lotteryType = lotteryType;
	}

	public LotteryWinSlipNumMap<LotteryPlayMethodKeyGenerator, ILotteryWinSlipNumCaculator> getParent() {
		return parent;
	}

	public void setParent(LotteryWinSlipNumMap<LotteryPlayMethodKeyGenerator, ILotteryWinSlipNumCaculator> parent) {
		this.parent = parent;
	}
	
	public LotteryWinSlipNumMap(){
		super();
	}

	public LotteryWinSlipNumMap(Map<LotteryPlayMethodKeyGenerator, ILotteryWinSlipNumCaculator> map) {
		super(map);
	}
}
