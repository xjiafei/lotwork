package com.winterframework.firefrog.game.lock.base.x3;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.winterframework.firefrog.game.lock.base.LockTools;
import com.winterframework.firefrog.game.lock.base.MethodPackage;
import com.winterframework.firefrog.game.lock.base.MultMethod;
//混合组选
public class SanxingHhZx extends MultMethod {


	@Override
	public List<MethodPackage> toSingleMethod(String betContent) {
		String[] bets = betContent.split(" ");
		MethodPackage z3ds = new MethodPackage(this.methodId);
		MethodPackage z6 = new MethodPackage(this.methodId);
		List<MethodPackage> methods = new ArrayList<MethodPackage>();
		for (String bet : bets) {
			boolean c = check(bet);
			if (c) {
				//如果有重复，z3
				z3ds.getBetContent().putAll(LockTools.toZ3(bet));
			} else {
				z6.getBetContent().putAll(LockTools.toZ6(bet));
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
				if (b[i].equals(b[j])) {
					return true;
				}
			}
		}
		return false;
	}

	@Override
	public Map<String,Integer> influncePoint() {
		Map<String,Integer> list=new HashMap<String,Integer>();
		String[] bets = betContent.split(" ");
		for (String bet : bets) {
			boolean c = check(bet);
			if (c) {
				//如果有重复，z3
				list.putAll(LockTools.toZ3(bet));
			} else {
				list.putAll(LockTools.toZ6(bet));
			}
		}
		return list;
	}
	public static void main(String[] s) {
		SanxingHhZx sm = new SanxingHhZx();
		sm.setBetContent("033 133 233 334 335 336 337 338 339 003 113 223 344 355 366 377 388 399");
		LockTools.printList(sm.influncePoint());
		//没有107
	}
}
