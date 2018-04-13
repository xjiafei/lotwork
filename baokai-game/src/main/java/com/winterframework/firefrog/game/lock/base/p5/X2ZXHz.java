package com.winterframework.firefrog.game.lock.base.p5;

import java.util.HashMap;
import java.util.Map;

import com.winterframework.firefrog.game.lock.base.BaseConfig;
import com.winterframework.firefrog.game.lock.base.LockTools;
import com.winterframework.firefrog.game.lock.base.SingleMethod;

public class X2ZXHz extends X2SingleMethod {
	@Override
	public Map<String, Integer> influncePoint() {
		Map<String, Integer> list = new HashMap<String, Integer>();
		for (String abc : betContent.split(",")) {
			list.putAll(P5X2Tool.influence(true,BaseConfig.getX2ZxHz(abc)));	//改成true，非直选  但是由于第二个参数已经全了，因此不用reverse
		}
		return list;

	}
	public static void main(String[] s){
		SingleMethod sm=new X2ZXHz();
		sm.setBetContent("4,5");
		LockTools.printList(sm.influncePoint());
	}
}
