package com.winterframework.firefrog.game.lock.base.x2;

import java.util.Map;

import com.winterframework.firefrog.game.lock.base.SingleMethod;

public class X2Ds extends X2SingleMethod {
	
	@Override
	public Map<String,Integer>  influncePoint() {	
		 return (X2Tool.influence(qOrh,betContent.split(" ")));
	}

}
