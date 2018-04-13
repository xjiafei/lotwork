package com.winterframework.firefrog.game.lock.base.p5;

import java.util.HashMap;
import java.util.Map;

import com.winterframework.firefrog.game.lock.base.BaseConfig;
import com.winterframework.firefrog.game.lock.base.LockTools;
import com.winterframework.firefrog.game.lock.base.SingleMethod;

//直选和值
public class X2Kd extends X2SingleMethod {
	@Override
	public Map<String, Integer> influncePoint() {
		Map<String, Integer> list = new HashMap<String, Integer>();
		for (String abc : betContent.split(",")) {
		   list.putAll(P5X2Tool.influence(BaseConfig.getX2Kd(abc)));
		}
		return list;

	}
	
}
