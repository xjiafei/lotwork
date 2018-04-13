package com.winterframework.firefrog.game.lock.base.x3;

import java.util.HashMap;
import java.util.Map;

import com.winterframework.firefrog.game.lock.base.BaseConfig;
import com.winterframework.firefrog.game.lock.base.LockTools;
import com.winterframework.firefrog.game.lock.base.SingleMethod;

public class X3hz extends SingleMethod{
	
	@Override
	public Map<String, Integer> influncePoint() {
		Map<String, Integer> list = new HashMap<String, Integer>();
		for (String abc : betContent.split(",")) {
			String[] hz = BaseConfig.getHezhi(abc);
			LockTools.addPkg(list, hz);

		}
		return list;

	}

}
