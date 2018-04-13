package com.winterframework.firefrog.game.lock.base.x2;

import java.util.HashMap;
import java.util.Map;

import com.winterframework.firefrog.game.lock.base.LockTools;

public class X2Fs extends X2SingleMethod {
	@Override
	public Map<String, Integer> influncePoint() {
		Map<String, Integer> list = new HashMap<String, Integer>();
		String[] zs = betContent.split(",");
		String[] first = LockTools.getBaseNum(zs[0]);
		String[] second = LockTools.getBaseNum(zs[1]);
		String[] two = LockTools.getBaseNum(zs[2]);
		if (qOrh) {
			for (int i = 0; i < first.length; i++) {
				for (int j = 0; j < second.length; j++) {
					list.putAll(X2Tool.influence(qOrh, first[i] + second[j]));
				}
			}
		} else {
			for (int i = 0; i < second.length; i++) {
				for (int j = 0; j < two.length; j++) {
					list.putAll(X2Tool.influence(qOrh, second[i] + two[j]));
				}
			}
		}
		return list;
	}

	public static void main(String[] s) {
		X2Fs sm = new X2Fs();
		sm.setqOrh(true);
		sm.setBetContent("9,9,-");
		LockTools.printList(sm.influncePoint());
	}
}
