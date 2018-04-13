package com.winterframework.firefrog.game.service.assist.lockPoint;

import java.util.HashMap;
import java.util.Map;

public class SpecialPlayMethoedKeyAssistMap<LotteryPlayMethodKeyGenerator, List> extends
		HashMap<LotteryPlayMethodKeyGenerator, List> {

	private static final long serialVersionUID = -3293395654718862487L;

	private Long lotteryType;

	private SpecialPlayMethoedKeyAssistMap<LotteryPlayMethodKeyGenerator, List> parent;

	public SpecialPlayMethoedKeyAssistMap() {
		super();
	}

	public SpecialPlayMethoedKeyAssistMap(Map<LotteryPlayMethodKeyGenerator, List> map) {
		super(map);
	}

	public Long getLotteryType() {
		return lotteryType;
	}

	public void setLotteryType(Long lotteryType) {
		this.lotteryType = lotteryType;
	}

	public SpecialPlayMethoedKeyAssistMap<LotteryPlayMethodKeyGenerator, List> getParent() {
		return parent;
	}

	public void setParent(SpecialPlayMethoedKeyAssistMap<LotteryPlayMethodKeyGenerator, List> parent) {
		this.parent = parent;
	}
}
