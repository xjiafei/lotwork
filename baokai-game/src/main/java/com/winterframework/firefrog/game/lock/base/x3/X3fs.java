package com.winterframework.firefrog.game.lock.base.x3;

import java.util.HashMap;
import java.util.Map;

import com.winterframework.firefrog.game.lock.base.LockTools;
import com.winterframework.firefrog.game.lock.base.PointPkg;
import com.winterframework.firefrog.game.lock.base.SingleMethod;
//三星直选复式
public class X3fs extends SingleMethod {
	@Override
	public Map<String,Integer> influncePoint(){
		Map<String,Integer> list = new HashMap<String,Integer>();
		String[] zs = betContent.split(",");
		String[] first = LockTools.getBaseNum(zs[0]);
		String[] second = LockTools.getBaseNum(zs[1]);
		String[] third = LockTools.getBaseNum(zs[2]);
		for (int i = 0; i < first.length; i++) {	
			for (int j = 0; j < second.length;j++) {
				for (int k = 0; k < third.length;k++) {
					LockTools.addPkg(list,first[i]+second[j]+third[k]);
				}
			}

		}
		return list;
	}
}
