package com.winterframework.firefrog.game.lock.base.p5;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.winterframework.firefrog.game.lock.base.LockTools;

public class P5X2Tool {
	/**
	 * 
	 * @param qOrh 前还是后
	 * @param dorz 但是还是组选
	 * @param abs
	 * @return
	 */
	public static Map<String, Integer> influence(boolean dorz, String... abs) {
		Map<String, Integer> lists = new HashMap<String, Integer>();
		int len = abs.length;
		for (int j = 0; j < len; j++) {
			{
				P5LockTools.addPkg(lists, abs[j]);
				if (!dorz) {
					P5LockTools.addPkg(lists, StringUtils.reverse(abs[j]));
				}
			}
		}

		return lists;
	}

	public static Map<String, Integer> influence(String... abs) {
		return influence(true, abs);
	}

}
