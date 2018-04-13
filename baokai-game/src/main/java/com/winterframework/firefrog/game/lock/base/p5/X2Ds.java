package com.winterframework.firefrog.game.lock.base.p5;

import java.util.Map;

import com.winterframework.firefrog.game.lock.base.SingleMethod;

public class X2Ds extends X2SingleMethod {
	
	@Override
	public Map<String,Integer>  influncePoint() {	
		 return (P5X2Tool.influence(betContent.split(" ")));
	}

}
