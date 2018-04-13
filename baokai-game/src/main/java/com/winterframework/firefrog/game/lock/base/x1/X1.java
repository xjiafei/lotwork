package com.winterframework.firefrog.game.lock.base.x1;

import java.util.HashMap;
import java.util.Map;

import com.winterframework.firefrog.game.lock.base.LockTools;
import com.winterframework.firefrog.game.lock.base.SingleMethod;

public class X1 extends SingleMethod {
	private static final String[] N100 = "00 01 02 03 04 05 06 07 08 09 10 11 12 13 14 15 16 17 18 19 20 21 22 23 24 25 26 27 28 29 30 31 32 33 34 35 36 37 38 39 40 41 42 43 44 45 46 47 48 49 50 51 52 53 54 55 56 57 58 59 60 61 62 63 64 65 66 67 68 69 70 71 72 73 74 75 76 77 78 79 80 81 82 83 84 85 86 87 88 89 90 91 92 93 94 95 96 97 98 99"
			.split(" ");

	public Map<String, Integer> influncePoint() {
		String[] ab = betContent.split(",");
		Map<String, Integer> list = new HashMap<String, Integer>();
		int index=3;
		for (String a : ab) {
			if (a.equals("-")) {
				index--;
				continue;
			} else {
				for (String n : N100) {
					String[] as = LockTools.getBaseNum(a);
					for (String aa : as) {
						if (index == 3) {
							LockTools.addPkg(list, aa + n);
						} else if (index == 2) {
							String[] t = LockTools.getBaseNum(n);
							LockTools.addPkg(list, t[0] + aa + t[1]);
						} else if (index == 1) {
							LockTools.addPkg(list, n + aa);
						}
					}
				}
			}
			index--;
		}
		return list;
	}

	public static void main(String[] s) {
		SingleMethod sm = new X1();
		sm.setBetContent("1,-,2");
		LockTools.printList(sm.influncePoint());
	}
}
