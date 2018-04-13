package com.winterframework.firefrog.game.service.assist.award;

import java.util.HashMap;

@SuppressWarnings("hiding")
public class SpecialLotteryGameAwardMap<LotteryPlayMethodKeyGenerator,String> extends HashMap<LotteryPlayMethodKeyGenerator, String> {

	private static final long serialVersionUID = -6859332374426876957L;

	private SpecialLotteryGameAwardMap<LotteryPlayMethodKeyGenerator, String> parent;
	private Long lotteryType;
	
	public SpecialLotteryGameAwardMap<LotteryPlayMethodKeyGenerator, String> getParent() {
		return parent;
	}
	public void setParent(SpecialLotteryGameAwardMap<LotteryPlayMethodKeyGenerator, String> parent) {
		this.parent = parent;
	}
	public Long getLotteryType() {
		return lotteryType;
	}
	public void setLotteryType(Long lotteryType) {
		this.lotteryType = lotteryType;
	}
	
	public SpecialLotteryGameAwardMap(){
		super();
	}
	
	public SpecialLotteryGameAwardMap(SpecialLotteryGameAwardMap<LotteryPlayMethodKeyGenerator, String> map){
		super(map);
	}
}

