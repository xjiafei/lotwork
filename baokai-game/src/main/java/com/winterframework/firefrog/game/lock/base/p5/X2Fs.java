package com.winterframework.firefrog.game.lock.base.p5;

import java.util.HashMap;
import java.util.Map;

import com.winterframework.firefrog.game.lock.base.x2.X2Tool;

public class X2Fs extends X2SingleMethod {
	@Override
	public Map<String, Integer> influncePoint() {
		Map<String, Integer> list = new HashMap<String, Integer>();
		String[] zs = betContent.split(",");
		String[] first = P5LockTools.getBaseNum(zs[3]);
		String[] second = P5LockTools.getBaseNum(zs[4]);
		for (int i = 0; i < first.length; i++) {
			for (int j = 0; j < second.length; j++) {
				list.putAll(P5X2Tool.influence(true, first[i] + second[j]));
			}

		}
		return list;
	}

	public static void main(String[] str) {
		X2Fs f = new X2Fs();
		f.setqOrh(false);
		f.setBetContent("-,-,-,13,12");
		P5LockTools.printList(f.influncePoint());
	}
}
