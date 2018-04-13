package com.winterframework.firefrog.game.lock.base.x3;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.winterframework.firefrog.game.lock.base.BaseConfig;
import com.winterframework.firefrog.game.lock.base.LockTools;
import com.winterframework.firefrog.game.lock.base.MethodPackage;
import com.winterframework.firefrog.game.lock.base.MultMethod;

public class SanxingHhZxHz extends MultMethod {

	@Override
	public Map<String,Integer> influncePoint() {
		Map<String,Integer>  list=new HashMap<String,Integer> ();
		String[] strs=betContent.split(" ");
		for(String str:strs){
			LockTools.addPkg(list,BaseConfig.getZ3Hezhi(str));
			LockTools.addPkg(list,BaseConfig.getZ6Hezhi(str));
		}
		return list;
	}

	@Override
	public List<MethodPackage> toSingleMethod(String betContent) {
		String[] bets = betContent.split(" ");
		MethodPackage z3ds = new MethodPackage(this.getMethodId());
		MethodPackage z6 = new MethodPackage(this.getMethodId());
		List<MethodPackage> methods = new ArrayList<MethodPackage>();
		for (String bet : bets) {
			boolean c = check(bet);
			if (c) {
				//如果有重复，z3
				LockTools.addPkg(z3ds.getBetContent(),bet);
			} else {
				LockTools.addPkg(z6.getBetContent(),bet);
			}
		}
		methods.add(z3ds);
		methods.add(z6);
		return methods;
	}

	public static boolean check(String bet) {
		String[] b = LockTools.getBaseNum(bet);
		for (int i = 0; i < b.length; i++) {
			for (int j = i + 1; j < b.length; j++) {
				if (b[i] == b[j]) {
					return true;
				}
			}
		}
		return false;
	}

}
