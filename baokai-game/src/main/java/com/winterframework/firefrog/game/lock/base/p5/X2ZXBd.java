package com.winterframework.firefrog.game.lock.base.p5;

import java.util.HashMap;
import java.util.Map;

import com.winterframework.firefrog.game.lock.base.BaseConfig;
import com.winterframework.firefrog.game.lock.base.LockTools;
import com.winterframework.firefrog.game.lock.base.SingleMethod;

public class X2ZXBd extends X2SingleMethod {
	@Override
	public Map<String, Integer> influncePoint() {
		Map<String, Integer> list = new HashMap<String, Integer>();
		for(int i=0;i<=9;i++){
			list.putAll(P5X2Tool.influence(false,betContent+i));
		}
	   
		return list;

	}
	public static void main(String[] s){
		SingleMethod sm=new X2ZXBd();
		sm.setBetContent("1");
		LockTools.printList(sm.influncePoint());
	}
}
