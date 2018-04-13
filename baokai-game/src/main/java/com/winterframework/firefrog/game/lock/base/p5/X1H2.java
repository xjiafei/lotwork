package com.winterframework.firefrog.game.lock.base.p5;

import java.util.HashMap;
import java.util.Map;

import com.winterframework.firefrog.game.lock.base.LockTools;
import com.winterframework.firefrog.game.lock.base.SingleMethod;

public class X1H2 extends SingleMethod {
	private static final String[] N100 = "0,1,2,3,4,5,6,7,8,9".split(",");

	public Map<String, Integer> influncePoint() {
		String[] ab = betContent.split(",");
		Map<String, Integer> list = new HashMap<String, Integer>();
		if (ab.length != 5)
			return list;
		int zump = 0;
		for (String a : ab) {
			zump++;
			if (a.equals("-") || zump < 4) {
				continue;
			} else {
				String[] as = LockTools.getBaseNum(a);
				for (String aa : as) {
					for (String s : N100) {
						if (zump == 4) {
							P5LockTools.addPkg(list, aa + "" + s);
						} else {
							P5LockTools.addPkg(list, s + "" +aa);
						}
					}
				}

			}
		}
		return list;
	}

	public static void main(String[] s) {
		SingleMethod sm = new X1H2();
		sm.setBetContent("-,-,-,0,-");
		P5LockTools.printList(sm.influncePoint());
	}
}
