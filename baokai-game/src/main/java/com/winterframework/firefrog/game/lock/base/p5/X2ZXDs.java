package com.winterframework.firefrog.game.lock.base.p5;

import java.util.HashMap;
import java.util.Map;

import com.winterframework.firefrog.game.lock.base.LockTools;
import com.winterframework.firefrog.game.lock.base.SingleMethod;

public class X2ZXDs extends X2SingleMethod {
	@Override
	public Map<String, Integer> influncePoint() {
		Map<String, Integer> list = new HashMap<String, Integer>();
		String[] zs = betContent.split(" ");

		for (String z : zs) {
			String[] first = LockTools.getBaseNum(z);
			for (int i = 0; i < first.length; i++) {
					list.putAll(P5X2Tool.influence(false,first[0] + first[1]));
			}
		}
		return list;
	}

	public static void main(String[] s) {
		SingleMethod sm = new X2ZXDs();
		sm.setBetContent("13 23");
		LockTools.printList(sm.influncePoint());
	}
}
