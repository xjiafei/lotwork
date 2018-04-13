package com.winterframework.firefrog.game.lock.base.x2;

import java.util.HashMap;
import java.util.Map;

import com.winterframework.firefrog.game.lock.base.BaseConfig;
import com.winterframework.firefrog.game.lock.base.LockTools;
import com.winterframework.firefrog.game.lock.base.SingleMethod;
import com.winterframework.firefrog.game.lock.base.p5.X2ZXBd;

//直选和值
public class X2Hz extends X2SingleMethod {
	@Override
	public Map<String, Integer> influncePoint() {
		Map<String, Integer> list = new HashMap<String, Integer>();
		for (String abc : betContent.split(",")) {
		   list.putAll(X2Tool.influence(qOrh,BaseConfig.getX2Hz(abc)));
		}
		return list;

	}
	
	
}
