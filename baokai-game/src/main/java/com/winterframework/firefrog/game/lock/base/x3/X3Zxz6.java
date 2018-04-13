package com.winterframework.firefrog.game.lock.base.x3;

import java.util.HashMap;
import java.util.Map;

import com.winterframework.combinatorics.Factory;
import com.winterframework.combinatorics.Generator;
import com.winterframework.combinatorics.ICombinatoricsVector;
import com.winterframework.firefrog.game.lock.base.LockTools;
import com.winterframework.firefrog.game.lock.base.SingleMethod;

public class X3Zxz6 extends SingleMethod {

	@Override
	public Map<String,Integer> influncePoint() {	
		Map<String,Integer> list=new HashMap<String,Integer>();
		ICombinatoricsVector<String> initialVector = Factory.createVector(betContent.split(","));
		Generator<String> gen = Factory.createSimpleCombinationGenerator(initialVector, 3);
		for (ICombinatoricsVector<String> combination : gen) {
			//list.add((combination.stringVal()));
			list.putAll(LockTools.toZ6(combination.stringVal()));
			
		}

		return list;

	}

	public static void main(String[] args) {
		X3Zxz6 a=new X3Zxz6();
		a.betContent="3,4,5,6";
		LockTools.printList(a.influncePoint());
	}

}
