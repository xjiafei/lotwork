package com.winterframework.firefrog.game.lock.base.x3;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import java.util.Map;

import com.winterframework.firefrog.game.lock.base.LockTools;
import com.winterframework.firefrog.game.lock.base.SingleMethod;

public class X3Zxz3ds extends SingleMethod {

	@Override
	public Map<String, Integer> influncePoint() {
		Map<String, Integer> list = new HashMap<String, Integer>();

		String[] zs = betContent.split(" ");
		for (String zsdx : zs) {
			List<Character> listCharacter = getZxz3ds(zsdx);
			LockTools.addPkg(list, String.valueOf(listCharacter.get(0)) + listCharacter.get(0) + listCharacter.get(1));
			LockTools.addPkg(list, String.valueOf(listCharacter.get(0)) + listCharacter.get(1) + listCharacter.get(0));
			LockTools.addPkg(list, String.valueOf(listCharacter.get(1)) + listCharacter.get(0) + listCharacter.get(0));
		}
		return list;

	}

	private List<Character> getZxz3ds(String zsdx) {
		List<Character> list = new ArrayList<Character>();
		char[] chars = zsdx.toCharArray();
		char first = chars[0];
		char second = chars[1];
		char third = chars[2];
		if (first == second) {
			list.add(first);
			list.add(third);
		} else if (first == third) {
			list.add(first);
			list.add(second);
		} else if (second == third) {
			list.add(second);
			list.add(first);
		}
		return list;
	}
}
